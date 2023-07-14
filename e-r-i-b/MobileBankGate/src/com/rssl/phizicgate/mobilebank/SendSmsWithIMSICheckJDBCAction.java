package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mfm.MFMService;
import com.rssl.phizic.gate.mobilebank.ImsiCheckResult;
import com.rssl.phizic.gate.mobilebank.MessageInfo;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.source.LogableCallableStatement;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizgate.mobilebank.cache.techbreak.ImsiCheckResultCacheEntry;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Отправка сообщения с проверкой IMSI
 * @author gladishev
 * @ created 18.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class SendSmsWithIMSICheckJDBCAction extends CachedJDBCAction<Integer, ImsiCheckResultCacheEntry>
{
	private static final String MB_IMSI_SEND_SMS = "mb_IMSI_SendSMS";

	private final String phone;
	private final String stubText;
	private final String mbSystemId;
	private final String text;
	private final String textToLog;

	private final MFMService mfmService = GateSingleton.getFactory().service(MFMService.class);
	private final MobileBankConfig config = ConfigFactory.getConfig(MobileBankConfig.class);

	public SendSmsWithIMSICheckJDBCAction(String phone, MessageInfo messageInfo, String mbSystemId) throws GateException
	{
		SendSmsUtils.smsLengthCheckAndThrow(messageInfo.getText(), messageInfo.getTextToLog());

		this.text = messageInfo.getText();
		this.textToLog = messageInfo.getTextToLog();
		this.phone = phone;
		this.mbSystemId = mbSystemId;
		this.stubText = messageInfo.getStubText();
	}

	public ImsiCheckResultCacheEntry doExecute(Connection con) throws SQLException, SystemException
	{
		CallableStatement cstmt =
				con.prepareCall("{? = call "+ MB_IMSI_SEND_SMS +"(" +
						"@strPhoneNumber    = ?, " +
						"@iExternalSystemID = ?, " +
						"@strSMS = ?, " +
						"@strStubSMS = ?, " +
						"@iMobileOperator = ?, " +
						"@iMessageID     = ?)}"
				);

			LogThreadContext.setProcName(MB_IMSI_SEND_SMS);
		try {
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, phone);        //strPhoneNumber
		    if (StringHelper.isEmpty(mbSystemId))
				cstmt.setInt(3, Integer.parseInt(ConfigFactory.getConfig(MobileBankConfig.class).getMbSystemId()));       //iExternalSystemID
			else
				cstmt.setInt(3, Integer.parseInt(mbSystemId));
			if (!StringHelper.isEmpty(textToLog) && cstmt instanceof LogableCallableStatement)
			{
				LogableCallableStatement lcstmt = (LogableCallableStatement) cstmt;
				lcstmt.setString(4, text, textToLog);
			}
			else
				cstmt.setString(4, text);    //strSMS

			cstmt.setString(5, stubText);  //stubText
			cstmt.setInt(6, MobileBankConfig.getMobileOperatorId());  //iMobileOperator
			cstmt.registerOutParameter(7, Types.INTEGER); //iMobileOperator
			cstmt.execute();

			// анализ кода возврата
			int rc = cstmt.getInt(1);
			StandInUtils.registerMBOfflineEvent(rc);
			SendSmsUtils.testReturnCode(rc);

			ImsiCheckResultCacheEntry result = new ImsiCheckResultCacheEntry();
			result.setPhoneNumber(phone);
			result.setMessageId(cstmt.getInt(7));
			return result;
		}
		finally
		{
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}

	@Override
	protected Integer processResult(ImsiCheckResultCacheEntry value) throws SystemException
	{
		return value.getMessageId();
	}

	//При обработке тех. перерыва происходит отправка смс в обход МБК.
	//При неудачной предыдущей попытке отправляется заглушечное смс.
	@Override
	protected ImsiCheckResultCacheEntry getDbCached() throws SystemException
	{
		ImsiCheckResultCacheEntry lastMessage= cacheService.getImsiCheckResultCacheEntry(phone);
		if (lastMessage == null)
		{
			return null;
		}

		boolean lastSent = ImsiCheckResult.send == SendSmsUtils.getImsiResult(lastMessage.getValidationResult());
		if (lastSent)
		{
			//предыдущая отправка успешна - отправить смс
			mfmService.sendSMSWithoutIMSI(phone, text, 2L);
		}
		else
		{
			//предыдущая отправка неуспешна - отправить заглушечное смс
			String smsText = StringHelper.isEmpty(stubText) ? config.getImsiFailStubText() : stubText;
			mfmService.sendSMSWithoutIMSI(phone, smsText, 2L);
		}

		return lastMessage;
	}

	@Override
	protected ImsiCheckResultCacheEntry getAppServerCached() throws SystemException
	{
		//отправка смс не берется из кеша без техперерывов
		return null;
	}
}
