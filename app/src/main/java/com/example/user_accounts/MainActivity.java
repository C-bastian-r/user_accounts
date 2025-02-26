package com.example.user_accounts;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.user_accounts.Models.DAOs.UserDAO;
import com.example.user_accounts.Models.Entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnRegister;
    private Button btnSearch;
    private Button btnDelete;
    private Button btnList;

    private ListView lvLista;

    private EditText etDocument;
    private EditText etNickName;
    private EditText etNames;
    private EditText etLastNames;
    private EditText etPassword;

    private void init(){
        btnRegister = findViewById(R.id.btnRegister);
        btnSearch = findViewById(R.id.btnSearch);
        btnDelete = findViewById(R.id.btnDelete);
        btnList = findViewById(R.id.btnList);

        lvLista = findViewById(R.id.lvLista);

        etDocument = findViewById(R.id.etDocument);
        etNickName = findViewById(R.id.etNickName);
        etNames = findViewById(R.id.etNames);
        etLastNames = findViewById(R.id.etLastNames);
        etPassword = findViewById(R.id.etPassword);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnRegister), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        btnRegister.setOnClickListener(this::register);
        btnDelete.setOnClickListener(this::deleteUser);
        btnList.setOnClickListener(this::loadUsers);
        btnSearch.setOnClickListener((this::getUserByDocument));

    }

    private void register(View view){
        try {
            String document = etDocument.getText().toString();
            String nickName = etNickName.getText().toString();
            String names = etNames.getText().toString();
            String lastNames = etLastNames.getText().toString();
            String password = etPassword.getText().toString();

            UserDAO userDAO = new UserDAO(this, view);
            UserEntity user = new UserEntity(document, nickName, names, lastNames, password);
            userDAO.getInsertUser(user);
            Toast.makeText(this, "usuario "+nickName+" registrado con exito!", Toast.LENGTH_LONG).show();
            clearFields();
        }catch (Exception e){
            Log.i("Error en el register del sigin", "codigo de error: "+e.getMessage());
            Toast.makeText(this, "Error en el registro del sigin: "+e, Toast.LENGTH_LONG).show();
        }
    }

    private void deleteUser(View view){
        UserDAO userDao = new UserDAO(this, view);
        String document = etDocument.getText().toString();
        userDao.getDeleteUser(document);
        clearFields();
    }

    private void loadUsers(View view){
        UserDAO userDAO = new UserDAO(this, view);
        List<UserEntity> users =  userDAO.getListAllUsers();

        List<String> userDataList = new ArrayList<>();
        for (UserEntity user : users) {
            userDataList.add(user.getUserDocument() + " - " + user.getUserNickName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userDataList);
        lvLista.setAdapter(adapter);
        clearFields();
    }

    private void getUserByDocument(View view){
        UserDAO userDAO = new UserDAO(this, view);
        String document = etDocument.getText().toString();

        UserEntity user = userDAO.getfindUserByDoc(document);
        etNickName.setText(user.getUserNickName());
        etNames.setText(user.getUserNames());
        etLastNames.setText(user.getUserLastNames());
    }

    //limpieza de campos
    private void clearFields() {
        etDocument.setText("");
        etNickName.setText("");
        etNames.setText("");
        etLastNames.setText("");
        etPassword.setText("");
    }

}