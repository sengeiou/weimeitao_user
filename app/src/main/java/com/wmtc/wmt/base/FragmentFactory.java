package com.wmtc.wmt.base;

import android.util.ArrayMap;

import com.wmtc.wmt.appoint.fragment.ServerFragment;
import com.wmtc.wmt.appoint.fragment.TbkMarketFragment;
import com.wmtc.wmt.forum.fragment.CommunityMainFragment;
import com.wmtc.wmt.message.fragment.MessageFragment;
import com.wmtc.wmt.personal.fragment.PersonalFragment;

import java.util.Map;

import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.base
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class FragmentFactory {
    private static FragmentFactory mFactory;

    public static FragmentFactory instance() {
        if (mFactory == null) {
            mFactory = new FragmentFactory();
        }
        return mFactory;
    }

    private Map<Integer, SuperBaseFragment> map = new ArrayMap<>();

    public SuperBaseFragment getSingleFragment(int position) {
        SuperBaseFragment fragment;
        if (map.containsKey(position)) {
            return map.get(position);
        }
        switch (position) {
            case 0:
                fragment = new CommunityMainFragment();
                break;
            case 1:
                fragment = new TbkMarketFragment();
                break;
            case 2:
                fragment = new ServerFragment();
                break;
            case 3:
                fragment = new MessageFragment();
                break;
            default:
                fragment = new PersonalFragment();
                break;
        }
        map.put(position, fragment);
        return fragment;
    }
}
