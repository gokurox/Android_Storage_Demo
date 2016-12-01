package in.ac.iiitd.gursimran14041.demoapp_mc_assign3;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Gursimran Singh on 29-09-2016.
 */
public class StorageFragment extends Fragment {
    private EditText
            mInOutData;

    private Button
            mWriteInternal,
            mReadInternal,
            mWriteExternalPrivate,
            mReadExternalPrivate,
            mWriteExternalPublic,
            mReadExternalPublic,
            mDeleteButton;

    private File
            mInternalFileDir,
            mExternalPrivateFileDir,
            mExternalPublicFileDir;

    private final String INTERNAL_FILENAME = "demoInternalFile";
    private final String EXTERNAL_PRIVATE_FILENAME = "demoExternalPrivateFile";
    private final String EXTERNAL_PUBLIC_FILENAME = "demoExternalPublicFile";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_storage, container, false);

        mInOutData = (EditText) view.findViewById (R.id.input_data);
        mWriteInternal = (Button) view.findViewById (R.id.writeInternal);
        mReadInternal = (Button) view.findViewById (R.id.readInternal);
        mWriteExternalPrivate = (Button) view.findViewById (R.id.writeExternalPrivate);
        mReadExternalPrivate = (Button) view.findViewById (R.id.readExternalPrivate);
        mWriteExternalPublic = (Button) view.findViewById (R.id.writeExternalPublic);
        mReadExternalPublic = (Button) view.findViewById (R.id.readExternalPublic);
        mDeleteButton = (Button) view.findViewById (R.id.deleteButton);

        mInternalFileDir = getContext().getFilesDir();
        mExternalPrivateFileDir = getContext().getExternalFilesDir ("Demo");
        mExternalPublicFileDir = Environment.getExternalStoragePublicDirectory ("Demo");

        mWriteInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String outData = mInOutData.getText().toString().trim();
                resetEditText ();
                FileOutputStream fOut = null;
                try {
                    fOut = getContext().openFileOutput (INTERNAL_FILENAME, Context.MODE_APPEND);
                    fOut.write (outData.getBytes());

                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "File: \"" + INTERNAL_FILENAME + "\" saved to Internal Storage (" + mInternalFileDir + ")",
                            Toast.LENGTH_LONG
                    ).show();
                }
                catch (FileNotFoundException e) {
                    mInOutData.setTextColor (Color.RED);
                    mInOutData.setText ("COULD NOT OPEN FILE / FILE NOT FOUND" + "\n");
                }
                catch (IOException e) {
                    mInOutData.setTextColor (Color.RED);
                    mInOutData.setText ("IO Exception Occurred" + "\n");
                }
                finally {
                    if (fOut != null) {
                        try {
                            fOut.close();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        mReadInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inData = "";
                resetEditText();

                int C;
                FileInputStream fIn = null;
                try {
                    fIn = getContext().openFileInput(INTERNAL_FILENAME);
                    while ((C = fIn.read()) != -1) {
                        inData += Character.toString ((char)(C));
                    }

                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "File: \"" + INTERNAL_FILENAME + "\" read from Internal Storage (" + mInternalFileDir + ")",
                            Toast.LENGTH_LONG
                    ).show();
                }
                catch (FileNotFoundException e) {
                    inData = "";
                    mInOutData.setTextColor (Color.RED);
                    mInOutData.setText ("COULD NOT OPEN FILE / FILE NOT FOUND" + "\n");
                }
                catch (IOException e) {
                    inData = "";
                    mInOutData.setTextColor (Color.RED);
                    mInOutData.setText ("IO Exception Occurred" + "\n");
                }
                finally {
                    if (fIn != null) {
                        try {
                            fIn.close();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (!inData.isEmpty()) {
                    mInOutData.setText (inData);
                }
            }
        });
        mWriteExternalPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String outData = mInOutData.getText().toString().trim();

                resetEditText();

                if (!isExternalStorageAvailable()) {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "External Storage not available on this device!",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }
                if (isExternalStorageReadOnly()) {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Can't write to External Storage on this device!",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }

                File externalFile = new File (mExternalPrivateFileDir, EXTERNAL_PRIVATE_FILENAME);
                FileOutputStream fOut = null;
                try {
                    fOut = new FileOutputStream (externalFile, true);
                    fOut.write (outData.getBytes());

                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "File: \"" + EXTERNAL_PRIVATE_FILENAME + "\" saved to External Storage (" + mExternalPrivateFileDir + ")",
                            Toast.LENGTH_LONG
                    ).show();
                }
                catch (FileNotFoundException e) {
                    mInOutData.setTextColor (Color.RED);
                    mInOutData.setText ("COULD NOT OPEN FILE / FILE NOT FOUND" + "\n");
                }
                catch (IOException e) {
                    mInOutData.setTextColor (Color.RED);
                    mInOutData.setText ("IO Exception Occurred" + "\n");
                }
                finally {
                    if (fOut != null) {
                        try {
                            fOut.close();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        mReadExternalPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inData = "";

                resetEditText();

                if (!isExternalStorageAvailable()) {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "External Storage not available on this device!",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }
                if (isExternalStorageReadOnly()) {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Can't write to External Storage on this device!",
                            Toast.LENGTH_LONG
                    ).show();
                }

                File externalFile = new File (mExternalPrivateFileDir, EXTERNAL_PRIVATE_FILENAME);
                BufferedReader fIn = null;
                try {
                    fIn = new BufferedReader (new InputStreamReader (new DataInputStream (new FileInputStream (externalFile))));
                    String strLine;
                    while ((strLine = fIn.readLine()) != null) {
                        inData = inData + strLine;
                    }

                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "File: \"" + EXTERNAL_PRIVATE_FILENAME + "\" read from External Storage (" + mExternalPrivateFileDir + ")",
                            Toast.LENGTH_LONG
                    ).show();
                }
                catch (FileNotFoundException e) {
                    inData = "";
                    mInOutData.setTextColor (Color.RED);
                    mInOutData.setText ("COULD NOT OPEN FILE / FILE NOT FOUND" + "\n");
                }
                catch (IOException e) {
                    inData = "";
                    mInOutData.setTextColor (Color.RED);
                    mInOutData.setText ("IO Exception Occurred" + "\n");
                }
                finally {
                    if (fIn != null) {
                        try {
                            fIn.close();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (!inData.isEmpty()) {
                    mInOutData.setText (inData);
                }
            }
        });
        mWriteExternalPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String outData = mInOutData.getText().toString().trim();

                resetEditText();

                if (!isExternalStorageAvailable()) {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "External Storage not available on this device!",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }
                if (isExternalStorageReadOnly()) {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Can't write to External Storage on this device!",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }

                File externalFile = new File(mExternalPublicFileDir, EXTERNAL_PUBLIC_FILENAME);
                FileOutputStream fOut = null;
                try {
                    if (!mExternalPublicFileDir.exists()) {
                        if (mExternalPublicFileDir.mkdirs()) {
//                            externalFile.createNewFile();
                            fOut = new FileOutputStream(externalFile, true);
                            fOut.write(outData.getBytes());

                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "File: \"" + EXTERNAL_PUBLIC_FILENAME + "\" saved to External Storage (" + mExternalPublicFileDir + ")",
                                    Toast.LENGTH_LONG
                            ).show();
                        } else {
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Could not create directory: " + mExternalPublicFileDir,
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    } else {
                        fOut = new FileOutputStream(externalFile, true);
                        fOut.write(outData.getBytes());

                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                "File: \"" + EXTERNAL_PUBLIC_FILENAME + "\" saved to External Storage (" + mExternalPublicFileDir + ")",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
                catch (FileNotFoundException e) {
                    mInOutData.setTextColor(Color.RED);
                    mInOutData.setText("COULD NOT OPEN FILE / FILE NOT FOUND" + "\n");
                }
                catch (IOException e) {
                    mInOutData.setTextColor(Color.RED);
                    mInOutData.setText("IO Exception Occurred" + "\n");
                }
                finally {
                    if (fOut != null) {
                        try {
                            fOut.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        mReadExternalPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inData = "";

                resetEditText();

                if (!isExternalStorageAvailable()) {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "External Storage not available on this device!",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }
                if (isExternalStorageReadOnly()) {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Can't write to External Storage on this device!",
                            Toast.LENGTH_LONG
                    ).show();
                }

                File externalFile = new File (mExternalPublicFileDir, EXTERNAL_PUBLIC_FILENAME);
                BufferedReader fIn = null;
                try {
                    fIn = new BufferedReader (new InputStreamReader (new DataInputStream (new FileInputStream (externalFile))));
                    String strLine;
                    while ((strLine = fIn.readLine()) != null) {
                        inData = inData + strLine;
                    }

                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "File: \"" + EXTERNAL_PUBLIC_FILENAME + "\" read from External Storage (" + mExternalPublicFileDir + ")",
                            Toast.LENGTH_LONG
                    ).show();
                }
                catch (FileNotFoundException e) {
                    inData = "";
                    mInOutData.setTextColor (Color.RED);
                    mInOutData.setText ("COULD NOT OPEN FILE / FILE NOT FOUND" + "\n");
                }
                catch (IOException e) {
                    inData = "";
                    mInOutData.setTextColor (Color.RED);
                    mInOutData.setText ("IO Exception Occurred" + "\n");
                }
                finally {
                    if (fIn != null) {
                        try {
                            fIn.close();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (!inData.isEmpty()) {
                    mInOutData.setText (inData);
                }
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetEditText();

                boolean
                        intFile = false,
                        extPrivateFile = false,
                        extPublicFile = false;

                File fileToDelete = null;
                fileToDelete = new File (mInternalFileDir + "/" + INTERNAL_FILENAME);
                intFile = fileToDelete.delete();

                if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {

                    fileToDelete = new File (mExternalPrivateFileDir + "/" + EXTERNAL_PRIVATE_FILENAME);
                    extPrivateFile = fileToDelete.delete();

                    fileToDelete = new File (mExternalPublicFileDir + "/" + EXTERNAL_PUBLIC_FILENAME);
                    extPublicFile = fileToDelete.delete();
                }

                ArrayList<String> DeletionSuccess = new ArrayList<>();
                ArrayList<String> DeletionFailed = new ArrayList<>();

                if (intFile)
                    DeletionSuccess.add (INTERNAL_FILENAME);
                else
                    DeletionFailed.add (INTERNAL_FILENAME);

                if (extPrivateFile)
                    DeletionSuccess.add (EXTERNAL_PRIVATE_FILENAME);
                else
                    DeletionFailed.add (EXTERNAL_PRIVATE_FILENAME);

                if (extPublicFile)
                    DeletionSuccess.add (EXTERNAL_PUBLIC_FILENAME);
                else
                    DeletionFailed.add (EXTERNAL_PUBLIC_FILENAME);

                Toast.makeText(
                        getActivity().getApplicationContext(),
                        "DeletionSuccess: " + DeletionSuccess + "\n" +
                        "DeletionFailed: " + DeletionFailed,
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        return view;
    }

    private void resetEditText() {
        mInOutData.setText (null);
        mInOutData.setTextColor (Color.BLACK);
    }

    private boolean isExternalStorageAvailable () {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    private boolean isExternalStorageReadOnly () {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }
}
