package com.rssl.auth.csa.back.messages;

import com.rssl.phizic.gate.mobilebank.MessageInfo;

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

	public MessageInfoImpl(String text, String textToLog)
	{
		this.text = text;
		this.textToLog = textToLog;
	}

	public MessageInfoImpl(String text, String textToLog, String stubText)
	{
		this.text = text;
		this.textToLog = textToLog;
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
