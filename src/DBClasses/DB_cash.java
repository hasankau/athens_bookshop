/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBClasses;


import Data.Cash;
import Data.Item;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
public class DB_cash {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public DB_cash() {
        try {
            java_connect c = java_connect.getInstance();
            conn = c.DBConnection();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(DB_query_subtypes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    public boolean insertIntoItem(Cash c) {
        String sql = "insert into cash(date, time, fiveThousand, twoThousand, thousand, fiveHundred, hundred, fifty, twenty, ten, five, two, one, total, drawyer, expen, safe, diff, dc, so, sc, se) values('" + c.getDate() + "', '" + c.getTime() + "', '" + c.getFiveThousand() + "', '" + c.getTwoThousand() + "', '" + c.getThousand() + "', '" + c.getFiveHundred() + "', '" + c.getHundred() + "', '" + c.getFifty() + "', '"+c.getTwenty()+"', '"+c.getTen()+"', '"+c.getFive()+"', '"+c.getTwo()+"', '"+c.getOne()+"', '"+c.getTotal()+"', '"+c.getDrawyer()+"', '"+c.getEx()+"', '"+c.getSafe()+"', '"+c.getDiff()+"', '"+c.getDc()+"', '"+c.getSo()+"', '"+c.getSc()+"', '"+c.getSe()+"')";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return false;
    }
    
    
    
    
    public boolean updateCash(Cash c) { 
        String sql = "update cash set fiveThousand = '"+c.getFiveThousand()+"', twoThousand = '"+c.getThousand()+"', fiveHundred = '"+c.getHundred()+"', hundred = '"+c.getHundred()+"', fifty = '"+c.getFifty()+"', twenty = '"+c.getTwenty()+"', ten = '"+c.getTen()+"', five = '"+c.getFive()+"', two = '"+c.getTwo()+"', one = '"+c.getOne()+"', total = '"+c.getTot()+"', drawyer = '"+c.getDrawyer()+"', expen = '"+c.getEx()+"', safe ='"+c.getSafe()+"', diff = '"+c.getDiff()+"', dc = '"+c.getDc()+"', sc = '"+c.getSc()+"', so = '"+c.getSo()+"', se = '"+c.getSe()+"' where date = '"+c+"'";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return false;
    }
    
    
    public boolean deleteCash(String c) {
        String sql = "delete cash where date = '"+c+"'";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return false;
    }
    
    
    
    
    public Vector getYesterDayCash() {
        String sql = "SELECT drawyer, safe, total from cash order by id desc limit 1";

        Vector v = new Vector();
        
        try {

            pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pst.executeQuery();
            if (rs.first()) {
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
            }
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

        return v;
    }
    
    
    
    public Cash getcash(String date) {
        String sql = "SELECT * from cash where date = '"+date+"'";
Cash c = null;
        
        try {

            pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pst.executeQuery();
            if (rs.first()) {
                c = new Cash();
                c.setFiveThousand(rs.getString(1));
                
        c.setTwoThousand(rs.getString(4));
        c.setThousand(rs.getString(5));
        c.setFiveHundred(rs.getString(6));
        c.setHundred(rs.getString(7));
        c.setFifty(rs.getString(8));
        c.setTwenty(rs.getString(9));
        c.setTen(rs.getString(10));
        c.setFive(rs.getString(11));
        c.setTwo(rs.getString(12));
        c.setOne(rs.getString(13));
        c.setTot(rs.getString(14));
        c.setDrawyer(rs.getString(15));
        c.setSafe(rs.getString(16));
        c.setTotal(rs.getString(17));
        c.setEx(rs.getString(18));
        c.setDiff(rs.getString(19));
        c.setDc(rs.getString(20));
        c.setSc(rs.getString(21));
        c.setSo(rs.getString(22));
        c.setSe(rs.getString(23));
            }
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

        return c;
    }
    
    
    public Vector getAll() {
        String sql = "SELECT * from cash where date = curdate()";

        Vector v = new Vector();
        
        try {

            pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pst.executeQuery();
            if (rs.first()) {
                v.add(rs.getString("amount"));
                v.add(rs.getString("drawyer"));
                v.add(rs.getString("total"));
                v.add(rs.getString("expen"));
                v.add(rs.getString("diff"));
            }
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

        return v;
    }
    
    
}
