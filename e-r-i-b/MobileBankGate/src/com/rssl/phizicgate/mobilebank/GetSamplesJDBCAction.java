package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.logging.LogThreadContext;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Erkin
 * @ created 21.04.2010
 * @ $Author$
 * @ $Revision$
 */
class GetSamplesJDBCAction extends JDBCActionBase<String>
{
	private final String cardNumber;

	private final String phoneNumber;

	private static final String MB_WWW_GET_SAMPLES = "mb_WWW_GetSamples";

	GetSamplesJDBCAction(String cardNumber, String phoneNumber)
	{
		this.cardNumber = cardNumber;
		this.phoneNumber = phoneNumber;
	}

	public String doExecute(Connection con) throws SQLException, SystemException
	{
		CallableStatement cstmt =
				con.prepareCall("{? = call " + MB_WWW_GET_SAMPLES +" "+
						"(@strCardNumber = ?, " +
						"@strPhoneNumber = ?, " +
						"@strRetStr = ?)}");

		LogThreadContext.setProcName(MB_WWW_GET_SAMPLES);
		try {
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, cardNumber);
			cstmt.setString(3, phoneNumber);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.execute();

			// анализ кода возврата
			int rc = cstmt.getInt(1);
			StandInUtils.registerMBOfflineEvent(rc);
			testReturnCode(rc);

			return cstmt.getString(4);
		} finally {
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}

	private void testReturnCode(int rc) throws SQLException
	{
		StandInUtils.checkStandInAndThrow(rc);
		switch (rc) {
			case 0: // all correct
				break;

			case 50001:
				throw new SQLException("не указан или указан заведомо неверный номер карты");

			case -5:
				throw new SQLException("номер карты содержит не только цифры");

			case -6:
				throw new SQLException("в БД АС МБ не найдена карта с указанным номером");

			default:
				if (rc < 0)
					throw new SQLException("ошибка в БД. Причина по коду не определяется (" + rc + ")");
				throw new SQLException("неизвестный код возврата (" + rc + ")");
		}
	}
}
