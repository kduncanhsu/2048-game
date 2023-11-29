/**
 * Class Description: this program runs the internet game 2048
**/

public class Game {
    
    public static void main(String[] args) {

        Board b = new Board();
        b.draw();
        
        boolean finished = false;
        
        while (!finished) {
            
            // check if user has won or lost
            if (b.checkLost()) {
                finished = true;
                b.drawLost();
            } else if (b.checkWon()) {
                finished = true;
                b.drawWon();
            }
            
            // create a deep copy of the current arrangement of the Tile 2D array
            Tile[][] copy = b.getTiles();
            Tile[][] old = new Tile[4][4];
            for (int row = 0; row < old.length; row++) {
                for (int col = 0; col < old[0].length; col++) {
                    old[row][col] = new Tile(copy[row][col].getValue(), row, col);
                }
            }
            
            if (PennDraw.hasNextKeyTyped()) {
                char keyPressed = PennDraw.nextKeyTyped();
                // move all Tiles in correct direction where possible
                b.move(keyPressed);
                // merge all Tiles where possible
                b.merge(keyPressed);
                /* move all Tiles in correct direct where possible (as spaces have
                 * freed up from merging)
                 */
                b.move(keyPressed);
                // only counts as a move if arrangement of board has changed
                if (b.hasChanged(old)) {
                    b.generateTile();
                    b.addNumMove();
                }
                b.draw();
                
            }
        }
        
        
    }
    
}