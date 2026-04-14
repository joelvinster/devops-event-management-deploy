package com.events.view;

import com.events.controller.EventController;
import com.events.model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.util.List;

public class EventView extends BorderPane {
    private final EventController controller;
    private TableView<Event> eventTable;
    private ObservableList<Event> tableData;

    // Form fields
    private TextField nameField;
    private DatePicker datePicker;
    private TextField locationField;
    private TextArea descriptionArea;
    private TextField searchField;

    private Event selectedEvent = null;

    public EventView(EventController controller) {
        this.controller = controller;
        this.tableData = FXCollections.observableArrayList();
        
        initUI();
        refreshTable(controller.getAllEvents());
    }

    private void initUI() {
        this.setPadding(new Insets(10));

        // Form Pane (Left)
        VBox formPane = createFormPane();
        this.setLeft(formPane);

        // Table Pane (Center)
        VBox tablePane = createTablePane();
        this.setCenter(tablePane);
    }

    private VBox createFormPane() {
        VBox formPane = new VBox(10);
        formPane.setPadding(new Insets(10));
        formPane.setPrefWidth(300);
        formPane.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5; -fx-background-color: #f9f9f9;");

        Label titleLabel = new Label("Event Details");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        nameField = new TextField();
        nameField.setPromptText("Event Name");

        datePicker = new DatePicker();
        datePicker.setPromptText("Date");
        datePicker.setMaxWidth(Double.MAX_VALUE);

        locationField = new TextField();
        locationField.setPromptText("Location");

        descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description...");
        descriptionArea.setPrefRowCount(4);
        descriptionArea.setWrapText(true);

        Button saveBtn = new Button("Save Event");
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        saveBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        saveBtn.setOnAction(e -> handleSave());

        Button clearBtn = new Button("Clear Form");
        clearBtn.setMaxWidth(Double.MAX_VALUE);
        clearBtn.setOnAction(e -> clearForm());

        formPane.getChildren().addAll(
                titleLabel, 
                new Label("Name:"), nameField, 
                new Label("Date:"), datePicker, 
                new Label("Location:"), locationField, 
                new Label("Description:"), descriptionArea, 
                saveBtn, clearBtn
        );
        return formPane;
    }

    private VBox createTablePane() {
        VBox tablePane = new VBox(10);
        tablePane.setPadding(new Insets(10, 0, 10, 10));

        // Search and Sort Box
        HBox topBox = new HBox(10);
        topBox.setAlignment(Pos.CENTER_LEFT);
        
        searchField = new TextField();
        searchField.setPromptText("Search events...");
        searchField.textProperty().addListener((obs, oldText, newText) -> {
            refreshTable(controller.searchEvents(newText));
        });

        Button sortBtn = new Button("Sort by Date");
        sortBtn.setOnAction(e -> refreshTable(controller.getEventsSortedByDate()));
        
        Button resetBtn = new Button("Reset View");
        resetBtn.setOnAction(e -> {
            searchField.clear();
            refreshTable(controller.getAllEvents());
        });

        topBox.getChildren().addAll(new Label("Search:"), searchField, sortBtn, resetBtn);

        // Table
        eventTable = new TableView<>();
        eventTable.setItems(tableData);
        eventTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Event, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Event, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Event, String> locCol = new TableColumn<>("Location");
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<Event, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        eventTable.getColumns().add(nameCol);
        eventTable.getColumns().add(dateCol);
        eventTable.getColumns().add(locCol);
        eventTable.getColumns().add(descCol);

        eventTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });

        // Bottom Action Box
        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);

        Button deleteBtn = new Button("Delete Selected");
        deleteBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteBtn.setOnAction(e -> handleDelete());

        bottomBox.getChildren().addAll(deleteBtn);

        tablePane.getChildren().addAll(topBox, eventTable, bottomBox);
        VBox.setVgrow(eventTable, Priority.ALWAYS);

        return tablePane;
    }

    private void handleSave() {
        String name = nameField.getText();
        LocalDate date = datePicker.getValue();
        String location = locationField.getText();
        String desc = descriptionArea.getText();

        if (name == null || name.trim().isEmpty() || date == null || location == null || location.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Name, Date, and Location are required.");
            return;
        }

        Event eventData = new Event(name.trim(), date, location.trim(), desc != null ? desc.trim() : "");

        if (selectedEvent == null) {
            controller.addEvent(eventData);
            clearForm();
            refreshTable(controller.getAllEvents());
            showAlert(Alert.AlertType.INFORMATION, "Success", "Event created successfully.");
        } else {
            controller.updateEvent(selectedEvent.getId(), eventData);
            clearForm();
            refreshTable(controller.getAllEvents());
            showAlert(Alert.AlertType.INFORMATION, "Success", "Event updated successfully.");
        }
    }

    private void handleDelete() {
        Event selected = eventTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an event to delete.");
            return;
        }

        controller.deleteEvent(selected.getId());
        clearForm();
        refreshTable(controller.searchEvents(searchField.getText()));
    }

    private void populateForm(Event event) {
        selectedEvent = event;
        nameField.setText(event.getName());
        datePicker.setValue(event.getDate());
        locationField.setText(event.getLocation());
        descriptionArea.setText(event.getDescription());
    }

    private void clearForm() {
        selectedEvent = null;
        nameField.clear();
        datePicker.setValue(null);
        locationField.clear();
        descriptionArea.clear();
        eventTable.getSelectionModel().clearSelection();
    }

    private void refreshTable(List<Event> events) {
        tableData.setAll(events);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}
