package com.rssl.phizicgate.mobilebank.csa;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizicgate.mobilebank.JDBCActionBase;
import com.rssl.phizicgate.mobilebank.StandInUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Random;

/**
 * @author vagin
 * @ created 20.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class ChangePassByCardNumberJDBCAction extends JDBCActionBase<Boolean>
{
	private String cardNumber;

	private static final String MB_WWW_SEND_PASS_BY_CARD_NUMBER= "mb_WWW_SendPassByCardNumber";

	public ChangePassByCardNumberJDBCAction(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public Boolean doExecute(Connection con) throws SQLException, SystemException
	{
		Random rand = new Random();
		CallableStatement cstmt = con.prepareCall("{? = call " + MB_WWW_SEND_PASS_BY_CARD_NUMBER +"  " +
				"(@requestId = ?, " +    //VARCHAR(100) OUTPUT - имя
				"@card = ? )}"            //VARCHAR(20)  --номер карты
		);

		LogThreadContext.setProcName(MB_WWW_SEND_PASS_BY_CARD_NUMBER);
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setInt(2, rand.nextInt(1000000));       // Id запроса - генерируется случайно
			cstmt.setString(3, cardNumber);               // CardNumber
			cstmt.execute();
			int rc = cstmt.getInt(1);
			StandInUtils.registerMBOfflineEvent(rc);
			StandInUtils.checkStandInAndThrow(rc);

			if (rc != 0)
			{
				throw new SQLException("Не удалось сгенерировать пароль для заданного клиента.");
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
