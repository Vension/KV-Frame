package com.kevin.vension.demo.v_custom;

import android.content.Intent;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kevin.vension.demo.R;
import com.kevin.vension.demo.models.TestEntity;
import com.kevin.vension.demo.ui.fragments.TabFragment_3;
import com.kevin.vension.demo.ui.fragments.agent.KnowledgeSummaryFragment;
import com.kevin.vension.demo.ui.fragments.agent.WebFragment;
import com.kevin.vension.demo.v_custom.activitys.GestureViewActivity;
import com.kevin.vension.demo.v_custom.fragments.AccelerationProgressFragment;
import com.kevin.vension.demo.v_custom.fragments.AlertViewDialogFragment;
import com.kevin.vension.demo.v_custom.fragments.AlphaIntroanimationGuideFragment;
import com.kevin.vension.demo.v_custom.fragments.AnimSubmitButtonFragment;
import com.kevin.vension.demo.v_custom.fragments.AnimationDrawableLoadingFragment;
import com.kevin.vension.demo.v_custom.fragments.BankCardUtilUseFragment;
import com.kevin.vension.demo.v_custom.fragments.BulletinViewFragment;
import com.kevin.vension.demo.v_custom.fragments.CaptchaFragment;
import com.kevin.vension.demo.v_custom.fragments.CarrouselLayoutFragment;
import com.kevin.vension.demo.v_custom.fragments.ClockViewFragment;
import com.kevin.vension.demo.v_custom.fragments.CompareIndicatorFragment;
import com.kevin.vension.demo.v_custom.fragments.CoverFlowViewFragment;
import com.kevin.vension.demo.v_custom.fragments.DashBoardEffectFragment;
import com.kevin.vension.demo.v_custom.fragments.DatePickerFragment;
import com.kevin.vension.demo.v_custom.fragments.DilatingDotsProgressBarFragment;
import com.kevin.vension.demo.v_custom.fragments.ELeMeAddCartAnimationFragment;
import com.kevin.vension.demo.v_custom.fragments.FastScrollRecyclerViewFragment;
import com.kevin.vension.demo.v_custom.fragments.FlipShareViewFragment;
import com.kevin.vension.demo.v_custom.fragments.FllowerViewFragment;
import com.kevin.vension.demo.v_custom.fragments.FlowTagLayoutFragment;
import com.kevin.vension.demo.v_custom.fragments.FruitViewFragment;
import com.kevin.vension.demo.v_custom.fragments.GADownloadingViewFragment;
import com.kevin.vension.demo.v_custom.fragments.GalleryAnimationPhotoFragment;
import com.kevin.vension.demo.v_custom.fragments.GalleryViewPagerFragment;
import com.kevin.vension.demo.v_custom.fragments.GossipViewFragment;
import com.kevin.vension.demo.v_custom.fragments.HourglassViewFragment;
import com.kevin.vension.demo.v_custom.fragments.LcLableViewFragment;
import com.kevin.vension.demo.v_custom.fragments.LikeViewFragment;
import com.kevin.vension.demo.v_custom.fragments.LiveFragment;
import com.kevin.vension.demo.v_custom.fragments.LotteryViewFragment;
import com.kevin.vension.demo.v_custom.fragments.NativeDrawHeartAndTypeTextViewFragment;
import com.kevin.vension.demo.v_custom.fragments.ObjectAnimatorFragment;
import com.kevin.vension.demo.v_custom.fragments.PaletteImageViewFragment;
import com.kevin.vension.demo.v_custom.fragments.PaperViewFragment;
import com.kevin.vension.demo.v_custom.fragments.PassWordInputFragment;
import com.kevin.vension.demo.v_custom.fragments.PasscodeViewFragment;
import com.kevin.vension.demo.v_custom.fragments.PasswordEdittextFragment;
import com.kevin.vension.demo.v_custom.fragments.PatternLockViewFragment;
import com.kevin.vension.demo.v_custom.fragments.PayPassFragment;
import com.kevin.vension.demo.v_custom.fragments.PeriscopeLayoutFragment;
import com.kevin.vension.demo.v_custom.fragments.PileLayoutFragment;
import com.kevin.vension.demo.v_custom.fragments.ProgressImageViewFragment;
import com.kevin.vension.demo.v_custom.fragments.PullToNextFragment;
import com.kevin.vension.demo.v_custom.fragments.RadarScanViewFragment;
import com.kevin.vension.demo.v_custom.fragments.RecordAnimationViewFragment;
import com.kevin.vension.demo.v_custom.fragments.RingViewFragment;
import com.kevin.vension.demo.v_custom.fragments.RxSeekBarFragment;
import com.kevin.vension.demo.v_custom.fragments.ScratchViewFragment;
import com.kevin.vension.demo.v_custom.fragments.ScrollviewChangeBarFragment;
import com.kevin.vension.demo.v_custom.fragments.ShadowImageViewFragment;
import com.kevin.vension.demo.v_custom.fragments.ShapeLoadingViewFragment;
import com.kevin.vension.demo.v_custom.fragments.ShimmerFrameLayoutFragment;
import com.kevin.vension.demo.v_custom.fragments.ShopingViewFragment;
import com.kevin.vension.demo.v_custom.fragments.SimplifySpanFragment;
import com.kevin.vension.demo.v_custom.fragments.SnowViewFragment;
import com.kevin.vension.demo.v_custom.fragments.SuperCircleViewFragment;
import com.kevin.vension.demo.v_custom.fragments.SvgViewFragment;
import com.kevin.vension.demo.v_custom.fragments.Tag_3DFragment;
import com.kevin.vension.demo.v_custom.fragments.ViewToolTipFragment;
import com.kevin.vension.demo.v_custom.fragments.WaterFragment;
import com.kevin.vension.demo.v_custom.fragments.WeatherAnimViewFragment;
import com.kevin.vension.demo.v_custom.fragments.WeiBoPopupWindpwnFragment;
import com.kevin.vension.demo.v_custom.fragments.XiuYiXiuFragment;
import com.kevin.vension.demo.v_custom.fragments.alphaview.AlphaViewFragment;
import com.kevin.vension.demo.v_custom.fragments.indexable_layout.IndexableLayoutFragment;
import com.kevin.vension.demo.v_custom.fragments.seat.SeatTableFragment;
import com.kevin.vension.demo.v_custom.fragments.stickynav_layout.StickyNavFragment;
import com.vension.frame.utils.VToastUtil;

/**
 * @author ：Created by vension on 2017/12/22.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public class DataManager {

	public static BaseQuickAdapter.OnItemChildClickListener creatItemChildClickListener(TabFragment_3 fragment) {
		BaseQuickAdapter.OnItemChildClickListener _OnItemChildClickListener = (adapter, view, position) -> {
			TestEntity _TestEntity = (TestEntity) adapter.getItem(position);
			if (_TestEntity != null){
				String title = _TestEntity.getTitle();
				switch (view.getId()){
					case R.id.tv_custom_view:
						startIntent(fragment,title,(_TestEntity.getId() - 1));
						break;
					case R.id.layout_desc:
						Bundle _Bundle = new Bundle();
						_Bundle.putString("web_title",title);
						_Bundle.putString("web_url",_TestEntity.getDesc());
						fragment.startAgentActivity(WebFragment.class,_Bundle);
						break;
				}
			}

		};
		return _OnItemChildClickListener;
	}


	public static TestEntity[] customeTitles = {
			new TestEntity("V-知识总结","https://github.com/BeesAndroid/BeesAndroid"),
			new TestEntity("贝塞尔曲线和正余弦函数实现-波浪动画"),
			new TestEntity("添加购物车控件(点击展开效果)"),
			new TestEntity("自定义智能配色可设置圆角和阴影的paletteImageView","https://github.com/DingMouRen/PaletteImageView"),
			new TestEntity("自定义支持比例效果"),
			new TestEntity("点赞、收藏效果的View"),
			new TestEntity("仿QQ运动积分抽奖转盘"),
			new TestEntity("仿支付宝咻一咻功能"),
			new TestEntity("最新最热等标签LcLableView","https://github.com/80945540/LcLableView"),
			new TestEntity("刮奖效果ScratchView"),
			new TestEntity("安卓原生代码画爱心+打印机打字效果"),
			new TestEntity("电影海报效果"),
			new TestEntity("时钟控件"),
			new TestEntity("颜色渐变且可分段显示的圆形进度条SuperCircleView"),
			new TestEntity("心型起泡飞舞效果"),
			new TestEntity("阻尼效果的FruitView"),
			new TestEntity("仿支付宝芝麻信用值圆环统计"),
			new TestEntity("仿58Loding阻尼效果LoadingView"),
			new TestEntity("DilatingDotsProgressBar进度条"),
			new TestEntity("电影院/飞机 选座效果"),
			new TestEntity("八卦形状的圆盘菜单GossipView"),
			new TestEntity("RxSeekBar(顶部有会话框)"),
			new TestEntity("仿饿了么外卖的购物车动画"),
			new TestEntity("AnimationDrawable使用之Loading动画"),
			new TestEntity("ObjectAnimator的使用"),
			new TestEntity("带动效的AnimSubmitButton"),
			new TestEntity("FlowTagLayout流式布局"),
			new TestEntity("Scrollview滑动标题栏背景渐变效果"),
			new TestEntity("上传进度imageview"),
			new TestEntity("密码输入框、九宫格密码"),
			new TestEntity("自定义密码控件PasscodeView","https://github.com/hanks-zyh/PasscodeView"),
			new TestEntity("仿华为安装进度条AccelerationProgress"),
			new TestEntity("自定义仪表盘效果、圆形进度条"),
			new TestEntity("RecyclerView-FastScroll快速滚动"),
			new TestEntity("Viewpager实现3D画廊效果"),
			new TestEntity("SimplifySpan自定义文本显示方式"),
			new TestEntity("DatePicker时间选择器"),
			new TestEntity("旋转木马效果"),
			new TestEntity("雪花下雪效果-SnowView"),
			new TestEntity("仿QQ聊天撒花特效"),
			new TestEntity("Android仿小米 MIUI8 天气动画（晴天）"),
			new TestEntity("雷达扫描效果"),
			new TestEntity("防止密码输入错误, 密码明文显示功能"),
			new TestEntity("手势密码控件GestureView"),
			new TestEntity("手势密码锁PatternLockView","https://github.com/aritraroy/PatternLockView"),
			new TestEntity("仿下拉查看商品详情效果"),
			new TestEntity("背景色和图片渐变的引导页效果"),
			new TestEntity("城市列表、联系人列表效果"),
			new TestEntity("滑动标题栏置顶效果"),
			new TestEntity("3D-TagCould标签"),
			new TestEntity("图片验证码和文字验证码效果"),
			new TestEntity("自定义带动画的FlipShareView"),
			new TestEntity("仿直播评论滚动、吐心效果"),
			new TestEntity("仿微信底部导航栏渐变AlphaView"),
			new TestEntity("仿微博更多弹窗效果PopupWindown"),
			new TestEntity("纸张效果+标签tagview"),
			new TestEntity("一个炫酷的相册动画合集"),
			new TestEntity("自定义AlertView实现AlertDialog效果"),
			new TestEntity("万能的公告栏轮播 View，也可用于商品个性垂直轮播展示","https://github.com/Bakumon/BulletinView"),
			new TestEntity("根据银行卡号获取银行卡信息，自动格式化银行卡号、手机号、身份证号输入的工具类","https://github.com/nanchen2251/BankCardUtils"),
			new TestEntity("沙漏效果的HourglassView"),
			new TestEntity("代码实现SVG图片的绘制效果","https://github.com/jaredrummler/AnimatedSvgView"),
			new TestEntity("抛砖转场动效Depth","https://github.com/florent37/Depth"),
			new TestEntity("（有bug）使用SVGAPlayer在Android、iOS、Web中播放 After Effects/Animate CC (Flash) 动画","https://github.com/yyued/SVGAPlayer-Android"),
			new TestEntity("ShimmerFrameLayout实现内容闪烁效果","https://github.com/facebook/shimmer-android"),
			new TestEntity("ShadowImageView根据图片内容变阴影颜色","https://github.com/yingLanNull/ShadowImageView"),
			new TestEntity("控件标注提示弹窗ViewToolTip","https://github.com/florent37/ViewTooltip"),
			new TestEntity("仿支付宝支付输入密码弹窗"),
			new TestEntity("自定义录音、播放动画View效果"),
			new TestEntity("一个绚丽的Downloading动画","https://github.com/Ajian-studio/GADownloading"),
			new TestEntity("堆叠点赞效果PileLayout","https://github.com/LineChen/PileLayout"),
	};


	private static void startIntent(TabFragment_3 fragment, String title, int pos) {
		Bundle _Bundle = new Bundle();
		_Bundle.putString("custome_title",title);
		switch (pos){
			case 0://V-知识总结
				fragment.startAgentActivity(KnowledgeSummaryFragment.class,_Bundle);
				break;
			case 1://贝塞尔曲线和正余弦函数实现-波浪动画
				fragment.startAgentActivity(WaterFragment.class,_Bundle);
				break;
			case 2://添加购物车控件(点击展开效果)
				fragment.startAgentActivity(ShopingViewFragment.class,_Bundle);
				break;
			case 3://自定义智能配色可设置圆角和阴影的paletteImageView
				fragment.startAgentActivity(PaletteImageViewFragment.class,_Bundle);
				break;
			case 4://自定义支持比例效果
				fragment.startAgentActivity(CompareIndicatorFragment.class,_Bundle);
				break;
			case 5://点赞、收藏效果的View
				fragment.startAgentActivity(LikeViewFragment.class,_Bundle);
				break;
			case 6://仿QQ运动积分抽奖转盘
				fragment.startAgentActivity(LotteryViewFragment.class,_Bundle);
				break;
			case 7://仿支付宝咻一咻功能
				fragment.startAgentActivity(XiuYiXiuFragment.class,_Bundle);
				break;
			case 8://最新最热等标签LcLableView
				fragment.startAgentActivity(LcLableViewFragment.class,_Bundle);
				break;
			case 9://刮奖效果ScratchView
				fragment.startAgentActivity(ScratchViewFragment.class,_Bundle);
				break;
			case 10://安卓原生代码画爱心+打印机打字效果
				fragment.startAgentActivity(NativeDrawHeartAndTypeTextViewFragment.class,_Bundle);
				break;
			case 11://电影海报效果
				fragment.startAgentActivity(CoverFlowViewFragment.class,_Bundle);
				break;
			case 12://时钟控件
				fragment.startAgentActivity(ClockViewFragment.class,_Bundle);
				break;
			case 13://颜色渐变且可分段显示的圆形进度条SuperCircleView
				fragment.startAgentActivity(SuperCircleViewFragment.class,_Bundle);
				break;
			case 14://心型起泡飞舞效果
				fragment.startAgentActivity(PeriscopeLayoutFragment.class,_Bundle);
				break;
			case 15://阻尼效果的FruitView
				fragment.startAgentActivity(FruitViewFragment.class,_Bundle);
				break;
			case 16://仿支付宝芝麻信用值圆环统计
				fragment.startAgentActivity(RingViewFragment.class,_Bundle);
				break;
			case 17://仿58Loding阻尼效果LoadingView
				fragment.startAgentActivity(ShapeLoadingViewFragment.class,_Bundle);
				break;
			case 18://DilatingDotsProgressBar进度条
				fragment.startAgentActivity(DilatingDotsProgressBarFragment.class,_Bundle);
				break;
			case 19://电影院/飞机 选座效果
				fragment.startAgentActivity(SeatTableFragment.class,_Bundle);
				break;
			case 20://八卦形状的圆盘菜单GossipView
				fragment.startAgentActivity(GossipViewFragment.class,_Bundle);
				break;
			case 21://RxSeekBar(顶部有会话框)
				fragment.startAgentActivity(RxSeekBarFragment.class,_Bundle);
				break;
			case 22://仿饿了么外卖的购物车动画
				fragment.startAgentActivity(ELeMeAddCartAnimationFragment.class,_Bundle);
				break;
			case 23://AnimationDrawable使用之Loading动画
				fragment.startAgentActivity(AnimationDrawableLoadingFragment.class,_Bundle);
				break;
			case 24://ObjectAnimator的使用
				fragment.startAgentActivity(ObjectAnimatorFragment.class,_Bundle);
				break;
			case 25://带动效的AnimSubmitButton
				fragment.startAgentActivity(AnimSubmitButtonFragment.class,_Bundle);
				break;
			case 26://FlowTagLayout流式布局
				fragment.startAgentActivity(FlowTagLayoutFragment.class,_Bundle);
				break;
			case 27://Scrollview滑动标题栏背景渐变效果
				fragment.startAgentActivity(ScrollviewChangeBarFragment.class,_Bundle);
				break;
			case 28://上传进度imageview
				fragment.startAgentActivity(ProgressImageViewFragment.class,_Bundle);
				break;
			case 29://密码输入框、九宫格密码
				fragment.startAgentActivity(PassWordInputFragment.class,_Bundle);
				break;
			case 30://自定义密码控件PasscodeView
				fragment.startAgentActivity(PasscodeViewFragment.class,_Bundle);
				break;
			case 31://仿华为安装进度条AccelerationProgress
				fragment.startAgentActivity(AccelerationProgressFragment.class,_Bundle);
				break;
			case 32://自定义仪表盘效果、圆形进度条
				fragment.startAgentActivity(DashBoardEffectFragment.class,_Bundle);
				break;
			case 33://RecyclerView-FastScroll快速滚动
				fragment.startAgentActivity(FastScrollRecyclerViewFragment.class,_Bundle);
				break;
			case 34://Viewpager实现3D画廊效果
				fragment.startAgentActivity(GalleryViewPagerFragment.class,_Bundle);
				break;
			case 35://SimplifySpan自定义文本显示方式
				fragment.startAgentActivity(SimplifySpanFragment.class,_Bundle);
				break;
			case 36://DatePicker时间选择器
				fragment.startAgentActivity(DatePickerFragment.class,_Bundle);
				break;
			case 37://旋转木马效果
				fragment.startAgentActivity(CarrouselLayoutFragment.class,_Bundle);
				break;
			case 38://雪花下雪效果-SnowView
				fragment.startAgentActivity(SnowViewFragment.class,_Bundle);
				break;
			case 39://仿QQ聊天撒花特效
				fragment.startAgentActivity(FllowerViewFragment.class,_Bundle);
				break;
			case 40://Android仿小米 MIUI8 天气动画（晴天）
				fragment.startAgentActivity(WeatherAnimViewFragment.class,_Bundle);
				break;
			case 41://雷达扫描效果
				fragment.startAgentActivity(RadarScanViewFragment.class,_Bundle);
				break;
			case 42://防止密码输入错误, 密码明文显示功能
				fragment.startAgentActivity(PasswordEdittextFragment.class,_Bundle);
				break;
			case 43://手势密码控件GestureView
				fragment.startActivity(new Intent(fragment.getActivity(), GestureViewActivity.class));
				break;
			case 44://手势密码锁PatternLockView
				fragment.startAgentActivity(PatternLockViewFragment.class,_Bundle);
				break;
			case 45://仿下拉查看商品详情效果
				fragment.startAgentActivity(PullToNextFragment.class,_Bundle);
				break;
			case 46://背景色和图片渐变的引导页效果
				fragment.startAgentActivity(AlphaIntroanimationGuideFragment.class,_Bundle);
				break;
			case 47://城市列表、联系人列表效果
				fragment.startAgentActivity(IndexableLayoutFragment.class,_Bundle);
				break;
			case 48://滑动标题栏置顶效果
				fragment.startAgentActivity(StickyNavFragment.class,_Bundle);
				break;
			case 49://3D-TagCould标签
				fragment.startAgentActivity(Tag_3DFragment.class,_Bundle);
				break;
			case 50://图片验证码和文字验证码效果
				fragment.startAgentActivity(CaptchaFragment.class,_Bundle);
				break;
			case 51://自定义带动画的FlipShareView
				fragment.startAgentActivity(FlipShareViewFragment.class,_Bundle);
				break;
			case 52://仿直播评论滚动、吐心效果
				fragment.startAgentActivity(LiveFragment.class,_Bundle);
				break;
			case 53://仿微信底部导航栏渐变AlphaView
				fragment.startAgentActivity(AlphaViewFragment.class,_Bundle);
				break;
			case 54://仿微博更多弹窗效果PopupWindown
				fragment.startAgentActivity(WeiBoPopupWindpwnFragment.class,_Bundle);
				break;
			case 55://纸张效果+标签tagview
				fragment.startAgentActivity(PaperViewFragment.class,_Bundle);
				break;
			case 56://一个炫酷的相册动画合集
				fragment.startAgentActivity(GalleryAnimationPhotoFragment.class,_Bundle);
				break;
			case 57://自定义AlertView实现AlertDialog效果
				fragment.startAgentActivity(AlertViewDialogFragment.class,_Bundle);
				break;
			case 58://万能的公告栏轮播 View，也可用于商品个性垂直轮播展示
				fragment.startAgentActivity(BulletinViewFragment.class,_Bundle);
				break;
			case 59://根据银行卡号 获取 银行卡类型、银行名称和银行编码 自动格式化银行卡号、手机号、身份证号输入的工具类
				fragment.startAgentActivity(BankCardUtilUseFragment.class,_Bundle);
				break;
			case 60://沙漏效果的HourglassView
				fragment.startAgentActivity(HourglassViewFragment.class,_Bundle);
				break;
			case 61://代码实现SVG图片的绘制效果
				fragment.startAgentActivity(SvgViewFragment.class,_Bundle);
				break;
			case 62://抛砖转场动效Depth
				VToastUtil.showToast("未实现");
//				fragment.startAgentActivity(BankCardUtilUseFragment.class,_Bundle);
				break;
			case 63://使用SVGAPlayer在Android、iOS、Web中播放 After Effects/Animate CC (Flash) 动画
//				fragment.startAgentActivity(SVGAPlayerFragment.class,_Bundle);
				VToastUtil.showToast("未实现");
				break;
			case 64://ShimmerFrameLayout实现内容闪烁效果
				fragment.startAgentActivity(ShimmerFrameLayoutFragment.class,_Bundle);
				break;
			case 65://ShadowImageView根据图片内容变阴影颜色
				fragment.startAgentActivity(ShadowImageViewFragment.class,_Bundle);
				break;
			case 66://控件标注提示弹窗ViewToolTip
				fragment.startAgentActivity(ViewToolTipFragment.class,_Bundle);
				break;
			case 67://仿支付宝支付输入密码弹窗
				fragment.startAgentActivity(PayPassFragment.class,_Bundle);
				break;
			case 68://自定义录音、播放动画View效果
				fragment.startAgentActivity(RecordAnimationViewFragment.class,_Bundle);
				break;
			case 69://一个绚丽的Downloading动画
				fragment.startAgentActivity(GADownloadingViewFragment.class,_Bundle);
				break;
			case 70://堆叠点赞效果PileLayout
				fragment.startAgentActivity(PileLayoutFragment.class,_Bundle);
				break;

			default:break;
		}
	}

}
