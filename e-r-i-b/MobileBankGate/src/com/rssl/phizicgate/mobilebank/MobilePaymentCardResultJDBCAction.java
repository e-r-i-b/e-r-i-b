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
 * ���� ��� �������� ������� �� ������ ��� � ��������� ������� ���� �� ������ ��������
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
	 * ��������� �� ������ ����������� ����� �� ������ �������� � �������� (P2P).
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
			// ������ ���� ��������
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
			// ��. ���������� �������.
			case 0:
				break;
			// ��. ���������� �������, ������ ��� ��������� ������� ������ - ��� ��� ������� �������.
			case 1306130001:
				break;
			// ��. ��������� ���� ������� �����. ���� ������� ���������� ��� ��������.
			case 1306130002:
				break;
			// ��������. �� ���������� @MobilePaymentCardRequestID ��� �������� ���� ������� ����������.
			case 1306138881:
				throw new SQLException("�� ���������� @MobilePaymentCardRequestID ��� �������� ���� ������� ����������.");
			// ��������. ������ � ������� ������ �����.
			case 1306138882:
				throw new SQLException("������ � ������� ������ �����.");
			// ��������. ������� ResultCode ����������� ���.
			case 1306138883:
				throw new SQLException("������� ResultCode ����������� ���.");
			// ��������. ����������� ������.
			case 1306138888:
				throw new SQLException("����������� ������.");
			default:
				throw new SQLException("����������� ��� �������� (" + rc + ")");
		}
	}
}
