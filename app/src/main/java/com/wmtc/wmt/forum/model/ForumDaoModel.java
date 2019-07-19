package com.wmtc.wmt.forum.model;

import android.content.Context;

import com.wmtc.wmt.greendao.DaoManager;
import com.wmtc.wmt.greendao.HistorySearchBeanDao;
import com.wmtc.wmt.forum.bean.HistorySearchBean;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import top.jplayer.baseprolibrary.utils.LogUtil;
/**
 * Created by Obl on 2018/8/10.
 * top.jplayer.quick_test.mvp.model
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class ForumDaoModel {
    private DaoManager mManager;

    public ForumDaoModel(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成bean记录的插入，如果表未创建，先创建bean表
     *
     * @param bean
     * @return
     */
    public boolean insertUser(HistorySearchBean bean) {
        boolean flag = false;
        flag = mManager.getDaoSession().getHistorySearchBeanDao().insert(bean) != -1;
        LogUtil.e(bean);
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     */
    public boolean insertMultUser(final List<HistorySearchBean> beanList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (HistorySearchBean bean : beanList) {
                        mManager.getDaoSession().insertOrReplace(bean);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     *
     * @return
     */
    public boolean updatebean(HistorySearchBean bean) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(bean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     *
     * @param bean
     * @return
     */
    public boolean deleteUserBean(HistorySearchBean bean) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(bean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     *
     * @return
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(HistorySearchBean.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<HistorySearchBean> queryAllbean() {
        return mManager.getDaoSession().loadAll(HistorySearchBean.class);
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public HistorySearchBean querybeanById(long key) {
        return mManager.getDaoSession().load(HistorySearchBean.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<HistorySearchBean> querybeanByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(HistorySearchBean.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     */
    public List<HistorySearchBean> querybeanByQueryBuilder(long id) {
        QueryBuilder<HistorySearchBean> queryBuilder = mManager.getDaoSession().queryBuilder(HistorySearchBean.class);
        return queryBuilder.where(HistorySearchBeanDao.Properties.Id.eq(id)).list();
    }
}
