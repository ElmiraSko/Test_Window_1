import javax.swing.*;

   public class Main_2 {

       public static void main(String[] arg){
                SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    System.out.printf("%s В методе run в главном потоке... \n", Thread.currentThread().getName());
                    new Form3();

                }
            });
           System.out.println("Поток завершен  " + Thread.currentThread().getName());
        }
    }


