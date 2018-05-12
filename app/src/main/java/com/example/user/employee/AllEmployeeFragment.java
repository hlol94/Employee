package com.example.user.employee;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllEmployeeFragment extends Fragment implements AdapterView.OnItemClickListener{

     ArrayList<Employee> employees;// = db.getAllEmployees();
    ArrayAdapter<Employee> adapter;


    public AllEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_employee2, container, false);

        EmployeeSQLiteHelper db = new EmployeeSQLiteHelper(getContext());
        // to use logcat
        //Log.i("EMPDATA",db.getAllEmployees().toString());

        ListView list = (ListView) v.findViewById(R.id.list);

         employees = db.getAllEmployees();

         adapter = new ArrayAdapter<Employee>(getContext(),R.layout.emp_list,employees){
            @NonNull
            @Override

            public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

                View row = LayoutInflater.from(getContext()).inflate(R.layout.emp_list,parent,false);

                ImageView ivProfile = (ImageView) row.findViewById(R.id.ivProfile);
                TextView tvName = (TextView) row.findViewById(R.id.tvName);
                TextView tvGender = (TextView) row.findViewById(R.id.tvGender);
                TextView tvCountry = (TextView) row.findViewById(R.id.tvCountry);
                TextView tvSalary = (TextView) row.findViewById(R.id.tvSalary);
                TextView tvDOB = (TextView) row.findViewById(R.id.tvDOB);

                Employee rowEmployee = employees.get(position);

                tvName.setText(rowEmployee.getEmpName());
                tvGender.setText(rowEmployee.getEmpGender());
                tvDOB.setText(rowEmployee.getDOB());
                tvCountry.setText(rowEmployee.getEmpCountry());
                tvSalary.setText("SDG"+ String.valueOf(rowEmployee.getEmpSalary()));

                Bitmap bm = BitmapFactory.decodeByteArray(rowEmployee.getEmpImg(),0,rowEmployee.getEmpImg().length);

                ivProfile.setImageBitmap(bm);

                return row;
            }
        };

        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        final Employee e = employees.get(position);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

        CharSequence [] items = {"Edit", "Delete", "Share"};

        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which){
                    case 0:
                        //Toast.makeText(getContext(),"Item at " + position + " Edit", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getContext(),updateemployee.class);

                        i.putExtra("editEMP",e);

                        startActivity(i);
                        break;

                    case 1:
                        //Toast.makeText(getContext(),"Item at " + position + " Delete", Toast.LENGTH_LONG).show();

                        EmployeeSQLiteHelper db = new EmployeeSQLiteHelper(getContext());
                        db.deleteEmployee(e.getEmpID());
                        db.close();

                        adapter.remove(e);

                        break;

                    case 2:
                        //Toast.makeText(getContext(),"Item at " + position + " share", Toast.LENGTH_LONG).show();

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_TEXT,e.toString());

                        byte[] image = e.getEmpImg();
                        Bitmap bm =BitmapFactory.decodeByteArray(image, 0,image.length);
                        shareIntent.putExtra(Intent.EXTRA_STREAM,image);
                        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),bm,"image11","sample img");
                        Uri imgUri = Uri.parse(path);

                        shareIntent.putExtra(Intent.EXTRA_STREAM,imgUri);

                        shareIntent.setType("text/plain");

                        startActivity(Intent.createChooser(shareIntent,"share..."));


                        break;

                }


            }
        });
        dialog.show();

    }
}
