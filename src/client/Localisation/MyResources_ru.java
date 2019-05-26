package client.Localisation;

import java.util.ListResourceBundle;

public class MyResources_ru extends ListResourceBundle {
        @Override
        protected Object[][] getContents() {
            return new Object[][]{
                    {"register", "Регистрация"},
                    {"login", "Войти"},
                    {"clear", "Очистить"},
                    {"submit", "Отправить"},
                    {"button", "Кнопка"},
                    {"submit", "Отправить"},
                    {"main", "Основной UI"},
                    {"serverConnection", "Связь Cо Cервером"},
                    {"helpText", "Приложения для организации работорговли.\nСоздатель: Иван Братчиков"},
                    {"nickname", "Имя Пользователя"},
                    {"email", "Почта"},
                    {"disconnected", "Соединение к серверу прервано. Попробуйте по позже!"},
                    {"password", "Пароль"},
                    {"language", "Язык"},
                    {"help", "Помощь"},
                    {"about", "О Приложении"},
                    {"table", "Таблица"},
                    {"theme", "Тема"},
                    {"logout", "Выйти"},
                    {"info", "Информация"},
                    {"humans", "Человека"},
                    {"desk", "Пульт Управления"},
                    {"action", "Действие"},
                    {"command", "Команда"},
                    {"add","Добавить"},
                    {"remove", "Удалить"},
                    {"removeGreater", "Удалить Больше"},
                    {"removeLower", "Удалить Меньше"},
                    {"addIfMin", "Добавить Если Минимальный"},
                    {"human", "Человек"},
                    {"general", "Общее"},
                    {"location", "Местоположение"},
                    {"skill", "Скилл"},
                    {"size", "Размер"},
                    {"pickedSize", "Размер: "},
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
