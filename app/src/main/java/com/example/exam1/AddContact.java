package com.example.exam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {


    Button AddButton, CancelButton;
    EditText txtName, txtId, txtNumber;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        AddButton=findViewById(R.id.BtnAdd);
        CancelButton=findViewById(R.id.BtnCancel);
        txtId=findViewById(R.id.AddId);
        txtName=findViewById(R.id.AddName);
        txtNumber=findViewById(R.id.AddNumber);

        Intent intent=getIntent();
//        Edit
        Bundle bundle=intent.getExtras();
        if(bundle !=null)
        {
            String Name=bundle.getString("Name");
            String Number=bundle.getString("Number");
            int Id=bundle.getInt("Id");


            if(Name!=null && Number !=null)
            {
                AddButton.setText("Edit");
                txtName.setText(Name);
                txtNumber.setText(Number);
                txtId.setText(String.valueOf(Id));
            }
        }
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, number;
                name=txtName.getText().toString().trim();
                number=txtNumber.getText().toString().trim();
                if(name.length()==0 || number.length()==0)
                {
                    Toast.makeText(AddContact.this, "Bạn nhập thiếu dữ liệu", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putString("Name", name);
                    bundle.putString("Number", number);
                    intent.putExtras(bundle);
                    setResult(200, intent);
                    finish();
                }
            }
        });

    }
}