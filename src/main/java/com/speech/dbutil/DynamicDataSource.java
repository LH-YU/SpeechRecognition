package com.speech.dbutil;

import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	static org.slf4j.Logger logger = LoggerFactory.getLogger(JdbcContextHolder.class);
	
	
	@Override
	protected Object determineCurrentLookupKey() {
		return JdbcContextHolder.getJdbcType();
	}

	@Override
	public Logger getParentLogger() {
		return null;
	}
}