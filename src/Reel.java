import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.image.Image;

public class Reel {	
	
	//creating the arrayList and taking inputs
	private  ArrayList<Symbol> reel = new ArrayList<Symbol>();
	
	//return reel
	public ArrayList<Symbol> getReel() {
		return reel;
	}
	
	//constructor to add symbols to reels
	public Reel(){
		reel.add(new Symbol(new Image("images/redseven.png"),7));
		reel.add(new Symbol(new Image("images/bell.png"),6));
		reel.add(new Symbol(new Image("images/cherry.png"),2));
		reel.add(new Symbol(new Image("images/lemon.png"),3));
		reel.add(new Symbol(new Image("images/plum.png"),4));
		reel.add(new Symbol(new Image("images/watermelon.png"),5));
	}
	
	//Shuffling the reels for output
	public void shuffle(){
		Collections.shuffle(reel);
	}
}
