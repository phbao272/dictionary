package Dictionary;

import java.util.Collections;
import java.util.Scanner;

public class DictionaryCommandLine {

    private DictionaryManagement dictManagement = new DictionaryManagement();

    public void showAllWords() {
        int dictSize = dictManagement.getDict().getSize();
        dictManagement.getDict().sortDict();
        System.out.println("com.example.stilldicbutmyui.Dictionary.Dictionary EN-VN:");
        System.out.format("%-5s %-20s %-20s\n", "No", "| English", "| Vietnamese");

        for (int i = 0; i < dictSize; i++) {
            System.out.format("%-5s %-20s %-20s\n",
                    (i + 1),
                    ("| " + dictManagement.getDict().getWord(i).getWord_target()),
                    ("| " + dictManagement.getDict().getWord(i).getWord_explain()));
        }
    }

    public void dictionaryBasic() {
        dictManagement.insertFromCommandline();
        showAllWords();
    }

    public void dictionaryAdvanced() {

        dictManagement.insertFromFile("src/Dictionary/Database/dictionaries.txt");
        showAllWords();
        System.out.println("Search word-1: ");
        System.out.println("Add word-2: ");
        System.out.println("Remove word-3: ");
        System.out.println("Edit word-4: ");
        System.out.println("Searcher-5");
        System.out.println("Export to file-8: ");
        System.out.println("Print dictionary-9: ");
        System.out.println("Exit-0: \n");

        int option = -1;
        do {

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your option: ");
            option = sc.nextInt();
            String tmp;
            String word_target;
            String word_explain;
            switch (option) {
                case 1:
                    System.out.println(dictManagement.dictionaryLookup());
                    break;
                case 2:
                    System.out.println("Add Word: ");
                    System.out.print("EN: ");
                    tmp = sc.nextLine();
                    word_target = sc.nextLine();
                    System.out.print("VN: ");
                    word_explain = sc.nextLine();

                    dictManagement.addWord(word_target, word_explain);
                    break;
                case 3:
                    System.out.print("Remove Word: ");
                    word_target = sc.nextLine();

                    dictManagement.removeWord(word_target);
                    break;
                case 4:
                    System.out.println("Edit Word: ");
                    System.out.print("EN: ");
                    tmp = sc.nextLine();
                    word_target = sc.nextLine();
                    System.out.print("VN: ");
                    word_explain = sc.nextLine();

                    dictManagement.editWord(word_target, word_explain);
                    break;
                case 5:
                    dictionarySearcher();
                    break;
                case 0:
                    System.out.println("Finish!!");
                    break;
                case 8:
                    dictManagement.dictionaryExportToFile("src/Dictionary/Database/dictionaries.txt");
                    break;
                case 9:
                    showAllWords();
                    break;
                default:
                    System.out.println("option does not exist");
                    break;
            }
        } while (option != 0);

//        showAllWords(dict);
    }

    public void dictionarySearcher() {
        boolean isFind = false;
        int cnt = 1;
        Scanner sc = new Scanner(System.in);

        System.out.print("Searcher: ");
        String searchWord = sc.nextLine();

        int pos = Collections.binarySearch(dictManagement.getDict().getListWords(), new Word(searchWord));

        if (pos < 0) {
            pos = -pos - 1;
        }

        for (int i = pos; i < dictManagement.getDict().getSize(); i++) {
            String str = dictManagement.getDict().getWord(i).getWord_target();

            if (isFind && !str.startsWith(searchWord.toLowerCase())) {
                break;
            }

            if (str.startsWith(searchWord.toLowerCase())) {
                isFind = true;

                if (isFind && cnt == 1) {
                    System.out.format("%-5s %-20s %-20s\n", "No", "| English", "| Vietnamese");
                }

                System.out.format("%-5s %-20s %-20s\n",
                        (cnt),
                        ("| " + dictManagement.getDict().getWord(i).getWord_target()),
                        ("| " + dictManagement.getDict().getWord(i).getWord_explain()));
                cnt++;
            }
        }

        if (!isFind) {
            System.out.println("No matching words found");
        }
    }

    public static void main(String[] args) {
        DictionaryCommandLine dictCommandLine = new DictionaryCommandLine();
//        dictCommandLine.dictionaryBasic();
        dictCommandLine.dictionaryAdvanced();

    }
}