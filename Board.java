/**
 * Class Description: this program maintains a 2D array of Tiles. It provides means to
 * move all the Tiles where possible, merge all Tiles where possible, and generate
 * a random Tile when needed. It also provides means to check if the user has won
 * or lost (and draw corresponding messages), and also compare two Tile 2D arrays
 * to see if they are different. 
**/

public class Board {
    
    private Tile[][] tiles;
    private int numMoves;
    
    /**
     * **CONSTRUCTOR**
     * Inputs: n/a
     * Outputs: n/a
     * Description: creates a 2D array of Tiles and populates it with "empty 
     * spaces", then generates 2 random Tiles of either 2 or 4
    */
    public Board() {
        Tile[][] game = new Tile[4][4];
        // populate 2D array of Tiles with "empty" pieces
        for (int row = 0; row < game.length; row++) {
            for (int col = 0; col < game[0].length; col++) {
                game[row][col] = new Tile(-1, row, col);
            }
        }
        this.tiles = game;
        // generate 2 random Tiles
        this.generateTile();
        this.generateTile();
        numMoves = 0;
    }
    
    /**
     * Inputs: n/a
     * Outputs: n/a
     * Description: draws the main grid and moves counter, and draws each Tile 
     * at its corresponding position
    */
    public void draw() {
        // draw grid 
        PennDraw.setXscale(-0.1, 1.1);
        PennDraw.setYscale(-0.1, 1.1);
        PennDraw.clear(PennDraw.LIGHT_GRAY);
        PennDraw.setPenColor(PennDraw.GRAY);
        PennDraw.setPenRadius(0.025);
        PennDraw.line(0, 0, 0, 1);
        PennDraw.line(0, 1, 1, 1);
        PennDraw.line(1, 1, 1, 0);
        PennDraw.line(1, 0, 0, 0);
        PennDraw.line(0.5, 0, 0.5, 1);
        PennDraw.line(0, 0.5, 1, 0.5);
        PennDraw.line(0.25, 0, 0.25, 1);
        PennDraw.line(0.75, 0, 0.75, 1);
        PennDraw.line(0, 0.25, 1, 0.25);
        PennDraw.line(0, 0.75, 1, 0.75);
        // draw moves counter
        PennDraw.setFontBold();
        PennDraw.setFontSize(16);
        PennDraw.text(0.1, -0.05, "Moves: " + numMoves);
        // draw each Tile in correct position
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                tiles[row][col].draw();
            }
        }
    }
    
    /**
     * Inputs: the key (WASD) the player pressed on the keyboard
     * Outputs: n/a
     * Description: depending on direction player pressed, all the Tiles are 
     * shifted up by one in that direction
    */
    public void shift(char keyPressed) {
        if (keyPressed == 'w') {
            // loop through tiles going from left to right, bottom to top
            for (int row = 0; row < tiles.length - 1; row++) {
                for (int col = 0; col < tiles[0].length; col++) {
                    /* if Tile above current Tile is empty and current Tile is
                     * not empty, shift current Tile up by 1
                     */
                    if (tiles[row + 1][col].getValue() == -1 && 
                        tiles[row][col].getValue() != -1) {
                        tiles[row][col].moveIntoEmpty(tiles[row + 1][col]);
                    }
                }
            }
       } else if (keyPressed == 'a') {
            // loop through tiles going from right to left, top to bottom
            for (int row = tiles.length - 1; row >= 0; row--) {
                for (int col = tiles[0].length - 1; col >= 1; col--) {
                    /* if Tile to the left of current Tile is empty and current 
                     * Tile is not empty, shift current Tile to the left by 1
                     */
                    if (tiles[row][col - 1].getValue() == -1 && 
                        tiles[row][col].getValue() != -1) {
                        tiles[row][col].moveIntoEmpty(tiles[row][col - 1]);
                    }
                }
            } 
       } else if (keyPressed == 's') {
            // loop through tiles going from right to left, top to bottom
            for (int row =  tiles.length - 1; row >= 1; row--) {
                for (int col = tiles[0].length - 1; col >= 0; col--) {
                    /* if Tile below current Tile is empty and current Tile is
                     * is not empty, shift current Tile down by 1
                     */
                    if (tiles[row - 1][col].getValue() == -1 && 
                        tiles[row][col].getValue() != -1) {
                        tiles[row][col].moveIntoEmpty(tiles[row - 1][col]);
                    }
                }
            }
       } else if (keyPressed == 'd') {
            // loop through tiles going from left to right, bottom to top
            for (int row = 0; row < tiles.length; row++) {
                for (int col = 0; col < tiles[0].length - 1; col++) {
                    /* if Tile to the right of current Tile is empty and current 
                     * Tile is not empty, shift current Tile to the right by 1
                     */
                    if (tiles[row][col + 1].getValue() == -1 && 
                        tiles[row][col].getValue() != -1) {
                        tiles[row][col].moveIntoEmpty(tiles[row][col + 1]);
                    }
                }
            } 
       }
    }
    
    /**
     * Inputs: the key (WASD) the player pressed on the keyboard
     * Outputs: n/a
     * Description: calls shift three times, which ensures that there are no
     * possible movements on the board and all Tiles are blocked in the direction
     * the user pressed (however Tiles may still be able to merge)
    */
    public void move(char keyPressed) {
        this.shift(keyPressed);
        this.shift(keyPressed);
        this.shift(keyPressed);
    }
    
    /**
     * Inputs: the key (WASD) the player pressed on the keyboard
     * Outputs: n/a
     * Description: depending on the direction the user pressed, adjacent tiles in
     * that same direction will combine to have double the value (original Tile
     * becomes "empty")
    */
    public void merge(char keyPressed) {
        if (keyPressed == 'w') {
            // loop through tiles going from top to bottom, left to right
            for (int col = 0; col < tiles[0].length; col++) {
                for (int row = tiles.length - 2; row >= 0; row--) {
                    /* if Tile above current Tile is of same value and current 
                     * Tile is not empty, merge current Tile with above Tile
                     */
                    if (tiles[row][col].getValue() == 
                        tiles[row + 1][col].getValue() && 
                        tiles[row][col].getValue() != -1) {
                        tiles[row][col].mergeWith(tiles[row + 1][col]);
                    }
                }
            }
        
        } else if (keyPressed == 'a') {
            // loop through tiles going from left to right, top to bottom
            for (int row = 0; row < tiles.length; row++) {
                for (int col = 1; col < tiles[0].length; col++) {
                    /* if Tile left of current Tile is of same value and current 
                     * Tile is not empty, merge current Tile with left Tile
                     */
                    if (tiles[row][col].getValue() == 
                        tiles[row][col - 1].getValue() && 
                        tiles[row][col].getValue() != -1) {
                        tiles[row][col].mergeWith(tiles[row][col - 1]);
                    }

                }
            }
            
        } else if (keyPressed == 's') {
            // loop through tiles going from top to bottom, left to right
            for (int col = 0; col < tiles[0].length; col++) {
                for (int row = 1; row < tiles.length; row++) {
                    /* if Tile below  current Tile is of same value and current 
                     * Tile is not empty, merge current Tile with below Tile
                     */
                    if (tiles[row][col].getValue() == 
                        tiles[row - 1][col].getValue() && 
                        tiles[row][col].getValue() != -1) {
                        tiles[row][col].mergeWith(tiles[row - 1][col]);
                    }
                }
            }
        } else if (keyPressed == 'd') {
            // loop through tiles going from right to left, bottom to top
            for (int row = 0; row < tiles.length; row++) {
                for (int col = tiles[0].length - 2; col >= 0; col--) {
                    /* if Tile right of current Tile is of same value and current 
                     * Tile is not empty, merge current Tile with right Tile
                     */
                    if (tiles[row][col].getValue() == 
                        tiles[row][col + 1].getValue() && 
                        tiles[row][col].getValue() != -1) {
                        tiles[row][col].mergeWith(tiles[row][col + 1]);
                    }
                }
            }
            
           
        } 
    } 
    
    /**
     * Inputs: n/a
     * Outputs: n/a
     * Description: randomly generates a new Tile of value 2 or 4 at an unoccupied
     * position on the board
    */
    public void generateTile() {
        int randomRow = 0;
        int randomCol = 0;
        int twoOrFour = 0;
        boolean available = true;
        
        // a 2 is more likely to be generated than a 4
        if (Math.random() < 0.85) {
            twoOrFour = 2;
        } else {
            twoOrFour = 4;
        }
        
        // keep on generating random position on board until position is unoccupied
        while (available) {
            randomRow = (int) (Math.random() * 4);
            randomCol = (int) (Math.random() * 4);
            if (tiles[randomRow][randomCol].getValue() == -1) {
                tiles[randomRow][randomCol].setValue(twoOrFour);
                available = false;
            }
        }   
    }
    
    /**
     * Inputs: n/a
     * Outputs: a boolean of whether or not user has won
     * Description: checks if a Tile of 2048 is present on the board
    */
    public boolean checkWon() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                if (tiles[row][col].getValue() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Inputs: n/a
     * Outputs: n/a
     * Description: draws the winning output message
    */
    public void drawWon() {
        // draw winning message
        PennDraw.setPenColor();
        PennDraw.setFontBold();
        PennDraw.setFontSize(64);
        PennDraw.text(0.5, 0.55, "YOU WIN!!!");
        PennDraw.setFontSize(24);
        PennDraw.text(0.5, 0.35, "Number of moves: " + numMoves);
    }
    
    /**
     * Inputs: n/a
     * Outputs: a boolean of if the player has lost
     * Description: checks if there are no "empty" Tiles and no Tiles of the same
     * value adjacent to each other
    */
    public boolean checkLost() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                /* if any tile is empty it is still possible or if any adjacent
                 * tile has same value it is possible to play on
                 */
                if (tiles[row][col].getValue() == -1) {
                    return false;
                }
                if (row + 1 < tiles.length) {
                    if (tiles[row][col].getValue() == 
                        tiles[row + 1][col].getValue()) {
                        return false;
                    }
                }
                if (row - 1 >= 0) {
                    if (tiles[row][col].getValue() == 
                        tiles[row - 1][col].getValue()) {
                        return false;
                    }
                }
                if (col + 1 < tiles[0].length) {
                    if (tiles[row][col].getValue() == 
                        tiles[row][col + 1].getValue()) {
                        return false;
                    }
                }
                if (col - 1 >= 0) {
                    if (tiles[row][col].getValue() == 
                        tiles[row][col - 1].getValue()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * Inputs: n/a
     * Outputs: n/a
     * Description: draws the losing output message
    */
    public void drawLost() {
        // display losing message
        PennDraw.setPenColor();
        PennDraw.setFontBold();
        PennDraw.setFontSize(64);
        PennDraw.text(0.5, 0.55, "YOU LOSE!");
        PennDraw.setFontSize(24);
        PennDraw.text(0.5, 0.35, "Number of moves: " + numMoves);
    }
    
    /**
     * Inputs: n/a
     * Outputs: boolean of whether or not the board has changed
     * Description: compares the instance Board's tiles to the Tile 2D array in
     * the argument, checking if anything has changed
    */
    public boolean hasChanged(Tile[][] old) {
        for (int row = 0; row < old.length; row++) {
            for (int col = 0; col < old[0].length; col++) {
                if (old[row][col].getValue() != tiles[row][col].getValue()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Inputs: n/a
     * Outputs: n/a
     * Description: adds 1 to the number of moves counter
    */
    public void addNumMove() {
        this.numMoves++;
    }
    
    /**
     * Inputs: n/a
     * Outputs: the current Tile 2D array of the board
     * Description: returns the current Tile 2D array of the board
    */
    public Tile[][] getTiles() {
        return tiles;
    }

}