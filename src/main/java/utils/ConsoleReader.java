package utils;

import loader.CSVLoader;

public class ConsoleReader {
    CSVLoader loader;

    public ConsoleReader() {
        loader = new CSVLoader();
    }

    public void readCommand(String input) {
        String[] splitLine = input.split(" ");
        String command = splitLine[0];
        switch (command) {
            case "/load":
                if (splitLine.length > 2) {
                    System.out.println("После команды /load укажите путь к файлу без пробелов.");
                } else if (splitLine.length < 2) {
                    System.out.println("Вы не указали путь к файлу! Укажите путь к файлу через пробел после команды.");
                } else {
                    loader.load(splitLine[1]);
                }
                break;
            case "/print":
                if (splitLine.length == 1) {
                    loader.print();
                } else {
                   if (isAllNumeric(splitLine)) {
                       for (int i = 1; i < splitLine.length; ++i) {
                           loader.print(Integer.parseInt(splitLine[i]));
                       }
                   }
                }
                break;
            case "/help":
                printAllCommands();
                break;
            case "":
                break;
            default:
                System.out.println("Не могу вас понять. Чтобы получить список комманд, введите /help.");
        }
    }

    private boolean isAllNumeric(String[] splitLine) {
        for (int i = 1; i < splitLine.length; ++i) {
            if (!splitLine[i].matches("[0-9]+")) {
                System.out.println("После команды /print должны вводиться только целые числа!");
                return false;
            }
        }
        return true;
    }

    private void printAllCommands() {
        System.out.println("Комманды: \n" +
                "/load path - загружает csv файл. Path - полный путь к файлу.\n" +
                "/print - печатает содержимое выбранной таблицы\n" +
                "/print n1 n2 - печатает столбцы n1 и n2 выбранной таблицы\n" +
                "/exit - выход из программы.");
    }
}
