import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class MyThread implements Runnable {
	
	//creating variables to store
	//Imageview to store images
	private  ImageView img;
	private  Reel reel;
	private int i = 0;
	private boolean spin;
	
	//constructor 
	public MyThread(ImageView img, Reel reel) {
		this.img = img;
		this.reel = reel;
		
	}

	/*public MyThread(ImageView img, Reel reel, Reel reel2, Reel reel3, Reel reel1) {
		this.img = img;
		this.reel = reel;
	}
	*/
	
	public void setSpin(boolean spin){
		this.spin = spin;
	}
	
	@Override
	public void run() {
		//execute when spinning
		while(spin){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
						i++;
						//used get a loop which has 0-6 but that doesn't stop.
						if(i == reel.getReel().size()){
							i = 0;
						}
					//Shuffle the reel to get random images
					reel.shuffle();
					
					img.setImage(reel.getReel().get(i).getImage());
					
				}
			});
		
			try {
				//setting the thread to sleep 
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	//getting the values to the slotmachine to calculate
	public int getValue(){
		return reel.getReel().get(i).getValue();
	}
	
}