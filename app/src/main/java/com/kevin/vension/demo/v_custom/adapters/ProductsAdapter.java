package com.kevin.vension.demo.v_custom.adapters;

import android.content.Context;
import android.view.View;

import com.kevin.vension.demo.R;
import com.vension.customview.bulletinview.BulletinAdapter;

import java.util.List;

/**
 * ProductsAdapter
 * Created by bakumon on 17-3-31.
 */

public class ProductsAdapter extends BulletinAdapter<Object> {


    public ProductsAdapter(Context context, List<Object> list) {
        super(context, list);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public View getView(int position) {
        View view;
        if (position % 2 == 0) { //
            view = getRootView(R.layout.item_product_first);
        } else {
            view = getRootView(R.layout.item_product_second);
        }
        return view;
    }
}
