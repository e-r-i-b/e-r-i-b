package com.rssl.phizic.business.ermb.migration.list.task;

import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Phone;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ClientService;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.messaging.mail.SmsTransportService;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

import static com.rssl.phizic.business.ermb.migration.list.task.TaskType.SMS_BROADCAST;

/**
 * @author Nady
 * @ created 23.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Задача СМС-рассылки клиентам в АС Мигратор
 */
public class SMSDeliveryTask extends TaskBase
{
	private final ClientService clientService = new ClientService();
	private final SmsTransportService messagingService = MessagingSingleton.getInstance().getErmbSmsTransportService();
	private static final int MAX_RESULT = 1000;

	private List<Segment> segmentsToSend;
	private List<Segment> segmentsNotSend;
	private String messageText;

	private int numSuccessClient = 0;
	private int numErrorClient = 0;

	/**
	 * ctor
	 * @param toSendSegments - сегменты для расылки
	 * @param notSendSegments - сегменты, которым не отправлять смс
	 * @param messageText - текст смс-сообщения
	 */
	public SMSDeliveryTask(String[] toSendSegments, String[] notSendSegments, String messageText)
	{
		this.segmentsToSend = listStringToListSegments(toSendSegments);
		this.segmentsNotSend = listStringToListSegments(notSendSegments);
		this.messageText = messageText;
	}

	@Override
	protected void start() throws Exception
	{
		int start = 0;
		int totalClientsCount = 0;
		while (true)
		{
			List<Client> clients = clientService.findClientsBySegmentsForSMS(segmentsToSend, segmentsNotSend, start, MAX_RESULT);
			if (clients.size() == 0)
				break;
			totalClientsCount += clients.size();

			for (Client client : clients)
			{
				String nameClient;
				if (!StringHelper.isEmpty(client.getMiddleName()))
					nameClient = client.getFirstName() + " " + client.getMiddleName();
				else
					nameClient= client.getFirstName();

				String messageForClient = getMessageForClient
						(
								messageText,
								nameClient
						);
				try
				{
					for (Phone phone : client.getPhones())
					{
						messagingService.sendSms
								(
									phone.getPhoneNumber(),
									messageForClient,
									messageForClient,
									TextMessage.DEFAULT_PRIORITY
								);
					}
				} catch (IKFLMessagingException e)
				{
					numErrorClient++;
				}
				numSuccessClient++;
			}
			start += MAX_RESULT;
		}
	}

	protected void stop()
	{

	}

	public TaskType getType()
	{
		return SMS_BROADCAST;
	}

	public Set<TaskType> getIllegalTasks()
	{
		return EnumSet.noneOf(TaskType.class);
	}

	private List<Segment> listStringToListSegments(String[] segments)
	{
		List<Segment> segmentList = new ArrayList<Segment>(segments.length);
		for (String segment : segments)
		{
			segmentList.add(Segment.fromValue(segment));
		}
		return segmentList;
	}

	private String getMessageForClient(String messageText, String name)
	{
		// текст(переменная) $name заменяется без учета регистра на значение переданное в name
		return messageText.replaceAll("(?i)" + "\\$name", name);
	}
}
