package com.vijayyogapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vijayyogapp.R;
import com.vijayyogapp.activity.HomeActivity;
import com.vijayyogapp.fragments.VoterDetailsFragment;
import com.vijayyogapp.models.VoterDetailModel;
import com.vijayyogapp.utils.Constants;
import com.vijayyogapp.utils.Utils;

import java.util.List;

/**
 * Created by SUHAS
 */
public class VoterListAdapter extends RecyclerView.Adapter<VoterListAdapter.ViewHolder> {

    private static int position = -1;
    private List<VoterDetailModel> voterDetailModelList;
    private final Context mContext;


    public static int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public VoterListAdapter(Context context, List<VoterDetailModel> voterDetailModelList) {
        this.voterDetailModelList = voterDetailModelList;
        this.mContext = context;
    }

    public void refreshAdapter(List<VoterDetailModel> VoterDetailModelList) {
        this.voterDetailModelList = VoterDetailModelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.voter_list_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final VoterDetailModel model = voterDetailModelList.get(position);

        holder.txtVoterName.setText(model.getMFULLNAME());
        holder.txtGenderAge.setText(model.getSEX() + "/" + model.getAge());
        holder.txtListAndSrNo.setText(model.getListNo() + "/" + model.getSRNO());

        holder.llVoterListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.VOTER_MODEL, model);
                ((HomeActivity) mContext).setFragment(new VoterDetailsFragment(), bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return voterDetailModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llVoterListItem;
        public TextView txtVoterName, txtGenderAge, txtListAndSrNo;

        public ViewHolder(View itemView) {
            super(itemView);
            txtVoterName = (TextView) itemView.findViewById(R.id.txt_voter_name);
            txtGenderAge = (TextView) itemView.findViewById(R.id.txt_gender_age);
            txtListAndSrNo = (TextView) itemView.findViewById(R.id.txt_ward);
            llVoterListItem = (LinearLayout) itemView.findViewById(R.id.ll_voter_listItem);
        }
    }
}
