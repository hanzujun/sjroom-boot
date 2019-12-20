package github.sjroom.mybatis.page;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询模型，用于业务继承
 *
 * @author dream.lu
 */
@Data
@ApiModel(description = "分页查询模型")
public class PageParam {

    @ApiModelProperty("页码")
    private Long pageNum = 1L;
    @ApiModelProperty("分页大小")
    private Long pageSize = 10L;
}
