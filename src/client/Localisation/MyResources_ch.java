package client.Localisation;

import java.util.ListResourceBundle;

public class MyResources_ch extends ListResourceBundle {

        @Override
        protected Object[][] getContents() {
            return new Object[][]{
                    {"register", "Registrace"},
                    {"login", "P\u0159ihl\u00E1sit se"},
                    {"clear", "Pr\u016Fhen\u00E1"},
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
                    {"collectionInfo", "Kolekce typu vektor. Obsahuje objekty lidsk\u00E9ho typu."},
                    {"help", "Pomoc"},
                    {"message","Zpr\u00E1va"},
                    {"warning", "Varov\u00E1n\u00ED"},
                    {"sheerVoid", "P\u0159\u00EDkaz pole, jm\u00E9no a v\u011Bk nemohou b\u00FDt neplatn\u00E9.\n" +
                            "Ujist\u011Bte se, \u017Ee jsou nastaveny."},
                    {"coordinatesVoid", "Nezadali jste \u017Eádn\u00E9 sou\u0159adnice. Proto bylo pou\u017Eito v\u00FDchoz\u00ED nastaven\u00ED!\n" +
                            "X = 0, Y = 0."},
                    {"yMissing", "Nezadali jste sou\u0159adnice Y. Proto bylo pou\u017Eito v\u00FDchoz\u00ED nastaven\u00ED!\n" +
                            "Y = 0."},
                    {"xMissing", "Nezadali jste sou\u0159adnice X. Proto bylo pou\u017Eito v\u00FDchoz\u00ED nastaven\u00ED!\n" +
                            "X = 0."},
                    {"skillNameMissing", "Nem\u016F\u017Eete poskytnout informace o dovednostech a neposkytovat jm\u00E9no dovednosti\n" +
                            "Zkus to znovu!"},
                    {"nicknameAndEmailVoid", "Zkontrolujte, zda jsou vypln\u011Bna pole p\u0159ezd\u00EDvka a e-mail!"},
                    {"nicknameAndPasswordVoid", "Zkontrolujte, zda jsou vypln\u011Bna pole p\u0159ezd\u00EDvky a hesla!\n"},
                    {"cantLoadMainUI", "Nelze na\u010D\u00EDst hlavn\u00ED u\u017Eivatelsk\u00E9 rozhran\u00ED!\n"},
                    {"objects", "Objekty"},
                    {"sorry", "Promi\u0148te"},
                    {"why", "Pro\u010D?!"},
                    {"noResponse", "M\u016F\u017Ee dostat odpov\u011Bď!"},
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
                    {"remove_greater", "Odstranit V\u011Bt\u0161\u00ED"},
                    {"remove_lower", "Odstranit Doln\u00ED"},
                    {"add_if_min", "P\u0159idat Pokud Min"},
                    {"human", "\u010Clov\u011Bk"},
                    {"general", "V\u0161eobecn\u00E9"},
                    {"location", "Um\u00EDst\u011Bn\u00ED"},
                    {"skill", "Dovednost"},
                    {"size", "Velikost"},
                    {"pickedSize", "Velikost: "},
                    {"name", "N\u00E1zev"},
                    {"age", "St\u00E1\u0159\u00ED"},
                    {"xCoordinate", "X Sou\u0159adnice = 0"},
                    {"yCoordinate", "Y Sou\u0159adnice = 0"},
                    {"lastCommand", "Posledn\u00ED P\u0159\u00EDkaz:"},
                    {"lastHuman", "Posledn\u00ED \u010Clov\u011Bk:"},
                    {"dont", "Netla\u010D to!"},
                    {"notANumber", "Zadan\u00E9  pole nen\u00ED \u010D\u00EDslo!"},
                    {"date", "Datum"},
                    {"update", "Aktualizace"},
                    {"notSelectHuman", "Zadan\u00E9  pole nen\u00ED \u010D\u00EDslo!"},
                    {"mode", "Re\u017Eimu"},
                    {"canvas", "Pl\u00E1tno"},
                    {"filter", "Filtr"},
                    {"showTable", "Nep\u0159epli jste re\u017Eim na tabulku!"},
                    {"registerFirst", "Mus\u00EDte se nejprve zaregistrovat!"},
                    {"wrongPass", "\u0160patn\u00E9 heslo!"},
                    {"userNotAuth", "U\u017Eivatel nen\u00ED autorizov\u00E1n"},
                    {"added", "Byl \u00FAsp\u011B\u017En\u011B %s pu0159id\u00E1n \u010Clov\u011Bk."},
                    {"alreadyExists", "Kolekce ji\u017E tento objekt ukl\u00E1d\u00E1."},
                    {"nameIsntTheSmallest", "%s jm\u00E9no nen\u00ED nejmen\u0161\u00ED: Nelze p\u0159idat do kolekce!"},
                    {"deleted", "\u010Clov\u011Bk %s byl vymaz\u00E1n"},
                    {"cantDelete", "Ve sb\u00EDrce takov\u00FD objekt neexistuje. Zkuste m\u00EDsto toho p\u0159idat." +
                            "\nMo\u017En\u00E1 nem\u00E1te pr\u00E1vo je smazat!"},
                    {"deletedN", "Odstran\u011Bno %s objekt\u016F."},
                    {"updated", "\u010Clov\u011Bk %s byl aktualizov\u00E1n."},
                    {"cantUpdate", "\u010Clov\u011Bk pat\u0159\u00ED jin\u011Bmu u\u017Eivateli.\n" +
                            "Nelze aktualizovat!"},
                    {"emailSent", "Registrace e-mailu je schv\u00E1lena!"},
                    {"userRegistered", "U\u017E jste se zaregistrovali!"},
                    {"cantRegister", "Nelze se zaregistrovat."}

            };
        }
}
