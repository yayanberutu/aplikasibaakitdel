/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg11318066_uas_pbo;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author ITDel
 */
public class Main extends Application {
    public static List<TMahasiswa> listMahasiswa;
    public static List<TMatakuliah> listMataKuliah;
    public static List<TMkmhs> listKrs;
    public static List<TDosen> listDosen;
    
    @Override
    public void start(Stage primaryStage) {
        TabPane tp = new TabPane();
        Tab tab1 = new Tab("Mahasiswa");
        Tab tab2 = new Tab("Dosen");
        Tab tab3 = new Tab("Mata kuliah");
        Tab tab4 = new Tab("Isi KRS");
        Tab tab5 = new Tab("Input Nilai");
        Tab tab6 = new Tab("Tampil IPS");
        tp.getTabs().addAll(tab1,tab2,tab3,tab4,tab5,tab6);

        GridPane grdPaneMhs =  new GridPane();
        GridPane grdPaneDosen =  new GridPane();
        GridPane grdPaneMatkul = new GridPane();
        GridPane grdKrs = new GridPane();
        GridPane grdInputNilai =  new GridPane();
        GridPane grdTampilIPS =  new GridPane();
        
        tab1.setContent(grdPaneMhs);
        tab2.setContent(grdPaneDosen);
        tab3.setContent(grdPaneMatkul);
        tab4.setContent(grdKrs);
        tab5.setContent(grdInputNilai);
        tab6.setContent(grdTampilIPS);
        
        grdPaneMhs.setPadding(new Insets(8));
        grdPaneDosen.setPadding(new Insets(8));
        grdPaneMatkul.setPadding(new Insets(8));
        grdKrs.setPadding(new Insets(8));
        grdInputNilai.setPadding(new Insets(8));
        grdTampilIPS.setPadding(new Insets(8));
        
        grdPaneMhs.setHgap(8);
        grdPaneMhs.setVgap(8);
        grdPaneDosen.setHgap(8);
        grdPaneDosen.setVgap(8);
        grdPaneMatkul.setHgap(8);
        grdPaneMatkul.setVgap(8);
        grdKrs.setHgap(8);
        grdKrs.setVgap(8);
        grdInputNilai.setHgap(8);
        grdInputNilai.setVgap(8);
        grdTampilIPS.setHgap(8);
        grdTampilIPS.setVgap(8);
        
        ColumnConstraints cons1 = new ColumnConstraints();
        ColumnConstraints cons2 = new ColumnConstraints();
        cons1.setPercentWidth(30);
        cons2.setPercentWidth(70);
        
        //TAB Mahasiswa

        Label lblNama = new Label("Nama Mahasiswa");
        TextField txtNama = new TextField();
        Label lblNim = new Label("NIM");
        TextField txtNim = new TextField();
        Label lblDob = new Label("Date of Birth");
        DatePicker dpDob = new DatePicker(LocalDate.now());
        dpDob.setEditable(false);
        
        
        dpDob.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd/MM/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern,Locale.US);
            {
                dpDob.setPromptText(pattern.toLowerCase());
            }
        
            @Override
            public String toString(LocalDate date) {
                if(date != null)
                    return dateFormatter.format(date);
                else
                    return "";
            }
            @Override
            public LocalDate fromString(String string) {
                if(string != null && !string.isEmpty())
                    return LocalDate.parse(string,dateFormatter);
                else
                    return null;
            }
        });
        
         
        Label lblEmail = new Label("Email");
        TextField txtEmail = new TextField();
        Label lblInfoTab1 = new Label();
        lblInfoTab1.setStyle("-fx-font-style: italic");
        
        Button btnSaveTab1 = new Button();
        btnSaveTab1.setPadding(new Insets(8));
        btnSaveTab1.setText("Simpan Data Mahasiswa");
        
        grdPaneMhs.getColumnConstraints().addAll(cons1,cons2);
        grdPaneMhs.add(lblNama, 0, 0);
        grdPaneMhs.add(txtNama ,1, 0);
        grdPaneMhs.add(lblNim ,0, 1);
        grdPaneMhs.add(txtNim ,1, 1); 
        grdPaneMhs.add(lblDob ,0, 2);
        grdPaneMhs.add(dpDob ,1, 2);
        grdPaneMhs.add(lblEmail ,0, 3);
        grdPaneMhs.add(txtEmail ,1, 3);
        grdPaneMhs.add(btnSaveTab1 ,1, 4);
        grdPaneMhs.add(lblInfoTab1 ,1, 5);
        
        btnSaveTab1.setOnAction((ActionEvent event) -> {
           if(txtNim.getText().trim().isEmpty() || 
                txtNama.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty()){
            
            lblInfoTab1.setTextFill(Color.RED);
            lblInfoTab1.setText("Silahkan isi dengan lengkap setiap kolom yang ada !");
            
           }
           else
           {
               TMahasiswa obj = new TMahasiswa();
               obj.setNim(txtNim.getText().trim());
               obj.setNama(txtNama.getText().trim());
               obj.setEmail(txtEmail.getText().trim());
               try{
                   obj.setDob(GeneralUtils.convertToDateFromString(dpDob.getEditor().getText(),"dd/MM/yyyy"));
               }
               catch(ParseException ex){
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,ex);
               }
               try{
                   int response = DBUtils.saveMahasiswa(obj);
                   loadAllData();
                   if(response > 0){
                       lblInfoTab1.setTextFill(Color.BLACK);
                       lblInfoTab1.setText("Data is Successfully saved");
                   }
                   else{
                       lblInfoTab1.setTextFill(Color.RED);
                       lblInfoTab1.setText("Unable to save data !!");
                   }
               }
               catch(SQLException ex){
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,ex);
               }
               catch(ClassNotFoundException ex){
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,ex);
               }
           }       
        });
        
        //TAB DOSEN
        Label lblNIDN = new Label("Nama NIDN");
        TextField txtNIDN = new TextField();
        Label lblNamaDosen = new Label("Nama Dosen");
        TextField txtNamaDosen = new TextField();
        Label lblEmailDosen = new Label("Email");
        TextField txtEmailDosen = new TextField();
        Label lblInfoTab11 = new Label();
        lblInfoTab11.setStyle("-fx-font-style: italic");
        
        Button btnSaveTab11 = new Button();
        btnSaveTab11.setPadding(new Insets(8));
        btnSaveTab11.setText("Simpan Data Dosen");
        
        grdPaneDosen.getColumnConstraints().addAll(cons1,cons2);
        grdPaneDosen.add(lblNIDN, 0, 0);
        grdPaneDosen.add(txtNIDN ,1, 0);
        grdPaneDosen.add(lblNamaDosen ,0, 1);
        grdPaneDosen.add(txtNamaDosen ,1, 1);
        grdPaneDosen.add(lblEmailDosen ,0, 2);
        grdPaneDosen.add(txtEmailDosen ,1, 2);
        grdPaneDosen.add(btnSaveTab11 ,1, 3);
        grdPaneDosen.add(lblInfoTab11 ,1, 4);

        btnSaveTab11.setOnAction((ActionEvent event) -> {
           if(txtNIDN.getText().trim().isEmpty() || 
                txtNamaDosen.getText().trim().isEmpty() ||
                txtEmailDosen.getText().trim().isEmpty()){
            
            lblInfoTab11.setTextFill(Color.RED);
            lblInfoTab11.setText("Silahkan isi dengan lengkap setiap kolom yang ada !");
            
           }
           else
           {
               TDosen obj = new TDosen();
               obj.setNidn(txtNIDN.getText().trim());
               obj.setEmail(txtEmailDosen.getText().trim());
               obj.setNama(txtNamaDosen.getText().trim());
               
               try{
                   int response = DBUtils.saveDosen(obj);
                   loadAllData();
                   if(response > 0){
                       lblInfoTab11.setTextFill(Color.BLACK);
                       lblInfoTab11.setText("Data is Successfully saved");
                   }
                   else{
                       lblInfoTab11.setTextFill(Color.RED);
                       lblInfoTab11.setText("Unable to save data !!");
                   }
               }
               catch(SQLException ex){
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,ex);
               }
               catch(ClassNotFoundException ex){
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,ex);
               }
           }       
        });        
        
        
        //TAB MATAKULIAh
        Label lblIdMatkul = new Label("Kode Mata Kuliah");
        TextField txtIdMatkul = new TextField();
        Label lblNamaMatkul = new Label("Nama Mata Kuliah");
        TextField txtNamaMatkul = new TextField(); 
        Label lblJlhSks = new Label("Jumlah SKS");
        TextField txtJlhSks = new TextField(); 
        Label lblInfoTab2 = new Label();
        lblInfoTab2.setStyle("-fx-font-style: italic");
        
        txtJlhSks.textProperty().addListener((ObservableValue <? extends String> observable,String oldValue,String newValue)->{
            if(!newValue.matches("\\d*")){
                txtJlhSks.setText(newValue.replaceAll("[^\\d]",""));
            }
        });
        
        Label lblSms = new Label("Semester");
        ComboBox cbSms = new ComboBox();
        cbSms.getItems().add("Gasal");
        cbSms.getItems().add("Genap");
        
        Label lblDp = new Label("Dosen Pengampu");
        ComboBox cbDp = new ComboBox();
        for(int i=0; i<listDosen.size(); i++){
            cbDp.getItems().add(listDosen.get(i).getNama());
        }
        
        Button btnSaveTab2 = new Button();
        btnSaveTab2.setPadding(new Insets(8));
        btnSaveTab2.setText("Simpan Data Mata Kuliah");
        
        grdPaneMatkul.getColumnConstraints().addAll(cons1,cons2);
        grdPaneMatkul.add(lblIdMatkul, 0, 0);
        grdPaneMatkul.add(txtIdMatkul,1,0);
        grdPaneMatkul.add(lblNamaMatkul,0,1);
        grdPaneMatkul.add(txtNamaMatkul,1,1);
        grdPaneMatkul.add(lblJlhSks,0,2);
        grdPaneMatkul.add(txtJlhSks,1,2);
        grdPaneMatkul.add(lblSms, 0, 3);
        grdPaneMatkul.add(cbSms,1,3);
        grdPaneMatkul.add(lblDp, 0, 4);
        grdPaneMatkul.add(cbDp,1,4);
        grdPaneMatkul.add(btnSaveTab2,1,5);
        grdPaneMatkul.add(lblInfoTab2,1,6);
        
        btnSaveTab2.setOnAction((t)->{
            if(txtIdMatkul.getText().trim().isEmpty() ||
               txtNamaMatkul.getText().trim().isEmpty() ||
               txtJlhSks.getText().trim().isEmpty()){
                
                lblInfoTab2.setTextFill(Color.RED);
                lblInfoTab2.setText("Silahkan isi dengan lengkap setiap kolom yang ada !!! ");
            }
            else{
                TMatakuliah obj = new TMatakuliah();
                obj.setIdMatkul(txtIdMatkul.getText().trim());
                obj.setNamaMatkul(txtNamaMatkul.getText().trim());
                obj.setSks(Integer.valueOf(txtJlhSks.getText().trim()));
            
                try{
                    int response = DBUtils.saveMatakuliah(obj);
                    loadAllData();
                    if(response > 0){
                        lblInfoTab2.setTextFill(Color.BLACK);
                        lblInfoTab2.setText("Data is Successfully Saved");
                    }
                    else{
                       lblInfoTab1.setTextFill(Color.RED);
                       lblInfoTab1.setText("Unable to save data !!");
                   }
                }
                catch(SQLException ex){
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,ex);
                }
                catch(ClassNotFoundException ex){
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        });
        


        //TAB ISI KRS
        
        Label okMatkul = new Label("0");
        Label okMhs = new Label("0");
        okMatkul.setVisible(false);
        okMhs.setVisible(false);
        
        Button btnSaveTab3 = new Button("Simpan data KRS");
        btnSaveTab3.setDisable(true);
        
        Label lblInfoTab3 = new Label();
        Label lblIdMatkulKrs = new Label("Kode Mata Kuliah");
        Label lblNamaMatkulKrs = new Label("Tidak Ditemukan");
        lblNamaMatkulKrs.setTextFill(Color.RED);
        Label lblNamaMatkul1 = new Label("Nama Mata Kuliah");
        TextField txtIdMatkulKrs = new TextField();
        
        Label lblNimKrs = new Label("NIM");
        Label lblNamaMhsKrs = new Label("Tidak Ditemukan");
        lblNamaMhsKrs.setTextFill(Color.RED);
        Label lblNama1 = new Label("Nama Mahasiswa");
        TextField txtNimKrs = new TextField();
        
        txtIdMatkulKrs.textProperty().addListener((ObservableValue<? extends String>
                    observable,String oldValue,String newValue) -> {
                    Object objMatkul = GeneralUtils.findMataKuliah(newValue,listMataKuliah);
                    
                if(objMatkul == null){
                    lblNamaMatkul.setTextFill(Color.RED);
                    lblNamaMatkul.setText("Tidak Ditemukan");
                    okMatkul.setText("0");
                }
                else{
                    lblNamaMatkulKrs.setTextFill(Color.BLUE);
                    lblNamaMatkulKrs.setText(((TMatakuliah)objMatkul).getNamaMatkul());
                    okMatkul.setText("1");
                }
                if(okMatkul.getText().equals("0") || okMhs.getText().equals("0"))
                    btnSaveTab3.setDisable(true);
                else
                    btnSaveTab3.setDisable(false);
            });
        
        txtNimKrs.textProperty().addListener((ObservableValue<? extends String>
                    observable,String oldValue,String newValue) -> {
                    Object objMhs = GeneralUtils.findMahasiswa(newValue,listMahasiswa);
                    
                if(objMhs == null){
                    lblNamaMhsKrs.setTextFill(Color.RED);
                    lblNamaMhsKrs.setText("Tidak Ditemukan");
                    okMhs.setText("0");
                }
                else{
                    lblNamaMhsKrs.setTextFill(Color.BLUE);
                    lblNamaMhsKrs.setText(((TMahasiswa)objMhs).getNama());
                    okMhs.setText("1");
                }
                if(okMatkul.getText().equals("0") || okMhs.getText().equals("0"))
                    btnSaveTab3.setDisable(true);
                else
                    btnSaveTab3.setDisable(false);
            });
        
        grdKrs.getColumnConstraints().addAll(cons1,cons2);
        grdKrs.add(lblIdMatkulKrs, 0, 0);
        grdKrs.add(txtIdMatkulKrs,1,0);
        grdKrs.add(lblNamaMatkul1,0,1);
        grdKrs.add(lblNamaMatkulKrs,1,1);
        grdKrs.add(lblNimKrs,0,2);
        grdKrs.add(txtNimKrs,1,2);
        grdKrs.add(lblNama1,0,3);
        grdKrs.add(lblNamaMhsKrs,1,3);
        grdKrs.add(btnSaveTab3,1,4);
        grdKrs.add(lblInfoTab3,1,5);
        
        lblNamaMatkulKrs.setStyle("-fx-font-style: italic");
        lblNamaMhsKrs.setStyle("-fx-font-style: italic");
        lblInfoTab3.setStyle("-fx-font-style: italic");
        
        btnSaveTab3.setOnAction((t)->{
            if(txtIdMatkulKrs.getText().trim().isEmpty() || txtNimKrs.getText().trim().isEmpty()){
               lblInfoTab3.setTextFill(Color.RED);
               lblInfoTab3.setText("Silahkan isi dengan lengkap setiap kolom yang ada !!! ");
            }
            else{
                TMkmhs obj = new TMkmhs();
                obj.setId_matkul(txtIdMatkulKrs.getText().trim().toUpperCase());
                    obj.setNim(txtNimKrs.getText().trim());
                if(GeneralUtils.checkKrsExist(txtIdMatkulKrs.getText().trim().toUpperCase(),txtNimKrs.getText().trim(),listKrs) != null){
                    lblInfoTab3.setTextFill(Color.RED);
                    lblInfoTab3.setText(String.format("Mahasiswa %s telah mengambil mata kuliah ini ! ",txtNimKrs.getText().trim()));
                }
                else{
                    try{
                        int response = DBUtils.saveKrs(obj);
                        loadAllData();
                        if(response > 0){
                            lblInfoTab2.setTextFill(Color.BLACK);
                            lblInfoTab2.setText("Data is Successfully Saved");
                        }
                        else{
                           lblInfoTab1.setTextFill(Color.RED);
                           lblInfoTab1.setText("Unable to save data !!");
                       }
                    }
                    catch(SQLException ex){
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,ex);
                    }
                    catch(ClassNotFoundException ex){
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,ex);
                    }
                }
            }
        });

        //TAB INPUT NILAI
        Label okMhs1 = new Label("0");
        okMhs1.setVisible(false);
        Label lblNimKrs1 = new Label("NIM");
        Label lblNamaMhsKrs1 = new Label("Tidak Ditemukan");
        TextField txtNimKrs1 = new TextField();
        
        Label lblNamaMatkul2 = new Label("Mata Kuliah");
        ComboBox cbMatkul = new ComboBox();
        for(int i=0; i<listMataKuliah.size(); i++){
            cbMatkul.getItems().add(listMataKuliah.get(i).getNamaMatkul());
        }
        Label lblNilai = new Label("Jumlah Nilai");
        TextField txtNilai = new TextField(); 
        
        txtJlhSks.textProperty().addListener((ObservableValue <? extends String> observable,String oldValue,String newValue)->{
            if(!newValue.matches("\\d*")){
                txtJlhSks.setText(newValue.replaceAll("[^\\d]",""));
            }
        });
        
        lblNamaMhsKrs1.setTextFill(Color.RED);
        txtNimKrs1.textProperty().addListener((ObservableValue<? extends String>
                    observable,String oldValue,String newValue) -> {
                    Object objMhs = GeneralUtils.findMahasiswa(newValue,listMahasiswa);
                    if(objMhs == null){
                    lblNamaMhsKrs1.setTextFill(Color.RED);
                    lblNamaMhsKrs1.setText("Tidak Ditemukan");
                    okMhs1.setText("0");
                }
                else{
                    lblNamaMhsKrs1.setTextFill(Color.BLUE);
                    lblNamaMhsKrs1.setText(((TMahasiswa)objMhs).getNama());
                    okMhs1.setText("1");
                }
                
            });
        Button btnSaveTab4 = new Button("Input Nilai");
        btnSaveTab4.setDisable(true);
        grdInputNilai.getColumnConstraints().addAll(cons1,cons2);
        grdInputNilai.add(lblNimKrs1, 0, 0);
        grdInputNilai.add(txtNimKrs1,1,0);
        grdInputNilai.add(lblNamaMhsKrs1,1,1);
        grdInputNilai.add(lblNamaMatkul2,0,2);
        grdInputNilai.add(cbMatkul,1,2);
        grdInputNilai.add(lblNilai,0,3);
        grdInputNilai.add(txtNilai,1,3);
        grdInputNilai.add(btnSaveTab4,1,5);
        
        //Tab Tampil IPS
        Label lblNimKrs11 = new Label("NIM");
        Label lblNamaMhsKrs11 = new Label("Pastikan NIM yang anda cari sudah benar");
        TextField txtNimKrs11 = new TextField();
        Label lblSemester = new Label("Semester");        
        Button btnSaveTab5 = new Button("Cari");
        btnSaveTab5.setDisable(true);
        ComboBox cbSemester = new ComboBox();
        for(int i=1; i<=8;i++){
            cbSemester.getItems().add(i);
        }
        txtNimKrs11.textProperty().addListener((ObservableValue<? extends String>
                    observable,String oldValue,String newValue) -> {
                    Object objMhs = GeneralUtils.findMahasiswa(newValue,listMahasiswa);
                    if(objMhs == null){
                    lblNamaMhsKrs1.setTextFill(Color.RED);
                    lblNamaMhsKrs1.setText("Tidak Ditemukan");
                    okMhs1.setText("0");
                }
                else{
                    lblNamaMhsKrs1.setTextFill(Color.BLUE);
                    lblNamaMhsKrs1.setText(((TMahasiswa)objMhs).getNama());
                    okMhs1.setText("1");
                }
                
            });
        grdTampilIPS.getColumnConstraints().addAll(cons1,cons2);
        grdTampilIPS.add(lblNimKrs11, 0, 0);
        grdTampilIPS.add(txtNimKrs11 ,1, 0);
        grdTampilIPS.add(lblNamaMhsKrs11 ,1, 1);
        
        grdTampilIPS.add(lblSemester ,0, 2);
        grdTampilIPS.add(cbSemester ,1, 2);

        grdPaneMhs.add(btnSaveTab5 ,1, 3);
        
        Scene scene = new Scene(tp,750,300);
        primaryStage.setTitle("Aplikasi BAAK IT-DEL");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        loadAllData();
        launch(args);
    }
    
    public static void loadAllData() throws SQLException,ClassNotFoundException{
        listMahasiswa = DBUtils.getAllMahasiswa();
        listMataKuliah = DBUtils.getAllMataKuliah();
        listKrs = DBUtils.getAllKrs();
        listDosen = DBUtils.getAllDosen();
    }
    
}
