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
 * @deprecated ���������� �� ���
 */
@Deprecated
//todo CHG059738 �������
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
				log.trace("����������� �������� ��� �� ����� " + hideCardNumber(cardNumber) + " ������������");
				return true;

			case -1:
				log.info("�������� ��� �� ������ ����� " + hideCardNumber(cardNumber) + " �� ��������.");
				return false;

			case -2:
				log.info("�������� ��� �� ����� " + hideCardNumber(cardNumber) + " �������������.");
				return false;

			case -3:
				log.trace("�������� ��� �� ����� " + hideCardNumber(cardNumber) + " ������������� �� ��������. " +
						"����������� ���������� �������������� ���");
				return true;

			case -4:
				log.info("�������� ��� �� ����� " + hideCardNumber(cardNumber) + " ������������� ������.");
				return false;

			case -5:
				log.trace("����� " + hideCardNumber(cardNumber) + " ���������� � ���������� ������. " +
						"����������� ���������� �������������� ���");
				return true;

			case -128:
			case -129:
				log.info("����� " + hideCardNumber(cardNumber) + " �� ���������� � ��. " +
						"�������� ��� �� ����� �� ���������");
				return false;

			default:
				log.warn("��������� ���� ������ ����������� ���: " + rc + ". " +
						"�������� ��� �� ����� " + hideCardNumber(cardNumber) + " �� ��������");
				return false;
		}
	}

	public boolean isConnectionLogEnabled()
	{
		return false;
	}
}
