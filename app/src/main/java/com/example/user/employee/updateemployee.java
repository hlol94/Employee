package com.example.user.employee;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class updateemployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateemployee);

        final Employee employee = (Employee) getIntent().getExtras().get("editEMP");

        final EditText etName = (EditText) findViewById(R.id.etEmp);
       // emName = (EditText)findViewById(R.id.etEmp);
        final EditText emDoB = (EditText)findViewById(R.id.etDate);
        final EditText emSalary = (EditText)findViewById(R.id.etSalary);
        final ImageView ivProfile = (ImageView)findViewById(R.id.ivProfile);
        final Button btnupdateEmployee = (Button)findViewById(R.id.updatebtn);
        final Spinner spGender = (Spinner) findViewById(R.id.spGender);
        final Spinner spCountry = (Spinner) findViewById(R.id.spCountry);

        etName.setText(employee.getEmpName());
        emDoB.setText(employee.getDOB());
        emSalary.setText(String.valueOf(employee.getEmpSalary()));

        ArrayAdapter<String> genderAdapter = (ArrayAdapter<String>) spGender.getAdapter();
        ArrayAdapter<String> countryAdapter = (ArrayAdapter<String>) spCountry.getAdapter();

        spGender.setSelection(genderAdapter.getPosition(employee.getEmpGender()));
        spCountry.setSelection(countryAdapter.getPosition(employee.getEmpCountry()));

        Bitmap bm = BitmapFactory.decodeByteArray(employee.getEmpImg(),0,employee.getEmpImg().length);

        ivProfile.setImageBitmap(bm);


        btnupdateEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Employee newEmployee = employee;

                newEmployee.setEmpName(etName.getText().toString());
                newEmployee.setEmpGender(spGender.getSelectedItem().toString());
                newEmployee.setEmpCountry(spCountry.getSelectedItem().toString());
                newEmployee.setDOB(emDoB.getText().toString());
                newEmployee.setEmpSalary(Float.parseFloat(emSalary.getText().toString()));
                //newEmployee.setEmpImg(ivProfile);

                //Add to database...
                EmployeeSQLiteHelper db = new EmployeeSQLiteHelper(getApplicationContext());

                db.updateEmployee(newEmployee);


                Toast.makeText(updateemployee.this,"Employee has been updated successfully",Toast.LENGTH_LONG).show();
            }
        });


    }
}
