package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author kenny
 * @since 2018-10-30
 */
@TableName("test_use")
public class Use extends Model<Use> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    /**
     * 货物
     */
    private String good;
    /**
     * 怕怕
     */
    private String papap;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getPapap() {
        return papap;
    }

    public void setPapap(String papap) {
        this.papap = papap;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Use{" +
        "id=" + id +
        ", good=" + good +
        ", papap=" + papap +
        "}";
    }
}
