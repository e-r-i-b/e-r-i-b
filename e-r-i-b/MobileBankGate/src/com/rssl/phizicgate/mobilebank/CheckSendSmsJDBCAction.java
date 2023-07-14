package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.dataaccess.jdbc.JDBCAction;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;

import static com.rssl.phizgate.mobilebank.GateCardHelper.hideCardNumber;

/**
 * @author Erkin
 * @ created 17.12.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */
@Deprecated
//todo CHG059738 удалить
class CheckSendSmsJDBCAction implements JDBCAction<Boolean>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private final String cardNumber;

	private static final String MB_CHECK_SEND_SMS = "mb_CheckSendSms";

	CheckSendSmsJDBCAction(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public Boolean execute(Connection con) throws SQLException
	{
		Calendar timeStart = Calendar.getInstance();
		CallableStatement cstmt =
				con.prepareCall("{? = call " + MB_CHECK_SEND_SMS + "(" +
						"@CardNumber    = ?)}"
				);

		LogThreadContext.setProcName(MB_CHECK_SEND_SMS);
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, cardNumber);
			cstmt.execute();

			int rc = cstmt.getInt(1);
			return testReturnCode(rc);
		}
		finally
		{
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
			try
			{
				StandInUtils.registerMBTimeOutEvent(DateHelper.diff(Calendar.getInstance(), timeStart));
			}
			catch (GateException e)
			{
				log.error(e);
			}
		}
	}

	@SuppressWarnings({"MagicNumber"})
	private boolean testReturnCode(int rc)
	{
		switch (rc)
		{
			case 0:
				log.trace("Возможность отправки СМС по карте " + hideCardNumber(cardNumber) + " подтверждена");
				return true;

			case -1:
				log.info("Отправка СМС по номеру карты " + hideCardNumber(cardNumber) + " не возможна.");
				return false;

			case -2:
				log.info("Отправка СМС по карте " + hideCardNumber(cardNumber) + " заблокирована.");
				return false;

			case -3:
				log.trace("Отправка СМС по карте " + hideCardNumber(cardNumber) + " заблокирована по неоплате. " +
						"Допускается отправлять информационные СМС");
				return true;

			case -4:
				log.info("Отправка СМС по карте " + hideCardNumber(cardNumber) + " заблокирована Банком.");
				return false;

			case -5:
				log.trace("Карта " + hideCardNumber(cardNumber) + " подключена к экономному тарифу. " +
						"Допускается отправлять информационные СМС");
				return true;

			case -128:
			case -129:
				log.info("Карта " + hideCardNumber(cardNumber) + " не подключена к МБ. " +
						"Отправка СМС по карте не допустима");
				return false;

			default:
				log.warn("Мобильный банк вернул неожиданный код: " + rc + ". " +
						"Отправка СМС по карте " + hideCardNumber(cardNumber) + " не возможна");
				return false;
		}
	}

	public boolean isConnectionLogEnabled()
	{
		return false;
	}
}
