/**
 * Class Description: this program provides means for drawing a tile, getting and setting
 * its value, merging two tiles, and the movement of a tile into an empty square
**/

public class Tile {
    
    private int value;
    private double coorX;
    private double coorY;
    
    /**
     * **CONSTRUCTOR**
     * Inputs: value of tile, and the position on the board in terms of row
     * and columns
     * Outputs: n/a
     * Description: assigns correct x-coordinate and y-coordinate to Tile based on
     * its row/col position
    */
    public Tile(int value, int row, int col) {
        this.value = value;
        if (col == 0) {
            coorX = 0.125;
        } else if (col == 1) {
            coorX = 0.375;
        } else if (col == 2) {
            coorX = 0.625;
        } else if (col == 3) {
            coorX = 0.875;
        } else {
            throw new IllegalArgumentException("column must be between 0 and 3");
        }
        if (row == 0) {
            coorY = 0.125;
        } else if (row == 1) {
            coorY = 0.375;
        } else if (row == 2) {
            coorY = 0.625;
        } else if (row == 3) {
            coorY = 0.875;
        } else {
            throw new IllegalArgumentException("row must be between 0 and 3");
        }
    }
    
    /**
     * Inputs: n/a
     * Outputs: n/a
     * Description: draws the tile at its correct coordinates with correct color
     * based on its value, with its value visibly written on top of the tile
    */
    public void draw() {
        if (this.value != -1) {
            PennDraw.setFontSize(52);
            // change pen color based on value
            if (this.value == 2) {
                PennDraw.setPenColor(244, 255, 229);
            } else if (this.value == 4) {
                PennDraw.setPenColor(229, 249, 199);
            } else if (this.value == 8) {
                PennDraw.setPenColor(209, 238, 166);
            } else if (this.value == 16) {
                PennDraw.setPenColor(185, 221, 132); 
            } else if (this.value == 32) {
                PennDraw.setPenColor(129, 170, 68); 
            } else if (this.value == 64) {
                PennDraw.setPenColor(158, 198, 99);
            } else if (this.value == 128) {
                PennDraw.setFontSize(40);
                PennDraw.setPenColor(97, 136, 40);
            } else if (this.value == 256) {
                PennDraw.setFontSize(40);
                PennDraw.setPenColor(65, 96, 19);
            } else if (this.value == 512) {
                PennDraw.setFontSize(40);
                PennDraw.setPenColor(81, 127, 12); 
            } else if (this.value == 1028) {
                PennDraw.setFontSize(32);
                PennDraw.setPenColor(97, 153, 15); 
            } else {
                PennDraw.setFontSize(32);
                PennDraw.setPenColor(119, 198, 0);
            }
            PennDraw.filledSquare(coorX, coorY, 0.11);
            // draw number on Tile
            PennDraw.setPenColor(PennDraw.GRAY);
            PennDraw.setFontBold();
            PennDraw.text(coorX, coorY, "" + value);
        }
    }
    
    /**
     * Inputs: n/a
     * Outputs: current integer value of Tile
     * Description: returns the current integer value of Tile
    */
    public int getValue() {
        return this.value;
    }
    
    /**
     * Inputs: new value of Tile
     * Outputs: n/a
     * Description: changes the value of the Tile to a new value
    */
    public void setValue(int newValue) {
        this.value = newValue;
    }
    
    /**
     * Inputs: n/a
     * Outputs: n/a
     * Description: facilitates the merging of two Tiles (argument Tile value
     * doubles and instance Tile value gets set to -1)
    */
    public void mergeWith(Tile t) {
        t.setValue(2 * t.getValue());
        this.setValue(-1);
    }
    
    /**
     * Inputs: n/a
     * Outputs: n/a
     * Description: facilitates the movement of a Tile into an "empty" square 
     * (argument "empty" Tile takes on value of instance Tile, the instance Tile 
     * value gets set to -1)
    */
    public void moveIntoEmpty(Tile t) {
        t.setValue(this.getValue());
        this.setValue(-1);
    }
    
}