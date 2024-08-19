import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GitFunctions {
    public static void processTreeContent(String content) {
        int charactersRead = 0;
        List<String> allResult = new LinkedList<>();
        List<String> nameResult = new LinkedList<>();
        System.out.println("Content : " + content);

        while (charactersRead < content.length()) {
            // Search for the first appearance of \n to delimit lines
            int newlineIndex = content.indexOf('\n', charactersRead);
            if (newlineIndex == -1) newlineIndex = content.length(); // Last line

            // Extract the line
            String line = content.substring(charactersRead, newlineIndex);
            int firstSpaceIndex = line.indexOf(' ');
            if (firstSpaceIndex == -1) break; // If no space, break

            int secondSpaceIndex = line.indexOf(' ', firstSpaceIndex + 1);
            if (secondSpaceIndex == -1) break; // If no second space, break

            int thirdSpaceIndex = line.indexOf(' ', secondSpaceIndex + 1);
            if (thirdSpaceIndex == -1) break; // If no third space, break

            // Extract relevant parts
            String mode = line.substring(0, firstSpaceIndex);
            String type = line.substring(firstSpaceIndex + 1, secondSpaceIndex);
            String sha = line.substring(secondSpaceIndex + 1, thirdSpaceIndex);
            String name = line.substring(thirdSpaceIndex + 1);

            System.out.println("SHA : " + sha);

            // Build each line
            StringBuilder eachLine = new StringBuilder();
            eachLine.append(mode).append(' ')
                    .append(type).append(' ')
                    .append(sha).append(' ')
                    .append(name);

            // Add the line and name to the lists
            allResult.add(eachLine.toString());
            nameResult.add(name);

            // Update index for the next iteration
            charactersRead = newlineIndex + 1;
        }

        // Sort names
        String[] sortedNames = nameResult.stream().sorted().toArray(String[]::new);

        // Print all information
        for (String sortedName : Arrays.asList(sortedNames)) {
            for (String unsortedLine : allResult) {
                if (unsortedLine.contains(sortedName)) {
                    System.out.println(unsortedLine);
                    break;
                }
            }
        }
    }

}
