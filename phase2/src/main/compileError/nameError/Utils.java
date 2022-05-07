package main.compileError.nameError;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

public class Utils {
    public static String genRandomName(String baseName) throws NoSuchAlgorithmException {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(System.nanoTime());
        String name = baseName + timestamp;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(name.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
//        while (hashtext.length() < 32) {
//            hashtext = "0" + hashtext;
//        }
        return no.toString(16);
    }
}
