package com.vijayyogapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vijayyogapp.R;
import com.vijayyogapp.models.SurnameCountModel;

import java.util.List;

/**
 * Created by SUHAS
 */
public class ReportSurnameListAdapter extends RecyclerView.Adapter<ReportSurnameListAdapter.ViewHolder> {

    private static int position = -1;
    private List<SurnameCountModel> surnameCountModelList;
    private final Context mContext;


    public static int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ReportSurnameListAdapter(Context context, List<SurnameCountModel> surnameCountModelList) {
        this.surnameCountModelList = surnameCountModelList;
        this.mContext = context;
    }

    public void refreshAdapter(List<SurnameCountModel> SurnameCountModelList) {
        this.surnameCountModelList = SurnameCountModelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.surname_item_list, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SurnameCountModel model = surnameCountModelList.get(position);

        holder.txtSurname.setText(model.getSurname());
        holder.txtCount.setText(""+model.getCount());

    }

    @Override
    public int getItemCount() {
        return surnameCountModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtSurname;
        private TextView txtCount;

        public ViewHolder(View view) {
            super(view);
            txtSurname = (TextView) view.findViewById(R.id.txt_surname);
            txtCount = (TextView) view.findViewById(R.id.txt_count);
        }
    }
}
