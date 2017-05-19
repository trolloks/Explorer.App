package za.co.westcoastexplorers.exploreapp.utils;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ViewGroupPagerAdapter extends PagerAdapter {
        ArrayList<ViewGroup> viewGroups;

        @Override
        public int getCount() {return viewGroups.size();}

        public ViewGroupPagerAdapter(){
            //instantiate your views list
            viewGroups = new ArrayList<ViewGroup>();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==((View)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewGroup currentView;
            currentView = viewGroups.get(position);
            container.addView(currentView);
            return currentView;
        }

        public View getCurrentItem(int position){
            return viewGroups.get(position);
        }

        @Override
        public void destroyItem(final ViewGroup container, int position, final Object object) {
            container.removeView((View) object);
        }

        public void addViewGroup(ViewGroup vg){
            viewGroups.add(vg);
            notifyDataSetChanged();
        }

        public void addViewGroup(int i, ViewGroup vg){
            viewGroups.add(i, vg);
            notifyDataSetChanged();
        }

        public void removeViewGroup(ViewGroup vg){
            viewGroups.remove(vg);
            notifyDataSetChanged();
        }

        public ArrayList<ViewGroup> getViewGroups(){
            return viewGroups;
        }

    }