package com.myf.dagger2example.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myf.dagger2example.R;
import com.myf.dagger2example.data.bean.MyInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MaoYouFeng on 2016/5/17.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.RepoViewHolder> {

    private ArrayList<MyInfo.RetDataBean> mMyInfo;

    public ListAdapter() {
        mMyInfo = new ArrayList<>();
    }

    public void setInfos(ArrayList<MyInfo.RetDataBean> mMyInfo) {
        this.mMyInfo = mMyInfo;
        notifyItemInserted(mMyInfo.size() - 1);
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        holder.bindTo(mMyInfo.get(position));
    }

    @Override
    public int getItemCount() {
        return mMyInfo.size();
    }

    public static class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_iv_name)
        TextView mIvRepoName;
        @BindView(R.id.item_iv_detail)
        TextView mIvRepoDetail;

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(MyInfo.RetDataBean repo) {
            mIvRepoName.setText(repo.getName_cn() );
            mIvRepoDetail.setText(repo.getArea_id());
        }
    }
}
