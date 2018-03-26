package com.kevin.vension.demo.v_custom.fragments;

import android.os.Bundle;
import android.widget.Button;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.v_custom.BaseCustomViewFragment;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ：Created by vension on 2018/1/9.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class SVGAPlayerFragment extends BaseCustomViewFragment {

	@BindView(R.id.btn_screen)
	Button btnScreen;


	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_test_svga_player;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
	}

	@Override
	public void lazyLoadData() {

	}

	@OnClick(R.id.btn_screen)
	public void onViewClicked() {
//		loadAnimation();
	}

//private void loadAnimation() {
//		btnScreen.setText("加载中...");
//		SVGAParser parser = new SVGAParser(getContext());
//		resetDownloader(parser);
//		try {
//			parser.parse(new URL("https://github.com/yyued/SVGA-Samples/blob/master/angel.svga?raw=true"), new SVGAParser.ParseCompletion() {
//				@Override
//				public void onComplete(@NotNull SVGAVideoEntity videoItem) {
//					btnScreen.setText("加载完成");
//					`SVGADrawable.kt` drawable = new `SVGADrawable.kt`(videoItem);
//					ivSvga.setImageDrawable(drawable);
//					ivSvga.startAnimation();
//				}
//				@Override
//				public void onError() {
//
//				}
//			});
//		} catch (Exception e) {
//			System.out.print(true);
//		}
//	}
	/**
	 * 设置下载器，这是一个可选的配置项。
	 * @param parser
	 */
//	private void resetDownloader(SVGAParser parser) {
//		parser.setFileDownloader(new SVGAParser.FileDownloader() {
//			@Override
//			public void resume(final URL url, final Function1<? super InputStream, Unit> complete, final Function1<? super Exception, Unit> failure) {
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						OkHttpClient client = new OkHttpClient();
//						Request request = new Request.Builder().url(url).get().build();
//						try {
//							Response response = client.newCall(request).execute();
//							complete.invoke(response.body().byteStream());
//						} catch (IOException e) {
//							e.printStackTrace();
//							failure.invoke(e);
//						}
//					}
//				}).start();
//			}
//		});
//	}

}
