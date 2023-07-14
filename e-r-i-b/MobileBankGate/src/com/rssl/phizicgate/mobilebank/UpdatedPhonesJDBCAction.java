package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.jdbc.JDBCUtils;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.profile.MBKPhone;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;

import java.sql.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 * запрос для получения списка обновленных телефонов.
 *
 * @author bogdanov
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 */

public class UpdatedPhonesJDBCAction extends JDBCActionBase<List<MBKPhone>>
{
	/*
	 ХП будет называться mb_WWW_GetMobileContactDate
	 Будет один параметр @ChangeDate DATE
	 Код возврата 0, если успешно. Любой другой код будет означать тех .ошибку, которая требует повторного вызова
	 Возвращать процедура будет рекордсет:
	 PhoneNumber varchar(20) -- например 79161234567
	 ChangeType char(1) -- D- удален телефон, I-добавлен телефон

	 Вызов процедуры нужно делать с отчетного сервера МБК примерно таким образом:

	 DECLARE             @return_value int

	 EXEC      @return_value = [dbo].[mb_WWW_GetMobileContactDate]
	                               @ChangeDate = '2014-09-01'

	 SELECT  'Return Value' = @return_value
	 */
	private static final String PHONE_NUMBER = "PhoneNumber";
	private static final String CHANGE_TYPE = "ChangeType";
	private static final int SUCCESS_RET_CODE = 0;

	private final Date date;
	private final String dateAsString;
	private int addedPhones = 0;
	private int deletedPhones = 0;

	private final JDBCMessageLogger messageLogger = new JDBCMessageLogger(System.mbk);

	public UpdatedPhonesJDBCAction(Calendar start)
	{
		date = new Date(start.getTimeInMillis());
		dateAsString = DateHelper.formatDateToString(start);
	}

	public List<MBKPhone> doExecute(Connection con) throws SQLException, SystemException
	{
		messageLogger.startEntry("mb_WWW_GetMobileContactDate");
		messageLogger.setSQLStatement("? = call mb_WWW_GetMobileContactDate");
		messageLogger.addInputParam("ChangeDate", dateAsString);

		CachedRowSet rs = null;
		CallableStatement cstmt = con.prepareCall("{? = call mb_WWW_GetMobileContactDate(@ChangeDate = ?)}");
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setDate(2, date);
			rs = JDBCUtils.executeStoredProcedureQuery(cstmt);
			messageLogger.setResultSet(rs);
			return processResult(rs, cstmt);
		}
		finally
		{
			messageLogger.finishEntry();
			if (rs != null)
				try { rs.close(); } catch (SQLException ignored) {}
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}

	private List<MBKPhone> processResult(ResultSet rs, CallableStatement cstmt) throws SQLException, GateException
	{
		int rc = cstmt.getInt(1);
		StandInUtils.registerMBOfflineEvent(rc);
		messageLogger.setResultCode(rc);

		List<MBKPhone> result = new LinkedList<MBKPhone>();
		if (rs != null)
		{
			while (rs.next())
				result.add(fillRequest(rs));
		}

		PhizICLogFactory.getLog(LogModule.Scheduler).info("Обновление номеров клиентов Сбербанка за " + dateAsString + ". Добавлено номеров: " + addedPhones + ". Удалено номеров: " + deletedPhones);
		if (rc != SUCCESS_RET_CODE)
			throw new SQLException("Хранимая процедура получения списка номеров телефонов вернула ошибку");

		return result;
	}

	private MBKPhone fillRequest(ResultSet rs) throws SQLException
	{
		String phoneNumber = rs.getString(PHONE_NUMBER);
		boolean deleted = rs.getString(CHANGE_TYPE).equals("D");
		if (deleted)
			deletedPhones++;
		else
			addedPhones++;

		MBKPhone mbkPhone = new MBKPhone();
		mbkPhone.setPhone(phoneNumber);
		mbkPhone.setAdded(!deleted);
		mbkPhone.setLastUpdateTime(Calendar.getInstance());
		return mbkPhone;
	}

	@Override
	public boolean isConnectionLogEnabled()
	{
		return false;
	}
}
