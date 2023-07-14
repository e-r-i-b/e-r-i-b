package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mfm.MFMService;

/**
 * @author Erkin
 * @ created 08.11.2010
 * @ $Author$
 * @ $Revision$
 */
abstract class SendSmsActionBase<ReturnType> extends JDBCActionBase<ReturnType>
{
	protected final MFMService mfmService = GateSingleton.getFactory().service(MFMService.class);

	private final String text;
	private final String textToLog;
	private final int smsID;

	///////////////////////////////////////////////////////////////////////////

	protected SendSmsActionBase(String text, String textToLog, int smsID) throws GateException
	{
		SendSmsUtils.smsLengthCheckAndThrow(text, textToLog);

		MobileBankConfig config = ConfigFactory.getConfig(MobileBankConfig.class);

		this.text = text;
		this.textToLog = textToLog;
		if (config.getMaxSMSID() > 0)
			this.smsID = 1 + smsID % config.getMaxSMSID();
		else this.smsID = smsID;
	}

	protected String getText()
	{
		return text;
	}

	public String getTextToLog()
	{
		return textToLog;
	}

	protected int getSmsID()
	{
		return smsID;
	}
}
