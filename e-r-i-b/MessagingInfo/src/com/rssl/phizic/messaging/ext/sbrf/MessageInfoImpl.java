package com.rssl.phizic.messaging.ext.sbrf;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.mobilebank.MessageInfo;
import com.rssl.phizic.messaging.mobilebank.MobileBankSmsConfig;
import com.rssl.phizic.utils.StringUtils;

/**
 * @author osminin
 * @ created 15.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class MessageInfoImpl implements MessageInfo
{
	private String text;
	private String textToLog;
	private String stubText;

	public MessageInfoImpl()
	{}

	public MessageInfoImpl(String text, String textToLog)
	{
		MobileBankSmsConfig mobileBankSmsConfig = ConfigFactory.getConfig(MobileBankSmsConfig.class);
		this.text = mobileBankSmsConfig.isSmsTranslit() ? StringUtils.translit(text) : text;
		this.textToLog = textToLog;
	}

	public MessageInfoImpl(String text, String textToLog, String stubText)
	{
		this(text, textToLog);
		this.stubText = stubText;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getTextToLog()
	{
		return textToLog;
	}

	public void setTextToLog(String textToLog)
	{
		this.textToLog = textToLog;
	}

	public String getStubText()
	{
		return stubText;
	}

	public void setStubText(String stubText)
	{
		this.stubText = stubText;
	}
}
