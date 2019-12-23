package github.sjroom.mybatis.fill;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author george.ouyang
 *
 */
@Data
public class FillFieldNameObject {

	private Set<FieldInfo> fieldInfoSet = Sets.newHashSetWithExpectedSize(2);

	private Set<Long> userIdSet = Sets.newHashSetWithExpectedSize(8);


	@Data
	@AllArgsConstructor
	public static class FieldInfo {

		private String fieldName;

		private Field field;
	}

}
