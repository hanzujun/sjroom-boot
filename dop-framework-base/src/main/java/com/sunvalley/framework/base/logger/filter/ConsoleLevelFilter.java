package com.sunvalley.framework.base.logger.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.PerformanceSensitive;

/**
 * @Author: manson.zhou
 * @Date: 2019/11/6 8:53
 * @Desc: level 可变的过滤器
 * @see ThresholdFilter
 */
@Plugin(name = "ConsoleLevelFilter", category = Node.CATEGORY, elementType = Filter.ELEMENT_TYPE, printObject = true)
@PerformanceSensitive("allocation")
public class ConsoleLevelFilter extends AbstractFilter {

	private static Level level = Level.WARN;

	public static void setLevel(Level level) {
		ConsoleLevelFilter.level = level;
	}

	private ConsoleLevelFilter(/*final Level level,*/ final Result onMatch, final Result onMismatch) {
		super(onMatch, onMismatch);
//		this.level = level;
	}

	@Override
	public Result filter(final Logger logger, final Level testLevel, final Marker marker, final String msg,
						 final Object... params) {
		return filter(testLevel);
	}

	@Override
	public Result filter(final Logger logger, final Level testLevel, final Marker marker, final Object msg,
						 final Throwable t) {
		return filter(testLevel);
	}

	@Override
	public Result filter(final Logger logger, final Level testLevel, final Marker marker, final Message msg,
						 final Throwable t) {
		return filter(testLevel);
	}

	@Override
	public Result filter(final LogEvent event) {
		return filter(event.getLevel());
	}

	private Result filter(final Level testLevel) {
		return testLevel.isMoreSpecificThan(this.level) ? onMatch : onMismatch;
	}

	@Override
	public Result filter(final Logger logger, final Level level, final Marker marker, final String msg,
						 final Object p0) {
		return filter(level);
	}

	@Override
	public Result filter(final Logger logger, final Level level, final Marker marker, final String msg,
						 final Object p0, final Object p1) {
		return filter(level);
	}

	@Override
	public Result filter(final Logger logger, final Level level, final Marker marker, final String msg,
						 final Object p0, final Object p1, final Object p2) {
		return filter(level);
	}

	@Override
	public Result filter(final Logger logger, final Level level, final Marker marker, final String msg,
						 final Object p0, final Object p1, final Object p2, final Object p3) {
		return filter(level);
	}

	@Override
	public Result filter(final Logger logger, final Level level, final Marker marker, final String msg,
						 final Object p0, final Object p1, final Object p2, final Object p3,
						 final Object p4) {
		return filter(level);
	}

	@Override
	public Result filter(final Logger logger, final Level level, final Marker marker, final String msg,
						 final Object p0, final Object p1, final Object p2, final Object p3,
						 final Object p4, final Object p5) {
		return filter(level);
	}

	@Override
	public Result filter(final Logger logger, final Level level, final Marker marker, final String msg,
						 final Object p0, final Object p1, final Object p2, final Object p3,
						 final Object p4, final Object p5, final Object p6) {
		return filter(level);
	}

	@Override
	public Result filter(final Logger logger, final Level level, final Marker marker, final String msg,
						 final Object p0, final Object p1, final Object p2, final Object p3,
						 final Object p4, final Object p5, final Object p6,
						 final Object p7) {
		return filter(level);
	}

	@Override
	public Result filter(final Logger logger, final Level level, final Marker marker, final String msg,
						 final Object p0, final Object p1, final Object p2, final Object p3,
						 final Object p4, final Object p5, final Object p6,
						 final Object p7, final Object p8) {
		return filter(level);
	}

	@Override
	public Result filter(final Logger logger, final Level level, final Marker marker, final String msg,
						 final Object p0, final Object p1, final Object p2, final Object p3,
						 final Object p4, final Object p5, final Object p6,
						 final Object p7, final Object p8, final Object p9) {
		return filter(level);
	}


	/**
	 * Creates a ThresholdFilter.
	 * <p>
	 * //	 * @param level    The logger Level.
	 *
	 * @param match    The action to take on a match.
	 * @param mismatch The action to take on a mismatch.
	 * @return The created ThresholdFilter.
	 */
	// TODO Consider refactoring to use AbstractFilter.AbstractFilterBuilder
	@PluginFactory
	public static ConsoleLevelFilter createFilter(
//		@PluginAttribute("level") final Level level,
		@PluginAttribute("onMatch") final Result match,
		@PluginAttribute("onMismatch") final Result mismatch) {
//		final Level actualLevel = level == null ? Level.ERROR : level;
		final Result onMatch = match == null ? Result.NEUTRAL : match;
		final Result onMismatch = mismatch == null ? Result.DENY : mismatch;
		return new ConsoleLevelFilter(onMatch, onMismatch);
	}
}
