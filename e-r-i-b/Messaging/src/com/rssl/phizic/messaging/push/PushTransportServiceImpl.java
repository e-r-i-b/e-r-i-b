package com.rssl.phizic.messaging.push;

import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.transmiters.Triplet;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.NotificationSettingsLogService;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.push.PushDeviceStatus;
import com.rssl.phizic.logging.settings.UserMessageLogHelper;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.person.ClientData;
import com.rssl.phizic.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Сервис для работы с push-уведомлениями
 * @author basharin
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 */

public class PushTransportServiceImpl implements PushTransportService
{
	private static final String SHA1_ERROR = "Ошибка при попытки получить цифровой отпечаток строки(SHA1)";
	private static final String INSTANCE_NAME = "Push";
	private static final String EVENT_TABLE_NAME = "QUEUE_PUSH_EVENTS_TAB";
	private static final String ADDRESS_TABLE_NAME = "QPE_ADDRESSES_TAB";
	private static final String DEVICE_TABLE_NAME = "QPE_DEVICES_TAB";
	private static final String QUEUE_DEVICE_TABLE_NAME = "QUEUE_PUSH_DEVICES_TAB";
	private static final String DEFAULT_SYSTEM_CODE = "erib";
	private static final int DEFAULT_MESSAGE_PRIORITY = 5;
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final NotificationSettingsLogService logStatisticService = new NotificationSettingsLogService();

	public void sendPush(final ClientData clientData,final PushMessage pushMessage,final Collection<String> phones,final List<Triplet<String, String, String>> listTriplets) throws IKFLMessagingException
	{
		try
        {
            HibernateExecutor.getInstance(INSTANCE_NAME).execute(new HibernateAction<Boolean>()
            {
                public Boolean run(Session session) throws IKFLMessagingException
                {
					PushEvent pushEvent = new PushEvent();
					pushEvent.setTimestamp(Calendar.getInstance());
					pushEvent.setEventId(generateEventId());
					pushEvent.setClientId(generateClientId(clientData));
					pushEvent.setShortMessage(pushMessage.getShortMessage());
					pushEvent.setSmsMessage(pushMessage.getSmsMessage());
					pushEvent.setSystemCode(DEFAULT_SYSTEM_CODE);
					pushEvent.setTypeCode(pushMessage.getTypeCode());
					pushEvent.setContent(pushMessage.getFullMessage());
					pushEvent.setLogContent(pushMessage.getTextToLog());
					pushEvent.setPriority(DEFAULT_MESSAGE_PRIORITY);             //Договорились вставлять пока фиксированное значение 5
					pushEvent.setPrivateFlag(pushMessage.getPrivacyType().toValue());
					pushEvent.setProcStatus(PushProcStatus.READY);
					add(pushEvent, EVENT_TABLE_NAME);

					if (CollectionUtils.isNotEmpty(listTriplets))
					{
						for (Triplet<String, String, String> triplet : listTriplets)
						{
							PushDevice pushDevice = new PushDevice();
							pushDevice.setTimestamp(Calendar.getInstance());
							pushDevice.setPushEvent(pushEvent);
							pushDevice.setDeviceId(triplet.getFirst());
							pushDevice.setMguidHash(getHashSHA1(triplet.getSecond()));
							pushDevice.setProcStatus(PushProcStatus.READY);
							add(pushDevice, DEVICE_TABLE_NAME);
						}
					}

					if (CollectionUtils.isNotEmpty(phones))
					{
						for (String phone : phones)
						{
							PushAddress pushAddress = new PushAddress();
							pushAddress.setTimestamp(Calendar.getInstance());
							pushAddress.setPushEvent(pushEvent);
							pushAddress.setAddress(phone);
							pushAddress.setProcStatus(PushProcStatus.READY);
							add(pushAddress, ADDRESS_TABLE_NAME);
						}
					}
					addToLog(pushEvent, clientData);
	                return true;
                }
            });
        }
        catch (Exception e)
        {
            throw new IKFLMessagingException(e);
        }
	}

	public void registerEvent(String deviceId, ClientData clientData, PushDeviceStatus pushMessageStatus, String securityToken, String mguid) throws IKFLMessagingException
	{
		if (deviceId == null || clientData == null || mguid == null)
		{
			log.error("Отсутствует необходимое поле при формировании push-уведомления");
			return;
		}
		PushQueueDevice pushQueueDevice = new PushQueueDevice();
		pushQueueDevice.setTimestamp(Calendar.getInstance());
		pushQueueDevice.setDeviceId(deviceId);
		pushQueueDevice.setClientId(generateClientId(clientData));
		pushQueueDevice.setStatus(pushMessageStatus);
		pushQueueDevice.setSecurityToken(securityToken);
		pushQueueDevice.setMguidHash(getHashSHA1(mguid));
		pushQueueDevice.setProcStatus(PushProcStatus.READY);
		add(pushQueueDevice, QUEUE_DEVICE_TABLE_NAME);
		addToLog(pushQueueDevice);
	}

	private void addToLog(PushEvent pushEvent, ClientData clientData)
	{
		if (clientData.getLoginId() != null)
		{
			UserMessageLogHelper.savePushEventToLog(pushEvent.getTimestamp(), pushEvent.getClientId(), clientData.getLoginId(), pushEvent.getTypeCode());
		}
	}

	private void addToLog(PushQueueDevice pushQueueDevice)
	{
		if (pushQueueDevice.getStatus() == PushDeviceStatus.ADD)
		{
			logStatisticService.savePushQueueDeviceToLog(pushQueueDevice.getTimestamp(), pushQueueDevice.getClientId(), pushQueueDevice.getMguidHash(), PushDeviceStatus.ADD);
		}
	}

	protected <T> T add(final T obj, String nameTable) throws IKFLMessagingException
	{
		String errorCode = "0";
		Calendar start = Calendar.getInstance();
		try
		{
			return HibernateExecutor.getInstance(INSTANCE_NAME).execute(new HibernateAction<T>()
			{
				public T run(Session session) throws Exception
				{
					session.save(obj);
					return obj;
				}
			}
			);
		}
		catch (Exception e)
		{
			errorCode = "-1";
			throw new IKFLMessagingException(e);
		}
		finally
		{
			writeToLog(errorCode, obj, DateHelper.diff(Calendar.getInstance(), start), nameTable);
		}
	}

	private void writeToLog(String errorCode, Object obj, Long duration, String nameTable)
	{
		try
		{
			MessageLogWriter messageLogWriter = MessageLogService.getMessageLogWriter();   //здесь надо маскированный
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();
			logEntry.setMessageRequest(obj.toString());
			logEntry.setMessageRequestId(System.jdbc.toValue());
			logEntry.setMessageType(nameTable);
			logEntry.setMessageResponseId(System.jdbc.toValue());
			logEntry.setMessageResponse(obj.toString());
			logEntry.setExecutionTime(duration);
			logEntry.setSystem(System.jdbc);
			logEntry.setErrorCode(errorCode);
			messageLogWriter.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("Ошибка записи сообщения в журнал", e);
		}
	}

	public List<PushRemoveDevice> getRemoveDevices() throws IKFLMessagingException
	{
		try
		{
			return HibernateExecutor.getInstance(INSTANCE_NAME).execute(new HibernateAction<List<PushRemoveDevice>>()
			{
				public List<PushRemoveDevice> run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(PushRemoveDevice.class)
							.add(Expression.eq("procStatus", PushProcStatus.READY));

					return criteria.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new IKFLMessagingException(e);
		}
	}

	public void updateRemoveDeviceState(PushRemoveDevice device) throws IKFLMessagingException
	{
		final PushQueueRemoveDevice pushDevice = (PushQueueRemoveDevice) device;
		try
		{
			HibernateExecutor.getInstance(INSTANCE_NAME).execute(new HibernateAction<PushQueueRemoveDevice>()
				{
					public PushQueueRemoveDevice run(Session session) throws Exception
					{
						pushDevice.setProcStatus(PushProcStatus.SEND);
						session.update(pushDevice);
						return null;
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new IKFLMessagingException(e);
		}
	}

	/**
	 * Сгенерировать новый случайный eventId для push-сообщений
	 * @return
	 */
	private String generateEventId()
	{
		return HashHelper.hash((new RandomGUID().getStringValue()));
	}

	/**
	 * Сгенерировать clientId из данных клиента для push-сообщений
	 * @return
	 */
	private String generateClientId(ClientData clientData)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(clientData.getSurName());
		stringBuilder.append(clientData.getFirstName());
		stringBuilder.append(clientData.getPatrName());
		stringBuilder.append(clientData.getDocumentSeriesNumber().replaceAll(" ", ""));
		stringBuilder.append(dateFormat.format(clientData.getBirthDay().getTime()));
		stringBuilder.append(getSynonymTb(clientData.getTb()));

		CihperHelper cihperHelper = new CihperHelper();

		try
		{
			return cihperHelper.SHA1(stringBuilder.toString());
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error(SHA1_ERROR, e);
			return null;
		}
		catch (UnsupportedEncodingException e)
		{
			log.error(SHA1_ERROR, e);
			return null;
		}
	}

	private String getSynonymTb(String tb)
	{
		TBSynonymsDictionary synonymsDictionary = ConfigFactory.getConfig(TBSynonymsDictionary.class);
		String tbBySynonym = synonymsDictionary.getMainTBBySynonymAndIdentical(tb.substring(0, 2));

		return StringHelper.addLeadingZeros(tbBySynonym, 2);
	}

	/**
	 * Получить хэш SHA1 от строки
	 * @return
	 */
	private String getHashSHA1(String string)
	{
		CihperHelper cihperHelper = new CihperHelper();
		try
		{
			return cihperHelper.SHA1(string);
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error(SHA1_ERROR, e);
			return "";
		}
		catch (UnsupportedEncodingException e)
		{
			log.error(SHA1_ERROR, e);
			return "";
		}
	}
}
