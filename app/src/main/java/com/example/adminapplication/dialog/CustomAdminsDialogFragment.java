package com.example.adminapplication.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.adminapplication.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomAdminsDialogFragment extends DialogFragment {


    private static final String TAG = "CustomDialogF";
    private CustomDialogListener mListener;
    private String username;
    private String password;
    private EditText etePassword;
    Button button;

    public CustomAdminsDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CustomDialogListener) {
            mListener = (CustomDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CustomDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View customView= inflater.inflate(R.layout.fragment_admis_dialog,null);
        etePassword= customView.findViewById(R.id.eteDIAName);
        button= customView.findViewById(R.id.btn_conf_name);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    if(validateForm()) {
                        String name = String.format(password);
                        Log.v(TAG, name);

                        mListener.nameValue(name);
                        CustomAdminsDialogFragment.this.getDialog().dismiss();
                    }
                }
            }
        });

        builder.setView(customView);
        return builder.create();
    }

    private boolean validateForm() {

        password= etePassword.getText().toString().trim();

        if(password.isEmpty())return false;
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public void vibrate() {
        Vibrator vibs = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibs.vibrate(200);
    }
}
