package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.jdbc.JDBCUtils;
import com.rssl.phizic.gate.mobilebank.ERMBPhone;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.utils.xml.XMLElementAttributes;
import com.rssl.phizic.utils.xml.XMLMessageWriter;

import java.sql.*;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 * Экшн, вызывающий хранимую процедуру Обновления в БД МБК таблицы телефонов, зарегистрированных в ЕРМБ
 * Входной параметр процедуры - множество номеров телефонов и кодов действия (добавить в таблицу/удалить из таблицы)
 * @author Rtischeva
 * @ created 09.07.14
 * @ $Author$
 * @ $Revision$
 */
class ErmbManagePhonesJDBCAction extends JDBCActionBase<Void>
{
	private static final int SUCCESS_RET_CODE = 0;
	private static final int INCORRECT_SIZE_RET_CODE = -1;
	private static final int INCORRECT_FORMAT_RET_CODE = -2;
	private static final int ERROR_RET_CODE = -3;

	private final JDBCMessageLogger messageLogger = new JDBCMessageLogger(System.mbk);
	private String phonesArrayXml;

	public ErmbManagePhonesJDBCAction(List<ERMBPhone> ermbPhones)
	{
		this.phonesArrayXml = makePhonesArrayXml(ermbPhones);
	}

	public Void doExecute(Connection con) throws SQLException, SystemException
	{
		messageLogger.startEntry("mb_BATCH_ERMB_ManagePhones");
		messageLogger.setSQLStatement("? = call mb_BATCH_ERMB_ManagePhones");
		messageLogger.addInputParam("PhonesArray", phonesArrayXml);

		CachedRowSet rs = null;
		CallableStatement cstmt = con.prepareCall("{? = call mb_BATCH_ERMB_ManagePhones(@PhonesArray = ?)}");
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setString(2, phonesArrayXml);

			rs = JDBCUtils.executeStoredProcedureQuery(cstmt);
			messageLogger.setResultSet(rs);
			processResult(rs, cstmt);
		}
		finally
		{
			messageLogger.finishEntry();
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
			if (rs != null)
				try { rs.close(); } catch (Exception ignore) {}
		}

		return null;
	}

	private void processResult(ResultSet rs, CallableStatement cstmt) throws SQLException, SystemException
	{
		int returnCode = cstmt.getInt(1);
		StandInUtils.registerMBOfflineEvent(returnCode);
		messageLogger.setResultCode(returnCode);
		StandInUtils.checkStandInAndThrow(returnCode);

		if (returnCode == INCORRECT_SIZE_RET_CODE)
			throw new SQLException("Превышен размер порции для хранимой процедуры Обновление в БД МБК таблицы телефонов зарегистрированных в ЕРМБ");
		if (returnCode == INCORRECT_FORMAT_RET_CODE)
			throw new SQLException("Некорректный формат параметра для хранимой процедуры Обновление в БД МБК таблицы телефонов зарегистрированных в ЕРМБ");
		if (returnCode == ERROR_RET_CODE)
			throw new SQLException("Хранимая процедура  Обновление в БД МБК таблицы телефонов зарегистрированных в ЕРМБ вернула ошибку");
	}

	private String makePhonesArrayXml(List<ERMBPhone> ermbPhones)
	{
		XMLMessageWriter writer = new XMLMessageWriter("UTF-8");

		writer.startDocument();
		writer.startElement("phones");

		for (ERMBPhone ermbPhone : ermbPhones)
		{
			if (ermbPhone == null)
				throw new IllegalArgumentException("телефон не может быть null!");

			XMLElementAttributes attributes = new XMLElementAttributes();
			attributes.add("number", MBKConstants.MBK_PHONE_NUMBER_FORMAT.format(ermbPhone.getPhoneNumber()));
			if (ermbPhone.isPhoneUsage())
				attributes.add("action", "C");
			else
				attributes.add("action", "D");

			writer.startElement("phone", attributes).endElement();
		}
		writer.endElement();

		return writer.toString();
	}

	@Override
	public boolean isConnectionLogEnabled()
	{
		return false;
	}
}
