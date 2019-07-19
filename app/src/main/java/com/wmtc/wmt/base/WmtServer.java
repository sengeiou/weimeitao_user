package com.wmtc.wmt.base;


import com.wmtc.wmt.appoint.bean.BaikeDateBean;
import com.wmtc.wmt.appoint.bean.CityListBean;
import com.wmtc.wmt.appoint.bean.CityOpenBean;
import com.wmtc.wmt.appoint.bean.CustomChatBean;
import com.wmtc.wmt.appoint.bean.MatchShopBean;
import com.wmtc.wmt.appoint.bean.OrderJudgeBean;
import com.wmtc.wmt.appoint.bean.PreOrderBean;
import com.wmtc.wmt.appoint.bean.ProBkBean;
import com.wmtc.wmt.appoint.bean.ProBkInfoBean;
import com.wmtc.wmt.appoint.bean.ProjectInfoBean;
import com.wmtc.wmt.appoint.bean.ShopCouponBean;
import com.wmtc.wmt.appoint.bean.ShopHbBean;
import com.wmtc.wmt.appoint.bean.ShopInfoBean;
import com.wmtc.wmt.appoint.bean.ShopProjectNameBean;
import com.wmtc.wmt.appoint.bean.TeachSelBean;
import com.wmtc.wmt.appoint.bean.TimeSelBean;
import com.wmtc.wmt.appoint.pojo.CityOpenPojo;
import com.wmtc.wmt.appoint.pojo.MatchPojo;
import com.wmtc.wmt.appoint.pojo.OrderCustomPojo;
import com.wmtc.wmt.appoint.pojo.PreOrderPojo;
import com.wmtc.wmt.appoint.pojo.ProListPojo;
import com.wmtc.wmt.appoint.bean.AliPayInfoBean;
import com.wmtc.wmt.appoint.bean.MarketBean;
import com.wmtc.wmt.appoint.bean.TitleBean;
import com.wmtc.wmt.appoint.pojo.JudgePojo;
import com.wmtc.wmt.appoint.pojo.SaveAfterSalePojo;
import com.wmtc.wmt.appoint.pojo.ShopCouponPojo;
import com.wmtc.wmt.appoint.pojo.ShopInfoPojo;
import com.wmtc.wmt.appoint.pojo.ShopTeachPojo;
import com.wmtc.wmt.forum.bean.MeForumCountBean;
import com.wmtc.wmt.forum.bean.OSSBean;
import com.wmtc.wmt.forum.bean.ShopFollowBean;
import com.wmtc.wmt.forum.pojo.AccusationPojo;
import com.wmtc.wmt.forum.pojo.ForumCountPojo;
import com.wmtc.wmt.forum.pojo.ForumMeListPojo;
import com.wmtc.wmt.forum.pojo.SendPojo;
import com.wmtc.wmt.message.bean.MessageBean;
import com.wmtc.wmt.message.pojo.MessagePojo;
import com.wmtc.wmt.forum.bean.CommentBannerBean;
import com.wmtc.wmt.personal.bean.CodeLoginBean;
import com.wmtc.wmt.forum.bean.CommentSaveBean;
import com.wmtc.wmt.appoint.bean.CanCouponBean;
import com.wmtc.wmt.appoint.bean.CouponDialogbean;
import com.wmtc.wmt.appoint.bean.DiscountsBean;
import com.wmtc.wmt.appoint.bean.EfficacyDetailsBean;
import com.wmtc.wmt.forum.bean.FollowListBean;
import com.wmtc.wmt.forum.bean.ForumCommentBean;
import com.wmtc.wmt.forum.bean.ForumDetailBean;
import com.wmtc.wmt.forum.bean.ForumListBean;
import com.wmtc.wmt.forum.bean.ForumMeBean;
import com.wmtc.wmt.forum.bean.HotSearchBean;
import com.wmtc.wmt.appoint.bean.JudgeBean;
import com.wmtc.wmt.personal.bean.LocalListBean;
import com.wmtc.wmt.personal.bean.LoginByCodeBean;
import com.wmtc.wmt.personal.bean.LoginDateBean;
import com.wmtc.wmt.appoint.bean.OneOrderBean;
import com.wmtc.wmt.appoint.bean.OrderDetailsBean;
import com.wmtc.wmt.appoint.bean.OrderListBean;
import com.wmtc.wmt.personal.bean.QLoginBean;
import com.wmtc.wmt.appoint.bean.RedbagBean;
import com.wmtc.wmt.appoint.bean.RefundBean;
import com.wmtc.wmt.forum.bean.SearchUserBean;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.personal.bean.UserInfoBean;
import com.wmtc.wmt.personal.bean.VersionBean;
import com.wmtc.wmt.personal.bean.WxPayInfoBean;
import com.wmtc.wmt.forum.bean.FansListBean;
import com.wmtc.wmt.personal.pojo.AuthLoginPojo;
import com.wmtc.wmt.appoint.pojo.BannerPojo;
import com.wmtc.wmt.appoint.pojo.BasePojo;
import com.wmtc.wmt.personal.pojo.CodeLoginPojo;
import com.wmtc.wmt.appoint.pojo.CommentSavePojo;
import com.wmtc.wmt.appoint.pojo.CanCouponPojo;
import com.wmtc.wmt.appoint.pojo.CouponPojo;
import com.wmtc.wmt.appoint.pojo.DictCodePojo;
import com.wmtc.wmt.appoint.pojo.EfficacyPojo;
import com.wmtc.wmt.forum.pojo.FollowPojo;
import com.wmtc.wmt.forum.pojo.ForumFansPojo;
import com.wmtc.wmt.forum.pojo.ForumPojo;
import com.wmtc.wmt.forum.pojo.ForumZanPojo;
import com.wmtc.wmt.appoint.pojo.JudgeListPojo;
import com.wmtc.wmt.appoint.pojo.OrderDetailPojo;
import com.wmtc.wmt.appoint.pojo.OrderListPojo;
import com.wmtc.wmt.appoint.pojo.PayPoJo;
import com.wmtc.wmt.personal.pojo.LocalPojo;
import com.wmtc.wmt.personal.pojo.LoginPojo;
import com.wmtc.wmt.personal.pojo.PhonePojo;
import com.wmtc.wmt.personal.pojo.PwdLoginPojo;
import com.wmtc.wmt.appoint.pojo.RedbagPojo;
import com.wmtc.wmt.appoint.pojo.RefundPojo;
import com.wmtc.wmt.forum.pojo.SearchUserPojo;
import com.wmtc.wmt.forum.pojo.SendCodePojo;
import com.wmtc.wmt.forum.pojo.SetPwdPojo;
import com.wmtc.wmt.forum.pojo.SetUserInfoPojo;
import com.wmtc.wmt.forum.pojo.UpdateUserInfoPojo;
import com.wmtc.wmt.forum.pojo.UserPojo;
import com.wmtc.wmt.appoint.pojo.WentiPojo;
import com.wmtc.wmt.personal.pojo.FeedBackPojo;
import com.wmtc.wmt.personal.pojo.VerLoginPojo;
import com.wmtc.wmt.personal.pojo.VersionPojo;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import top.jplayer.codelib.AutoMP;
import top.jplayer.codelib.AutoResetUrl;

public interface WmtServer {

    /*帖子相关----------------------start----------------*/
    @AutoResetUrl(key = "111", value = "222")
    @POST("api/community/findForum")
    Observable<ForumListBean> findForum(@Body ForumPojo pojo);

    @POST("api/community/saveXinde")
    Observable<BaseBean> saveXinde(@Body SendPojo pojo);

    @AutoMP
    @POST("api/community/editXinde")
    Observable<BaseBean> editXinde(@Body SendPojo pojo);

    @AutoMP
    @POST("api/community/saveVideo")
    Observable<BaseBean> saveVideo(@Body SendPojo pojo);

    @POST("api/community/saveHeji")
    Observable<BaseBean> saveHeji(@Body SendPojo pojo);

    @POST("api/community/editHeji")
    Observable<BaseBean> editHeji(@Body SendPojo pojo);

    @POST("api/community/editRiji")
    Observable<BaseBean> editRiji(@Body SendPojo pojo);

    @POST("api/community/saveRiji")
    Observable<BaseBean> saveRiji(@Body SendPojo pojo);

    @POST("api/user/oss/createAppOssToken")
    Observable<OSSBean> ossToken();

    @POST("api/yuyue/addFeedback?")
    Observable<BaseBean> addFeedback(@Body FeedBackPojo pojo);

    @POST("api/community/findCommunityHomeStatisticCount")
    Observable<ForumMeBean> findCommunityHomeStatisticCount(@Body ForumPojo pojo);

    @POST("api/community/findAttentionUserForum")
    Observable<FollowListBean> findFollow(@Body ForumPojo pojo);

    @POST("api/community/forumZan")
    Observable<BaseBean> forumZan(@Body ForumZanPojo pojo);

    @POST("api/community/detTeizi")
    Observable<BaseBean> detTeizi(@Body ForumZanPojo pojo);

    @POST("api/community/collection")
    Observable<BaseBean> forumCollection(@Body ForumZanPojo pojo);

    @POST("api/community/attention")
    Observable<BaseBean> followShopOrUser(@Body FollowPojo pojo);

    @POST("api/community/findTeiziById")
    Observable<ForumDetailBean> forumDetail(@Body ForumZanPojo pojo);

    @POST("api/community/commentaryListForForum")
    Observable<ForumCommentBean> forumComment(@Body ForumPojo pojo);

    @POST("api/community/commentarySave")
    Observable<CommentSaveBean> commentarySave(@Body CommentSavePojo pojo);

    @POST("api/community/searchNotAttentionUser")
    Observable<SearchUserBean> searchUser(@Body SearchUserPojo pojo);

    @POST("api/community/hotSearch")
    Observable<HotSearchBean> hotSearch();

    @POST("api/community/fensList")
    Observable<FansListBean> fensList(@Body ForumFansPojo pojo);

    @POST("api/community/attentionUserList")
    Observable<FansListBean> attentionUserList(@Body ForumFansPojo pojo);

    @POST("api/community/saveAccusation")
    Observable<BaseBean> saveAccusation(@Body AccusationPojo pojo);

    @POST("api/community/attentionShopList")
    Observable<ShopFollowBean> attentionShopList(@Body ForumFansPojo pojo);

    @POST("api/community/findFourmCount")
    Observable<MeForumCountBean> findFourmCount(@Body ForumCountPojo pojo);

    @POST("api/community/socialHomeForumList")
    Observable<ForumListBean> socialHomeForumList(@Body ForumMeListPojo pojo);
    /*----------------------end----------------*/


    /*消息相关----------------------start----------------*/
    @POST("api/yuyue/getMessageList")
    Observable<MessageBean> getMessageList(@Body MessagePojo pojo);

    @POST("api/yuyue/deleteMessage")
    Observable<BaseBean> deleteMessage(@Body MessagePojo pojo);
    /*----------------------end----------------*/


    /*预约相关----------------------start----------------*/
    //获取护肤百科二级页面的详细内容
    @POST("api/yuyue/getSkinBaike?")
    Observable<EfficacyDetailsBean> getSkinBaike(@Body EfficacyPojo pojo);

    //根据pid获取dict
    @POST("api/yuyue/getListByParentId?")
    Observable<DictListBean> getListByParentId(@Body WentiPojo pojo);

    //获取肤质
    @POST("api/yuyue/getDictList?")
    Observable<DictListBean> getDict(@Body DictCodePojo dictCodePojo);

    @POST("api/yuyue/getProductBaike")
    Observable<ProBkInfoBean> getProductBaike(@Body DictCodePojo dictCodePojo);

    //获取banner
    @POST("api/yuyue/getBannerList?")
    Observable<CommentBannerBean> getBanner(@Body BannerPojo pojo);

    @POST("api/yuyue/getFunctionList?")
    Observable<ProBkBean> getFunctionList();

    //淘宝客
    @POST("api/mall/getGoodList?")
    Observable<MarketBean> getGoodList(@Body BasePojo pojo);

    //获取首页即将过期的红包
    @POST("api/user/getCouponInfoIndex?")
    Observable<RedbagBean> getRedbag(@Body RedbagPojo pojo);


    //订单详情
    @POST("api/yuyue/refund?")
    Observable<RefundBean> getRefund(@Body RefundPojo pojo); //订单详情

    //订单详情
    @POST("api/yuyue/saveAfterSale?")
    Observable<BaseBean> saveAfterSale(@Body SaveAfterSalePojo pojo); //订单详情

    @POST("api/yuyue/delete?")
    Observable<BaseBean> delete(@Body RefundPojo pojo);

    //订单详情
    @POST("api/yuyue/cancelOrder?")
    Observable<BaseBean> cancelOrder(@Body RefundPojo pojo);

    //红包列表
    @POST("api/user/getCouponList?")
    Observable<DiscountsBean> getCouponList(@Body CouponPojo pojo);

    //新人获取优惠劵
    @POST("api/yuyue/getNewUseCoupon?")
    Observable<CouponDialogbean> getNewUseCoupon(@Body UserPojo pojo);

    //拉去店铺里面可有和不可用优惠劵
    @POST("api/yuyue/getCanList?")
    Observable<CanCouponBean> getCanList(@Body CanCouponPojo pojo);


    @POST("api/yuyue/getOrderJudge?")
    Observable<JudgeBean> getAllJudge(@Body JudgeListPojo judgePojo);

    @POST("api/yuyue/toOpenCity?")
    Observable<CityOpenBean> toOpenCity(@Body CityOpenPojo cityOpenPojo);

    @POST("api/yuyue/cityLick?")
    Observable<CityListBean> cityLick();

    @POST("api/yuyue/getCustomerService?")
    Observable<CustomChatBean> getCustomerService(@Body OrderCustomPojo orderPojo);

    @POST("api/yuyue/getTitle")
    Observable<TitleBean> getTitle();


    @AutoMP()
    @POST("api/yuyue/saveOrderJudge?")
    Observable<BaseBean> saveOrderJudge(@Body JudgePojo pojo);

    @AutoMP()
    @POST("api/yuyue/shopTeach")
    Observable<TeachSelBean> shopTeach(@Body ShopTeachPojo pojo);

    @POST("api/yuyue/timeSel")
    Observable<TimeSelBean> timeSel(@Body ShopTeachPojo pojo);

    @POST("api/yuyue/preOrder")
    Observable<PreOrderBean> preOrder(@Body PreOrderPojo pojo);

    @POST("api/yuyue/getOrderListByOne?")
    Observable<OneOrderBean> getOrderByOne(@Body UserPojo userPojo);

    @POST("api/yuyue/getShopCoupon")
    Observable<ShopCouponBean> getShopCoupon(@Body ShopCouponPojo pojo);

    @POST("api/yuyue/matchPros")
    Observable<ShopProjectNameBean> matchPros(@Body ProListPojo pojo);

    @POST("api/yuyue/getProductBaike")
    Observable<BaikeDateBean> getProductBaike(@Body ProListPojo pojo);

    @POST("api/yuyue/matchShopByES")
    Observable<MatchShopBean> matchShopByES(@Body MatchPojo pojo);

    @POST("api/yuyue/shopInfo")
    Observable<ShopInfoBean> shopInfo(@Body ShopInfoPojo pojo);

    @POST("api/yuyue/getOfflineCan")
    Observable<ShopHbBean> getOfflineCan(@Body ShopInfoPojo pojo);

    @POST("api/yuyue/getOrderJudge")
    Observable<OrderJudgeBean> getOrderJudge(@Body ShopInfoPojo pojo);

    @POST("api/yuyue/proInfo")
    Observable<ProjectInfoBean> proInfo(@Body ShopInfoPojo pojo);

    //订单详情
    @POST("api/yuyue/getOrderDetail?")
    Observable<OrderDetailsBean> getOrderDetails(@Body OrderDetailPojo pojo);

    //阿里支付
    @POST("api/yuyue/getPayOrderStr?")
    Observable<AliPayInfoBean> getPayOrderAli(@Body PayPoJo poJo);

    @POST("api/yuyue/getPayOrderApp?")
    Observable<AliPayInfoBean> getPayOrderAppAli(@Body PayPoJo poJo);

    //微信支付
    @POST("api/yuyue/getPayOrderStr?")
    Observable<WxPayInfoBean> getPayOrderWX(@Body PayPoJo poJo);

    @POST("api/yuyue/getPayOrderApp?")
    Observable<WxPayInfoBean> getPayOrderAppWx(@Body PayPoJo poJo);


    //获取订单
    @POST("api/yuyue/getNewOrderList?")
    Observable<OrderListBean> getAllOrderList(@Body OrderListPojo phonePojo);


    /*----------------------end----------------*/


    /*个人相关----------------------start----------------*/
    @POST("api/user/getNewVersion?")
    Observable<VersionBean> getNewVersion(@Body VersionPojo pojo);

    @POST("api/user/registByVerificationCode")
    Observable<LoginByCodeBean> registByVerificationCode(@Body LoginPojo pojo);

    @POST("api/user/suser/smsver?")
    Observable<BaseBean> smsVer(@Body VerLoginPojo verLoginPojo);

    @POST("api/user/setPassword")
    Observable<BaseBean> setPassword(@Body VerLoginPojo verLoginPojo);

    //获取用户信息
    @POST("api/user/getUserInfo?")
    Observable<UserInfoBean> getUserInfo(@Body UserPojo userPojo);

    @POST("api/user/addAddress?")
    Observable<BaseBean> addAddress(@Body LocalPojo pojo);

    @POST("api/user/updateAddress?")
    Observable<BaseBean> updateAddress(@Body LocalPojo pojo);

    @POST("api/user/getAddressList?")
    Observable<LocalListBean> getAddressList(@Body LocalPojo pojo);

    @POST("api/user/delAddress?")
    Observable<BaseBean> delAddress(@Body LocalPojo pojo);

    //上传头像
    @AutoMP()
    @Multipart
    @POST("api/user/setAvatar?")
    Observable<BaseBean> avatar(@PartMap Map<String, RequestBody> map);

    //更新用户信息无头像
    @POST("api/user/updateUserInfo?")
    Observable<DictListBean> updateUserInfo(@Body UpdateUserInfoPojo userInfoPojo);

    //设置用户信息无头像
    @POST("api/user/setInfo?")
    Observable<DictListBean> setUserInfo(@Body SetUserInfoPojo setUserInfoPojo);

    //绑定手机号
    @POST("api/user/validateAccountNew?")
    Observable<BaseBean> setPhone(@Body PhonePojo phonePojo);

    //绑定手机号
    @POST("api/user/authLoginBindTel?")
    Observable<CodeLoginBean> authLoginBindTel(@Body PhonePojo phonePojo);


    //发送验证码
    @POST("api/user/sendVerificationCode?")
    Observable<BaseBean> sendCode(@Body SendCodePojo pojo);

    //第三方登录
    @POST("api/user/authLogin?")
    Observable<QLoginBean> authLogin(@Body AuthLoginPojo pojo);

    //手机号注册，登录
    @POST("api/user/registByVerificationCode?")
    Observable<CodeLoginBean> codeLogin(@Body CodeLoginPojo pojo);

    //设置密码
    @POST("api/user/setPassword?")
    Observable<BaseBean> setPwd(@Body SetPwdPojo pojo);

    //手机号密码登录
    @POST("api/user/loginBypassword?")
    Observable<LoginDateBean> pwdLogin(@Body PwdLoginPojo pojo);


    /*----------------------end----------------*/


}
