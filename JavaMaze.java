/**
 * Created by Troy on 7/19/17.
 */
class Maze {

    private char startChar = 'S';
    private int[] startCoordinates;
    private char endChar = 'E';
    private int endId;
    private char wallChar = 'X';
    private char pathChar = 'o';

    private char[][] grid = {{'-','-','-','-','X','X','X','-'},
                             {'X','X','X','-','-','-','-','-'},
                             {'-','-','-','-','X','X','X','-'},
                             {'-','X','X','-','E','-','-','X'},
                             {'-','-','X','X','X','X','X','-'},
                             {'-','X','-','-','-','-','-','X'},
                             {'-','X','-','X','S','X','-','-'},
                             {'-','-','-','-','X','X','X','-'}};

    private int[] shortestPath = new int[64];
    private int shortestLength = 65;

    private int[] pathTravelled = new int[64];
    private int lengthTravelled = 0;

    Maze() {
        java.util.Arrays.fill(shortestPath, -1);
        java.util.Arrays.fill(pathTravelled, -1);

        this.setStartAndEnd();

        this.findPath(this.getStartCoordinates()[0], this.getStartCoordinates()[1],
                this.pathTravelled ,this.lengthTravelled);

    }

    public boolean wasVisited(int r, int c, int[] pathTravelled, int lengthTravelled) {

        int squareid = getSquareId(r,c);

        for(int i=0; i<lengthTravelled; i++) {
            if(pathTravelled[i] == squareid)
                return true;
        }
        return false;
    }

    public void findPath(int r, int c, int[] pathTravelled, int lengthTravelled) {

        if(r<0 || r>=8 || c<0 || c>=8)
            return;
        if(this.grid[r][c] == this.wallChar)
            return;
        if(this.wasVisited(r,c, pathTravelled, lengthTravelled))
            return;

        int[] currentPath = new int[lengthTravelled+1];

        System.arraycopy(pathTravelled, 0, currentPath, 0, lengthTravelled);

        currentPath[lengthTravelled++] = this.getSquareId(r,c);

        if(this.getSquareId(r,c) == this.getEndId()) {

            if(lengthTravelled<=this.shortestLength) {
                this.setShortestLength(lengthTravelled);
                System.arraycopy(currentPath, 0, this.shortestPath, 0, lengthTravelled);
            }

            return;
        }

        findPath(r-1, c, currentPath, lengthTravelled);
        findPath(r, c-1, currentPath, lengthTravelled);
        findPath(r, c+1, currentPath, lengthTravelled);
        findPath(r+1, c, currentPath, lengthTravelled);

    }

    public void showSolution() {

        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++){
                if(this.wasVisited(i, j, this.shortestPath, this.shortestLength) && grid[i][j]!='S' && grid[i][j]!='E')
                    System.out.print(this.pathChar + " ");
                else
                    System.out.print(this.grid[i][j] + " ");
            }
            System.out.println("");
        }

    }

    public void showBlankMaze() {
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++){
                    System.out.print(this.grid[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public int getSquareId(int r, int c) {
        return r*8+c;
    }

    public void setShortestLength(int shortest_length) {
        this.shortestLength = shortest_length;
    }

    public int[] setStartCoordinates() {

        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++){
                if(this.grid[i][j] == this.startChar)
                    return new int[] {i,j};
            }
        }
        return new int[] {0,0};
    }

    public int[] getStartCoordinates() {
        return this.startCoordinates;
    }

    public int setEndId() {

        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++){
                if(this.grid[i][j] == this.endChar)
                    return this.getSquareId(i,j);
            }
        }
        return 63;
    }

    public int getEndId() {
        return this.endId;
    }

    public void setStartAndEnd() {
        this.startCoordinates = this.setStartCoordinates();
        this.endId = this.setEndId();
    }

}

public class JavaMaze {
    public static void main(String[] args) {
        Maze maze1 = new Maze();

        System.out.print("Maze:\n\n");

        maze1.showBlankMaze();

        System.out.print("\n\nSolution:\n\n");

        maze1.showSolution();
    }
}
