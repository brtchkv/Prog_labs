import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {

    private CustomCommands customCommands;
    private boolean needExit;
    Scanner myScan = new Scanner(System.in);

    public Console(){
        if(FileHandler.checkFileRead()) {
            this.customCommands = new CustomCommands(FileHandler.convertToLinkedHashSet());
            needExit = false;
            this.execute();
        }else{
            System.out.println("hehehe");
            needExit = true;
        }
    }

    /**
     * Method interprets commands read by readOneLinedCommands() and readMultipleLinedCommands() methods.
     * Besides, creates Shutdown Hook when CTRL-D or CTRL-C were pressed.
     */

    public void execute(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println();
                if (FileHandler.getSavePermission()) {
                    customCommands.save();
                }else {
                    System.err.println("********* Fatal Quit: Lost collection data! **********");
                }

            } catch (Exception e) {
                System.err.println("********* Fatal Quit: Lost collection data! **********");
            }
        }));

            while (!needExit) {
                String[] fullCommand;
                String typeOfInput = whichTypeOfInput();
                if (typeOfInput.equals("1")) {
                    fullCommand = readOneLinedCommands();
                } else if (typeOfInput.equals("exit")) {
                    fullCommand = new String[1];
                    fullCommand[0] = "exit";
                } else {
                    fullCommand = readMultipleLinedCommand();
                }
                try {

                    Human forAction = null;
                    if (fullCommand[0].equals("add") || fullCommand[0].equals("remove")
                            || fullCommand[0].equals("remove_lower") || fullCommand[0].equals("add_if_min")
                            || fullCommand[0].equals("remove_greater")) {
                        if (fullCommand.length == 1) {
                            System.out.println("Error, " + fullCommand[0] + " must have an argument.");
                            System.out.println("Type \"command_name arg\" for additional info. i.e \"add arg\"");
                            continue;
                        }

                        if ((fullCommand.length == 2) && !fullCommand[1].equals("arg")) {
                            try {
                                Gson gson = new Gson();
                                forAction = gson.fromJson(fullCommand[1], Temp.class).createHuman();

                                if ((forAction == null) || (forAction.getName() == null) || (forAction.getAge() == 0)) {
                                    System.out.println("Error, the item is set incorrectly: \n- You may not have specified all the values!");
                                    System.out.println("Type \"command_name arg\" for additional info. i.e \"add arg\"");
                                    continue;
                                }
                            } catch (JsonSyntaxException ex) {
                                System.out.println("Error, the item is set incorrectly!\nType \"command_name arg\" for additional info. i.e \"add arg\"");
                                continue;
                            }
                        } else if (fullCommand[1].equals("arg")) {
                            customCommands.infoCommand(fullCommand[0]);
                            continue;
                        }


                    }

                    switch (fullCommand[0]) {
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
                            if (!FileHandler.getSavePermission()) {
                                promptSave();
                                if (!FileHandler.getSavePermission()) {
                                    customCommands.save();
                                }
                            } else {
                                System.err.println("********* Fatal Quit: Lost collection data! **********");
                            }
                            break;
                        case "exit":
                            needExit = true;
                            break;


                        default:
                            System.out.println("Error: undefined command!");
                    }
                } catch (Exception e) {
                    System.out.println("The command is set improperly!");
                    continue;
                }

        }
    }

    /**
     * Method reads One Lined JSON Commands commands from console.
     * @return String[] with a list of commands, split by spaces.
     */
    private String[] readOneLinedCommands(){
        String command;
        try {
            System.out.println();
            System.out.println("Feed me with your command:");
            command = myScan.nextLine();
            System.out.println();

        }catch(NoSuchElementException | IllegalStateException ex){
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

    /**
     * Method asks for the format of the JSON input command.
     * By default "One Lined JSON" format is chosen.
     * @return String with a type of a command ("1" or "2").
     */
    private String whichTypeOfInput(){
        String type = "1";
        String temp;
        try {
            System.out.println();
            System.out.println("Enter The Number Of The Desired Input Format:");
            System.out.println("\t1) One-lined JSON");
            System.out.println("\t2) Multiple-lined JSON");
            System.out.println("By default \"One-lined JSON.\" option is chosen.");
            temp = myScan.nextLine().trim();
            if (temp.length() == 1 || temp.equals("1") || temp.equals("2")){
                type = temp;
            }else {
                System.out.println("\nYour choice exceeds our abilities!\nCheck your spelling next time.\nYou're now running the default option.");
            }


        }catch(NoSuchElementException | IllegalStateException ex){
            type = "exit";
        }

        type = type.trim();

        return type;
    }

    /**
     * Method reads Multiple Lined JSON Commands commands from console.
     * @return String[] with a list of commands, split by spaces.
     */
    private String[] readMultipleLinedCommand(){

        String myInput;
        String[] fullCommand = new String[2];
        StringBuffer s = new StringBuffer();
        String[] temp;
        int count = 0;


        try {
            System.out.println();
            System.out.println("Feed me with your command:");
            fullCommand[0] = myScan.nextLine();

        }catch(NoSuchElementException ex){
            fullCommand[0] = "exit";
        }

        try {

            if (fullCommand[0].equals("show") || fullCommand[0].equals("info") || fullCommand[0].equals("save")) {
                return fullCommand;
            }

            if(fullCommand[0].contains("{")){
                count++;
                temp = fullCommand[0].split(" ");
                fullCommand[0] = temp[0];
                s.append("{");

                if (temp[1].contains("}")) {
                    s.append("}");
                    return fullCommand;
                }
            }
            while (myScan.hasNext()) {

                myInput = myScan.nextLine();
                myInput = myInput.replaceAll("\t", "");
                myInput = myInput.replaceAll("\n", "");
                s.append(myInput);

                if (myInput.equals("{") || myInput.substring(myInput.length() - 1).trim().equals("{")) {
                    count++;
                }
                if (myInput.equals("}") || myInput.contains("},") || myInput.substring(myInput.length() - 1).trim().equals("}")) {
                    count--;
                }
                if (myInput.contains("}") && count == 0) {
                    break;
                } else if (count < 0) {
                    break;
                }
            }
            System.out.println();
            fullCommand[1] = s.toString();

        }catch (Exception e){}
        return fullCommand;
    }

    /**
     * Method asks whether to overwrite a source file when the import was initially broken.
     * (It changes the private variable "savePermission" if allowed to.
     */
    public void promptSave(){
        try {

        String command;
        System.out.println();
        System.out.println("Would like to save your collection to the file?");
        System.out.println("\tYes/No");
        System.out.println("Be careful: the \"Yes\" option implies an rewrite of the source file!");
        System.out.println("By default a \"No\" option is chosen.");
        command = myScan.nextLine();
        command = command.trim();

        if(command.contains("Yes") || command.contains("yes")) {
            FileHandler.setSavePermission(true);
        }

        }catch(Exception ex){
            System.out.println("Something bad has happened.");
            System.out.println("Your source file WILL NOT be overwritten!");
        }
    }

}
