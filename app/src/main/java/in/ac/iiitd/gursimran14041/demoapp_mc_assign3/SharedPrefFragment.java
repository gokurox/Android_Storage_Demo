package in.ac.iiitd.gursimran14041.demoapp_mc_assign3;

import android.content.Context;
import android.content.SharedPreferences;
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
public class SharedPrefFragment extends Fragment {
    private TextView
            mDataFound,
            mDataDisplay;
    private EditText
            mInputData1,
            mInputData2;
    private Button
            mSaveDataButton,
            mForgetDataButton;

    private final String PREF_FILENAME = "demoPreferences";
    private final String TAG_DATA1 = "data1";
    private final String TAG_DATA2 = "data2";

    private SharedPreferences mSharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_sharedpref, container, false);

        mDataFound = (TextView) view.findViewById (R.id.dataFoundText);
        mDataDisplay = (TextView) view.findViewById (R.id.dataDisplay);
        mInputData1 = (EditText) view.findViewById (R.id.input_data1);
        mInputData2 = (EditText) view.findViewById (R.id.input_data2);
        mSaveDataButton = (Button) view.findViewById (R.id.saveDataButton);
        mForgetDataButton = (Button) view.findViewById (R.id.forgetDataButton);

        mSharedPreferences = getContext().getSharedPreferences (PREF_FILENAME, Context.MODE_PRIVATE);

        mDataFound.setText (null);

        renderView();

        mSaveDataButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data1 = mInputData1.getText().toString().trim();
                String data2 = mInputData2.getText().toString().trim();

                SharedPreferences.Editor editor = mSharedPreferences.edit();

                editor.putString (TAG_DATA1, data1);
                editor.putString (TAG_DATA2, data2);
                editor.commit();

                Toast.makeText(
                        getActivity().getApplicationContext(),
                        "Data Saved",
                        Toast.LENGTH_SHORT
                ).show();
                renderView();
            }
        });
        mForgetDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.clear();
                editor.commit();

                Toast.makeText(
                        getActivity().getApplicationContext(),
                        "Data Forgotten",
                        Toast.LENGTH_SHORT
                ).show();
                renderView();
            }
        });

        return view;
    }

    private void renderView () {
        if (mSharedPreferences.contains(TAG_DATA1) && mSharedPreferences.contains(TAG_DATA2)) {
            mDataFound.setText ("SAVED DATA FOUND!");
            mDataDisplay.setText (
                    "DATA1" + ":\t\t" + mSharedPreferences.getString (TAG_DATA1, "NOT FOUND") +
                    "\n" +
                    "DATA2" + ":\t\t" + mSharedPreferences.getString (TAG_DATA2, "NOT FOUND")
            );
        }
        else {
            mDataFound.setText (null);
            mDataDisplay.setText (null);
        }
    }
}
