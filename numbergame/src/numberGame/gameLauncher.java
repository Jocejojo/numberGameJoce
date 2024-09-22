package numberGame;

import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class gameLauncher extends Application {

	int targetNum;
	int leftAttempts = 10;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Guessing Game");
		this.setTargetNum();
		
		final Label guessLabel = new Label("Guess the number (1-100) within 10 tries:");
		TextField textInput = new TextField();
		textInput.setPromptText("Enter 1-100 here");
		Button submitBtn = new Button("Submit");
		
		//Set button image
		Image btnImage = new Image(getClass().getResourceAsStream("button.png"));
		ImageView view = new ImageView(btnImage);
		view.setFitHeight(30);
		view.setFitWidth(30);
		submitBtn.setGraphic(view);
		submitBtn.setContentDisplay(ContentDisplay.RIGHT);
		
		Label feedbackLabel = new Label();
		Label attemptLabel = new Label("Attempts left: " + leftAttempts);
		
		//Set winning image
		Image winImage = new Image(getClass().getResourceAsStream("SmileyFace.png"));
		ImageView winView = new ImageView(winImage);
		winView.setFitHeight(30);
		winView.setFitWidth(40);
		
		//Set losing image
		Image loseImage = new Image(getClass().getResourceAsStream("SadFace.png"));
		ImageView loseView = new ImageView(loseImage);
		loseView.setFitHeight(30);
		loseView.setFitWidth(40);
		
		
		submitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				String input = textInput.getText();
				
				try {				
					int guessNum = Integer.parseInt(input);//Transfer input to integer
					guessProcessor(guessNum, feedbackLabel, attemptLabel, submitBtn,textInput,winView,loseView);				
				}catch (NumberFormatException e) {
					feedbackLabel.setText("Invalid input. Must enter an integer");
				}
				
				textInput.clear();
			}			
		});
		
		VBox vBox = new VBox(10, guessLabel, textInput, submitBtn, feedbackLabel, attemptLabel);
		vBox.setAlignment(Pos.CENTER);
		vBox.setPadding(new Insets(25,25,25,25));
		
		Scene scene = new Scene(vBox);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setWidth(500);
		primaryStage.show();
	}

	public void setTargetNum() {
		Random r = new Random();
		targetNum = r.nextInt(100) + 1;//Set random number between 1-100	
	}
	
	public void guessProcessor(int guess, Label feedback, Label attempt, Button btn, TextField textInput, ImageView win, ImageView lose) {
		if(guess < 1 || guess > 100) {
			feedback.setText("Please enter a number between 1 and 100.");
		}else {
			this.leftAttempts--;
			
			if(guess == this.targetNum) {
				feedback.setText("Congrats! You've guessed the correct number!");
				feedback.setGraphic(win);
	            btn.setDisable(true);          
	            textInput.setDisable(true); 
			}else if(this.leftAttempts == 0) {
				feedback.setText("You've run out of times. The correct number is " + this.targetNum);
				feedback.setGraphic(lose);
				btn.setDisable(true);
			}else if (guess < this.targetNum) {
				feedback.setText("Guess is lower than the target.");
			}else {
				feedback.setText("Guess is higher than the target.");
			}
			attempt.setText("Attempt(s) left: " + this.leftAttempts);
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
