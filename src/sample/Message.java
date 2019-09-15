package sample;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Message implements Serializable {
    private String name;
    private String msg;

    private static final String ALGO = "AES";
    private static final byte[] keyValue =
            new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
                    'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };


    public String encrypt(String message) throws Exception{
        //Homemade encrypt putting "salt" in the middle of the reversed word
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(message);
        stringBuilder.reverse();
        stringBuilder.insert(stringBuilder.length()/2, "salt");
        String encryptedMessage = stringBuilder.toString();
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(encryptedMessage.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }

    public String decrypt(String encryptedMessage) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedMessage);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);

        //Homemade decrypt removing "salt" in the middle of the reversed word
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(decryptedValue);
        stringBuilder.replace(stringBuilder.length()/2-2,stringBuilder.length()/2+2,"");
        stringBuilder.reverse();
        return stringBuilder.toString();
    }

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }



    public void setMsg(String msg) {
        this.msg = msg;
    }
}
