package sample;

import java.util.HashMap;
import java.util.Map;

public class Vigenere {

    public final static double ENGLISH_IC = 0.0667;
    public final static double MAX_KEY_LENGTH = 10;
    public static HashMap<Character,Integer> numLetter=new HashMap<Character, Integer>();
    private static HashMap<Character,Double> englishFreq=new HashMap<Character, Double>();

    public double coincidenceIndex(int keyLength,String cipherText,int textLength)
    {
        String[] partCipher=new String[keyLength];
        double coeff=0;

        for(int i=0;i<keyLength;i++)
        {
            partCipher[i]="";

            for(int j=i;j<textLength;j+=keyLength)
            {
                partCipher[i] += cipherText.charAt(j);
            }
            int partLength=partCipher[i].length();
            coeff+=coincidenceCalcul(partCipher[i], partLength);

        }
        return coeff/keyLength;



    }
    public double coincidenceCalcul(String cipherText,int texlength)
    {
        resetFreq();
        cipherText=cipherText.toUpperCase();

        int i=0;
        while(i<texlength && numLetter.containsKey(cipherText.charAt(i)))
        {

                int freq = (int) numLetter.get(cipherText.charAt(i));
                freq++;
                numLetter.put(cipherText.charAt(i), freq);
            i++;

        }
        if(i<texlength)
            return 100;

        double coef=0;
        for (Map.Entry entry : numLetter.entrySet())
        {
            int val=(int)entry.getValue();
            double upLine=(val*(val-1));
            double bottomLine=(texlength*(texlength-1));
            coef+=upLine/bottomLine;
        }

        return coef;


    }
    public  String getKey(int keyLength,String cipherText,int textLength)
    {
        String[] key=new String[keyLength];
        String[] partCipher=new String[keyLength];
        String bestKey="";
        resetFreq();
        for(int i=0;i<keyLength;i++)
        {
            key[i]="";
            partCipher[i]="";
            for(int j=i;j<textLength;j+=keyLength)
            {
                int freq=(int)numLetter.get(cipherText.charAt(j));
                freq++;
                numLetter.put(cipherText.charAt(j),freq);
                partCipher[i]+=cipherText.charAt(j);
            }
            char mostFrequentLetter=mostFreqLetter();
            key[i]+=decipherChar(mostFrequentLetter,"ETAOIN");
            bestKey+=detectBestKeyChar(key[i],partCipher[i]);

            resetFreq();
        }
        return bestKey;
    }

    public String decipher(String key,String cipherText,int textLength,int keyLength)
    {
        String decipherText="";
        int j=0;
        for(int i=0;i<textLength;i++)
        {
            decipherText+=decipherChar(cipherText.charAt(i),""+key.charAt(j));
            j++;
            if(j==keyLength)
                j=0;
        }
        return decipherText;

    }
    public  String decipherChar(char cipherChar,String keyChar)
    {
        String keys="";
        for(int i=0;i<keyChar.length();i++)
        {
            int decipherChar=(cipherChar%65) -(keyChar.charAt(i)%65);
            if(decipherChar<0)
                decipherChar+=91;
            else
                decipherChar+=65;
            keys+=(char)decipherChar;
        }

        return keys;
    }
    public char detectBestKeyChar(String keyChars,String partCipher)
    {
        int bestKey=-1;
        double distanceFreq=1000;
        for(int i=0;i<keyChars.length();i++)
        {
            String decipherText="";
            resetFreq();
            double distance=0;
            for (int j = 0; j < partCipher.length(); j++)
            {
                decipherText += decipherChar(partCipher.charAt(j), ""+keyChars.charAt(i));
                int freq=(int)numLetter.get(decipherText.charAt(j));
                freq++;
                numLetter.put(decipherText.charAt(j),freq);

            }
            for (Map.Entry entry : numLetter.entrySet())
            {
                int val=(int)entry.getValue();
                int length=decipherText.length();
                double freqPart=((double)val/(double)length)*100;
                double freqEnglish=englishFreq.get(entry.getKey());
                distance+=Math.abs(freqEnglish-freqPart);

            }

            if(distance<distanceFreq)
            {
                distanceFreq=distance;
                bestKey = i;
            }
        }
        return keyChars.charAt(bestKey);
    }
    public static void fillAlphabet(String alphabet)
    {
        for(int i=0;i<alphabet.length();i++)
        {
            numLetter.put(alphabet.charAt(i),0);
        }
    }
    public static void fillAlphabetFreq(String alphabet)
    {
        double[] freq={8.167,1.492,2.782,4.253,12.702,2.228,2.015,6.094,6.966,0.153,0.772,4.025,2.406,6.749,7.507,1.929,0.095,5.987,6.327,9.056,2.758,0.978,2.360,0.150,1.974,0.074};
        for(int i=0;i<alphabet.length();i++)
        {
            englishFreq.put(alphabet.charAt(i),freq[i]);
        }
    }
    public static void resetFreq()
    {
        for (Map.Entry entry : numLetter.entrySet())
        {
            entry.setValue(0);
        }
    }

    public static char mostFreqLetter()
    {

        char freqLetter=' ';
        int maxFreq=0;
        for (Map.Entry entry : numLetter.entrySet())
        {
            int val=(int)entry.getValue();

            if(val>maxFreq)
            {
                maxFreq=val;
                freqLetter=(char)entry.getKey();
            }

        }
        return freqLetter;
    }
}

