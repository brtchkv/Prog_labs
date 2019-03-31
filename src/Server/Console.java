package Server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Set;

public class Console {

    private CustomCommands customCommands;

    public Console(){

        this.customCommands = new CustomCommands(FileHandler.convertToLinkedHashSet());
        // TODO: reading local file on server
    }

    public Console(Set<Human> human){
        if (human != null) {
            this.customCommands = new CustomCommands(human);

        }else {
            System.out.println("Collection is not set!");

        }
    }

    /**
     * Method interprets commands read by readOneLinedCommands() and readMultipleLinedCommands() methods.
     * Besides, creates Shutdown Hook when CTRL-D or CTRL-C were pressed.
     */

    public void execute(String text){

                String[] fullCommand = text.split(" ");

                try {

                    Human forAction = null;
                    if (fullCommand[0].equals("add") || fullCommand[0].equals("remove")
                            || fullCommand[0].equals("remove_lower") || fullCommand[0].equals("add_if_min")
                            || fullCommand[0].equals("remove_greater")) {
                        if (fullCommand.length == 1) {
                            System.out.println("Error, " + fullCommand[0] + " must have an argument.");
                            System.out.println("Type \"command_name arg\" for additional info. i.e \"add arg\"");

                        }

                        if ((fullCommand.length == 2) && !fullCommand[1].equals("arg")) {
                            try {
                                Gson gson = new Gson();
                                forAction = gson.fromJson(fullCommand[1], Temp.class).createHuman();

                                if ((forAction == null) || (forAction.getName() == null) || (forAction.getAge() == 0)) {
                                    System.out.println("Error, the item is set incorrectly: \n- You may not have specified all the values!");
                                    System.out.println("Type \"command_name arg\" for additional info. i.e \"add arg\"");

                                }
                            } catch (JsonSyntaxException ex) {
                                System.out.println("Error, the item is set incorrectly!\nType \"command_name arg\" for additional info. i.e \"add arg\"");

                            }
                        } else if (fullCommand[1].equals("arg")) {
                            customCommands.infoCommand(fullCommand[0]);
                        }


                    }

                    switch (fullCommand[0]) {
                        case "import":
                            break;
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
                                if (!FileHandler.getSavePermission()) {
                                    //TODO:save
                                    //customCommands.save();
                                }
                            } else {
                                System.err.println("********* Fatal Quit: Lost collection data! **********");
                            }
                            break;
                        case "exit":
                            break;

                        default:
                            System.out.println("Error: undefined command!");

                    }
                } catch (Exception e) {
                    System.out.println("The command is set improperly!");
                }

        }

        public void prepareSave(Set<Human> collection){

        }
    }

