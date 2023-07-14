package com.rssl.phizic.test.way4.actions;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.jdbc.JDBCAction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Types;

/**
 * Акшн вызова хранимой процедуры mb_MockChangeIPasPasswordByLoginPassword для изменения пароля у клиента по логину и паролю
 * Исключительно для ЗАГЛУШКИ!!!
 * @author niculichev
 * @ created 24.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class MockChangeIPasPasswordByLoginPassword implements JDBCAction<Pair<Integer, String>>
{
	private String userId;
	private String password;

	public static final int DATA_NOT_FOUND_RET_CODE    = -100;
	public static final int INVALID_PASSWORD_RET_CODE  = -200;
	public static final int VALID_RET_CODE  = 0;

	public MockChangeIPasPasswordByLoginPassword(String userId, String password)
	{
		this.userId = userId;
		this.password = password;
	}

	public Pair<Integer, String> execute(Connection con) throws SQLException, SystemException
	{
		CallableStatement cstmt = con.prepareCall("{? = call mb_MockChangeIPasPasswordByLoginPassword  (" +
				"@AuthIdt = ?, " +          //VARCHAR(10) - логин СБОЛ
				"@InputPassword = ?, " +    //VARCHAR(65) - пароль СБОЛ
				"@newPassword = ?, "  +     //VARCHAR(100) OUTPUT - новый пароль
				"@strErrDescr = ? )}"         //VARCHAR(500) OUTPUT - описание ошибки
		);

		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setString(2, userId);                   // AuthIdt
			cstmt.setString(3, password);                 // Password
			cstmt.registerOutParameter(4, Types.VARCHAR); // NewPassword
			cstmt.registerOutParameter(5, Types.VARCHAR); // strErrDescr
			cstmt.execute();

			return processResult(cstmt);
		}
		finally
		{
			if (cstmt != null)
			{
				try
				{
					cstmt.close();
				}
				catch (SQLException ignored)
				{
				}
			}
		}
	}

	private Pair<Integer, String> processResult(CallableStatement cstmt) throws SQLException, SystemException
	{
		int execCode = cstmt.getInt(1);

		if (execCode == DATA_NOT_FOUND_RET_CODE	|| execCode == INVALID_PASSWORD_RET_CODE)
		{
			return new Pair<Integer, String>(execCode, null);
		}

		if (cstmt.getInt(1) != VALID_RET_CODE)
		{
			throw new SystemException(cstmt.getString(3));
		}

		return new Pair<Integer, String>(VALID_RET_CODE, cstmt.getString(4));
	}

	public boolean isConnectionLogEnabled()
	{
		return true;
	}
}
