package shared;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Localisation {

//    private static final String RESOURCE_NAME = Resources_En.class.getTypeName();
//
//    private static final ObservableResourceFactory RESOURCE_FACTORY = new ObservableResourceFactory();
//
//    static {
//        RESOURCE_FACTORY.setResources(ResourceBundle.getBundle(RESOURCE_NAME));
//    }

//    @Override
//    public void start(Stage primaryStage) {
//        ComboBox<Locale> languageSelect = new ComboBox<>();
//        languageSelect.getItems().addAll(Locale.ENGLISH, new Locale("ru"));
//        languageSelect.setValue(Locale.ENGLISH);
//        languageSelect.setCellFactory(lv -> new LocaleCell());
//        languageSelect.setButtonCell(new LocaleCell());
//
//        languageSelect.valueProperty().addListener((obs, oldValue, newValue) -> {
//            if (newValue != null) {
//                RESOURCE_FACTORY.setResources(ResourceBundle.getBundle(RESOURCE_NAME, newValue));
//            }
//        });
//
//        Label label = new Label();
//        label.textProperty().bind(RESOURCE_FACTORY.getStringBinding("greeting"));
//
//        BorderPane root = new BorderPane(null, languageSelect, null, label, null);
//        root.setPadding(new Insets(10));
//        Scene scene = new Scene(root, 400, 400);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

//    public static class LocaleCell extends ListCell<Locale> {
//        @Override
//        public void updateItem(Locale locale, boolean empty) {
//            super.updateItem(locale, empty);
//            if (empty) {
//                setText(null);
//            } else {
//                setText(locale.getDisplayLanguage(locale));
//            }
//        }
//    }

//    public static class ObservableResourceFactory {
//
//        private ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();
//
//        public ObjectProperty<ResourceBundle> resourcesProperty() {
//            return resources;
//        }
//
//        public final ResourceBundle getResources() {
//            return resourcesProperty().get();
//        }
//
//        public final void setResources(ResourceBundle resources) {
//            resourcesProperty().set(resources);
//        }
//
//        public StringBinding getStringBinding(String key) {
//            return new StringBinding() {
//                {
//                    bind(resourcesProperty());
//                }
//
//                @Override
//                public String computeValue() {
//                    return getResources().getString(key);
//                }
//            };
//        }
//
//    }


    public static class Resources_ru extends ListResourceBundle {

        @Override
        protected Object[][] getContents() {
            return new Object[][]{
                    {"registration", "Регистрация"},
                    {"login", "Войти"},
                    {"clear", "Очистить"},
                    {"submit", "Отправить"},
                    {"submit", "Отправить"},
                    {"nickname", "Имя пользователя"},
                    {"password", "Пароль"},
                    {"language", "Язык"},
                    {"help", "Помощь"},
                    {"about", "О приложении"},
                    {"table", "Таблица"},
                    {"theme", "Тема"},
                    {"logout", "Выйти"},
                    {"info", "Информация"},
                    {"humans", "Человека"},
                    {"desk", "Пульт Управления"},
                    {"command", "Команда"},
                    {"add","Добавить"},
                    {"remove", "Удалить"},
                    {"removeGreater", "Удалить Больше"},
                    {"removeLower", "Удалить Меньше"},
                    {"addIfMin", "Добавить если минимальный"},
                    {"human", "Человек"},
                    {"general", "Общее"},
                    {"location", "Местоположение"},
                    {"skill", "Скилл"},
                    {"size", "Размер"},
                    {"pickedSize", "Выбранный размер:"},
                    {"name", "Имя"},
                    {"age", "Возраст"},
                    {"xCoordinate", "X Координата"},
                    {"yCoordinate", "Y Координата"},
                    {"lastCommand", "Последняя Команда:"},
                    {"lastHuman", "Последний Человек:"},
                    {"dont", "Не нажимать!"}
            };
        }
    }

    public static class Resources_Ch extends ListResourceBundle {

        @Override
        protected Object[][] getContents() {
            return new Object[][]{
                    {"registration", "Registrace"},
                    {"login", "P?ihl?sit se"},
                    {"clear", "Pr?hledn?"},
                    {"submit", "P?edlo?it"},
                    {"nickname", "P?ezd?vka"},
                    {"password", "Heslo"},
                    {"language", "Jazyk"},
                    {"help", "Pomoc"},
                    {"about", "Asi"},
                    {"table", "Tabulka"},
                    {"theme", "T?ma"},
                    {"logout", "Odhl?sit se"},
                    {"info", "Informace"},
                    {"humans", "Lid?"},
                    {"desk", "Ovl?dac? panel"},
                    {"command", "P??kaz"},
                    {"add","P?idat"},
                    {"remove", "Odstranit"},
                    {"removeGreater", "Odstranit V?t??"},
                    {"removeLower", "Odstranit Doln?"},
                    {"addIfMin", "P?idat Pokud Min"},
                    {"human", "?lov?k"},
                    {"general", "V?eobecn?"},
                    {"location", "Um?st?n?"},
                    {"skill", "Dovednost"},
                    {"size", "Velikost"},
                    {"pickedSize", "Vybral velikost:"},
                    {"name", "N?zev"},
                    {"age", "St???"},
                    {"xCoordinate", "X Sou?adnice"},
                    {"yCoordinate", "Y Sou?adnice"},
                    {"lastCommand", "Posledn? P??kaz:"},
                    {"lastHuman", "Posledn? ?lov?k:"},
                    {"dont", "Netla? to!"}
            };
        }
    }

    public static class Resources_Po extends ListResourceBundle {

        @Override
        protected Object[][] getContents() {
            return new Object[][]{
                    {"registration", "Rejestracja"},
                    {"login", "Zaloguj Si?"},
                    {"clear", "Jasny"},
                    {"submit", "Zatwierd?"},
                    {"nickname", "Przezwisko"},
                    {"password", "Has?o"},
                    {"language", "J?zyk"},
                    {"help", "Wsparcie"},
                    {"about", "Dooko?a"},
                    {"table", "Tabela"},
                    {"theme", "Motyw"},
                    {"logout", "Wyloguj"},
                    {"info", "Informacja"},
                    {"humans", "Ludzie"},
                    {"desk", "Panel Sterowania"},
                    {"command", "Dow?dztwo"},
                    {"add","Dodaj"},
                    {"remove", "Usun??"},
                    {"removeGreater", "Usu? Greater"},
                    {"removeLower", "Usu? Lower"},
                    {"addIfMin", "Dodaj Je?li Min"},
                    {"human", "Cz?owiek"},
                    {"general", "Genera?"},
                    {"location", "Lokalizacja"},
                    {"skill", "Umiej?tno??"},
                    {"size", "Rozmiar"},
                    {"pickedSize", "Wybrany Rozmiar:"},
                    {"name", "Imi?"},
                    {"age", "Wiek"},
                    {"xCoordinate", "X Wsp??rz?dna"},
                    {"yCoordinate", "Y Wsp??rz?dne"},
                    {"lastCommand", "Ostatnie Polecenie:"},
                    {"lastHuman", "Ostatni Cz?owiek:"},
                    {"dont", "Nie naciskaj tego!"}
            };
        }
    }

}