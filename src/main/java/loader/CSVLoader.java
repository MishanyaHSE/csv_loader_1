package loader;

import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class CSVLoader {
    private File file = null;
    private final List<List<String>> fileContent = new ArrayList<>();
    private int maxColumns;
    private String fileName;
    private int maxCellLen = 15;

    private boolean fileLoaded = false;

    private boolean openFile(String path) {
        try{
            File file = new File(path);
            if (file.exists()) {
                if (path.split("\\.")[path.split("\\.").length - 1].equals("csv")) {
                    this.file = new File(path);
                    fileLoaded = true;
                    System.out.println("Вы успешно открыли файл!");
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
                fileContent.clear();
                maxColumns = 0;
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(";");
                    if (data.length > maxColumns) {
                        maxColumns = data.length;
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
        if (fileLoaded) {
            if (file.length() == 0) {
                System.out.println("Файл пуст.");
            } else {
                System.out.println(fileName + ":");
                printLine(maxColumns);
                Formatter fmt;
                for (var row : fileContent) {
                    fmt = new Formatter();
                    fmt.format("%1s", "|");
                    int counter = 0;
                    for (String cell : row) {
                        counter++;
                        if (cell.length() > maxCellLen - 1) {
                            fmt.format("%" + maxCellLen + "s", cell.substring(0, maxCellLen - 4) + "...|");
                        } else {
                            fmt.format("%" + maxCellLen + "s", cell + "|");
                        }
                    }
                    while (counter < maxColumns) {
                        fmt.format("%" + maxCellLen + "s", "|");
                        counter++;
                    }
                    System.out.println(fmt);
                }
            }
            printLine(maxColumns);
        } else {
            System.out.println("Сначала выберите файл!");
        }
    }

    public void print(int numOfColumn) {
        if (fileLoaded) {
            Formatter fmt = new Formatter();
            for (int j = 0; j < fileContent.size(); ++j) {
                for (int i = 0; i < fileContent.get(j).size(); ++i) {
                    if (i == numOfColumn - 1) {
                        fmt.format("%1s", "\n|");
                        if (fileContent.get(j).get(i).length() > maxCellLen - 1) {
                            fmt.format("%" + maxCellLen + "s", fileContent.get(j).get(i).substring(0, maxCellLen - 4) + "..|");
                        } else {
                            fmt.format("%" + maxCellLen + "s", fileContent.get(j).get(i) + "|");
                        }
                    }
                }
            }
            if (fmt.toString().equals("")) {
                System.out.println("Столбец " + numOfColumn + " пуст.");
            } else {
                System.out.println("Столбец " + numOfColumn + ":");
                System.out.println(fmt);
            }
            printLine(1);
        } else {
            System.out.println("Сначала выберите файл!");
        }
    }

    private String getFileName (String path) {
        String name;
        if (path.split("/").length == 1) {
            return path.split("\\\\")[path.split("\\\\").length - 1];
        } else {
            return path.split("/")[path.split("/").length - 1];
        }
    }

    public void changeLen(int newLen) {
        maxCellLen = newLen;
    }

    private void printLine(int numOfColumns) {
        int numOfSlashes = maxCellLen * numOfColumns + 1;
        for (int i = 0; i < numOfSlashes; ++i) {
            System.out.print("-");
        }
        System.out.println();
    }

    public String getFileName() {
        return fileName;
    }

    public int getMaxColumns() {
        return maxColumns;
    }

    public int getMaxCellLen() {
        return maxCellLen;
    }
}
