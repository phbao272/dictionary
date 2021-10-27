package sample;

import Dictionary.DictionaryManagement;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import ggTranslate.Audio;
import ggTranslate.Translator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;

import javax.speech.Central;
import javax.speech.EngineException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends Application implements Initializable {
    @FXML
    private TextField txtSearch;
    @FXML
    private TextArea txtOutput;
    @FXML
    private TextArea txtSuggest;
    @FXML
    private TextArea txtHistory;
    @FXML
    private Text mode;

    public static DictionaryManagement dictManagement = new DictionaryManagement();
    private Translator translator = new Translator();
    private Audio audio = new Audio();

    public static boolean EnToVi = true;

    private ControllerAdd controllerAdd = new ControllerAdd();
    private ControllerRemove controllerRemove = new ControllerRemove();
    private ControllerModify controllerModify = new ControllerModify();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        renderListWord();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setTitle("Dictionary");
        stage.setScene(new Scene(root, 865, 660));
        stage.show();
    }

    public void renderListWord() {
        dictManagement.insertFromFile("src/Dictionary/Database/dictionaries.txt");
        txtSuggest.clear();
        String allWords = dictManagement.showAllWords();
        txtSuggest.setText(allWords);
    }

    public void textChange(KeyEvent keyEvent) throws Exception {
        txtOutput.clear();
        if (!EnToVi) {
            if(keyEvent.getCode() == KeyCode.ENTER) {
                Submit();
            }
        } else {
            String wordSearch = txtSearch.getText();
            System.out.println(wordSearch);
            String wordSuggest = dictManagement.dictionarySearcher(wordSearch);
            if(keyEvent.getCode() == KeyCode.ENTER) {
                Submit();
            }
            if (wordSuggest.equals("")) {
                renderListWord();
            } else {
                txtSuggest.setText(wordSuggest);
            }
        }

    }

    public void Submit() throws Exception {
        String wordSearch = txtSearch.getText();

        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);

        if (wordSearch.length() != 0) {
            System.out.println(wordSearch);
            String res = dictManagement.dictionaryLookup(wordSearch);
            if (res.equals("The word dose not exist")) {
                searchWithGG();
            } else {
                addHistorySearch(wordSearch, res);
                txtOutput.setText(res);
            }

        } else {
            alert.setContentText("Bạn chưa nhập gì cả");
            alert.showAndWait();
        }
    }

    public void changeMode(ActionEvent e) {
        EnToVi = !EnToVi;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);

        if (EnToVi) {
            mode.setText("EN-VN");
            alert.setContentText("Chế độ dịch EN-VN!");
            System.out.println("EN-VN");
        } else {
            mode.setText("VN-EN");
            alert.setContentText("Chế độ dịch VN-EN!");
            System.out.println("VN-EN");
        }

        alert.showAndWait();
    }

    public void SaveFile()  {
        dictManagement.dictionaryExportToFile("src/Dictionary/Database/dictionaries.txt");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Lưu thay đổi thành công!");
        alert.showAndWait();

        renderListWord();
    }

    public void VoiceTextToSpeech(ActionEvent actionEvent) throws EngineException {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
        Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");

        Voice voice;
        voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
        }
        try {
            voice.speak(txtSearch.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchWithGG() throws Exception {
        try {
            String word_target = txtSearch.getText().toLowerCase();
            if (word_target.length() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cảnh báo!");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập từ cần tìm kiếm!");
                alert.showAndWait();
                return;
            }
            String word_explain;
            if (EnToVi) {
                word_explain = translator.callUrlAndParseResult("en", "vi", word_target);
            } else {
                word_explain = translator.callUrlAndParseResult("vi", "en", word_target);
            }
            txtOutput.setText(word_explain);
            addHistorySearch(word_target, word_explain);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kết nối bị gián đoạn!");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng kiểm tra kết nối Internet!");
            alert.showAndWait();
        } finally {
            System.out.println("Finish searchWithGG");
        }
    }

    public void VoiceSearchWithGG() throws IOException, JavaLayerException {
        try {
            String word = txtSearch.getText();
            if (word.length() == 0) return;
            InputStream sound;
            if (EnToVi) {
                sound = audio.getAudio(word, "en");
            } else {
                sound = audio.getAudio(word, "vi");
            }
            audio.play(sound);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kết nối bị gián đoạn!");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng kiểm tra kết nối Internet!");
            alert.showAndWait();
            return;
        }
    }

    public void VoiceOutputWithGG() throws IOException, JavaLayerException {
        try {
            String word = txtOutput.getText();
            if (word.length() == 0) return;
            InputStream sound;
            if (EnToVi) {
                String[] arrWordExplain = word.split("\n");
                if (arrWordExplain.length != 1) {
                        sound = audio.getAudio(arrWordExplain[2], "vi");
                        audio.play(sound);
                } else {
                    sound = audio.getAudio(word, "vi");
                    audio.play(sound);
                }
            } else {
                sound = audio.getAudio(word, "en");
                audio.play(sound);
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kết nối bị gián đoạn!");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng kiểm tra kết nối Internet!");
            alert.showAndWait();
            return;
        }
    }

    public void addHistorySearch(String word_target, String word_explain) {
        String historySearch =  dictManagement.historySearch(word_target, word_explain);

        txtHistory.setText(historySearch);
    }

    public static String contains(String key) {
        return dictManagement.dictionaryLookup(key);
    }

    public void openAddWindow() {
        controllerAdd.run();
//        SaveFile();
    }

    public void openRemoveWindow() {
        controllerRemove.run();
//        SaveFile();
    }

    public void openModifyWindow() {
        controllerModify.run();
//        SaveFile();
    }
}
