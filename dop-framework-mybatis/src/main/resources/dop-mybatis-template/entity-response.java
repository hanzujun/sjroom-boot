package ${currentPackage};

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
<% if (fileSuffix=='RespVo') {%>import io.swagger.annotations.ApiModelProperty;<% } %>


/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@Data
public class ${upperModelName}${fileSuffix}  {
<% for(var item in dbTableFieldInfoList) {%>

<% if (fileSuffix=="RespVo") {%>
    @ApiModelProperty("${item.comment}")
<% } else {%>
    /**
     * ${item.comment}
     */
<% } %>
    private ${item.propertyType} ${item.property};
    <% if(strutil.startWith(item.comment,"@status")) { %>

    /**
     * ${item.comment},状态描述
     */
    public String get${sputil.capitalize(item.property)}Desc(){
        return ${upperModelName}${sputil.capitalize(item.property)}Enum.getMsg(this.${item.property});
    }
    <% }%>
<% }%>
}
