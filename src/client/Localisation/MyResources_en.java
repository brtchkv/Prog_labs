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
                {"collectionInfo", "Collection of Vector type. Contains objects of human type."},
                {"submit", "Submit"},
                {"sheerVoid", "The fields command, name and age can't be void.\nMake sure they're set."},
                {"coordinatesVoid", "You didn't provide any coordinates. Thus the default preset was used!\nX = 0, Y = 0."},
                {"yMissing", "You didn't provide Y coordinates. Thus the default preset was used!\nY = 0."},
                {"xMissing", "You didn't provide X coordinates. Thus the default preset was used!\nX = 0."},
                {"skillNameMissing", "You can't provide skill info and don't provide skill name\nTry again!"},
                {"nicknameAndEmailVoid", "Make sure nickname and email fields are filled!"},
                {"nicknameAndPasswordVoid", "Make sure nickname and password fields are filled!"},
                {"cantLoadMainUI", "Can't load the main UI!\n"},
                {"objects", "Objects"},
                {"sorry", "Sorry"},
                {"why", "Why?!"},
                {"noResponse", "Can get the response!\n"},
                {"message","Message"},
                {"warning", "Warning"},
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