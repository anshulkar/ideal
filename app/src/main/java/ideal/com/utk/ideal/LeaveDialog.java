package ideal.com.utk.ideal;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Utkarsh on 09-04-2016 with the help of SWAG.
 */
public class LeaveDialog extends DialogFragment {




    // Empty constructor required for DialogFragment
    public LeaveDialog() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leavedialog, container);
        getDialog().setTitle("Leave Details");

        return view;
    }


}