package com.rssl.phizic.credit;

import com.rssl.phizic.TestEjbMessage;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.utils.jms.JmsService;

import java.io.UnsupportedEncodingException;
import javax.jms.JMSException;

/**
 * @author Erkin
 * @ created 26.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ��������� �� �������� (TSM ��� ���)
 * ���������: ��������� JMS-��������� (���� -> Transact SM)
 * ������: XML �� ����� LoanApplicationRelease16.xsd  (�������� ��� ChargeLoanApplicationRq)��� LoanApplicationRelease19.xsd ��� ERIB.BKI.Schema.xsd
 */
public class EsbCreditProcessor extends MessageProcessorBase<TestEjbMessage>
{
	/**
	 * ������������ ������ ���������� ��������� JMS (� ��).
	 * ��������� ��������� JMS �� ����� ��������� 65��.
	 * ����������� ������� ������������� ����������� UTF:
	 * + � ������ ���� ������ ��������� ����� ���������
	 * (��. /java/io/DataOutputStream.java:346)
	 */
	private static final int JMS_TEXT_MESSAGE_MAX_SIZE = 65535;

	private final JmsService jmsService = new JmsService();

	private final BKIResponseBuilder bkiResponseBuilder = new BKIResponseBuilder();

	public EsbCreditProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected void doProcessMessage(TestEjbMessage xmlRequest)
	{
		try
		{
			String responseXml;

			if (xmlRequest.getChargeLoanApplicationRqRelease13() != null || xmlRequest.getChargeLoanApplicationRqRelease16() != null)
			{
				//��������� ������� ������� ����� ���-���� "/credit/loanclaim"
				return;
			}
			else if (xmlRequest.getEnquiryRequestERIB() != null)
			{
				responseXml = bkiResponseBuilder.makeResponse(xmlRequest.getEnquiryRequestERIB());
			}
			else if (xmlRequest.getCrmNewApplRq() != null)
			{
				return;
			}
			else if (xmlRequest.getEribUpdApplStatusRq() != null)
			{
				return;
			}
			else if (xmlRequest.getSearchApplicationRq() != null)
			{
				return;
			}
			else if (xmlRequest.getTicket() != null)
			{
				return;
			}
			else
			{
				log.error("����������� ��� ��������� " + xmlRequest.getClass().getSimpleName());
				return;
			}

			sendResponse(responseXml);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean writeToLog()
	{
		// �������� � ��� �� �����
		return false;
	}

	private void sendResponse(String responseXml)
	{
		LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
		String queueName = loanClaimConfig.getEsbCreditBackQueueName();
		String qcfName = loanClaimConfig.getEsbCreditBackQCFName();

		try
		{
			byte[] responseXMLBytes = responseXml.getBytes("UTF-8");
			if (responseXMLBytes.length > JMS_TEXT_MESSAGE_MAX_SIZE)
				jmsService.sendBytesToQueue(responseXMLBytes, queueName, qcfName, null, null);
			else jmsService.sendMessageToQueue(responseXml, queueName, qcfName, null, null);
		}
		catch (JMSException e)
		{
			throw new RuntimeException("���� �� �������� ������ �� ��� � �������", e);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException("���� �� �������� ������ �� ��� � �������", e);
		}
	}
}
