import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Main class of the script. This will rename the files whose names had been
 * renamed by the DuplicateFinnder
 * 
 * @author FlyingWolFox / lips.pissaia@gmail.com
 * @version 0.9.0
 */
public class Main {
    ArrayList<FileInfo> files; // holds the directory files

    /**
     * Main contructor. Gets a dir path and rename all files in it, taking off the
     * repetion ID (number + letter + slash + space)
     * 
     * @param path path to directory to have its files renamed
     */
    public Main(String path) {
        files = new ArrayList<FileInfo>();
        // gets all the files in the directory
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(path));
            for (Path file : stream) {
                if (!file.toFile().isDirectory()) {
                    files.add(new FileInfo(file.toFile(), Paths.get(path)));
                }
            }
            stream.close();
        } catch (IOException x) {
            x.printStackTrace();
            System.err.println(x);
        }

        // rename all the files
        for (FileInfo file : files) {
            file.rename();
        }
    }

    /**
     * This function recursively copy all the sub directories and files from
     * sourcedirectory to destinationDirectory
     * 
     * @param sourceDirectory      the directory to be copied
     * @param destinationDirectory the path and the new name of the directory
     * @throws IOException if IO error occurs
     */
    private static void copyFolder(File sourceDirectory, File destinationDirectory) throws IOException {
        // Check if sourceFolder is a directory or file
        // If sourceFolder is file; then copy the file directly to new location
        if (sourceDirectory.isDirectory()) {
            // Verify if destinationFolder is already present; If not then create it
            if (!destinationDirectory.exists()) {
                destinationDirectory.mkdir();
            }

            // Get all files from source directory
            String files[] = sourceDirectory.list();

            // Iterate over all files and copy them to destinationFolder one by one
            for (String file : files) {
                File srcFile = new File(sourceDirectory, file);
                File destFile = new File(destinationDirectory, file);

                // Recursive function call
                copyFolder(srcFile, destFile);
            }
        } else {
            // Copy the file content from one place to another
            Files.copy(sourceDirectory.toPath(), destinationDirectory.toPath());
        }
    }

    /**
     * Start of the sript
     * 
     * @param args arguments received, should all be directories
     */
    public static void main(String[] args) {
        // rename in all directories received
        for (String path : args) {
            // creates a backup directory, containing all files with the start names
            Path backup; // main backup filder
            try {
                // TODO: put a way to not do a backup
                try {
                    backup = Files.createDirectory(Paths.get(path).getParent().resolve("Backup"));
                } catch (FileAlreadyExistsException e) {
                    backup = Paths.get(path).getParent().resolve("Backup");
                }
                copyFolder(Paths.get(path).toFile(), backup.resolve(Paths.get(path).getFileName()).toFile());
                System.out.println("Backup Successful");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to create backup. Proceed? (y/n)");
                String response = System.console().readLine();
                if (!response.contains("y") && !response.contains("Y")) {
                    System.exit(1);
                }
            }

            // starts the renaming script
            new Main(path);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Unable to pause execution");
            e.printStackTrace();
        }
        System.exit(0);
    }
}