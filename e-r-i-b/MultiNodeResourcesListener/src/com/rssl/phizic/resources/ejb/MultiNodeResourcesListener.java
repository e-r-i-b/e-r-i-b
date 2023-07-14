package com.rssl.phizic.resources.ejb;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.mail.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.resources.ejb.mail.IncomeMailListProcessor;
import com.rssl.phizic.resources.ejb.mail.OutcomeMailListProcessor;
import com.rssl.phizic.resources.ejb.mail.RemovedMailListProcessor;
import com.rssl.phizic.resources.ejb.mail.statistics.MailAverageTimeProcessor;
import com.rssl.phizic.resources.ejb.mail.statistics.MailEmployeeStatisticProcessor;
import com.rssl.phizic.resources.ejb.mail.statistics.MailFirstDateProcessor;
import com.rssl.phizic.resources.ejb.mail.statistics.MailStatisticsProcessor;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author mihaylov
 * @ created 22.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Листенер сообщений о получении данных конкретного блока. После получения сообщения ответные данные отправляем в другую очередь.
 */
public class MultiNodeResourcesListener implements MessageDrivenBean, MessageListener
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final Map<String,MultiNodeProcessorBase> processors = new HashMap<String,MultiNodeProcessorBase>();

	static
	{
		processors.put(Constants.GET_INCOME_MAIL_LIST_REQUEST_NAME,new IncomeMailListProcessor());
		processors.put(Constants.GET_OUTCOME_MAIL_LIST_REQUEST_NAME,new OutcomeMailListProcessor());
		processors.put(Constants.GET_REMOVED_MAIL_LIST_REQUEST_NAME,new RemovedMailListProcessor());
		processors.put(Constants.GET_MAIL_STATISTICS_REQUEST_NAME,new MailStatisticsProcessor());
		processors.put(Constants.GET_MAIL_AVERAGE_TIME_REQUEST_NAME,new MailAverageTimeProcessor());
		processors.put(Constants.GET_MAIL_FIRST_DATE_REQUEST_NAME,new MailFirstDateProcessor());
		processors.put(Constants.GET_MAIL_FIRST_DATE_REQUEST_NAME,new MailFirstDateProcessor());
		processors.put(Constants.GET_MAIL_EMPLOYEE_STATISTICS_REQUEST_NAME,new MailEmployeeStatisticProcessor());
	}


	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException
	{
	}

	public void ejbRemove() throws EJBException
	{
	}

	public void onMessage(Message message)
	{
		try
		{
			ApplicationInfo.setCurrentApplication(Application.Listener);
			MultiNodeProcessorBase processor = processors.get(message.getJMSType());
			if(processor != null)
				processor.process(((TextMessage)message).getText(),message.getJMSMessageID());
			else
				log.error("Не найден обработчик для запроса с типом " + message.getJMSType());

		}
		catch (JMSException e)
		{
			log.error("Ошибка обработки сообщения", e);
		}
		catch (SystemException e)
		{
			log.error("Ошибка обработки сообщения", e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}




}
