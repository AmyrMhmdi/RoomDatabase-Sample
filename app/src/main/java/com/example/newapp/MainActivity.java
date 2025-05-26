package com.example.newapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvUserId;
    private EditText etName, etFamily;
    private Button btnInsert, btnUpdate, btnDelete;
    private RecyclerView rvUsers;

    private UserAdapter adapter;
    private UserDao userDao;
    private List<User> userList;

    private User selectedUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // دیتابیس و DAO
        userDao = testDatabase.getInstance(getApplicationContext()).userDao();

        // ویوها
        tvUserId = findViewById(R.id.tvUserId);
        etName = findViewById(R.id.etName);
        etFamily = findViewById(R.id.etFamily);
        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        rvUsers = findViewById(R.id.rvUsers);

        // تنظیم RecyclerView
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        loadUsers();

        btnInsert.setOnClickListener(v -> insertUser());
        btnUpdate.setOnClickListener(v -> updateUser());
        btnDelete.setOnClickListener(v -> deleteUser());
    }

    private void loadUsers() {
        userList = userDao.getUsers();
        if (adapter == null) {
            adapter = new UserAdapter(userList);
            rvUsers.setAdapter(adapter);

            adapter.setOnItemClickListener(user -> {
                selectedUser = user;
                tvUserId.setText("ID: " + user.getUserId());
                etName.setText(user.getName());
                etFamily.setText(user.getFamily());
            });
        } else {
            adapter.setUserList(userList);
        }
    }

    private void insertUser() {
        String name = etName.getText().toString().trim();
        String family = etFamily.getText().toString().trim();

        if (name.isEmpty() || family.isEmpty()) {
            Toast.makeText(this, "لطفا نام و نام خانوادگی را وارد کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(name, family);
        userDao.insertUser(newUser);

        Toast.makeText(this, "کاربر اضافه شد", Toast.LENGTH_SHORT).show();
        clearFields();
        loadUsers();
    }

    private void updateUser() {
        if (selectedUser == null) {
            Toast.makeText(this, "ابتدا یک کاربر را انتخاب کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = etName.getText().toString().trim();
        String family = etFamily.getText().toString().trim();

        if (name.isEmpty() || family.isEmpty()) {
            Toast.makeText(this, "لطفا نام و نام خانوادگی را وارد کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        userDao.updateUser(selectedUser.getUserId(), name, family);

        Toast.makeText(this, "کاربر بروزرسانی شد", Toast.LENGTH_SHORT).show();
        clearFields();
        loadUsers();
    }

    private void deleteUser() {
        if (selectedUser == null) {
            Toast.makeText(this, "ابتدا یک کاربر را انتخاب کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        userDao.deleteUser(selectedUser.getUserId());

        Toast.makeText(this, "کاربر حذف شد", Toast.LENGTH_SHORT).show();
        clearFields();
        loadUsers();
    }

    private void clearFields() {
        selectedUser = null;
        tvUserId.setText("ID: ");
        etName.setText("");
        etFamily.setText("");
    }
}
