package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.LogThreadContext;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Определение подключения к МБ по номерам телефонов
 * @author Dorzhinov
 * @ created 27.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class GetMobileContactJDBCAction extends JDBCActionBase<String>
{
	private static final String MB_WWW_GET_MOBILE_CONTACT = "mb_WWW_GetMobileContact";

	private String phoneNumbersToFind;

	public GetMobileContactJDBCAction(String phoneNumbersToFind)
	{
		this.phoneNumbersToFind = phoneNumbersToFind;
	}

	public String doExecute(Connection con) throws SQLException, SystemException
	{
		CallableStatement cstmt = con.prepareCall("{? = call " + MB_WWW_GET_MOBILE_CONTACT + " " +
				"(@PhoneNumbersToFind = ?, " +   //VARCHAR(max),              --телефоны, проверяемые на подключение к МБ (разделитель ";")
				"@PhoneNumbersFound = ? )}"       //VARCHAR(max)  = NULL OUTPUT --телефоны, подключенные к МБ (разделитель ";")
		);

		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setString(2, phoneNumbersToFind);       // PhoneNumbersToFind
			cstmt.registerOutParameter(3, Types.VARCHAR); // PhoneNumbersFound 
			LogThreadContext.setProcName(MB_WWW_GET_MOBILE_CONTACT);
			cstmt.execute();
			return processResult(cstmt);
		} finally {
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}

	private String processResult(CallableStatement cstmt) throws SQLException, GateException
	{
		int rc = cstmt.getInt(1);
		StandInUtils.registerMBOfflineEvent(rc);
		StandInUtils.checkStandInAndThrow(rc);
		switch (rc)
		{
			case 0: //OK
				return cstmt.getString(3);
			case -1: //неверный формат данных в @PhoneNumbersToFind
				throw new SQLException("Неверный формат данных во входящем параметре.");
			case -2: //превышено разрешенное число вызовов ХП в минуту
				throw new SQLException("Превышено разрешенное число вызовов ХП в минуту");
			default: //Любой другой – внутренняя ошибка процедуры
				throw new SQLException("Внутренняя ошибка процедуры. Код возврата: " + rc);
		}
	}
}
