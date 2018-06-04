import javafx.scene.image.Image;

//interface contains behaviours that a class implements. (abstract)
public interface ISymbol {

	void setImage(Image image);

    String getImage();

    void setValue(int v);

    int getValue();
	
}

