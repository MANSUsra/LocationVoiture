package application;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class CarDetailsScene extends Scene {
	
    public CarDetailsScene(Voiture voiture, Scene firstScene) {
        super(new VBox(),350,250);
        VBox container = (VBox) getRoot();
        container.setSpacing(10);
        container.setPadding(new Insets(20));
        
        
        Stage primaryStage = (Stage) firstScene.getWindow();
        primaryStage.setResizable(false); // Disable resizing
        primaryStage.setMaximized(false);
        primaryStage.setX(600); // Set the X coordinate of the stage
        primaryStage.setY(250); // Set the Y coordinate of the stage
        
        
        Label marqueLabel = new Label("Marque: " + voiture.getMarque());
        Label modeleLabel = new Label("Modèle: " + voiture.getModele());
        Label anneeLabel = new Label("Année: " + voiture.getAnnee());
        Label matriculeLabel = new Label("Matricule: " + voiture.getMatricule());

        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> {
        	primaryStage.setScene(firstScene);
        	primaryStage.setMaximized(true);
            primaryStage.show();
        });
        
        //Print Button 
        Button printButton = new Button("Imprimer");
        printButton.setOnAction(event -> {
            printScene();
        });
        
        container.getChildren().addAll(marqueLabel, modeleLabel, anneeLabel, matriculeLabel, backButton, printButton);
     
     container.setStyle(
    		    "-fx-background-color: #818184;" +
    		    	    "-fx-font-family: Arial;" +
    		    	    "-fx-font-size: 14px;"
    		    	);

    		    	marqueLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
    		    	modeleLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
    		    	anneeLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
    		    	matriculeLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

    		    	backButton.setStyle(
    		    	    "-fx-background-color: #601417;" +
    		    	    "-fx-text-fill: white;" +
    		    	    "-fx-border-color: transparent;" +
    		    	    "-fx-border-radius: 0.75rem;" +
    		    	    "-fx-padding: 0.75rem 1.2rem;" +
    		    	    "-fx-font-family: 'Inter var', 'ui-sans-serif', 'system-ui', 'Segoe UI', Roboto, 'Helvetica Neue', Arial, 'Noto Sans', sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji';" +
    		    	    "-fx-font-size: 1.125rem;" +
    		    	    "-fx-font-weight: 600;" +
    		    	    "-fx-line-height: 1.5rem;" +
    		    	    "-fx-text-decoration: none #6B7280 solid;" +
    		    	    "-fx-transition-duration: 0.2s;" +
    		    	    "-fx-transition-property: background-color, border-color, color, fill, stroke;" +
    		    	    "-fx-transition-timing-function: cubic-bezier(.4, 0, 0.2, 1);" +
    		    	    "-fx-cursor: hand;"
    		    	);

    		    	printButton.setStyle(
    		    	    "-fx-background-color: #601417;" +
    		    	    "-fx-text-fill: white;" +
    		    	    "-fx-border-color: transparent;" +
    		    	    "-fx-border-radius: 0.75rem;" +
    		    	    "-fx-padding: 0.75rem 1.2rem;" +
    		    	    "-fx-font-family: 'Inter var', 'ui-sans-serif', 'system-ui', 'Segoe UI', Roboto, 'Helvetica Neue', Arial, 'Noto Sans', sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji';" +
    		    	    "-fx-font-size: 1.125rem;" +
    		    	    "-fx-font-weight: 600;" +
    		    	    "-fx-line-height: 1.5rem;" +
    		    	    "-fx-text-decoration: none #6B7280 solid;" +
    		    	    "-fx-transition-duration: 0.2s;" +
    		    	    "-fx-transition-property: background-color, border-color, color, fill, stroke;" +
    		    	    "-fx-transition-timing-function: cubic-bezier(.4, 0, 0.2, 1);" +
    		    	    "-fx-cursor: hand;"
    		    	);


         
        


       
    }

 

    private void printScene() {
    	
    	// Hide the buttons temporarily
        Button backButton = null;
        Button printButton = null;
    	    
        // Find the buttons in the container
        for (Node node : ((VBox) getRoot()).getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getText().equals("Retour")) {
                    backButton = button;
                } else if (button.getText().equals("Imprimer")) {
                    printButton = button;
                }
            }
        }
    	
    	 // Save the original visibility state of the buttons
    	    boolean backButtonVisible = backButton.isVisible();
    	    boolean printButtonVisible = printButton.isVisible();
    	    
    	    // Hide the buttons
    	    backButton.setVisible(false);
    	    printButton.setVisible(false);
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(getRoot().getScene().getWindow())) {
            boolean success = printerJob.printPage(getRoot());
            if (success) {
                printerJob.endJob();
            }else {
                // Print job was cancelled, restore the visibility of the buttons
                backButton.setVisible(backButtonVisible);
                printButton.setVisible(printButtonVisible);
            }
          
        }else {
            // Print dialog was cancelled, restore the visibility of the buttons
            backButton.setVisible(backButtonVisible);
            printButton.setVisible(printButtonVisible);
        }
            
        
        // Restore the original visibility state of the buttons
        backButton.setVisible(backButtonVisible);
        printButton.setVisible(printButtonVisible);
    
    }
 }
