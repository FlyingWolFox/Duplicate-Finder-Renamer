# Duplicate Finder Renamer Tool

This is the renamer tool for [Duplicate Finder](https://github.com/FlyingWolFox/Duplicate-Finder). This will restore the names of files that had been renamed by the Duplicate Finder. It'll uses the terminal. The windows executable is a wrapper by JSmooth.

## How to use

Pass the firectories that you want to have renaming by argument and the script will do everything. You can use the terminal to run Main.class or the .jar or drag and drop the folder on top of the executable (it's the same thing). After the script is done, the files will be renamed and a Backup directory is bein the parent directory. The backup can be disabled by using -!b.

### Abot the Backup directory

To prevent loss, be by using the script in the wrong directory or by want to reverse this tool effects, backup directories are created for all arguments. In the parent folder of every directory passed will be a directory called `Backup`, it'll contain a copy of the directory. If more than one directory have the same parent, still be just one `Backup` direcory, but one copy of each directory will be in it

### How it works

It'll get all files of the directories passed and will look for `-` in th efilenames and will take out of the name everything before it, the `-` itself and one more character after. IF the file has been renamed by Duplicate Finder, this will restore the original name of the file. If a directory that didin't have its files renamed by Duplicate Finder, nothing will happen, if the files doesn't contain `-` and if they do, it'll rename it. Since this is a destructive action, the Backup will be useful.

## About the code

This tool uses `java.nio` libraries, which aren't available below Java 8, and maybe in JavaME
