package com.example.li.myapplication;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by d on 2016/1/18.
 */
public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseHolder> {
    private Context mContext;
    private List<Object> mDatas=new ArrayList<>();
    private int item;
    public BaseAdapter(Context mContext,int item,List<Object> mDatas){
        this.mContext=mContext;
        this.item=item;
        this.mDatas=mDatas;
    }
    private OnItemClickLitener mOnItemClickLitener;
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public BaseAdapter.BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder holder = new BaseHolder(LayoutInflater.from(
                mContext).inflate(item, parent,
                false));

        return holder;
    }

    @Override
    public void onBindViewHolder(final BaseAdapter.BaseHolder holder, int position) {
        holder.bind(mDatas.get(position));
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public static class BaseHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding;

        public BaseHolder(View itemView) {
            super(itemView);
            //mBinding = DataBindingUtil.bind(itemView);
            mBinding =DataBindingUtil.bind(itemView);
        }

        public void bind(@NonNull Object data) {
            mBinding.setVariable(BR.data,data);
        }
    }
}
