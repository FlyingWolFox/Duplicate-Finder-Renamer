import java.io.File;
import java.nio.file.Path;

/**
 * This class holds file info
 * 
 * @author FlyingWolFox / lips.pissaia@gmail.com
 * @version 0.9.0
 */
public class FileInfo {
	File file;
	Path path;
	Path parent; // this file parent folder to rename
	String name; // this file's name

	/**
	 * Main constructor. Will initialize all the fields
	 * 
	 * @param file   File reperesenting this file
	 * @param parent Path to file's pareent folder
	 */
	public FileInfo(File file, Path parent) {
		this.file = file;
		this.parent = parent;
		name = file.getName();
		path = file.toPath();
	}

	/**
	 * Will rename the file to take out the repetion ID and slash, renaming to
	 * file's original name
	 */
	public void rename() {
		System.out.print("Renaming: " + name);
		int i;
		// find were the slash is at and use it as reference
		for (i = 0; i < name.length(); i++) {
			if (name.charAt(i) == '-') {
				i += 2;
				break;
			}
		}

		if (i == name.length()) {
			System.out.println(" -> " + name);
			return;
		}

		// new name does'nt have the repetion ID
		name = name.substring(i);

		// rename
		File newName = new File(parent.resolve(name).toString());
		if (!file.renameTo(newName))
			System.err.println("Failed to rename " + name);
		System.out.println(" -> " + name);
	}

}