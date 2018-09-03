package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Controller {

    @FXML
    private TextArea polyACipherText;

    @FXML
    private Button polyABtn;


    public void polyAPlainText()
    {
        String cipherText=polyACipherText.getText();
        System.out.println(cipherText);

        int alphaLenght=26;
        int textLength=cipherText.length();
        for(int i=0;i<textLength;i++)
        {
            
        }
    }
}