package Application;

import utils.ConsoleReader;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в CSV loader! Чтобы получить список комманд, введите /help.");
        String input = scanner.nextLine();
        ConsoleReader reader = new ConsoleReader();
        while (!input.equals("/exit")) {
            reader.readCommand(input);
            input = scanner.nextLine();
        }
    }
}
