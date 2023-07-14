package com.rssl.phizic.credit;

import com.rssl.common.forms.FormsEngine;
import com.rssl.phizic.auth.AuthEngine;
import com.rssl.phizic.bankroll.BankrollEngine;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.dataaccess.hibernate.HibernateEngine;
import com.rssl.phizic.dataaccess.hibernate.ProxyListenerHibernateEngine;
import com.rssl.phizic.engine.EngineManager;
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
import com.rssl.phizic.security.ConfirmEngine;
import com.rssl.phizic.session.SessionEngine;
import com.rssl.phizic.text.TextEngine;

/**
 * @author Erkin
 * @ created 21.02.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Обработчик сообщений из шины с информацией по кредитам (БКИ/TSM)
 */
@SuppressWarnings({"ThisEscapedInObjectConstruction"})
public class PhizProxyCreditListenerModule implements Module
{
	private final ModuleLoader moduleLoader = new BasicModuleLoader(this);

	private final WorkManager workManager = new WorkManagerImpl(this);

	private final EngineManager engineManager = new EngineManager(this);

	private final LogEngine logEngine = new LogEngineImpl(engineManager);

	private final HibernateEngine hibernateEngine = new ProxyListenerHibernateEngine(engineManager);

	private final MessageProcessor claimStatusProxyProcessor = new PhizProxyCreditProcessor(this);

	private final MessageProcessor crmLoanClaimResponseProcessor = new CRMLoanClaimResponseProcessor(this);

	///////////////////////////////////////////////////////////////////////////

	public String getName()
	{
		return Application.CreditProxyListener.name();
	}

	public Application getApplication()
	{
		return Application.CreditProxyListener;
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
		return null;
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
	 * @return обработчик сообщения со статусом заявки
	 */
	public MessageProcessor getClaimStatusProxyProcessor()
	{
		return claimStatusProxyProcessor;
	}

	public MessageProcessor getCrmLoanClaimResponseProcessor()
	{
		return crmLoanClaimResponseProcessor;
	}
}
