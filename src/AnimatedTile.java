public class AnimatedTile {
    public int value;
    public int startRow, startCol; // old position
    public int endRow, endCol;     // target position
    public double x, y;            // current pixel location (for painting)
    public double scale = 1.0;

    public AnimatedTile(int value, int startRow, int startCol, int endRow, int endCol, double x, double y) {
        this.value = value;
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.x = x;
        this.y = y;
    }


}