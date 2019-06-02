package client.Localisation;

import java.util.ListResourceBundle;

public class MyResources_pl extends ListResourceBundle{

        @Override
        protected Object[][] getContents() {
            return new Object[][]{
                    {"register", "Rejestracja"},
                    {"login", "Zaloguj Si\u0119"},
                    {"clear", "Jasny"},
                    {"submit", "Zatwid\u017A"},
                    {"nickname", "Przezwisko"},
                    {"main", "G\u0142\u00F3wny UI"},
                    {"serverConnection", "Po\u0142\u0105czenie Z Serwerem"},
                    {"email", "Email"},
                    {"helpText", "Aplikacja do organizowania dzia\u0142alno\u015Bci niewolniczej.\n" +
                            "Tw\u00F3rca: Ivan Bratchikov"},
                    {"password", "Has\u0142o"},
                    {"disconnected", "Odpojen\u00ED od serveru.\n" +
                            "Zkuste to pozd\u011Bji znovu!"},
                    {"language", "J\u0119zyk"},
                    {"button", "Przycisk"},
                    {"message","Wiadomo\u015B\u0107"},
                    {"warning", "Ostrze\u017Cenie"},
                    {"collectionInfo", "Kolekcja typu Vector. Zawiera obiekty typu ludzkiego."},
                    {"help", "Wsparcie"},
                    {"sheerVoid", "Polecenia p\u00F3l, imię i wiek nie mog\u0105 zosta\u0107 uniewa\u017Cnione.\n" +
                            "Upewnij si\u0119, \u017Ce s\u0105 ustawione."},
                    {"coordinatesVoid", "Nie poda\u0142e\u015B \u017Cadnych wsp\u00F3\u0142rz\u0119dnych. W ten spos\u00F3b zosta\u0142 u\u017Cyty domy\015Blny preset!\n" +
                            "X = 0, Y = 0."},
                    {"yMissing", "Nie poda\u0142e\u015B wsp\u00F3\u0142rz\u0119dnych Y. W ten spos\u00F3b zosta\u0142 u\u017Cyty domy\u015Blny preset!\n" +
                            "Y = 0."},
                    {"xMissing", "Nie poda\u0142e\u015B wsp\u00F3\u0142rz\u0119dnych X. W ten spos\u00F3b zosta\u0142 u\u017Cyty domy\u015Blny preset!\n" +
                            "X = 0."},
                    {"skillNameMissing", "Nie mo\u017Cesz poda\u0107 informacji o umiej\u0119tno\u015Bciach i nie podawa\u0107 nazwy umiej\u0119tno\u015Bci\n" +
                            "Spr\u00F3buj ponownie!"},
                    {"nicknameAndEmailVoid", "Upewnij si\u0119, \u017Ce pseudonim i pola e-mail są wype\u0142nione!"},
                    {"nicknameAndPasswordVoid", "Upewnij si\u0119, \u017Ce pola pseudonimu i has\u0142a są wype\u0142nione!"},
                    {"cantLoadMainUI", "Nie mo\u017Cna za\u0142adowa\u0107 g\u0142\u00F3wnego interfejsu u\u017Cytkownika!"},
                    {"objects", "Obiekty"},
                    {"sorry", "Przepraszam"},
                    {"why", "Czemu?!"},
                    {"noResponse", "Mo\u017Ce uzyska\u0107 odpowied\u017C!\n"},
                    {"about", "Dooko\u0142a"},
                    {"table", "Tabela"},
                    {"theme", "Motyw"},
                    {"logout", "Wyloguj"},
                    {"info", "Informacja"},
                    {"humans", "Ludzie"},
                    {"desk", "Panel Sterowania"},
                    {"action", "Akcja"},
                    {"command", "Dow\u00F3dztwo"},
                    {"add","Dodaj"},
                    {"remove", "Usun\u0105\u0107"},
                    {"remove_greater", "Usu\u0144 Greater"},
                    {"remove_lower", "Usu\u0144 Lower"},
                    {"add_if_min", "Dodaj Je\u015Bli Min"},
                    {"human", "Cz\u0142owiek"},
                    {"general", "Genera\u0142"},
                    {"location", "Lokalizacja"},
                    {"skill", "Umiej\u0119tno\u015B\u0107"},
                    {"size", "Rozmiar"},
                    {"pickedSize", "Rozmiar: "},
                    {"name", "Imi\u0119"},
                    {"age", "Wiek"},
                    {"xCoordinate", "X Wsp\u00F3\u0142rz\u0119dna = 0"},
                    {"yCoordinate", "Y Wsp\u00F3\u0142rz\u0119dne = 0"},
                    {"lastCommand", "Ostatnie Polecenie:"},
                    {"lastHuman", "Ostatni Cz\u0142owiek:"},
                    {"dont", "Nie naciskaj tego!"},
                    {"notANumber", "Zadan\u00E9  pole nen\u00ED \u010D\u00EDslo!"},
                    {"date", "Data"},
                    {"update", "Aktualizacja"},
                    {"notSelectHuman", "Zadan\u00E9  pole nen\u00ED \u010D\u00EDslo!"},
                    {"mode", "Tryb"},
                    {"canvas", "Brezentowy"},
                    {"filter", "Filtr"},
                    {"showTable", "Nie prze\u0142ączy\u0142e\u015B trybu na Table!"},
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
