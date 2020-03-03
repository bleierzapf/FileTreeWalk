/**
 * Brian Leierzapf
 * 2/6/2020
 * Assignment 3
 */

package listfiles;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class fileTreeWalk {

    private File _pathname; // Accepts pathname from user
    private Collection<fileDetail> fileTree = new ArrayList<>(); // Collects class information for file tree structure
    private Collection<fileDetail> foundList = new ArrayList<>(); // Collects files found that match search string from user
    private int[] methodRun = new int[2]; // Tracks which methods have been used - removes double call of method

    // File tree walk constructor & checks for valid directory path input from user
    public fileTreeWalk(File pathname){
        this._pathname = pathname;
        if(_pathname.isFile()) {
            throw new IllegalArgumentException("Illegal path input. Input was a file not a directory.");
        }
    }

    public void listAllFiles(){
        methodRun[0] = 1; // Sets method 0 to 1 to indicate it being used
        if(methodRun[1] == 0) { listAllFiles(_pathname, 0); } // If method 1 has been used do not run (p)listAllFile again
    }

    // Takes the starting directory and navigates down the directory tree
    private void listAllFiles(File folder, int level){
        File[] fileNames = folder.listFiles();
        level++;
        for (File file : fileNames) {
            // If directory adds directory name to fileTree but also does a recursive call to move down 1 level
            if(file.isDirectory()) {
                fileTree.add(addFile("DIRECTORY", file, level));
                listAllFiles(file, level);
            }
            // If file adds file name to fileTree and moves on to next file/directory
            if (file.isFile()) {
                fileTree.add(addFile("FILE", file, level));
            }
        }
    }

    // Method to add file detail to the object
    private fileDetail addFile (String type, File file, int level){
        fileDetail fd = new fileDetail();
        fd.type = type;
        fd.level = level;
        fd.location = file;
        fd.name = file.getName();
        return fd;
    }

    public void findFiles (String fileName){
        methodRun[1] = 1; // Sets method 1 to 1 to indicate it being used
        if(methodRun[0] == 0) { listAllFiles(_pathname, 0); } // If method 0 has been used do not run (p)listAllFile again
        // Iterates through fileTree and checks if any file or directory names match the fileName String
        for(fileDetail fd : fileTree) {
            // If found add fileDetail to foundList
            if(fd.name.equalsIgnoreCase(fileName)){
                foundList.add(fd);
            }
        }
    }

    public String toString(){
        StringBuilder outputStatement = new StringBuilder();
        outputStatement.append("Searched Directory: " + _pathname + "\n");
        // Prints the fileTree from the input directory
        if(methodRun[0] == 1) {
            for (fileDetail fd : fileTree) {
                for (int i = 0; i < fd.level; i++) {
                    outputStatement.append("\t");
                }
                outputStatement.append(fd.name + "\n");
            }
        }
        // Prints all the files/directories that were found that match the user's search criteria
        if(methodRun[1] == 1){
            if(foundList.isEmpty()){
                outputStatement.append("Requested file/directory not located.");
            }
            for (fileDetail fd : foundList) {
                outputStatement.append("\nFound requested file/directory at directory: " + fd.location);
            }
        }
        // Resets which methods were run prior to print statement call
        methodRun[0] = 0;
        methodRun[1] = 0;
        return outputStatement.toString();
    }
}

// file detail object
class fileDetail {
    String type;    // Stores if it is a FILE or DIRECTORY
    int level;      // Stores what level of the directory tree the file was found
    File location;  // Stores the locations of the found file
    String name;    // Stores the file name
}