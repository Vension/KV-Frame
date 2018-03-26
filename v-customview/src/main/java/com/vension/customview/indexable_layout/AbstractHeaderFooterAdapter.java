package com.vension.customview.indexable_layout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.vension.customview.indexable_layout.database.HeaderFooterDataObservable;
import com.vension.customview.indexable_layout.database.HeaderFooterDataObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoKey on 16/10/16.
 */

abstract class AbstractHeaderFooterAdapter<T> {
    private final HeaderFooterDataObservable mDataSetObservable = new HeaderFooterDataObservable();

    ArrayList<EntityWrapper<T>> mEntityWrapperList = new ArrayList<>();
    protected OnItemClickListener<T> mListener;
    protected OnItemLongClickListener<T> mLongListener;

    private String mIndex, mIndexTitle;

    /**
     * 不想显示哪个就传null
     *
     * @param index      IndexBar的字母索引
     * @param indexTitle IndexTitle
     * @param datas      数据源
     */
    public AbstractHeaderFooterAdapter(String index, String indexTitle, List<T> datas) {
        this.mIndex = index;
        this.mIndexTitle = indexTitle;

        if (indexTitle != null) {
            EntityWrapper<T> wrapper = wrapEntity();
            wrapper.setItemType(EntityWrapper.TYPE_TITLE);
        }
        for (int i = 0; i < datas.size(); i++) {
            EntityWrapper<T> wrapper = wrapEntity();
            wrapper.setData(datas.get(i));
        }
    }

    private EntityWrapper<T> wrapEntity() {
        EntityWrapper<T> wrapper = new EntityWrapper<>();
        wrapper.setIndex(mIndex);
        wrapper.setIndexTitle(mIndexTitle);
        wrapper.setHeaderFooterType(getHeaderFooterType());
        mEntityWrapperList.add(wrapper);
        return wrapper;
    }

    public abstract int getItemViewType();

    public abstract RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent);

    public abstract void onBindContentViewHolder(RecyclerView.ViewHolder holder, T entity);

    /**
     * Notifies the attached observers that the underlying data has been changed
     * and any View reflecting the data set should refresh itself.
     */
    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    public void addData(T data) {
        int size = mEntityWrapperList.size();

        EntityWrapper<T> wrapper = wrapEntity();
        wrapper.setItemType(getItemViewType());
        wrapper.setData(data);

        if (size > 0) {
            mDataSetObservable.notifyAdd(getHeaderFooterType() == EntityWrapper.TYPE_HEADER, mEntityWrapperList.get(size - 1), wrapper);
        }
    }

    public void removeData(T data) {
        for (EntityWrapper wrapper : mEntityWrapperList) {
            if (wrapper.getData() == data) {
                mEntityWrapperList.remove(wrapper);
                mDataSetObservable.notifyRemove(getHeaderFooterType() == EntityWrapper.TYPE_HEADER, wrapper);
                return;
            }
        }
    }

    int getHeaderFooterType() {
        return EntityWrapper.TYPE_HEADER;
    }

//    public void addData(int position, T data) {
//        // TODO: 16/10/27
//    }
//
//    public void addDatas(List<T> datas) {
//        // TODO: 16/10/27
//    }
//
//    public void addDatas(int position, List<T> datas) {
//        // TODO: 16/10/27
//    }

//    public void removeAll(List<T> datas) {
//        // TODO: 16/10/27
//    }

    OnItemClickListener<T> getOnItemClickListener() {
        return mListener;
    }


    OnItemLongClickListener getOnItemLongClickListener() {
        return mLongListener;
    }

    ArrayList<EntityWrapper<T>> getDatas() {
        for (EntityWrapper<T> wrapper : mEntityWrapperList) {
            if (wrapper.getItemType() == EntityWrapper.TYPE_CONTENT) {
                wrapper.setItemType(getItemViewType());
            }
        }
        return mEntityWrapperList;
    }

    void registerDataSetObserver(HeaderFooterDataObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    void unregisterDataSetObserver(HeaderFooterDataObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    interface OnItemClickListener<T> {
        void onItemClick(View v, int currentPosition, T entity);
    }

    interface OnItemLongClickListener<T> {
        boolean onItemLongClick(View v, int currentPosition, T entity);
    }
}
