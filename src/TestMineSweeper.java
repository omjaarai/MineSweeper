 /* This file contains testing methods for the MineSweeper project.
 * These methods are intended to serve several objectives:
 * 1) provide an example of a way to incrementally test your code
 * 2) provide example method calls for the MineSweeper methods
 * 3) provide examples of creating, accessing and modifying arrays
 *    
 * Toward these objectives, the expectation is that part of the 
 * grade for the MineSweeper project is to write some tests and
 * write header comments summarizing the tests that have been written. 
 * Specific places are noted with FIXME but add any other comments 
 * you feel would be useful.
 * 
 * Some of the provided comments within this file explain
 * Java code as they are intended to help you learn Java.  However,
 * your comments and comments in professional code, should
 * summarize the purpose of the code, not explain the meaning
 * of the specific Java constructs.
 *    
 */

import java.util.Random;
import java.util.Scanner;


/**
 * This class contains a few methods for testing methods in the MineSweeper
 * class as they are developed. These methods are all private as they are only
 * intended for use within this class.
 * 
 * @author Jim Williams
 * @author FIXME add your name here when you add tests and comment the tests
 *
 */
public class TestMineSweeper {

    /**
     * This is the main method that runs the various tests. Uncomment the tests
     * when you are ready for them to run.
     * 
     * @param args  (unused)
     */
    public static void main(String [] args) {

        // Milestone 1
        //testing the main loop, promptUser and simplePrintMap, since they have
        //a variety of output, is probably easiest using a tool such as diffchecker.com
        //and comparing to the examples provided.
        testEraseMap();
        
        // Milestone 2
        testPlaceMines();
        testNumNearbyMines();
        testShowMines();
        testAllSafeLocationsSwept();
        
        // Milestone 3
        testSweepLocation();
        testSweepAllNeighbors();
        //testing printMap, due to printed output is probably easiest using a 
        //tool such as diffchecker.com and comparing to the examples provided.
    }
    
    /**
     * This is intended to run some tests on the eraseMap method. 
     * 1. The first test compares the dimensions of expectedArray and studentMap.
     * 2. The test method creates expectedArray that is initialized with the Config.Unswept character.
     * 3. The method then generates a studentMap array through the eraseMap method.
     * 4. The method then iterates through each array to compare the elements.
     */
    private static void testEraseMap() {
        //Review the eraseMap method header carefully and write
        //tests to check whether the method is working correctly.
        
        //initialization of determining error
        boolean error = false;
        //sets a array that should match method output
        char[][] expectedArray = new char[][]{
            {Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT},
            {Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT},
            {Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT},
            {Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT},
            {Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT,Config.UNSWEPT}
        };
        //initialization of array that will be passed to method
        char[][]studentMap=new char[5][5];
        MineSweeper.eraseMap(studentMap);
        //condition checks that dimensions are equal
        if(studentMap.length != expectedArray.length) {
            
            System.out.println("Error: studentMap.length is not expected value of" + expectedArray.length);
            
        }
        //iterates through array to make sure contents match between expected and generated
        for(int i = 0; i < expectedArray.length; i++) {
            for(int j = 0; j < expectedArray[i].length; j++) {
                if(expectedArray[i][j] != studentMap[i][j]) {
                    error = true;
                    break;
                }
                else {
                    error = false;
                }
            }
            //check for error
            if(error) {
                System.out.println("There is an error in MineSweeper.eraseMap");
                break;
            }
            
        }
        
        if(!error) {
            System.out.println("testEraseMap: passed");
        }
        
    }      
    
    /*
     * Calculate the number of elements in array1 with different values from 
     * those in array2
     */
    private static int getDiffCells(boolean[][] array1, boolean[][] array2) {
        int counter = 0;
        for(int i = 0 ; i < array1.length; i++){
            for(int j = 0 ; j < array1[i].length; j++){
                if(array1[i][j] != array2[i][j]) 
                    counter++;
            }
        }
        return counter;
    }    
    
    /**
     * This runs some tests on the placeMines method. 
     * 1. First test compares the number of mines expected with the student mines generated.
     * 2. Second test iterates through the array to make sure each mine position is correct.
     */
    private static void testPlaceMines() {
        //initialization for determining error
        boolean error = false;
        
        //These expected values were generated with a Random instance set with
        //seed of 123 and MINE_PROBABILITY is 0.2.
        boolean [][] expectedMap = new boolean[][]{
            {false,false,false,false,false},
            {false,false,false,false,false},
            {false,false,false,true,true},
            {false,false,false,false,false},
            {false,false,true,false,false}};
        
        //number of mines in expected map
        int expectedNumMines = 3;
        //initialize an anticipated random number generator
        Random studentRandGen = new Random( 123);
        //generate an array to compare to expected
        boolean [][] studentMap = new boolean[5][5];
        //number of mines generated in student map
        int studentNumMines = MineSweeper.placeMines( studentMap, studentRandGen);
        
        if ( studentNumMines != expectedNumMines) {
            error = true;
            System.out.println("testPlaceMines 1: expectedNumMines=" + expectedNumMines + " studentNumMines=" + studentNumMines);
        }
        //number of cells that are different when comparing expected to student
        int diffCells = getDiffCells( expectedMap, studentMap);
        //condition checks for differences between expected and student map
        if ( diffCells != 0) {
            error = true;
            System.out.println("testPlaceMines 2: mine map differs.");
        }

        //checking if there is error
        if (error) {
            System.out.println("testPlaceMines: failed");
        } else {
            System.out.println("testPlaceMines: passed");
        }        
        
    }
    
    /**
     * This runs some tests on the numNearbyMines method. 
     * 1. Tests a location that should have a certain number of nearby mines 
     * 2. Tests a location that should have no nearby mines
     */
    private static void testNumNearbyMines() {
        //initializes error message
        boolean error = false;
        //generates an expected map with mines
        boolean [][] mines = new boolean[][]{
            {false,false,true,false,false},
            {false,false,false,false,false},
            {false,true,false,true,true},
            {false,false,false,false,false},
            {false,false,true,false,false}};
        
        //number of mines around location
        int numNearbyMines = MineSweeper.numNearbyMines( mines, 1, 1);
        //condition to test case with mines nearby
        if ( numNearbyMines != 2) {
            error = true;
            System.out.println("testNumNearbyMines 1: numNearbyMines=" + numNearbyMines + " expected mines=2");
        }
        
       numNearbyMines = MineSweeper.numNearbyMines( mines, 2, 1);
        //condition to test case with no nearby mines
        if ( numNearbyMines != 0) {
            error = true;
            System.out.println("testNumNearbyMines 2: numNearbyMines=" + numNearbyMines + " expected mines=0");
        }        
        
        //error determining conditions
        if (error) {
            System.out.println("testNumNearbyMines: failed");
        } else {
            System.out.println("testNumNearbyMines: passed");
        }
    }
    
    /**
     * This runs some tests on the showMines method. 
     * 1. First test tests a scenario where a mine is not included on the map
     * 2. Second test tests a scenario where a mine is included that should not be
     */
    private static void testShowMines() {
        //initialize for error message
        boolean error = false;
        
        //array of mine locations
        boolean [][] mines = new boolean[][]{
            {false,false,true,false,false},
            {false,false,false,false,false},
            {false,true,false,false,false},
            {false,false,false,false,false},
            {false,false,true,false,false}};
            
        char [][] map = new char[mines.length][mines[0].length];
        map[0][2] = Config.UNSWEPT;
        map[2][1] = Config.UNSWEPT;
        map[4][2] = Config.UNSWEPT;
        
        //call method and use conditions to check if unexpected mines are mapped or
        //if a mine is placed that shouldn't be
        MineSweeper.showMines( map, mines);
        if ( !(map[0][2] == Config.HIDDEN_MINE && map[2][1] == Config.HIDDEN_MINE && map[4][2] == Config.HIDDEN_MINE)) {
            error = true;
            System.out.println("testShowMines 1: a mine not mapped");
        }
        if ( map[0][0] == Config.HIDDEN_MINE || map[0][4] == Config.HIDDEN_MINE || map[4][4] == Config.HIDDEN_MINE) {
            error = true;
            System.out.println("testShowMines 2: unexpected showing of mine.");
        }
        
        //error message conditions
        if (error) {
            System.out.println("testShowMines: failed");
        } else {
            System.out.println("testShowMines: passed");
        }        
    }    
    
    /**
     * This is intended to run some tests on the allSafeLocationsSwept method.
     * 1. First test details a scenario where all locations are swept 
     * 2. Second test details a scenario where one non-mine location
     */
    private static void testAllSafeLocationsSwept() {
        //Review the allSafeLocationsSwept method header carefully and write
        //tests to check whether the method is working correctly.
        
        //error message initialization
        boolean errorVal = false;
        //array that stores anticipated mine locations
        boolean testMines1[][]= new boolean[][] {
            {false, true, false, false},
            {false, false, false, true},
            {false, false, true, false},
            {true, false, false, false}
        };
        //array that matches anticipated mine locations
        //and has all safe locations swept
        char testMap1[][] = new char[][]{
            {' ', Config.UNSWEPT, ' ', ' '},
            {' ', ' ', ' ', Config.UNSWEPT},
            {' ', ' ', Config.UNSWEPT, ' '},
            {Config.UNSWEPT, ' ', ' ', ' '}
        };
        //array that matches anticipated mine locations but
        //does not have all safe locations swept
        char testMap2[][] = new char[][]{
            {' ', Config.UNSWEPT, ' ', ' '},
            {' ', Config.UNSWEPT, ' ', Config.UNSWEPT},
            {' ', ' ', Config.UNSWEPT, ' '},
            {Config.UNSWEPT, ' ', ' ', ' '}
        };
        
        //test for all safe locations swept
        //conditions determine error
        errorVal = MineSweeper.allSafeLocationsSwept(testMap1, testMines1);
        if(!errorVal) {
            System.out.println("MineSweeper.allSafeLocationsSwept has an error.");
        }
        else {
            System.out.println("testAllSafeLocationsSwept part 1: passed");
        }
        
        //test for array without all safe locations swept
        //conditions determine error
        errorVal = MineSweeper.allSafeLocationsSwept(testMap2, testMines1);
        if(errorVal) {
            System.out.println("MineSweeper.allSafeLocationsSwept has an error.");
        }
        else {
            System.out.println("testAllSafeLocationsSwept part 2: passed");
        }
        
        
    }      

    
    /**
     * This is intended to run some tests on the sweepLocation method. 
     * 1. First test tests the return value for numNearbyMines
     * 2. Second test tests the return value for a mine location
     * 3. Third test tests the return value for a location that has already been swept
     * 4. Fourth test tests the return value for a location that is out of bounds
     */
    private static void testSweepLocation() {
        //Review the sweepLocation method header carefully and write
        //tests to check whether the method is working correctly.
        
        //stores known mine locations
        boolean testMines1[][]= new boolean[][] {
            {false, true, false, false},
            {false, false, false, true},
            {false, false, false, false},
            {true, false, false, false}
        };
        //array that will be compared to testMines1
        char testMap1[][] = new char[][]{
            {Config.UNSWEPT, Config.UNSWEPT,Config.UNSWEPT, Config.UNSWEPT},
            {Config.UNSWEPT, Config.UNSWEPT,Config.UNSWEPT, Config.UNSWEPT},
            {Config.UNSWEPT, Config.UNSWEPT,Config.UNSWEPT, Config.UNSWEPT},
            {Config.UNSWEPT, Config.UNSWEPT,Config.UNSWEPT, Config.NO_NEARBY_MINE}
        };
        
        //first test is testing the ability of sweepLocation to return the correct numNearbyMines
        int num = MineSweeper.sweepLocation(testMap1, testMines1, 0, 0);
        
        if(num != 1) {
            System.out.println("Error");
        }
        else {
            System.out.println("First testSweepLocation: passed");
        }
        
        //second test is testing the ability of sweepLocation to return the correct value when location is a mine
        num = MineSweeper.sweepLocation(testMap1, testMines1, 0, 1);
        
        if(num != -1) {
            System.out.println("Error");
        }
        else {
            System.out.println("Second testSweepLocation: passed");
        }
        
        //third test is testing the ability of sweepLocation to return the correct value when location has already been called
        num = MineSweeper.sweepLocation(testMap1, testMines1, 3, 3);
        
        if(num != -2) {
            System.out.println("Error");
        }
        else {
            System.out.println("Third testSweepLocation: passed");
        }
        
        //fourth test is testing the ability of sweepLocation to return the value for out of bounds location
        num = MineSweeper.sweepLocation(testMap1, testMines1, 4, 4);
        
        if(num != -3) {
            System.out.println("Error");
        }
        else {
            System.out.println("Fourth testSweepLocation: passed");
        }
    }      
    
    /**
     * This is intended to run some tests on the sweepAllNeighbors method. 
     * 1. Part 1: Tests that the location called with no nearby mines is changed to NO_NEARBY_MINE
     * 2. Part 2: Tests that the location iterated through with nearby mines is set to right number
     * 3. Part 3: Tests that the location iterated through with a mine is still unswept and not
     *            changed to swept
     * 4. Part 4: Tests that a non-iterated location remains unchanged
     */
    private static void testSweepAllNeighbors() {
        //Review the sweepAllNeighbors method header carefully and write
        //tests to check whether the method is working correctly.
        
        //stores known mine locations
        boolean testMines1[][]= new boolean[][] {
            {false, false, false, true},
            {false, false, false, false},
            {false, false, false, true},
            {true, false, true, false}
        };
        //array initialized that will be passed to method
        char testMap1[][] = new char[][]{
            {Config.UNSWEPT, Config.UNSWEPT,Config.UNSWEPT, Config.UNSWEPT},
            {Config.UNSWEPT, Config.UNSWEPT,Config.UNSWEPT, Config.UNSWEPT},
            {Config.UNSWEPT, Config.UNSWEPT,Config.UNSWEPT, Config.UNSWEPT},
            {Config.UNSWEPT, Config.UNSWEPT,Config.UNSWEPT, Config.UNSWEPT}
        };
        //array that testMap1 will be compared to
        char mapCompare[][]= new char[][]{
            {Config.NO_NEARBY_MINE, Config.NO_NEARBY_MINE, '1', Config.UNSWEPT},
            {Config.NO_NEARBY_MINE, Config.NO_NEARBY_MINE, '2', Config.UNSWEPT},
            {'1', '2', '2', Config.UNSWEPT},
            {Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT, Config.UNSWEPT}
        };
        MineSweeper.sweepAllNeighbors(testMap1, testMines1, 0, 0);
        
        //Part 1: Check to see if location called with no nearby mines is set to right Config
        if(testMap1[0][0] == mapCompare[0][0] && testMap1[0][0] == Config.NO_NEARBY_MINE) {
            System.out.println("testSweepAllNeighbors part 1: passed");
        }
        else {
            System.out.println("There is an error in testSweepAllNeighbors: Part 1");
        }
        
        //Part 2: Check to see if location called with nearby mines is set to right Config
        if(testMap1[1][2] == mapCompare[1][2] && testMap1[1][2] == '2') {
            System.out.println("testSweepAllNeighbors part 2: passed");
        }
        else {
            System.out.println("There is an error in testSweepAllNeighbors: Part 2");
        }
        
        //Part 3: Check to see if location called with a mine is still unswept and not set to swept mine
        if(testMap1[3][2] == mapCompare[3][2] && testMap1[3][2] != Config.SWEPT_MINE) {
            System.out.println("testSweepAllNeighbors part 3: passed");
        }
        else {
            System.out.println("There is an error in testSweepAllNeighbors: Part 3");
        }
        
        //Part 4: Check to see if locations that should not be iterated through remain unswept
        if(testMap1[1][3] == mapCompare[1][3] && testMap1[1][3] == Config.UNSWEPT) {
            System.out.println("testSweepAllNeighbors part 4: passed");
        }
        else {
            System.out.println("There is an error in testSweepAllNeighbors: Part 4");
        }
        
    }      
}
