package com.rssl.phizicgate.mobilebank.csa;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizicgate.mobilebank.JDBCActionBase;
import com.rssl.phizicgate.mobilebank.StandInUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * ����, ���������� �� ��� �������������� ������ ������� ipas, ������������� � ����
 * @author Rtischeva
 * @ created 25.12.14
 * @ $Author$
 * @ $Revision$
 */
public class ChangePassByCardNumberForErmbClientJDBCAction extends JDBCActionBase<Boolean>
{
	private String cardNumber;

	private String phoneNumber;

	private static final String MB_WWW_SEND_PASS_BY_CARD_NUMBER_ERMB= "mb_WWW_SendPassByCardNumber_ERMB";

	public ChangePassByCardNumberForErmbClientJDBCAction(String cardNumber, String phoneNumber)
	{
		this.cardNumber = cardNumber;
		this.phoneNumber = phoneNumber;
	}

	@Override
	protected Boolean doExecute(Connection con) throws SQLException, SystemException
	{

		CallableStatement cstmt = con.prepareCall("{? = call " + MB_WWW_SEND_PASS_BY_CARD_NUMBER_ERMB +"  " +
				"(@card = ?, " +    //VARCHAR(20) - ����� ����� ������� ����, � ������� �������� iPAS �����
				"@phone = ? )}"     //VARCHAR(20) - ����� ��������, �� ������� ���������� ������� ��� ��� ������ ����� �������
		);

		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, cardNumber);
			cstmt.setString(3, phoneNumber);
			cstmt.execute();
			int rc = cstmt.getInt(1);
			StandInUtils.checkStandInAndThrow(rc);
			StandInUtils.registerMBOfflineEvent(rc);
			if (rc != 0)
			{
				throw new SQLException("�� ������� ������������� ������ ��� ��������� �������.");
			}
			return true;
		}
		finally
		{
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}
}
