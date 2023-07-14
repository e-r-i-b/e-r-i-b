package com.rssl.phizic.business.ermb.auxiliary;

import com.rssl.common.forms.FormsEngine;
import com.rssl.phizic.auth.AuthEngine;
import com.rssl.phizic.bankroll.BankrollEngine;
import com.rssl.phizic.business.ermb.ErmbBankrollEngine;
import com.rssl.phizic.business.ermb.auxiliary.messaging.profile.ConfirmProfileProcessor;
import com.rssl.phizic.business.ermb.auxiliary.messaging.profile.UpdateClientProcessor;
import com.rssl.phizic.business.ermb.auxiliary.messaging.profile.UpdateResourceProcessor;
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
import com.rssl.phizic.mbk.P2PMessageProcessor;
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
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Служебный канал ЕРМБ
 * TODO: (ЕРМБ) собрать все модули служебного канала в этот. Исполнитель Еркин С.
 */
@SuppressWarnings({"ThisEscapedInObjectConstruction"})
public class ErmbAuxChannel implements Module
{
	private final ModuleLoader moduleLoader = new BasicModuleLoader(this);

	private final WorkManager workManager = new WorkManagerImpl(this);

	private final EngineManager engineManager = new EngineManager(this);

	private final LogEngine logEngine = new LogEngineImpl(engineManager);

	private final HibernateEngine hibernateEngine = new HibernateEngineImpl(engineManager);

	private final TextEngine textEngine = new TextEngineImpl(engineManager);

	private final GateEngine gateEngine = new GateEngineImpl(engineManager);

	private final UpdateResourceProcessor updateResourceProcessor = new UpdateResourceProcessor(this);

	private final UpdateClientProcessor updateClientProcessor = new UpdateClientProcessor(this);

	private final ConfirmProfileProcessor confirmProfileProcessor = new ConfirmProfileProcessor(this);

	private final BankrollEngine bankrollEngine = new ErmbBankrollEngine(engineManager);

	private final P2PMessageProcessor mbkP2PProcessor = new P2PMessageProcessor(this);

	///////////////////////////////////////////////////////////////////////////

	public String getName()
	{
		return "ErmbAuxChannel";
	}

	public Application getApplication()
	{
		return Application.ErmbAuxChannel;
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
		return gateEngine;
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
		return bankrollEngine;
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
	 * @return процессор служебного канала, обрабатывающий СОС-оповещения об изменении продукта клиента в ЕРМБ
	 */
	public UpdateResourceProcessor getUpdateResourceProcessor()
	{
		return updateResourceProcessor;
	}

	/**
	 * @return процессор служебного канала, обрабатывающий СОС-оповещения об изменении данных клиента в ЕРМБ
	 */
	public UpdateClientProcessor getUpdateClientProcessor()
	{
		return updateClientProcessor;
	}

	/**
	 * @return процессор служебного канала, обрабатывающий СОС-подтверждения о приёме ЕРМБ-профиля
	 */
	public ConfirmProfileProcessor getConfirmProfileProcessor()
	{
		return confirmProfileProcessor;
	}

	/**
	 * @return процессор p2p-запросов МБК
	 */
	public P2PMessageProcessor getMBKP2PProcessor()
	{
		return mbkP2PProcessor;
	}
}
