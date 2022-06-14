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
                   } else {
                       System.out.println("После команды указывайте только целые числа - номера столбцов.");
                   }
                }
                break;
            case "/help":
                printAllCommands();
                break;
            case "":
                break;
            case "/cell_len":
                if (splitLine.length == 1) {
                    System.out.println("После команды укажите длину одной клетки! (от 5 до 20)");
                } else if (splitLine.length > 2) {
                    System.out.println("После команды укажите один аргумент - длину одной клетки" +
                            "(целое число от 5 до 20).");
                } else if (isAllNumeric(splitLine)) {
                    int newLen = Integer.parseInt(splitLine[1]);
                    if (newLen > 4 && newLen < 21) {
                        loader.changeLen(newLen);
                        System.out.println("Вы успешно изменили длину клетки! Новая длина: " + newLen);
                    } else {
                        System.out.println("Длина клетки должна быть от 5 до 20!");
                    }
                }
                break;
            default:
                System.out.println("Не могу вас понять. Чтобы получить список комманд, введите /help.");
        }
    }

    private boolean isAllNumeric(String[] splitLine) {
        for (int i = 1; i < splitLine.length; ++i) {
            if (!splitLine[i].matches("[0-9]+")) {
                return false;
            }
        }
        return true;
    }

    private void printAllCommands() {
        System.out.println("""
                Комманды:
                /load path - загружает csv файл. Path - полный путь к файлу.
                /print - печатает содержимое выбранной таблицы
                /print n1 n2 - печатает столбцы n1 и n2 выбранной таблицы
                /cell_len n - изменить длину одной клетки на n
                /exit - выход из программы.""");
    }
}
