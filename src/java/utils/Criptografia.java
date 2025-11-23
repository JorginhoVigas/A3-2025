package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {

    public static String converter(String senha) {
        if (senha == null || senha.isEmpty()) {
            return null;
        }
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(senha.getBytes(), 0, senha.length());
            BigInteger i = new BigInteger(1, m.digest());
            // Formata para 32 caracteres preenchendo com zeros à esquerda se necessário
            return String.format("%1$032x", i);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
