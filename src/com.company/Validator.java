package com.company;

import java.io.*;
import java.util.*;

public class Validator {
    private final boolean IS_DESCEND;
    private final boolean IS_INTEGER;
    private final ArrayList<File> INPUT_FILES;

    public Validator(boolean isDescend, boolean isInteger, ArrayList<File> inputFiles) {
        this.IS_DESCEND = isDescend;
        this.IS_INTEGER = isInteger;
        this.INPUT_FILES = inputFiles;
    }

    /**
     * checks the contents and order of files. deletes lines that do not not match the data type and sort order
     *
     * @return List of temporary files ready for sorting
     */
    public List<File> ValidateFiles() {
        List<File> filesToSort = new ArrayList<>();

        for (int i = 0; i < INPUT_FILES.size(); i++) {
            File validatedFile = tryToFixFile(INPUT_FILES.get(i));
            if (validatedFile.length() != 0) {
                filesToSort.add(validatedFile);
            }
        }
        return filesToSort;
    }

    private File tryToFixFile(File validatingFile) {
        File validatedFile = null;
        try {
            validatedFile = File.createTempFile("validated_", null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Scanner scanner = new Scanner(new FileReader(validatingFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(validatedFile))) {

            String previousLine = null;
            String nextLine;
            while (scanner.hasNext()) {
                nextLine = scanner.nextLine();

                if (isNextLineValid(previousLine, nextLine)) {
                    writer.write(nextLine + "\n");
                    previousLine = nextLine;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return validatedFile;
    }

    private boolean isNextLineValid(String previousLine, String nextLine) {
        return IS_INTEGER ? validateIntegerLine(previousLine, nextLine) : validateStringLine(previousLine, nextLine);
    }

    private boolean validateIntegerLine(String previousLine, String nextLine) {
        if (!nextLine.matches("-?\\d+")) {
            return false;
        }

        if (previousLine == null) {
            return true;
        }

        Integer nextInt = Integer.parseInt(nextLine);
        Integer prevInt = Integer.parseInt(previousLine);

        if (IS_DESCEND) {
            return nextInt <= prevInt;
        } else {
            return nextInt >= prevInt;
        }
    }

    private boolean validateStringLine(String previousLine, String nextLine) {
        if (nextLine == null || nextLine.equals("")) {
            return false;
        }
        if (previousLine == null || previousLine.equals("")) {
            return true;
        }
        if (IS_DESCEND) {
            return nextLine.compareTo(previousLine) <= 0;
        } else {
            return nextLine.compareTo(previousLine) >= 0;
        }
    }
}
