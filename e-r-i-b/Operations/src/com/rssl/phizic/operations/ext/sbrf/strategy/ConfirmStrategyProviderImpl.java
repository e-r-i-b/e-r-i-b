package com.rssl.phizic.operations.ext.sbrf.strategy;

import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.access.CheckCapConfirmAccessOperation;
import com.rssl.phizic.operations.access.CheckPushConfirmAccessOperation;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;

import java.util.Properties;

/**
 * User: Moshenko
 * Date: 22.05.12
 * Time: 12:12
 */
public class ConfirmStrategyProviderImpl implements ConfirmStrategyProvider
{
	private static final AccessPolicyService accessService = new AccessPolicyService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private OperationFactory operationFactory = new OperationFactoryImpl(new RestrictionProviderImpl());
	private UserPrincipal principal;


	public ConfirmStrategyProviderImpl(UserPrincipal principal)
	{
		this.principal = principal;
	}

	public ConfirmStrategy getStrategy()
	{
		CommonLogin login = principal.getLogin();
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (Application.atm == applicationConfig.getLoginContextApplication())
			return new NotConfirmStrategy();

		ConfirmStrategy result;
		AccessPolicy policy = principal.getAccessPolicy();
		try
		{
			AccessType accessType = policy.getAccessType();
			AccessType simpleAccessType = LoginHelper.getPrincipalAccessTypeForAPI( AuthenticationContext.getContext(), principal);
			accessType = simpleAccessType != null ? simpleAccessType : accessType;

			Properties userProperties = accessService.getProperties(principal.getLogin(), accessType);
			String key = userProperties.getProperty(policy.getConfirmationChoice().getProperty());
			if (StringHelper.isEmpty(key))
				throw new ConfigurationException("Стратегия не определена: LoginId:"+principal.getLogin().getId());
			result = (ConfirmStrategy)((policy.getConfirmationMode().findStrategy(key)).clone());

		}
		catch (SecurityDbException e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
		catch (CloneNotSupportedException e)
		{
			log.error(e.getMessage(), e);
			return null;
		}

		if (result instanceof CompositeConfirmStrategy)
		{
			CompositeConfirmStrategy str = (CompositeConfirmStrategy) result;
			//Стратегия подтверждения по CAP паролю
			iPasCapConfirmStrategy iCapStrategy = (iPasCapConfirmStrategy)str.getStrategy(ConfirmStrategyType.cap);
			if (iCapStrategy != null)
			{
				try
				{
					if (!operationFactory.checkAccess(CheckCapConfirmAccessOperation.class))
					{
						removeCapStrategy(str);
					}
					else if (!iCapStrategy.isAvalible(login))
					{
						log.error("У клиента(login_id="+login.getId()+") нет карт попадающих в  промежуток номеров карт с разрешением подтверждения по CAP-токену");
						removeCapStrategy(str);
					}
				}
				catch (BusinessException e)
				{
					log.error(e.getMessage(), e);
					removeCapStrategy(str);
				}
			}
			//Стратегия подтверждения по одноразовому паролю через механизм Push-сообщений
			PushPasswordConfirmStrategy pushStrategy = (PushPasswordConfirmStrategy)str.getStrategy(ConfirmStrategyType.push);
			boolean isMobileApi = ApplicationUtil.isMobileApi();
            boolean isSocialApi = ApplicationUtil.isSocialApi();
			AuthenticationContext context = AuthenticationContext.getContext();
			if (pushStrategy != null)
			{
				try
				{
					if (!operationFactory.checkAccess(CheckPushConfirmAccessOperation.class)||
							((isMobileApi || isSocialApi) && !context.isPlatformPasswordConfirm()) )
					{
						removePushStrategy(str);
					}

				}
				catch (BusinessException e)
				{
					log.error(e.getMessage(), e);
					removePushStrategy(str);
				}
			}

			//Стратегия подтверждения по одноразовому SMS-паролю для mAPI
			if ((isMobileApi || isSocialApi) && !context.isPlatformPasswordConfirm() &&
				str.getStrategy(ConfirmStrategyType.sms) != null)
			{
				removeSmsStrategy(str);
			}
		}
		return result;
	}

	public UserPrincipal getPrincipal()
	{
		return principal;
	}

	private void removePushStrategy(CompositeConfirmStrategy strategy)
	{
		strategy.removeStrategy(ConfirmStrategyType.push);
	}

	private void removeCapStrategy(CompositeConfirmStrategy strategy)
	{
		strategy.removeStrategy(ConfirmStrategyType.cap);
	}

	private void removeSmsStrategy(CompositeConfirmStrategy strategy)
	{
		strategy.removeStrategy(ConfirmStrategyType.sms);
	}
}
