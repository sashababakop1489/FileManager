package com.babakov.command;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Remove extends Command{
    public Remove(Context context) {
        super(context);
    }

    @Override
    @SneakyThrows
    public String execute(List<String> args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Specify the path,  where you want to delete file.");
        File file = new File(scanner.nextLine());
        String path = file.getPath();
        FileUtils.deleteDirectory(file);
        return "File was delete from " + path;
    }
}
