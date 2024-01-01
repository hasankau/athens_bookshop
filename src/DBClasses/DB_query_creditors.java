/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBClasses;


import Data.Account;
import Data.Customer;
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
public class DB_query_creditors {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public DB_query_creditors() {
        try {
            java_connect c = java_connect.getInstance();
            conn = c.DBConnection();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(DB_query_subtypes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public Customer getCustomer(String cus){
        String sql = "SELECT * from customer where id = '"+cus+"'";
        
        Customer c = new Customer();
        
        try {
            
            pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pst.executeQuery();
            if (rs.first()) { 
                
                if (rs.getString(1)==null) {
                }else{
                    c.setFirst_name(rs.getString("first_name"));
                    c.setLast_name(rs.getString("last_name"));
                }
                return c;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return null;
    }
    
    public Account getAccountByInv(String inv){
        String sql = "SELECT * from payment where billno = '"+inv+"'";
        
        
                    Account a = new Account();
        try {
            
            pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pst.executeQuery();
            if (rs.first()) { 
                
                if (rs.getString(1)==null) {
                }else{
                    a.setCustomer_id(rs.getString(2));
                a.setPo(rs.getString("PO"));
                a.setDATE(rs.getString("DATE"));
                a.setDel_date(rs.getString("DEL_DATE"));
                a.setAMOUNT(rs.getString("AMOUNT"));
                a.setDno(rs.getString("DELIVERY_NO"));
                a.setDue_in(rs.getString("DUE_IN"));
                a.setCordinator(rs.getString("CORDINATOR"));
                
                    System.out.println(a.getDATE()+" b-----------------------------");
                    System.out.println(a.getDel_date()+" b-----------------------------");
                }
                return a;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return null;
    }
    
    public boolean insertIntoDebit(Account a) {
        String sql = "insert into supp_debit(DATE, AMOUNT, sup_id, BILLNO, REF_NO)values('"+a.getDATE()+"',  '"+a.getAMOUNT()+"', '"+a.getCustomer_id()+"', '"+a.getBILLNO()+"', '"+a.getREF()+"')";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            
//            JOptionPane.showMessageDialog(null, "Saved to account", "Successfull", JOptionPane.INFORMATION_MESSAGE);
            return true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
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

    
public boolean updateAccountDSD(Account a) {
    
        
    
        String sql = "update supplier set current_debit_amount=GREATEST(current_debit_amount+"+a.getAMOUNT()+", 0)  where supplier_id = "+a.getCustomer_id()+"";
        System.out.println(sql);
        try {

            pst = conn.prepareStatement("select * from supplier where supplier_id = "+a.getCustomer_id()+"");
            pst.executeQuery();
            
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
            }
        }

        return false;
    }

public String getRefNow(){
        String sql = "SELECT max(id) from account";
        
        
        String empno = "1";
        try {
            
            pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pst.executeQuery();
            if (rs.first()) { 
                
                if (rs.getString(1)==null) {
                    empno="1";
                }else{
                    empno = rs.getString(1);
                }
                return empno;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return "1";
    }
public boolean updateDebit(Account a) {
        String sql = "delete from supp_debit where id = '"+a.getId()+"'";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "Updated account", "Successfull", JOptionPane.INFORMATION_MESSAGE);
            return true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
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
public boolean updateAccountDD(Account a) {
    
        
    
        String sql = "update supplier set current_debit_amount=GREATEST(current_debit_amount-"+a.getAMOUNT()+", 0)  where supplier_id = "+a.getCustomer_id()+"";
        System.out.println(sql);
        try {

            pst = conn.prepareStatement("select * from supplier where supplier_id = "+a.getCustomer_id()+"");
            pst.executeQuery();
            
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
            }
        }

        return false;
    }
public boolean updatePaymentStateD(Account a) {
        String sql = "update supp_debit set STATE='"+a.getSTATE()+"', REF_NO = '"+a.getREF()+"' , DATE_PAID = now() where id = '"+a.getId()+"'";
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
            }
        }

        return false;
    }
    
    
    public Vector loadUpnaidGrn(String id) {
        String sql = "select billno from supp_debit where sup_id = '"+id+"' and state = 'Not Paid'";
        Vector v = new Vector();
        try {
            

            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
            v.add(rs.getString(1));
            }
            return v;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
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
    
    
    public Vector loadUpnaidGrn2(String id, String bi) {
        String sql = "select billno from supp_debit where billno like '%"+bi+"%' and sup_id = '"+id+"' and state = 'Not Paid'";
        Vector v = new Vector();
        try {
            

            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
            v.add(rs.getString(1));
            }
            return v;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
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
    
    
    
    public Vector loadUpnaidInv2(String id, String bi) {
        String sql = "select billno from payment where billno like '%"+bi+"%' and cus_id = '"+id+"' and state = 'Not Paid'";
        Vector v = new Vector();
        try {
            

            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
            v.add(rs.getString(1));
            }
            return v;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
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
    
    public String getRef(){
        String sql = "SELECT max(id)+1 from account";
        
        
        String empno = "1";
        try {
            
            pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pst.executeQuery();
            if (rs.first()) { 
                
                if (rs.getString(1)==null) {
                    empno="1";
                }else{
                    empno = rs.getString(1);
                }
                return empno;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return "1";
    
    }
    
    
    public boolean insertIntoCredit(Account a) {
        String sql = "insert into payment(DATE, AMOUNT, cus_id, BILLNO, REF_NO, DUE_DATE, DUE_IN, CORDINATOR, TEL, PO, DELIVERY_NO, DEL_DATE)values('"+a.getDATE()+"',  '"+a.getAMOUNT()+"', '"+a.getCustomer_id()+"', '"+a.getBILLNO()+"', '"+a.getREF()+"', '' , '"+a.getDue_in()+"', '"+a.getCordinator()+"', '"+a.getTel()+"', '"+a.getPo()+"', '"+a.getDno()+"', '"+a.getDel_date()+"')";
        System.out.println(sql);
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            
//            JOptionPane.showMessageDialog(null, "Saved to account", "Successfull", JOptionPane.INFORMATION_MESSAGE);
            return true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
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

    public boolean updateAccountC(Account a) {
        String sql = "update customer set current_credit_amount=GREATEST(current_credit_amount+'"+a.getAMOUNT()+"', 0)  where id = '"+a.getCustomer_id()+"'";
        System.out.println(sql);
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
            }
        }

        return false;
    }
    
    
    public boolean updatePaymentState(Account a) {
        String sql = "update payment set STATE='"+a.getSTATE()+"', REF_NO = '"+a.getREF()+"', DATE_RECEIVED = 'now()' where id = '"+a.getId()+"'";
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
            }
        }

        return false;
    }
    
    
    public boolean updateCredit(Account a) {
        String sql = "delete from payment where id = '"+a.getId()+"'";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "Updated account", "Successfull", JOptionPane.INFORMATION_MESSAGE);
            return true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
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

    
    public boolean updateAccountD(Account a) {
    
        
    
        String sql = "update customer set current_credit_amount=GREATEST(current_credit_amount-"+a.getAMOUNT()+", 0)  where id = "+a.getCustomer_id()+"";
        System.out.println(sql);
        try {

            pst = conn.prepareStatement("select * from customer where id = "+a.getCustomer_id()+"");
            pst.executeQuery();
            
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
            }
        }

        return false;
    }
    
    
    public boolean updateAccount(Account a) {
        
        String sql = "delete from account where id = '"+a.getId()+"'";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "Updated to account", "Successfull", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
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
    
    
    
    public boolean updateAccountState(Account a) {
        String sql = "update account set CHEQUE_STATE='"+a.getCHEQUE_STATE()+"'  where id = '"+a.getId()+"'";
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
            }
        }

        return false;
    }
    
    
    public boolean insertIntoAccount(Account a) {
        String sql = "insert into account(DATE, NAME, AMOUNT, METHOD, CHEQUE, customer_id, CHEQUE_DATE, REF_NO)values('"+a.getDATE()+"', (select first_name from customer where id = '"+a.getCustomer_id()+"'), '"+a.getAMOUNT()+"', '"+a.getMETHOD()+"', '"+a.getCHEQUE()+"', '"+a.getCustomer_id()+"','"+a.getCHEQUE_DATE()+"', '"+a.getREF()+"')";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "Saved to account", "Successfull", JOptionPane.INFORMATION_MESSAGE);
            return true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
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

    
public boolean insertIntoAccountD(Account a) {
        String sql = "insert into supp_paid(DATE, NAME, AMOUNT, METHOD, CHEQUE, sup_id, CHEQUE_DATE, REF_NO, GRN_NO, DATE_PAID)values('"+a.getDATE()+"', (select first_name from supplier where supplier_id = '"+a.getCustomer_id()+"'), '"+a.getAMOUNT()+"', '"+a.getMETHOD()+"', '"+a.getCHEQUE()+"', '"+a.getCustomer_id()+"','"+a.getCHEQUE_DATE()+"', '"+a.getREF()+"', '"+a.getBILLNO()+"', now())";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "Saved to account", "Successfull", JOptionPane.INFORMATION_MESSAGE);
            return true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
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
public boolean updateAccountDSDD(Account a) {
        
        String sql = "delete from supp_paid where id = '"+a.getId()+"'";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "Updated to account", "Successfull", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
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
public boolean updateAccountDS(Account a) {
        String sql = "update supplier set current_debit_amount=GREATEST(current_debit_amount+'"+a.getAMOUNT()+"', 0)  where supplier_id = '"+a.getCustomer_id()+"'";
        System.out.println(sql);
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
            }
        }

        return false;
    }

    
  
    public String getCusno(){
        String sql = "SELECT max(id)+1 from customer";
        
        
        String empno = "1";
        try {
            
            pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pst.executeQuery();
            if (rs.first()) { 
                
                if (rs.getString(1)==null) {
                    empno="1";
                }else{
                    empno = rs.getString(1);
                }
                return empno;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return "1";
    }
    
 
    public boolean insertIntoAccount(String max, String newc, String cus_id) {
        String sql = "insert into account (max_credit_amount, current_credit_amount, customer_id)values('" + max + "', '" + newc + "', '"+cus_id+"')";
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
            }
        }

        return false;
    }
    
    
    
    public boolean deleteFromAccount(String id) {
        String sql = "delete from account where id = '" + id + "'";
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
            }
        }

        return false;
    }
    
    public boolean deleteFromCus(String id) {
        String sql = "delete from customer where id =  '"+ id + "'";
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
            }
        }

        return false;
    }
    
    public boolean updateCus(String id, String fn, String ln, String tel, String max, String newc, String ema) {
        String sql = "update customer set max_credit_amount = '"+max+"', current_credit_amount = "+newc+", first_name = '"+fn+"', last_name = '"+ln+"', tel = '"+tel+"', email = '"+ema+"' where id = '"+id+"'";
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
            }
        }

        return false;
    }
    
   

    public boolean insertIntoCus(String fname, String lname, String tel, String max, String cur, String ema) {
        String sql = "insert into customer(first_name, last_name, tel, max_credit_amount, current_credit_amount, email)values('"+fname+"','" + lname + "', '" + tel + "', '" + max + "', '" + cur + "', '"+ema+"')";
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
            }
        }

        return false;
    }
    
    public boolean updateCus2(String id, String fname, String lname, String tel) {
        String sql = "update customer set first_name = '"+fname+"', last_name = '" + lname + "', tel = '" + tel + "' where id = '"+id+"'";
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
            }
        }

        return false;
    }
    
public String isCustomerExists(String fn, String ln, String tel){
        String sql = "SELECT id from customer where first_name='"+fn+"' and last_name = '"+ln+"' and '"+tel+"'";
        
        try {
            
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.first()) { 
                return rs.getString(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured!"+e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
        }

        return null;
    }
    













public boolean deleteFromRpair(String id) {
        String sql = "delete from repair where id = '" + id + "'";
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
            }
        }

        return false;
    }
    
    

    public boolean insertIntoRepair(String date, String item, String disc, String cus, String am, String tel) {
        String sql = "insert into repair(date, item, disc, customer, status, total, tel)values('"+date+"','" + item + "', '" + disc + "', '" + cus + "', 'Ready', '"+am+"', '"+tel+"')";
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
            }
        }

        return false;
    }
    
    public boolean updateRepair(String id, String item, String disc, String am, String tel) {
        String sql = "update customer set item = '"+item+"', disc = '" + disc + "', total = '"+am+"', tel = '"+tel+"' where id = '"+id+"'";
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
            }
        }

        return false;
    }
    
    
    
    
    public boolean statusRepair(String id) {
        String sql = "update repair set status = 'Finished' where id = '" + id + "'";
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
            }
        }

        return false;
    }
}
