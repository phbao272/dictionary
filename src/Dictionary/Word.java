package Dictionary;

public class Word implements Comparable<Word> {

    private String word_target;
    private String word_explain;

    public Word() {

    }

    //Search word
    public Word(String word_target) {
        this.word_target = word_target;
    }

    public Word(String word_target, String word_explain) {
        this.word_target = word_target.trim().toLowerCase();
        this.word_explain = word_explain.trim().toLowerCase();
    }

    public String getWord_target() {
        return word_target;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target.trim().toLowerCase();
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain.trim().toLowerCase();
    }

    @Override
    // So sánh dựa vào word target
    public int compareTo(Word word) {
        return this.word_target.compareToIgnoreCase(word.word_target);
    }
}