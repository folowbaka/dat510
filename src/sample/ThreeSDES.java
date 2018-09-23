package sample;

public class ThreeSDES extends SDES {

    public String cipher(String rawKeyOne,String rawKeyTwo,String plainText)
    {
        String cipherText=this.cipher(rawKeyOne,plainText);
        cipherText=this.decipher(rawKeyTwo,cipherText);
        cipherText=this.cipher(rawKeyOne,cipherText);
        return cipherText;

    }

    public String decipher(String rawKeyOne,String rawKeyTwo,String cipherText)
    {

        return this.decipher(rawKeyOne,this.cipher(rawKeyTwo,this.decipher(rawKeyOne,cipherText)));
    }


}
