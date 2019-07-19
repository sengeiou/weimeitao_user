package com.wmtc.wmt.forum.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Objects;

/**
 * Created by Obl on 2019/5/5.
 * com.wmtc.wmt.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
@Entity
public class HistorySearchBean {
    @Id(autoincrement = true)
    private Long id;
    public String name;

    @Generated(hash = 1144824581)
    public HistorySearchBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 954352461)
    public HistorySearchBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this.name == o) return true;
        if (o == null || String.class != o.getClass()) return false;
        return Objects.equals(name, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
