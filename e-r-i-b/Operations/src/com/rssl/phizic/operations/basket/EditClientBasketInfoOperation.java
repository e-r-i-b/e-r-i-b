package com.rssl.phizic.operations.basket;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessage;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessagesService;
import com.rssl.phizic.operations.OperationBase;

/**
 *  Операция редактирования в АРМ информирующих клиента текстов по использованию корзины платежей
 *
 * @author muhin
 * @ created 25.07.15
 * @ $Author$
 * @ $Revision$
 */
public class EditClientBasketInfoOperation extends OperationBase
{
	private StaticMessage message;
	private static final StaticMessagesService service = new StaticMessagesService();
	private static final String BASKET_MESSAGE_KEY = "clientinfo_message";
	private String defaultMessage = "Мы будем автоматически проверять наличие задолжености по данному ПУ и показывать счета для оплаты в разделе переводы и платежи.";

	public void initialize() throws BusinessException, BusinessLogicException
	{
		try
		{
			message = service.findByKey(BASKET_MESSAGE_KEY);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}

	public String getMessageText()
	{
		if (message != null)
			return message.getText();
		return  defaultMessage;
	}

	public void saveMessage(String messageText)
	{

		try
		{
			message = service.findByKey(BASKET_MESSAGE_KEY);

			if (message == null)
				message = new StaticMessage(BASKET_MESSAGE_KEY, messageText);
			else
				message.setText(messageText);
			service.addOrUpdate(message);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}
}
