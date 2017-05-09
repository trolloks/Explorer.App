package za.co.westcoastexplorers.exploreapp.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.models.Attraction;
import za.co.westcoastexplorers.exploreapp.models.SingleLineListItem;

/**
 * Created by rikus on 2017/05/09.
 */

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.SingleLineListViewHolder> {

    private ArrayList<Attraction> filteredItems;
    private Context context;

    static class SingleLineListViewHolder extends RecyclerView.ViewHolder {

        public View v;
        public AppCompatTextView tv;

        public SingleLineListViewHolder(View view) {
            super(view);
            tv = (AppCompatTextView)view.findViewById(R.id.text);
            v = view;
        }
    }

    public AttractionAdapter(Context context, ArrayList<Attraction> items){
        this.context = context;
        this.filteredItems = items;
    }

    @Override
    public SingleLineListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_attraction, parent, false);
        return new SingleLineListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleLineListViewHolder holder, int position) {
        final Attraction item = filteredItems.get(position);

        if (holder.tv != null){
            holder.tv.setText(item.name);
        }

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.clickListener != null)
                    item.clickListener.onClick(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }



}
