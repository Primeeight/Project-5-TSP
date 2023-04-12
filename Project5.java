import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/**
 * Project 5
 *
 * @author Zachary Nelson
 * 11/10/21
 * 
 */

public class Project5 {
    //Requires array lists partialTour and remainingCities to be passed.
    static int CITI = 29;
    //number of cities in a tou.
    static int[][] adjacency = new int[CITI][CITI];
    //The adjacency matrix.
    static int bestcost = Integer.MAX_VALUE;
    //The best cost found; initially set to max.

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<Integer> partialTour = new ArrayList<Integer>();
        ArrayList<Integer> remainingCities = new ArrayList<Integer>();

        populateMatrix(adjacency);
        partialTour.add(0);
        for (int i = 1; i < CITI; i++) {
            remainingCities.add(i);
        }
        recDFS(partialTour, remainingCities);
    }

    /*Read the file into remainingCities*/
    public static void populateMatrix(int[][] adjacency) throws FileNotFoundException {// Prompt for a file

        Scanner reader = new Scanner(System.in);
        //Citi is file length may make global variable.
        System.out.println("Input filename:");
        String filename = reader.nextLine();
        File inputFile = new File(filename);
        Scanner input = new Scanner(inputFile);


        int value, i, j;
        for (i = 0; i < CITI && input.hasNext(); i++) { //CITI is a constant
            for (j = i; j < CITI && input.hasNext(); j++) { /*CITI will need to read the contents of the tour*/
                if (i == j) {
                    adjacency[i][j] = 0;
                } else {
                    value = input.nextInt();
                    adjacency[i][j] = value;
                    adjacency[j][i] = value;
                }
            }
        }
    }


    //Gets adjacency from static array.
    public static int computeCost(ArrayList<Integer> tour) { //CITI is the size of the tour as specified earlier.
        int totalcost = 0;

        for (int i = 0; i < tour.size() - 1; i++) { //(all cities in this tour)
            totalcost += adjacency[tour.get(i)][tour.get(i + 1)];
            //EndFor
        }
        if (tour.size() == CITI) { //tour is a complete tour
            totalcost += adjacency[tour.get(tour.size() - 1)][0];
        }
        return totalcost;
        //End computeCost
    }

    public static void recDFS(ArrayList<Integer> partialTour, ArrayList<Integer> remainingCities) {
        if (remainingCities.isEmpty()) {
            /*Compute tour cost for
            partialTour */
            int ptourcost = computeCost(partialTour);

            if (ptourcost < bestcost) {/*tour cost is less than best known cost*/
               /*Set best known cost with tour cost
            Output this tour and its cost */
                bestcost = ptourcost;
                System.out.print("The most cost efficient tour is " + ptourcost);
                for (int i = 0; i < partialTour.size(); i++) {
                    System.out.println(partialTour.get(i));
                }
            }
        } else {

            for (int i = 0; i < remainingCities.size(); i++) {//all cities in remainingCities
                ArrayList<Integer> newpartialTour = new ArrayList<Integer>(partialTour);
                newpartialTour.add(remainingCities.get(i));
                int newptourcost = computeCost(newpartialTour);
                /*Create a new partialTour with partialTour
                    Compute the cost of newpartialTour */

                if (newptourcost < bestcost) { // pruning
                    ArrayList<Integer> newremainingCities = new ArrayList<Integer>(remainingCities);
                    newremainingCities.remove(i);
                    recDFS(newpartialTour, newremainingCities);
                    /*Call recDFS with newpartialTour and newRemainingCities */

                } //EndIf
                else {
                    break;
                }
            }//EndFor
        }// Check to see if this ends the else statement.
    }//EndIf
    //end recDFS
}
