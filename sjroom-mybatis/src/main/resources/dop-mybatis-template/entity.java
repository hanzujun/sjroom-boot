package ${currentPackage};

import com.baomidou.mybatisplus.annotation.TableName;
import github.sjroom.mybatis.core.${sys};
import github.sjroom.mybatis.annotation.TableBId;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@TableName("${dbTableName}")
@Data
@EqualsAndHashCode(callSuper = true)
public class ${upperModelName} extends ${sys} {

<% for(var item in dbTableFieldInfoList) {%>
    <% if (item.column == "tenant_id" ||item.column == "system_id" || item.column == "created_at" || item.column == "created_by"){
        continue;
    } if (item.column == "updated_at" || item.column == "updated_by" || item.column == "owner" || item.column == "own_role_id" || item.column == "id"){
        continue;
    } %>
    /**
     * ${item.comment}
     */
    <% if (item.related) {%>
    @TableBId
    <% } %>
    private ${item.propertyType} ${item.property};
<% }%>
}
