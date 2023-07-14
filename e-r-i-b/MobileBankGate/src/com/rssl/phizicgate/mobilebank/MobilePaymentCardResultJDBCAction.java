package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mobilebank.MobilePaymentCardResult;
import com.rssl.phizic.logging.LogThreadContext;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Gulov
 * @ created 23.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшн для отправки ответов на запрос МБК о получении номеров карт по номеру телефона
 */
public class MobilePaymentCardResultJDBCAction extends JDBCActionBase<Void>
{
	private static final String PROCEDURE_NAME = "ERMB_MobilePaymentCardResult";
	private static final String ERMB_MOBILE_PAYMENT_CARD_RESULT = "{? = call " + PROCEDURE_NAME + " (" +
			"@MobilePaymentCardRequestId = ?, " +
			"@CardNumber = ?, " +
			"@ClientName = ?, " +
			"@ResultCode = ?, " +
			"@Comment = ? " +
			") }";

	/**
	 * Результат на запрос определение карты по номеру телефона и тербанку (P2P).
	 */
	private final MobilePaymentCardResult cardResult;

	public MobilePaymentCardResultJDBCAction(MobilePaymentCardResult cardResult)
	{
		this.cardResult = cardResult;
	}

	public Void doExecute(Connection con) throws SQLException, SystemException
	{
		CallableStatement cstmt = con.prepareCall(ERMB_MOBILE_PAYMENT_CARD_RESULT);
		LogThreadContext.setProcName(PROCEDURE_NAME);
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setInt(2, cardResult.getId());
			cstmt.setString(3, cardResult.getCardNumber());
			cstmt.setString(4, cardResult.getClientName());
			cstmt.setInt(5, cardResult.getResultCode());
			cstmt.setString(6, cardResult.getComment());
			cstmt.execute();
			// анализ кода возврата
			int rc = cstmt.getInt(1);
			testResult(rc);
		}
		finally
		{
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}

		return null;
	}

	private void testResult(int rc) throws SQLException, GateException
	{
		StandInUtils.registerMBOfflineEvent(rc);
		StandInUtils.checkStandInAndThrow(rc);
		switch (rc)
		{
			// ОК. Информация принята.
			case 0:
				break;
			// ОК. Информация принята, однако она поступила слишком поздно - МБК уже отказал клиенту.
			case 1306130001:
				break;
			// ОК. Инфомация была принята ранее. ЕРИБ передал информацию МБК повторно.
			case 1306130002:
				break;
			// ПРОБЛЕМА. Не существует @MobilePaymentCardRequestID для которого ЕРИБ передал информацию.
			case 1306138881:
				throw new SQLException("Не существует @MobilePaymentCardRequestID для которого ЕРИБ передал информацию.");
			// ПРОБЛЕМА. Ошибка в формате номера карты.
			case 1306138882:
				throw new SQLException("Ошибка в формате номера карты.");
			// ПРОБЛЕМА. Передан ResultCode неизвестный МБК.
			case 1306138883:
				throw new SQLException("Передан ResultCode неизвестный МБК.");
			// ПРОБЛЕМА. Техническая ошибка.
			case 1306138888:
				throw new SQLException("Техническая ошибка.");
			default:
				throw new SQLException("неизвестный код возврата (" + rc + ")");
		}
	}
}
