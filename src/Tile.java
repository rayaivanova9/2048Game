import javax.swing.*;
import java.util.Random;

public class Tile {

    private final int SIZE = 100;
    Random rand = new Random();

    public int getValue(){
        int number = rand.nextInt(100);
        int value;

        if(number < 51){
            value = 2;
        } else if(number < 91){
            value = 4;
        } else {
            value = 8;
        }
        return value;
    }

    public void setValue(int value){

        JLabel label = new JLabel();
        label.setText(getValue()+ "");

    }

    public void setColor() {
        int color = getValue();

    }
}
