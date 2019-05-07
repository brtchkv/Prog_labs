package shared;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static shared.Console.getString;
import static shared.Console.getStrings;

public class ConsoleClient {

    private boolean needExit;

    Scanner myScan = new Scanner(System.in);

    public ConsoleClient(){
        needExit = false;

    }

    /**
     * Method interprets commands read by readOneLinedCommands() and readMultipleLinedCommands() methods.
     * Besides, creates Shutdown Hook when CTRL-D or CTRL-C were pressed.
     */

    public String execute(){

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

                    if (fullCommand[0].equals("add") || fullCommand[0].equals("remove")
                            || fullCommand[0].equals("remove_lower") || fullCommand[0].equals("add_if_min")
                            || fullCommand[0].equals("remove_greater")) {
                        if (fullCommand.length == 1) {
                            System.out.println("Error, " + fullCommand[0] + " must have an argument.");
                            System.out.println("Type \"command_name arg\" for additional info. i.e \"add arg\"");
                            continue;
                        }

                        if ((fullCommand.length == 2) && !fullCommand[1].equals("arg")) {

                            return fullCommand[0] + " " + fullCommand[1];

                        } else if (fullCommand[1].equals("arg")) {
                            return fullCommand[0] + " " + "arg";
                        }
                    }

                    switch (fullCommand[0]) {
                        case "import":
                            return "import";

                        case "info":
                            return "info";

                        case "add":
                            return "add";

                        case "remove":
                            return "remove";

                        case "remove_lower":
                            return "remove_lower";

                        case "show":
                            return "show";

                        case "remove_greater":
                            return "remove_greater";

                        case "add_if_min":
                            return "add_if_min";

                        case "save":
                            return "save";

                        case "exit":
                            needExit = true;
                            System.exit(0);
                            break;

                        default:
                            System.out.println("Error: undefined command!");
                    }
                } catch (Exception e) {
                    System.out.println("The command is set improperly!");
                    continue;
                }
        }
        return null;
    }

    /**
     * Method reads One Lined JSON Commands commands from console.
     * @return String[] with a list of commands, split by spaces.
     */
    private String[] readOneLinedCommands(){
        return getStrings(myScan);
    }

    /**
     * Method asks for the format of the JSON input command.
     * By default "One Lined JSON" format is chosen.
     * @return String with a type of a command ("1" or "2").
     */
    private String whichTypeOfInput(){
        return getString(myScan);
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
            System.out.print("> ");
            fullCommand[0] = myScan.nextLine();

        }catch(NoSuchElementException ex){
            fullCommand[0] = "exit";
        }

        try {

            if (fullCommand[0].equals("show") || fullCommand[0].equals("info") || fullCommand[0].equals("save") || fullCommand[0].equals("import")) {
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


}
