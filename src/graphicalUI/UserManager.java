package graphicalUI;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.svg.SVGGlyph;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tools.WrongUserTypeException;
import users.*;

import java.io.IOException;

/**
 * User manager window.
 *
 * @author Ruslan Shakirov
 */
public class UserManager {
	private CoreAPI api;
	private Stage stage;
	private Scene scene;
	private StackPane layout;
	private JFXSnackbar snackbar;
	@FXML
	private JFXButton goBackBtn;
	@FXML
	private JFXButton addUserBtn;
	@FXML
	private JFXTreeTableView<UserCell> usersTable;

	public UserManager() {
	}

	/**
	 * Create new view.
	 *
	 * @param stage         Main stage.
	 * @param previousScene Previous scene.
	 * @param api           Core API.
	 */
	public UserManager(Stage stage, Scene previousScene, CoreAPI api) {
		this.api = api;
		this.stage = stage;

		try {
			layout = FXMLLoader.load(getClass().getResource("layout/UserManager.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		scene = new Scene(layout);

		goBackBtn = (JFXButton) layout.lookup("#goBackBtn");
		goBackBtn.setText("");
		SVGGlyph goBackGraphic = Glyphs.ARROW_BACK();
		goBackGraphic.setSize(20, 20);
		goBackGraphic.setFill(Paint.valueOf("#8d8d8d"));
		goBackBtn.setGraphic(goBackGraphic);
		goBackBtn.setOnAction(event -> stage.setScene(previousScene));

		addUserBtn = (JFXButton) layout.lookup("#addUserBtn");
		if (api.getUser() instanceof Admin ||
				((Librarian) api.getUser()).getPrivilege() >= 2) {
			addUserBtn.setOnAction(event -> showAddDialog());
		}

		usersTable = (JFXTreeTableView<UserCell>) layout.lookup("#usersTable");
		initUserTable();

		snackbar = new JFXSnackbar(layout);
	}

	/**
	 * Show view.
	 */
	public void show() {
		stage.setScene(scene);
	}

	/**
	 * Initialize user table.
	 */
	private void initUserTable() {
		JFXTreeTableColumn<UserCell, String> names = new JFXTreeTableColumn<>("NAME");
		names.setPrefWidth(150);
		names.setCellValueFactory(param -> param.getValue().getValue().name);

		JFXTreeTableColumn<UserCell, String> surnames = new JFXTreeTableColumn<>("SURNAME");
		surnames.setPrefWidth(150);
		surnames.setCellValueFactory(param -> param.getValue().getValue().surname);

		JFXTreeTableColumn<UserCell, String> addresses = new JFXTreeTableColumn<>("ADDRESS");
		addresses.setPrefWidth(150);
		addresses.setCellValueFactory(param -> param.getValue().getValue().address);

		JFXTreeTableColumn<UserCell, String> phones = new JFXTreeTableColumn<>("PHONE");
		phones.setPrefWidth(100);
		phones.setCellValueFactory(param -> param.getValue().getValue().phone);

		JFXTreeTableColumn<UserCell, String> statuses = new JFXTreeTableColumn<>("STATUS");
		statuses.setPrefWidth(80);
		statuses.setCellValueFactory(param -> param.getValue().getValue().status);

		final TreeItem<UserCell> tableRoot = new RecursiveTreeItem<>(api.getAllUsers(), RecursiveTreeObject::getChildren);
		usersTable.getColumns().setAll(names, surnames, addresses, phones, statuses);
		usersTable.setRoot(tableRoot);
		usersTable.setShowRoot(false);

		if (api.getUser() instanceof Admin ||
				((Librarian) api.getUser()).getPrivilege() >= 1) {
			usersTable.setOnMouseClicked(event -> showEditDialog());
		}
	}

	/**
	 * Show add dialog.
	 */
	private void showAddDialog() {
		JFXDialog addUserDialog = new JFXDialog();

		Label addUser = new Label();
		if (api.getUser() instanceof Admin) {
			addUser.setText("Add librarian");
		} else {
			addUser.setText("Add patron");
		}
		addUser.setFont(new Font("Roboto", 26));

		JFXTextField loginField = new JFXTextField();
		loginField.setPromptText("Login");
		loginField.setLabelFloat(true);

		JFXTextField passwordField = new JFXTextField();
		passwordField.setPromptText("Password");
		passwordField.setLabelFloat(true);

		HBox loginPassword = new HBox();
		loginPassword.getChildren().addAll(loginField, passwordField);
		loginPassword.setSpacing(20);

		JFXTextField nameField = new JFXTextField();
		nameField.setPromptText("Name");
		nameField.setLabelFloat(true);

		JFXTextField surnameField = new JFXTextField();
		surnameField.setPromptText("Surname");
		surnameField.setLabelFloat(true);

		HBox nameSurname = new HBox();
		nameSurname.getChildren().addAll(nameField, surnameField);
		nameSurname.setSpacing(20);

		JFXTextField phoneField = new JFXTextField();
		phoneField.setPromptText("Phone");
		phoneField.setLabelFloat(true);

		JFXTextField addressField = new JFXTextField();
		addressField.setPromptText("Address");
		addressField.setLabelFloat(true);

		JFXComboBox<String> comboBox = new JFXComboBox<>();
		if (api.getUser() instanceof Librarian) {
			comboBox.getItems().setAll("Student", "Instructor",
					"Teaching assistant", "Visiting professor",
					"Professor");
		} else {
			comboBox.getItems().add("Librarian");
			comboBox.setValue("Librarian");
		}
		comboBox.setPromptText("Select status");

		Label privilegeLabel = new Label("Privilege");
		JFXSlider privilegeSlider = new JFXSlider();
		privilegeSlider.setMin(0);
		privilegeSlider.setMax(3);
		privilegeSlider.setMinorTickCount(1);
		privilegeSlider.setBlockIncrement(1);

		JFXButton addBtn = new JFXButton("ADD");
		addBtn.setFont(new Font("Roboto Bold", 16));

		VBox container = new VBox();
		container.getChildren().addAll(addUser, loginPassword,
				nameSurname, phoneField, addressField);
		if (api.getUser() instanceof Admin) {
			container.getChildren().addAll(privilegeLabel, privilegeSlider);
		} else {
			container.getChildren().add(comboBox);
		}
		container.getChildren().add(addBtn);

		container.setSpacing(40);
		container.setPadding(new Insets(20));

		addBtn.setOnAction(event -> {
			if (comboBox.getValue() == null) return;

			User newUser;
			if (comboBox.getValue().equals("Librarian")) {
				newUser = new Librarian(loginField.getText(), passwordField.getText(),
						nameField.getText(), surnameField.getText(),
						phoneField.getText(), addressField.getText());
				((Librarian) newUser).setPrivilege((int) Math.round(privilegeSlider.getValue()));
			} else {
				newUser = makePatronWithParameters(loginField.getText(), passwordField.getText(),
						comboBox.getValue(),
						nameField.getText(), surnameField.getText(),
						phoneField.getText(), addressField.getText());
			}
			api.addNewUser(newUser);

			TreeItem<UserCell> newCell =
					new TreeItem<>(new UserCell(newUser.getId(), newUser.getName(),
							newUser.getSurname(), newUser.getAddress(),
							newUser.getPhoneNumber(), newUser.getClass().getSimpleName()));
			usersTable.getRoot().getChildren().add(newCell);

			addUserDialog.close();

			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Registered "
					+ nameField.getText() + " " + surnameField.getText()));
		});

		addUserDialog.setContent(container);
		addUserDialog.show(layout);
	}

	/**
	 * Show edit dialog.
	 */
	private void showEditDialog() {
		UserCell selected = usersTable.getSelectionModel().getSelectedItem().getValue();
		if (selected == null) return;
		if (api.getUser() instanceof Admin &&
				!selected.status.getValue().equals("Librarian")) return;

		JFXDialog editUserDialog = new JFXDialog();

		Label editUser = new Label();
		if (api.getUser() instanceof Admin) {
			editUser.setText("Edit librarian");
		} else {
			editUser.setText("Edit patron");
		}
		editUser.setFont(new Font("Roboto", 26));

		JFXTextField loginField = new JFXTextField();
		loginField.setPromptText("Login");
		loginField.setLabelFloat(true);

		JFXTextField passwordField = new JFXTextField();
		passwordField.setPromptText("Password");
		passwordField.setLabelFloat(true);

		HBox loginPassword = new HBox();
		loginPassword.getChildren().addAll(loginField, passwordField);
		loginPassword.setSpacing(20);

		JFXTextField nameField = new JFXTextField();
		nameField.setPromptText("Name");
		nameField.setLabelFloat(true);

		JFXTextField surnameField = new JFXTextField();
		surnameField.setPromptText("Surname");
		surnameField.setLabelFloat(true);

		HBox nameSurname = new HBox();
		nameSurname.getChildren().addAll(nameField, surnameField);
		nameSurname.setSpacing(20);

		JFXTextField phoneField = new JFXTextField();
		phoneField.setPromptText("Phone");
		phoneField.setLabelFloat(true);

		JFXTextField addressField = new JFXTextField();
		addressField.setPromptText("Address");
		addressField.setLabelFloat(true);

		JFXComboBox<String> comboBox = new JFXComboBox<>();
		comboBox.getItems().addAll("Student", "Instructor",
				"Teaching assistant", "Visiting professor",
				"Professor");
		if (api.getUser() instanceof Admin) {
			comboBox.getItems().setAll("Librarian");
			comboBox.setValue("Librarian");
		}
		comboBox.setPromptText("Select status");

		JFXButton saveBtn = new JFXButton("SAVE");
		saveBtn.setFont(new Font("Roboto Bold", 16));

		JFXButton deleteBtn = new JFXButton("DELETE");
		deleteBtn.setDisable(true);
		deleteBtn.setFont(new Font("Roboto Bold", 16));
		deleteBtn.setTextFill(Paint.valueOf("#e53935"));
		if (api.getUser() instanceof Admin ||
				((Librarian) api.getUser()).getPrivilege() >= 3) {
			deleteBtn.setDisable(false);
		}

		Label privilegeLabel = new Label("Privilege");
		JFXSlider privilegeSlider = new JFXSlider();
		privilegeSlider.setMin(0);
		privilegeSlider.setMax(3);
		privilegeSlider.setMinorTickCount(1);
		privilegeSlider.setBlockIncrement(1);

		HBox buttons = new HBox();
		buttons.setSpacing(20);
		buttons.getChildren().addAll(saveBtn, deleteBtn);

		VBox container = new VBox();
		container.getChildren().addAll(editUser, loginPassword,
				nameSurname, phoneField, addressField);
		if (api.getUser() instanceof Admin) {
			container.getChildren().addAll(privilegeLabel, privilegeSlider);
		} else {
			container.getChildren().add(comboBox);
		}
		container.getChildren().add(buttons);

		container.setPadding(new Insets(20));
		container.setSpacing(40);

		User found = api.getUserByID(selected.id);

		loginField.setText(found.getLogin());
		passwordField.setText(found.getPassword());
		nameField.setText(selected.name.getValue());
		surnameField.setText(selected.surname.getValue());
		phoneField.setText(selected.phone.getValue());
		addressField.setText(selected.address.getValue());
		comboBox.setValue(determineComboBoxValue(selected.status.getValue().toLowerCase()));
		if (api.getUser() instanceof Admin) {
			privilegeSlider.setValue(((Librarian) found).getPrivilege());
		}

		saveBtn.setOnAction(event -> {
			try {
				api.editUser(selected.id, "status", determineStatus(comboBox.getValue()).toUpperCase());
			} catch (WrongUserTypeException e) {
				comboBox.setUnFocusColor(Paint.valueOf("#e53935"));
				return;
			}
			api.editUser(selected.id, "login", loginField.getText());
			api.editUser(selected.id, "password", passwordField.getText());
			api.editUser(selected.id, "firstname", nameField.getText());
			api.editUser(selected.id, "lastname", surnameField.getText());
			api.editUser(selected.id, "phone", phoneField.getText());
			api.editUser(selected.id, "address", addressField.getText());

			if (api.getUser() instanceof Admin) {
				api.editUser(selected.id, "privileges",
						String.valueOf(Math.round(privilegeSlider.getValue())));
			}

			initUserTable();
			editUserDialog.close();

			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Saved "
					+ nameField.getText() + " " + surnameField.getText()));
		});

		deleteBtn.setOnAction(event -> {
			api.deleteUser(selected.id);
			initUserTable();
			editUserDialog.close();

			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Deleted "
					+ nameField.getText() + " " + surnameField.getText()));
		});

		editUserDialog.setContent(container);
		editUserDialog.setTransitionType(JFXDialog.DialogTransition.TOP);
		editUserDialog.show(layout);
	}

	private User makePatronWithParameters(String login, String password,
	                                      String comboBoxStatus,
	                                      String name, String surname,
	                                      String phone, String address) {
		switch (determineStatus(comboBoxStatus)) {
			case "student":
				return new Student(login, password,
						name, surname, phone, address);
			case "instructor":
				return new Instructor(login, password,
						name, surname, phone, address);
			case "ta":
				return new TeachingAssistant(login, password,
						name, surname, phone, address);
			case "vp":
				return new VisitingProfessor(login, password,
						name, surname, phone, address);
			case "professor":
				return new Professor(login, password,
						name, surname, phone, address);
			default:
				throw new WrongUserTypeException();
		}
	}

	/**
	 * Get the right status for system.
	 *
	 * @param comboBoxVal Showing value.
	 * @return System value.
	 */
	private String determineStatus(String comboBoxVal) {
		switch (comboBoxVal) {
			case "Student":
				return "student";
			case "Instructor":
				return "instructor";
			case "Teaching assistant":
				return "ta";
			case "Visiting professor":
				return "vp";
			case "Professor":
				return "professor";
			case "Librarian":
				return "librarian";
			default:
				throw new WrongUserTypeException();
		}
	}

	/**
	 * Get the right status for view.
	 *
	 * @param status System value.
	 * @return Showing value.
	 */
	private String determineComboBoxValue(String status) {
		System.out.println(status);
		switch (status) {
			case "student":
				return "Student";
			case "instructor":
				return "Instructor";
			case "teachingassistant":
				return "Teaching assistant";
			case "visitingprofessor":
				return "Visiting professor";
			case "professor":
				return "Professor";
			case "librarian":
				return "Librarian";
			default:
				throw new WrongUserTypeException();
		}
	}

	/**
	 * Users table cell.
	 */
	public static class UserCell extends RecursiveTreeObject<UserCell> {
		int id;
		StringProperty name;
		StringProperty surname;
		StringProperty address;
		StringProperty phone;
		StringProperty status;

		public UserCell(int id, String name, String surname, String address, String phone, String status) {
			this.id = id;
			this.name = new SimpleStringProperty(name);
			this.surname = new SimpleStringProperty(surname);
			this.address = new SimpleStringProperty(address);
			this.phone = new SimpleStringProperty(phone);
			this.status = new SimpleStringProperty(status);
		}
	}
}
