package com.rssl.phizicgate.sbrf.ws.listener;

import com.rssl.phizicgate.sbrf.ws.CODMessageData;

/**
 * @author Omeliyanchuk
 * @ created 09.02.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� ����������� ������������ ��� ������ � ����������
 */
public class InternalMessageInfoContainer 
{
	private String link;// id �������� ���������� � ����������
	private String messageTag;//��� ���������� ���������
	private String errorCode;//��� ������
	private String errorText;//��������� �� ������
	private CODMessageData message;//��������� �������������

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public String getMessageTag()
	{
		return messageTag;
	}

	public void setMessageTag(String messageTag)
	{
		this.messageTag = messageTag;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorText(String errorText)
	{
		this.errorText = errorText;
	}

	public String getErrorText()
	{
		return errorText;
	}

	public void setMessage(CODMessageData message)
	{
		this.message = message;
	}

	public CODMessageData getMessage()
	{
		return message;
	}
}
