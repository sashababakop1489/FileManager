package com.babakov.command;

import java.io.File;
import java.util.List;

public interface FindFile {

     default File findFile(List<String> args, Context context) {
        File currentDirectory = context.getCurrentDirectory();
        if (args.isEmpty()) {
            return null;
        } else {
            File file = new File(currentDirectory, args.get(0));
            return file.exists() ? file : null;
        }
    }
}
