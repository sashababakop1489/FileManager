package com.babakov.command;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Open extends Command{
    public Open(Context context) {
        super(context);
    }

    @SneakyThrows
    @Override
    public String execute(List<String> args) {
        if (args.isEmpty())
            return "Please provide argument";
        File currentFile = context.getCurrentDirectory();
        if (!currentFile.exists())
            return "File not found";
        if (!currentFile.isFile())
            return "give me file";
        return FileUtils.readFileToString(currentFile, StandardCharsets.UTF_8);
    }

}
