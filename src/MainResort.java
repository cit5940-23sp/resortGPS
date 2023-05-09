import java.util.*;

public class MainResort {

    public static void printLocations(List<Integer> ids, Map<Integer, String> idLocationMap) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            String location = idLocationMap.get(id);

            if (location != null) {
                output.append(location);

                if (i < ids.size() - 1) {
                    output.append(" -> ");
                }
            }
        }

        System.out.println(output.toString());
    }

    public static void printNamesInTable(Map<Integer, String> namesMap, int rows, int columns) {
        int currentIndex = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (namesMap.containsKey(currentIndex)) {
                    if (currentIndex == 12) {
                        break;
                    }
                    System.out.print(currentIndex + " ");
                    System.out.printf("%-30s", namesMap.get(currentIndex));
                } else {
                    if (currentIndex == 12) {
                        break;
                    }
                    System.out.print(currentIndex + " ");
                    System.out.printf("%-30s", "");
                }
                currentIndex++;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // reads into graph
        GraphMaker timeGraph = new GraphMaker();
        timeGraph.readWeights("real_map_time_weights.mtx");

        GraphMaker physicalGraph = new GraphMaker();
        physicalGraph.readWeights("real_map_dis_weight.mtx");
        physicalGraph.readCords("real_map_cords.mtx");

        GraphMaker attractionGraph = new GraphMaker();
        attractionGraph.readWeights("real_map_similarity.mtx");

        // initiate our map processor class
        MapGraph physicalMap = new MapGraph(physicalGraph.getGraph());
        MapGraph timeMap = new MapGraph(timeGraph.getGraph());
        AttractivesMap attractions = new AttractivesMap(attractionGraph.getGraph());

        GraphMaker names = new GraphMaker();
        HashMap<Integer, String> res  = names.readNames("real_map_names.mtx");

        System.out.println("------------------------------------------------------------------");
        System.out.println("------------------------Welcome to our park!----------------------");
        System.out.println("------------------------------------------------------------------");

        System.out.println("This is the park GPS.");
        System.out.println("We have the following attractions.");
        System.out.println("You can use our GPS to go anywhere you want!");
        System.out.println("------------------------------------------------------------------");


        printNamesInTable(res, 4, 3);

        System.out.println("------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------");

        Scanner in = new Scanner(System.in);
        String userInput;
        boolean newSearch;


        do {
            userInput = "";
            newSearch = false;
            System.out.println("Please tell us your plan: ");
            System.out.println("1 to find the closest route from one attraction to another");
            System.out.println("2 to find recommended attractions based on types");
            System.out.println("3 to find a list of attractions to visit given a time frame in minutes");
            System.out.println("4 Wheelchair-Friendly Routes. ");
            System.out.println("q to quit");
            userInput = in.next();
            if (userInput.equals("q"))
                break;
            else if (userInput.equals("1")) {
                System.out.println("------------------------------------------------------------------");
                System.out.println("------------------------------------------------------------------");
                in.nextLine();
                // For the starting location
                System.out.println();
                printNamesInTable(res, 4, 3);
                System.out.println("Please enter the starting location:");
                int startLoc = Integer.valueOf(in.nextLine());

                System.out.println("------------------------------------------------------------------");
                // For the destination
                printNamesInTable(res, 4, 3);
                System.out.println("Please enter the destination:");
                int endLoc = Integer.valueOf(in.nextLine());

                System.out.println("------------------------------------------------------------------");

                System.out.print("Sound great! You're going from");
                System.out.print(res.get(startLoc));
                System.out.print(" to ");
                System.out.println(res.get(endLoc));
                System.out.println("------------------------------------------------------------------");

                System.out.println("The suggested shortest path is: ");
                // Now you can use startLoc and endLoc in your code
                List<Integer> shortestPaths = physicalMap.shortestDistancePath(startLoc, endLoc);
                printLocations(shortestPaths, res);
                System.out.println("------------------------------------------------------------------");
                System.out.println("------------------------------------------------------------------");

            }

            else if (userInput.equals("2")) {
                in.nextLine(); // Consume the newline character after the user input

                // Ask the user to enter the attraction type
                System.out.println();
                printNamesInTable(res, 4, 3);
                System.out.println("Which attraction do you want our suggestions based on?");
                System.out.println("You can either love it or hate it.");
                int getSuggestions = Integer.valueOf(in.nextLine());
                System.out.println("If you love it, please enter 0. If you hate it then enter 1.");
                int likeOrNot = Integer.valueOf(in.nextLine());
                System.out.println("How many suggestions do you want? ");
                int count = Integer.valueOf(in.nextLine());
                List<Integer> suggestionList;


                System.out.println(attractions.playSuggestion(getSuggestions));

                /*
                if (likeOrNot == 0) {
                    // love it
                    suggestionList = attractions.playSuggestion(getSuggestions);
                    suggestions = attractions.generateSuggestions(count, true);
                } else {
                    suggestions = attractions.generateSuggestions(count, false);
                }

                 */

                System.out.println("Your suggestion list is: ");
                //printLocations(suggestions, res);
                System.out.println("------------------------------------------------------------------");
                System.out.println("------------------------------------------------------------------");


            }

            /*else if (userInput.equals("3")) {
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
            } */
            else if (userInput.equals("4")) {
                in.nextLine();
                do {
                    System.out.println();
                    System.out.println("------------------------------------------------------------------");
                    System.out.println("------------------------------------------------------------------");
                    System.out.println("Our park wants to create an inclusive and enjoyable theme park " );
                    System.out.println("experience for all visitors, regardless of their mobility needs.");
                    System.out.println("Just let me know where you are, I will provide everywhere that you can go :)");
                    System.out.println("Please input your position, eg : 1");
                    int srcNode = Integer.valueOf(in.nextLine());
                    List<Integer> mstPath = physicalMap.getMSTPath(srcNode);
                    System.out.println("These places are covered with convenient service! ");
                    printLocations(mstPath, res);
//                    String attractionsStr = in.nextLine();
//                    String[] attractions = attractionsStr.split(",");
//                    List<String> attractionList = Arrays.asList(attractions);
//
//                    System.out.println("Cheapest transport path: " + pMap.findCheapestTransportPath(attractionList));
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

