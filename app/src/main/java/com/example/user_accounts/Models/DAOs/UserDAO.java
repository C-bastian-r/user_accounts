package com.example.user_accounts.Models.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.example.user_accounts.Models.DB.ManagerDataBase;
import com.example.user_accounts.Models.Entities.UserEntity;
import com.example.user_accounts.Utils.Security.SecurityModule;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private ManagerDataBase dataBase;
    private Context context;
    private View view;
    private SecurityModule securityModule;
    private UserEntity userEntity;

    //constructor, pide contexto y view
    public UserDAO(Context context, View view) {
        this.context = context;
        this.view = view;
        this.dataBase = new ManagerDataBase(this.context);
        this.securityModule = new SecurityModule();
    }

    //region Encapsulasión de metodos

    //función de incersión dentro de la bd de usuarios
    private void insertUser(UserEntity user){
        try {
            SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();

            if (isValidUser(user.getUserDocument(), user.getUserNickName())){
                Snackbar.make(context, view, "Ya existe un usuario con el mismo documento o nombre de usuario...", 2).show();
                return;
            }
            if(sqLiteDatabase != null){
                ContentValues values = new ContentValues();
                values.put("user_document", user.getUserDocument());
                values.put("user_nick_name", user.getUserNickName());
                values.put("user_names", user.getUserNames());
                values.put("user_lastnames", user.getUserLastNames());
                values.put("user_password", securityModule.hashPassword(user.getPassword()));

                long response = sqLiteDatabase.insert("users", null, values);
                Snackbar.make(context, view, "registro de usuario exitoso!"+response, 2).show();

            }else {
                Snackbar.make(context, view, "registro de usuario fallido...", 2).show();
            }
        } catch (Exception e) {
            Log.i("error en la gestión de la db", " :"+e.getMessage().toString());
            Snackbar.make(context, view,"Error en la conexión con la base de datos", 2).show();
            throw new RuntimeException(e);
        }
    }

    //eliminación de usuario
    private void deleteUser(String document){
        try{
           SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
           if (sqLiteDatabase != null){
               int response = sqLiteDatabase.delete("users", "user_document = ?", new String[]{document});
               if (response > 0){
                   Snackbar.make(view, "Usuario eliminado con exito!!", Snackbar.LENGTH_SHORT).show();
               }else {
                   Snackbar.make(view, "usuario no encontrado", Snackbar.LENGTH_SHORT).show();
               }
           }
        }catch (Exception e){
            Log.i("Error durante la eliminación", e.getMessage());
            Snackbar.make(view, "Error al eliminar el usuario", Snackbar.LENGTH_SHORT).show();
        }
    }

    //Recuperación de todos los usuarios
    private List<UserEntity> listAllUsers() {
        List<UserEntity> userList = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = dataBase.getReadableDatabase();
            String query = "SELECT * FROM users";
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String document = cursor.getString(0);
                    String nickName = cursor.getString(1);
                    String names = cursor.getString(2);
                    String lastNames = cursor.getString(2);
                    String password = cursor.getString(2);

                    UserEntity user = new UserEntity(document, nickName, names, lastNames, password);
                    userList.add(user);
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("Error en listado", e.getMessage());
        }
        return userList;
    }

    //busqueda de usuario por documento
    private UserEntity findUserByDoc(String n_doc) {
        UserEntity user = null;
        SQLiteDatabase db = dataBase.getReadableDatabase();

        try {
            String query = "SELECT user_document, user_nick_name, user_names, user_lastnames, user_password FROM users WHERE user_document = ?";
            Cursor cursor = db.rawQuery(query, new String[]{n_doc});

            if (cursor.moveToFirst()) {
                String document = cursor.getString(0);
                String nickName = cursor.getString(1);
                String names = cursor.getString(2);
                String lastNames = cursor.getString(3);
                String password = cursor.getString(4);

                user = new UserEntity(document, nickName, names, lastNames, password);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("Error en búsqueda de usuario", e.getMessage());
        }
        return user;
    }

    //endregion

    //metodos de acceso

    public void getInsertUser(UserEntity user){
        insertUser(user);
    }
    public void getDeleteUser(String document) {
        deleteUser(document);
    }
    public List<UserEntity> getListAllUsers(){
        return listAllUsers();
    }
    public UserEntity getfindUserByDoc(String document){
        return findUserByDoc(document);
    }

    //VALIDACIONES:

    //usuario valido
    private boolean isValidUser(String document, String nickname) {
        boolean isValid = false;
        try {
            SQLiteDatabase sqLiteDatabase = dataBase.getReadableDatabase();
            String query = "SELECT COUNT(*) FROM users WHERE user_document = ? OR user_nick_name = ?";
            Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{document, nickname});

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int count = cursor.getInt(0);
                    isValid = count > 0;
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("Error en verificación", e.getMessage());
        }
        return isValid;
    }
}
