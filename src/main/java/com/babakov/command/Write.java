package com.babakov.command;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Write extends Command implements FindFile{

    public Write(Context context) {
        super(context);
    }

    @Override
    @SneakyThrows
    public String execute(List<String> args) {
        File currentFile = context.getCurrentDirectory();
        Scanner scanner = new Scanner(System.in);
        String line;

        FileWriter fileWriter = new FileWriter(currentFile,true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        line = scanner.nextLine();
        printWriter.println(line);
        printWriter.close();
        return "Write " + line + " to the " + currentFile.getAbsolutePath();
    }
}
