import javafx.scene.image.Image;

public class Symbol {
	
	//declaring instance variables
	private Image image;
    private int value;
    
    public Symbol() {
		super();
	}
    
	//constructor using fields
	public Symbol(Image image, int value) {
		super();
		this.image = image;
		this.value = value;
	}
	
	//getters and setters for images and values
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
}