// Theoretical number of digits max integer is [-2**63, 2**63-1] or 8.3010348e+19;

import java.util.Arrays;

public class UnsizedInteger {
    private static final String PLUS_SIGN = "+";
    private static final String NEG_SIGN = "-";

    private byte[] numberData;
    private boolean isNegative = false;


    // ----------------------- Constructors -------------------- //
    public UnsizedInteger(String n) {
        if (n.startsWith(NEG_SIGN) || n.startsWith(PLUS_SIGN)) {
            numberData = new byte[n.length() - 1];
            isNegative = n.startsWith(NEG_SIGN);
            n = n.substring(1);
        } else numberData = new byte[n.length()];
        for (int i = 0; i < n.length(); i++) {
            String currentChar = String.valueOf(n.charAt(i));
            try {
                numberData[i] = (byte) Integer.parseInt(currentChar);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public UnsizedInteger() {
        numberData = new byte[0];
        isNegative = false;
    }

    //dependency injection
    public UnsizedInteger(long n) {
        this(Long.toString(n));
    }

    public String toString() {
        StringBuilder outputString = new StringBuilder();
        if (isNegative) outputString.append(NEG_SIGN);
        for (byte b : numberData) outputString.append(b);
        return outputString.toString();
    }

    // --------------------- Mathematical Operations --------------------- //
    public UnsizedInteger add(UnsizedInteger n) {
        if (isNegative()==n.isNegative()) {
            return _add(n);
        } else {
            return _sub(n);
        }
    }

    //private
    private UnsizedInteger oldub(UnsizedInteger n) {
        NormalizeArraySizes(n);
        for (int i = n.length()-1; i >= 0; i--) {
            byte tempB = (byte) (numberData[i] - n.numberData[i]);
            if (tempB < 0) {
                isNegative = true;
                tempB *= -1;
            }
            numberData[i] = tempB;
            System.out.println(Arrays.toString(numberData));
        }
        return this;
    }



    private UnsizedInteger _add(UnsizedInteger n) {
        NormalizeArraySizes(n);
        for (int i=n.length()-1; i>=0; i--) {
            byte tempB = (byte) (numberData[i] + n.numberData[i]);
            if (tempB > 9) {
                numberData[i] = (byte) (tempB%0xA);
            } else {
                numberData[i] = (tempB);
            }
            if (i==0 && tempB >0x9) {
                byte[] outputArray = new byte[length()+1];
                System.arraycopy(numberData, 0, outputArray, 1, numberData.length);
                numberData = outputArray;
                numberData[i] += (tempB / 0xA);
            } else if (i!=0) numberData[i-1] += (tempB / 0xA);
        }
        return this;
    }

    // --------------------- Getter ---------------------- //
    public int length() {
        return numberData.length;
    }

    public boolean isNegative() {
        return isNegative;
    }

    // --------------------- Private Helper Methods ----------------------- //
    private void NormalizeArraySizes(UnsizedInteger n) {
        byte[] arrayCopy;
        if (length() > n.length()) {
            arrayCopy = new byte[length()];
            System.arraycopy(n.numberData, 0, arrayCopy, numberData.length - n.numberData.length, n.numberData.length);
            n.numberData = arrayCopy;
        } else if (length() < n.length()) {
            arrayCopy = new byte[n.length()];
            System.arraycopy(numberData, 0, arrayCopy, n.numberData.length - numberData.length, numberData.length);
            numberData = arrayCopy;
        }
    }

}