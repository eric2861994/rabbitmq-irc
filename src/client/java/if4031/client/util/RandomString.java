package if4031.client.util;

import java.util.Random;

public class RandomString {

    private final Random random = new Random();

    public String randomString(int length) {
        char[] buf = new char[length];
        for (int idx = 0; idx < length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    private static final char[] symbols;

    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch) {
            tmp.append(ch);
        }
        for (char ch = 'a'; ch <= 'z'; ++ch) {
            tmp.append(ch);
        }
        for (char ch = 'A'; ch <= 'Z'; ++ch) {
            tmp.append(ch);
        }
        tmp.append("_");
        symbols = tmp.toString().toCharArray();
    }
}
