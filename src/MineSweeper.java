//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           MineSweeper
// Files:           MineSweeper.java
// Course:          CS200, Fall, 17
//
// Author:          Omjaa Rai
// Email:           orai@wisc.edu 
// Lecturer's Name: Dr. Renault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Zachary Gahl
// Partner Email:   gahl@wisc.edu
// Lecturer's Name: Dr. Renault
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   _X_ Write-up states that pair programming is allowed for this assignment.
//   _X_ We have both read and understand the course Pair Programming Policy.
//   _X_ We have registered our team prior to the team registration deadline.
//

/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////



import java.util.Random;
import java.util.Scanner;

public class MineSweeper {

    /**
     * This is the main method for Mine Sweeper game! This method contains the within game and play
     * again loops and calls the various supporting methods.
     * 
     * @param args (unused)
     */
    public static void main(String[] args) {
        
        int height;
        int width;
        Scanner in = new Scanner(System.in);
        Random randGen = new Random(Config.SEED);
        
        System.out.println("Welcome to Mine Sweeper!");
        
        //boolean gameLoop is used to determine if the entire game keeps going
        boolean gameLoop = true;
        //boolean gameContinue is used to determine if the current game is continued
        boolean gameContinue = true;
        
        //obtain user specified dimensions for the map
        String prompt1 = "What width of map would you like (" + Config.MIN_SIZE + " - " + Config.MAX_SIZE + "): ";
        String prompt2 = "What height of map would you like (" + Config.MIN_SIZE + " - " + Config.MAX_SIZE + "): ";
        
        while (gameLoop) {
            
            //initialization of the map
            width = promptUser(in, prompt1, Config.MIN_SIZE, Config.MAX_SIZE);
            in.nextLine();
            height = promptUser(in, prompt2, Config.MIN_SIZE, Config.MAX_SIZE);
            System.out.println();
            
            //initialization of map array that will be used throughout game
            char map[][] = new char[height][width];
            eraseMap(map);
            
            //initialization of the mines map that will be used to compare selected location
            //with mine locations
            boolean[][] mines = new boolean [height][width];
            
            //records the number of mines in the map
            int numMines = placeMines(mines, randGen);
            
            
            
            //Begin user input for game
            while(gameContinue) {
                
                //prints out the initial view of the game
                System.out.println("Mines: " + numMines);     
                printMap(map);
                    
                //gathers user input for next location
                in.nextLine();
                //row specified by user
                int row = promptUser(in, "row: ", 1, height);
                in.nextLine();
                //column specified by user
                int column = promptUser(in, "column: ", 1, width);
                
                //compares the location specifications to determine how game continues
                int nearbyMines = 0;
                nearbyMines = sweepLocation(map, mines, row-1, column-1);
                
                //Condition where the location is a mine
                if(nearbyMines == -1) {
                    
                    showMines(map, mines);
                    printMap(map);
                    System.out.println("Sorry, you lost.");
                    
                    break;
                }
                else if(nearbyMines == -2) {
                    continue;
                }
                else if(nearbyMines == -3) {
                    continue;
                }
                
                //condition if location is not a mine
                else {
                    
                    //determines if there are other 0 nearbyMines locations around current location
                    if(nearbyMines == 0) {
                        
                        sweepAllNeighbors(map, mines, row-1, column-1);
                        
                    }
                    //Updates map with new nearbyMines at location
                    else{
                        map[row - 1][column - 1] = (char)(nearbyMines + 48);
                        
                        //game winning criteria
                        if(allSafeLocationsSwept(map, mines)) {
                            showMines(map, mines);
                            printMap(map);
                            System.out.println("You Win!");
                            break;
                        }   
                    }
                
                }
                System.out.println();
            }
            
            //prompt for continuation of game
            System.out.print("Would you like to play again (y/n)? ");
            //user input to decide if they continue overall game
            String userWish = in.next();
            if (userWish.charAt(0)=='y'||userWish.charAt(0)=='Y') {
                gameLoop = true;
            }
            if (userWish.charAt(0)=='n'||userWish.charAt(0)=='N') {
                gameLoop = false;
            }
        }
        System.out.println("Thank you for playing Mine Sweeper!");
    }

    /**
     * This method prompts the user for a number, verifies that it is between min and max,
     * inclusive, before returning the number.
     * 
     * If the number entered is not between min and max then the user is shown an error message and
     * given another opportunity to enter a number. If min is 1 and max is 5 the error message is:
     * Expected a number from 1 to 5.
     * 
     * If the user enters characters, words or anything other than a valid int then the user is
     * shown the same message. The entering of characters other than a valid int is detected using
     * Scanner's methods (hasNextInt) and does not use exception handling.
     * 
     * Do not use constants in this method, only use the min and max passed in parameters for all
     * comparisons and messages. Do not create an instance of Scanner in this method, pass the
     * reference to the Scanner in main, to this method. The entire prompt should be passed in and
     * printed out.
     *
     * @param in The reference to the instance of Scanner created in main.
     * @param prompt The text prompt that is shown once to the user.
     * @param min The minimum value that the user must enter.
     * @param max The maximum value that the user must enter.
     * @return The integer that the user entered that is between min and max, inclusive.
     */
    public static int promptUser(Scanner in, String prompt, int min, int max) {

        //Initialization
        System.out.print(prompt);
        
        while ( true) {
            
            //String that stores user input
            String userInput;
            //Integer that stores a correctly inputed number
            int inputNumber;
            
            //Loop contains conditions that determine if the user input is a valid input
            //(is an integer that relates to a valid coordinate)
            while (!in.hasNextInt()) {
                System.out.println("Expected a number from " + min + " to " + max + ".");
                
              userInput = in.nextLine();
                
            }
            
            inputNumber  = in.nextInt();
            //now that a number has been inputed parameters have to be met to continue
            while(inputNumber>max||inputNumber<min) {
                System.out.println("Expected a number from " + min + " to " + max + ".");
                userInput=in.nextLine();
                
                //handles the case where an invalid number is inputed and then a non-numerical
                //value is inputed
                while(!in.hasNextInt()) {
                    System.out.println("Expected a number from " + min + " to " + max + ".");
                    userInput=in.nextLine();
                }
                inputNumber=in.nextInt();
            }
            
            //Condition that executes when input is finally valid
            if (inputNumber <=max && inputNumber >= min ) {
                
                return inputNumber;
            } 
        }

    }

    /**
     * This initializes the map char array passed in such that all elements have the Config.UNSWEPT
     * character. Within this method only use the actual size of the array. Don't assume the size of
     * the array. This method does not print out anything. This method does not return anything.
     * 
     * @param map An allocated array. After this method call all elements in the array have the same
     *        character, Config.UNSWEPT.
     */
    public static void eraseMap(char[][] map) {
        
        //iterates through array to set elements
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = Config.UNSWEPT;
            }
        }
    }

    /**
     * This prints out a version of the map without the row and column numbers. A map with width 4
     * and height 6 would look like the following: . . . . . . . . . . . . . . . . . . . . . . . .
     * For each location in the map a space is printed followed by the character in the map
     * location.
     * 
     * @param map The map to print out.
     */
    public static void simplePrintMap(char[][] map) {
        
        //iterates through array to show current status of the array
        for (int i = 0; i < map.length; i++) {
            
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(" "+map[i][j] );
            }
            System.out.println();
        }
        return;
    }

    /**
     * This prints out the map. This shows numbers of the columns and rows on the top and left side,
     * respectively. map[0][0] is row 1, column 1 when shown to the user. The first column, last
     * column and every multiple of 5 are shown.
     * 
     * To print out a 2 digit number with a leading space if the number is less than 10, you may
     * use: System.out.printf("%2d", 1);
     * 
     * @param map The map to print out.
     */
    public static void printMap(char[][] map) {
        //displays the column numbers starting at 1 and then going up by five until the last column
        for(int i = 1; i <= map[0].length; i++) {
            
            //handles the case of the first label
            if(i==1) {
                System.out.print("   "+ i);
            }
            //handles the case of the first increment of five by adding leading space
            else if(i%5==0 && i==5) {
                System.out.print(" "+ i);
            }
            //handles the case of the later increments of five that are two digit numbers
            else if(i%5==0 && i>5) {
                System.out.print(i);
            }
            //handles the case of the label at last column if it is a non-two digit number
            else if(i==map[0].length && map[0].length < 10) {
                System.out.print(" " + i);
            }
            //handles the case of the label at last column if it is a two digit number
            else if(i==map[0].length && map[0].length >=10) {
                System.out.print(i);
            }
            //default case label for column
            else {
                System.out.print("--");
            }
        }
        System.out.println();
        
        for (int i = 0; i < map.length; i++) {
            
            //list of conditions that prints out the correct row numbers
            //case for row 1
            if(i==0) {
                System.out.print(" "+ (i+1));
            }
            //next two conditions are for the increments of five
            else if((i+1)%5 == 0 && ((i+1)==5)) {
                System.out.print(" "+ (i+1));
            }
            else if((i+1)%5 == 0 && ((i+1)>5)) {
                System.out.print((i+1));
            }
            //next two conditions are for the last row
            else if((i+1)== map.length && map.length < 10) {
                System.out.print(" "+ (i+1) );
            }
            else if((i+1)== map.length && map.length >=10) {
                System.out.print(i+1);
            }
            //default label for row
            else {
                System.out.print(" |");
            }
            
            //displays the current swept and un-swept characteristics of the map
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(" " + map[i][j]);
            }
            System.out.println();
        }
        return;
    }

    /**
     * This method initializes the boolean mines array passed in. A true value for an element in the
     * mines array means that location has a mine, false means the location does not have a mine.
     * The MINE_PROBABILITY is used to determine whether a particular location has a mine. The
     * randGen parameter contains the reference to the instance of Random created in the main
     * method.
     * 
     * Access the elements in the mines array with row then column (e.g., mines[row][col]).
     * 
     * Access the elements in the array solely using the actual size of the mines array passed in,
     * do not use constants.
     * 
     * A MINE_PROBABILITY of 0.3 indicates that a particular location has a 30% chance of having a
     * mine. For each location the result of randGen.nextFloat() < Config.MINE_PROBABILITY
     * determines whether that location has a mine.
     * 
     * This method does not print out anything.
     * 
     * @param mines The array of boolean that tracks the locations of the mines.
     * @param randGen The reference to the instance of the Random number generator created in the
     *        main method.
     * @return The number of mines in the mines array.
     */
    public static int placeMines(boolean[][] mines, Random randGen) {
        //Stores a temporary value to return
        int val = 0;
        
        //iterates through the boolean array and randomly populates with mines
        for(int i = 0; i < mines.length; i++) {
            
            for(int j =0; j < mines[i].length; j++) {
                //condition to place mine
                if(randGen.nextFloat() < Config.MINE_PROBABILITY) {
                
                    mines[i][j] = true;
                    val += 1;
                }
                else {
                    mines[i][j] = false;
                
                }
            }
        }
        
        return val;
    }

    /**
     * This method returns the number of mines in the 8 neighboring locations. For locations along
     * an edge of the array, neighboring locations outside of the mines array do not contain mines.
     * This method does not print out anything.
     * 
     * If the row or col arguments are outside the mines array, then return -1. This method (or any
     * part of this program) should not use exception handling.
     * 
     * @param mines The array showing where the mines are located.
     * @param row The row, 0-based, of a location.
     * @param col The col, 0-based, of a location.
     * @return The number of mines in the 8 surrounding locations or -1 if row or col are invalid.
     */
    public static int numNearbyMines(boolean[][] mines, int row, int col) {
        //temporarily stores a value to return later
        int num = 0;
        
        //iteration sequence for checking positions around location for mines.
        for(int i = -1; i <=1; i++) {
            for(int j = -1; j<=1; j++) {
                if((row+i) < 0 || (col+j) < 0) {
                    continue;
                }
                //skips checking it's own location
                if((row + i == row) && (col + j == col)) {
                    continue;
                }
                //skips location if out of bounds
                if((row+i) >= mines.length || (col + j) >= mines[0].length) {
                    continue;
                }
                //if method finds a mine it adds one to the variable num which acts as a counter
                else if(mines[row+i][col+j] == true) {
                    num +=1;
                }
            }
        }
        
        return num;
    }

    /**
     * This updates the map with each unswept mine shown with the Config.HIDDEN_MINE character.
     * Swept mines will already be mapped and so should not be changed. This method does not print
     * out anything.
     * 
     * @param map An array containing the map. On return the map shows unswept mines.
     * @param mines An array indicating which locations have mines. No changes are made to the mines
     *        array.
     */
    public static void showMines(char[][] map, boolean[][] mines) {
        //iterates through the array and shows which mines were not swept
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                
                //condition allows hit mine to be ignored
                if(map[i][j]==Config.SWEPT_MINE) {
                    continue;
                }
                //sets non-swept mines to be seen
                else if(mines[i][j]==true) {
                    map[i][j] = Config.HIDDEN_MINE;
                }
            }
        }
        
        return;
    }

    /**
     * Returns whether all the safe (non-mine) locations have been swept. In other words, whether
     * all unswept locations have mines. This method does not print out anything.
     * 
     * @param map The map showing touched locations that is unchanged by this method.
     * @param mines The mines array that is unchanged by this method.
     * @return whether all non-mine locations are swept.
     */
    public static boolean allSafeLocationsSwept(char[][] map, boolean[][] mines) {
        
        boolean val = false;
        //iterates through the array
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                //condition that lets the game know to continue if there are still unswept
                //safe locations.
                if(map[i][j] == Config.UNSWEPT) {
                    
                    //condition that determines a safe location that has not been swept
                    //ends iteration
                    if(mines[i][j] == false) {
                    return false;
                    }
                    
                    //condition that skips over unswept mines
                    if(mines[i][j] == true) {
                        val = true;
                        continue;
                    }
                    
                }
                }
        }
        
        return val;

    }

    /**
     * This method sweeps the specified row and col. - If the row and col specify a location outside
     * the map array then return -3 without changing the map. - If the location has already been
     * swept then return -2 without changing the map. - If there is a mine in the location then the
     * map for the corresponding location is updated with Config.SWEPT_MINE and return -1. - If
     * there is not a mine then the number of nearby mines is determined by calling the
     * numNearbyMines method. - If there are 1 to 8 nearby mines then the map is updated with the
     * characters '1'..'8' indicating the number of nearby mines. - If the location has 0 nearby
     * mines then the map is updated with the Config.NO_NEARBY_MINE character. - Return the number
     * of nearbyMines.
     * 
     * @param map The map showing swept locations.
     * @param mines The array showing where the mines are located.
     * @param row The row, 0-based, of a location.
     * @param col The col, 0-based, of a location.
     * @return the number of nearby mines, -1 if the location is a mine, -2 if the location has
     *         already been swept, -3 if the location is off the map.
     */
    public static int sweepLocation(char[][] map, boolean[][] mines, int row, int col) {
        //condition if location is out of bounds
        if( (row < 0 || row >= map.length) || (col < 0  || col >= map[0].length) ) {
            return -3;
        }
        //condition if location has already been swept
        else if(map[row][col] != Config.UNSWEPT) {
            return -2;
        }
        //condition if location is a mine
        else if(mines[row][col] == true) {
            map[row][col] = Config.SWEPT_MINE;
            return -1;
        }
        //default condition that returns a number of nearby mines
        else{
            int num = 0;
            
            num = numNearbyMines(mines, row, col);
            //condition if numNearbyMines is 0
            if(num==0) {
                map[row][col] = Config.NO_NEARBY_MINE;
            }
            return num;
        }
    }
    

    /**
     * This method iterates through all 8 neighboring locations and calls sweepLocation for each. It
     * does not call sweepLocation for its own location, just the neighboring locations.
     * 
     * @param map The map showing touched locations.
     * @param mines The array showing where the mines are located.
     * @param row The row, 0-based, of a location.
     * @param col The col, 0-based, of a location.
     */
    public static void sweepAllNeighbors(char[][] map, boolean[][] mines, int row, int col) {
        
        //iteration sequence to check each location around user inputed location
        for(int i = -1; i <= 1; i++) {
            //out of bounds check 1
            if(row + i > (mines.length-1) || row + i < 0){
                continue;
            }
            
            for(int j = -1; j <= 1; j++) {
                //out of bounds check 2
                if(col + j > (mines[0].length-1) || col + j <0) {
                    continue;
                }
                //skips entered locations
                if(j==0 && i==0) {
                    continue;
                }
                
                int val = sweepLocation(map, mines, row+i, col+j);
                
                //condition if checked location is out of bounds
                if(val == -3 || val == -2) {
                    continue;
                }
                
                //Calls sweepAllNeighbors again if checked location has 0 nearby mines              
                if(val == 0) {
                    map[row+i][col+j] = Config.NO_NEARBY_MINE;
                    //sweepAllNeighbors(map, mines, row+i, col+j);
                }
                //default case which sets location to number of nearby mines
                else {
                    map[row+i][col+j] = (char)(val+48);
                }
                
            }
        }
        return;
    }
}

