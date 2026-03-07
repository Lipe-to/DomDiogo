package com.domdiogo;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean check(String plainPassword, String storedPassword) {
        if (storedPassword == null || plainPassword == null) {
            return false;
        }

        // Se o valor armazenado é um hash BCrypt (começa com $2a$ ou $2b$)
        if (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$")) {
            return BCrypt.checkpw(plainPassword, storedPassword);
        }

        // Fallback: senha legada em plain-text — comparação direta
        return plainPassword.equals(storedPassword);
    }

    public static boolean isHashed(String password) {
        return password != null && (password.startsWith("$2a$") || password.startsWith("$2b$"));
    }
}

