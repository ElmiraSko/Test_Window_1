import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Form1 extends JFrame {
    Connect con = new Connect();
    private JLabel labText;
    private JTextField textFil;
    private JTextField ans1, ans2, ans3, ans4;
    private JButton bot1;
    private JButton bot2;
    private JButton bot3;
    private String str;
    private JCheckBox ch1, ch2, ch3, ch4;
    protected int it1, it2, it3, it4;
    Form2 f;

    Form1() {
        super("Окно для ввода вопроса");
        con.getConnection_DB();
        setLayout(new GridLayout(1, 1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        //  setLocationRelativeTo(null);

        labText = new JLabel("Вопрос:");  //Для получения дополнительного пространства
        textFil = new JTextField(70);
        textFil.setActionCommand("Add");
        textFil.addActionListener(new Action_add());

        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout());
        pane.add(labText);
        pane.add(textFil);
//Создаем панель pan1 на которой разместим метку lab1, текстовое поле для ответа - ans1 и чекбокс - ch1. На ch1 устанав слушателя.
        JLabel lab1 = new JLabel("Ответ А");
        ans1 = new JTextField(30);
        JPanel pan1 = new JPanel();
        pan1.setLayout(new FlowLayout());
        ch1 = new JCheckBox();
        ch1.addItemListener(new Check());
        pan1.add(lab1);
        pan1.add(ans1);
        pan1.add(ch1);

        JLabel lab2 = new JLabel("Ответ В");
        ans2 = new JTextField(30);
        JPanel pan2 = new JPanel();
        pan2.setLayout(new FlowLayout());
        ch2 = new JCheckBox();
        ch2.addItemListener(new Check());
        pan2.add(lab2);
        pan2.add(ans2);
        pan2.add(ch2);

        JLabel lab3 = new JLabel("Ответ C");
        ans3 = new JTextField(30);
        JPanel pan3 = new JPanel();
        pan3.setLayout(new FlowLayout());
        ch3 = new JCheckBox();
        ch3.addItemListener(new Check());
        pan3.add(lab3);
        pan3.add(ans3);
        pan3.add(ch3);

        JLabel lab4 = new JLabel("Ответ D");
        ans4 = new JTextField(30);
        JPanel pan4 = new JPanel();
        pan4.setLayout(new FlowLayout());
        ch4 = new JCheckBox();
        ch4.addItemListener(new Check());
        pan4.add(lab4);
        pan4.add(ans4);
        pan4.add(ch4);
//Кнопки управления.
        bot1 = new JButton("Add");
        bot2 = new JButton("Clean");
        bot3 = new JButton("All questions");
        bot1.addActionListener(new Action_add());
        bot2.addActionListener(new Action_add());
        bot3.addActionListener(new Action_add());
//Нашла в интернете пример, как скомпоновать кнопки так, чтоб они имели
// одинаковый размер не смотря на различную длину надписи.
        JPanel p1 = new JPanel(new GridLayout(1, 1));
        p1.add(bot1);
        p1.add(bot2);
        p1.add(bot3);
        JPanel p2 = new JPanel(new FlowLayout());
        p2.add(p1);
        Border border = BorderFactory.createEtchedBorder();
        //Border title = BorderFactory.createTitledBorder(border, "Xa xa xa");

        //Менеджер BoxLayout размещает элементы на панели в строку или в столбец.
        // Обычно для работы с этим менеджером используют вспомогательный класс Box,
        // представляющий собой панель, для которой уже настроено блочное размещение.
        // Создается такая панель не конструктором, а одним из двух статических методов,
        // определенных в классе Box: createHorizontalBox() и createVerticalBox().

        Box box = Box.createVerticalBox();
        box.setBorder(border);
        box.add(Box.createVerticalStrut(20)); //Создает "распорку" (пустое пространство) перед pane.
        box.add(pane);
        box.add(Box.createVerticalStrut(10)); //Создает "распорку" (пространство) после pane.
        box.add(pan1);
        box.add(pan2);
        box.add(pan3);
        box.add(pan4);
        box.add(Box.createVerticalStrut(20));
        box.add(p2);
        box.add(Box.createVerticalStrut(30));
        getContentPane().add(box);
        pack();
        setVisible(true);
        System.out.printf("%s     ... \n", Thread.currentThread().getName());
    }

    //=======================================================
    class Check implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
//Создали вспомогательные переменные типа int. Им в зависимости от выбора чекбокса устанавливаем значения 1 или 0.
            if (ch1.isSelected()){it1 = 1;}
            else {it1= 0;}
            if (ch2.isSelected()){it2 = 1;}
            else {it2= 0;}
            if (ch3.isSelected()){it3 = 1;}
            else {it3= 0;}
            if (ch4.isSelected()){it4 = 1;}
            else {it4= 0;}
        }
    }
    //===============================
    class Action_add implements ActionListener {
        //Слушатель для кнопок. Используем метод getActionCommand() для получения команды действия.
        // Этот метод возвращает String надпись на кнопке (или установленный нами текст).
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Add")) {
//В переменную str присваеваем "текст" из поля для вопросов.
                str = textFil.getText();
                String t1 = ans1.getText(); //"текст" ответа А присваиваем переменной t1.
                String t2 = ans2.getText();
                String t3 = ans3.getText();
                String t4 = ans4.getText();
//Можно вносить как 3 варианта ответов, так и 4-е. Регулируется условием  if (t4.trim().length() > 0)  ...
                if (str.trim().length() > 0) {
                    if (t1.trim().length() > 0 && t2.trim().length() > 0 && t3.trim().length() > 0) {
                        boolean z = true;
                        if (ch1.isSelected()==z || ch2.isSelected() == z || ch3.isSelected() == z || ch4.isSelected() == z) {
                        con.insertQu(str); //Записываем в таблицу новый вопрос.
                        System.out.println("Запрос выполнился...." + str); //Временно для проверки
                        con.selectId(str); //Получаем id записанного вопроса.
                        System.out.println("id вопроса получили...." + con.n + "  " + str); //Временно для проверки
//Записываем в таблицу -Ответов варианты ответов для полученного id.

                            con.insertAnswer(t1, con.n, it1);
                            con.insertAnswer(t2, con.n, it2);
                            con.insertAnswer(t3, con.n, it3);
                            if (t4.trim().length() > 0) {
                                con.insertAnswer(t4, con.n, it4);
                            }
                            clean();
                        }else {JOptionPane.showMessageDialog(null, "<html>Пожалуйста, отметьте <br>правильный вариант ответа!");}
                    } else {JOptionPane.showMessageDialog(null, "Пожалуйста, наберите ответы полностью!"); }
                } else { JOptionPane.showMessageDialog(null, "Пожалуйста, наберите вопрос!!!"); }
            }
            //Метод trim() — возвращает копию строки с пропущенными начальными и конечными пробелами,
// другими словами метод позволяет в Java удалить пробелы в начале и конце строки.
            if (e.getActionCommand().equals("Clean")) {
                textFil.setText("");
                ans1.setText("");
                ans2.setText("");
                ans3.setText("");
                ans4.setText("");
                ch1.setSelected(false);
                ch2.setSelected(false);
                ch3.setSelected(false);
                ch4.setSelected(false);
            }

            if (e.getActionCommand().equals("All questions")) {
                try {
                    f = new Form2();
                    f.setVisible(true);
                    dispose();
                } catch (java.lang.Exception ex) {
                    System.out.println(ex);
                }
            }
        }
    }
    //Метод clean() очищает поля ввода. Используем его в слушателе.
    public  void clean(){
        textFil.setText("");
        ans1.setText("");
        ans2.setText("");
        ans3.setText("");
        ans4.setText("");
        ch1.setSelected(false);
        ch2.setSelected(false);
        ch3.setSelected(false);
        ch4.setSelected(false);
    }
}