package dao;

import dao.interfaces.CommentDao;
import dao.interfaces.RowMapper;
import model.Comment;
import model.Post;
import model.User;
import singletone.ConnectionService;

import javax.ws.rs.container.ConnectionCallback;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentDaoImpl implements CommentDao {
    private Connection connection;

    public CommentDaoImpl() {
        this.connection = ConnectionService.getConnection();
    }


    @Override
    public Optional<Comment> find(Long id) {
        Comment comment = null;
        //Создаём новый объект Statement
        //Использование try-with-resources необходимо для арантированного закрытия statement,
        // вне зависимости от успешности операции.
        String SQL_FIND_BY_ID = "select * from comment WHERE id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                comment = commentRowMapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            //Если операция провалилась, обернём пойманное исключение в непроверяемое и пробросим дальше(best-practise)
            throw new IllegalStateException(e);
        }
        //Возвращаем полученный в результате операции ArrayList
        return Optional.of(comment);
    }

    @Override
    public void save(Comment model) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO comment(text, owner_id, post_id, date) " +
                        "VALUES (?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, model.getText());
            statement.setLong(2, model.getOwnerId());
            statement.setLong(3, model.getPostId());
            statement.setObject(4, model.getDate());
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
    //language=sql
    public static final String SQLUpdate = "UPDATE comment SET text = ?, owner_id = ?, post_id = ?, date = ?  WHERE id = ?";
    @Override
    public void update(Comment model) {
        //Создаём новый объект PreparedStatement,с соотвествующим запросом для обновления информации у конкретного пользователя
        //Использование try-with-resources необходимо для гарантированного закрытия statement,вне зависимости от успешности операции.
        try (PreparedStatement statement = connection.prepareStatement(SQLUpdate)) {
            //На место соответвующих вопросительных знаков уставнавливаем параметры модели, которую мы хотим обновить
            statement.setString(1, model.getText());
            statement.setLong(2, model.getOwnerId());
            statement.setLong(3, model.getPostId());
            statement.setObject(4, model.getDate());
            statement.setLong(5, model.getId());
            //Выполняем запрос и сохраняем колличество изменённых строк
            int updRows = statement.executeUpdate();

            if (updRows == 0) {
                //Если ничего не было изменено, значит возникла ошибка
                //Возбуждаем соответсвующее исключений
                throw new SQLException();
            }
        } catch (SQLException e) {
            //Если обноление провалилось, обернём пойманное исключение в непроверяемое и пробросим дальше(best-practise)
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public List<Comment> findAllByOwnerId(Long id) {
        List<Comment> result = new ArrayList<>();

        //Создаём новый объект Statement
        //Использование try-with-resources необходимо для арантированного закрытия statement,
        // вне зависимости от успешности операции.
        String SQL_FIND_BY_OWNER_ID = "select * from comment WHERE owner_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_OWNER_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Comment comment = commentRowMapper.mapRow(resultSet);
                result.add(comment);
            }
        } catch (SQLException e) {
            //Если операция провалилась, обернём пойманное исключение в непроверяемое и пробросим дальше(best-practise)
            throw new IllegalStateException(e);
        }
        //Возвращаем полученный в результате операции ArrayList
        return result;
    }

    private RowMapper<Comment> commentRowMapper = row -> {
        Long id = row.getLong("id");
        String text = row.getString("text");
        Long ownerId = row.getLong("owner_id");
        Long postId = row.getLong("post_id");
        LocalDateTime date = row.getObject(5, LocalDateTime.class);
        return new Comment(id, text, ownerId,postId, date);
    };

    @Override
    public List<Comment> findAllByPostId(Long id) {
        List<Comment> result = new ArrayList<>();

        //Создаём новый объект Statement
        //Использование try-with-resources необходимо для арантированного закрытия statement,
        // вне зависимости от успешности операции.
        String SQL_FIND_BY_POST_ID = "select * from comment WHERE post_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_POST_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Comment comment = commentRowMapper.mapRow(resultSet);
                result.add(comment);
            }
        } catch (SQLException e) {
            //Если операция провалилась, обернём пойманное исключение в непроверяемое и пробросим дальше(best-practise)
            throw new IllegalStateException(e);
        }
        //Возвращаем полученный в результате операции ArrayList
        return result;
    }
}
