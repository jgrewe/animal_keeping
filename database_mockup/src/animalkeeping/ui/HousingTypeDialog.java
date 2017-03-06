package animalkeeping.ui;

import animalkeeping.model.HousingType;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class HousingTypeDialog extends VBox {
    private TextField nameField;
    private Label idLabel;
    private TextArea descriptionArea;
    private HousingType type;


    public HousingTypeDialog() {
        super();
        this.setFillWidth(true);
        this.type = new HousingType();
        init();
    }


    public HousingTypeDialog(HousingType type) {
        this();
        setHousingType(type);
    }


    private void init() {
        idLabel = new Label();
        nameField = new TextField();
        descriptionArea = new TextArea();
        descriptionArea.setWrapText(true);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5, 0, 5, 0));
        grid.add(new Label("ID"), 1, 1);
        grid.add(idLabel, 2, 1);

        grid.add(new Label("name (*)"), 1, 2);
        grid.add(nameField, 2, 2, 2,1);

        grid.add(new Label("description"), 1, 3);
        grid.add(descriptionArea, 1, 4, 3, 3);

        grid.add(new Label("(*) required"), 1, 7);
        this.getChildren().add(grid);
    }


    public void setHousingType(HousingType type) {
        this.type = type != null ? type : new HousingType();
        this.nameField.setText(type != null ? type.getName() : "");
        this.idLabel.setText(type != null ? type.getId().toString() : "");
        this.descriptionArea.setText(type != null ? type.getDescription() : "");
    }


    public HousingType getHousingType() {
        if (this.nameField.getText().isEmpty()) {
            return null;
        }
        type.setName(nameField.getText());
        type.setDescription(descriptionArea.getText());
        return type;
    }
}