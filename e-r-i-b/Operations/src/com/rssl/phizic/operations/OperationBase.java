package com.rssl.phizic.operations;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.modes.ConfirmStrategyProvider;
import com.rssl.phizic.auth.modes.ConfirmStrategySource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.messages.MessageConfig;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ext.sbrf.strategy.ConfirmStrategyProviderImpl;
import com.rssl.phizic.operations.ext.sbrf.strategy.EmployeeConfirmStrategyProvider;
import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 27.09.2005
 * Time: 14:40:08
 */
@PublicDefaultCreatable
public abstract class OperationBase<R extends Restriction> implements Operation<R>
{
	/**
	 * Фабрика, которая создала операцию
	 */
	@Deprecated
	//TODO BUG049348: Удалить operationFactory из OperationBase
	private OperationFactory operationFactory;
	private R restriction;
	private ConfirmStrategySource strategy;
	private MessageConfig messageConfig;
	private final MessageCollector messageCollector = new MessageCollector();
	private final StateMachineEvent stateMachineEvent = new StateMachineEvent();
	private VersionNumber clientAPIVersion;

	///////////////////////////////////////////////////////////////////////////
	@Deprecated
	//TODO BUG049348: Удалить operationFactory из OperationBase
	public OperationFactory getOperationFactory()
	{
		return operationFactory;
	}
	@Deprecated
	//TODO BUG049348: Удалить operationFactory из OperationBase
	public void setOperationFactory(OperationFactory operationFactory)
	{
		this.operationFactory = operationFactory;
	}

	protected MessageConfig getMessageConfig()
	{
		return messageConfig;
	}

	public void setMessageConfig(MessageConfig messageConfig)
	{
		this.messageConfig = messageConfig;
	}

	/**
	 * @return текущая версия ЕРИБ API, которую использует клиент
	 */
	public VersionNumber getClientAPIVersion()
	{
		return clientAPIVersion;
	}

	public void setClientAPIVersion(VersionNumber clientAPIVersion)
	{
		this.clientAPIVersion = clientAPIVersion;
	}

	protected String message(String bundle, String key) throws BusinessException
	{
		return messageConfig.message(bundle, key);
	}

	public Query createQuery(String name)
    {
        return new OperationQuery(this, name,getInstanceName());
    }

	public R getRestriction()
	{
		return restriction;
	}

	public void setRestriction(R restriction)
	{
		this.restriction = restriction;
	}

	public ConfirmStrategySource getStrategy()
	{
		return strategy;
	}

	public void setStrategy(ConfirmStrategySource strategy)
	{
		this.strategy = strategy;
	}

	/**
	 * Получить имя экземпляра БД, из которого будут получены данные
	 * @return имя экземпляра
	 */
	protected String getInstanceName()
	{
		return null;
	}

	public MessageCollector getMessageCollector()
	{
		return messageCollector;
	}
	
	public StateMachineEvent getStateMachineEvent()
	{
		return stateMachineEvent;
	}

	public ConfirmStrategyProvider getConfirmStrategyProvider()
	{
		ApplicationConfig config = ApplicationConfig.getIt();
		if (Application.PhizIA == config.getLoginContextApplication())
		{
			Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
			return new EmployeeConfirmStrategyProvider(new PrincipalImpl(login));
		}

		return new ConfirmStrategyProviderImpl(AuthModule.getAuthModule().getPrincipal());
	}
}
