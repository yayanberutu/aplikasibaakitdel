/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg11318066_uas_pbo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ITDel
 */
public class GeneralUtils {
     public static java.sql.Date convertToDateFromString(String date,String format) throws ParseException{
        SimpleDateFormat sdf1 = new SimpleDateFormat(format);
        Date dt = sdf1.parse(date);
        return new java.sql.Date(dt.getTime());
    }
     public static TMahasiswa findMahasiswa(String nim,List<TMahasiswa> listMahasiswa){
         Iterator<TMahasiswa> iterator = listMahasiswa.iterator();
         while(iterator.hasNext()){
             TMahasiswa mhs = iterator.next();
             if(mhs.getNim().toLowerCase().equals(nim)) return mhs;
         }
         return null;
     }
     
     public static TMatakuliah findMataKuliah(String kodeMatkul,List<TMatakuliah> listMatkul){
         Iterator<TMatakuliah> iterator = listMatkul.iterator();
         while(iterator.hasNext()){
             TMatakuliah mkl = iterator.next();
             if(mkl.getIdMatkul().toLowerCase().equals(kodeMatkul)) return mkl;
         }
         return null;
     }
     
         
    public static TMkmhs checkKrsExist(String kodeMatkul,String nim,List<TMkmhs> listMkmhs){
        Iterator<TMkmhs> iterator = listMkmhs.iterator();
        while(iterator.hasNext()){
            TMkmhs mkmhs = iterator.next();
            if(mkmhs.getId_matkul().toLowerCase().equals(kodeMatkul) &&
                    mkmhs.getNim().toLowerCase().equals(nim)) return mkmhs;
        }
        return null;
    }
}
