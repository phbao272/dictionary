package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerAdd{

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    @FXML
    private TextArea word;

    @FXML
    private TextArea mean;

    void initialize(){

    }
    void initDataAdd(String _word, String _mean){
        word.setText(_word);
        mean.setText(_mean);
    }

    public void run() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ControllerAdd.fxml"));
            Scene scene = new Scene(root);
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Thêm từ mới");
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(String word_target, String i) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ControllerAdd.fxml"));
            Scene scene = new Scene(root);
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Thêm từ mới");
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addButtonClicked() {
        String Word = word.getText().trim();
        String Mean = mean.getText().trim();
        if (Word.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa nhập từ. Vui lòng kiểm tra lại!");
            alert.showAndWait();
        } else if (Mean.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa nhập nghĩa. Vui lòng kiểm tra lại!");
            alert.showAndWait();
        } else if (!Controller.contains(Word).equals("The word dose not exist")) {
            try {
//                Parent root = FXMLLoader.load(getClass().getResource("ControllerAddException.fxml"));
//                Scene scene = new Scene(root);
//                Stage window = new Stage();
//                window.initModality(Modality.APPLICATION_MODAL);
//
//                window.setScene(scene);
//                window.showAndWait();
//
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ControllerAddException.fxml"));
                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Sửa từ!");
                window.setScene(new Scene(loader.load()));
                ControllerModify modify = loader.getController();
                modify.initDataModify(Word, Mean);
                window.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Đã thêm từ " + Word + " thành công");
            Controller.dictManagement.addWord(Word, Mean);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Thêm từ thành công!");
            alert.showAndWait();
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        }
    }

    public void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}