package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Controller {

    @FXML
    private TextArea polyACipherText;

    @FXML
    private Button polyABtn;

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
}

