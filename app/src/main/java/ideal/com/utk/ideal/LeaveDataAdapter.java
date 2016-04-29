package ideal.com.utk.ideal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ideal.com.utk.ideal.JSON.LeaveDataJSONSchema;
import ideal.com.utk.ideal.custom_datatypes.NameValuePair;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by Utkarsh on 03-04-2016 with the help of SWAG.
 */
public class LeaveDataAdapter extends RecyclerView.Adapter<LeaveDataAdapter.MyViewHolder> {
    private List<LeaveDataJSONSchema> leaveDataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //public TextView nature, leaveStart, leaveEnd, type;
        private Context c;
        private View view;
        private LeaveDataCard card;
        private CardViewNative cardView;
        public MyViewHolder(View v , Context context) {
            super(v);
            c= context;
            view= v;
        }
        public void card_init(String t, String s , String e, String n,List<NameValuePair> list){
            card = new LeaveDataCard(c);
            cardView = (CardViewNative) view;
            card.set_header(t,s,e,n);
            card.setup_child(list);
            card.init();
        }

        public void card_attach(){
            cardView.setCard(card);
        }

    }


    public LeaveDataAdapter(List<LeaveDataJSONSchema> leaveDataList) {
        this.leaveDataList = leaveDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context c = parent.getContext();
        View itemView = LayoutInflater.from(c)
                .inflate(R.layout.card_leave_data, parent, false);

        return new MyViewHolder(itemView,c);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LeaveDataJSONSchema leaveData = leaveDataList.get(position);

        List<NameValuePair> list = new ArrayList<>();
        list.add(new NameValuePair("Grounds for Leave",leaveData.grounds));
        list.add(new NameValuePair("Address While on leave",leaveData.address));
        list.add(new NameValuePair("Recommender's Comment",leaveData.recommenderComment));
        list.add(new NameValuePair("Approver's Comment",leaveData.approverComment));
        list.add(new NameValuePair("Recommender",leaveData.recommender));

        holder.card_init(leaveData.type,leaveData.leaveStart,leaveData.leaveEnd,leaveData.nature,list);
        holder.card_attach();
    }

    @Override
    public int getItemCount() {
        return leaveDataList.size();
    }
}
