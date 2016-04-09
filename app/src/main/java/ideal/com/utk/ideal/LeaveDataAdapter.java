package ideal.com.utk.ideal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ideal.com.utk.ideal.JSON.LeaveDataJSONSchema;

/**
 * Created by Utkarsh on 03-04-2016 with the help of SWAG.
 */
public class LeaveDataAdapter extends RecyclerView.Adapter<LeaveDataAdapter.MyViewHolder> {
    private List<LeaveDataJSONSchema> leaveDataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nature, leaveStart, leaveEnd, type;

        public MyViewHolder(View view) {
            super(view);
            nature = (TextView) view.findViewById(R.id.leave_nature);
            leaveStart = (TextView) view.findViewById(R.id.leave_start_date);
            leaveEnd = (TextView) view.findViewById(R.id.leave_end_date);
            type = (TextView) view.findViewById(R.id.leave_type);

        }
    }


    public LeaveDataAdapter(List<LeaveDataJSONSchema> leaveDataList) {
        this.leaveDataList = leaveDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_leave_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LeaveDataJSONSchema leaveData = leaveDataList.get(position);
        holder.nature.setText(leaveData.nature);
        holder.leaveStart.setText(leaveData.leaveStart);
        holder.leaveEnd.setText(leaveData.leaveEnd);
        holder.type.setText(leaveData.type);
    }

    @Override
    public int getItemCount() {
        return leaveDataList.size();
    }
}
