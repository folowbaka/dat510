package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Controller {

    @FXML
    private TextArea polyACipherText;

    @FXML
    private Button polyABtn;

    @FXML
    private TextField rawKeyTextField;

    @FXML
    private TextField plainSDESTextField;

    @FXML
    private TextField cipherSDESTextField;

    @FXML
    private TextField rawKeyOneThreeTextField;

    @FXML
    private TextField rawKeyTwoThreeTextField;

    @FXML
    private TextField plainThreeSDESTextField;

    @FXML
    private TextField cipherThreeSDESTextField;



    public void polyAPlainText() {
        String cipherText = polyACipherText.getText();
        cipherText = cipherText.replaceAll("[ \\n]", "");
        Vigenere.fillAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Vigenere.fillAlphabetFreq("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        int textLength = cipherText.length();
        int keyLength = 0;
        double icMinDistance = 20;
        Vigenere v = new Vigenere();
        for (int i = 1; i <= Vigenere.MAX_KEY_LENGTH; i++) {
            double icCoeff = v.coincidenceIndex(i, cipherText, textLength);
            System.out.println(icCoeff);
            double distance = Math.abs(Vigenere.ENGLISH_IC - icCoeff);
            if (distance < icMinDistance) {
                keyLength = i;
                icMinDistance = distance;
            }

        }
        System.out.println(keyLength);
        String key = v.getKey(keyLength, cipherText, textLength);
        polyACipherText.setText(v.decipher(key, cipherText, textLength, keyLength));

    }

    public void sDESCipher()
    {
        String plainText=plainSDESTextField.getText();
        String rawKey=rawKeyTextField.getText();
        SDES sdes=new SDES();
        cipherSDESTextField.setText(sdes.cipher(rawKey,plainText));
    }

    public void sDESDecipher()
    {
        String cipherText=cipherSDESTextField.getText();
        String rawKey=rawKeyTextField.getText();
        SDES sdes=new SDES();
        plainSDESTextField.setText(sdes.decipher(rawKey,cipherText));

    }
    public void ThreeSDESCipher()
    {
        String plainText=plainThreeSDESTextField.getText();
        String rawKeyOne=rawKeyOneThreeTextField.getText();
        String rawKeyTwo=rawKeyTwoThreeTextField.getText();
        ThreeSDES threeSDES=new ThreeSDES();
        String cipherText=threeSDES.cipher(rawKeyOne,rawKeyTwo,plainText);
        cipherThreeSDESTextField.setText(cipherText);
    }
    public void ThreeSDESDecipher()
    {
        String cipherText=cipherThreeSDESTextField.getText();
        String rawKeyOne=rawKeyOneThreeTextField.getText();
        String rawKeyTwo=rawKeyTwoThreeTextField.getText();
        ThreeSDES threeSDES=new ThreeSDES();
        String plainText=threeSDES.decipher(rawKeyOne,rawKeyTwo,cipherText);
        plainThreeSDESTextField.setText(plainText);

    }
}

