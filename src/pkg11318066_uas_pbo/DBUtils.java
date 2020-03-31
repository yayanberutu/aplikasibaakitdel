/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg11318066_uas_pbo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ITDel
 */
public class DBUtils {
     public static Connection getConnection() throws SQLException,
        ClassNotFoundException{
            String className = "com.mysql.cj.jdbc.Driver";
            Class.forName(className);
            System.out.println("Driver loaded Successfully !");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PROFIL?useTimezone=true&serverTimezone=UTC","root", "");
            if(con.isClosed()) return null;
            else return con;
        }
     public static void closeConnection(Connection con) throws SQLException{
        con.close();
     }
     
     public static int saveMatakuliah(TMatakuliah obj) throws SQLException,ClassNotFoundException{
        Connection con = getConnection();
        String sql = "INSERT INTO TMatakuliah(ID_MATKUL,NAMA_MATKUL,SKS) "+"VALUES(?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,obj.getIdMatkul());
        ps.setString(2, obj.getNamaMatkul());
        ps.setInt(3, obj.getSks());
        
        int response = ps.executeUpdate();
        ps.close();
        con.close();
        
        return response;
    }
     
     public static int saveMahasiswa(TMahasiswa obj) throws SQLException,ClassNotFoundException{
        Connection con = getConnection();
        String sql = "INSERT INTO TMahasiswa(NIM,NAMA,DOB,EMAIL) "+"VALUES(?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,obj.getNim());
        ps.setString(2, obj.getNama());
        ps.setDate(3, obj.getDob());
        ps.setString(4, obj.getEmail());
        
        int response = ps.executeUpdate();
        ps.close();
        con.close();
        
        return response;
    }
     public static int saveDosen(TDosen obj) throws SQLException,ClassNotFoundException{
        Connection con = getConnection();
        String sql = "INSERT INTO TDosen(NIDN,NAMA,EMAIL) "+"VALUES(?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,obj.getNidn());
        ps.setString(2, obj.getNama());
        ps.setString(3, obj.getEmail());
        
        int response = ps.executeUpdate();
        ps.close();
        con.close();
        
        return response;
    }
     
      public static int saveKrs(TMkmhs obj) throws SQLException,ClassNotFoundException{
        Connection con = getConnection();
        String sql = "INSERT INTO TMkMhs(NIM,ID_MATKUL) "+"VALUES(?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,obj.getNim());
        ps.setString(2, obj.getId_matkul());
        
        int response = ps.executeUpdate();
        ps.close();
        con.close();
        
        return response;
    }
      
      public static List<TMahasiswa> getAllMahasiswa() throws SQLException,ClassNotFoundException{
        Connection con = getConnection();
        String sql = "SELECT * FROM TMahasiswa";
        PreparedStatement ps = con.prepareStatement(sql);
        
         List<TMahasiswa> listMahasiswa  = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        
        TMahasiswa objMhs;
        while(rs.next()){
            objMhs = new TMahasiswa();
            objMhs.setNim(rs.getString("NIM"));
            objMhs.setNama(rs.getString("NAMA"));
            objMhs.setDob(rs.getDate("DOB"));
            objMhs.setEmail(rs.getString("Email"));
            
            listMahasiswa.add(objMhs);
        }
        
        return listMahasiswa;
      }
      
      public static List<TMatakuliah> getAllMataKuliah() throws SQLException,ClassNotFoundException{
        Connection con = getConnection();
        String sql = "SELECT * FROM TMataKuliah";
        PreparedStatement ps = con.prepareStatement(sql);
        
        List<TMatakuliah> listMataKuliah  = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        
        TMatakuliah objMatkul;
        while(rs.next()){
            objMatkul = new TMatakuliah();
            objMatkul.setIdMatkul(rs.getString("ID_MATKUL"));
            objMatkul.setNamaMatkul(rs.getString("NAMA_MATKUL"));
            objMatkul.setSks(rs.getInt("SKS"));
               
            listMataKuliah.add(objMatkul);
        }
        
        return listMataKuliah;
    }
      
      public static List<TMkmhs> getAllKrs() throws SQLException,ClassNotFoundException{
        Connection con = getConnection();
        String sql = "SELECT * FROM TMkMhs";
        PreparedStatement ps = con.prepareStatement(sql);
        
        List<TMkmhs> listMkMhs  = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        
        TMkmhs objMkMhs;
        while(rs.next()){
            objMkMhs = new TMkmhs();
            objMkMhs.setId_matkul(rs.getString("ID_MATKUL"));
            objMkMhs.setNim(rs.getString("NIM"));
               
            listMkMhs.add(objMkMhs);
        }
        
        return listMkMhs;
      }
      
      public static List<TDosen> getAllDosen() throws SQLException, ClassNotFoundException{
          Connection con = getConnection();
          String sql = "SELECT * FROM TDosen";
          PreparedStatement ps = con.prepareStatement(sql);
          
          List<TDosen> listDosen  = new ArrayList<>();
          ResultSet rs = ps.executeQuery();
          TDosen objDosen;
          while(rs.next()){
            objDosen = new TDosen();
            objDosen.setNidn(rs.getString("NIDN"));
            objDosen.setEmail(rs.getString("EMAIL"));
            objDosen.setNama(rs.getString("NAMA"));
               
            listDosen.add(objDosen);
        }
        
        return listDosen;
          
      }
}
