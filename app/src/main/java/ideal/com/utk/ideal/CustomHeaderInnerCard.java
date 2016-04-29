package ideal.com.utk.ideal;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by Utkarsh on 28-04-2016 with the help of SWAG.
 */
public class CustomHeaderInnerCard extends CardHeader {

    private String type, sdate , edate, nature;
    private TextView tnature, leaveStart, leaveEnd, ttype;
    private Button expand;
    public CustomHeaderInnerCard(Context context) {
        super(context, R.layout.card_leavedata_inner_header);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view!=null){
            tnature = (TextView) view.findViewById(R.id.leave_nature);
            leaveStart = (TextView) view.findViewById(R.id.leave_start_date);
            leaveEnd = (TextView) view.findViewById(R.id.leave_end_date);
            ttype = (TextView) view.findViewById(R.id.leave_type);
            expand = (Button)view.findViewById(R.id.card_header_button_expand);
            if(tnature!=null){
                tnature.setText(nature);
            }
            if(leaveStart !=null){
                leaveStart.setText(sdate);
            }
            if(leaveEnd!=null){
                leaveEnd.setText(edate);
            }
            if(ttype!=null){
                ttype.setText(type);
            }
        }


    }
    public void setupElements(String t, String s , String e, String n){
        type=t;
        sdate=s;
        edate=e;
        nature=n;

    }
}