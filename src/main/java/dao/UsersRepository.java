package dao;

import model.User;
import singletone.ConnectionService;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class UsersRepository {
    private Connection connection;
    public UsersRepository() {
        connection = ConnectionService.getConnection();
    }
    private RowMapper<User> userFindRowMapper = row -> {
        Long id = row.getLong("id");

        String name = row.getString("name");
        String password = row.getString("password");
        String nick = row.getString("nickname");
        String mail = row.getString("email");
        Date birthday = row.getObject(6, Date.class);
        LocalDateTime registration = row.getObject(7,LocalDateTime.class);
        Long posts = row.getLong(8);
        return new User(id,name,password,nick,mail, birthday, registration,posts);
    };
    //language=SQL
    private static final String SQL_find = "select * from users WHERE username = ";
    public Optional<User> find(String name) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = ?")){
            statement.setString(1,name);
            ResultSet resultSet = statement.executeQuery();
            //Если соответстующая строка найдена,обрабатываем её c помощью userRowMapper.
            //Соответствунно получаем объект User.
            if (resultSet.next()) {
                user = userFindRowMapper.mapRow(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    public void save(User model) {
        //Создаём новый объект PreparedStatement,с соотвествующим запросом для сохранния пользователя
        //Использование try-with-resources необходимо для гарантированного закрытия statement,вне зависимости от успешности операции.
        //Аргумент Statement.RETURN_GENERATED_KEYS даёт возможность хранения сгенерированных id (ключей)  внутри statement.
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (name, password, nickname, email, birthday, registration_date, posts) VALUES (?,?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1,model.getName());
            statement.setString(2,model.getPassword());
            statement.setString(3,model.getNick());
            statement.setString(4,model.getEmail());
            statement.setObject(5,model.getBirth_date());
            statement.setObject(6, LocalDateTime.now());
            statement.setLong(7,model.getCountPosts());
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
}
