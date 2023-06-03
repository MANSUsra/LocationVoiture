package application;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {
	
	Label titre;
	Label marqueVoiture;
	Label modeleVoiture;
	Label anneeVoiture;
	Label matriculeVoiture;
	Label copyright;
//	Label matriculeToUpdate;
	
	TextField matricule;
	TextField marque;
	TextField modele;
	TextField annee;
//	TextField matriculeUpdate;
	
	Button ajout;
	Button update;
	Button suppression;
	Button affichage;
	Button show;
	
	Scene tableVoitures;
	Scene scene;
	Connection connection;
	DatabaseManagement dbManagement;

	Text message;
	
	Stage primaryStage;
	Stage window;
	GridPane grid;
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws SQLException, FileNotFoundException {  
		
		//DataBase management
		dbManagement = new DatabaseManagement();
		
		 // Create a new database
		String databaseName = "LocationVoiture";
		dbManagement.createDatabase(databaseName);
		
		// Connect to the database
		dbManagement.connect(databaseName);
		
		// Use the connection for further database operations
		connection = dbManagement.getConnection();
		
		// Perform your database operations using the 'connection' object
		
	// AFFICHAGE DES ELEMENTS INSERER EN BDD
		
		// TableView
        TableView<Voiture> tableView = new TableView<>();

        // Columns
        
        TableColumn<Voiture, String> marqueColumn = new TableColumn<>("Marque");
        marqueColumn.setCellValueFactory(new PropertyValueFactory<>("marque"));

        TableColumn<Voiture, String> modeleColumn = new TableColumn<>("Modèle");
        modeleColumn.setCellValueFactory(new PropertyValueFactory<>("modele"));

        TableColumn<Voiture, String> anneeColumn = new TableColumn<>("Année");
        anneeColumn.setCellValueFactory(new PropertyValueFactory<>("annee"));

        TableColumn<Voiture, String> matriculeColumn = new TableColumn<>("Matricule");
        matriculeColumn.setCellValueFactory(new PropertyValueFactory<>("matricule"));

        tableView.getColumns().addAll(marqueColumn, modeleColumn, anneeColumn, matriculeColumn);


        // Retrieve the list of cars from the database
        String query = "SELECT marque, modele, matricule, annee FROM Voitures";
        PreparedStatement selectStatement = connection.prepareStatement(query);
        ResultSet resultSet = selectStatement.executeQuery();

        // Create a list to store the cars
        ObservableList<Voiture> voituresListe = FXCollections.observableArrayList();

        while (resultSet.next()) {
            String marque = resultSet.getString("marque");
            String modele = resultSet.getString("modele");
            String annee = resultSet.getString("annee");
            String matricule = resultSet.getString("matricule");

            Voiture car = new Voiture(marque, modele, annee, matricule);
            voituresListe.add(car);
        }

        tableView.setItems(voituresListe);
		
		//////////// stage //////////
		primaryStage.setTitle("Location Voiture");
		titre = new Label();
		titre.setText("Veuillez remplir les champs ci-dessous\net sélectionner l'action que vous souhaitez effectuer en cliquant sur le button approprié.\n\n\n\n ");
		titre.setAlignment(Pos.CENTER);
	

		
		//marque 
		marqueVoiture = new Label("Marque: ");
		marqueVoiture.getStyleClass().add("my-label");
		marque = new TextField();
		marque.setPromptText("marque");
		marque.setMinWidth(100);
		
		//Modele
		modeleVoiture = new Label("Modèle: ");
		modeleVoiture.getStyleClass().add("my-label");
		modele = new TextField();
		modele.setPromptText("modèle");
		modele.setMinWidth(100);
		
		//ANNEE
		anneeVoiture = new Label("Année: ");
		anneeVoiture.getStyleClass().add("my-label");
		annee = new TextField();
		annee.setPromptText("année");
		annee.setMinWidth(100);
		
		//Matricule
		matriculeVoiture = new Label("Matricule: ");
		matriculeVoiture.getStyleClass().add("my-label");
		matricule = new TextField();
		matricule.setPromptText("matricule");
		matricule.setMinWidth(100);
		
		//Matricule To Update
//		matriculeToUpdate = new Label("Matricule To Update: ");
//		matriculeUpdate = new TextField();
//		matriculeUpdate.setPromptText("matricule Value");
//		matriculeUpdate.setMinWidth(100);
		
		
		//Button ajout
		ajout = new Button("Ajouter voiture");
		ajout.setId("custom-button");

		ajout.setOnAction(e -> {
			String marqueV = marque.getText();
            String modeleV = modele.getText();
            String anneeV = annee.getText();
		    String matriculeV=matricule.getText();

            Voiture nouvelleVoiture = new Voiture(marqueV, modeleV, anneeV,matriculeV);
           insertCar(nouvelleVoiture);
           marque.clear();
           modele.clear();
           annee.clear();
           matricule.clear();
		});
		GridPane.setHalignment(ajout, HPos.RIGHT);
		GridPane.setValignment(ajout, VPos.CENTER);
		
		//Button update
		update=new Button("Modifier voiture");
		update.setId("custom-button");

		update.setOnAction(e-> {
			 String marqueV = marque.getText();
			 String modeleV = modele.getText();
		     String anneeV = annee.getText();
		     String matriculeV=matricule.getText();
			 Voiture voitureToUpdate = new Voiture(marqueV, modeleV, anneeV,matriculeV);
			 updateCar(voitureToUpdate);
			
		});
		GridPane.setHalignment(update, HPos.RIGHT);
		GridPane.setValignment(update, VPos.CENTER);
		
		
		//New stage and scene  to display Table
		Stage tableStage = new Stage();
		tableVoitures = new Scene(tableView);
		//Button affichage 
		 affichage = new Button("Afficher la liste des voitures");
		 affichage.setId("custom-button");

	     affichage.setOnAction(e -> {
	    	 tableStage.setTitle("Voitures");
	    	 Image icon = new Image("logo2.png");
	    	 tableStage.getIcons().add(icon);
 
	    	 tableStage.setScene(tableVoitures);
	    	 tableStage.show();
	        });
	     GridPane.setHalignment(affichage, HPos.RIGHT);
	     GridPane.setValignment(affichage, VPos.CENTER);


		//Button suppression
		suppression = new Button("Supprimer voiture");
		suppression.setId("custom-button");

        suppression.setOnAction(e -> {
        	String marqueV = marque.getText();
			String modeleV = modele.getText();
		    String anneeV = annee.getText();
		    String matriculeV = matricule.getText();
			Voiture voitureASupprimer = new Voiture(marqueV, modeleV, anneeV,matriculeV);
			deleteCar(voitureASupprimer);
        });
        GridPane.setHalignment(suppression, HPos.RIGHT);
        GridPane.setValignment(suppression, VPos.CENTER);
        
        //Button show profile
		show = new Button("Afficher voiture");
		show.setId("custom-button");
		show.setOnAction(e->{
			    String matriculeValue = matricule.getText();
			    if (!matriculeValue.isEmpty()) {
			        Voiture selectedCar = null;
			        for (Voiture car : voituresListe) {
			            if (car.getMatricule().equals(matriculeValue)) {
			                selectedCar = car;
			                break;
			            }
			        }
			        if (selectedCar != null) {
			            CarDetailsScene carDetailsScene = createCarDetailsScene(selectedCar,primaryStage);
			            if (carDetailsScene != null) {
			                primaryStage.setScene(carDetailsScene);
			                primaryStage.show();
			            } else {
			                System.out.println("Erreur lors de la récupération des détails de la voiture.");
			            }
			        } else {
			            System.out.println("Aucune voiture trouvée avec le matricule spécifié.");
			        }
			    } else {
			        System.out.println("Veuillez entrer un matricule valide.");
			    }
			});

		GridPane.setHalignment(show, HPos.RIGHT);
		GridPane.setValignment(show, VPos.CENTER);

        
        
        
		//add Labels and Inputs to Hbox
		HBox hbox1 = new HBox(10);
		hbox1.getChildren().addAll(marqueVoiture, marque);
		HBox hbox2 = new HBox(10);
		hbox2.getChildren().addAll(modeleVoiture, modele);
		HBox hbox3 = new HBox(10);
		hbox3.getChildren().addAll(anneeVoiture, annee);
		HBox hbox4 = new HBox(10);
		hbox4.getChildren().addAll(matriculeVoiture, matricule);
		
		//Message reussite ou échec de l'action
		message = new Text();
		message.setTextAlignment(TextAlignment.CENTER);
		message.setFont(Font.font("Arial", FontWeight.BOLD, 19));
		
		//COPYRIGHT
		copyright = new Label("© 2023 Yousra MANSOUR et ZAIM Mohamed - Tous droits réservés. Fondateurs de l'application");
		copyright.setId("my-label1");
		GridPane.setHalignment(copyright, HPos.CENTER);
		GridPane.setValignment(copyright, VPos.BOTTOM);
		
		
		grid = new GridPane();
		grid.add(titre, 0, 0, 6, 1);
		grid.add(hbox1, 2, 2);
		grid.add(hbox2, 3, 2);
		grid.add(hbox3, 4, 2);
		grid.add(hbox4, 1, 2);

		
		grid.add(ajout, 4, 3); // Adjusted row number
		grid.add(update, 4, 4); // Adjusted row number
		grid.add(suppression, 4, 7); // Spanning across 1 column and 2 rows
		grid.add(affichage, 4, 6); // Adjusted row number
		grid.add(show, 4, 5); // Adjusted row number
		grid.add(message, 3, 4, 2, 1);
		grid.add(copyright, 1, 10, 6, 1);
      
		grid.setVgap(20);
		grid.setHgap(20);
		grid.setPadding(new Insets(50, 50, 50, 50));
		grid.setAlignment(Pos.CENTER);
		GridPane.setFillWidth(titre, true);
		titre.setMaxWidth(Double.MAX_VALUE);
		titre.setAlignment(Pos.CENTER);
		titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white; ");


		
		scene = new Scene(grid);
		grid.setId("grid-pane");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 //Changing icon
		Image icon = new Image("logo2.png");
		primaryStage.getIcons().add(icon);

		primaryStage.setMaximized(true);
		primaryStage.setResizable(false); // Fix the stage size	
		primaryStage.setScene(scene);
		primaryStage.show();	
		
		 
       
	}
	

	
	
	private void createTable() {
	    try {
	        String query = "CREATE TABLE IF NOT EXISTS Voitures "
	                + "(id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, "
	                + "marque VARCHAR(100), modele VARCHAR(100), "
	                + "annee VARCHAR(4), matricule VARCHAR(15) Unique)";
	        Statement statement = connection.createStatement();
	        statement.executeUpdate(query);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	




private void insertCar(Voiture nouvelleVoiture) {
		try {
		
			createTable();
			 if (nouvelleVoiture.getMarque().isEmpty() || nouvelleVoiture.getModele().isEmpty()
		                || nouvelleVoiture.getAnnee().isEmpty() || nouvelleVoiture.getMatricule().isEmpty()) {
		            message.setText("Veuillez remplir tous les champs !");
		            message.setFill(Color.RED);
		            return;
		        }
			String query3 = "INSERT INTO Voitures (marque, modele, annee, matricule) VALUES (? ,?, ?,?)";
			PreparedStatement statement = connection.prepareStatement(query3);
			statement.setString(1, nouvelleVoiture.getMarque());
			statement.setString(2, nouvelleVoiture.getModele());
			statement.setString(3, nouvelleVoiture.getAnnee());
			statement.setString(4, nouvelleVoiture.getMatricule());

			statement.executeUpdate();
			message.setText("Voiture ajoutée avec succès !");
	        message.setFill(Color.GREEN);
	        
			
		}catch(SQLException e) {
			e.printStackTrace();
			// Afficher le message d'erreur
	        message.setText("Erreur lors de l'ajout de la voiture !");
	        message.setFill(Color.RED);
		}
	}
	private void updateCar(Voiture voiture) {
	    try {
	    	if (voiture.getMarque().isEmpty() || voiture.getModele().isEmpty()
	                || voiture.getAnnee().isEmpty() || voiture.getMatricule().isEmpty()) {
	            message.setText("Veuillez remplir tous les champs !");
	            message.setFill(Color.RED);
	            return;
	        }
	        String updateQuery = "UPDATE Voitures SET modele = ?, annee = ?,marque= ? WHERE matricule = ?";
	        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
	        updateStatement.setString(1, voiture.getModele());
	        updateStatement.setString(2, voiture.getAnnee());
	        updateStatement.setString(3, voiture.getMarque());
	        updateStatement.setString(4, voiture.getMatricule());

	        int rowsUpdated = updateStatement.executeUpdate();

	        if (rowsUpdated > 0) {
	        	message.setText("Voiture modifiée avec succès !");
		        message.setFill(Color.GREEN);
	        } 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        message.setText("Erreur lors de la modification de la voiture !");
	        message.setFill(Color.RED);
	    }
	    
	}
	private void deleteCar(Voiture carToDelete) {
        try {
        	if (carToDelete.getMatricule().isEmpty()) {
	            message.setText("Veuillez remplir tous les champs !");
	            message.setFill(Color.RED);
	            return;
	        }
            String query = "DELETE FROM Voitures WHERE matricule = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, carToDelete.getMatricule());
            statement.executeUpdate();
            message.setText("Voiture supprimée avec succès !");
	        message.setFill(Color.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            message.setText("Erreur lors de la suppression de la voiture !");
	        message.setFill(Color.RED);
        }
    }
	private CarDetailsScene createCarDetailsScene(Voiture voiture,Stage primaryStage) {
	    try {
	        String query = "SELECT marque, modele, annee, matricule FROM Voitures WHERE matricule = ?";
	        PreparedStatement selectStatement = connection.prepareStatement(query);
	        selectStatement.setString(1, voiture.getMatricule());
	        ResultSet resultSet = selectStatement.executeQuery();

	        if (resultSet.next()) {
	            String marque = resultSet.getString("marque");
	            String modele = resultSet.getString("modele");
	            String annee = resultSet.getString("annee");
	            String matricule = resultSet.getString("matricule");

	            Voiture car = new Voiture(marque, modele, annee, matricule);
	            return new CarDetailsScene(car,scene);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	

}