import java.util.*;

public class MainResort {

    /**
     Prints the locations of a given list of attraction IDs, based on a map that associates each attraction ID to its name.
     @param ids the list of attraction IDs
     @param idLocationMap the map that associates each attraction ID to its name
     */
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

    /**
     * Prints the names from the namesMap in a table format with given rows and columns.
     * If a name is not present for a particular index, it prints a blank space.
     *
     * @param namesMap a map of Integer IDs and corresponding names
     * @param rows the number of rows in the table
     * @param columns the number of columns in the table
     */
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

        List<Integer> listOne = new ArrayList<>();
        listOne.add(3);
        listOne.add(8);
        listOne.add(9);


        List<Integer> listTwo = new ArrayList<>();
        listTwo.add(2);
        listTwo.add(4);
        listTwo.add(11);


        List<Integer> listThree = new ArrayList<>();
        listThree.add(5);
        listThree.add(6);
        listThree.add(7);
        listThree.add(10);


        // reads into graph
        GraphMaker timeGraph = new GraphMaker();
        timeGraph.readWeights("real_map_time_weights.mtx");

        GraphMaker physicalGraph = new GraphMaker();
        physicalGraph.readWeights("real_map_dis_weight.mtx");
        physicalGraph.readCords("real_map_cords.mtx");

        GraphMaker trafficGraph = new GraphMaker();
        trafficGraph.readWeights("real_map_traffic.mtx");

        // initiate our map processor class
        MapGraph physicalMap = new MapGraph(physicalGraph.getGraph());
        MapGraph timeMap = new MapGraph(timeGraph.getGraph());
        TrafficMap traffic = new TrafficMap(trafficGraph.getGraph());

        GraphMaker names = new GraphMaker();
        HashMap<Integer, String> res  = names.readNames("real_map_names.mtx");

        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("--------------------------------Welcome to our park!------------------------------");
        System.out.println("----------------------------------------------------------------------------------");

        System.out.println("This is the park GPS.");
        System.out.println("We have the following attractions.");
        System.out.println("You can use our GPS to go anywhere you want!");
        System.out.println("----------------------------------------------------------------------------------");


        printNamesInTable(res, 4, 3);

        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------");
        Scanner in = new Scanner(System.in);
        String userInput;
        boolean newSearch;


        do {
            userInput = "";
            newSearch = false;
            System.out.println("Please tell us your plan: ");
            System.out.println("1 to find the closest route from one attraction to another");
            System.out.println("2 to find recommended attractions with least traffic");
            System.out.println("3 to find a list of attractions to visit given a time frame in minutes");
            System.out.println("4 Wheelchair-Friendly Routes. ");
            System.out.println("q to quit");
            userInput = in.next();
            if (userInput.equals("q"))
                break;
            else if (userInput.equals("1")) {
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println("----------------------------------------------------------------------------------");
                in.nextLine();
                // For the starting location
                printNamesInTable(res, 4, 3);
                System.out.println("Please enter the starting location:");
                int startLoc = Integer.valueOf(in.nextLine());

                System.out.println("----------------------------------------------------------------------------------");
                // For the destination
                printNamesInTable(res, 4, 3);
                System.out.println("Please enter the destination:");
                int endLoc = Integer.valueOf(in.nextLine());

                System.out.println("----------------------------------------------------------------------------------");

                System.out.print("Sound great! You're going from");
                System.out.print(res.get(startLoc));
                System.out.print(" to ");
                System.out.println(res.get(endLoc));
                System.out.println("----------------------------------------------------------------------------------");

                System.out.println("The suggested shortest path is: ");
                // Now you can use startLoc and endLoc in your code
                List<Integer> shortestPaths = physicalMap.shortestDistancePath(startLoc, endLoc);
                printLocations(shortestPaths, res);
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println("----------------------------------------------------------------------------------");

            }

            else if (userInput.equals("2")) {
                in.nextLine(); // Consume the newline character after the user input

                // Ask the user to enter the attraction type
                System.out.println("Where are you starting from?");
                printNamesInTable(res, 4, 3);
                int getSuggestions = Integer.valueOf(in.nextLine());
                List<Integer> suggestions = traffic.generateSuggestions(getSuggestions);
                List<Integer> suggestionRes = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    if (!(suggestions.get(i) == 1)) {
                        suggestionRes.add(suggestions.get(i));
                    }

                }
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println("The least traffic places you can go to is: ");
                for (int stop: suggestionRes) {
                    System.out.println(res.get(stop));

                }

                System.out.println("----------------------------------------------------------------------------------");
                System.out.println("----------------------------------------------------------------------------------");


            }

            else if (userInput.equals("3")) {
                in.nextLine(); // Consume the newline character after the user input
                // Ask the user to enter the time frame in minutes
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println("Please enter the upper left X boundary:");
                int upperLeftX = in.nextInt(); // Store the user's input into the timeFrame integer
                System.out.println("Please enter the upper left Y boundary:");
                int upperLeftY = in.nextInt(); // Store the user's input into the timeFrame integer
                in.nextLine(); // Consume the newline character after the user input
                System.out.println("Please enter the lower right X boundary:");
                int botRightX = in.nextInt(); // Store the user's input into the timeFrame integer
                System.out.println("Please enter the lower right Y boundary:");
                int botRightY = in.nextInt(); // Store the user's input into the timeFrame integer

                System.out.println("Please enter the type of attractions:");
                System.out.println("1: Big thrill; 2. Fun for little ones; 3. For everyone.");
                System.out.println("----------------------------------------------------------------------------------");
                int type = in.nextInt(); // Store the user's input into the timeFrame integer

                // call function
                List<Integer> coveredList = physicalMap.covered(upperLeftX, upperLeftY, botRightX, botRightY);
                List<Integer> filteredList = new ArrayList<>();
                // little
                if (type == 1) {
                    for (int i = 0; i < coveredList.size(); i++){
                        if (listOne.contains(coveredList.get(i))){
                            filteredList.add(coveredList.get(i));
                        }
                    }
                }

                if (type == 2) {
                    for (int i = 0; i < coveredList.size(); i++){
                        if (listTwo.contains(coveredList.get(i))){
                            filteredList.add(coveredList.get(i));
                        }
                    }
                }

                if (type == 3) {
                    for (int i = 0; i < coveredList.size(); i++){
                        if (listThree.contains(coveredList.get(i))){
                            filteredList.add(coveredList.get(i));
                        }
                    }
                }
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println("Here are the attractions within the boundary you input");
                for (int a : filteredList) {
                    System.out.println(res.get(a));
                }
                System.out.println("----------------------------------------------------------------------------------");
            }
            else if (userInput.equals("4")) {
                in.nextLine();
                do {
                    System.out.println("----------------------------------------------------------------------------------");
                    System.out.println("----------------------------------------------------------------------------------");
                    System.out.println("Our park wants to create an inclusive and enjoyable theme park " );
                    System.out.println("experience for all visitors, regardless of their mobility needs.");
                    System.out.println("Just let me know where you are, I will provide everywhere that you can go :)");
                    System.out.println("Please input your position, eg : 1");
                    int srcNode = Integer.valueOf(in.nextLine());
                    List<Integer> mstPath = physicalMap.getMSTPath(srcNode);
                    System.out.println("----------------------------------------------------------------------------------");
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
        System.out.println("Thanks for visiting our park. Hope to see you next time!");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------");

        }


}

