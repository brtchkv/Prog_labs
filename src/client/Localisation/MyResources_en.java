package client.Localisation;

import java.util.ListResourceBundle;

public class MyResources_en extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"register", "Registration"},
                {"login", "Login"},
                {"clear", "Clear"},
                {"main", "Main UI"},
                {"email", "Email"},
                {"serverConnection", "Server Connection"},
                {"helpText", "An application for organising your slavery business.\nCreator: Ivan Bratchikov"},
                {"disconnected", "Disconnected from the server.\nTry again later!"},
                {"submit", "Submit"},
                {"nickname", "Nickname"},
                {"password", "Password"},
                {"language", "Language"},
                {"help", "Help"},
                {"button", "Button"},
                {"about", "About"},
                {"table", "Table"},
                {"theme", "Theme"},
                {"logout", "Logout"},
                {"info", "Info"},
                {"humans", "Humans"},
                {"desk", "Remote Control"},
                {"action", "Action"},
                {"command", "Command"},
                {"add","Add"},
                {"remove", "Remove"},
                {"removeGreater", "Remove Greater"},
                {"removeLower", "Remove Lower"},
                {"addIfMin", "Add If Min"},
                {"human", "Human"},
                {"general", "General"},
                {"location", "Location"},
                {"skill", "Skill"},
                {"size", "Size"},
                {"pickedSize", "Picked Size: "},
                {"name", "Name"},
                {"age", "Age"},
                {"xCoordinate", "X Coordinate"},
                {"yCoordinate", "Y Coordinate"},
                {"info", "Info"},
                {"lastCommand", "Last Command:"},
                {"lastHuman", "Last Human:"},
                {"dont", "Don't press it!"}
        };
    }
}