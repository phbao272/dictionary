package Dictionary;

import java.util.ArrayList;
import java.util.Collections;

public class Dictionary {
    private ArrayList<Word> listWords = new ArrayList<>();

    public void addWord(Word word) {
        listWords.add(word);
    }

    public void editWord(int pos, String wordExplain) {
        listWords.get(pos).setWord_explain(wordExplain);
    }

    public void editWord(int pos, String wordTarget, String wordExplain) {
        listWords.get(pos).setWord_target(wordTarget);
        listWords.get(pos).setWord_explain(wordExplain);
    }

    public void removeWord(int pos) {
        listWords.remove(listWords.get(pos));
    }

    public Word getWord(int pos) {
        return listWords.get(pos);
    }

    public void setListWords(ArrayList<Word> listWords) {
        this.listWords = listWords;
    }

    public ArrayList<Word> getListWords() {
        return listWords;
    }

    public void sortDict() {
        Collections.sort(listWords);
    }

    public int getSize() {
        return listWords.size();
    }


}