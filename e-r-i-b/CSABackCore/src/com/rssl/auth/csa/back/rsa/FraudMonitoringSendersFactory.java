package com.rssl.auth.csa.back.rsa;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.NullFraudMonitoringSender;
import com.rssl.phizic.rsa.senders.events.*;
import com.rssl.phizic.rsa.senders.types.EventsType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tisov
 * @ created 27.02.15
 * @ $Author$
 * @ $Revision$
 * Фрод-мониторинг фабрика для CSABack
 */
public class FraudMonitoringSendersFactory
{
	private static final FraudMonitoringSendersFactory INSTANCE = new FraudMonitoringSendersFactory();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	//сендеры по событию
	private static final Map<EventsType, Class<? extends FraudMonitoringSender>> eventSenders
			= new HashMap<EventsType, Class<? extends FraudMonitoringSender>>();

	static
	{
		eventSenders.put(EventsType.FAILED_LOGIN_ATTEMPT,                   NotifyFailedLogInSender.class);
		eventSenders.put(EventsType.FAILED_MOBILE_LOGIN_ATTEMPT,            NotifyFailedMobileLogInSender.class);
		eventSenders.put(EventsType.CHANGE_PASSWORD,                        AnalyzeChangePasswordSender.class);
		eventSenders.put(EventsType.ENROLL,                                 AnalyzeEnrollEventSender.class);
	}

	private FraudMonitoringSendersFactory()
	{}

	/**
	 * @return фабрика
	 */
	public static FraudMonitoringSendersFactory getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Сендер во Фрод-мониторинг по событию
	 * @param event событие
	 * @return сендер
	 */
	public FraudMonitoringSender getSender(EventsType event)
	{
		RSAConfig config = RSAConfig.getInstance();
		if (!config.isSystemActive())
		{
			return new NullFraudMonitoringSender();
		}

		try
		{
			Class<? extends FraudMonitoringSender> senderClass = eventSenders.get(event);
			if (senderClass == null)
			{
				return new NullFraudMonitoringSender();
			}

			FraudMonitoringSender sender = senderClass.newInstance();
			return sender;
		}
		catch (Exception e)
		{
			log.error(e);
			return new NullFraudMonitoringSender();
		}
	}
}
