package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.source.LogableCallableStatement;
import com.rssl.phizic.utils.StringHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Экшн для отправки кллиенту смс-сообщения с описанием заказа и возможностью оплатить его.
 *
 * @author bogdanov
 * @ created 24.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class OfferSendMessageJDBCAction extends SendSmsActionBase<Void>
{
	private static final String MB_OFFER_SEND_MESSAGE = "mb_Offer_SendMessage";

	private final String phone;
	private final String mbSystemId;
	private final Long messageId;

	public OfferSendMessageJDBCAction(String phone, String text, String textToLog,  Long messageId, String mbSystemId) throws GateException
	{
		super(text, textToLog, messageId.intValue());
		this.messageId = messageId;
		this.phone = phone;
		this.mbSystemId = mbSystemId;
	}

	public Void doExecute(Connection con) throws SQLException, SystemException
	{
		CallableStatement cstmt =
				con.prepareCall("{? = call " + MB_OFFER_SEND_MESSAGE +"(" +
						"@ExternalSystemID = ?, \n" +
						"@MessageID = ?, \n" +
						"@PhoneNumber = ?, \n" +
						"@MessageText = ?)}"
				);

		LogThreadContext.setProcName(MB_OFFER_SEND_MESSAGE);
		try {
			cstmt.registerOutParameter(1, Types.INTEGER);
			if (StringHelper.isEmpty(mbSystemId))
				cstmt.setInt(2, Integer.parseInt(ConfigFactory.getConfig(MobileBankConfig.class).getMbSystemId()));
			else
				cstmt.setInt(2, Integer.parseInt(mbSystemId));
			cstmt.setLong(3, messageId);
			cstmt.setString(4, phone);
			if (!StringHelper.isEmpty(getTextToLog()) && cstmt instanceof LogableCallableStatement)
			{
				LogableCallableStatement lcstmt = (LogableCallableStatement) cstmt;
				lcstmt.setBytes(5, getText().getBytes(),getTextToLog());
			}
			else
				cstmt.setBytes(5, getText().getBytes());

			LogThreadContext.setProcName(MB_OFFER_SEND_MESSAGE);
			cstmt.execute();
			// анализ кода возврата
			int rc = cstmt.getInt(1);
			StandInUtils.registerMBOfflineEvent(rc);
			SendSmsUtils.testReturnCode(rc);

			return null;
		} finally {
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}
}
