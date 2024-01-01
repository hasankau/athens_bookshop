/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBClasses;

import Data.Item;
import Data.StockItem;
import Data.Stock_var;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author HASANKA
 */
public class DB_query_var {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public DB_query_var() {
        java_connect c = java_connect.getInstance();
        conn = c.DBConnection();
    }

        
        public boolean insertIntoPro(Item item) {
            
        String sql = "insert into item_var_pro (barcode, name, qty, rec_qty, diff, type, date, time, item_id) values ('"+item.getBarcode()+"', (select name from item where barcode = '"+item.getBarcode()+"'), '"+item.getQty()+"', '"+item.getAqty()+"', '"+item.getAqty()+"', '"+item.getType()+"', '"+item.getDate_in()+"', '"+item.getTime()+"', '"+item.getIid()+"')";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Stock saved", "Successful", JOptionPane.ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
        
        
        public boolean insertIntoDaily(Item item) {
            
        String sql = "insert into stock_daily (id, barcode, name, description, manufacturer, purchase_price, selling_price, qty, date, time) values ('"+item.getIid()+"', '"+item.getBarcode()+"', (select name from item where barcode = '"+item.getBarcode()+"'), (select description from item where barcode = '"+item.getBarcode()+"'), (select manufacturer from item where barcode = '"+item.getBarcode()+"'), (select purchase_price from item where barcode = '"+item.getBarcode()+"'), (select selling_price from item where barcode = '"+item.getBarcode()+"'), '"+item.getQty()+"', '"+item.getDate_in()+"', '"+item.getTime()+"')";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Stock saved", "Successful", JOptionPane.ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
        
        
        
        public boolean insertIntoDiff(Item item) {
            
        String sql = "insert into stock_diff (id, barcode, name, description, manufacturer, purchase_price, selling_price, qty, date, time) values ('"+item.getIid()+"', '"+item.getBarcode()+"', (select name from item where barcode = '"+item.getBarcode()+"'), (select description from item where barcode = '"+item.getBarcode()+"'), (select manufacturer from item where barcode = '"+item.getBarcode()+"'), (select purchase_price from item where barcode = '"+item.getBarcode()+"'), (select selling_price from item where barcode = '"+item.getBarcode()+"'), (select qty from item where barcode = '"+item.getBarcode()+"')-(select qty from stock_daily where barcode = '"+item.getBarcode()+"' and date='"+item.getDate_in()+"'), '"+item.getDate_in()+"', '"+item.getTime()+"')";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Stock saved", "Successful", JOptionPane.ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
        
        
        public boolean insertIntoSystemDaily(String date, String time) {
            
            
            
        String sql = "insert into stock_system_daily (id, barcode, name, description, manufacturer, purchase_price, selling_price, qty, date, time) select id, barcode, name, description, manufacturer, purchase_price, selling_price, qty, '"+date+"', '"+time+"' from item";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
        
   public boolean insertIntoFin(Item item) {
            
        String sql = "insert into item_var_fin (barcode, name, qty, rec_qty, diff, type, date, time, item_id) values ('"+item.getBarcode()+"', (select name from item where barcode = '"+item.getBarcode()+"'), '"+item.getQty()+"', '"+item.getAqty()+"', qty-'"+item.getAqty()+"', '"+item.getType()+"', '"+item.getDate_in()+"', '"+item.getTime()+"', '"+item.getIid()+"')";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Stock saved", "Successful", JOptionPane.ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
   
   public boolean deletePro(String date) {
            
        String sql = "delete from stock_daily where date = curdate()";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Stock saved", "Successful", JOptionPane.ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
   
   
   public boolean deleteDaily(String date) {
            
        String sql = "delete from stock_daily where date = '"+date+"'";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
   
   
   public boolean deleteSysDaily(String date) {
            
        String sql = "delete from stock_system_daily where date = '"+date+"'";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
   
   
   
   public boolean checkSysDaily(String date) {
            
        String sql = "select * from stock_system_daily where date = curdate()";
        try {

            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.first()){
                   return true; 
            }
            
            //JOptionPane.showMessageDialog(null, "Stock saved", "Successful", JOptionPane.ERROR_MESSAGE);
            
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
   
   public boolean deleteFin(String date) {
            
        String sql = "delete from stock_diff where date = curdate()";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            //JOptionPane.showMessageDialog(null, "Stock saved", "Successful", JOptionPane.ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
   
   
   public boolean checkPro(String date) {
            
        String sql = "select * from item_var_pro where date = '"+date+"'";
        try {

            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.first()){
                   return true; 
            }
            //JOptionPane.showMessageDialog(null, "Stock saved", "Successful", JOptionPane.ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
   
   public boolean checkDaily(String date) {
            
        String sql = "select * from stock_daily where date = curdate()";
        try {

            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.first()){
                   return true; 
            }
            //JOptionPane.showMessageDialog(null, "Stock saved", "Successful", JOptionPane.ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
   
   
   public boolean checkDailyDiff(String date) {
            
        String sql = "select * from stock_diff where date = curdate()";
        try {

            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.first()){
                   return true; 
            }
            //JOptionPane.showMessageDialog(null, "Stock saved", "Successful", JOptionPane.ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
   
   public boolean checkDiff(String date) {
            
        String sql = "select * from stock_diff where date = '"+date+"'";
        try {

            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.first()){
                   return true; 
            }
            //JOptionPane.showMessageDialog(null, "Stock saved", "Successful", JOptionPane.ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
   
   
   public boolean checkFin(String date) {
            
        String sql = "select * from item_var_fin where date = '"+date+"'";
        try {

            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.first()){
                   return true; 
            }
            //JOptionPane.showMessageDialog(null, "Stock saved", "Successful", JOptionPane.ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
        
   public boolean deleteFlow(String id) {
        String sql = "delete from stock_flow1 where id = '" + id + "'";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return false;
    }
   
   
   
   public boolean saveFlow(StockItem s, String state, String qty, String date) {
        String sql = "insert into stock_flow (AMOUNT, STATE, BALANCE_STORE, DATE, BALANCE_INCOME, BALANCE_LORRY, BALANCE_LORRY_INCOME, ITEM_CODE, NAME) values ('" + qty + "', '"+state+"', (select qty from item where barcode = '"+s.getItem_item_code()+"'), '" + date+ "', (select qty*selling_price from item where barcode = '"+s.getItem_item_code()+"'), (select qty_lorry from item where barcode = '"+s.getItem_item_code()+"'), (select qty_lorry*selling_price from item where barcode = '"+s.getItem_item_code()+"'), (select id from item where barcode = '"+s.getItem_item_code()+"'), (select name from item where barcode = '"+s.getItem_item_code()+"'))";
        System.out.println(sql);
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Flow Saved", "Successfull", JOptionPane.INFORMATION_MESSAGE);

            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }
   
   
   public boolean updateStock(String barcode, String qty) {
        String sql = "update item set qty=GREATEST(0, qty+'" + qty + "') where barcode ='" + barcode + "';";
        try {
            java_connect c = java_connect.getInstance();
            conn = c.DBConnection();
            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql);
            pst.execute();

            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                conn.commit();
            } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(DB_invoice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return false;
    }
   
}
