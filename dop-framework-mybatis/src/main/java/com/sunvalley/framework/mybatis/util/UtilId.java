package com.sunvalley.framework.mybatis.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.sunvalley.framework.mybatis.annotation.TableBId;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 业务id 生成工具
 *
 * @author dream.lu
 */
@UtilityClass
public class UtilId {

	/**
	 * 获取 bid 属性名
	 *
	 * @param modelClass 模型类型
	 * @return 属性名
	 */
	public static String getBIdFieldName(Class<?> modelClass) {
		List<Field> fieldList = ReflectionKit.getFieldList(modelClass);
		String fieldName = null;
		for (Field field : fieldList) {
			if (field.isAnnotationPresent(TableBId.class)) {
				return field.getName();
			}
		}
		return fieldName;
	}

	/**
	 * 生成业务id
	 *
	 * @return 业务id
	 */
	public static Long getBId() {
		return IdWorker.getId();
	}
}
