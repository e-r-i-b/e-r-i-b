package com.rssl.phizic.operations;

import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.operations.ConfirmableOperation;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.payments.InvalidUserIdBusinessException;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.SmsErrorLogicException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.password.BlockedException;
import com.rssl.phizic.security.password.InvalidUserIdException;

import java.util.Calendar;
import java.util.Properties;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: khudyakov $
 * @ $Revision: 84619 $
 */

public abstract class ConfirmableOperationBase<R extends Restriction> extends OperationBase<R> implements ConfirmableOperation<R>
{
	private ConfirmRequest request;
	private ConfirmResponse response;
	private CommonLogin login;
	private ConfirmStrategy confirmStrategy;
    private PreConfirmObject preConfirm=null;
	private static final AccessPolicyService accessService = new AccessPolicyService();
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private Exception warning;

	public PreConfirmObject getPreConfirm()
	{
		return preConfirm;
	}

	public void setPreConfirm(PreConfirmObject preConfirm)
	{
		this.preConfirm = preConfirm;
	}

	public ConfirmRequest createConfirmRequest(String sessionId) throws BusinessException, BusinessLogicException
	{
		try
		{
			initializeNew();
			ConfirmRequest newRequest = confirmStrategy.createRequest(login, getConfirmableObject(), sessionId, getPreConfirm());
			setWarning(confirmStrategy.getWarning());
			request = newRequest;
			return newRequest;
		}
		catch (InvalidUserIdException e)
		{
			throw new InvalidUserIdBusinessException(e);
		}
		catch (SecurityException e)
		{
			throw new BusinessException(e);
		}
		catch (SecurityLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	public void initializeNew()
	{
		if (login != null && confirmStrategy != null)
			return;

		ConfirmStrategyProvider provider = getConfirmStrategyProvider();
		login = provider.getPrincipal().getLogin();
		confirmStrategy = findStrategy(provider);
	}

	protected ConfirmStrategy findStrategy(ConfirmStrategyProvider provider)
	{
		ConfirmStrategy result = provider.getStrategy();
		ConfirmStrategySource source = getStrategy();

		if (source == null)
		{
			return result;
		}

		try
		{
			boolean guestLoanClaim = false;
			if (getConfirmableObject() instanceof ExtendedLoanClaim)
			{
				ExtendedLoanClaim claim = (ExtendedLoanClaim) getConfirmableObject();
				guestLoanClaim = claim.getOwner().getPerson() instanceof GuestPerson;
			}
			AccessPolicy policy = provider.getPrincipal().getAccessPolicy();
			String userStrategyType = null;
			if (!guestLoanClaim)
			{
				Properties userProperties = accessService.getProperties(login, policy.getAccessType());
				userStrategyType = userProperties.getProperty("userOptionType");
			}
			return source.getStrategy(getConfirmableObject(), result, userStrategyType);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (SecurityDbException e)
		{
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public void setConfirmRequest(ConfirmRequest request)
	{
		this.request = request;
	}

	public void setConfirmResponse(ConfirmResponse confirmResponse)
	{
		this.response = confirmResponse;
	}


	public ConfirmRequest getRequest()
	{
		return request;
	}

	protected ConfirmResponse getResponse()
	{
		return response;
	}

	public ConfirmStrategyType getStrategyType()
	{
		return confirmStrategy.getType();
	}

	public ConfirmResponseReader getConfirmResponseReader()
	{
		if (confirmStrategy==null) initializeNew();
		return confirmStrategy.getConfirmResponseReader();
	}

	public void setStrategyType()
	{
		if (confirmStrategy == null)
		{
			initializeNew();
		}
	}

	public boolean isSignatureSaveRequired()
	{
		return confirmStrategy.hasSignature();
	}

	public DocumentSignature getSignature() throws SecurityLogicException, SecurityException
	{
		return confirmStrategy.createSignature(getRequest(), (SignatureConfirmResponse)getResponse());
	}

	public void confirm() throws SecurityLogicException, BusinessException, BusinessLogicException
	{
		validateConfirm();
		saveConfirm();
	}

	protected abstract void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException;

	/**
	 * ѕровести проверку подтверждени€
	 */
	public void validateConfirm() throws BusinessException, SecurityLogicException, SecurityException, BusinessLogicException
	{
		initializeNew();

		try
		{
			confirmStrategy.validate(login, request, response);
		}
		catch (BlockedException e)
		{
			Calendar blockUntil = Calendar.getInstance();
			SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
			blockUntil.add(Calendar.SECOND, securityConfig.getBlockedTimeout());
			PersonHelper.personLock(blockUntil, true);
			preConfirm = null;
			throw new SecurityLogicException("Ќеправильный ввод парол€. ƒоступ в систему запрещен до " + String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS", blockUntil.getTime())+".");
		}
		catch (SmsErrorLogicException me)
		{
			preConfirm = null;
			throw me;
		}
		catch (SecurityLogicException e)
		{
			preConfirm=null;
			throw new SecurityLogicException(e.getLocalizedMessage());
		}
	}

	public PreConfirmObject preConfirm(CallBackHandler callBackHandler) throws SecurityLogicException, BusinessException, BusinessLogicException
	{
		try
		{
			return doPreConfirm(callBackHandler);
		}
		finally
		{
			doPreFraudControl();
		}
	}

	protected PreConfirmObject doPreConfirm(CallBackHandler callBackHandler) throws SecurityLogicException
	{
		if (confirmStrategy == null)
		{
			initializeNew();
		}

		return confirmStrategy.preConfirmActions(callBackHandler);
	}

	public void doPreFraudControl() throws BusinessLogicException, BusinessException {};

	public void setUserStrategyType(ConfirmStrategyType type)
	{
		// ”станавливаем тип стратегии подтверждени€ по одноразовому паролю, выбранный пользователем (смс, чек)
		if (confirmStrategy instanceof CompositeConfirmStrategy)
		{
			CompositeConfirmStrategy strategy = (CompositeConfirmStrategy)confirmStrategy;
			strategy.setDefaultStrategy(type);
		}
	}

	public Exception getWarning()
	{
		return warning;
	}

	public void setWarning(Exception warning)
	{
		this.warning = warning;
	}

	public boolean isAnotherStrategy()
	{
		if(confirmStrategy instanceof CompositeConfirmStrategy)
		{
			return ((CompositeConfirmStrategy)confirmStrategy).getStrategies().size()>1;
		}
		else
		{
			return false;
		}
	}

	public void resetConfirmStrategy() throws BusinessException
	{
		if (confirmStrategy==null) initializeNew();
		try
		{
			confirmStrategy.reset(login, getConfirmableObject());
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	/**
	 * @return сообщение, которое выводитс€ при успешном сохранении данных
	 */
	public String getSuccessfulMessage()
	{
		return "ƒанные успешно сохранены.";
	}

}