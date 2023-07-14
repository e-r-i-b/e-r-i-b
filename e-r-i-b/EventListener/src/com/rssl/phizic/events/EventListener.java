package com.rssl.phizic.events;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;

import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClassHelper;
import org.apache.commons.logging.*;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * @author Krenev
 * @ created 01.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class EventListener implements MessageDrivenBean, MessageListener
{
	private MessageDrivenContext ctx;
	private Map<String, EventHandler<Event>> eventsMap = new HashMap<String, EventHandler<Event>>();
    private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public EventListener() { }

	public void setMessageDrivenContext(MessageDrivenContext ctx) throws EJBException
	{
		this.ctx = ctx;
	}

	public void ejbCreate() throws Exception
	{
		PropertyReader propertyReader = ConfigFactory.getReaderByFileName("events.properties");
		Properties properties = propertyReader.getAllProperties();
		for (Enumeration<?> enumeration = properties.propertyNames(); enumeration.hasMoreElements();)
		{
			String eventType = (String) enumeration.nextElement();
			String handler = properties.getProperty(eventType);
			registerEvent(eventType, handler);
		}
	}

	private void registerEvent(String eventType, String handler) throws Exception
	{
		Class<EventHandler<Event>> handlerClass = ClassHelper.loadClass(handler);
		eventsMap.put(eventType, handlerClass.newInstance());
	}

	public void ejbRemove()
	{
		ctx = null;
		eventsMap.clear();
	}

	public void onMessage(Message message)
	{
		ApplicationInfo.setCurrentApplication(Application.EventListener);
		try
		{
			EventHandler<Event> eventHandler = eventsMap.get(message.getJMSType());
			// хендлера не зарегистрировано - пропускаем
			if(eventHandler == null)
				return;

			Event event = (Event) ((ObjectMessage) message).getObject();
			eventHandler.process(event);
			log.error("JMS, сервер "+ ApplicationConfig.getIt().getApplicationPrefix() + ", event " + event.getStringForLog());
		}
		catch (Exception e)
		{
			LogFactory.getLog(LogModule.Core.name()).error(e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}
}