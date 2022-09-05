package com.babakov;

import com.babakov.command.Command;
import com.babakov.command.Context;
import com.babakov.util.ScannerUtil;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Main {
    private static final ScannerUtil scannerUtils = new ScannerUtil();

    public static void main(String[] args) {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        Context context =new Context(null, new File(s));
        Map<String, Command> commandMap = scannerUtils.getCommands(context);
        context.setCommandMap(commandMap);
        System.out.println("Hi there! Press q or exit to quit. Print help, to get available commands");
        scannerUtils.performCommands(context, commandMap);

    }
}
