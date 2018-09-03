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


    public void polyAPlainText()
    {
        String cipherText=polyACipherText.getText();
        System.out.println(cipherText);
        int maxKeyLength=10;
        int alphaLenght=26;
        int textLength=cipherText.length();
        for(int i=0;i<textLength;i++)
        {

        }
    }

    public int friedManTest(int maxKeyLength,String cipherText,int textLength)
    {
        double[] coincidence=new double[10];
        for(int i=1;i<=maxKeyLength;i++)
        {
            for(int j=0;j<i;j++)
            {

            }

        }

    }
    public double coincidenceCalcul(String cipherText,int Texlength)
    {
        String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        HashMap numLetter=new HashMap();
        for(int i=0;i<alphabet.length();i++)
        {
           numLetter.put(alphabet.charAt(i),0);
        }
        for(int i=0;i<Texlength;i++)
        {
            int freq=(int)numLetter.get(cipherText.charAt(i));
            freq++;
            numLetter.put(cipherText.charAt(i),freq);
        }

        for(Map.Entry<Character,Integer> freq:numLetter.entrySet()) {
            char cle = ;
            int valeur = entry.getValue();
            // traitements
        }
    }
}