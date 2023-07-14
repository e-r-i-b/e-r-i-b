package com.rssl.phizic.test.way4.actions;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.dataaccess.jdbc.JDBCAction;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.utils.DateHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Акшн вызова хранимой процедуры mb_MockGetClientByLoginPassword для поиска клиента
 * Исключительно для ЗАГЛУШКИ!!!
 * @author niculichev
 * @ created 24.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class MockIPasGetClientByLoginPasswordJDBCAction implements JDBCAction<Pair<Integer, UserInfo>>
{
	private String userId;
	private String password;

	public static final int DATA_NOT_FOUND_RET_CODE    = -100;
	public static final int INVALID_PASSWORD_RET_CODE  = -200;
	public static final int VALID_RET_CODE  = 0;

	public MockIPasGetClientByLoginPasswordJDBCAction(String userId, String password)
	{
		this.userId = userId;
		this.password = password;
	}

	public Pair<Integer, UserInfo> execute(Connection con) throws SQLException, SystemException
	{
		CallableStatement cstmt = con.prepareCall("{? = call mb_MockGetClientByLoginPassword  " +
				"(@AuthIdt = ?, " +     //VARCHAR(10) - логин СБОЛ
				"@InputPassword = ?, " +    //VARCHAR(65) - пароль СБОЛ
				"@FirstName = ?, " +   //VARCHAR(100) OUTPUT - имя
				"@FathersName = ?, " + //VARCHAR(100) OUTPUT - отчество
				"@LastName = ?, " +    //VARCHAR(100) OUTPUT - фамилия
				"@RegNumber = ?, " +   //VARCHAR(20) OUTPUT - паспортные данные
				"@BirthDate = ?, " +   //DATE OUTPUT - дата рождения
				"@CbCode = ?, " +      //VARCHAR(20) OUTPUT - тербанк в котором выдана карта (полный формат)
				"@CardNumber = ?, " +  //VARCHAR(20) OUTPUT - номер карты
				"@strErrDescr = ? )}"    //VARCHAR(500) OUTPUT - описание ошибки
		);

		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setString(2, userId);                   // AuthIdt
			cstmt.setString(3, password);                 // Password
			cstmt.registerOutParameter(4, Types.VARCHAR); // FirstName
			cstmt.registerOutParameter(5, Types.VARCHAR); // FathersName
			cstmt.registerOutParameter(6, Types.VARCHAR); // LastName
			cstmt.registerOutParameter(7, Types.VARCHAR); // RegNumber
			cstmt.registerOutParameter(8, Types.DATE);    // BirthDate
			cstmt.registerOutParameter(9, Types.VARCHAR); // CbCode
			cstmt.registerOutParameter(10, Types.VARCHAR);// CardNumber
			cstmt.registerOutParameter(11, Types.VARCHAR);// strErrDescr
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

	private Pair<Integer, UserInfo> processResult(CallableStatement cstmt) throws SQLException, SystemException
	{
		int execCode = cstmt.getInt(1);

		if (execCode == DATA_NOT_FOUND_RET_CODE	|| execCode == INVALID_PASSWORD_RET_CODE)
		{
			return new Pair<Integer, UserInfo>(execCode, null);
		}

		if (cstmt.getInt(1) != VALID_RET_CODE)
		{
			throw new SystemException(cstmt.getString(11));
		}

		UserInfoImpl result = new UserInfoImpl();

		result.setFirstname(cstmt.getString(4));
		result.setPatrname(cstmt.getString(5));
		result.setSurname(cstmt.getString(6));
		result.setPassport(cstmt.getString(7));
		result.setBirthdate(
				DateHelper.toCalendar(cstmt.getDate(8)));
		result.setCbCode(cstmt.getString(9));
		result.setCardNumber(cstmt.getString(10));

		result.setUserId(userId);

		return new Pair<Integer, UserInfo>(VALID_RET_CODE, result);
	}

	public boolean isConnectionLogEnabled()
	{
		return true;
	}
}
