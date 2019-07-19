package com.wmtc.wmt.base;

import com.wmtc.wmt.appoint.bean.AliPayInfoBean;
import com.wmtc.wmt.appoint.bean.BaikeDateBean;
import com.wmtc.wmt.appoint.bean.CanCouponBean;
import com.wmtc.wmt.appoint.bean.CityListBean;
import com.wmtc.wmt.appoint.bean.CityOpenBean;
import com.wmtc.wmt.appoint.bean.CouponDialogbean;
import com.wmtc.wmt.appoint.bean.CustomChatBean;
import com.wmtc.wmt.appoint.bean.DictListBean;
import com.wmtc.wmt.appoint.bean.DiscountsBean;
import com.wmtc.wmt.appoint.bean.EfficacyDetailsBean;
import com.wmtc.wmt.appoint.bean.JudgeBean;
import com.wmtc.wmt.appoint.bean.MarketBean;
import com.wmtc.wmt.appoint.bean.MatchShopBean;
import com.wmtc.wmt.appoint.bean.OneOrderBean;
import com.wmtc.wmt.appoint.bean.OrderDetailsBean;
import com.wmtc.wmt.appoint.bean.OrderJudgeBean;
import com.wmtc.wmt.appoint.bean.OrderListBean;
import com.wmtc.wmt.appoint.bean.PreOrderBean;
import com.wmtc.wmt.appoint.bean.ProBkBean;
import com.wmtc.wmt.appoint.bean.ProBkInfoBean;
import com.wmtc.wmt.appoint.bean.ProjectInfoBean;
import com.wmtc.wmt.appoint.bean.RedbagBean;
import com.wmtc.wmt.appoint.bean.RefundBean;
import com.wmtc.wmt.appoint.bean.ShopCouponBean;
import com.wmtc.wmt.appoint.bean.ShopHbBean;
import com.wmtc.wmt.appoint.bean.ShopInfoBean;
import com.wmtc.wmt.appoint.bean.ShopProjectNameBean;
import com.wmtc.wmt.appoint.bean.TeachSelBean;
import com.wmtc.wmt.appoint.bean.TimeSelBean;
import com.wmtc.wmt.appoint.bean.TitleBean;
import com.wmtc.wmt.appoint.pojo.BannerPojo;
import com.wmtc.wmt.appoint.pojo.BasePojo;
import com.wmtc.wmt.appoint.pojo.CanCouponPojo;
import com.wmtc.wmt.appoint.pojo.CityOpenPojo;
import com.wmtc.wmt.appoint.pojo.CommentSavePojo;
import com.wmtc.wmt.appoint.pojo.CouponPojo;
import com.wmtc.wmt.appoint.pojo.DictCodePojo;
import com.wmtc.wmt.appoint.pojo.EfficacyPojo;
import com.wmtc.wmt.appoint.pojo.JudgeListPojo;
import com.wmtc.wmt.appoint.pojo.JudgePojo;
import com.wmtc.wmt.appoint.pojo.MatchPojo;
import com.wmtc.wmt.appoint.pojo.OrderCustomPojo;
import com.wmtc.wmt.appoint.pojo.OrderDetailPojo;
import com.wmtc.wmt.appoint.pojo.OrderListPojo;
import com.wmtc.wmt.appoint.pojo.PayPoJo;
import com.wmtc.wmt.appoint.pojo.PreOrderPojo;
import com.wmtc.wmt.appoint.pojo.ProListPojo;
import com.wmtc.wmt.appoint.pojo.RedbagPojo;
import com.wmtc.wmt.appoint.pojo.RefundPojo;
import com.wmtc.wmt.appoint.pojo.SaveAfterSalePojo;
import com.wmtc.wmt.appoint.pojo.ShopCouponPojo;
import com.wmtc.wmt.appoint.pojo.ShopInfoPojo;
import com.wmtc.wmt.appoint.pojo.ShopTeachPojo;
import com.wmtc.wmt.appoint.pojo.WentiPojo;
import com.wmtc.wmt.forum.bean.CommentBannerBean;
import com.wmtc.wmt.forum.bean.CommentSaveBean;
import com.wmtc.wmt.forum.bean.FansListBean;
import com.wmtc.wmt.forum.bean.FollowListBean;
import com.wmtc.wmt.forum.bean.ForumCommentBean;
import com.wmtc.wmt.forum.bean.ForumDetailBean;
import com.wmtc.wmt.forum.bean.ForumListBean;
import com.wmtc.wmt.forum.bean.ForumMeBean;
import com.wmtc.wmt.forum.bean.HotSearchBean;
import com.wmtc.wmt.forum.bean.MeForumCountBean;
import com.wmtc.wmt.forum.bean.OSSBean;
import com.wmtc.wmt.forum.bean.SearchUserBean;
import com.wmtc.wmt.forum.bean.ShopFollowBean;
import com.wmtc.wmt.forum.pojo.AccusationPojo;
import com.wmtc.wmt.forum.pojo.FollowPojo;
import com.wmtc.wmt.forum.pojo.ForumCountPojo;
import com.wmtc.wmt.forum.pojo.ForumFansPojo;
import com.wmtc.wmt.forum.pojo.ForumMeListPojo;
import com.wmtc.wmt.forum.pojo.ForumPojo;
import com.wmtc.wmt.forum.pojo.ForumZanPojo;
import com.wmtc.wmt.forum.pojo.SearchUserPojo;
import com.wmtc.wmt.forum.pojo.SendCodePojo;
import com.wmtc.wmt.forum.pojo.SendPojo;
import com.wmtc.wmt.forum.pojo.SetPwdPojo;
import com.wmtc.wmt.forum.pojo.SetUserInfoPojo;
import com.wmtc.wmt.forum.pojo.UpdateUserInfoPojo;
import com.wmtc.wmt.forum.pojo.UserPojo;
import com.wmtc.wmt.message.bean.MessageBean;
import com.wmtc.wmt.message.pojo.MessagePojo;
import com.wmtc.wmt.personal.bean.CodeLoginBean;
import com.wmtc.wmt.personal.bean.LocalListBean;
import com.wmtc.wmt.personal.bean.LoginByCodeBean;
import com.wmtc.wmt.personal.bean.LoginDateBean;
import com.wmtc.wmt.personal.bean.QLoginBean;
import com.wmtc.wmt.personal.bean.UserInfoBean;
import com.wmtc.wmt.personal.bean.VersionBean;
import com.wmtc.wmt.personal.bean.WxPayInfoBean;
import com.wmtc.wmt.personal.pojo.AuthLoginPojo;
import com.wmtc.wmt.personal.pojo.CodeLoginPojo;
import com.wmtc.wmt.personal.pojo.FeedBackPojo;
import com.wmtc.wmt.personal.pojo.LocalPojo;
import com.wmtc.wmt.personal.pojo.LoginPojo;
import com.wmtc.wmt.personal.pojo.PhonePojo;
import com.wmtc.wmt.personal.pojo.PwdLoginPojo;
import com.wmtc.wmt.personal.pojo.VerLoginPojo;
import com.wmtc.wmt.personal.pojo.VersionPojo;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import top.jplayer.networklibrary.model.BaseModel;
import top.jplayer.networklibrary.net.retrofit.IoMainSchedule;

public class CommonModel extends BaseModel<WmtServer> {
    public CommonModel(Class<WmtServer> t) {
        super(t);
    }

    public Observable<JudgeBean> getAllJudge(JudgeListPojo judgePojo) {
        return mServer.getAllJudge(judgePojo).compose(new IoMainSchedule<>());
    }

    public Observable<CityOpenBean> toOpenCity(CityOpenPojo pojo) {
        return mServer.toOpenCity(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<CityListBean> cityLick() {
        return mServer.cityLick().compose(new IoMainSchedule<>());
    }

    public Observable<TitleBean> getTitle() {
        return mServer.getTitle().compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> saveOrderJudge(JudgePojo judgePojo) {
        return mServer.saveOrderJudge(judgePojo).compose(new IoMainSchedule<>());
    }

    public Observable<TeachSelBean> shopTeach(ShopTeachPojo pojo) {
        return mServer.shopTeach(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<TimeSelBean> timeSel(ShopTeachPojo pojo) {
        return mServer.timeSel(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<PreOrderBean> preOrder(PreOrderPojo pojo) {
        return mServer.preOrder(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<CustomChatBean> getCustomerService(OrderCustomPojo orderPojo) {
        return mServer.getCustomerService(orderPojo).compose(new IoMainSchedule<>());
    }

    public Observable<ForumListBean> findForum(ForumPojo pojo) {
        return mServer.findForum(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> saveXinde(SendPojo pojo) {
        return mServer.saveXinde(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> saveVideo(SendPojo pojo) {
        return mServer.saveVideo(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> saveHeji(SendPojo pojo) {
        return mServer.saveHeji(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> editHeji(SendPojo pojo) {
        return mServer.editHeji(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> editRiji(SendPojo pojo) {
        return mServer.editRiji(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> saveRiji(SendPojo pojo) {
        return mServer.saveRiji(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<ForumMeBean> findCommunityHomeStatisticCount(ForumPojo pojo) {
        return mServer.findCommunityHomeStatisticCount(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<OSSBean> ossToken() {
        return mServer.ossToken().compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> saveAfterSale(SaveAfterSalePojo pojo) {
        return mServer.saveAfterSale(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> addFeedback(FeedBackPojo pojo) {
        return mServer.addFeedback(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<FollowListBean> findFollow(ForumPojo pojo) {
        return mServer.findFollow(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<ForumListBean> socialHomeForumList(ForumMeListPojo pojo) {
        return mServer.socialHomeForumList(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<MessageBean> getMessageList(MessagePojo pojo) {
        return mServer.getMessageList(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> deleteMessage(MessagePojo pojo) {
        return mServer.deleteMessage(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> forumZan(ForumZanPojo pojo) {
        return mServer.forumZan(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<ShopInfoBean> shopInfo(ShopInfoPojo pojo) {
        return mServer.shopInfo(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<ShopHbBean> getOfflineCan(ShopInfoPojo pojo) {
        return mServer.getOfflineCan(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<ProjectInfoBean> proInfo(ShopInfoPojo pojo) {
        return mServer.proInfo(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<OrderJudgeBean> getOrderJudge(ShopInfoPojo pojo) {
        return mServer.getOrderJudge(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> detTeizi(ForumZanPojo pojo) {
        return mServer.detTeizi(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> forumCollection(ForumZanPojo pojo) {
        return mServer.forumCollection(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> followShopOrUser(FollowPojo pojo) {
        return mServer.followShopOrUser(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<ForumDetailBean> forumDetail(ForumZanPojo pojo) {
        return mServer.forumDetail(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<ForumCommentBean> forumComment(ForumPojo pojo) {
        return mServer.forumComment(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<CommentSaveBean> commentarySave(CommentSavePojo pojo) {
        return mServer.commentarySave(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<SearchUserBean> searchUser(SearchUserPojo pojo) {
        return mServer.searchUser(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<HotSearchBean> hotSearch() {
        return mServer.hotSearch().compose(new IoMainSchedule<>());
    }

    public Observable<FansListBean> fensList(ForumFansPojo pojo) {
        return mServer.fensList(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<FansListBean> attentionUserList(ForumFansPojo pojo) {
        return mServer.attentionUserList(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<ShopFollowBean> attentionShopList(ForumFansPojo pojo) {
        return mServer.attentionShopList(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<MeForumCountBean> findFourmCount(ForumCountPojo pojo) {
        return mServer.findFourmCount(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<UserInfoBean> getUserInfo(UserPojo userPojo) {
        return mServer.getUserInfo(userPojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> addAddress(LocalPojo pojo) {
        return mServer.addAddress(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> updateAddress(LocalPojo pojo) {
        return mServer.updateAddress(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<LocalListBean> getAddressList(LocalPojo pojo) {
        return mServer.getAddressList(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> delAddress(LocalPojo pojo) {
        return mServer.delAddress(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<OneOrderBean> getOrderByOne(UserPojo userPojo) {
        return mServer.getOrderByOne(userPojo).compose(new IoMainSchedule<>());
    }

    public Observable<ShopCouponBean> getShopCoupon(ShopCouponPojo pojo) {
        return mServer.getShopCoupon(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<ShopProjectNameBean> matchPros(ProListPojo pojo) {
        return mServer.matchPros(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaikeDateBean> getProductBaike(ProListPojo pojo) {
        return mServer.getProductBaike(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<MatchShopBean> matchShopByES(MatchPojo pojo) {
        return mServer.matchShopByES(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<DictListBean> getSkinDict(DictCodePojo Pojo) {
        return mServer.getDict(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<ProBkInfoBean> getProductBaike(DictCodePojo Pojo) {
        return mServer.getProductBaike(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<DictListBean> getDict(DictCodePojo Pojo) {
        return mServer.getDict(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<DictListBean> getxingqu(DictCodePojo Pojo) {
        return mServer.getDict(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<AliPayInfoBean> getPayOrderAli(PayPoJo poJo) {
        return mServer.getPayOrderAli(poJo).compose(new IoMainSchedule<>());
    }

    public Observable<WxPayInfoBean> getPayOrderWX(PayPoJo poJo) {
        return mServer.getPayOrderWX(poJo).compose(new IoMainSchedule<>());
    }

    public Observable<WxPayInfoBean> getPayOrderAppWx(PayPoJo poJo) {
        return mServer.getPayOrderAppWx(poJo).compose(new IoMainSchedule<>());
    }

    public Observable<AliPayInfoBean> getPayOrderAppAli(PayPoJo poJo) {
        return mServer.getPayOrderAppAli(poJo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> avatar(Map<String, RequestBody> body) {
        return mServer.avatar(body).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> updateUserInfo(UpdateUserInfoPojo Pojo) {
        return mServer.updateUserInfo(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> saveAccusation(AccusationPojo Pojo) {
        return mServer.saveAccusation(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> setUserInfo(SetUserInfoPojo Pojo) {
        return mServer.setUserInfo(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> setPhone(PhonePojo Pojo) {
        return mServer.setPhone(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<CodeLoginBean> authLoginBindTel(PhonePojo Pojo) {
        return mServer.authLoginBindTel(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<OrderListBean> getAllOrderList(OrderListPojo Pojo) {
        return mServer.getAllOrderList(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<CommentBannerBean> getBanner(BannerPojo Pojo) {
        return mServer.getBanner(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<ProBkBean> getFunctionList() {
        return mServer.getFunctionList().compose(new IoMainSchedule<>());
    }

    public Observable<MarketBean> getGoodList(BasePojo Pojo) {
        return mServer.getGoodList(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<RedbagBean> getRedbag(RedbagPojo Pojo) {
        return mServer.getRedbag(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<OrderDetailsBean> getOrderDetails(OrderDetailPojo Pojo) {
        return mServer.getOrderDetails(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<RefundBean> getRefund(RefundPojo Pojo) {
        return mServer.getRefund(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> delete(RefundPojo Pojo) {
        return mServer.delete(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> cancelOrder(RefundPojo Pojo) {
        return mServer.cancelOrder(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<DiscountsBean> getCouponList(CouponPojo Pojo) {
        return mServer.getCouponList(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<CouponDialogbean> getNewUseCoupon(UserPojo Pojo) {
        return mServer.getNewUseCoupon(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<VersionBean> getNewVersion(VersionPojo pojo) {
        return mServer.getNewVersion(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<LoginByCodeBean> registByVerificationCode(LoginPojo pojo) {
        return mServer.registByVerificationCode(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> smsVer(VerLoginPojo pojo) {
        return mServer.smsVer(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> setPassword(VerLoginPojo pojo) {
        return mServer.setPassword(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<CanCouponBean> getCanList(CanCouponPojo Pojo) {
        return mServer.getCanList(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<EfficacyDetailsBean> getSkinBaike(EfficacyPojo Pojo) {
        return mServer.getSkinBaike(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<DictListBean> getListByParentId(WentiPojo pojo) {
        return mServer.getListByParentId(pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> sendCode(SendCodePojo Pojo) {
        return mServer.sendCode(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<QLoginBean> authLogin(AuthLoginPojo Pojo) {
        return mServer.authLogin(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<CodeLoginBean> codeLogin(CodeLoginPojo Pojo) {
        return mServer.codeLogin(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> setPwd(SetPwdPojo Pojo) {
        return mServer.setPwd(Pojo).compose(new IoMainSchedule<>());
    }

    public Observable<LoginDateBean> pwdLogin(PwdLoginPojo Pojo) {
        return mServer.pwdLogin(Pojo).compose(new IoMainSchedule<>());
    }


}
