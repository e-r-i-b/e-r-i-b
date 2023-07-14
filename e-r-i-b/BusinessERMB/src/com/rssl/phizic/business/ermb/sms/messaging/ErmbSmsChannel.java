package com.rssl.phizic.business.ermb.sms.messaging;

import com.rssl.common.forms.FormsEngine;
import com.rssl.common.forms.FormsEngineImpl;
import com.rssl.phizic.auth.AuthEngine;
import com.rssl.phizic.auth.SmsAuthEngine;
import com.rssl.phizic.bankroll.BankrollEngine;
import com.rssl.phizic.business.ermb.ErmbBankrollEngine;
import com.rssl.phizic.business.ermb.sms.interactive.ErmbUserInteractEngine;
import com.rssl.phizic.business.ermb.sms.module.loader.SmsModuleLoader;
import com.rssl.phizic.business.ermb.sms.module.work.SmsChannelWorkManager;
import com.rssl.phizic.business.ermb.sms.payment.ErmbPaymentEngine;
import com.rssl.phizic.business.ermb.text.TextEngineImpl;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.dataaccess.hibernate.HibernateEngine;
import com.rssl.phizic.dataaccess.hibernate.HibernateEngineImpl;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.gate.GateEngine;
import com.rssl.phizic.gate.GateEngineImpl;
import com.rssl.phizic.interactive.UserInteractEngine;
import com.rssl.phizic.logging.system.LogEngine;
import com.rssl.phizic.logging.system.LogEngineImpl;
import com.rssl.phizic.messaging.MessagingEngine;
import com.rssl.phizic.messaging.MessagingEngineImpl;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.module.loader.ModuleLoader;
import com.rssl.phizic.module.work.WorkManager;
import com.rssl.phizic.operations.PermissionEngineImpl;
import com.rssl.phizic.payment.PaymentEngine;
import com.rssl.phizic.permission.PermissionEngine;
import com.rssl.phizic.security.ConfirmEngine;
import com.rssl.phizic.security.SmsConfirmEngine;
import com.rssl.phizic.session.SessionEngine;
import com.rssl.phizic.session.SessionEngineImpl;
import com.rssl.phizic.text.TextEngine;

/**
 * @author Erkin
 * @ created 06.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * CМС-канал ЕРМБ
 * Обрабатывает СМС-запросы клиентов
 * СМС-запрос обрабатывается в следующем порядке:
 * -> SmsEjbListener (MDB)
 *   -> SmsListenerModule
 *     -> SmsProcessor
 *       -> SmsXmlParser
 *       -> SmsReader
 *       -> Command
 */
@SuppressWarnings({"ThisEscapedInObjectConstruction"})
public class ErmbSmsChannel implements Module
{
	private final ModuleLoader moduleLoader = new SmsModuleLoader(this);

	private final WorkManager workManager = new SmsChannelWorkManager(this);

	private final EngineManager engineManager = new EngineManager(this);

	private final LogEngine logEngine = new LogEngineImpl(engineManager);

	private final HibernateEngine hibernateEngine = new HibernateEngineImpl(engineManager);

	private final TextEngine textEngine = new TextEngineImpl(engineManager);

	private final GateEngine gateEngine = new GateEngineImpl(engineManager);

	private final UserInteractEngine userInteractEngine = new ErmbUserInteractEngine(engineManager);

	private final FormsEngine formsEngine = new FormsEngineImpl(engineManager);
	
	private final AuthEngine authEngine = new SmsAuthEngine(engineManager);

	private final SessionEngine sessionEngine = new SessionEngineImpl(engineManager);

	private final PermissionEngine permissionEngine = new PermissionEngineImpl(engineManager);

	private final MessagingEngine messagingEngine = new MessagingEngineImpl(engineManager);

	private final BankrollEngine bankrollEngine = new ErmbBankrollEngine(engineManager);

	private final ConfirmEngine confirmEngine = new SmsConfirmEngine(engineManager);

	private final PaymentEngine paymentEngine = new ErmbPaymentEngine(engineManager);

	private final SmsProcessor smsProcessor = new SmsProcessor(this);

	///////////////////////////////////////////////////////////////////////////

	public String getName()
	{
		return "СМС-канал ЕРМБ";
	}

	public Application getApplication()
	{
		return Application.ErmbSmsChannel;
	}

	public ModuleLoader getModuleLoader()
	{
		return moduleLoader;
	}

	public WorkManager getWorkManager()
	{
		return workManager;
	}

	public EngineManager getEngineManager()
	{
		return engineManager;
	}

	public LogEngine getLogEngine()
	{
		return logEngine;
	}

	public HibernateEngine getHibernateEngine()
	{
		return hibernateEngine;
	}

	public TextEngine getTextEngine()
	{
		return textEngine;
	}

	public AuthEngine getAuthEngine()
	{
		return authEngine;
	}

	public SessionEngine getSessionEngine()
	{
		return sessionEngine;
	}

	public PermissionEngine getPermissionEngine()
	{
		return permissionEngine;
	}

	public GateEngine getGateEngine()
	{
		return gateEngine;
	}

	public UserInteractEngine getUserInteractEngine()
	{
		return userInteractEngine;
	}

	public FormsEngine getFormsEngine()
	{
		return formsEngine;
	}

	public BankrollEngine getBankrollEngine()
	{
		return bankrollEngine;
	}

    public MessagingEngine getMessagingEngine()
	{
		return messagingEngine;
	}

	public PaymentEngine getPaymentEngine()
	{
		return paymentEngine;
	}

	public ConfirmEngine getConfirmEngine()
	{
		return confirmEngine;
	}

	/**
	 * @return процессор для обработки входного СМС-потока (СОС -> ЕРИБ)
	 */
	public SmsProcessor getSmsProcessor()
	{
		return smsProcessor;
	}
}
