package com.rssl.phizic.limits.ejb;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.limits.exceptions.TransactionNotFoundException;
import com.rssl.phizic.limits.exceptions.UnknownRequestException;
import com.rssl.phizic.limits.handlers.RequestRouter;
import com.rssl.phizic.limits.handlers.TransactionProcessor;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.service.StartupServiceDictionary;
import com.rssl.phizic.utils.ValidateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author osminin
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Слушатель очереди
 */
public class LimitsAppListener implements MessageDrivenBean, MessageListener
{
	private static final org.apache.commons.logging.Log log = LogFactory.getLog(LogModule.Web.toString());

	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException
	{
	}

	public void ejbRemove() throws EJBException
	{
	}

	/**
	 * Создание бина
	 * @throws EJBException
	 */
	public void ejbCreate() throws EJBException
	{
	}

	public void onMessage(Message message)
	{
		ApplicationInfo.setCurrentApplication(Application.LimitsApp);
		try
		{
			TextMessage textMessage = (TextMessage) message;
			Document document = XmlHelper.parse(textMessage.getText());
			TransactionProcessor processor = RequestRouter.getProcessor(document.getDocumentElement().getTagName());

			processor.process(document);
		}
		catch (ValidateException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (UnknownRequestException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (TransactionNotFoundException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (Exception e)
		{
			log.error("Ошибка обработки сообщения.", e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}
}
