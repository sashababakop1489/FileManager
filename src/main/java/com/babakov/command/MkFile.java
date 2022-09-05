package com.babakov.command;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class MkFile extends Command{

    public MkFile(Context context) {
        super(context);
    }

    @Override
    @SneakyThrows
    public String execute(List<String> args) {
        String path = args.get(0);
        //Creating a File object
        File file = new File(context.getCurrentDirectory(), path);
        //Creating the directory
        boolean bool = file.createNewFile();
        if (bool) {
            System.out.println("File created successfully");
        } else {
            System.out.println("Sorry couldn’t create specified directory");
        }
        return file.getName();    }
}
