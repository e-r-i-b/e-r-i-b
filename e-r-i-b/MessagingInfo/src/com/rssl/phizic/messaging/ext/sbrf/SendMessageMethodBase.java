package com.rssl.phizic.messaging.ext.sbrf;

import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.messaging.mail.SmsTransportService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 09.11.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class SendMessageMethodBase implements SendMessageMethod
{

	private final TranslitMode translit;
	private Map<String, SendMessageError> errorInfo = new HashMap<String, SendMessageError>();

	///////////////////////////////////////////////////////////////////////////

	protected SendMessageMethodBase(TranslitMode translit)
	{
		this.translit = translit;
	}

	public TranslitMode getTranslit()
	{
		return translit;
	}

	public Map<String, SendMessageError> getErrorInfo()
	{
		return errorInfo;
	}

	protected void addErrorInfo(String info, SendMessageError error)
	{
		errorInfo.put(info, error);
	}

	public boolean imsiResult()
	{
		return true;
	}
}
