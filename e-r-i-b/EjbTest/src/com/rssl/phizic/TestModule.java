package com.rssl.phizic;

import com.rssl.common.forms.FormsEngine;
import com.rssl.phizic.auth.AuthEngine;
import com.rssl.phizic.bankroll.BankrollEngine;
import com.rssl.phizic.business.ermb.text.TextEngineImpl;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.credit.EsbCreditProcessor;
import com.rssl.phizic.dataaccess.hibernate.HibernateEngine;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.ermb.auxiliary.UpdateProfileProcessor;
import com.rssl.phizic.ermb.transport.EchoTestProcessor;
import com.rssl.phizic.ermb.transport.TransportSmsProcessor;
import com.rssl.phizic.gate.GateEngine;
import com.rssl.phizic.interactive.UserInteractEngine;
import com.rssl.phizic.logging.system.LogEngine;
import com.rssl.phizic.logging.system.LogEngineImpl;
import com.rssl.phizic.messaging.MessageProcessor;
import com.rssl.phizic.messaging.MessagingEngine;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.module.loader.BasicModuleLoader;
import com.rssl.phizic.module.loader.ModuleLoader;
import com.rssl.phizic.module.work.WorkManager;
import com.rssl.phizic.module.work.WorkManagerImpl;
import com.rssl.phizic.payment.PaymentEngine;
import com.rssl.phizic.permission.PermissionEngine;
import com.rssl.phizic.esb.ejb.mock.federal.sbnkd.SBNKDProcessor;
import com.rssl.phizic.security.ConfirmEngine;
import com.rssl.phizic.session.SessionEngine;
import com.rssl.phizic.text.TextEngine;

/**
 * @author EgorovaA
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Модуль-заглушка
 */
@SuppressWarnings({"ThisEscapedInObjectConstruction"})
public class TestModule implements Module
{
	private final ModuleLoader moduleLoader = new BasicModuleLoader(this);

	private final WorkManager workManager = new WorkManagerImpl(this);

	private final EngineManager engineManager = new EngineManager(this);

	private final LogEngine logEngine = new LogEngineImpl(engineManager);

	private final TextEngine textEngine = new TextEngineImpl(engineManager);

	private final TransportSmsProcessor transportSmsProcessor = new TransportSmsProcessor(this);

	private final UpdateProfileProcessor updateProfileProcessor = new UpdateProfileProcessor(this);

	private final EsbCreditProcessor esbCreditProcessor = new EsbCreditProcessor(this);

	private final EchoTestProcessor echoTestProcessor = new EchoTestProcessor(this);

	private final SBNKDProcessor sbnkdProcessor = new SBNKDProcessor(this);

	///////////////////////////////////////////////////////////////////////////

	public String getName()
	{
		return "ErmbTestModule";
	}

	public Application getApplication()
	{
		return Application.Other;
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
		return null;
	}

	public TextEngine getTextEngine()
	{
		return textEngine;
	}

	public AuthEngine getAuthEngine()
	{
		return null;
	}

	public SessionEngine getSessionEngine()
	{
		return null;
	}

	public PermissionEngine getPermissionEngine()
	{
		return null;
	}

	public GateEngine getGateEngine()
	{
		return null;
	}

	public UserInteractEngine getUserInteractEngine()
	{
		return null;
	}

	public FormsEngine getFormsEngine()
	{
		return null;
	}

	public BankrollEngine getBankrollEngine()
	{
		return null;
	}

	public MessagingEngine getMessagingEngine()
	{
		return null;
	}

	public PaymentEngine getPaymentEngine()
	{
		return null;
	}

	public ConfirmEngine getConfirmEngine()
	{
		return null;
	}

	/**
	 * @return процессор для приёма исходящих СМС (ЕРИБ -> СОС)
	 */
	public TransportSmsProcessor getTransportSmsProcessor()
	{
		return transportSmsProcessor;
	}

	/**
	 * @return процессор для приёма уведомлений об изменении ЕРМБ-профиля
	 */
	public UpdateProfileProcessor getUpdateProfileProcessor()
	{
		return updateProfileProcessor;
	}

	/**
	 * @return процессор для обработки расширенной заявки на кредит
	 */
	public MessageProcessor getEsbCreditProcessor()
	{
		return esbCreditProcessor;
	}

	/**
	 * @return процессор ответов о списании абонентской платы
	 */
	public MessageProcessor getEchoTestProcessor()
	{
		return echoTestProcessor;
	}

	public SBNKDProcessor getSbnkdProcessor()
	{
		return sbnkdProcessor;
	}
}
