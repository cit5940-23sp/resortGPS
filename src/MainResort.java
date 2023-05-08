import java.util.*;

public class MainResort {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String userInput;
        boolean newSearch;
        Map pMap = new Map(); // Assuming Map is a class that handles the operations.
        Set<String> locNames = pMap.getLocationNames(); // Assuming this function returns a set of all location names.

        do {
            userInput = "";
            newSearch = false;
            System.out.println("What do you want to do?");
            System.out.println("1 to find the closest route from one attraction to another");
            System.out.println("2 to find recommended attractions based on types");
            System.out.println("3 to find a list of attractions to visit given a time frame in minutes");
            System.out.println("4 to find the cheapest transport path given a set of attractions that the user wants to visit");
            System.out.println("q to quit");
            userInput = in.next();
            if (userInput.equals("q"))
                break;
            else if (userInput.equals("1")) {
                in.nextLine();
                // For the starting location
                System.out.println();
                System.out.println("Please enter the starting location:");
                String startLoc = in.nextLine();

                // For the destination
                System.out.println("Please enter the destination:");
                String endLoc = in.nextLine();

                // Now you can use startLoc and endLoc in your code
            } else if (userInput.equals("2")) {
                in.nextLine(); // Consume the newline character after the user input

                // Ask the user to enter the attraction type
                System.out.println();
                System.out.println("Please enter the attraction type:");
                String attractionType = in.nextLine(); // Store the user's input into the attractionType string

                // Now you can use attractionType in your code to find the recommended attractions
                List<Attraction> recommendedAttractions = pMap.findRecommendedAttractions(attractionType);
                System.out.println("Recommended attractions:");
                for (Attraction a : recommendedAttractions) {
                    System.out.println(a.getName());
                }
            } else if (userInput.equals("3")) {
                in.nextLine(); // Consume the newline character after the user input

                // Ask the user to enter the time frame in minutes
                System.out.println();
                System.out.println("Please enter the time frame in minutes:");
                int timeFrame = in.nextInt(); // Store the user's input into the timeFrame integer
                in.nextLine(); // Consume the newline character after the user input

                // Now you can use timeFrame in your code to find a list of attractions to visit
                List<Attraction> attractionsToVisit = pMap.findAttractionsToVisit(timeFrame);
                System.out.println("Attractions to visit:");
                for (Attraction a : attractionsToVisit) {
                    System.out.println(a.getName());
                }
            } else if (userInput.equals("4")) {
                in.nextLine();
                do {
                    System.out.println();
                    System.out.println("Please enter the attractions you want to visit separated by commas:");
                    String attractionsStr = in.nextLine();
                    String[] attractions = attractionsStr.split(",");
                    List<String> attractionList = Arrays.asList(attractions);

                    System.out.println("Cheapest transport path: " + pMap.findCheapestTransportPath(attractionList));
                    newSearch = true;

                    if (newSearch == true)
                        break;
                } while (true);
            } else {
                System.out.println("Invalid input");
            }
        } while (true);
        in.close();
    }
}