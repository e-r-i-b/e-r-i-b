package com.rssl.phizic.operations.basket;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessage;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessagesService;
import com.rssl.phizic.operations.OperationBase;

/**
 * �������� �������������� �������������� �������
 *
 * @author muhin
 * @ created 25.07.15
 * @ $Author$
 * @ $Revision$
 */
public class EditBasketInfoOperation extends OperationBase
{
	private StaticMessage messageWith;
	private StaticMessage messageWithout;
	private static final StaticMessagesService service = new StaticMessagesService();
	private static final String BASKET_MESSAGE_WITH_KEY_PREFIX = "basket_message_with_";
	private static final String BASKET_MESSAGE_WITHOUT_KEY_PREFIX = "basket_message_without_";
	private String defaultMessageWithoutRC = "�������� ������� ��������. �� ����� ������������� ��������� ������� ������������� �� �� ������� ����Ļ � ���������� ����� � ������� ������� � ��������. �������� ������������� ������������� � �� ������ ��������� ������� �������, ����������� ������������ ���";
	private String defaultMessageWithoutDL = "�������� ������� ��������. �� ����� ������������� ��������� ������� ������������� �� �� ������� ����Ļ � ���������� ����� � ������� ������� � ��������. �������� �������������� � ����������� ������������� �������� � �� ������ ��������� ������� ������� �� ������������ �������� � ������, ��������������� �������� �������������";
	private String defaultMessageWithRC = "�������� ������� ��������. �� ����� ������������� ��������� ������� ������������� �� �� ������� ����Ļ � ���������� ����� � ������� ������� � ��������";
	private String defaultMessageWithDL = "�������� ������� ��������. �� ����� ������������� ��������� ������� ������������� �� �� ������� ����Ļ � ���������� ����� � ������� ������� � ��������";
	private String defaultMessageWithINN = "�������� ������� ��������. �� ����� ������������� ��������� ������� ������������ ������������� �� �� ������ � ������ ������� ��ѻ � ���������� ����� ��� ������ � ������� �������� � �������.";


	public void initialize() throws BusinessException, BusinessLogicException
	{
		try
		{
			messageWith = service.findByKey(BASKET_MESSAGE_WITH_KEY_PREFIX);
			messageWithout = service.findByKey(BASKET_MESSAGE_WITHOUT_KEY_PREFIX);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}

	private String getDefaultMessageWithText(String typeDoc)
	{
		if ("RC".equals(typeDoc))
			return defaultMessageWithRC;
		else if ("DL".equals(typeDoc))
			return defaultMessageWithDL;
		else if ("INN".equals(typeDoc))
			return defaultMessageWithINN;
		else
			return "";
	}

	private String getDefaultMessageWithoutText(String typeDoc)
	{
		if ("RC".equals(typeDoc))
			return defaultMessageWithoutRC;
		else if ("DL".equals(typeDoc))
			return defaultMessageWithoutDL;
		else
			return "";
	}


	public String getMessageWithText(String typeDoc)
	{
		try
		{
			StaticMessage message = service.findByKey(BASKET_MESSAGE_WITH_KEY_PREFIX + typeDoc);
			if (message != null)
				return message.getText();
			return getDefaultMessageWithText(typeDoc);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
		return getDefaultMessageWithText(typeDoc);
	}

	public String getMessageWithoutText(String typeDoc)
	{
		try
		{
			StaticMessage message = service.findByKey(BASKET_MESSAGE_WITHOUT_KEY_PREFIX + typeDoc);
			if (message != null)
				return message.getText();
			return  getDefaultMessageWithoutText(typeDoc);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
		return getDefaultMessageWithoutText(typeDoc);
	}

	public void saveMessage(String messageWithText, String messageWithoutText, String typeDoc)
	{
		try
		{
			messageWith = service.findByKey(BASKET_MESSAGE_WITH_KEY_PREFIX  + typeDoc);
			if (messageWith == null)
				messageWith = new StaticMessage(BASKET_MESSAGE_WITH_KEY_PREFIX  + typeDoc, messageWithText);
			else
				messageWith.setText(messageWithText);
			service.addOrUpdate(messageWith);
			if (!"INN".equals(typeDoc))
			{
				messageWithout = service.findByKey(BASKET_MESSAGE_WITHOUT_KEY_PREFIX  + typeDoc);
				if (messageWithout == null)
					messageWithout = new StaticMessage(BASKET_MESSAGE_WITHOUT_KEY_PREFIX  + typeDoc, messageWithoutText);
				else
					messageWithout.setText(messageWithoutText);
				service.addOrUpdate(messageWithout);
			}
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}
}
