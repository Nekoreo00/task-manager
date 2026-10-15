import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String DB_DIRECTORY =
        System.getProperty("user.home")
        + "/.taskmanager-data";

    private static final String DB_PATH =
        DB_DIRECTORY + "/tasks.db";

    private static final String URL =
        "jdbc:sqlite:" + DB_PATH;

    private static Connection getConnection()
        throws SQLException {
        return DriverManager.getConnection(URL);
    }


    public static void initialize() {

        System.out.println("initialize開始");

        new java.io.File(DB_DIRECTORY).mkdirs();

        String sql =
            """
            CREATE TABLE IF NOT EXISTS tasks (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                month TEXT NOT NULL,
                day TEXT NOT NULL,
                hour TEXT NOT NULL,
                minute TEXT NOT NULL,
                note TEXT,
                completed INTEGER NOT NULL
            )
            """;

        try (
            Connection connection =
                getConnection();

            Statement statement =
                connection.createStatement()
        ) {

            System.out.println("DB接続成功");

            statement.execute(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public static int insertTask(
    Task task
) {

    String sql =
        """
        INSERT INTO tasks (
            month,
            day,
            hour,
            minute,
            note,
            completed
        )
        VALUES (?, ?, ?, ?, ?, ?)
        """;

    try (
        Connection connection =
            getConnection();

        PreparedStatement statement =
            connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            )
    ) {

        statement.setString(
            1,
            task.getMonth()
        );

        statement.setString(
            2,
            task.getDay()
        );

        statement.setString(
            3,
            task.getHour()
        );

        statement.setString(
            4,
            task.getMinute()
        );

        statement.setString(
            5,
            task.getNote()
        );

        statement.setInt(
            6,
            task.isCompleted() ? 1 : 0
        );

        statement.executeUpdate();

        ResultSet keys =
            statement.getGeneratedKeys();

        if (keys.next()) {
            return keys.getInt(1);
        }

    } catch (SQLException e) {

        e.printStackTrace();
    }

    return -1;
}

    public static void updateTask(
    Task task
) {

    String sql =
        """
        UPDATE tasks
        SET
            month = ?,
            day = ?,
            hour = ?,
            minute = ?,
            note = ?
        WHERE id = ?
        """;

    try (
        Connection connection =
            getConnection();

        PreparedStatement statement =
            connection.prepareStatement(sql)
    ) {

        statement.setString(
            1,
            task.getMonth()
        );

        statement.setString(
            2,
            task.getDay()
        );

        statement.setString(
            3,
            task.getHour()
        );

        statement.setString(
            4,
            task.getMinute()
        );

        statement.setString(
            5,
            task.getNote()
        );

        statement.setInt(
            6,
            task.getId()
        );

        statement.executeUpdate();

    } catch (SQLException e) {

        e.printStackTrace();
    }
}

    public static void updateCompleted(
        Task task
    ) {

        String sql =
            """
            UPDATE tasks
            SET completed = ?
            WHERE id = ?
            """;

        try (
            Connection connection =
                getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(
                1,
                task.isCompleted() ? 1 : 0
            );

            statement.setInt(
                2,
                task.getId()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public static void deleteTask(
        Task task
    ) {

        String sql =
            """
            DELETE FROM tasks
            WHERE id = ?
            """;

        try (
            Connection connection =
                getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(
                1,
                task.getId()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
public static List<Task> loadTasks() {

    List<Task> tasks =
        new ArrayList<>();

    String sql =
        """
        SELECT *
        FROM tasks
        ORDER BY id
        """;

    try (
        Connection connection =
            getConnection();

        PreparedStatement statement =
            connection.prepareStatement(sql);

        ResultSet resultSet =
            statement.executeQuery()
    ) {

        while (
            resultSet.next()
        ) {

            int id =
                resultSet.getInt("id");

            String month =
                resultSet.getString("month");

            String day =
                resultSet.getString("day");

            String hour =
                resultSet.getString("hour");

            String minute =
                resultSet.getString("minute");

            String note =
                resultSet.getString("note");

            boolean completed =
                resultSet.getInt("completed") == 1;


            Task task =
                new Task(
                    id,
                    month,
                    day,
                    hour,
                    minute,
                    note,
                    completed
                );


            tasks.add(task);
        }

    } catch (SQLException e) {

        e.printStackTrace();
    }

    return tasks;
}

}

