import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Model extends AbstractListModel {
   private ArrayList <String> data = new ArrayList<>();

   public  void  getDataSource (ResultSet res, String column) {
           try {
               data.clear();
            while (res.next()) {
                synchronized (data){
                   String s1 = res.getString(column);
                   data.add(s1);}
                System.out.println("размер списка!!!" + data.size());
                  // fireIntervalAdded(res.getString(column), 0, getSize());
            }
            res.close();
           fireContentsChanged(this, 0, getSize());

        } catch (SQLException ex) {System.out.println("Verry bad!  " + ex);
        }
    }

    //=====================================================================
    public int getSize() {
        return data.size();
    }

    synchronized public Object getElementAt(int i) {
            return  data.get(i);
            }

   public void clear(){data.clear();}
   public void removeValueAt(String obj){
synchronized (data){
       data.remove(obj);}
       fireContentsChanged(this, 0, getSize());
   }
}
//========================================================
//public  void  getDataSource (ResultSet res, String column, String column2) {
//    try {
//        data.clear();
//        while (res.next()) {
//            synchronized (data){
//                String s1 = res.getString(column);
//                String s2 = res.getString(column2);
//                data.add(s1 + " ---- " + s2);}
//            System.out.println("!!!" + data.size());
//            // fireIntervalAdded(res.getString(column), 0, getSize());
//        }
//        res.close();
//        fireContentsChanged(this, 0, getSize());
//
//    } catch (SQLException ex) {System.out.println("Verry bad!  " + ex);
//    }
//}