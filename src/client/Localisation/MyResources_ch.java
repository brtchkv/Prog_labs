package client.Localisation;

import java.util.ListResourceBundle;

public class MyResources_ch extends ListResourceBundle {

        @Override
        protected Object[][] getContents() {
            return new Object[][]{
                    {"register", "Registrace"},
                    {"login", "P\u0159ihl\u00E1sit se"},
                    {"clear", "Pr\u016Fhledn\u00E1"},
                    {"submit", "P\u0159edlo\u017Eit"},
                    {"nickname", "P\u0159ezd\u00EDvka"},
                    {"email", "Emailem"},
                    {"main", "Hlavn\u00ED UI"},
                    {"helpText", "\u017D\u00E1dost o organizaci va\u0161eho otroctv\u00ED.\n" +
                            "Autor: Ivan Bratchikov"},
                    {"serverConnection", "P\u0159ipojen\u00ED serveru"},
                    {"disconnected", "Odpojen\u00ED od serveru. Zkuste to pozd\u011Bji znovu!"},
                    {"password", "Heslo"},
                    {"language", "Jazyk"},
                    {"help", "Pomoc"},
                    {"button", "Tla\u010D\u00EDtko"},
                    {"about", "Asi"},
                    {"table", "Tabulka"},
                    {"theme", "T\u00E9ma"},
                    {"logout", "Odhl\u00E1sit se"},
                    {"info", "Informace"},
                    {"humans", "Lid\u00ED"},
                    {"desk", "Ovl\u00E1dac\u00ED panel"},
                    {"action", "Akce"},
                    {"command", "P\u0159\u00EDkaz"},
                    {"add","P\u0159idat"},
                    {"remove", "Odstranit"},
                    {"removeGreater", "Odstranit V\u011Bt\u0161\u00ED"},
                    {"removeLower", "Odstranit Doln\u00ED"},
                    {"addIfMin", "P\u0159idat Pokud Min"},
                    {"human", "\u010Clov\u011Bk"},
                    {"general", "V\u0161eobecn\u00E9"},
                    {"location", "Um\u00EDst\u011Bn\u00ED"},
                    {"skill", "Dovednost"},
                    {"size", "Velikost"},
                    {"pickedSize", "Velikost: "},
                    {"name", "N\u00E1zev"},
                    {"age", "St\u00E1\u0159\u00ED"},
                    {"xCoordinate", "X Sou\u0159adnice"},
                    {"yCoordinate", "Y Sou\u0159adnice"},
                    {"lastCommand", "Posledn\u00ED P\u0159\u00EDkaz:"},
                    {"lastHuman", "Posledn\u00ED \u010Clov\u011Bk:"},
                    {"dont", "Netla\u010D to!"}
            };
        }
}
