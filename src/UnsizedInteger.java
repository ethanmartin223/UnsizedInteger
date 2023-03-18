// Theoretical number of digits max integer is [-2**63, 2**63-1] or 8.3010348e+19;

public class UnsizedInteger {
    private static final String PLUS_SIGN = "+";
    private static final String NEG_SIGN = "-";

    private byte[] numberData;
    private boolean isNegative = false;

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
        System.out.println(n);
    }

    public String toString() {
        StringBuilder outputString = new StringBuilder();
        if (isNegative) outputString.append(NEG_SIGN);
        for (byte b : numberData) outputString.append(b);
        return outputString.toString();
    }

    // --------------------- Mathematical Operations --------------------- //

    // Addition
    public UnsizedInteger add(String n) {
        int numberOfOperations = n.length();
        return new UnsizedInteger();
    }

    public UnsizedInteger add(int n) {
        return add(Integer.toString(n));
    }

    // Subtraction
    //public UnsizedInteger subtract(String n) {}

    // --------------------- Mathematical Operations --------------------- //
    public boolean isNegative() {
        return isNegative;
    }

}