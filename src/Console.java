import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {

    private CustomCommands customCommands;
    private boolean needExit;
    private LinkedHashSet<Human> humans;

    public Console(){
        this.humans = File.convertToLinkedHashSet();
        this.customCommands = new CustomCommands(humans);
        needExit = false;
    }

    public void execute(){
        while(!needExit){
            String[] fullCommand = readCommands();
            Human forAction = null;
            if (fullCommand[0].equals("add") || fullCommand[0].equals("remove")
                    || fullCommand[0].equals("remove_lower") || fullCommand[0].equals("add_if_min")) {
                if(fullCommand.length == 1) {
                    System.out.println("Error, " + fullCommand[0] + " must have an argument.");
                    continue;
                }
                if((fullCommand.length == 2)){
                    try{
                        Gson gson = new Gson();
                        forAction = gson.fromJson(fullCommand[1], Temp.class).createHuman();

                        if ((forAction == null) || (forAction.getName() == null) || (Integer.toString(forAction.getAge()) == null)){
                            System.out.println("Error, the item is set incorrectly - you may not have specified all the values!");
                            continue;
                        }
                    }catch(JsonSyntaxException ex) {
                        System.out.println("Error, the item is set incorrectly!");
                        System.out.println(ex.getCause());
                        continue;
                    }
                }
            }
            switch (fullCommand[0]){
                case "info":
                    customCommands.info();
                    break;
                case "add":
                    customCommands.add(forAction);
                    break;
                case "remove":
                    customCommands.remove(forAction);
                    break;
                case "remove_lower":
                    customCommands.remove_lower(forAction);
                    break;
                case "show":
                    customCommands.show();
                    break;
                case "remove_greater":
                    customCommands.remove_greater(forAction);
                    break;
                case "add_if_min":
                    customCommands.add_if_min(forAction);
                    break;
                case "save":
                    File.save(humans);
                    break;
                case "^C":
                    needExit = true;
                    System.out.println("*************** Program Terminated ***************");
                    break;
                case "^D":
                    needExit = true;
                    File.save(humans);
                    System.out.println("*************** Successfully exited ***************");
                    break;

                default:
                    System.out.println("Error: undefined command!");
            }
        }
    }


    private String[] readCommands(){
        Scanner consoleScanner = new Scanner(System.in);
        String command;
        try {
            System.out.println("Feed me with your commands");
            command = consoleScanner.nextLine();
        }catch(NoSuchElementException ex){
            command = "exit";
        }
        command = command.trim();
        String[] fullCommand = command.split(" ",2);
        if(fullCommand.length > 1) {
            while (fullCommand[1].contains("  ")) {
                fullCommand[1] = fullCommand[1].replaceAll("  ", " ");
            }
        }
        return fullCommand;
    }

}
