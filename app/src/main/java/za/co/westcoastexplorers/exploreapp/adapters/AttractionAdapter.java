package za.co.westcoastexplorers.exploreapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;

import java.util.ArrayList;

import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.models.Attraction;
import za.co.westcoastexplorers.exploreapp.models.SingleLineListItem;
import za.co.westcoastexplorers.exploreapp.utils.AssetUtils;

/**
 * Created by rikus on 2017/05/09.
 */

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.SingleLineListViewHolder> implements Filterable {

    private ArrayList<Attraction> filteredItems;
    private ArrayList<Attraction> allItems;
    private AttractionFilter filter;
    private Context context;
    public CharSequence lastFilter = "";

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter  = new AttractionFilter();
        }
        return filter;
    }

    static class SingleLineListViewHolder extends RecyclerView.ViewHolder {

        public View v;
        public AppCompatTextView tv, dv;
        public AppCompatImageView iv;
        public ProgressBar p;

        public SingleLineListViewHolder(View view) {
            super(view);
            tv = (AppCompatTextView)view.findViewById(R.id.text);
            dv = (AppCompatTextView)view.findViewById(R.id.description);
            iv = (AppCompatImageView)view.findViewById(R.id.image);
            p = (ProgressBar) view.findViewById(R.id.progress);
            v = view;
        }
    }

    public AttractionAdapter(Context context, ArrayList<Attraction> items){
        this.context = context;
        this.allItems = items;
        this.filteredItems = new ArrayList<Attraction>();
        filteredItems.addAll(items);
    }

    public CharSequence getLastFilter(){
        return lastFilter;
    }

    @Override
    public SingleLineListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_attraction, parent, false);
        return new SingleLineListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SingleLineListViewHolder holder, int position) {
        final Attraction item = filteredItems.get(position);

        if (holder.tv != null){
            if (item.name != null && !item.name.isEmpty()){
                holder.tv.setText(item.name);
                holder.tv.setVisibility(View.VISIBLE);
            } else {
                holder.tv.setVisibility(View.GONE);
            }
        }

        if (holder.dv != null){
            if (item.description != null && !item.description.isEmpty()){
                holder.dv.setText(item.description);
                holder.dv.setVisibility(View.VISIBLE);
            } else {
                holder.dv.setVisibility(View.GONE);
            }
        }

        if (holder.iv != null){
            if (item.thumbnailURL != null) {
                ViewCompat.setHasTransientState(holder.v, true);
                holder.p.setVisibility(View.VISIBLE);
                AsyncTask<Void, Void, Bitmap> asyncTask = new AsyncTask<Void, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Void... voids) {
                        byte [] image = AssetUtils.downloadImage(item.thumbnailURL);
                        Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);
                        return bm;
                    }

                    @Override
                    protected void onPostExecute(Bitmap bm) {
                        holder.iv.setImageBitmap(bm);
                        holder.p.setVisibility(View.GONE);
                        ViewCompat.setHasTransientState(holder.v, false);
                    }
                };
                asyncTask.execute();
            }
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


    private class AttractionFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            lastFilter = constraint;
            FilterResults result = new FilterResults();
            if(constraint.toString().length() > 0)
            {
                ArrayList<Attraction> filteredItems = new ArrayList<Attraction>();

                for(int i = 0, l = allItems.size(); i < l; i++)
                {
                    Attraction attraction = allItems.get(i);
                    if (attraction.name.toLowerCase().contains(constraint)){
                        filteredItems.add(attraction);
                    }
                }

                result.count = filteredItems.size();
                result.values = filteredItems;
            }
            else
            {
                synchronized(this)
                {
                    result.values = allItems;
                    result.count = allItems.size();
                }
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<Attraction> temp = ((ArrayList<Attraction>)filterResults.values);
            if ((!(filteredItems.containsAll(temp)) || filteredItems.size() != temp.size())) {
                clear();
                notifyDataSetChanged();
                for (int i = 0, l = ((ArrayList<Attraction>) filterResults.values).size(); i < l; i++)
                    add(((ArrayList<Attraction>) filterResults.values).get(i));
            }
        }
    }

    public void clear(){
        filteredItems.clear();
    }

    public void add(Attraction a) {
        filteredItems.add(a);
        notifyItemChanged(getItemCount());
    }
}
