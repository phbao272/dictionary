package Dictionary;

import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class DictionaryManagement {

    private Dictionary dict = new Dictionary();

    public Dictionary getDict() {
        return dict;
    }

    public static final String URL_INPUT = "src/Dictionary/Database/dictionaries.txt";
    public static final String URL_OUTPUT = "src/Dictionary/Database/dictionariesOUT.txt";

    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);

        System.out.print("The number of words to add to the dictionary: ");
        int n = sc.nextInt();

        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("EN-" + (i + 1) + ": ");
            String word_target = sc.nextLine();
            System.out.print("VN-" + (i + 1) + ": ");
            String word_explain = sc.nextLine();

            dict.addWord(new Word(word_target, word_explain));
        }
    }

    public void insertFromFile(String URL) {
        dict = new Dictionary();

        FileInputStream fis = null;
        BufferedReader reader = null;

        try {

            fis = new FileInputStream(URL);
            reader = new BufferedReader(new InputStreamReader(fis));

            boolean firstAddWord = false;

            String line = reader.readLine();;
            String[] wordArr = new String[2];

            while (line != null) {
                boolean newWord;
                if (line.startsWith("@")) {
                    newWord = true;
                    if (newWord && firstAddWord) {
                        dict.addWord(new Word(wordArr[0], wordArr[1]));
                    }

                    wordArr = line.split(" /");

                    wordArr[0] = wordArr[0].replace("@", "");
                    firstAddWord = true;
                } else {
                    wordArr[1] = wordArr[1] + "\n" + line;
                }

                line = reader.readLine();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("Insert From File successful!!!");
        }
    }

    // Dùng cho phiên bản commandline
    public String dictionaryLookup() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the search word: ");
        String searchWord = sc.nextLine();

        int pos = Collections.binarySearch(dict.getListWords(), new Word(searchWord));

        if (pos >= 0) {
            System.out.println(dict.getWord(pos).getWord_target().length());
            return searchWord + " means: " + dict.getWord(pos).getWord_explain();
        } else {
            return "The word dose not exist";
        }
    }

    // Dùng phiên bản giao diện
    public String dictionaryLookup(String searchWord) {
        int pos = Collections.binarySearch(dict.getListWords(), new Word(searchWord));
        if (pos >= 0) {
            return dict.getWord(pos).getWord_explain();
        } else {
            return "The word dose not exist";
        }
    }

    public void addWord(String word_target, String word_explain) {
        Word add = new Word(word_target, word_explain);
        dict.addWord(add);
        dict.sortDict();
    }

    public void editWord(String word_target, String word_explain) {
        int pos = Collections.binarySearch(dict.getListWords(), new Word(word_target));
        if (pos >= 0) {
            dict.editWord(pos, word_target, word_explain);
        } else {
            System.out.println("The word dose not exist");
        }
        dict.sortDict();
    }

    public void removeWord(String word_target) {
        int pos = Collections.binarySearch(dict.getListWords(), new Word(word_target));
        if (pos >= 0) {
            dict.removeWord(pos);
        } else {
            System.out.println("The word dose not exist");
        }
    }

    public void dictionaryExportToFile(String URL) {
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            fout = new FileOutputStream(URL);
            bout = new BufferedOutputStream(fout);

            for (int i = 0; i < dict.getSize(); i++) {
                String line = "@" + dict.getWord(i).getWord_target() + " /" + dict.getWord(i).getWord_explain() + "\n\n";
                bout.write(line.getBytes());
                bout.flush();
            }
            bout.write("@ finish".getBytes());
            bout.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("Export to file successful!!!");
        }
    }

    // Dùng trong phiên bản giao diện
    public String showAllWords() {
        int dictSize = dict.getSize();
        dict.sortDict();

        StringBuilder allWords = new StringBuilder();

        for (int i = 0; i < dictSize; i++) {
            allWords.append(dict.getWord(i).getWord_target() + "\n");
        }

        return allWords.toString();
    }

//    src/Dictionary/Database/dataHistorySearch.txt
    private StringBuilder historySearch = new StringBuilder();

    // Dùng trong phiên bản giao diện
    public String historySearch(String word_target, String word_explain) {
        Date date = java.util.Calendar.getInstance().getTime();
        String[] arrWord_explain = word_explain.split("\n");
        if (arrWord_explain.length == 1) {
            historySearch.insert(0, "(" + date + ") " + word_target + " : " + word_explain + "\n");
        } else {
            historySearch.insert(0, "(" + date + ") " + word_target + " : " + arrWord_explain[2] + "\n");
        }

        return historySearch.toString();
    }

    // Dùng trong phiên bản giao diện
    public String dictionarySearcher(String searchWord) {
        if (searchWord.equals("")) {
            System.out.println("Null");
            return "";
        }

        boolean isFind = false;
        StringBuilder wordSearcher = new StringBuilder();

        int pos = Collections.binarySearch(dict.getListWords(), new Word(searchWord));

        if (pos < 0) {
            pos = -pos - 1;
        }

        for (int i = pos; i < dict.getSize(); i++) {
            String str = dict.getWord(i).getWord_target();
            if (isFind && !str.startsWith(searchWord.toLowerCase())) {
                break;
            }
            if (str.startsWith(searchWord.toLowerCase())) {
                isFind = true;
                wordSearcher.append(dict.getWord(i).getWord_target() + "\n");
            }
        }
        if (!isFind) {
            return " ";
        }
        return wordSearcher.toString();
    }
}