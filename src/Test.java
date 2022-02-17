import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;

public class Test {
    /**
     * Test run program
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 0) {
            System.out.println("""
                    Error: You have to add a file to the execution that contains the adjacency list of the map to paint
                    To do so you have to execute the program as follows:
                        Test "adjacencyList.txt Path"
                    For an example, you can call: Test "InputSampleAustralia.txt\"""");
            return;
        }
        File file = new File(args[0]);
        Map map = new Map(file);
        try {
            // Measure the execution times of each algorithm
            long startTimeGreedy = System.nanoTime();
            Hashtable<String, Integer> mapColoringGreedy = map.getGreedyMapColoring();
            long stopTimeGreedy = System.nanoTime();

            long startTimeBT = System.nanoTime();
            Hashtable<String, Integer> mapColoringBackTracking = map.getBacktrackingMapColoring();
            long stopTimeBT = System.nanoTime();

            System.out.println("Coloring achieved by the greedy algorithm [red, blue, yellow, green]: ");
            printColors(mapColoringGreedy);
            System.out.printf("Execution time [in milliseconds]: %d%n", (stopTimeGreedy - startTimeGreedy));
            System.out.println("------------------------------------------------------------------------------------------------------");
            System.out.println("Coloring achieved by the backtracking algorithm [red, blue, yellow, green]: ");
            printColors(mapColoringBackTracking);
            System.out.printf("Execution time [in milliseconds]: %d%n", (stopTimeBT - startTimeBT));
            float timesFaster = (float) ((stopTimeBT - startTimeBT) / (stopTimeGreedy - startTimeGreedy));
            System.out.printf("The greedy algorithm was approximately %.2f times faster than the backtracking one.", timesFaster);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Print the colors in the terminal
     *
     * @param mapColoring Coloring to print as a hashtable (Key:Region name, Value: Color as a number)
     */
    public static void printColors(Hashtable<String, Integer> mapColoring) {
        for (String node : mapColoring.keySet()) {
            System.out.print(node + ": ");
            switch (mapColoring.get(node)) {
                case (0) -> System.out.println("red");
                case (1) -> System.out.println("blue");
                case (2) -> System.out.println("yellow");
                case (3) -> System.out.println("green");
            }
        }
    }
}
