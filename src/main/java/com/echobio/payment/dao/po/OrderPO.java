package com.echobio.payment.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName("order_info")
public class OrderPO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "type")
    private String type;
    @TableField(value = "type_name")
    private String typeName;
    @TableField(value = "order_no")
    private String orderNo;
    @TableField(value = "pay_status")
    private Integer payStatus;
    @TableField(value = "user_id")
    private String userId;
    @TableField(value = "amount")
    private Double amount;
    @TableField(value = "pay_amount")
    private Double payAmount;
    @TableField(value = "use_point")
    private Integer usePoint;
    @TableField(value = "point_amount")
    private Double pointAmount;
    @TableField(value = "discount")
    private Double discount;
    @TableField(value = "pay_time")
    private Date payTime;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "modify_time")
    private Date modifyTime;
    @TableField(value = "create_by")
    private String createBy;
    @TableField(value = "modify_by")
    private String modifyBy;
}
