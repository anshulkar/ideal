package ideal.com.utk.ideal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.prototypes.CardWithList;

/**
 * Created by Utkarsh on 20-04-2016 with the help of SWAG.
 */
public class LeaveDataCard extends CardWithList{
    public LeaveDataCard(Context context) {
        super(context);
    }

    @Override
    protected CardHeader initCardHeader() {
        return null;
    }

    @Override
    protected void initCard() {

    }

    @Override
    protected List<ListObject> initChildren() {
        return null;
    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getChildLayoutId() {
        return 0;
    }
}
