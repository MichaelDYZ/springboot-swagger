package com.dyz.swagger.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dyz
 * @version 1.0
 * @date 2020/5/13 15:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户实体类", description = "User")
public class User implements Serializable {
    private static final long serialVersionUID = 5057954049311281252L;

    @ApiModelProperty(value = "id", required = true)
    private Integer id;

    @ApiModelProperty(value = "用户名", required = true)
    private String name;

    @ApiModelProperty(value = "性别", required = true)
    private String sex;
}
