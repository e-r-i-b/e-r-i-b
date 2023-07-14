package com.rssl.phizic.ermb.transport;

import com.rssl.phizic.TestEjbMessage;
import com.rssl.phizic.business.ermb.sms.messaging.imsi.CheckIMSIState;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.messaging.ermb.*;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.synchronization.types.CheckImsiResponse;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.jms.JmsService;

import java.util.Calendar;
import java.util.Random;
import javax.xml.bind.JAXBException;

import static com.rssl.phizic.messaging.ermb.ErmbJndiConstants.ERMB_QUEUE;
import static com.rssl.phizic.messaging.ermb.ErmbJndiConstants.ERMB_QCF;

/**
 * @author EgorovaA
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ��� ������ ������������ ���������
 */
public class TransportSmsProcessor extends MessageProcessorBase<TestEjbMessage>
{
	private final Random random = new Random();

	private final JmsService jmsService = new JmsService();

	private final TransportMessageSerializer requestSerializer = new TransportMessageSerializer();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param module - ������
	 */
	public TransportSmsProcessor(Module module)
	{
		super(module);
	}

	@Override protected void doProcessMessage(TestEjbMessage xmlRequest)
	{
		try
		{
			// A. ������ ��� �������� IMSI
			if (xmlRequest.getSendSmsRequest() != null)
			{
				SendSmsRequest sendSmsRequest = xmlRequest.getSendSmsRequest();
				log.info("�������� ���-���������: " + sendSmsRequest.getText());
				return;
			}

			// B. ������ � ��������� IMSI
			if (xmlRequest.getSendSmsWithImsiRequest() != null)
			{
				SendSmsWithImsiRequest sendSmsWithImsiRequest = xmlRequest.getSendSmsWithImsiRequest();
				log.info("�������� ���-��������� � ��������� IMSI: " + sendSmsWithImsiRequest.getText());

				// 2. �������� ��������� � ������������ �������� IMSI (� ������� ������������)
				if (getRandomBoolean())
				{
					//���������� ��������� �������� IMSI
					String messageText = buildMessageXml(sendSmsWithImsiRequest);
					jmsService.sendMessageToQueue(messageText, ERMB_QUEUE, ERMB_QCF, null, null);
				}
				else
					log.error("�� ������� ��������� ��������� �������� IMSI");

				return;
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private String buildMessageXml(SendSmsWithImsiRequest request)
	{
		try
		{
			CheckImsiResponse response = new CheckImsiResponse();
			response.setRqVersion(CheckImsiResponse.VERSION);
			response.setRqUID(new RandomGUID().toUUID());
			response.setRqTime(Calendar.getInstance());
			response.setPhone(request.getPhone());
			response.setResultCode(generateResultCode());
			response.setCorrelationID(request.getRqUID());

			return requestSerializer.marshallCheckIMSIResponse(response);
		}
		catch (JAXBException e)
		{
			throw new RuntimeException("���� �� �������� � XML", e);
		}
	}

	private boolean getRandomBoolean()
	{
		int i = random.nextInt(10);
		if (2 >= i && i > 0)
			return false;
		return true;
	}

	/**
	 * ������������� ������ ������ ��������
	 * @return
	 */
	private String generateResultCode()
	{
		int i = random.nextInt(10);
		if (2 >= i && i > 0)
			return CheckIMSIState.ERROR.getType();
		return CheckIMSIState.OK.getType();
	}

	@Override
	public boolean writeToLog()
	{
		// � �������� ���������� �� ����
		return false;
	}
}
