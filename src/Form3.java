import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class Form3 extends JFrame {

        Connect con = new Connect();
        ResultSet rs, rss;
        private static int count;
        private static int answer_count;
        private JLabel lab1;
        private JButton bot1;
        private JButton bot2;
        private String pass2 = "java";
        private String word = null;
        private String s1 = null;
        private String s2 = null;
        private String s3 = null;
        private String s4 = null;
        private int m1 = 0;
        private int m2 = 0;
        private int m3 = 0;
        private int m4 = 0;
        JRadioButton bn1, bn2, bn3, bn4;
        ButtonGroup group;
        JPanel pnB;
        ArrayList<Questions> l = null;
    //Массив для вопросов. Заполняется один раз в конструкторе окна.
        ArrayList<Integer> b = null; //Вспомогательный массив.
        // Каждый раз при нажатии на кнопку "Следующий" заполняется новыми элементами.
        private boolean t, m;
        ArrayList<String> a = null;
        ArrayList<Integer> a2 = null;
        JPasswordField pass;

    public Form3() {
        super("Окно для тестирования");
        con.getConnection_DB();
        //При открытии (создании) нового окна массивы очищаем и счетчики обнуляем.
        l = new ArrayList<>();
        b = new ArrayList<>();
        count = 0;
        answer_count = 0;

        try {
            rs = con.select();
            while (rs.next()) {
                String sss = (String) rs.getString("quest");
                Integer it = (Integer) rs.getInt("id");
                l.add(new Questions(it, sss));
            }
        } catch (SQLException ex) {
        }

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setMaximumSize(new Dimension(100, 300));
        //  setLocationRelativeTo(null);
        // Будем группировать переключатели при помощи объекта new ButtonGroup(). Сылкой на это объект является group.
        group = new ButtonGroup();
        //Создаем первый переключатель с ссылкой на него(bn1). Задаем ему слушателя. Устанавливаем поведение - невидимость - bn1.setVisible(false).
        bn1 = new JRadioButton(s1);
        bn1.addItemListener(new AnswOk());
        bn1.setVisible(false);
        bn2 = new JRadioButton(s2);
        bn2.addItemListener(new AnswOk());
        bn2.setVisible(false);
        bn3 = new JRadioButton(s3);
        bn3.addItemListener(new AnswOk());
        bn3.setVisible(false);
        bn4 = new JRadioButton(s4);
        bn4.addItemListener(new AnswOk());
        bn4.setVisible(false);
        group.add(bn1);  //Добавляем переключатели в группу.
        group.add(bn2);
        group.add(bn3);
        group.add(bn4);
//Получаем панель getContentPane() и добавляем на него Метку.
        add(new JLabel(), BorderLayout.NORTH);
        //Панель для вопроса-метки и переключателей.
        pnB = new JPanel(new GridLayout(0, 1, 0, 5)); //Надо подробно разобрать
        pnB.setSize(300, 100);

        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        pnB.setBorder(border);

        lab1 = new JLabel("Вы готовы начать тестирование?");
        lab1.setHorizontalAlignment(SwingConstants.CENTER); //Метку расположили в центре по горизонтали.
        lab1.setMaximumSize(new Dimension(290, 10));
        lab1.setMinimumSize(new Dimension(280, 5));
        pnB.add(lab1);
        pnB.add(bn1);
        pnB.add(bn2);
        pnB.add(bn3);
        pnB.add(bn4);
        add(pnB, BorderLayout.CENTER);
//Панель для кнопок.
        JPanel panelButton = new JPanel();
        panelButton.setLayout(new GridLayout(1, 3));
        bot1 = new JButton("Начать");
        bot1.addActionListener(new Action_add());
        bot1.setActionCommand("Следующий");
        bot2 = new JButton("Все вопросы");
        bot2.addActionListener(new Action_add());

        panelButton.add(bot1);
        panelButton.add(bot2);
        add(panelButton, BorderLayout.SOUTH);
        setVisible(true);
    }
//======= Получаем диалоговое окно с приглашением ввести пароль. Метод JOptionPane.showOptionDialog
// вовращает результат нажатия на одну из кнопок "OK", "Отмена", то есть константу (0 или 1)
public int getPane() {
    pass = new JPasswordField(10);
    JPanel panel = new JPanel();
    JLabel label = new JLabel("Введите пароль");
    panel.add(label);
    panel.add(pass);
    String[] options = new String[]{"OK", "Отмена"};
    return JOptionPane.showOptionDialog(
            null,
            panel, //
            "Инициализация",  //
            JOptionPane.NO_OPTION, //
            JOptionPane.PLAIN_MESSAGE, //
            null, //
            options, //optionType - Определяет набор кнопок опций, которые появляются в нижней части диалогового окна:
            options[0]); //По умолчанию 'выделенная' кнопка
}

    //====== Метод getPassword() возвращает нам введенное пользователем слово преобразованное в тип String
    public String getPassword() {
        char[] password = pass.getPassword();
        word = new String(password);
        System.out.println("Your password is: " + word);
        return word;
    }

    //=============================================================================
    //Класс - слушатель.
    class Action_add implements ActionListener {
        //Слушатель для кнопок. Используем метод getActionCommand() для получения команды действия.
        // Этот метод возвращает String надпись на кнопке (или установленный нами текст).

        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals("Все вопросы")) {

                for(int i = 1; i>0 ;) // Создаем искусственный цикл, чтоб можно было воспользоваться оператором
                    // перехода continue, который перекидывает нас опять на for(int i = 1;....).
                    // Так получаем возможность снова вводить пароль и проверять его на совпадение.
                {
                   if (getPane()==0) {  //Если была нажата кнопка ОК
                        if (pass2.equals(getPassword())) {    // Если есть совпадение, то
                            new Form2();                     // открываем новое окно Form2()
                            dispose();                       // Закрываем текущее окно и освобождаем ресурсы
                            i--;    //Уменьшаем i на единицу, чтобы прервать цикл for(int i = 1;....).
                        }
                            else {    //Если не было совпадения паролей, показываем уведомление для совершения очередной попытки
                            JOptionPane.showMessageDialog(null, "Неверный пароль. Попробуйте снова.");
                            continue;   //Переходим снова к for(int i = 1;....) и начинаем проверку сначало
                        }
                    }
                    else {
                        i--;
                        return;
                     }
                }
            }


                    if (e.getActionCommand().equals("Следующий")) {
                        group.clearSelection(); //Метод clearSelection() доступен с  Java 6.
                        // Очищает выбор так, что ни одна из кнопок в ButtonGroup становится невыбранной.
                        a = new ArrayList<>(); //Массив для вопросов.
                        a2 = new ArrayList<>();  //Массив для вариантов ответов.
                        System.out.println("Пока правильных ответов:  " + answer_count); //Временная проверка.
                        //count - счетчик вопросов. Используем его как индекс из массива "l" - список вопросов из таблицы Questions.

                        if (count < l.size()) {
                            lab1.setText(l.get(count).getQuest());
//Пытаемся получить варианты ответов по указанному ID вопроса из массива вопросов l.
                            try {
                                rss = con.selectt(l.get(count).getId());
                                while (rss.next()) {
                                    String s = rss.getString("answer");
                                    int x = rss.getInt("checkB");
                                    a.add(s);
                                    a2.add(x);
                                }
                            } catch (SQLException ex) {
                                System.out.println("Ох, и что тут творится с ответами   " + ex);
                            }

                            b.clear(); //Предварительно очищаем массив от предыдущих ответов.
                            for (int x : a2) {
                                b.add(x);
                            }
                            if (a.size() <= 4) {
                                bn1.setText(a.get(0));
                                bn1.setVisible(true);

                                bn2.setText(a.get(1));
                                bn2.setVisible(true);

                                bn3.setText(a.get(2));
                                bn3.setVisible(true);

                                if (a.size() > 3) {
                                    bn4.setText(a.get(3));
                                    bn4.setVisible(true);
                                } else {
                                    bn4.setVisible(false);
                                }
                            }
                        }
                        //Пока не сделан выбор варианта ответа, кнопка "Следующий" неактивна.
                        if (!bn1.isSelected() && !bn1.isSelected() && !bn1.isSelected() && !bn1.isSelected()) {
                            bot1.setEnabled(false);
                        }

                        bot1.setText("Следующий");
                        count++;
                    }

                    //Блок отвечающий за завершение тестирования.=====================================
                    if (count > 0 && count >= l.size() + 1) {
                        lab1.setText("  ");
                        bn1.setVisible(false);
                        bn2.setVisible(false);
                        bn3.setVisible(false);
                        bn4.setVisible(false);
                        bot1.setEnabled(false);
                        a = null;
                        a2 = null;
                        float t;
                        t = (float) 100 * answer_count / l.size();

                        JOptionPane.showMessageDialog(null, "Количество правильных ответов: " + answer_count
                                + " из " + l.size() + "\n Ваша оценка:  " + rating(t));
                        return;
                    }
                }
            }
            //=================================================================================
            //Класс - слушатель
            public class AnswOk implements ItemListener {
                public void itemStateChanged(ItemEvent e) {
                    if (bn1.isSelected()) {
                        m1 = 1;
                        System.out.println(b.get(0) + " проверка 1-го варианта");
                        if (b.get(0) == 1 && m1 == 1) {
                            System.out.println(b.get(0));
                            answer_count++;
                        }
                        bot1.setEnabled(true);
                    }
                    if (bn2.isSelected()) {
                        m2 = 1;
                        System.out.println(b.get(1) + " проверка 2-го варианта");
                        if (1 == b.get(1) && 1 == m2) {
                            System.out.println(b.get(1));
                            answer_count++;
                        }
                        bot1.setEnabled(true);
                    }
                    if (bn3.isSelected()) {
                        m3 = 1;
                        System.out.println(b.get(2) + " проверка 3-го варианта");
                        if (b.get(2) == m3) {
                            System.out.println(b.get(2));
                            answer_count++;
                        }
                        bot1.setEnabled(true);
                    }
                    if (bn4.isSelected()) {
                        m4 = 1;
                        System.out.println(b.get(3) + " проверка 4-го варианта");
                        if (m4 == b.get(3)) {
                            System.out.println(b.get(3));
                            answer_count++;
                        }
                        bot1.setEnabled(true);
                    }
                }
            }
            //========== Метод для получения оценки ======

        public int rating(double d) {
            if (d > 85) {
                return 5;
            }
            if (70 < d && d < 86) {
                return 4;
            }
            if (55 < d && d < 71) {
                return 3;
            } else return 2;
        }

        //=====
    }





