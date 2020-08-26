package com.wrabbit.notesclone.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wrabbit.notesclone.R;

import java.util.ArrayList;


/**
 * Created by i-Sachin464 on 16/04/2019.
 */

public class StaggeredRecyclerviewAdapter extends RecyclerView.Adapter<StaggeredRecyclerviewAdapter.ViewHolder> {

    private static final String TAG = "StaggeredRecyclerViewAd";

    private ArrayList<String> mNames = new ArrayList<>();
    private Context mContext;

    public StaggeredRecyclerviewAdapter(Context context, ArrayList<String> names) {
        mNames = names;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staggered_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

//        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.drawable.ic_launcher_background);
//
//        Glide.with(mContext)
//                .load(mImageUrls.get(position))
//                .apply(requestOptions)
//                .into(holder.image);

        holder.name.setText(mNames.get(position));

//        holder.image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: clicked on: " + mNames.get(position));
//                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name_widget);
        }
    }
}