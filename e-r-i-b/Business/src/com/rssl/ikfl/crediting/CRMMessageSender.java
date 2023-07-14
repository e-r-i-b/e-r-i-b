package com.rssl.ikfl.crediting;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.common.types.annotation.Stateless;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.EtsmConfig;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.JMSQueueMessageSender;

import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 * @author Erkin
 * @ created 01.01.2015
 * @ $Author$
 * @ $Revision$
 */
@Stateless
public class CRMMessageSender
{
	/**
	 * Отправить сообщение [из ЕРИБ] в CRM (для предложений!)
	 * @param message - текст сообщения (never null nor empty)
	 */
	public void sendMessageToCRMForOffers(CRMMessage message) throws NamingException, JMSException
	{
		CreditingConfig creditingConfig = ConfigFactory.getConfig(CreditingConfig.class);
		String crmQueueName = creditingConfig.getEribToCrmQueueName();
		String crmQCFName = creditingConfig.getConnectionFactoryName();

		sendMessage(message.getMessage(), crmQueueName, crmQCFName);
	}

	/**
	 * Отправить сообщение [из ЕРИБ] в CRM (для кредитных заявок!)
	 * @param message - текст сообщения (never null nor empty)
	 */
	public void sendMessageToCRMForLoanClaim(CRMMessage message) throws NamingException, JMSException
	{
		EtsmConfig etsmConfig = ConfigFactory.getConfig(EtsmConfig.class);
		String queueName = etsmConfig.getEsbCreditQueueName();
		String qcfName = etsmConfig.getEsbCreditQCFName();

		sendMessage(message.getMessage(), queueName, qcfName);
	}

	/**
	 * Отправить сообщение [из заглушки CRM] в ЕРИБ(для предложений!)
	 * @param messageText - текст сообщения (never null nor empty)
	 */
	public void sendMessageToERIBForOffers(String messageText) throws NamingException, JMSException
	{
		CreditingConfig creditingConfig = ConfigFactory.getConfig(CreditingConfig.class);
		String eribQueueName = creditingConfig.getCrmToEribQueueName();
		String eribQCFName = creditingConfig.getConnectionFactoryName();

		sendMessage(messageText, eribQueueName, eribQCFName);
	}

	/**
	 * Отправить сообщение [из заглушки CRM] в ЕРИБ (для кредитных заявок!)
	 * @param messageText - текст сообщения (never null nor empty)
	 */
	public void sendMessageToERIBForLoanClaim(String messageText) throws NamingException, JMSException
	{
		LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
		String loanClaimBackQueueName = loanClaimConfig.getEsbCreditBackQueueName();
		String loanClaimBackQCFName = loanClaimConfig.getEsbCreditBackQCFName();

		sendMessage(messageText, loanClaimBackQueueName, loanClaimBackQCFName);
	}

	private void sendMessage(String messageText, String queueName, String qcfName) throws NamingException, JMSException
	{
		ApplicationAutoRefreshConfig appConfig = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class);
		String jmsGroupID = appConfig.getGroupId();

		JMSQueueMessageSender messageSender = new JMSQueueMessageSender(queueName, qcfName);
		try
		{
			if (StringHelper.isNotEmpty(jmsGroupID))
				messageSender.setJMSXGroupID(jmsGroupID);

			messageSender.sendTextMessage(messageText);
		}
		finally
		{
            messageSender.close();
		}
	}
}
