package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.passwordcards.PasswordCardNotAvailableException;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.password.InvalidUserIdException;
import org.apache.commons.logging.Log;

import static com.rssl.phizic.common.types.security.Constants.DAFAULT_ERROR_MESSAGE;
import static com.rssl.phizic.logging.Constants.LOG_MODULE_CORE;

/**
 * Author: Moshenko
 * Date: 28.04.2010
 * Time: 15:12:00
 */
public class iPasCompositeConfirmStrategy extends CompositeConfirmStrategy
{
	private static final String ERROR_STRATEGY_MESSAGE = "¬ыбранна€ вами стратеги€ подтверждени€ не доступна, воспользуйтесь предложенной стратегией.";
	private static final String ERROR_CARD_STRATEGY_MESSAGE = "¬ы не можете подтвердить операцию паролем с чека. ѕожалуйста, подтвердите операцию паролем.";

	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_CORE);

	public ConfirmStrategyType getType()
	{
		return ConfirmStrategyType.conditionComposite;
	}

	@SuppressWarnings({"ThrowableInstanceNeverThrown"})
	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId,PreConfirmObject preConfrim) throws SecurityException
	{
		try
		{
			return getStrategy(defaultStrategy).createRequest(login, value, sessionId, preConfrim);
		}
		catch(InvalidUserIdException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			setWarning(defaultStrategy == ConfirmStrategyType.card ? new PasswordCardNotAvailableException(ERROR_CARD_STRATEGY_MESSAGE) : new SecurityLogicException(ERROR_STRATEGY_MESSAGE));
			log.error(e.getMessage(), e);

			for(ConfirmStrategy strategy: strategies.values())
			{
				if (!strategy.getType().equals(defaultStrategy))
				{
					try
					{
						ConfirmRequest rq = strategy.createRequest(login, value, sessionId, preConfrim);
						defaultStrategy   = strategy.getType();
						value.setConfirmStrategyType(defaultStrategy);
						return rq;
					}
					catch (SecurityException ex)
					{
						setWarning(new SecurityLogicException(ERROR_STRATEGY_MESSAGE));
						log.error(ex);
					}
					catch (SecurityLogicException ex)
					{
						setWarning(new SecurityLogicException(ERROR_STRATEGY_MESSAGE));
						log.error(ex);
					}
				}
			}
		}

		return new ErrorConfirmRequest(defaultStrategy, DAFAULT_ERROR_MESSAGE);
	}

	public PreConfirmObject preConfirmActions(CallBackHandler callBackHandler) throws SecurityLogicException, SecurityException
	{
		return getStrategy(getDefaultStrategy()).preConfirmActions(callBackHandler);
	}

	public ConfirmResponseReader getConfirmResponseReader()
	{
		return getStrategy(getDefaultStrategy()).getConfirmResponseReader();
	}

	public ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityLogicException, SecurityException
	{
		return getStrategy(getDefaultStrategy()).validate(login, request, response);
	}

	public Object clone() throws CloneNotSupportedException
	{
		iPasCompositeConfirmStrategy newStrategy = new iPasCompositeConfirmStrategy();
		newStrategy.setDefaultStrategy(getDefaultStrategy());
		for (ConfirmStrategy strategy: strategies.values())
		{
			newStrategy.addStrategy(strategy);
		}
		return newStrategy;
	}
}
