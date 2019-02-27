import java.sql.*;

public class Connect {
   private Connection co;
    private ResultSet rs, rss;
    private Statement state;
    int m, n, t;
//-----------------------------------------------------------------------------------------------------------------
public Connect(){}
//Метод для получения соединения с БД.
    public Connection getConnection_DB() {
        try {
            Class.forName("org.sqlite.JDBC");
            co = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Admin\\Documents\\Java_Ex\\sqLite\\Test_1.db");
            System.out.println("Все отлично! Мы подключились к БД");
        } catch (ClassNotFoundException ex) {
            System.out.println("Не удалось соединиться с базой: " + ex.getMessage());
        } catch (Exception e) {
            System.out.println("Что-то не так, надо разобраться! " + e.getMessage());
        }
        return co;
    }

//-----------------------------------------------------------------------------------------------------------
        void close() {
        try {
            co.close();
        } catch (SQLException e) {
            System.out.println("Не закрыли" + e.getMessage());
        }
    }
//------------------------------------------------------------------------------
    //Метод возвращает выборку из даблицы questions.
    public ResultSet select() {
        try {
            String query = "SELECT * FROM questions";
            state = co.createStatement();
            rs = state.executeQuery(query);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return rs;
    }
    //==============
    public ResultSet selectt(int id) {
        try {
            String query = "SELECT * FROM answers WHERE id_quest= '" + id + "'";
            state = co.createStatement();
            rss = state.executeQuery(query);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return rss;
    }
    //-----------------------------------------------------------------------------------------------
    public ResultSet select(String name_1, String name_2, String table_1, String table_2, String condition) {
        try {
            String query = "SELECT " + name_1 + "," + name_2 + " FROM " + table_1  + "," + table_2 + " WHERE " + condition ;
            state = co.createStatement();
            rs = state.executeQuery(query);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return rs;
    }
//===========================================================================
    //Метод для обновления ...

    public void update(String s1, String s2) {
        //   getConnection_DB(); //Получили соединение
        try {
            String query = "UPDATE questions \n" +

                    "SET quest= '" + s1 + "'  WHERE quest= '" + s2 + "'";
            state = co.createStatement();
            state.executeUpdate(query);
            // close_db(); // Закрыли соединение
        }catch (SQLException ex)
        { System.out.println("Error in update " + ex);}}
//---------------------------------------------------------------------------------
    //Метод удаляет из таблицы questions указаную строку (запись) deleteStr.
        synchronized void delete_(String deleteStr) {
        System.out.println(deleteStr);
        try {
            System.out.println("Запрос на удаление:" + "'" + deleteStr + "'  ");
            String query = "DELETE FROM questions WHERE quest = '" + deleteStr + "'";
           Statement statement = co.createStatement();
            statement.executeUpdate(query);

           System.out.println("Запрос на удаление выполнен!! Ура товарищи: " + deleteStr);
        } catch (SQLException e) {
            System.out.println("Не удалось удалить запись из БД! " + e);
       }
    }
//===========================================
//Метод удаляет из таблицы questions указаную строку (запись) deleteStr.
    //SQLite, по-видимому, не поддерживает объединения (JOIN) с инструкцией delete, как вы можете видеть на диаграммах
// синтаксиса (https://www.sqlite.org/syntaxdiagrams.html#delete-stmt).
    // Однако вы должны использовать подзапрос, чтобы удалить то что нужно.
    //В нашем случае подзапрос: (SELECT answers.id_quest FROM answers LEFT JOIN questions ON questions.id=answers.id_quest WHERE quest IS NULL).
synchronized void deleteAns() {
        try {
        System.out.println("Удаляем ответы по удаленному вопросу" );
        String query = "DELETE FROM answers WHERE answers.id_quest IN \n" +
                "(SELECT answers.id_quest FROM answers LEFT JOIN questions ON questions.id=answers.id_quest WHERE quest IS NULL)";
        Statement statement = co.createStatement();
        statement.executeUpdate(query);
        System.out.println("Запрос на удаление ответов выполнен!!");

    } catch (SQLException e) {
        System.out.println("Увы! Не удалось удалить ответы! " + e);
    }
}

    //-----------------------------------------------------------------------------------------------------
    //Метод добавляет новую запись (вопрос) в таблицу questions  возвращает какое-то число???.
    int insertQu(String strs) {
        try {
            String query = "INSERT INTO questions (quest) \n" +
                    "VALUES ('" + strs + "')";
            Statement statement = co.createStatement();
             m = statement.executeUpdate(query);
            System.out.println(m);
        } catch (SQLException e) {
            System.out.println("Не удалось добавить в БД новую запись! " + e);
        }
        return m;
            }

//------------------------------------------------------------------------------------------
    //Метод получает (выборка) из таблицы questions номер id по указанной строке(st) и ничего не возвращает.
    public void selectId(String st) {
        try {
        String query = "SELECT id FROM questions WHERE quest = '" + st + "'" ;
        state = co.createStatement();
        rs = state.executeQuery(query);
        while (rs.next())
        {int k = rs.getInt(1);
        this.n=k;
            System.out.println(k); // Проверка
            }
    } catch (SQLException e) {System.out.println(e); }
   }
//===========================================================================
public int selectIdd(String st) {
    try {
        String query = "SELECT id FROM questions WHERE quest = '" + st + "'";
        state = co.createStatement();
        rs = state.executeQuery(query);
        while (rs.next()) {
            t = rs.getInt(1);
                    }
    }
    catch (SQLException e) {System.out.println(e); }
    return t;
}
    //Метод insertAnswer добавляет новую строку в таблицу answers в поля answer, id_quest (- внешний ключ) и checkB (- чекбокса)
// и ничего не возвращает.
    void insertAnswer(String st1, int st2, int st3) {
    try {
        String query = "INSERT INTO answers (answer, id_quest, checkB) \n" +
                "VALUES ('" + st1 + "'" + "," + "'" + st2 + "'" + "," + "'" + st3 + "')";
        Statement statement = co.createStatement();
        System.out.println(st1 + "   " +st2 + " --- " + st3);
        statement.executeUpdate(query);
    } catch (SQLException e) {
        System.out.println("Ой, ну надо же, не удалось добавить ответы! " + e);
    }
}
}