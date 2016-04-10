package ideal.com.utk.ideal;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
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


    private TextView sdate=null,edate=null,nature=null,rcomment=null,acomment=null,address=null,grounds=null,tlc=null;

    // Empty constructor required for DialogFragment
    public LeaveDialog() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leavedialog, container);
        getDialog().setTitle("Leave Details");

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.d_toolbar);
        toolbar.setTitle("Leave Details");

        nature = (TextView) view.findViewById(R.id.l_diag_nature);
        sdate = (TextView) view.findViewById(R.id.l_diag_startdate);
        edate = (TextView) view.findViewById(R.id.l_diag_enddate);
        grounds = (TextView) view.findViewById(R.id.l_diag_grounds);
        address = (TextView) view.findViewById(R.id.l_diag_address);
        tlc = (TextView) view.findViewById(R.id.l_diag_tlc);
        rcomment = (TextView) view.findViewById(R.id.l_diag_rcomment);
        acomment = (TextView) view.findViewById(R.id.l_diag_acomment);

        nature.setText(getArguments().getString("nature"));
        sdate.setText(getArguments().getString("sdate"));
        edate.setText(getArguments().getString("edate"));
        grounds.setText(getArguments().getString("grounds"));
        rcomment.setText(getArguments().getString("rcomment"));
        acomment.setText(getArguments().getString("acomment"));
        return view;
    }


}