package com.example.exam1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ListView listView;
    ImageView BtnAdd;
    EditText txtName;
    Sang_Adapter ListContactAdapter;
    ArrayList<Contact_191203366> ListContact_191203366;
    MyDB myDB;
    Contact_191203366 itemContact;
    //    Tạo context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.menu, menu);
    }
    // Khi một Item context menu được chọn
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mnuDelete:
                AlertDialog Confirm=new AlertDialog.Builder(MainActivity.this).create();
                Confirm.setTitle("Warning");
                Confirm.setMessage("Bạn có chắc chắn muốn xóa không");
                Confirm.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        myDB.deleteContact(itemContact.getId());
                        ListContact_191203366.remove(itemContact);
                        listView.setAdapter(ListContactAdapter);
                    }
                });
                Confirm.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                Confirm.show();
                break;
            case R.id.mnuEdit:
                Intent intent=new Intent(MainActivity.this, AddContact.class);
                Bundle bundle=new Bundle();
                bundle.putInt("Id", itemContact.getId());
                bundle.putString("Name", itemContact.getTen());
                bundle.putString("Number", itemContact.getSDT());
                intent.putExtras(bundle);
                startActivityForResult(intent, 300);
                break;
        }
        return super.onContextItemSelected(item);
    }
    // Xử lý giao tiếp giữa các activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // add
        if(requestCode==100 && resultCode==200)
        {
            Bundle bundle=data.getExtras();
            String name=bundle.getString("Name");
            String number=bundle.getString("Number");
            Contact_191203366 contact=new Contact_191203366(name, number);
            myDB.addContact(contact);
            ListContact_191203366=myDB.getAllcontacts();
            ListContactAdapter=new Sang_Adapter(this, ListContact_191203366);
            listView.setAdapter(ListContactAdapter);
        }
        //edit
        if(requestCode==300 && resultCode==200)
        {
            Bundle bundle=data.getExtras();
            String name=bundle.getString("Name");
            String number=bundle.getString("Number");
            for (Contact_191203366 Contact:ListContact_191203366
            ) {
                if(Contact.getId()==itemContact.getId())
                {
                    Contact.setTen(name);
                    Contact.setSDT(number);
                    myDB.updateContact(Contact.getId(), Contact);
                    break;
                }

            }
            ListContact_191203366=myDB.getAllcontacts();
            ListContactAdapter=new Sang_Adapter(this, ListContact_191203366);
            listView.setAdapter(ListContactAdapter);
        }

    }
    // Hàm chính
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Tham chiếu
        listView=(ListView) findViewById(R.id.ListContact);
        listView.setLongClickable(true);
        BtnAdd=findViewById(R.id.BtnAdd);
        txtName=findViewById(R.id.TxtContact);
        // Tạo dữ liệu
        ListContact_191203366=new ArrayList<Contact_191203366>();
        myDB=new MyDB(this, "Sang_Sqlite", null,1);
//        myDB.addContact(new Contact_191203366("Xuan", "19120336"));
//        myDB.addContact(new Contact_191203366("Nam", "1912033"));
//        myDB.addContact(new Contact_191203366("Hai", "1912066"));
//        myDB.addContact(new Contact_191203366("Manh", "191266"));
//        myDB.addContact(new Contact_191203366("Sang", "1903366"));
//        myDB.addContact(new Contact_191203366("Thuy", "1203366"));

        ListContact_191203366=myDB.getAllcontacts();
        ListContactAdapter=new Sang_Adapter(this, ListContact_191203366);
        listView.setAdapter(ListContactAdapter);
        // Đăng ký contextMenu
        registerForContextMenu(listView);
        // Chọn phần tử được nhấn giữ
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();
                itemContact=ListContact_191203366.get(i);

                return false;
            }
        });
        //Thêm
        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, AddContact.class);
                startActivityForResult(intent, 100);

            }
        });
        //Lọc
        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ListContactAdapter.getFilter().filter(charSequence.toString());
                ListContactAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}