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
                printLine(1);
            } else {
                int currentColumn = 0;
                System.out.println(fileName + ":");
                Formatter fmt;
                while (currentColumn < maxColumns) {
                    fmt = new Formatter();
                    for (int i = currentColumn + 1; i < Math.min(currentColumn + 5, maxColumns) + 1; ++i) {
                        fmt.format("%" + maxCellLen + "s", "Столбец " + i + ":");
                    }
                    System.out.println(fmt);
                    printLine(Math.min(maxColumns - currentColumn, 5));
                    for (var row : fileContent) {
                        fmt = new Formatter();
                        fmt.format("%1s", "|");
                        int counter = 0;
                        for (int i = currentColumn; i < Math.min(currentColumn + 5, maxColumns); ++i) {
                            counter++;
                            if (row.size() > i && row.get(i).length() > maxCellLen - 1) {
                                fmt.format("%" + maxCellLen + "s", row.get(i).substring(0, maxCellLen - 4) + "...|");
                            } else if (row.size() > i) {
                                fmt.format("%" + maxCellLen + "s", row.get(i) + "|");
                            } else {
                                fmt.format("%" + maxCellLen + "s", "" + "|");
                            }
                        }
                        while (counter < Math.min(maxColumns - currentColumn, 5)) {
                            fmt.format("%" + maxCellLen + "s", "|");
                            counter++;
                        }
                        System.out.println(fmt);
                    }
                    printLine(Math.min(maxColumns - currentColumn, 5));
                    currentColumn += 5;
                }
            }
        } else {
            System.out.println("Сначала выберите файл!");
        }
    }

    public void print(int numOfColumn) {
        if (fileLoaded) {
            boolean isEmpty = true;
            Formatter fmt = new Formatter();
            for (int j = 0; j < fileContent.size(); ++j) {
                fmt.format("%1s", "\n|");
                if (fileContent.get(j).size() > numOfColumn - 1 && fileContent.get(j).get(numOfColumn - 1).length() > maxCellLen - 1) {
                    fmt.format("%" + maxCellLen + "s", fileContent.get(j).get(numOfColumn - 1).substring(0, maxCellLen - 4) + "..|");
                    isEmpty = false;
                } else if (fileContent.get(j).size() > numOfColumn - 1){
                    fmt.format("%" + maxCellLen + "s", fileContent.get(j).get(numOfColumn - 1) + "|");
                    isEmpty = false;
                } else {
                    fmt.format("%" + maxCellLen + "s", "" + "|");
                }
            }
            if (isEmpty) {
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
