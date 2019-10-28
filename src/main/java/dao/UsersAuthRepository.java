package dao;

import model.User;
import model.UserAuthParametr;
import org.springframework.security.core.parameters.P;
import singletone.ConnectionService;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class UsersAuthRepository {
    private Connection connection;

    public UsersAuthRepository() {
        this.connection = ConnectionService.getConnection();
    }
    private RowMapper<UserAuthParametr> authCookieRowMapper = row -> {
        String parametr = row.getString("parametr");
        Long ownerid = row.getLong(3);
        return new UserAuthParametr(parametr,ownerid);
    };
    public void delete(Long id) {
        //id не может быть меньше 0(в противном случае выбрасываем исключение).
        if (id < 0L) throw new IllegalArgumentException();
        /* Мы выполняем sql-запрос, удаляя строку из таблицы по параметру id. */
        //Создаём новый объект Statement
        //Использование try-with-resources необходимо для гарантированного закрытия statement,вне зависимости от успешности операции.
        try (Statement statement = connection.createStatement()) {
            //Выолняем запрос и получаем колличество изменённых строк
            int updRows = statement.executeUpdate("DELETE from usersauthcookies where ownerid = " + id + ";");
            if (updRows == 0) {
                //Если ничего не было изменено, значит возникла ошибка
                //Возбуждаем соответсвующее исключений
                throw new SQLException();
            }
        } catch (SQLException e) {
            //Если удаление провалилось, обернём пойманное исключение в непроверяемое и пробросим дальше(best-practise)
            throw new IllegalStateException(e);
        }
    }
    public Optional<UserAuthParametr> findById(Long id) {
        UserAuthParametr user = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM usersauthcookies WHERE ownerid = ?")){
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            //Если соответстующая строка найдена,обрабатываем её c помощью userRowMapper.
            //Соответствунно получаем объект User.
            if (resultSet.next()) {
                user = authCookieRowMapper.mapRow(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }
    public Optional<UserAuthParametr> findByPatametr(String parametr) {
        UserAuthParametr user = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM usersauthcookies WHERE parametr = ? ")){
            statement.setString(1,parametr);
            ResultSet resultSet = statement.executeQuery();
            //Если соответстующая строка найдена,обрабатываем её c помощью userRowMapper.
            //Соответствунно получаем объект User.
            if (resultSet.next()) {
                user = authCookieRowMapper.mapRow(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }
    public void save(UserAuthParametr model) {
        //Создаём новый объект PreparedStatement,с соотвествующим запросом для сохранния пользователя
        //Использование try-with-resources необходимо для гарантированного закрытия statement,вне зависимости от успешности операции.
        //Аргумент Statement.RETURN_GENERATED_KEYS даёт возможность хранения сгенерированных id (ключей)  внутри statement.
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO usersauthcookies (parametr, ownerid) VALUES (?,?) ",
                Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1,model.getParametr());
            statement.setLong(2,model.getOwnerId());
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
    //language=SQL
}
