package com.jsmirabal.appstoreexample.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jsmirabal.appstoreexample.R;
import com.jsmirabal.appstoreexample.activity.DetailActivity;
import com.jsmirabal.appstoreexample.adapter.SimilarAppRecyclerAdapter;
import com.jsmirabal.appstoreexample.utility.Util;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import static com.jsmirabal.appstoreexample.data.AppListData.AUTHOR_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.COPYRIGHT_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.IMAGE_PATH_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.NAME_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.RELEASE_DATE_PARAM;
import static com.jsmirabal.appstoreexample.data.AppListData.SUMMARY_PARAM;

/**
 * Created by Julio on 19/1/2017.
 */

@EFragment(R.layout.fragment_detail)
public class DetailFragment extends Fragment {

    private DetailActivity mActivity;
    private Context mContext;
    private Bundle mData;
    private int mPosition, mLastPosition;
    private String mCategory;

    @ViewById(R.id.detail_panel_view)
    ImageView mPanelView;
    @ViewById(R.id.app_list_icon)
    ImageView mIconView;
    @ViewById(R.id.app_name)
    TextView mNameView;
    @ViewById(R.id.app_author)
    TextView mAuthorView;
    @ViewById(R.id.app_release_date)
    TextView mDateView;
    @ViewById(R.id.app_summary)
    TextView mSummaryView;
    @ViewById(R.id.app_copyright)
    TextView mCopyrightView;
    @ViewById(R.id.similar_app_recycler_view)
    RecyclerView mSimilarAppRecycler;
    @ViewById(R.id.app_install)
    ImageButton mInstallButton;
    @ViewById(R.id.app_summary_more)
    ImageButton mMoreButton;
    @ViewById(R.id.app_summary_less)
    ImageButton mLessButton;
    @ViewById(R.id.similar_app_recycler_view)
    RecyclerView mRecycler;


    @AfterViews
    void init() {
        initMemberVars();
        setComponentsData();
    }

    private void initMemberVars() {
        mContext = getActivity();
        mActivity = (DetailActivity) mContext;

        if (getTag() == null) {
            mPosition = mActivity.getIntent().getExtras().getInt(AppListFragment.APP_DATA_POSITION);
            mData = mActivity.getIntent().getExtras().getBundle(AppListFragment.APP_DATA);
        } else {
            mPosition = Integer.parseInt(getTag());
            mData = getArguments();
        }
        mCategory = mActivity.getIntent().getExtras().getString(AppListFragment.APP_DATA_CATEGORY);
    }

    private void setComponentsData() {
        // set app icon
        Picasso.with(mContext)
                .load(Util.getFieldValue(mData, IMAGE_PATH_PARAM, mCategory, mPosition))
                .into(mIconView);
        mNameView.setText(Util.getFieldValue(mData, NAME_PARAM, mCategory, mPosition));
        mAuthorView.setText(Util.getFieldValue(mData, AUTHOR_PARAM, mCategory, mPosition));
        mDateView.setText(Util.formatDate(
                Util.getFieldValue(mData, RELEASE_DATE_PARAM, mCategory, mPosition)
        ));
        mSummaryView.setText(Util.getFieldValue(mData, SUMMARY_PARAM, mCategory, mPosition));
        mCopyrightView.setText(Util.getFieldValue(mData, COPYRIGHT_PARAM, mCategory, mPosition));
        mInstallButton.setOnClickListener(view -> {
            Toast.makeText(mContext, "Installing...", Toast.LENGTH_LONG).show();
        });
        mMoreButton.setOnClickListener(view -> {
            mSummaryView.setMaxLines(Integer.MAX_VALUE);
            mMoreButton.setVisibility(View.GONE);
            mLessButton.setVisibility(View.VISIBLE);
        });
        mLessButton.setOnClickListener(view -> {
            mSummaryView.setMaxLines(4);
            mLessButton.setVisibility(View.GONE);
            mMoreButton.setVisibility(View.VISIBLE);
        });

        mRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRecycler.getLayoutManager().offsetChildrenHorizontal(10);
        SimilarAppRecyclerAdapter adapter = new SimilarAppRecyclerAdapter(
                Util.getSimilarAppData(mData, mCategory, mPosition), this, mCategory);
        mRecycler.setAdapter(adapter);
    }

    public void onSimilarAppItemClick(ImageView view, Bundle data, int position) {
        Fragment fragment = DetailFragment_.builder().build();
        fragment.setArguments(data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(new Fade(Fade.IN).setDuration(1000));
            fragment.setSharedElementReturnTransition(new Fade(Fade.IN).setDuration(1000));
            fragment.setEnterTransition(new Slide());

        }
        FragmentManager manager = mActivity.getSupportFragmentManager();
        manager.beginTransaction()
                .hide(this)
                .add(R.id.activity_detail, fragment, Integer.toString(position))
                .addSharedElement(view, "app_icon")
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(Integer.toString(position))
                .commit();
    }
}
