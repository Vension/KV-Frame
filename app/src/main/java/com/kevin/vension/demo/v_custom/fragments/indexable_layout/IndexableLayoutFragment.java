package com.kevin.vension.demo.v_custom.fragments.indexable_layout;

import android.os.Bundle;
import android.view.View;
import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;
import org.jetbrains.annotations.Nullable;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2017/12/26.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class IndexableLayoutFragment extends BaseCustomViewFragment {
	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_index_layout;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
	}

	@Override
	public void lazyLoadData() {

	}

	@OnClick({R.id.btn_pick_city, R.id.btn_pick_contact})
	public void onClick(View v){
		Bundle _Bundle = new Bundle();
		switch (v.getId()){
			case R.id.btn_pick_city:
				_Bundle.putString("custome_title","选择城市");
				startAgentActivity(PickCityFragment.class,_Bundle);
				break;
			case R.id.btn_pick_contact:
				_Bundle.putString("custome_title","选择联系人");
				startAgentActivity(PickContactFragment.class,_Bundle);
				break;
		}
	}


}
