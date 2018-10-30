package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
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
 * @since 2018-09-17
 */
@TableName("tmp_test")
public class Test extends Model<Test> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value="id", type= IdType.ID_WORKER)
    private Long id;
    /**
     * 订单名
     */
    @TableField("order_name")
    private String orderName;
    /**
     * 订单数量
     */
    @TableField("order_num")
    private Integer orderNum;
    /**
     * 订单发往地
     */
    @TableField("order_to")
    private String orderTo;
    /**
     * 所属公司id
     */
    private Integer deptid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderTo() {
        return orderTo;
    }

    public void setOrderTo(String orderTo) {
        this.orderTo = orderTo;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Test{" +
        "id=" + id +
        ", orderName=" + orderName +
        ", orderNum=" + orderNum +
        ", orderTo=" + orderTo +
        ", deptid=" + deptid +
        "}";
    }
}
