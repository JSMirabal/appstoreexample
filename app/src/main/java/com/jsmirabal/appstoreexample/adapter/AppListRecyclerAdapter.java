package com.jsmirabal.appstoreexample.adapter;

/*
 * Copyright (c) 2017. JSMirabal
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jsmirabal.appstoreexample.R;
import com.jsmirabal.appstoreexample.fragment.AppListFragment;
import com.jsmirabal.appstoreexample.utility.Util;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.jsmirabal.appstoreexample.data.AppListData.AUTHOR_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.IMAGE_PATH_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.NAME_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.PRICE_PARAM;

public class AppListRecyclerAdapter extends RecyclerView.Adapter<AppListRecyclerAdapter.ViewHolder> {

    private Bundle mData;
    private String mCategory;
    private AppListFragment mFragment;
    private int lastAnimatedPosition = -1;
    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;

    public AppListRecyclerAdapter(Bundle data, AppListFragment fragment, String category) {
        mData = data;
        mFragment = fragment;
        mCategory = category;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppListRecyclerAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_app_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = mData.getBundle(NAME_PARAM).getStringArrayList(mCategory).get(position);
        String author = mData.getBundle(AUTHOR_PARAM).getStringArrayList(mCategory).get(position);
        String price = mData.getBundle(PRICE_PARAM).getStringArrayList(mCategory).get(position);
        String iconPath = mData.getBundle(IMAGE_PATH_PARAM).getStringArrayList(mCategory).get(position);

        holder.mAppCounter.setText(Integer.toString(position + 1));
        holder.mAppName.setText(name);
        holder.mAppAuthor.setText(author);
        holder.mAppPrice.setText(Util.formatPrice(price));
        Picasso.with(mFragment.getContext()).load(iconPath).into(holder.mAppIcon, new Callback(){

            @Override
            public void onSuccess() {
            }
            @Override
            public void onError() {
                holder.mAppIcon.setImageResource(R.drawable.ic_no_icon);
            }
        });

        runEnterAnimation(holder.itemView, position);

        holder.itemView.setOnClickListener(view -> mFragment.onAppListItemClick(view,position));
    }

    @Override
    public int getItemCount() {
        return mData.getBundle(NAME_PARAM).getStringArrayList(mCategory).size();
    }

    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationX(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationX(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 40 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(600)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    })
                    .start();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mAppCounter, mAppPrice, mAppName, mAppAuthor;
        ImageView mAppIcon;
        ImageButton mMenu;
        Context mContext;
        ViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mAppCounter = (TextView) itemView.findViewById(R.id.app_list_counter);
            mAppName = (TextView) itemView.findViewById(R.id.app_list_name);
            mAppAuthor = (TextView) itemView.findViewById(R.id.app_list_author);
            mAppPrice = (TextView) itemView.findViewById(R.id.app_list_price);
            mAppIcon = (ImageView) itemView.findViewById(R.id.app_list_icon);
            mMenu = (ImageButton) itemView.findViewById(R.id.app_list_menu);

            mMenu.setOnClickListener(view ->{
                PopupMenu popup = new PopupMenu(mContext, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.app_list_item_menu, popup.getMenu()); // Create popup menu
                popup.setOnMenuItemClickListener(menuItem ->{
                    switch (menuItem.getItemId()) {
                        case R.id.app_list_item_action_install:
                            Toast.makeText(mContext, "Installing...", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.app_list_item_action_wishlist:
                            Toast.makeText(mContext, "Added to wishlist", Toast.LENGTH_SHORT).show();
                            return true;
                        default: return false;
                    }
                });
                popup.show();
            });
        }
    }
}
