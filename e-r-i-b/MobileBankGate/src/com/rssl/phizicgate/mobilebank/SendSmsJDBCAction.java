package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.StandInExternalSystemException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.source.LogableCallableStatement;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Erkin
 * @ created 20.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class SendSmsJDBCAction extends SendSmsActionBase<Void>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private final String phone;
	private final String mbSystemId;
	private static final String MB_ESB_SEND_SMS = "mb_ESB_SendSMS";

	public SendSmsJDBCAction(String phone, String text, String textToLog,  int smsID, String mbSystemId) throws GateException
	{
		super(text, textToLog, smsID);
		this.phone = phone;
		this.mbSystemId = mbSystemId;
	}

	public Void doExecute(Connection con) throws SQLException, SystemException
	{
		CallableStatement cstmt =
				con.prepareCall("{? = call " + MB_ESB_SEND_SMS +"(" +
						"@iExternalSystemID = ?, " +
						"@iMobileOperatorID = ?, " +
						"@strPhoneNumber    = ?, " +
						"@SMSID             = ?, " +
						"@vbMessageBody     = ?)}"
				);

		LogThreadContext.setProcName(MB_ESB_SEND_SMS);
		try {
			cstmt.registerOutParameter(1, Types.INTEGER);
			if (StringHelper.isEmpty(mbSystemId))
				cstmt.setInt(2, Integer.parseInt(ConfigFactory.getConfig(MobileBankConfig.class).getMbSystemId()));
			else
				cstmt.setInt(2, Integer.parseInt(mbSystemId));
			cstmt.setInt(3, MobileBankConfig.getMobileOperatorId());
			cstmt.setString(4, phone);
			cstmt.setInt(5, getSmsID());
			if (!StringHelper.isEmpty(getTextToLog()) && cstmt instanceof LogableCallableStatement)
			{
				LogableCallableStatement lcstmt = (LogableCallableStatement) cstmt;
				lcstmt.setBytes(6, getText().getBytes(),getTextToLog());
			}
			else
				cstmt.setBytes(6, getText().getBytes());

			LogThreadContext.setProcName(MB_ESB_SEND_SMS);
			cstmt.execute();
			// анализ кода возврата
			int rc = cstmt.getInt(1);
			StandInUtils.registerMBOfflineEvent(rc);
			SendSmsUtils.testReturnCode(rc);

			return null;
		}
		catch (StandInExternalSystemException e)
		{
			//В режиме Stand-In отправлять через MFM
			try
			{
				mfmService.sendSMSWithoutIMSI(phone,getText(), 2L);
				return null;
			}
			catch (GateException ge)
			{
				log.error(ge);
				throw e;
			}
		}
		finally
		{
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}

	@Override
	protected Void processInactiveException(InactiveExternalSystemException e)
	{
		try
		{
			mfmService.sendSMSWithoutIMSI(phone,getText(), 2L);
			return null;
		}
		catch (GateException ge)
		{
			log.error(ge);
			throw e;
		}
	}
}
