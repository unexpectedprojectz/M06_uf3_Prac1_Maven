/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.m06.uf1.presentacio;

import dam.m06.uf1.aplicacio.TipusBD;
import dam.m06.uf1.aplicacio.AplicacioException;
import dam.m06.uf1.aplicacio.Common;
import dam.m06.uf1.aplicacio.DriverMySql;
import dam.m06.uf1.aplicacio.LogicEquip;
import dam.m06.uf1.aplicacio.LogicJugador;
import dam.m06.uf1.aplicacio.model.Equip;
import dam.m06.uf1.aplicacio.model.Equips;
import dam.m06.uf1.aplicacio.model.Jugador;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author manel
 */
public class FXMLDocumentController implements Initializable {
    
    //instancia on es carreguen totes les dades
    Equips dades = new Equips();
    
    //definim una llista observable d'objectes de la classe Jugador
    ObservableList<Jugador> llistaObservableJugadors;
    
    ObservableList<Equip> llistaObservableEquips;
    
    DriverMySql conn;
    
    Stage escena;
    
    //camps equip
    @FXML
    TextField txtIdEquip, txtNomEquip, txtEstadiEquip, txtPoblacioEquip, txtProvinciaEquip, txtCpEquip;
         
    //camps jugadors
    @FXML
    TextField txtIdJugador, txtEquipJugador, txtNomJugador, txtDorsalJugador, txtEdatJugador, txtCpJugador;
    
    @FXML 
    TableView taulaJugadors;
    
    @FXML 
    TableView taulaEquips;
    
    @FXML 
    TableColumn colIdJugador, colEquipJugador, colNomJugador, colDorsalJugador, colEdatJugador, colCpJugador;
    
    @FXML 
    TableColumn colIdEquip, colNomEquip, colEstadiEquip, colPoblacioEquip, colProvinciaEquip, colCpEquip;
    
    //<editor-fold defaultstate="collapsed" desc="Jugadors">
    @FXML
    private void handleExportJugadorsToCSV(ActionEvent event) {
        exportarJugadorsACSV();
    }
    
     private void exportarJugadorsACSV()
    {
        try
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar jugadors a CSV");
            fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Fitxers CSV", "*.csv"),
            new ExtensionFilter("tots els fitxers", "*.*"));
            File selectedFile = fileChooser.showSaveDialog(null);
            
            if (selectedFile != null)
            {
                Equips e = new Equips();
                e.setEquips(new ArrayList<>(llistaObservableEquips));
                LogicJugador.desaJugadorsCSV(selectedFile, e);

                mostrarInfo("Exportació de jugadors realitzada correctament a: " + selectedFile.getAbsolutePath());
            }
        }
        catch (AplicacioException ex)
        {
            mostrarAlertaError(ex.toString());
        }  
    }
    
    @FXML
    private void handleOnTaulaJugadorsMouseClicked(MouseEvent event){
        Jugador jugador = (Jugador)taulaJugadors.getSelectionModel().getSelectedItem();
        
        if (jugador != null)
            setJugadorToView(jugador);
    }
    
    @FXML
    private void handleButtonModificaJugador(ActionEvent event) {
        Jugador jugador;
        
        try
        {
            jugador = this.getJugadorFromView();
            LogicJugador.modifyJugador(jugador);
            carregarJugadorsByIdEquip(jugador.getIdEquip());
            
        }
        catch (AplicacioException | NumberFormatException ex)
        {
            mostrarAlertaError(ex.toString());
        }
    }
    
    @FXML
    private void handleButtonNetejaJugador(ActionEvent event) {
        clearJugadorFromView();
    }
    
    @FXML
    private void handleButtonEliminaJugador(ActionEvent event) {
        Jugador jugador;
        
        try
        {
            jugador = this.getJugadorFromView();
            LogicJugador.deleteJugador(jugador);
            carregarJugadorsByIdEquip(jugador.getIdEquip());
        }
        catch (AplicacioException | NumberFormatException ex)
        {
            mostrarAlertaError(ex.toString());
        }
    }
    
     @FXML
    private void handleButtonNouJugador(ActionEvent event) {
        try
        {
            Jugador jugador = this.getJugadorFromView();
            LogicJugador.insertJugador(jugador);
            carregarJugadorsByIdEquip(jugador.getIdEquip());
        }
        catch (AplicacioException | NumberFormatException ex)
        {
            mostrarAlertaError(ex.toString());
        }
    }
    
    private void carregarJugadorsByIdEquip(int idEquip)
    {
        try {
            llistaObservableJugadors = FXCollections.<Jugador>observableArrayList(LogicJugador.getJugadorsByIdEquip(idEquip));
            clearJugadorFromView();
        } catch (AplicacioException ex) {
            mostrarInfo(ex.toString());
        }
        
        llistaObservableJugadors.addListener(new ListChangeListener<Jugador>() {
        @Override public void onChanged(ListChangeListener.Change<? extends Jugador> c) {taulaJugadors.refresh();}
        });
        
        taulaJugadors.setItems(llistaObservableJugadors);
        
        if (taulaJugadors.getItems().size() > 0) 
        {
            setJugadorToView((Jugador)taulaJugadors.getItems().get(0));
        }
    }
    
     /**
     * Mostra les dades d'un jugador a la 
     * @param j 
     */
    private void setJugadorToView(Jugador j)
    {
        
        txtIdJugador.setText(j.getId().toString());
        txtNomJugador.setText(j.getNombre());
        txtEquipJugador.setText(j.getIdEquip().toString());
        txtDorsalJugador.setText(j.getDorsal().toString());
        txtEdatJugador.setText(j.getEdad().toString());
        txtCpJugador.setText(j.getCp());
    }
    
    private void clearJugadorFromView()
    {
        txtIdJugador.setText("");
        txtNomJugador.setText("");
        txtEquipJugador.setText("");
        txtDorsalJugador.setText("");
        txtEdatJugador.setText("");
        txtCpJugador.setText("");
        
         Equip equip = (Equip)taulaEquips.getSelectionModel().getSelectedItem();
        
        if (equip != null)
        {
             txtEquipJugador.setText(equip.getId().toString());
        }
    }
    
    private Jugador getJugadorFromView()
    {
        Jugador ret = new Jugador();
        
       
        int id = (txtIdJugador.getText().isEmpty())? -1 : Integer.valueOf(txtIdJugador.getText());
        ret.setId(id);
        ret.setNombre(txtNomJugador.getText());
        ret.setIdEquip(Integer.parseInt(txtEquipJugador.getText()));
        ret.setDorsal(Integer.parseInt(txtDorsalJugador.getText()));
        ret.setEdad(Integer.parseInt(txtEdatJugador.getText()));
        ret.setCp(txtCpJugador.getText());
        
        return ret;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Equips">
    @FXML
    private void handleExportEquipsToCSV(ActionEvent event){
        exportarEquipsACSV();
    }
    
    private void exportarEquipsACSV()
    {
        try
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar a CSV");
            fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Fitxers CSV", "*.csv"),
            new ExtensionFilter("tots els fitxers", "*.*"));
            File selectedFile = fileChooser.showSaveDialog(null);
            if (selectedFile != null)
            {
                Equips e = new Equips();
                e.setEquips(new ArrayList<>(llistaObservableEquips));
                LogicEquip.desaEquipsCSV(selectedFile, e);

                mostrarInfo("Exportació realitzada correctament a: " + selectedFile.getAbsolutePath());
            }
        }
        catch (AplicacioException ex)
        {
            mostrarAlertaError(ex.toString());
        }  
    }
    
    @FXML
    private void handleOnTaulaEquipsMouseClicked(MouseEvent event){
        Equip equip = (Equip)taulaEquips.getSelectionModel().getSelectedItem();
        
        if (equip != null)
        {
            setEquipToView(equip);
            
            carregarJugadorsByIdEquip(equip.getId());
        }
    }
    
    @FXML
    private void handleButtonCarregarEquips(ActionEvent event) {
        
        carregarEquips();
    }
    
    @FXML
    private void handleButtonNetejaEquip(ActionEvent event){
        clearEquipFromView();
    }
    
    @FXML
    private void handleButtonNetejarEquips(ActionEvent event){
        llistaObservableEquips.clear();
    }
    
    @FXML
    private void handleButtonNouEquip(ActionEvent event){
        try
        {
            Equip equip = this.getEquipFromView();
            LogicEquip.insertEquip(equip);
            carregarEquips();
        }
        catch (AplicacioException | NumberFormatException ex)
        {
            mostrarAlertaError(ex.toString());
        }
    }
    
    @FXML
    private void handleButtonModificaEquip(ActionEvent event){
        Equip equip;
        
        try
        {
            equip = this.getEquipFromView();
            LogicEquip.modifyEquip(equip);
            carregarEquips();
            
        }
        catch (AplicacioException | NumberFormatException ex)
        {
            mostrarAlertaError(ex.toString());
        }
    }
    
    @FXML
    private void handleButtonEliminaEquip(ActionEvent event){
        Equip equip;
        
        try
        {
            equip = this.getEquipFromView();
            LogicEquip.deleteEquip(equip);
            carregarEquips();
        }
        catch (AplicacioException | NumberFormatException ex)
        {
            mostrarAlertaError(ex.toString());
        }
    }
    
    /**
     * Captura les dades en pantalla de l'equip
     * Si no existeix id, li assigna el valor -1
     * @return 
     */
    private Equip getEquipFromView()
    {
        Equip ret = new Equip();
        
        int id = (txtIdEquip.getText().isEmpty())? -1 : Integer.valueOf(txtIdEquip.getText());

        ret.setId(id);
        ret.setNombre(txtNomEquip.getText());
        ret.setEstadio(txtEstadiEquip.getText());
        ret.setPoblacion(txtPoblacioEquip.getText());
        ret.setProvincia(txtProvinciaEquip.getText());
        ret.setCp(txtCpEquip.getText());
        
        return ret;
    }
    
    
    
    /**
     * Estableix les dades d'un equip a la pantalla
     * @param e 
     */
    private void setEquipToView(Equip e)
    {
        txtIdEquip.setText(e.getId().toString());
        txtNomEquip.setText(e.getNombre());
        txtEstadiEquip.setText(e.getEstadio());
        txtPoblacioEquip.setText(e.getPoblacion());
        txtProvinciaEquip.setText(e.getProvincia());
        txtCpEquip.setText(e.getCp());
    }
    
   
    
    private void clearEquipFromView()
    {
        txtIdEquip.setText("");
        txtNomEquip.setText("");
        txtEstadiEquip.setText("");
        txtPoblacioEquip.setText("");
        txtProvinciaEquip.setText("");
        txtCpEquip.setText("");
    }
    
    /**
     * Carrega les dades dels equips i jugadors 
     */
    private void carregarEquips()
    {
        try {
            
            dades.setEquips(LogicEquip.getEquips());
            
            llistaObservableEquips = FXCollections.<Equip>observableArrayList(LogicEquip.getEquips());
            
            llistaObservableEquips.addListener(new ListChangeListener<Equip>() {
            @Override public void onChanged(ListChangeListener.Change<? extends Equip> c) {taulaEquips.refresh();}
            });
            
            taulaEquips.setItems(llistaObservableEquips);

            if (taulaEquips.getItems().size() > 0) 
            {
               Equip e = (Equip)llistaObservableEquips.get(0);
               carregarJugadorsByIdEquip(e.getId());
               
               taulaEquips.requestFocus();
               taulaEquips.getSelectionModel().select(0);
               taulaEquips.getFocusModel().focus(0);
            }
            
        } catch (AplicacioException ex) {
            mostrarInfo(ex.toString());
        }
    }
    
    private void importarDadesFromXML()
    {
        String errorsImport = "";
                
        try
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Importar de XML");
            fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Fitxers XML", "*.xml"),
            new ExtensionFilter("tots els fitxers", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(null);
            
              if (selectedFile != null)
              {

                Equips e = new Equips();

                //obtenim les dades del XML
                e = LogicEquip.carregaDadesDeXML(selectedFile);

                //eliminem les dades de la BD
                LogicJugador.deleteAllJugadors();
                LogicEquip.deleteAllEquips();

                //desem les noves dades tenint en compte les regles de negoci
                errorsImport = LogicEquip.desaDadesBD(e);

                //les tornem a carregar per visualitzar-les
                carregarEquips();

                if (errorsImport.length() > 0)
                {


                    try {
                        File temp = File.createTempFile("ErrorsExportacioXML", ".txt");
                        FileWriter fitx = new FileWriter(temp);
                        fitx.write(errorsImport);
                        fitx.flush();
                        fitx.close();

                        mostrarAlertaError("S'han produït errors d'importació. Fitxer d'errors a " + temp.getAbsolutePath());

                    } catch (IOException ex) {
                        mostrarAlertaError("No s'ha pogut generar el fitxer d'errors");
                    }

                } else
                    mostrarInfo("Importació finalitzada sense errors");
              }

        }
        catch (AplicacioException ex)
        {
            mostrarAlertaError("Error important dades: " + ex.toString());
        }   
        
    }

    private void exportarDadesToXML()
    {
        try
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar a XML");
            fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Fitxers XML", "*.xml"),
            new ExtensionFilter("tots els fitxers", "*.*"));
            File selectedFile = fileChooser.showSaveDialog(null);
            
            if (selectedFile != null)
            {
                Equips e = new Equips();
                e.setEquips(new ArrayList<>(llistaObservableEquips));
                LogicEquip.exportaDadesToXML(selectedFile, e);

                mostrarInfo("Exportació realitzada correctament a: " + selectedFile.getAbsolutePath());
            }
            
        }
        catch (AplicacioException ex)
        {
            mostrarAlertaError(ex.toString());
        }        
    }    
    
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Altres">
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        TipusBD tipusbd = new TipusBD();
        
        try {
            tipusbd.obtenirTipusBD();
        } 
        
        catch (AplicacioException ex) {
            mostrarAlertaError(ex.toString());
        }
        
        //establim un vincle entre els atributs de la classe Jugador i les columnes del tableView
        colIdJugador.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEquipJugador.setCellValueFactory(new PropertyValueFactory<>("idEquip"));
        colNomJugador.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDorsalJugador.setCellValueFactory(new PropertyValueFactory<>("dorsal"));
        colEdatJugador.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colCpJugador.setCellValueFactory(new PropertyValueFactory<>("cp"));

        //el mateix per equips
        colIdEquip.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomEquip.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEstadiEquip.setCellValueFactory(new PropertyValueFactory<>("estadio"));
        colPoblacioEquip.setCellValueFactory(new PropertyValueFactory<>("poblacion"));
        colProvinciaEquip.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        colCpEquip.setCellValueFactory(new PropertyValueFactory<>("cp"));
        
        carregarEquips();
       
    }
    
    
    /**
     * Mostrem l'error per pantalla amb JavaFX
     * @param txt
     */
    private void mostrarAlertaError(String txt)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(txt);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        
        alert.showAndWait();
    }
    
    /**
     * Mostrem l'error per pantalla amb JavaFX
     * @param txt
     */
    private void mostrarInfo(String txt)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info:");
        alert.setContentText(txt);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        
        alert.showAndWait();
    }
    
   
    @FXML
    private void handleImportDadesXML(ActionEvent event){
        
        importarDadesFromXML();
       
    }
    
    @FXML
    private void handleExportDadesXML(ActionEvent event){
        
        exportarDadesToXML();
       
    }
    
    @FXML void handlePrediccioTemps(ActionEvent event)
    {
         String url = "";
        String missatge = "";
        
        Date date = Calendar.getInstance().getTime();   
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusDays(1);
        Date currentDatePlusOneDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");  
        String tomorrow = dateFormat.format(currentDatePlusOneDay);
        
        ArrayList<String> urls = new ArrayList<>();
        urls.add("http://www.aemet.es/xml/provincias/"+tomorrow+"_Provincias_6917.xml");
        urls.add("http://www.aemet.es/xml/provincias/"+tomorrow+"_Provincias_6908.xml");
        urls.add("http://www.aemet.es/xml/provincias/"+tomorrow+"_Provincias_6925.xml");
        urls.add("http://www.aemet.es/xml/provincias/"+tomorrow+"_Provincias_6943.xml");
        
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Girona");
        choices.add("Barcelona");
        choices.add("Lleida");
        choices.add("Tarragona");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Girona", choices);
        dialog.setTitle("Escollir opció");
        dialog.setContentText("Ciutat:");
        
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            url = urls.get(choices.indexOf(result.get()));
            
            try {
                
                missatge = Common.retornaTempsCiutat(url);
                
                mostrarInfo("La predicció del temps per la data " + tomorrow +" a "+ result.get() + " és: " + System.getProperty("line.separator") + System.getProperty("line.separator") +  missatge);
                
            } catch (AplicacioException ex) {
                mostrarAlertaError("Error: " + ex.toString());
            }
        }
    }
}
//</editor-fold>
