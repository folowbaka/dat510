package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Controller {

    @FXML
    private TextArea polyACipherText;

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

    @FXML
    private TextField crackRawKeyOneTF;

    @FXML
    private TextField crackRawKeyTwoTF;

    @FXML
    private TextArea crackTextArea;

    /**
     * Method to decipher a Vigenere cipherText
     */
    public void polyAPlainText() {
        String cipherText = polyACipherText.getText();
        cipherText = cipherText.replaceAll("[ \\n]", "");
        Vigenere.fillAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Vigenere.fillAlphabetFreq("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        int textLength = cipherText.length();
        int keyLength = 0;
        double icMinDistance = 20;
        Vigenere v = new Vigenere();
        // Search the key length
        for (int i = 1; i <= Vigenere.MAX_KEY_LENGTH; i++)
        {
            double icCoeff = v.coincidenceIndex(i, cipherText, textLength);
            System.out.println("key length : "+i+" IC : "+icCoeff);
            double distance = Math.abs(Vigenere.ENGLISH_IC - icCoeff);
            if (distance < icMinDistance) {
                keyLength = i;
                icMinDistance = distance;
            }

        }
        System.out.println(keyLength);
        //Obtain the key with its key length
        String key = v.getKey(keyLength, cipherText, textLength);
        System.out.println("Key : "+key);
        //Decipher the text with the key found
        polyACipherText.setText(v.decipher(key, cipherText, textLength, keyLength));

    }

    /**
     * Method to cipher wirh SDES a plaintext
     */
    public void sDESCipher()
    {
        String plainText=plainSDESTextField.getText();
        String rawKey=rawKeyTextField.getText();
        SDES sdes=new SDES();
        cipherSDESTextField.setText(sdes.cipher(rawKey,plainText));
    }

    /**
     * Method to decipher a SDES cipherText
     */
    public void sDESDecipher()
    {
        String cipherText=cipherSDESTextField.getText();
        String rawKey=rawKeyTextField.getText();
        SDES sdes=new SDES();
        plainSDESTextField.setText(sdes.decipher(rawKey,cipherText));

    }
    /**
     * Method to cipher wirh TripleSDES a plaintext
     */
    public void ThreeSDESCipher()
    {
        String plainText=plainThreeSDESTextField.getText();
        String rawKeyOne=rawKeyOneThreeTextField.getText();
        String rawKeyTwo=rawKeyTwoThreeTextField.getText();
        ThreeSDES threeSDES=new ThreeSDES();
        String cipherText=threeSDES.cipher(rawKeyOne,rawKeyTwo,plainText);
        System.out.println(cipherText);
        cipherThreeSDESTextField.setText(cipherText);
    }
    /**
     * Method to decipher a TripleSDES cipherText
     */
    public void ThreeSDESDecipher()
    {
        String cipherText=cipherThreeSDESTextField.getText();
        String rawKeyOne=rawKeyOneThreeTextField.getText();
        String rawKeyTwo=rawKeyTwoThreeTextField.getText();
        ThreeSDES threeSDES=new ThreeSDES();
        String plainText=threeSDES.decipher(rawKeyOne,rawKeyTwo,cipherText);
        System.out.println(plainText);
        plainThreeSDESTextField.setText(plainText);

    }
    /**
     * Method to decipher a SDES cipherText without the key
     */
    public void crackSDES()
    {
        String cipherText=crackTextArea.getText();
        cipherText=cipherText.replaceAll("\n","");
        String rawKey="0000000000";
        String binaryToAdd="0000000001";
        SDES sdes=new SDES();
        String plaintext="";
        int textLength=cipherText.length();
        int numberOfBlock=0;
            numberOfBlock=textLength/8;
        if(textLength%8!=0)
            numberOfBlock++;
        double minDistance=100;
        Vigenere vg=new Vigenere();
        Vigenere.fillAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        String bestKey="";
        for(int i=0;i<1024;i++)
        {
            String cipherBlockText="";
            String protoPlainText="";
            for(int j=0;j<numberOfBlock;j++)
            {

                if(j==numberOfBlock-1 && textLength-(j*8)<8)
                    cipherBlockText=cipherText.substring(j*8,j*8+(textLength-(j*8)-1));
                else
                    cipherBlockText=cipherText.substring(j*8,j*8+8);
                protoPlainText+=(char)Integer.parseInt(sdes.decipher(rawKey,cipherBlockText),2);


            }
            double coeff=vg.coincidenceCalcul(protoPlainText,protoPlainText.length());
            if((double)(Math.abs(Vigenere.ENGLISH_IC-coeff))<minDistance)
            {
                minDistance=(Math.abs(Vigenere.ENGLISH_IC-coeff));
                plaintext=protoPlainText;
                bestKey=rawKey;
            }
            int newKey=Integer.parseInt(rawKey,2)+Integer.parseInt(binaryToAdd,2);
            rawKey=Integer.toBinaryString(newKey);
            int keyLength=rawKey.length();
            rawKey=sdes.fillZeroBinary(keyLength,rawKey,10);

        }
        System.out.println("Raw key :"+bestKey);
        crackRawKeyOneTF.setText(bestKey);
        System.out.println(plaintext);
        crackTextArea.setText(plaintext);


    }
    /**
     * Method to decipher a TripleSDES cipherText without the keys
     */
    public void crack3SDES()
    {
        String cipherText=crackTextArea.getText();
        cipherText=cipherText.replaceAll("\n","");
        String rawKeyOne="0000000000";
        String binaryToAdd="0000000001";
        String bestKeyOne="";
        String bestKeyTwo="";
        ThreeSDES threeSDES=new ThreeSDES();
        String plaintext="";
        int textLength=cipherText.length();
        int numberOfBlock=0;
        numberOfBlock=textLength/8;
        if(textLength%8!=0)
            numberOfBlock++;
        double minDistance=100;
        Vigenere vg=new Vigenere();
        Vigenere.fillAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        for(int i=0;i<1024;i++)
        {
            String rawKeyTwo="0000000000";

            for(int j=0;j<1024;j++)
            {
                boolean specialChaacter=false;
                String cipherBlockText="";
                String protoPlainText="";
                for(int k=0;k<numberOfBlock;k++)
                {

                    if(k==numberOfBlock-1 && textLength-(k*8)<8)
                        cipherBlockText=cipherText.substring(k*8,k*8+(textLength-(k*8)-1));
                    else
                        cipherBlockText=cipherText.substring(k*8,k*8+8);
                    protoPlainText+=(char)Integer.parseInt(threeSDES.decipher(rawKeyOne,rawKeyTwo,cipherBlockText),2);
                    String protoUpper=(""+protoPlainText.charAt(k)).toUpperCase();
                    if(!Vigenere.numLetter.containsKey(protoUpper.charAt(0)))
                    {
                        specialChaacter=true;
                        break;
                    }

                }
                if(!specialChaacter)
                {

                    double coeff = vg.coincidenceCalcul(protoPlainText, protoPlainText.length());
                    if ((double) (Math.abs(Vigenere.ENGLISH_IC - coeff)) < minDistance) {
                        minDistance = (Math.abs(Vigenere.ENGLISH_IC - coeff));
                        plaintext = protoPlainText;
                        bestKeyOne=rawKeyOne;
                        bestKeyTwo=rawKeyTwo;
                    }
                }
                int newKeyTwo=Integer.parseInt(rawKeyTwo,2)+Integer.parseInt(binaryToAdd,2);
                rawKeyTwo=Integer.toBinaryString(newKeyTwo);
                int keyTwoLength=rawKeyTwo.length();
                rawKeyTwo=threeSDES.fillZeroBinary(keyTwoLength,rawKeyTwo,10);
            }
            int newKeyOne=Integer.parseInt(rawKeyOne,2)+Integer.parseInt(binaryToAdd,2);
            rawKeyOne=Integer.toBinaryString(newKeyOne);
            int keyOneLength=rawKeyOne.length();
            rawKeyOne=threeSDES.fillZeroBinary(keyOneLength,rawKeyOne,10);


        }
        System.out.println("Raw key 1 : "+bestKeyOne);
        System.out.println("Raw key 2 : "+bestKeyTwo);
        crackRawKeyOneTF.setText(bestKeyOne);
        crackRawKeyTwoTF.setText(bestKeyTwo);
        System.out.println(plaintext);
        crackTextArea.setText(plaintext);
    }
}

