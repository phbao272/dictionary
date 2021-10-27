package sample;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerModify {

    @FXML
    private TextArea word;

    @FXML
    private TextArea mean;

    @FXML
    private Button modifyButton;

    @FXML
    private Button closeButton;

    private ControllerAdd ControllerModifyException = new ControllerAdd();

    void initDataModify(String _word, String _mean){
        word.setText(_word);
        mean.setText(_mean);
    }

    public void run() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ControllerModify.fxml"));
            Scene scene = new Scene(root);
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Sửa từ");
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modifyButtonClicked() {
        String Word = word.getText().trim();
        String Mean = mean.getText().trim();
        if (Word.length() == 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa nhập từ. Vui lòng kiểm tra lại!");
            alert.showAndWait();
        } else if (Mean.length() == 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa nhập nghĩa. Vui lòng kiểm tra lại!");
            alert.showAndWait();
        } else if (Controller.contains(Word).equals("The word dose not exist")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ControllerModifyException.fxml"));
                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Thêm từ!");
                window.setScene(new Scene(loader.load()));
                ControllerAdd add = loader.getController();
                add.initDataAdd(Word, Mean);
                window.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Controller.dictManagement.editWord(Word, Mean);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Sửa từ thành công!");
            alert.showAndWait();
            Stage stage = (Stage) modifyButton.getScene().getWindow();
            stage.close();
        }
    }

    public void openModifyExWindow() {
        ControllerModifyException.run();
    }

    public void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}