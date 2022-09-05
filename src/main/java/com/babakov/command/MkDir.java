package com.babakov.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class MkDir extends Command{


    public MkDir(Context context) {
        super(context);
    }

    @Override
    public String execute(List<String> args) {
        String path = args.get(0);
        File file = new File(path);
        boolean bool = file.mkdir();
        if (bool) {
            System.out.println("Directory created successfully");
        } else {
            System.out.println("Sorry couldn’t create specified directory");
        }
        return file.getName();
    }
}
