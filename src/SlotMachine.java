import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage; 

public class SlotMachine extends Application {
	
	//Declared variables
	static int win = 0;
	static int losses = 0;
	static int startCoins =10;
	private int numberOfMatches;	
	private int bet = 0;
	static String status = "";
	static int numberWin;
	static int numberLosses;
	static double averageCredits;
	private static int wincomb1 = 0;
	private static int wincomb2 = 0;
	private static int wincomb3 = 0;
	private static int wincomb4 = 0;
	
	//Adding reels to the SlotMachine
	private static Reel reel1 = new Reel();
	private static Reel reel2 = new Reel();
	private static Reel reel3 = new Reel();
	
	//values for images
	private static int val1 = 0;
	private static int val2 = 0;
	private static int val3 = 0;
	
	//getting the images for reels
	private static ImageView img1;
	private static ImageView img2;
	private static ImageView img3;
	
	//thread class 
	MyThread mt1;
	MyThread mt2;
	MyThread mt3;
	
	//booleans to start and stop spinning
	private static boolean isSpinning = true;
	private static boolean reel1Spining = true;
    private static boolean reel2Spining = true;
    private static boolean reel3Spining = true;
    
		public static boolean isSpinning() {
			return isSpinning;
		}
		public static boolean reel1Spining() {
			return reel1Spining;
		}
		public static boolean reel2Spining() {
			return reel2Spining;
		}
		public static boolean reel3Spining() {
			return reel3Spining;
		}
		
		//Getter for total matches
		public int getNumberOfMatches() {
			return numberOfMatches;
		}

		//Setter for total matches
		public void setNumberOfMatches() {
			numberOfMatches++;
		}
		

	//Main method is to run the application
	public static void main(String[] args){
		launch(args);
	}

	@Override
	//creating the page layout
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("Slot Machine");
      
        //Create a GridPane Layout
        GridPane grid = new GridPane();
        //keep gridPane at original size(to resize)
        grid.setMinSize(1000, 1000);
        grid.setMaxSize(1000, 1000);
        
        //resize grid has passed in to stackPane
        StackPane root = new StackPane(grid);
        NumberBinding maxScale = Bindings.min(root.widthProperty().divide(1000),root.heightProperty().divide(1000));
        grid.scaleXProperty().bind(maxScale);
        grid.scaleYProperty().bind(maxScale);
        
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(100, 10, 10, 10));
        //vertical spacing
        grid.setVgap(50);
        //horizontal spacing
        grid.setHgap(50);
        
        //Creating hbox 
        HBox hBox = new HBox();
        
        //Add the label Topic     
        Label topic = new Label("Slot Machine");
        topic.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        GridPane.setConstraints(topic, 1, 0);
        topic.setAlignment(Pos.CENTER);
        
        //placing images for reel1
        img1 = new ImageView(reel1.getReel().get(1).getImage());
        img1.setFitHeight(200);
        img1.setFitWidth(200);
        GridPane.setConstraints(img1, 0, 2);
        //To align horizontally in the cell
        GridPane.setHalignment(img1, HPos.CENTER);
        //To align vertically in the cell
        GridPane.setValignment(img1, VPos.CENTER); 
        
        //placing images for reel2
        img2 = new ImageView(reel2.getReel().get(1).getImage());
        img2.setFitHeight(200);
        img2.setFitWidth(200);
        GridPane.setConstraints(img2, 1, 2);
        GridPane.setHalignment(img2, HPos.CENTER); 
        GridPane.setValignment(img2, VPos.CENTER); 
        
        //placing images for reel3
        img3 = new ImageView(reel3.getReel().get(1).getImage());
        img3.setFitHeight(200);
        img3.setFitWidth(200);
        GridPane.setConstraints(img3, 2, 2);
        GridPane.setHalignment(img3, HPos.CENTER); 
        GridPane.setValignment(img3, VPos.CENTER); 
        
        //Making the buttons same size
        VBox vBox = new VBox();
        vBox.setPrefWidth(100);
        
        //label for credits
        Label creditArea = new Label();
		GridPane.setConstraints(creditArea, 1, 6);
		
		//label for bets
		Label betArea = new Label();
		GridPane.setConstraints(betArea, 1, 7);
		
		//label for information
		Label info = new Label();
		GridPane.setConstraints(info, 1, 5);
		
		//setText method to set the text in to label
		creditArea.setText("Remaining Coins: " + startCoins);
		betArea.setText("Your bet: "+ bet);
		info.setText("Info: "+ status);

		//Adding images from MyThread to reels
		mt1 = new MyThread(img1, reel1);
		mt2 = new MyThread(img2, reel2);
		mt3 = new MyThread(img3, reel3);
		
		//Add coins 
        Button btnAddCoin = new Button();
		btnAddCoin.setText("Add Coin");
		GridPane.setConstraints(btnAddCoin, 0, 6);
		//make the text of the button in to center
		btnAddCoin.setAlignment(Pos.TOP_CENTER);
		btnAddCoin.setMinWidth(vBox.getPrefWidth());
		
			//button click event for add coin
			btnAddCoin.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					addCoin();
				}
				
				private void addCoin() {
					startCoins++;
					creditArea.setText("Remaining Coins: " + startCoins);
				}				
			});
			
			
		//spin button
	    Button btnSpin = new Button();
		btnSpin.setText("Spin");
		//set an id for styling
		btnSpin.setId("spin");
		GridPane.setConstraints(btnSpin, 1, 8);
		btnSpin.setPrefWidth(300);
		btnSpin.setAlignment(Pos.CENTER);
		GridPane.setHalignment(btnSpin, HPos.CENTER); 
	    GridPane.setValignment(btnSpin, VPos.CENTER);  
	    
	    //button click event to spin
        btnSpin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				spin();
				
				if(bet>0){
					//To disable the spin button for multiple clicks
					btnSpin.setDisable(true);
				}
			}

			//spin method
			public void spin(){		
				if(bet<=0){
					//Adding the warning to the spin
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Look, a Warning Dialog");
					alert.setContentText("You have to bet first to continue.");
					alert.showAndWait();
				} else{
					
					//setting true to spin
					mt1.setSpin(true);
					mt2.setSpin(true);
					mt3.setSpin(true);
					
					//Adding images from the reel to the thread
					Thread t1 = new Thread(mt1);
					//starting the thread 
					t1.start();
							
					Thread t2 = new Thread(mt2);
					t2.start();
					
					Thread t3 = new Thread(mt3);
					t3.start();
					
					//count the number of spins
					numberOfMatches++;
				}
			}
		});
        		
		//Bet one 
        Button btnBetOne = new Button();
		btnBetOne.setText("Bet One");
		GridPane.setConstraints(btnBetOne, 0, 7);
		btnBetOne.setAlignment(Pos.TOP_CENTER);
		btnBetOne.setMinWidth(vBox.getPrefWidth());
		
		//button click event
		btnBetOne.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				oneBet();
				//To enable spin button again for inputs
                btnSpin.setDisable(false);
			}

			private void oneBet() {
				if(bet>=3){
					//Adding a warning to the betting
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Look, a Warning Dialog");
					alert.setContentText("You can't bet more than 3!");
					alert.showAndWait();
				}else if (startCoins <= 0) {
					//Adding a warning to the betting
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Look, a Warning Dialog");
					alert.setContentText("Add more credits to continue");
					alert.showAndWait();
				}else{
					startCoins--;
					bet++;
					creditArea.setText("Remaining Coins: " + startCoins);
					betArea.setText("Your bet: "+ bet);
				}
				//setting image values 0 for new round
				val1 = 0;
				val2 = 0;
				val3 = 0;
			}
		});
		
		//Bet max
		Button btnBetMax = new Button();
		btnBetMax.setText("Bet Max");
		GridPane.setConstraints(btnBetMax, 2, 6);
		btnBetMax.setAlignment(Pos.TOP_CENTER);
		btnBetMax.setMinWidth(vBox.getPrefWidth());
		
		//button click event for Bet max
		btnBetMax.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				maxBet();
				//To enable spin button again for inputs
                btnSpin.setDisable(false);
            }

			private void maxBet() {				
				if (startCoins <= 0) {
					//Adding a warning to the betting
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Look, a Warning Dialog");
					alert.setContentText("Add more credits to continue");
					alert.showAndWait();					
				}else if(bet>=3){
					//Adding a warning to the betting
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Look, a Warning Dialog");
					alert.setContentText("You can only bet max once");
					alert.showAndWait();					
				} else if (bet == 0){
					startCoins = startCoins - 3;
					bet = 3;
					creditArea.setText("Remaining Coins: " + startCoins);
					betArea.setText("Your bet: "+ bet);					
				}else if (bet == 1){
					startCoins = startCoins - 2;
					bet = 3;
					creditArea.setText("Remaining Coins: " + startCoins);
					betArea.setText("Your bet: "+ bet);					
				}else if (bet == 2){
					startCoins = startCoins - 1;
					bet = 3;
					creditArea.setText("Remaining Coins: " + startCoins);
					betArea.setText("Your bet: "+ bet);
				}
				//setting image values 0 for new round
				val1 = 0;
				val2 = 0;
				val3 = 0;
			}
		});
		
		//Reset
		Button btnReset = new Button();
		btnReset.setText("Reset");
		GridPane.setConstraints(btnReset, 2, 7);
		btnReset.setAlignment(Pos.TOP_CENTER);
		btnReset.setMinWidth(vBox.getPrefWidth());
		
		//button click event for reset
		btnReset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(bet>0){
					setReset();
				}else{
					//Adding a warning to the reset
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Look, a Warning Dialog");
					alert.setContentText("Bet some coins to Reset");
					alert.showAndWait();
				}
				
			}
			
			//Resetting bets
			public void setReset() {
				startCoins = startCoins + bet;
				bet = 0;
				creditArea.setText("Remaining Coins: " + startCoins);
				betArea.setText("Your bet: "+ bet);
			}
		});
		
        //action for image click
        img1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse pressed");
                //stopping the reel
                mt1.setSpin(false);
                
                //getting the values from MyThread for the images
                val1 = mt1.getValue();
                System.out.println(val1);
                if(val1>1 && val2>1 && val3>1){
                	results();
                }
                //Stop the spin
                stopSpin();
            }
            
            //conditions to win the game
            private void results() {
            	//getting the conditions to check win or loss
                if((val1 == val2) && (val1 == val3)){
                	status = "You win!";
                	info.setText(status);
                	win = val1*bet;
                	startCoins = startCoins + win;
                	numberWin++;
                	creditArea.setText("Remaining Coins: " + startCoins);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	//getting the number of winning combinations
                	wincomb1++;
                	Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("You Won");
					alert.setContentText("You Won $"+ win);
					alert.showAndWait();
                	
                } else if(val1 == val2){
                	status = "You win!�";
                	info.setText(status);
                	win = val1*bet;
                	startCoins = startCoins + win;
                	numberWin++;
                	creditArea.setText("Remaining Coins: " + startCoins);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	//getting the number of winning combinations
                	wincomb2++;
                	Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("You Won");
					alert.setContentText("You Won $"+ win);
					alert.showAndWait();
					
                } else if (val1 == val3){
                	status = "You win!�";
                	info.setText(status);
                	win = val1*bet;
                	startCoins = startCoins + win;
                	numberWin++;
                	creditArea.setText("Remaining Coins: " + startCoins);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	//getting the number of winning combinations
                	wincomb3++;
                	Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("You Won");
					alert.setContentText("You Won $"+ win);
					alert.showAndWait();
                	
                } else if (val2 == val3){
                	status = "You win!�";
                	info.setText(status);
                	win = val2*bet;
                	startCoins = startCoins + win;
                	numberWin++;
                	creditArea.setText("Remaining Coins: " + startCoins);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	//getting the number of winning combinations
                	wincomb4++;
                	Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("You Won");
					alert.setContentText("You Won $"+ win);
					alert.showAndWait();
                	
                } else{
                	status = "You lost!";
                	info.setText(status);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	numberLosses++;
                	Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("You Lost!");
					alert.setHeaderText("LOST!");
					alert.setContentText("You have lost "+ numberLosses+" times.");
					alert.showAndWait();
                }
			}
            //stop the spinning for mouse click
			private void stopSpin() {
            	btnSpin.setOnMouseClicked(new EventHandler<MouseEvent>() {
                  public void handle(MouseEvent me) {
                	  //isSpinning = true;
                	  reel1Spining = true;  
                	  
                  }
                });
			}
        });
        
        img2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
            	mt2.setSpin(false);
                System.out.println("Mouse pressed");
                
                //getting the value of the image
                val2 = mt2.getValue();
                System.out.println(val2);
                if(val1>1 && val2>1 && val3>1){
                	//System.out.println("hello ");
                	results();
                }
                stopSpin();
            }

            private void results() {
            	if((val1 == val2) && (val1 == val3)){
                	status = "You win!�";
                	info.setText(status);
                	win = val1*bet;
                	startCoins = startCoins + win;
                	numberWin++;
                	creditArea.setText("Remaining Coins: " + startCoins);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	//getting the number of winning combinations
                	wincomb1++;
                	Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("You Won");
					alert.setContentText("You Won $"+ win);
					alert.showAndWait();

                } else if(val1 == val2){
                	status = "You win!�";
                	info.setText(status);
                	win = val1*bet;
                	startCoins = startCoins + win;
                	numberWin++;
                	creditArea.setText("Remaining Coins: " + startCoins);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	//getting the number of winning combinations
                	wincomb2++;
                	Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("You Won");
					alert.setContentText("You Won $"+ win);
					alert.showAndWait();
                	
                } else if (val1 == val3){
                	status = "You win!�";
                	info.setText(status);
                	win = val1*bet;
                	startCoins = startCoins + win;
                	numberWin++;
                	creditArea.setText("Remaining Coins: " + startCoins);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	//getting the number of winning combinations
                	wincomb3++;
                	Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("You Won");
					alert.setContentText("You Won $"+ win);
					alert.showAndWait();
                	
                } else if (val2 == val3){
                	status = "You win!�";
                	info.setText(status);
                	win = val2*bet;
                	startCoins = startCoins + win;
                	numberWin++;
                	creditArea.setText("Remaining Coins: " + startCoins);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	//getting the number of winning combinations
                	wincomb4++;
                	Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("You Won");
					alert.setContentText("You Won $"+ win);
					alert.showAndWait();
                	
                } else{
                	status = "You lost!";
                	info.setText(status);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	numberLosses++;
                	
                	Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("You Lost!");
					alert.setHeaderText("LOST!");
					alert.setContentText("You have lost "+ numberLosses+" times.");
					alert.showAndWait();
                }
			}

			private void stopSpin() {
            	isSpinning = false;
            	btnSpin.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                    	isSpinning = true;
                    	//btnSpin.spin();
                    	
                    }
                });
			}
        });
        
        img3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
            	mt3.setSpin(false);
                System.out.println("Mouse pressed");
                
                //getting the value of the image
                val3 = mt3.getValue();
                System.out.println(val3);
                if(val1>1 && val2>1 && val3>1){
                	//System.out.println("hello ");
                	results();
                }
                stopSpin();
            }

            private void results() {
            	if((val1 == val2) && (val1 == val3)){
                	status = "You win!�";
                	info.setText(status);
                	win = val1*bet;
                	startCoins = startCoins + win;
                	numberWin++;
                	creditArea.setText("Remaining Coins: " + startCoins);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	//getting the number of winning combinations
                	wincomb1++;
                	Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("You Won");
					alert.setContentText("You Won $"+ win);
					alert.showAndWait();
                	
                } else if(val1 == val2){
                	status = "You win!�";
                	info.setText(status);
                	win = val1*bet;
                	startCoins = startCoins + win;
                	numberWin++;
                	creditArea.setText("Remaining Coins: " + startCoins);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	//getting the number of winning combinations
                	wincomb2++;
                	Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("You Won");
					alert.setContentText("You Won $"+ win);
					alert.showAndWait();
                	
                } else if (val1 == val3){
                	status = "You win!�";
                	info.setText(status);
                	win = val1*bet;
                	startCoins = startCoins + win;
                	numberWin++;
                	creditArea.setText("Remaining Coins: " + startCoins);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	//getting the number of winning combinations
                	wincomb3++;
                	Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("You Won");
					alert.setContentText("You Won $"+ win);
					alert.showAndWait();
                	
                } else if (val2 == val3){
                	status = "You win!�";
                	info.setText(status);
                	win = val2*bet;
                	startCoins = startCoins + win;
                	numberWin++;
                	creditArea.setText("Remaining Coins: " + startCoins);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	//getting the number of winning combinations
                	wincomb4++;
                	Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("You Won");
					alert.setContentText("You Won $"+ win);
					alert.showAndWait();
                	
                } else{
                	status = "You lost!";
                	info.setText(status);
                	bet = 0;
                	betArea.setText("Your bet: "+ bet);
                	numberLosses++;
                	
                	Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("You Lost!");
					alert.setHeaderText("LOST!");
					alert.setContentText("You have lost "+ numberLosses+" times.");
					alert.showAndWait();
                }
			}

			private void stopSpin() {
            	isSpinning = false;
            	btnSpin.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                    	isSpinning = true;
                    }
                });
			}
        });
        
        
        //Statistics button
        Button btnStatistics = new Button();
		btnStatistics.setText("Statistics");
		GridPane.setConstraints(btnStatistics, 1, 9);
		btnStatistics.setPrefWidth(300);
		btnStatistics.setAlignment(Pos.CENTER);
		GridPane.setHalignment(btnStatistics, HPos.CENTER); 
        GridPane.setValignment(btnStatistics, VPos.CENTER);
        
        //button click event for statistics
        btnStatistics.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
			public void handle(ActionEvent event) {
        		if(numberOfMatches>0){
        			viewStatics();
        			System.out.println("All three reels stop at the same symbol "+wincomb1);
        			System.out.println("Reel one and Reel two stops at the same symbol "+wincomb2);
        			System.out.println("Reel one and Reel three stops at the same symbol "+wincomb3);
        			System.out.println("Reel two and Reel three stops at the same symbol "+wincomb4);
        		}else{
        			Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Look, a Warning Dialog");
					alert.setContentText("Play the game first!");
					alert.showAndWait();
        		}
			}

			private void viewStatics() {
				//creating a nw window to show details
				Stage stage = new Stage();
				stage.setTitle("Statistics");
			    
				
		        //Create a GridPane Layout
		        GridPane grid = new GridPane();
		        
		        //keep gridPane at original size(to resize)
		        grid.setMinSize(1000, 1000);
		        grid.setMaxSize(1000, 1000);
		        
		        //resize grid has passed in to stackPane
		        StackPane root = new StackPane(grid);
		        NumberBinding maxScale = Bindings.min(root.widthProperty().divide(1000),root.heightProperty().divide(1000));
		        grid.scaleXProperty().bind(maxScale);
		        grid.scaleYProperty().bind(maxScale);
		        
		        
		        
		        grid.setAlignment(Pos.TOP_CENTER);
		        grid.setPadding(new Insets(100, 10, 10, 10));
		        //vertical spacing
		        grid.setVgap(10);
		        //horizontal spacing
		        grid.setHgap(10);
		        
		        Label topic = new Label("Statistics");
		        GridPane.setConstraints(topic, 1, 0);
		        topic.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		        topic.setAlignment(Pos.CENTER);
		        GridPane.setValignment(topic, VPos.CENTER); 
		        
		        //Adding the pie chart
		        PieChart pieChart = new PieChart();
                PieChart.Data p1 = new PieChart.Data("Wins", numberWin);
                PieChart.Data p2 = new PieChart.Data("Losses"  , numberLosses);
                //wins
                pieChart.getData().add(p1);
                //losses
                pieChart.getData().add(p2);
                //adding pie chart to the grid
                GridPane.setConstraints(pieChart, 0, 2);
                
                Label numMatches = new Label("  Number of matches: " + numberOfMatches);
                GridPane.setConstraints(numMatches, 2, 3);
                System.out.println(numberOfMatches);
                Label winStat = new Label("  Number of wins: "+ numberWin);
                GridPane.setConstraints(winStat, 2, 4);
                Label lossStat = new Label("  Number of loses: " + numberLosses);
                GridPane.setConstraints(lossStat, 2, 5);
                
                //calculate the average
                averageCredits = ((numberWin - numberLosses) / numberOfMatches);
                Label averageStat = new Label("  Average number of credits: "+ averageCredits );
                GridPane.setConstraints(averageStat, 2, 6);
                
                //button to save statistics. 
                Button save = new Button();
        		save.setText("Save Statistics");
        		GridPane.setConstraints(save, 0, 7);
        		//make the text of the button in to center
        		save.setAlignment(Pos.TOP_CENTER);
        		save.setMinWidth(vBox.getPrefWidth());
        		
        		save.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                    	saveData();
                    }

                    //Save the statistics 
        			private void saveData() {
        				//getting the date and time
        				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        		        Date dateobj = new Date();
        		        String date = df.format(dateobj);
        		        String fileName = date + ".txt";
        				if(numberOfMatches > 0){
        					try {
        						//saving file
								PrintWriter outputFile = new PrintWriter(fileName);
								outputFile.println("Number of Matches:" + getNumberOfMatches()+'\n');
        						outputFile.println("Number of wins: "+ numberWin +'\n');
        						outputFile.println("Number of loses: " + numberLosses +'\n');
        						outputFile.println("Average number of credits: "+ averageCredits +'\n');
        						outputFile.println("\t Thank you for Playing. ");
								//closing the printwriter
                                outputFile.close();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
        			}
        		}
               });
        		
        		//button to go back.
        		Button back = new Button();
        		back.setText("Back");
        		GridPane.setConstraints(back, 1, 7);
        		
        		//make the text of the button in to center
        		back.setAlignment(Pos.TOP_CENTER);
        		back.setMinWidth(vBox.getPrefWidth());
        		
        		//set action for the back button
        		back.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                    	//close the window
                    	stage.close();
                    }
        		});
        		
		        grid.getChildren().addAll(topic, pieChart,  winStat, lossStat, averageStat, numMatches, back, save);
		        Scene scene = new Scene(root, 1000, 1000);
		        scene.getStylesheets().add("Style.css");
		        stage.setScene(scene);
				stage.show();
			}
        });
        
        //Adding items to the gui
		vBox.getChildren().addAll(btnAddCoin, btnBetOne, btnBetMax, btnReset);
		hBox.getChildren().add(img1);
        hBox.getChildren().add(img2);
        hBox.getChildren().add(img3);
		grid.getChildren().addAll(topic, btnAddCoin, btnBetOne, btnBetMax, btnReset, btnSpin, btnStatistics, img1, img2, img3, creditArea, betArea, info);
		
		//set the window and set the size
        Scene scene = new Scene(root, 1000, 1000);
        scene.getStylesheets().add("Style.css");
        primaryStage.setScene(scene);
        //Displaying to the user   
        primaryStage.show();
    }
}