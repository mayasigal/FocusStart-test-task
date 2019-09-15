package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ArgsParser parser = null;
        try {
            parser = new ArgsParser(args);
        } catch (ArgsParserException e) {
            System.out.print(e);
            System.exit(1);
        }
        boolean isDescend = parser.getIsDescend();
        boolean isInteger = parser.getIsInteger();
        File outputFile = parser.getOutputFile();
        ArrayList<File> inputFiles = parser.getInputFiles();

        Validator validator = new Validator(isDescend,isInteger, inputFiles);
        List filesToSort = validator.ValidateFiles();

        if (filesToSort.size() > 0) {
            Sorter sorter = new Sorter(isDescend, isInteger, filesToSort, outputFile);
            sorter.Sort();
        } else System.out.println("all data are invalid");
     }
}
