package sample;

public class SDES {

    private String sK;
    public final static String[][] SZERO={{"01","00","11","10"},{"11","10","01","00"},{"00","10","01","11"},{"11","01","11","10"}};
    public final static String[][] SONE={{"00","01","10","11"},{"10","00","01","11"},{"11","00","01","00"},{"10","01","00","11"}};

    public SDES()
    {
        this.sK="";
    }
    public String cipher(String rawKey,String plainText)
    {
        String pTenkey=pTen(rawKey);
        String shiftedPTenKey=shift(pTenkey,1);
         this.sK=pEight(shiftedPTenKey);
        String permutedText=iP(plainText);
        int textLength=permutedText.length();
        String cipherText=fK(permutedText.substring(0,textLength/2),permutedText.substring(textLength/2,textLength));
        cipherText=sW(cipherText);
        shiftedPTenKey=shift(shiftedPTenKey,2);
        this.sK=pEight(shiftedPTenKey);
        cipherText=fK(cipherText.substring(0,textLength/2),cipherText.substring(textLength/2,textLength));
        cipherText=iPInverse(cipherText);
        return  cipherText;

    }
    public String decipher(String rawKey,String cipherText)
    {
        String pTenkey=pTen(rawKey);
        String shiftedPTenKey=shift(pTenkey,1);
        String sKOne=pEight(shiftedPTenKey);
        shiftedPTenKey=shift(shiftedPTenKey,2);
        this.sK=pEight(shiftedPTenKey);
        String permutedText=iP(cipherText);
        int textLength=permutedText.length();
        String plainText=fK(permutedText.substring(0,textLength/2),permutedText.substring(textLength/2,textLength));
        plainText=sW(plainText);
        this.sK=sKOne;
        plainText=fK(plainText.substring(0,textLength/2),plainText.substring(textLength/2,textLength));
        plainText=iPInverse(plainText);
        return plainText;

    }

    public  String iP(String text)
    {
        String permutedText="";
        permutedText+=text.charAt(1);
        permutedText+=text.charAt(5);
        permutedText+=text.charAt(2);
        permutedText+=text.charAt(0);
        permutedText+=text.charAt(3);
        permutedText+=text.charAt(7);
        permutedText+=text.charAt(4);
        permutedText+=text.charAt(6);

        return permutedText;
    }
    public String iPInverse(String text)
    {
        String permutedText="";
        permutedText+=text.charAt(3);
        permutedText+=text.charAt(0);
        permutedText+=text.charAt(2);
        permutedText+=text.charAt(4);
        permutedText+=text.charAt(6);
        permutedText+=text.charAt(1);
        permutedText+=text.charAt(7);
        permutedText+=text.charAt(5);
        return permutedText;
    }
    public String pTen(String rawKey)
    {
        String permutedKey="";
        permutedKey+=rawKey.charAt(2);
        permutedKey+=rawKey.charAt(4);
        permutedKey+=rawKey.charAt(1);
        permutedKey+=rawKey.charAt(6);
        permutedKey+=rawKey.charAt(3);
        permutedKey+=rawKey.charAt(9);
        permutedKey+=rawKey.charAt(0);
        permutedKey+=rawKey.charAt(8);
        permutedKey+=rawKey.charAt(7);
        permutedKey+=rawKey.charAt(5);

        return permutedKey;
    }

    public String shift(String key,int nBit)
    {
        int keyLength=key.length();
        String leftKey=key.substring(0,keyLength/2);
        String rightKey=key.substring(keyLength/2,keyLength);
        leftKey=leftShift(leftKey,nBit);
        rightKey=leftShift(rightKey,nBit);

        return leftKey+rightKey;

    }
    public String leftShift(String key,int nBit)
    {
        String leftShiftedKey;
        int keyLength=key.length();
        leftShiftedKey=key.substring(nBit,keyLength);
        leftShiftedKey+=key.substring(0,nBit);
        return leftShiftedKey;
    }
    public String pEight(String key)
    {
        String pEightKey="";
        pEightKey+=key.charAt(5);
        pEightKey+=key.charAt(2);
        pEightKey+=key.charAt(6);
        pEightKey+=key.charAt(3);
        pEightKey+=key.charAt(7);
        pEightKey+=key.charAt(4);
        pEightKey+=key.charAt(9);
        pEightKey+=key.charAt(8);


        return pEightKey;
    }
    public String fK(String lText,String rText)
    {
        String fOutput=fFunction(rText,this.sK);
        String xorResult=Integer.toBinaryString(Integer.parseInt(lText,2)^Integer.parseInt(fOutput,2));
        String fResult=xorResult+rText;
        int fResultLength=fResult.length();
        if(fResultLength<8)
            fResult=fillZeroXor(fResultLength,fResult);

        return fResult;
    }
    public String fFunction(String rText,String sK)
    {
        String ePRText=exPerm(rText);
        String xorResult=Integer.toBinaryString(Integer.parseInt(ePRText,2)^Integer.parseInt(sK,2));
        int xorResultLength=xorResult.length();
        if(xorResultLength<8)
        {
            xorResult = fillZeroXor(xorResultLength, xorResult);
            xorResultLength=8;
        }

        String boxedOutput=sBox(xorResult.substring(0,xorResultLength/2),xorResult.substring(xorResultLength/2,xorResultLength));
        return pFor(boxedOutput);

    }
    public String exPerm(String input)
    {
        String ePInput="";
        ePInput+=input.charAt(3);
        ePInput+=input.charAt(0);
        ePInput+=input.charAt(1);
        ePInput+=input.charAt(2);
        ePInput+=input.charAt(1);
        ePInput+=input.charAt(2);
        ePInput+=input.charAt(3);
        ePInput+=input.charAt(0);

        return ePInput;
    }
    public  String sBox(String lInput,String rInput)
    {
        int sZeroRow=Integer.parseInt(""+lInput.charAt(0)+lInput.charAt(3),2);
        int sZeroCol=Integer.parseInt(""+lInput.charAt(1)+lInput.charAt(2),2);
        String sZeroOutput=SZERO[sZeroRow][sZeroCol];
        int sOneRow=Integer.parseInt(""+rInput.charAt(0)+rInput.charAt(3),2);
        int sOneCol=Integer.parseInt(""+rInput.charAt(1)+rInput.charAt(2),2);
        String sOneOutput=SONE[sOneRow][sOneCol];

        return sZeroOutput+sOneOutput;
    }
    public String pFor(String bits)
    {
        String pForBits="";
        pForBits+=bits.charAt(1);
        pForBits+=bits.charAt(3);
        pForBits+=bits.charAt(2);
        pForBits+=bits.charAt(0);
        return pForBits;

    }
    public String sW(String bits)
    {
        int bitLength=bits.length();

        return bits.substring(bitLength/2,bitLength)+bits.substring(0,bitLength/2);
    }
    public String fillZeroXor(int xorResultLength,String xorResult)
    {
            String zeroAdded="";
            int zeroToAdd=8-xorResultLength;
            for(int i=0;i<zeroToAdd;i++)
                zeroAdded+="0";
            xorResult=zeroAdded+xorResult;

        return  xorResult;
    }

}
