package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import view.commands.Command;

public class TextMenu {
    private Map<String, Command> commands;
    
    public TextMenu() {
        this.commands = new HashMap<String, Command>();
    }

    public void addCommand(Command c) {
        this.commands.put(c.getKey(), c);
    }

    private void printMenu() {
        for (Command c : this.commands.values()) {
            String line = String.format("%4s: %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        this.printMenu();
        System.out.println("Input the option: ");
        String key = scanner.nextLine();
        scanner.close();
        Command c = this.commands.get(key);
        if (c == null) 
            System.out.println("Invalid Option!\n");
            
        else c.execute();
    }
}
