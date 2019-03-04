import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Form2 extends JFrame {

    Form1 f_1;
    Connect ct;
    Model dell;
    JList textList;
    JScrollPane sp;
    String str3;

    public Form2() {
        super("Окно вопросов");
        ct = new Connect();
        ct.getConnection_DB();

        dell = new Model();
        dell.getDataSource(ct.select(), "quest");
        //dell.getDataSource((ct.select("quest", "answer", "questions", "answers", "questions.id=answers.id_quest")),"quest", "answer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLUE);
        setSize(750, 250);
        setLocationRelativeTo(null); //Окно в центре экрана
        setVisible(true);

        textList = new JList(dell);
        textList.setVisibleRowCount(8);
        textList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        textList.setSelectedIndex(0);
        textList.addMouseListener(new MouseAdapter() {
                                      public void mouseClicked(MouseEvent e) {
                                          if (e.getClickCount() == 2) {//двойной шелчок по элементу списка
                                            new FormEdit(str3);
                                            dispose();
                                          }
                                      }
                                  });

        sp = new JScrollPane(textList);
        JButton b1 = new JButton("Delete");
        JButton b2 = new JButton("Add");
        JButton b3 = new JButton("Testing");
        JButton b4 = new JButton("Edit");

        b1.addActionListener(new Action_add());
        b2.addActionListener(new Action_add());
        b3.addActionListener(new Action_add());
        b4.addActionListener(new Action_add());
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1, 1));
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        Border brd = BorderFactory.createEtchedBorder();
        p2.setBorder(brd);
        p2.add(p);


        Border border = BorderFactory.createEtchedBorder();
        Border title = BorderFactory.createTitledBorder(border, "Перечень вопросов:");
        sp.setBorder(title);
        Container c = getContentPane();
        Box box = Box.createVerticalBox();
        box.add(sp);
        box.add(p2);
        c.add(box);
        }
    //----------------------------------------------------
    void setedVisible(JFrame frr, boolean val) {frr.setVisible(val);}
    //==========================================================================================
//        class Action_select implements ListSelectionListener {
//            public void valueChanged(ListSelectionEvent il) {
//                if (!il.getValueIsAdjusting()){ //Нужен ли метод здесь или нет, надо разобраться.
//
//        }
//--------------------------------------------------------------------
    class Action_add implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Delete")) {
                String stt = (String) (textList.getSelectedValue());
                ct.delete_(stt);
                dell.removeValueAt(stt);
                ct.deleteAns();
                //После удаления элемента из списка, мне понадобилось указать "виду-View", что теперь
                // индекс выбранного(выделенного цветом) элемента стал меньше на 1-цу. Иначе лезла ошибка.
                textList.setSelectedIndex(dell.getSize() - 1);
                textList.ensureIndexIsVisible(dell.getSize() - 1);
            }
            if (e.getActionCommand().equals("Add")) {
                f_1 = new Form1();
                f_1.setVisible(true);
                dispose();  //Окно Form2() закрывается и убирается сборщиком мусора.
            }
            if (e.getActionCommand().equals("Testing")) {
                new Form3();
                dispose();  //Окно Form2() закрывается и убирается сборщиком мусора.

            }
            if (e.getActionCommand().equals("Edit")) {
                String string = (String) (textList.getSelectedValue());
                str3 = string;
                new FormEdit(str3);
                dispose();
            }
        }
    }
}