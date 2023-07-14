package com.rssl.phizic.csa.ejb;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.servises.way4NotificationEntries.Way4NotificationRecord;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.service.StartupServiceDictionary;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author vagin
 * @ created 11.11.14
 * @ $Author$
 * @ $Revision$
 * —лушатель нотификаций обратного потока Way4.
 */
public class Way4NotifyListener implements MessageDrivenBean, MessageListener
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Gate);
	private static final String WAY4_NOTIFICATION_LISTENER_ENABLE_PROPERTY_NAME = "com.rssl.auth.csa.back.modules.way4listener.enabled";

	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException {}

	public void ejbRemove() throws EJBException {}

	/**
	 * —оздание бина
	 * @throws EJBException
	 */
	public void ejbCreate() throws EJBException
	{
		StartupServiceDictionary serviceDictionary = new StartupServiceDictionary();
		serviceDictionary.startServices();
	}

	public void onMessage(Message message)
	{
		ApplicationInfo.setCurrentApplication(Application.Way4NotifyListener);
		Config cfg = ConfigFactory.getConfig(Config.class);
		//провер€ем включен ли режим записи.
		if(!Boolean.valueOf(cfg.getProperty(WAY4_NOTIFICATION_LISTENER_ENABLE_PROPERTY_NAME)))
			return;

		String text = getText(message);
		//≈сли упали при получении сообщени€, ничего больше не делаем.
		if (StringHelper.isEmpty(text))
		{
			ApplicationInfo.setDefaultApplication();
			return;
		}

		try
		{
			Document request = XmlHelper.parse(text);
			String custModRq = XmlHelper.getElementValueByPath(request.getDocumentElement(), "/IFX/BaseSvcRq/CustModRq");
			//если не сообщение об обновлении клиента-припускаем.
			if (StringHelper.isEmpty(custModRq))
				return;
			//подход€щее сообщение - пишем в лог.
			writeToLog(text, XmlHelper.getElementValueByPath(request.getDocumentElement(), "/IFX/BaseSvcRq/RqUID"), "Way4ApplicationMessage");

			Element root = request.getDocumentElement();
			String birthDate = XmlHelper.getElementValueByPath(request.getDocumentElement(), "/IFX/BaseSvcRq/CustModRq/CustInfo/PersonInfo/Birthday");

			String clientId = XmlHelper.getElementValueByPath(root, "/IFX/BaseSvcRq/CustModRq/CustId/CustPermId");
			String amndDateString = XmlHelper.getElementValueByPath(root, "/IFX/BaseSvcRq/CustModRq/CustInfo/EffDt");
			Date amndDate = DateHelper.fromXMlDateToDate(amndDateString);

			if (clientId == null || amndDate == null)
				return;

			//заполнение структуры
			Way4NotificationRecord record = new Way4NotificationRecord();
			record.setFirstName(XmlHelper.getElementValueByPath(root, "/IFX/BaseSvcRq/CustModRq/CustInfo/PersonInfo/PersonName/FirstName"));
			record.setSurName(XmlHelper.getElementValueByPath(root, "/IFX/BaseSvcRq/CustModRq/CustInfo/PersonInfo/PersonName/LastName"));
			record.setPatrName(XmlHelper.getElementValueByPath(root, "/IFX/BaseSvcRq/CustModRq/CustInfo/PersonInfo/PersonName/MiddleName"));
			record.setPassport(XmlHelper.getElementValueByPath(request.getDocumentElement(), "/IFX/BaseSvcRq/CustModRq/CustInfo/PersonInfo/IdentityCard/IdNum"));
			Date dateOfBirth = DateHelper.fromXMlDateToDate(birthDate);
			Calendar date = Calendar.getInstance();
			date.setTime(dateOfBirth);
			record.setBirthDate(date);
			record.setClientId(clientId);
			record.setCbCode("9968115398");  //TODO после уточнени€ убрать, либо заполнить из структуры.
			Calendar amnd_calendar = Calendar.getInstance();
			amnd_calendar.setTime(amndDate);
			record.setAmndDate(amnd_calendar);
			//сохран€ем
			Way4NotificationRecord.updateJournal(record);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	private String getText(Message message)
	{
		try
		{
			TextMessage textMessage = (TextMessage) message;
			return textMessage.getText();
		}
		catch (JMSException e)
		{
			log.error("ќшибка при работе с JMS.", e);
			return "";
		}
	}

	private void writeToLog(String request, String rqUID, String messageType)
	{
		try
		{
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();

			logEntry.setApplication(ApplicationInfo.getCurrentApplication());
			logEntry.setSystem(com.rssl.phizic.logging.messaging.System.esberib);
			logEntry.setDate(Calendar.getInstance());
			logEntry.setMessageRequestId(rqUID);
			logEntry.setMessageType(messageType);
			logEntry.setMessageRequest(request);

			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			writer.write(logEntry);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
