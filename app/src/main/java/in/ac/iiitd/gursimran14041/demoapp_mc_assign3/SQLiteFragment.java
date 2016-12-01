package in.ac.iiitd.gursimran14041.demoapp_mc_assign3;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Gursimran Singh on 29-09-2016.
 */
public class SQLiteFragment extends Fragment {

    EditText
            mInputRollNumber,
            mInputName,
            mInputSemester;
    Button
            mInsertButton,
            mUpdateButton,
            mDeleteButton,
            mShowButton;
    TextView
            mDataDisplay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_sqlite, container, false);

        mInputRollNumber = (EditText) view.findViewById (R.id.input_roll_no);
        mInputName = (EditText) view.findViewById (R.id.input_name);
        mInputSemester = (EditText) view.findViewById (R.id.input_semester);

        mInsertButton = (Button) view.findViewById (R.id.insertButton);
        mUpdateButton = (Button) view.findViewById (R.id.updateButton);
        mDeleteButton = (Button) view.findViewById (R.id.deleteButton);
        mShowButton = (Button) view.findViewById (R.id.showButton);

        mDataDisplay = (TextView) view.findViewById (R.id.dataDisplay);

        mInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataDisplay.setText (null);
                mDataDisplay.setTextColor (Color.BLACK);

                String roll_no = mInputRollNumber.getText().toString().trim();
                String name = mInputName.getText().toString().trim();
                String str_semester = mInputSemester.getText().toString().trim();
                Integer semester;

                if (!(roll_no.isEmpty() || name.isEmpty() || str_semester.isEmpty())) {
                    try {
                        semester = Integer.parseInt (str_semester);
                    }
                    catch (NumberFormatException ex) {
                        mDataDisplay.setTextColor (Color.RED);
                        mDataDisplay.setText (
                                mDataDisplay.getText() + "Semester must be an Integer" + "\n"
                        );
                        clearEditTexts ();
                        return;
                    }
                }
                else {
                    mDataDisplay.setTextColor (Color.RED);
                    mDataDisplay.setText (
                            mDataDisplay.getText() + "Invalid Input" + "\n"
                    );
                    clearEditTexts ();
                    return;
                }

                if (!(semester >= 1 && semester <= 8)) {
                    mDataDisplay.setTextColor (Color.RED);
                    mDataDisplay.setText (
                            mDataDisplay.getText() + "Semester Value must be between 1 and 8" + "\n"
                    );
                    clearEditTexts ();
                    return;
                }

                SQLiteHelper sqlite = SQLiteHelper.getInstance (getActivity().getApplicationContext());

                if (!(sqlite.insertRecord (roll_no, name, semester))) {
                    mDataDisplay.setTextColor (Color.RED);
                    mDataDisplay.setText (
                            mDataDisplay.getText() + "Roll Number isn't unique" + "\n"
                    );
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Not Inserted!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Inserted!",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                clearEditTexts ();
            }
        });
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataDisplay.setText (null);
                mDataDisplay.setTextColor (Color.BLACK);

                String roll_no = mInputRollNumber.getText().toString().trim();
                String name = mInputName.getText().toString().trim();
                String str_semester = mInputSemester.getText().toString().trim();
                Integer semester;

                if (!(roll_no.isEmpty() || name.isEmpty() || str_semester.isEmpty())) {
                    try {
                        semester = Integer.parseInt (str_semester);
                    }
                    catch (NumberFormatException ex) {
                        mDataDisplay.setTextColor (Color.RED);
                        mDataDisplay.setText (
                                mDataDisplay.getText() + "Semester must be an Integer" + "\n"
                        );
                        clearEditTexts ();
                        return;
                    }
                }
                else {
                    mDataDisplay.setTextColor (Color.RED);
                    mDataDisplay.setText (
                            mDataDisplay.getText() + "Invalid Input" + "\n"
                    );
                    clearEditTexts ();
                    return;
                }

                if (!(semester >= 1 && semester <= 8)) {
                    mDataDisplay.setTextColor (Color.RED);
                    mDataDisplay.setText (
                            mDataDisplay.getText() + "Semester Value must be between 1 and 8" + "\n"
                    );
                    clearEditTexts ();
                    return;
                }

                SQLiteHelper sqlite = SQLiteHelper.getInstance (getActivity().getApplicationContext());

                if (!(sqlite.updateRecord (roll_no, name, semester))) {
                    mDataDisplay.setTextColor (Color.RED);
                    mDataDisplay.setText (
                            mDataDisplay.getText() + "Roll Number isn't unique" + "\n"
                    );
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Not Updated!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Updated!",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                clearEditTexts ();
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataDisplay.setText (null);
                mDataDisplay.setTextColor (Color.BLACK);

                String roll_no = mInputRollNumber.getText().toString().trim();

                if (roll_no.isEmpty()) {
                    mDataDisplay.setTextColor (Color.RED);
                    mDataDisplay.setText (
                            mDataDisplay.getText() + "Enter Roll Number" + "\n"
                    );
                    clearEditTexts ();
                    return;
                }

                SQLiteHelper sqlite = SQLiteHelper.getInstance (getActivity().getApplicationContext());

                if (!(sqlite.deleteRecord (roll_no))) {
                    mDataDisplay.setTextColor (Color.RED);
                    mDataDisplay.setText (
                            mDataDisplay.getText() + "Roll Number Not Found" + "\n"
                    );
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Not Deleted!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Deleted!",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                clearEditTexts ();
            }
        });
        mShowButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataDisplay.setText (null);
                mDataDisplay.setTextColor (Color.BLACK);

                String roll_no = mInputRollNumber.getText().toString().trim();

                SQLiteHelper sqlite = SQLiteHelper.getInstance (getActivity().getApplicationContext());

                if (roll_no.isEmpty()) {
                    Cursor cursor = sqlite.getData();
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        String data =
                                cursor.getString (cursor.getColumnIndex (SQLiteHelper.StudentTable.COL_1)) + ". " +
                                cursor.getString (cursor.getColumnIndex (SQLiteHelper.StudentTable.COL_2)) + ",\t\t" +
                                cursor.getString (cursor.getColumnIndex (SQLiteHelper.StudentTable.COL_3)) + ",\t\t" +
                                cursor.getString (cursor.getColumnIndex (SQLiteHelper.StudentTable.COL_4)) + "\n";

                        mDataDisplay.setText(
                                mDataDisplay.getText() + data
                        );
                        cursor.moveToNext();
                    }
                }
                else {
                    Cursor cursor = sqlite.getData(roll_no);
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        String data =
                                cursor.getString (cursor.getColumnIndex (SQLiteHelper.StudentTable.COL_1)) + ". " +
                                cursor.getString (cursor.getColumnIndex (SQLiteHelper.StudentTable.COL_2)) + ",\t\t" +
                                cursor.getString (cursor.getColumnIndex (SQLiteHelper.StudentTable.COL_3)) + ",\t\t" +
                                cursor.getString (cursor.getColumnIndex (SQLiteHelper.StudentTable.COL_4)) + "\n";

                        mDataDisplay.setText(
                                mDataDisplay.getText() + data
                        );
                        cursor.moveToNext();
                    }
                }

                clearEditTexts ();
            }
        });

        return view;
    }

    private void clearEditTexts() {
        mInputRollNumber.setText (null);
        mInputName.setText (null);
        mInputSemester.setText (null);
    }
}
