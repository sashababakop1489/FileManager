package com.babakov.command;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Ls extends Command{

    public Ls(Context context) {
        super(context);
    }

    @Override
    public String execute(List<String> args) {
        File directory = context.getCurrentDirectory();
        File[] allFiles = directory.listFiles();
        if (allFiles == null) return "No files at this directory.";

        String flags = (args.size() != 0) ? args.get(0) : null;
        StringBuilder log = new StringBuilder("\n");


        // if flags exist :
        if (flags != null) {

            // if flags matches with condition:
            if (flags.matches("^(?:([srwe])(?!.*\\1))*$")) {

                // Get all fields :
                List<String> titles = getTitles(flags);
                List<List<Object>> rows = getRows(allFiles, flags);
                int cellSize = getCellSize(allFiles, titles);

                // Format and display all fields :
                log.append(formatTitles(titles, cellSize));
                log.append(formatRows(rows, cellSize));
            } else {
                return "Error. Enter correct flag(s) for 'ls' command. Use any of these: s,r,w,e .";
            }
        } else {
            log.append(shiftList(allFiles, null));
        }

        return log.toString();
    }

    private int getCellSize(File[] allFiles, List<String> titles) {
        int maxRWLength = 5,
                maxFileNameLength = 0,
                maxSizeValueLength = 0,
                maxExtensionLength = 0,
                maxTitleLength =
                        !titles.isEmpty()
                                ? titles.stream().mapToInt(String::length).max().getAsInt()
                                : 0;
        int[] valueMaxLengths = new int[]{
                maxRWLength,
                maxFileNameLength,
                maxSizeValueLength,
                maxExtensionLength,
                maxTitleLength};
        for (File file : allFiles) {
            valueMaxLengths[1] = Math.max(file.getName().split("\\.")[0].length(), valueMaxLengths[1]);
            valueMaxLengths[2] = Math.max(String.valueOf(file.getUsableSpace()).length(), valueMaxLengths[2]);
            valueMaxLengths[3] = file.isFile()
                    ? Math.max(file.getName().split("\\.")[1].length(), valueMaxLengths[3])
                    : Math.max(file.getName().length(), valueMaxLengths[3]);
        }

        return Arrays.stream(valueMaxLengths).max().getAsInt();
    }

    private String formatTitles(List<String> titles, int cellSize) {
        StringBuilder log = new StringBuilder();
        for (String title : titles) {
            log.append(title).append("\s".repeat(cellSize - title.length())).append("\t");
        }
        log.append("\n");
        return log.toString();
    }

    private String formatRows(List<List<Object>> rows, int cellSize) {
        StringBuilder log = new StringBuilder();
        for (List<Object> row : rows) {
            for (Object data : row) {
                int dataSize = data.toString().length();
                log.append(data.toString()).append("\s".repeat(cellSize - dataSize)).append("\t");
            }
            log.append("\n");
        }
        return log.toString();
    }

    private List<List<Object>> getRows(File[] allFiles, String flags) {
        List<List<Object>> rows = new LinkedList<>();
        for (File file : allFiles) {
            List<Object> row = new LinkedList<>();
            boolean isFile = file.isFile();
            String[] fileData = file.getName().split("\\.");
            row.add(fileData[0].equals("") ? file.getName() : fileData[0]);
            for (Character flag : flags.toCharArray()) {
                switch (flag) {
                    case 's' -> row.add(file.length());
                    case 'r' -> row.add(file.canRead());
                    case 'w' -> row.add(file.canWrite());
                    case 'e' -> row.add(isFile
                            ? fileData[1]
                            : file.getName().replace(".",""));
                }
            }
            rows.add(row);
        }
        return rows;
    }

    private List<String> getTitles(String flags) {
        List<String> titles = new LinkedList<>();
        titles.add("File name:");
        for (Character flag : flags.toCharArray()) {
            switch (flag) {
                case 's' -> titles.add("Size (Bytes):");
                case 'r' -> titles.add("Readable:");
                case 'w' -> titles.add("Writable:");
                case 'e' -> titles.add("Extension:");
            }
        }
        return titles;
    }

    private String shiftList(File[] allFiles, Integer columns) {
        if (columns == null) columns = 3;
        int rows = (int) Math.ceil((float) allFiles.length / columns);
        int biggestWord = Arrays.stream(allFiles).mapToInt(x -> x.getName().length()).max().getAsInt();

        StringBuilder tree = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = i; j < allFiles.length; j += rows) {
                if (allFiles[i].getName().length() == biggestWord) {
                    tree.append(allFiles[j].getName()).append("\t");
                } else {
                    tree
                            .append(allFiles[j].getName())
                            .append("\s"
                                    .repeat(biggestWord - allFiles[j].getName().length()))
                            .append("\t");
                }
            }
            tree.append("\n");
        }
        return tree.toString();
    }
}
