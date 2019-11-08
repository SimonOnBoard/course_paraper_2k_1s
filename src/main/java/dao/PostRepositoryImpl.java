package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Category;
import model.Post;
import model.User;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.recycler.Recycler;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import singletone.ConnectionService;

import java.net.InetAddress;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostRepositoryImpl implements PostRepository {
    private ObjectMapper objectMapper;
    private Connection connection;

    public PostRepositoryImpl() {
        this.connection = ConnectionService.getConnection();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Optional<Post> find(Long id) {
        Post post = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM post WHERE id = ?")){
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            //Если соответстующая строка найдена,обрабатываем её c помощью userRowMapper.
            //Соответствунно получаем объект User.
            if (resultSet.next()) {
                post = postRowMapper.mapRow(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(post);
    }

    @Override
    public void save(Post model) {
        //saveToElastic(model);
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO post(name, text, category, photo_path, publication_date, show_auth, author_id) " +
                        "VALUES (?,?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1,model.getName());
            statement.setString(2,model.getText());
            statement.setString(3,model.getCategory().toString());
            statement.setString(4,model.getPhotopath());
            statement.setObject(5,model.getPublication());
            statement.setObject(6, model.getShowAuthor());
            statement.setLong(7,model.getAuth_id());
            //Выполняем запрос и сохраняем колличество изменённых строк
            int updRows = statement.executeUpdate();
            if (updRows == 0) {
                //Если ничего не было изменено, значит возникла ошибка
                //Возбуждаем соответсвующее исключений
                throw new SQLException();
            }
            //Достаём созданное Id пользователя
            try (ResultSet set = statement.getGeneratedKeys();) {
                //Если id  существет,обновляем его у подели.
                if (set.next()) {
                    model.setId(set.getLong(1));
                } else {
                    //Модель сохранилась но не удаётся получить сгенерированный id
                    //Возбуждаем соответвующее исключение
                    throw new SQLException();
                }
            }

        } catch (SQLException e) {
            //Если сохранений провалилось, обернём пойманное исключение в непроверяемое и пробросим дальше(best-practise)
            throw new IllegalStateException(e);
        }
    }

    private synchronized void saveToElastic(Post model) {
        try {
            String value = objectMapper.writeValueAsString(model);
            TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
            IndexResponse response = client.prepareIndex("test_ind", "_doc", String.valueOf(model.getId()))
                    .setSource(value, XContentType.JSON)
                    .get();
            System.out.println(response);
            client.close();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Post model) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Post> findAll() {
        return null;
    }

    @Override
    public List<Post> findAllByCategory(String category,Long offset) {
        List<Post> result = new ArrayList<>();

        //Создаём новый объект Statement
        //Использование try-with-resources необходимо для арантированного закрытия statement,
        // вне зависимости от успешности операции.
        String SQL_findByCategory = "select * from post WHERE category = (?) limit 5 offset (?);";

        try (PreparedStatement statement = connection.prepareStatement(SQL_findByCategory))

        {
            statement.setString(1,category);
            statement.setObject(2,offset);
            //ResultSet - итерируемый объект.
            //Пока есть что доставать, идём по нему и подаём строки в userRowMapper,
            // который возвращает нам готовый объект User.
            //Добавляем полученный объект в ArrayList.
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Post post = postRowMapper.mapRow(resultSet);
                result.add(post);
            }
        } catch (SQLException e) {
            //Если операция провалилась, обернём пойманное исключение в непроверяемое и пробросим дальше(best-practise)
            throw new IllegalStateException(e);
        }
        //Возвращаем полученный в результате операции ArrayList
        return result;
    }
    private RowMapper<Post> postRowMapper = row -> {
        Long id = row.getLong("id");
        String name = row.getString("name");
        String text = row.getString("text");
        Category category = Category.valueOf(row.getString("category"));
        String photoPath = row.getString("photo_path");
        LocalDateTime date = row.getObject(6, LocalDateTime.class);
        Boolean bool = row.getObject(7,Boolean.class);
        Long author_id = row.getLong(8);
        return new Post(id,name,text,category,photoPath,date,bool,author_id);
    };
}
