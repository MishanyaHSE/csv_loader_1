package loader;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class CSVLoader {
    private File file = null;
    private List<List<String>> fileContent = new ArrayList<>();
    private int maxLen;
    private String fileName;
    private int maxCellLen = 15;

    private boolean openFile(String path) {
        try{
            File file = new File(path);
            if (file.exists()) {
                if (path.split("\\.")[path.split("\\.").length - 1].equals("csv")) {
                    this.file = new File(path);
                } else {
                    System.out.println("Это не csv файл! Я умею работать только с файлами расширения csv.");
                    return false;
                }
            } else {
                System.out.println("Не удалось найти файл!");
                return false;
            }
        } catch (NullPointerException ex) {
            System.out.println("Не удалось найти файл! Укажите абсолютный путь к файлу.");
            return false;
        }
        return true;
    }

    public void load(String path) {
        if (openFile(path)) {
            BufferedReader csvReader;
            try {
                csvReader = new BufferedReader(new FileReader(file));
                String row;
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(";");
                    if (data.length > maxLen) {
                        maxLen = data.length;
                    }
                    fileName = getFileName(path);
                    fileContent.add(List.of(data));
                }
                csvReader.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Не удалось найти файл!");
            } catch (IOException e) {
                System.out.println("Не удалось прочитать содержимое файла. Попробуйте еще раз или выберите другой файл.");
            }
        }
    }

    public void print() {
        System.out.println(fileName + ":");
        Formatter fmt;
        for (var row : fileContent) {
            fmt = new Formatter();
            fmt.format("%1s", "|");
            int counter = 0;
            for (String cell : row) {
                counter++;
                if (cell.length() > 11) {
                    fmt.format("%15s", cell.substring(0, 11) + "...|");
                } else {
                    fmt.format("%15s", cell + "|");
                }
            }
            while(counter < maxLen) {
                fmt.format("%15s", "|");
                counter++;
            }
            System.out.println(fmt);
        }
        System.out.println("---------------------------------------------------------");
    }

    public void print(int numOfColumn) {
        Formatter fmt = new Formatter();
        for (var row : fileContent) {
            for (int i = 0; i < row.size(); ++i) {
                if (i == numOfColumn - 1) {
                    fmt.format("%10s", row.get(i) + "\n");
                }
            }
        }
        if (fmt.toString().equals("")) {
            System.out.println("Колонка " + numOfColumn + " пуста.");
        } else {
            System.out.println("Столбец " + numOfColumn + ":");
            System.out.println(fmt);
        }
        System.out.println("---------------------------------------------------------");
    }

    private String getFileName (String path) {
        String name;
        if (path.split("/").length == 1) {
            return path.split("\\\\")[path.split("\\\\").length - 1];
        } else {
            return path.split("/")[path.split("/").length - 1];
        }
    }
}
