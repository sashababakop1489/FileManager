package com.babakov.util;

import com.babakov.command.Command;
import com.babakov.command.Context;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.util.*;

public class ScannerUtil {
    @SneakyThrows
    public void performCommands(Context context, Map<String, Command> commands) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (StringUtils.isBlank(line)){
                continue;
            }

            List<String> allArguments = Arrays.asList(line.split(" "));
            String commandName = allArguments.get(0);
            if (commandName.equals("q") || commandName.equals("exit")) {
                System.out.println("Bye bye");
                break;
            }
            Command command = commands.getOrDefault(commandName, new Command(context) {
                @Override
                public String execute(List<String> args) {
                    return "Command " + line + " is unknown";
                }
            });
            System.out.println(command.execute(allArguments.subList(1, allArguments.size())));
        }
    }


    @SneakyThrows
    public Map<String, Command> getCommands(Context context){
        Reflections reflections =new Reflections("com.babakov.command", Scanners.SubTypes);
        Set<Class<? extends Command>> allClasses = reflections.getSubTypesOf(Command.class);

        Map<String, Command> commandMap = new LinkedHashMap<>();
        for (Class<? extends Command> each : allClasses){
            Command instance = each.getDeclaredConstructor(Context.class).newInstance(context);
            commandMap.put(each.getSimpleName().toLowerCase(), instance);
        }
        return commandMap;
    }
}
