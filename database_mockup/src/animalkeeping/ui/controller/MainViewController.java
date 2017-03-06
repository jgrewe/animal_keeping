package animalkeeping.ui.controller;

import animalkeeping.model.SpeciesType;
import animalkeeping.model.SubjectType;
import animalkeeping.model.SupplierType;
import animalkeeping.ui.*;
import animalkeeping.util.Dialogs;
import animalkeeping.util.EntityHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;


public class MainViewController extends VBox implements Initializable{
    @FXML private TitledPane animalHousingPane;
    @FXML private TitledPane personsPane;
    @FXML private TitledPane licensesPane;
    @FXML private TitledPane inventoryPane;
    @FXML private TitledPane subjectsPane;
    @FXML private TitledPane treatmentsPane;
    @FXML private TextField idField;
    @FXML private ScrollPane scrollPane;
    @FXML private VBox masterBox;
    @FXML private ComboBox<String> findBox;
    @FXML private TitledPane findPane;
    @FXML private Menu speciesTypeMenu;
    @FXML private Menu subjectTypeMenu;
    @FXML private Menu supplierMenu;
    private Vector<TitledPane> panes;
    private HashMap<String, View> views;


    public MainViewController() {
        URL url = Main.class.getResource("/animalkeeping/ui/fxml/MainView.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        loader.setController(this);
        try {
            this.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        findBox.getItems().clear();
        findBox.getItems().addAll("Person", "Subject", "Housing unit", "Treatment");
        findBox.getSelectionModel().select("Subject");
        this.scrollPane.setContent(null);
        if (!Main.isConnected()) {
            LoginController login = new LoginController();
            login.addEventHandler(LoginController.DatabaseEvent.CONNECT, event -> connectedToDatabase());
            this.scrollPane.setContent(login);
        }
        else {
            try{
                connectedToDatabase();}
            catch(Exception e){
                e.printStackTrace();
            }
        }
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.prefWidthProperty().bind(masterBox.prefWidthProperty());
        scrollPane.prefHeightProperty().bind(masterBox.prefWidthProperty());
        panes = new Vector<>();
        panes.add(inventoryPane);
        panes.add(subjectsPane);
        panes.add(treatmentsPane);
        panes.add(personsPane);
        panes.add(animalHousingPane);
        panes.add(licensesPane);
        views = new HashMap<>();
    }


    private Boolean viewIsCached(String name) {
        return views.containsKey(name);
    }

    private void cacheView(String name, View view) {
        if (!views.containsKey(name)) {
            views.put(name, view);
        }
    }


    @FXML
    private void showInventory() {
        this.scrollPane.setContent(null);
        inventoryPane.setExpanded(true);
        try {
            InventoryController inventory;
            if (viewIsCached("inventory")) {
                inventory = (InventoryController) views.get("inventory");
            } else {
                inventory = new InventoryController();
                cacheView("inventory", inventory);
            }
            inventory.prefHeightProperty().bind(this.scrollPane.heightProperty());
            inventory.prefWidthProperty().bind(this.scrollPane.widthProperty());
            this.scrollPane.setContent(inventory);
            this.inventoryPane.setContent(inventory.getControls());
            collapsePanes(inventoryPane);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void showPersons() throws Exception{
        this.scrollPane.setContent(null);
        if (!personsPane.isExpanded()) {
            showInventory();
        } else {
            try{
                PersonsView pv;
                if (viewIsCached("persons")) {
                    pv = (PersonsView) views.get("persons");
                } else {
                    pv = new PersonsView();
                    cacheView("persons", pv);
                }
                this.scrollPane.setFitToHeight(true);
                this.scrollPane.setFitToWidth(true);
                pv.minHeightProperty().bind(this.scrollPane.heightProperty());
                pv.minWidthProperty().bind(this.scrollPane.widthProperty());
                this.scrollPane.setContent(pv);
                this.personsPane.setContent(pv.getControls());
                collapsePanes(personsPane);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void showSubjects() {
        this.scrollPane.setContent(null);
        if (!subjectsPane.isExpanded()) {
            showInventory();
        } else {
            try {
                FishView fv;
                if (viewIsCached("subjects")) {
                    fv = (FishView) views.get("subjects");
                } else {
                    fv = new FishView();
                    cacheView("subjects", fv);
                }
                fv.prefHeightProperty().bind(this.scrollPane.heightProperty());
                fv.prefWidthProperty().bind(this.scrollPane.widthProperty());
                this.scrollPane.setContent(fv);
                subjectsPane.setContent(fv.getControls());
                collapsePanes(subjectsPane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void showTreatments() {
        this.scrollPane.setContent(null);
        if (!treatmentsPane.isExpanded()) {
            showInventory();
        } else {
            try {
                TreatmentsView treatmentsView;
                if (viewIsCached("treatment")) {
                    treatmentsView = (TreatmentsView) views.get("treatment");
                } else {
                    treatmentsView = new TreatmentsView();
                    cacheView("treatment", treatmentsView);
                }
                treatmentsView.prefHeightProperty().bind(this.scrollPane.heightProperty());
                treatmentsView.prefWidthProperty().bind(this.scrollPane.widthProperty());
                this.scrollPane.setContent(treatmentsView);
                this.treatmentsPane.setContent(treatmentsView.getControls());
                collapsePanes(treatmentsPane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void showHousingUnits() {
        this.scrollPane.setContent(null);
        if (!animalHousingPane.isExpanded()) {
            showInventory();
        } else {
            try {
                HousingView housingView;
                if (viewIsCached("housing")) {
                    housingView = (HousingView) views.get("housing");
                } else {
                    housingView = new HousingView();
                    cacheView("housing", housingView);
                }
                housingView.prefHeightProperty().bind(this.scrollPane.heightProperty());
                housingView.prefWidthProperty().bind(this.scrollPane.widthProperty());
                this.scrollPane.setContent(housingView);
                this.animalHousingPane.setContent(housingView.getControls());
                collapsePanes(animalHousingPane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void showLicenseView() {
        this.scrollPane.setContent(null);
        if (!licensesPane.isExpanded()) {
            showInventory();
        } else {
            this.licensesPane.setContent(null);
            try {
                LicenseView licenseView;
                if (viewIsCached("license")) {
                    licenseView = (LicenseView) views.get("license");
                } else {
                    licenseView = new LicenseView();
                    cacheView("license", licenseView);
                }
                licenseView.prefHeightProperty().bind(this.scrollPane.heightProperty());
                licenseView.prefWidthProperty().bind(this.scrollPane.widthProperty());
                this.scrollPane.setContent(licenseView);
                this.licensesPane.setContent(licenseView.getControls());
                collapsePanes(licensesPane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Long looksLikeId(String text) {
        Long aLong = null;
        try {
            aLong = Long.parseLong(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (aLong!= null && aLong < 0)
            aLong = null;
        return aLong;
    }


    @FXML
    private void goToId(){
        this.scrollPane.setContent(null);
        Long id = looksLikeId(idField.getText());
        String selectedTable = findBox.getSelectionModel().getSelectedItem();
        if (selectedTable == null) {
            selectedTable = findBox.getItems().get(0);
        }
        if (!Main.isConnected()) {
            return;
        }

        switch (selectedTable) {
            case "Subject":
                FishView fv = new FishView();
                if (id != null) {
                    fv.idFilter(id);
                } else {
                    fv.nameFilter(idField.getText());
                }
                this.scrollPane.setContent(fv);
                break;
            case "Person":
                PersonsView pv = new PersonsView();
                if (id != null) {
                    pv.idFilter(id);
                } else {
                    pv.nameFilter(idField.getText());
                }
                this.scrollPane.setContent(pv);
                break;
            case "Treatment":
                System.out.println("not yet supported");
                break;
            default:
                System.out.println("invalid selection");
                break;
        }
    }



    @FXML
    private void showUserAddInterface() throws Exception{
        Main.getPrimaryStage().setScene(new Scene(FXMLLoader.load(Main.class.getResource("fxml/UserAddView.fxml"))));
        /*this.scrollPane.setContent(null);
        PersonsTable personTable = new PersonsTable();
        this.scrollPane.setContent(personTable);*/
    }

    @FXML
    private  void closeApplication() {
        Main.getPrimaryStage().close();
    }

    @FXML
    private void disconnectFromDatabase() {
        Main.sessionFactory.close();
    }


    @FXML
    private void newSubjectType() {
        Dialogs.editSubjectTypeDialog(null);
        fillSubjectTypeMenu();
    }


    @FXML
    private void newSpeciesType() {
        Dialogs.editSpeciesTypeDialog(null);
        fillSpeciesTypeMenu();
    }


    @FXML
    private void newSupplierType() {
        Dialogs.editSupplierTypeDialog(null);
        fillSupplierTypeMenu();
    }

    @FXML
    private void refreshView() {
        if (this.scrollPane.getContent() instanceof View) {
            ((View) this.scrollPane.getContent()).refresh();
        }
    }


    private void fillSubjectTypeMenu() {
        subjectTypeMenu.getItems().clear();
        List<SubjectType> subjectTypeList = EntityHelper.getEntityList("From SubjectType", SubjectType.class);
        MenuItem newSubjectItem = new MenuItem("new");
        newSubjectItem.setOnAction(event -> newSubjectType());
        subjectTypeMenu.getItems().add(newSubjectItem);
        subjectTypeMenu.getItems().add(new SeparatorMenuItem());

        for (SubjectType t : subjectTypeList) {
            Menu submenu = new Menu(t.getName());
            subjectTypeMenu.getItems().add(submenu);
            MenuItem editItem = new MenuItem("edit");
            editItem.setUserData(t);
            editItem.setOnAction(event -> editSubjectType((SubjectType) editItem.getUserData()));
            MenuItem deleteItem = new MenuItem("delete");
            deleteItem.setUserData(t);
            deleteItem.setOnAction(event -> deleteSubjectType((SubjectType) editItem.getUserData()));
            submenu.getItems().add(editItem);
            submenu.getItems().add(deleteItem);
        }
    }


    private void fillSpeciesTypeMenu() {
        speciesTypeMenu.getItems().clear();
        List<SpeciesType> speciesTypeList = EntityHelper.getEntityList("From SpeciesType", SpeciesType.class);

        MenuItem newSpeciesItem = new MenuItem("new");
        newSpeciesItem.setOnAction(event -> newSpeciesType());
        speciesTypeMenu.getItems().add(newSpeciesItem);
        speciesTypeMenu.getItems().add(new SeparatorMenuItem());

        for (SpeciesType t : speciesTypeList) {
            Menu submenu = new Menu(t.getName());
            speciesTypeMenu.getItems().add(submenu);
            MenuItem editItem = new MenuItem("edit");
            editItem.setUserData(t);
            editItem.setOnAction(event -> editSpeciesType((SpeciesType) editItem.getUserData()));
            MenuItem deleteItem = new MenuItem("delete");
            deleteItem.setUserData(t);
            deleteItem.setOnAction(event -> deleteSpeciesType((SpeciesType) editItem.getUserData()));
            submenu.getItems().add(editItem);
            submenu.getItems().add(deleteItem);
        }
    }


    private void fillSupplierTypeMenu() {
        supplierMenu.getItems().clear();
        List<SupplierType> supplier = EntityHelper.getEntityList("From SupplierType", SupplierType.class);
        MenuItem newSupplierItem = new MenuItem("new");
        newSupplierItem.setOnAction(event -> newSupplierType());
        supplierMenu.getItems().add(newSupplierItem);
        supplierMenu.getItems().add(new SeparatorMenuItem());

        for (SupplierType t : supplier) {
            Menu submenu = new Menu(t.getName());
            supplierMenu.getItems().add(submenu);
            MenuItem editItem = new MenuItem("edit");
            editItem.setUserData(t);
            editItem.setOnAction(event -> editSupplierType((SupplierType) editItem.getUserData()));
            MenuItem deleteItem = new MenuItem("delete");
            deleteItem.setUserData(t);
            deleteItem.setOnAction(event -> deleteSupplierType((SupplierType) editItem.getUserData()));
            submenu.getItems().add(editItem);
            submenu.getItems().add(deleteItem);
        }
    }


    private void fillMenus() {
        fillSubjectTypeMenu();
        fillSpeciesTypeMenu();
        fillSupplierTypeMenu();
    }


    private void connectedToDatabase() {
        subjectsPane.setDisable(false);
        personsPane.setDisable(false);
        inventoryPane.setDisable(false);
        treatmentsPane.setDisable(false);
        findPane.setDisable(false);
        animalHousingPane.setDisable(false);
        licensesPane.setDisable(false);
        subjectTypeMenu.setDisable(false);
        speciesTypeMenu.setDisable(false);
        fillMenus();
        showInventory();
    }

    private void collapsePanes(TitledPane excludedPane) {
        for (TitledPane p : panes) {
            if (p != excludedPane) {
                p.setExpanded(false);
            }
        }
    }

    private void editSubjectType(SubjectType t) {
        Dialogs.editSubjectTypeDialog(t);
    }

    private void deleteSubjectType(SubjectType t) {
        if (!EntityHelper.deleteEntity(t)) {
            Dialogs.showInfo("Subject type " + t.getName() + " could not be deleted. Probably referenced by other entries.");
            return;
        }
        fillSubjectTypeMenu();
    }

    private void editSpeciesType(SpeciesType t) {
        Dialogs.editSpeciesTypeDialog(t);
    }

    private void deleteSpeciesType(SpeciesType t) {
        if (!EntityHelper.deleteEntity(t)) {
            Dialogs.showInfo("Species " + t.getName() + " could not be deleted. Probably referenced by other entries.");
            return;
        }
        fillSpeciesTypeMenu();
    }


    private void editSupplierType(SupplierType t) {
        Dialogs.editSupplierTypeDialog(t);
    }

    private void deleteSupplierType(SupplierType t) {
        if (!EntityHelper.deleteEntity(t)) {
            Dialogs.showInfo("Supplier " + t.getName() + " could not be deleted. Probably referenced by other entries.");
            return;
        }
        fillSupplierTypeMenu();
    }


}