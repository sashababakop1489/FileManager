package com.babakov.command;

import lombok.RequiredArgsConstructor;

import java.io.FileNotFoundException;
import java.util.List;
@RequiredArgsConstructor
public abstract class Command {

    public final Context context;

    public abstract String execute(List<String> args) throws FileNotFoundException;
}
