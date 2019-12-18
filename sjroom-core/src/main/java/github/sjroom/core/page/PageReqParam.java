package github.sjroom.core.page;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 分页查询模型，用于业务继承
 *
 * @author manson.zhou
 */
@Data
@ApiModel(description = "分页查询模型")
public class PageReqParam extends PageParam {


    @Nullable
    @ApiModelProperty("升序参数数组")
    private List<String> ascs;
    @Nullable
    @ApiModelProperty("降序参数数组")
    private List<String> descs;

}
