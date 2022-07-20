package ex00;

import java.util.HashMap;

public class Signatures {

    private HashMap<String, String> signatures;

    Signatures(byte[] data) {
        signatures = new HashMap<>();
        convertRawToMap(data);
    }

    private void convertRawToMap(byte[] data) {

        int startIndex = 0;
        boolean flag1 = false;
        boolean flag2 = false;

        String key = "";
        String value = "";
        for (int i = 0; i < data.length; ++i) {
            if ((char) data[i] == ',') {
                key = new String(data, startIndex, i - startIndex);
                startIndex = i + 2;
                flag1 = true;
            }
            if ((char)data[i] == '\n' || i == data.length - 1) {
                if (i == data.length - 1) {
                    value = new String(data, startIndex, i + 1 - startIndex);
                } else {
                    value = new String(data, startIndex, i - startIndex);
                }
                startIndex = i + 1;
                flag2 = true;
            }
            if (flag1 == true && flag2 == true) {
                signatures.put(key, value);
                flag1 = false;
                flag2 = false;
            }
        }
    }

    public HashMap<String, String> getSignatures() {
        return signatures;
    }
}
