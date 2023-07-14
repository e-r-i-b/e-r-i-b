package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.LogThreadContext;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Экшн, вызывающий хранимую процедуру Возврата результата обработки в ЕРМБ подключения, полученного из МБК
 * Входные параметры процедуры: id подключения, код результата обработки и описание ошибки (если результат обработки - ошибка)
 * @author Rtischeva
 * @ created 09.07.14
 * @ $Author$
 * @ $Revision$
 */
class RegistrationResultForErmbJDBCAction extends JDBCActionBase<Void>
{
	private static final int INCORRECT_FORMAT_RET_CODE = -1;
	private static final int ERROR_RET_CODE = -2;

	private long id;
	private int resultCode;
	private String errorDescr;

	RegistrationResultForErmbJDBCAction(long id, int resultCode, String errorDescr)
	{
		this.id = id;
		this.resultCode = resultCode;
		this.errorDescr = errorDescr;
	}

	public Void doExecute(Connection con) throws SQLException, GateException
	{
		LogThreadContext.setProcName("mb_BATCH_ERMB_RegistrationResult");

		CallableStatement cstmt = con.prepareCall("{? = call mb_BATCH_ERMB_RegistrationResult" +
				"(@ID = ?, \n" +
				"@ResultCode = ?, \n" +
				"@ErrorDescr = ?)}");
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setLong(2, id);
			cstmt.setInt(3, resultCode);
			cstmt.setString(4, errorDescr);
			cstmt.execute();

			processResult(cstmt);
		}
		finally
		{
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}

		return null;
	}

	private void processResult(CallableStatement cstmt) throws SQLException, GateException
	{
		int returnCode = cstmt.getInt(1);
		StandInUtils.registerMBOfflineEvent(returnCode);
		StandInUtils.checkStandInAndThrow(returnCode);
		if (returnCode == INCORRECT_FORMAT_RET_CODE)
			throw new SQLException("Ошибка формата для хранимой процедуры Возврат результата обработки в ЕРМБ подключения полученного из МБК");
		if (returnCode == ERROR_RET_CODE)
			throw new SQLException("Хранимая процедура Возврат результата обработки в ЕРМБ подключения полученного из МБК");
	}
}
