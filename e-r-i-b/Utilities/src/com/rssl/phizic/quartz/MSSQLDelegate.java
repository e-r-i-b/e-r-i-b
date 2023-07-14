package com.rssl.phizic.quartz;

import org.apache.commons.logging.Log;

import java.io.ByteArrayOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Kosyakov
 * @ created 04.12.2006
 * @ $Author: kosyakov $
 * @ $Revision: 2960 $
 */
public class MSSQLDelegate extends org.quartz.impl.jdbcjobstore.MSSQLDelegate
{
	public MSSQLDelegate(Log log, String tablePrefix, String instanceId)
	{
		super(log, tablePrefix, instanceId);
	}

	public MSSQLDelegate(Log log, String tablePrefix, String instanceId, Boolean useProperties)
	{
		super(log, tablePrefix, instanceId, useProperties);
	}

	protected void setBytes(PreparedStatement ps, int index, ByteArrayOutputStream baos) throws SQLException
	{
		ps.setBytes(index, (baos == null) ? null : baos.toByteArray());
	}
}
