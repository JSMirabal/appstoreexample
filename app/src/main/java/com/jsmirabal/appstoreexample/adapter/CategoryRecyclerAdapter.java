package com.jsmirabal.appstoreexample.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsmirabal.appstoreexample.R;
import com.jsmirabal.appstoreexample.data.AppListData;
import com.jsmirabal.appstoreexample.fragment.CategoryFragment;
import com.jsmirabal.appstoreexample.utility.Util;

import java.util.ArrayList;

/*
 * Copyright (c) 2017. JSMirabal
 */

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {

    private Bundle mData;
    private CategoryFragment mFragment;
    private int lastAnimatedPosition = -1;
    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;

    public CategoryRecyclerAdapter(Bundle data, CategoryFragment fragment) {
        this.mData = data;
        this.mFragment = fragment;
    }

    @Override
    public CategoryRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryRecyclerAdapter.ViewHolder holder, int position) {
        ArrayList<String> categoryList = mData.getStringArrayList(AppListData.CATEGORY_PARAM);

        if (categoryList != null) {
            String category = categoryList.get(position);
            holder.mCategoryLabel.setText(category);
            holder.mCategoryIcon.setImageResource(Util.getCategoryIconRes(category));
            holder.mCard.setCardBackgroundColor(mFragment.getResources()
                    .getColor(Util.getCategoryColor(category)));
            runEnterAnimation(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.getStringArrayList(AppListData.CATEGORY_PARAM).size();
    }

    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 40 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(400)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    })
                    .start();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCard;
        TextView mCategoryLabel;
        ImageView mCategoryIcon;

        ViewHolder(View itemView) {
            super(itemView);
            mCategoryLabel = (TextView) itemView.findViewById(R.id.category_text_view);
            mCategoryIcon = (ImageView) itemView.findViewById(R.id.category_icon);
            mCard = (CardView) itemView.findViewById(R.id.category_card);
        }
    }
}
