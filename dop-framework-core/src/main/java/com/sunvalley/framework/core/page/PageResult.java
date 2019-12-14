package com.sunvalley.framework.core.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sunvalley.framework.core.utils2.UtilCollection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页查询响应模型，用于业务继承
 *
 * @author dream.lu
 */
@Data
@ApiModel(description = "分页查询模型")
public class PageResult<T> {
    @ApiModelProperty("页码")
    private Long pageNum;
    @ApiModelProperty("分页大小")
    private Long pageSize;
    @ApiModelProperty("总页数")
    private Long pageCount;
    @ApiModelProperty("总数")
    private Long totalCount;
    @ApiModelProperty("数据集合")
    private List<T> records;

}
