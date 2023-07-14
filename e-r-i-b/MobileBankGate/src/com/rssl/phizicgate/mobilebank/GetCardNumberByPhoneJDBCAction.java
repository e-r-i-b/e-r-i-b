package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * User: Moshenko
 * Date: 25.01.2012
 * Time: 15:51:05
 * Получаем номер карты 
 */
public class GetCardNumberByPhoneJDBCAction extends JDBCActionBase<String>
{
	private final String phone;
	private final String terBank;
	private final String branch;
	private static final String MB_WWW_GET_MOBILE_PAYMENT_CARD= "mb_WWW_GetMobilePaymentCard";

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	public GetCardNumberByPhoneJDBCAction(String phone,String terBank, String branch)
	{
		this.phone = phone;
		this.terBank = terBank;
		this.branch = branch;
	}

	public String doExecute(Connection con) throws SQLException,GateException
	{
		CallableStatement cstmt =
				con.prepareCall("{? = call " + MB_WWW_GET_MOBILE_PAYMENT_CARD  + " " +
						"(@phone  = ?, " +
						"@terbankCode  = ?, " +
						"@branch  = ?, " +
						"@card  = ?, " +
						"@clientName  = ?, " +
						"@error  = ?," +
						"@comment = ? )}");

		LogThreadContext.setProcName(MB_WWW_GET_MOBILE_PAYMENT_CARD);
		try {

			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setString(2, phone);

			if (StringHelper.isEmpty(terBank))
				cstmt.setNull(3, Types.TINYINT);
			else
				cstmt.setByte(3, new Byte(terBank));

			if (StringHelper.isEmpty(branch))
				cstmt.setNull(4, Types.SMALLINT);
			else
				cstmt.setShort(4, new Short(branch));

			cstmt.registerOutParameter(5, Types.VARCHAR); // cardNumber
			cstmt.registerOutParameter(6, Types.VARCHAR); //clientName
			cstmt.registerOutParameter(7, Types.INTEGER); // error
			cstmt.registerOutParameter(8, Types.VARCHAR); //comment
			cstmt.execute();

			int rc = cstmt.getInt(1);
			int error = cstmt.getInt(7);
			String comment = cstmt.getString(8);

			return  testReturnCode(rc,error,comment,cstmt.getString(5));

		} finally {
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}

	/**
	 * анализ кода возврата
	 * @param rc
	 * @param error
	 * @param comment
	 * @param cardNumber
	 * @return Возвращаем номер карты, либо null в случаи ошибки
	 * @throws SQLException
	 */
	private String testReturnCode(int rc,int error,String comment,String cardNumber) throws SQLException, GateException
	{
		StandInUtils.registerMBOfflineEvent(rc);
		StandInUtils.checkStandInAndThrow(rc);
		switch (rc) {
			case 0: // all correct
				return cardNumber;
			case -1:
			{
			log.error("При попытки получения номера карты по номеру телефона: " + phone+
					" , из мобильного банка  вернулось сообщение об ошибке." +
					"Код ошибки: "+ error + "Сообщение: " + comment);

				return null;
			}
			case -10:
				throw new SQLException("SQL ошибка");
			default:
				throw new SQLException("неизвестный код возврата (" + rc + ")");
		}
	}
}
