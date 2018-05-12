package com.example.user.employee;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewEmployeeFragment extends Fragment {

    byte[] imgBytes;


    ImageView ivProfile;
    EditText emName, emDoB, emSalary;
    Button btnAddEmployee;
    Spinner spGender, spCountry;


    public NewEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_employee2, container, false);

        emName = (EditText)v.findViewById(R.id.etEmp);
        emDoB = (EditText)v.findViewById(R.id.etDate);
        emSalary = (EditText)v.findViewById(R.id.etSalary);
        ivProfile = (ImageView) v.findViewById(R.id.ivProfile);
        btnAddEmployee = (Button) v.findViewById(R.id.btn);
        spGender = (Spinner) v.findViewById(R.id.spGender);
        spCountry = (Spinner) v.findViewById(R.id.spCountry);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence [] item = {"Take Photo","Choose Gallery","Cancel"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

                dialog.setTitle("Add Employee Photo")
                        .setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(cameraIntent,CAMERA_REQUEST_CODE);

                                        Toast.makeText(getContext(),"Take Photo",Toast.LENGTH_LONG).show();
                                    break;

                                    case 1:
                                        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                        galleryIntent.setType("image/*");
                                        startActivityForResult(galleryIntent,GALLERY_REQUEST_CODE);

                                        Toast.makeText(getContext(),"Choose From Gallery",Toast.LENGTH_LONG).show();
                                    break;

                                    case 2:
                                        Toast.makeText(getContext(),"Cancel",Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        }).show();
            }
        });

        //Calendar cal = Calendar.getInstance(); // to get the current time.
        emDoB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                String date = year + "/" + (month+1) + "/" + dayOfMonth;
                                emDoB.setText(date);

                            }

                },1994,0,7);// default date

                datePickerDialog.show();
            }

        });

        //Button Add new Employee click...

        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employee newEmployee = new Employee();

                newEmployee.setEmpName(emName.getText().toString());
                newEmployee.setEmpGender(spGender.getSelectedItem().toString());
                newEmployee.setEmpCountry(spCountry.getSelectedItem().toString());
                newEmployee.setDOB(emDoB.getText().toString());
                newEmployee.setEmpSalary(Float.parseFloat(emSalary.getText().toString()));
                newEmployee.setEmpImg(imgBytes);

                //Add to database...
                EmployeeSQLiteHelper db = new EmployeeSQLiteHelper(getContext());

                db.addEmployee(newEmployee);

                Toast.makeText(getContext(),"Employee has been add successfully",Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    static final int CAMERA_REQUEST_CODE = 100;
    static final int GALLERY_REQUEST_CODE = 200;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if (resultCode == RESULT_OK){

             switch (requestCode){

                 case CAMERA_REQUEST_CODE:
                     onCameraResult(data);
                     break;

                 case GALLERY_REQUEST_CODE:
                     onGalleryResult(data);
                     break;
             }
         }

    }

    private void onCameraResult(Intent data){

        Bitmap bm = (Bitmap) data.getExtras().get("data");

        ivProfile.setImageBitmap(bm);

        ByteArrayOutputStream bs = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG,80,bs);

        Calendar cal = Calendar.getInstance();

        File file = new File (Environment.getExternalStorageDirectory(),"EMP_"+ System.currentTimeMillis()+".JPG");

        imgBytes = bs.toByteArray();

        try{
            FileOutputStream fos = new FileOutputStream(file);

            fos.write(imgBytes);

            fos.close();
        }catch (IOException e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void onGalleryResult(Intent data){

        Bitmap bm = null;

        if (data != null){

            try{
                bm= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),data.getData());
            }catch (IOException e){
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,90,bytes);

        imgBytes = bytes.toByteArray();
        ivProfile.setImageBitmap(bm);

    }
}

