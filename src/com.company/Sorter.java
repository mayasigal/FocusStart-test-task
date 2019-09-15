package com.company;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Sorter {
    private final boolean IS_DESCEND;
    private final boolean IS_INTEGER;
    private final File OUTPUT_FILE;

    private List<File> INPUT_FILES;

    public Sorter(boolean isDescend, boolean isInteger, List<File> inputFiles, File outputFile) {
        this.IS_DESCEND = isDescend;
        this.IS_INTEGER = isInteger;
        this.INPUT_FILES = inputFiles;
        this.OUTPUT_FILE = outputFile;
    }

    /**
     * Merge sorts data from List of input files and print it to given output file
     * IMPORTANT: can only works with completely valid input files!
     */
    public void Sort() {
        while (INPUT_FILES.size() > 1) {
            File merged = merge(INPUT_FILES.get(0), INPUT_FILES.get(1));
            INPUT_FILES.remove(0);
            INPUT_FILES.remove(0);
            INPUT_FILES.add(merged);
        }
        try {
            Files.move(INPUT_FILES.get(0).toPath(), OUTPUT_FILE.toPath(), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File merge(File file1, File file2) {
        File merged = null;
        try {
            merged = File.createTempFile("merged_", null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Scanner scanner1 = new Scanner(new FileReader(file1));
             Scanner scanner2 = new Scanner(new FileReader(file2));
             BufferedWriter writer = new BufferedWriter(new FileWriter(merged))) {

            String line1 = scanner1.nextLine();
            String line2 = scanner2.nextLine();
            String toWrite;
            while(true) {
                 toWrite = compare(line1, line2);
                writer.write(toWrite + "\n");
                if (toWrite.equals(line1)) {
                    if (scanner1.hasNext()) {
                        line1 = scanner1.nextLine();
                    } else{
                        break;
                    }
                } else {
                    if (scanner2.hasNext()) {
                        line2 = scanner2.nextLine();
                    } else break;
                }
            }
            String notWrote = toWrite.equals(line1) ? line2 : line1;
            Scanner lastScanner = scanner1.hasNext() ? scanner1 : scanner2;

            while (lastScanner.hasNext()) {
                line1 = lastScanner.nextLine();
                toWrite = compare(notWrote, line1);
                writer.write(toWrite + "\n");
                if (toWrite.equals(notWrote)) {
                    notWrote = null;
                    writer.write(line1);
                    while(lastScanner.hasNext()) {
                        writer.write(lastScanner.nextLine() + "\n");
                    }
                }
            }
            if (notWrote != null) {
                writer.write(notWrote + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return merged;
    }

    private String compare(String string1, String string2) {
        if (IS_INTEGER) {
            Integer int1 = Integer.parseInt(string1);
            Integer int2 = Integer.parseInt(string2);
            if (IS_DESCEND) {
                return (int1 >= int2 ? int1 : int2).toString();
            } else {
                return (int1 <= int2 ? int1 : int2).toString();
            }
        } else {
            if (IS_DESCEND) {
                return string1.compareTo(string2) >= 0 ? string1 : string2;
            } else {
                return string1.compareTo(string2) >= 0 ? string2 : string1;
            }
        }
    }

}

