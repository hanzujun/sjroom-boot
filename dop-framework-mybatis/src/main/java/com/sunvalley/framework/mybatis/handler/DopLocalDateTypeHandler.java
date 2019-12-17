package com.sunvalley.framework.mybatis.handler;

import com.sunvalley.framework.core.utils2.UtilDate;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.LocalDateTypeHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * 自定义扩展 LocalDate 的处理器
 *
 * <p>
 * 目标： 不处理时区
 * java    LocalDate
 * mysql date
 * </p>
 *
 * @author manson.zhou
 */
public class DopLocalDateTypeHandler extends LocalDateTypeHandler {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType) throws SQLException {
		ps.setObject(i, UtilDate.formatDateStr(parameter));
	}
}
