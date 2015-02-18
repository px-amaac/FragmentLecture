package com.example.aaron.fragmentlecture;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


public class MyFragment extends Fragment implements OnClickListener {
    public static final String TAG = "myfrag";
    private static final String FRAGNUMBER_TAG = TAG + "FRAGNUMBER";
    private static final String INIFNITE_TAG = TAG + "INIFNITE";
    private GetFragNumber gfn;
    private Button changefrag;
    private TextView text;
    private int fragnumber = -1;
    private Fragmentmanageractivity fragmanager = null;
    private CheckBox infinite = null;

    static MyFragment newInstance(int newFragNumber, boolean infinite) {
        MyFragment frag = new MyFragment();
        Bundle args = new Bundle();
        args.putBoolean(INIFNITE_TAG, infinite);
        args.putInt(FRAGNUMBER_TAG, newFragNumber);
        frag.setArguments(args);
        return frag;
    }

    public interface GetFragNumber {
        public int getData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            gfn = (GetFragNumber) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement GetFragNumber");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        View result = inflater.inflate(R.layout.my_fragment, container, false);
        if (fragnumber == -1) {
            fragnumber = gfn.getData();
        }
        changefrag = (Button) result.findViewById(R.id.button);
        changefrag.setOnClickListener(this);
        infinite = (CheckBox) result.findViewById(R.id.infinite_checkbox);
        text = (TextView) result.findViewById(R.id.text);
        text.setText(Integer.toString(fragnumber));
        // Inflate the layout for this fragment
        return result;
    }

    @Override
    public void onResume() {
        int newFragNumber = getArguments().getInt(FRAGNUMBER_TAG);
        boolean infiniteBool = getArguments().getBoolean(INIFNITE_TAG);
        infinite.setChecked(infiniteBool);
        if(newFragNumber > 1){
            fragnumber = newFragNumber;
        }
        changefrag.setText(getActivity().getString(R.string.launch_fragment) + (fragnumber + 1));
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        fragmanager = (Fragmentmanageractivity) getActivity();
        int id = v.getId();
        if (id == R.id.button) {
            if (fragmanager != null)
                fragmanager.addFragment(infinite.isChecked());
        }
    }
}
