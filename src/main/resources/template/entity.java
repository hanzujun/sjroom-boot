package ${config.entityPackage};


import com.dazong.persistence.annotation.TableField;
import com.dazong.persistence.annotation.TableId;
import com.dazong.persistence.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;

/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@TableName("${tableName}")
@Data
@EqualsAndHashCode(callSuper = false)
public class ${upperModelName} implements Serializable {

    private static final long serialVersionUID = 1L;
<% for(var item in dbTableFieldInfoList) {%>

    /**
     * ${item.comment}
     */
    <% if (item.column == dbTableInfo.keyColumn) {%>
    @TableId
    <% } else {%>
    @TableField("${item.column}")
    <% }%>
    private ${item.propertyType} ${item.property};
<% }%>
}
