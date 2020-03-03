package listfiles;
import java.io.File;

public class ListFiles {
    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();

        //File folder = new File("X:\\Winter2020\\csd436\\week3");
        File folder = new File("/Applications/Programming/Java/workspace/company/");
        System.out.println("Reading files before Java8 - Using listFiles() method");
        fileTreeWalk ftw = new fileTreeWalk(folder);

        //ftw.listAllFiles();
        ftw.findFiles("pdfsorter");

        System.out.println(ftw);

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }
}
