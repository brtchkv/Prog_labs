package client.Localisation;

import java.util.ListResourceBundle;

public class MyResources_pl extends ListResourceBundle{

        @Override
        protected Object[][] getContents() {
            return new Object[][]{
                    {"register", "Rejestracja"},
                    {"login", "Zaloguj Si\u0119"},
                    {"clear", "Jasny"},
                    {"submit", "Zatwierd\u017A"},
                    {"nickname", "Przezwisko"},
                    {"main", "G\u0142\u00F3wny UI"},
                    {"serverConnection", "Po\u0142ączenie Z Serwerem"},
                    {"email", "Email"},
                    {"helpText", "Aplikacja do organizowania dzia\u0142alno\u015Bci niewolniczej.\n" +
                            "Tw\u00F3rca: Ivan Bratchikov"},
                    {"password", "Has\u0142o"},
                    {"disconnected", "Odpojen\u00ED od serveru.\n" +
                            "Zkuste to pozd\u011Bji znovu!"},
                    {"language", "J\u0119zyk"},
                    {"button", "Przycisk"},
                    {"help", "Wsparcie"},
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
                    {"remove", "Usuną\u0107"},
                    {"removeGreater", "Usu\u0144 Greater"},
                    {"removeLower", "Usu\u0144 Lower"},
                    {"addIfMin", "Dodaj Je\u015Bli Min"},
                    {"human", "Cz\u0142owiek"},
                    {"general", "Genera\u0142"},
                    {"location", "Lokalizacja"},
                    {"skill", "Umiej\u0119tno\u015B\u0107"},
                    {"size", "Rozmiar"},
                    {"pickedSize", "Rozmiar: "},
                    {"name", "Imię"},
                    {"age", "Wiek"},
                    {"xCoordinate", "X Wsp\u00F3\u0142rz\u0119dna"},
                    {"yCoordinate", "Y Wsp\u00F3\u0142rz\u0119dne"},
                    {"lastCommand", "Ostatnie Polecenie:"},
                    {"lastHuman", "Ostatni Cz\u0142owiek:"},
                    {"dont", "Nie naciskaj tego!"}
            };
        }
}
