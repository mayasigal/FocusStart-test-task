package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Command line args parser
 * Can only works with certain order of args: -a|-d  -i|-s  outputFilePath  inputFilesPathes
 * All args are necessary. should be at least one input file path specified.
 */
public class ArgsParser {
    private final boolean IS_DESCEND;
    private final boolean IS_INTEGER;
    private final File OUTPUT_FILE;
    private final ArrayList<File> INPUT_FILES;

    public ArgsParser(String[] args) throws ArgsParserException {
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));

        switch (argsList.get(0)) {
            case "-d":
                IS_DESCEND = true;
                argsList.remove(0);
                break;
            case "-a":
                IS_DESCEND = false;
                argsList.remove(0);
                break;
            default:
                throw new ArgsParserException("first argument is wrong");
        }
        switch (argsList.get(0)) {
            case "-i":
                IS_INTEGER = true;
                argsList.remove(0);
                break;
            case "-s":
                IS_INTEGER = false;
                argsList.remove(0);
                break;
            default:
                throw new ArgsParserException("second argument is wrong");
        }
        if (argsList.size() < 2) throw new ArgsParserException("wrong file path count. specify output and input files");
        OUTPUT_FILE = new File(argsList.get(0));
        argsList.remove(0);
        INPUT_FILES = new ArrayList<File>();

        for(String path: argsList) {
            INPUT_FILES.add(new File(path));
        }
    }

    public boolean getIsDescend() {
        return IS_DESCEND;
    }

    public boolean getIsInteger() {
        return IS_INTEGER;
    }

    public File getOutputFile() {
        return OUTPUT_FILE;
    }

    public ArrayList<File> getInputFiles() {
        return INPUT_FILES;
    }
}
