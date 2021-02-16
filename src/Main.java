import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // reader from cmd
        BufferedReader cmd = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("paste the path of document here: ");
        String path = cmd.readLine();

        // build output path
        StringTokenizer tokenizer = new StringTokenizer(path, "\\");
        StringBuilder prevPath = new StringBuilder();
        String filename = "";
        while (tokenizer.hasMoreTokens()) {
            filename = tokenizer.nextToken();
            if (tokenizer.hasMoreElements()) {
                prevPath.append(filename).append('\\');
            }
        }
        String outputPath = prevPath.toString().concat("formatted_".concat(filename));
        outputPath = outputPath.replaceAll("vtt", "txt");
        // for input document
        BufferedReader fileReader = new BufferedReader(new FileReader(path));
        // for write to file
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputPath));
        String line;
        int c = 0;
        while ((line = fileReader.readLine()) != null) {
            // skip to next line
            if (line.contains("NOTE") || line.contains("-->") || line.isEmpty()
                || line.contains("-")) {
                continue;
            }
            line = line.toLowerCase();
            line = line.replaceAll("\\.", "");
            line = line.replaceAll("\\?", "");
            fileWriter.write(c == 7 ? line.concat("\n") : line.concat(" "));
            c = c == 7 ? 0 : c + 1;
        }
        fileReader.close();
        fileWriter.flush();
        fileWriter.write("\n");
        fileWriter.close();
        System.out.println("file " + outputPath + " created.");
    }
}
