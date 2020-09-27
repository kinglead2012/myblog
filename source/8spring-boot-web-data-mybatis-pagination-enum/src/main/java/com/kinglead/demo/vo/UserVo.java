package com.kinglead.demo.vo;

import com.kinglead.demo.entity.PageRowInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserVo extends PageRowInfo implements Serializable {
    private static final long serialVersionUID = -21070736985722463L;
    /**
    * 用户名
    */
    @NotNull(message = "用户名不能为空")
    @Size(max = 20, message = "用户名长度不能大于20")
    private String name;

}