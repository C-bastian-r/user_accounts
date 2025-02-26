package com.example.user_accounts.Utils.Security;

import android.util.Log;
import android.util.Patterns;

import com.google.android.material.snackbar.Snackbar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityModule {

    //función de hasheo
    //usa algoritmo SHA-256
    //crea una cadena hexadecimal a partir del password
    public String hashPassword(String password){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b: hash){
                String hexa = Integer.toString(0xff & b);
                if (hexa.length() == 1) hexString.append("0");
                hexString.append(hexa);
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    //password -> input contraseña
    //storedPassword -> contraseña recuperada de la bd
    //toma el password y lo hashea,
    //compara con el password hasheado de la base de datos
    public boolean verifyPassword(String password, String storedPassword){
        String hashPassword = hashPassword(password);
        return hashPassword.equals(storedPassword);
    }


}