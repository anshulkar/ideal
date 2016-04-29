package ideal.com.utk.ideal;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ideal.com.utk.ideal.custom_datatypes.NameValuePair;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

/**
 * Created by Utkarsh on 20-04-2016 with the help of SWAG.
 */
public class LeaveDataCard extends CardWithList{
    private Context context;
    List<ListObject> mObjects = new ArrayList<ListObject>();

    public LeaveDataCard(Context c) {
        super(c);
        context = c;
    }

    private String type, sdate , edate, nature;
    public void set_header(String t, String s , String e, String n){
        type=t;
        sdate=s;
        edate=e;
        nature=n;

    }

    @Override
    protected CardHeader initCardHeader() {
        CustomHeaderInnerCard header = new CustomHeaderInnerCard(context);
        header.setupElements(type, sdate , edate, nature);
        return header;
    }

    @Override
    protected void initCard() {

        setUseEmptyView(false);

    }

    public void setup_child(List<NameValuePair> list) {

        for(int i=0;i<list.size();i++){
            LeaveObject w= new LeaveObject(this);
            w.head = list.get(i).name;
            w.content = list.get(i).value;
            w.setObjectId(w.head); //It can be important to set ad id
            if (list.get(i).value != null && !list.get(i).value.equals("") ) {
                mObjects.add(w);
            }
        }

    }

    @Override
    protected List<ListObject> initChildren() {
        return mObjects;
    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {

        //Setup the ui elements inside the item
        TextView head = (TextView) convertView.findViewById(R.id.card_leavedata_innner_list_head);
        TextView content = (TextView) convertView.findViewById(R.id.card_leavedata_innner_list_content);

        //Retrieve the values from the object
        LeaveObject leaveObject= (LeaveObject) object;
        head.setText(leaveObject.head);
        content.setText(leaveObject.content);

        return  convertView;
    }

    @Override
    public int getChildLayoutId() {
        return R.layout.card_leavedata_inner_list;
    }


    public class LeaveObject extends DefaultListObject{
        public String head;
        public String content;

        public LeaveObject(Card parentCard){
            super(parentCard);
        }
    }

}
