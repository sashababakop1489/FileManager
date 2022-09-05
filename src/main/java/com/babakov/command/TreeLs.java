package com.babakov.command;

import lombok.SneakyThrows;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class TreeLs extends Command{

    public TreeLs(Context context) {
        super(context);
    }

    @Override
    @SneakyThrows
    public String execute(List<String> args) {
        File folder = context.getCurrentDirectory();
        if (!folder.isDirectory()){
            throw new IllegalArgumentException("folder is not a Directory");
        }
        int in = 0;
        StringBuilder builder = new StringBuilder();
        printLSTree(folder,in, builder);
        return builder.toString();
    }

    private void printLSTree(File folder, int in, StringBuilder builder) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        builder.append(getIndentString(in));
        builder.append("├──");
        builder.append(folder.getName());
        builder.append("\n");
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isDirectory()) {
                printLSTree(file, in + 1, builder);
            } else {
                printFile(file, in + 1, builder);
            }
        }
    }

    private static void printFile(File file, int indent, StringBuilder sb) {
        sb.append(getIndentString(indent));
        sb.append("└──");
        sb.append(file.getName());
        sb.append("\n");
    }

    private static String getIndentString(int indent) {
        return "│  ".repeat(Math.max(0, indent));
    }
}
