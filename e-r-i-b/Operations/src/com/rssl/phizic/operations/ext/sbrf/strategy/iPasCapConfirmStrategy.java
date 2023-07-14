package com.rssl.phizic.operations.ext.sbrf.strategy;

import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.authgate.AuthGateException;
import com.rssl.phizic.authgate.AuthGateLogicException;
import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.authgate.passwords.PasswordGateService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.MinMax;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.confirm.ConfirmType;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ext.sbrf.readers.CapConfirmResponseReader;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: Moshenko
 * Date: 14.05.12
 * Time: 11:40
 * Стратегия подтверждения по CAP паролю
 */
public class iPasCapConfirmStrategy implements ConfirmStrategy
{
	private Exception warning;
	private static ExternalResourceService externalResourceService = new ExternalResourceService();
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId, PreConfirmObject preConfirm) throws SecurityException
	{
		try
		{
			String capCardNumber = getCapNumber(login);
			if (StringHelper.isEmpty(capCardNumber))
				throw new  SecurityException("У клиента нет доступных карты поддерживающих CAP-пароль");

			ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().initializeCAPSuccess(capCardNumber);
			return new iPasCapConfirmRequest(capCardNumber);
		}
		catch (SecurityException e)
		{
			ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().initializeCAPFailed();
			throw e;
		}
	}

	public ConfirmStrategyType getType()
	{
		return ConfirmStrategyType.cap;
	}

	public boolean hasSignature()
	{
		return false;
	}

	public ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityLogicException, SecurityException
	{
		if (response == null)
			throw new SecurityException("Не установлен запрос на подтверждение");

		if (request == null)
			throw new SecurityException("Не установлен ответ на подтверждение");

		if (!(request instanceof iPasCapConfirmRequest))
			throw new SecurityException("некорректный тип ConfirmRequest, ожидался iPasCapConfirmRequest");

		iPasCapConfirmRequest req = (iPasCapConfirmRequest) request;
		iPasCapConfirmResponse res = (iPasCapConfirmResponse) response;

		PasswordGateService passwordGateService = AuthGateSingleton.getPasswordService();
		String confirmCode = res.getCapToken();
		try
		{
			passwordGateService.verifyCAP(req.getCardNumber(), confirmCode);
		}
		catch (AuthGateException e)
		{
			ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmFailed(ConfirmType.CAP, confirmCode);
			throw new SecurityException(e);
		}
		catch (AuthGateLogicException e)
		{
			ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmFailed(ConfirmType.CAP, confirmCode);
			throw new SecurityLogicException(e.getMessage());
		}

		ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmSuccess(ConfirmType.CAP, confirmCode);
		return new ConfirmStrategyResult(false);
	}

	public PreConfirmObject preConfirmActions(CallBackHandler callBackHandler) throws SecurityLogicException, SecurityException
	{
		// ничего делать не надо
		return null;
	}

	public DocumentSignature createSignature(ConfirmRequest request, SignatureConfirmResponse confirmResponse) throws SecurityLogicException, SecurityException
	{
		throw new UnsupportedOperationException();
	}

	public ConfirmResponseReader getConfirmResponseReader()
	{
		return new CapConfirmResponseReader();
	}

	public void reset(CommonLogin login, ConfirmableObject confirmableObject) throws SecurityDbException
	{
		//nothing
	}

	public Exception getWarning()
	{
		return warning;
	}

	public void setWarning(Exception warning)
	{
		this.warning = warning;
	}

	public boolean filter(Map<ConfirmStrategyType, List<StrategyCondition>> conditions, String userChoice, ConfirmableObject object)
	{
		if(conditions!=null)
		{
			if (conditions.get(getType())!=null)
				{
					for (StrategyCondition condition: conditions.get(getType()))
					{
						if (!condition.checkCondition(object))
						{
							String message = condition.getWarning();
							setWarning(message == null ? null : new SecurityLogicException(message));
							return false;
						}
					}
				}
		}
		return true;
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}


	/**
	 *
	 * Ищем у клиента номер CAP карты
	 *	1.	Смотрим карту входа.
	 *	2.	Если не нашли ищем по кард линкам.
	 * @param login
	 * @return номер карты CAP, либо null
	 */
	private String getCapNumber(CommonLogin login)
	{

		//Ищем у клиента CAP карту
		List<MinMax<String>> allowCodes = ConfigFactory.getConfig(BusinessSettingsConfig.class).getCAPCompatibleCardNumbersList();
		if (allowCodes.isEmpty())
			return null;
		try
		{
			String capCardNubmer = "";
			List<CardLink> personCardLinks = externalResourceService.getLinks(login, CardLink.class);
			Set<String> personCardLinksNubmer = new HashSet<String>();

			for (CardLink cardLink:personCardLinks)
			{
				personCardLinksNubmer.add(cardLink.getNumber());
			}

			String lastLogonCardNumber = login.getLastLogonCardNumber();
			if (StringHelper.isNotEmpty(lastLogonCardNumber))
				personCardLinksNubmer.add(lastLogonCardNumber);

			for (MinMax<String> allowCode: allowCodes)
			{
				BigInteger minNumber = new BigInteger(allowCode.getMin());
				BigInteger maxNumber = new BigInteger(allowCode.getMax());

				for (String cardLinkNumber: personCardLinksNubmer)
				{
					BigInteger cardNubmer = new BigInteger(cardLinkNumber);
					if ((cardNubmer.compareTo(minNumber)>=0)&&(cardNubmer.compareTo(maxNumber)<=0))
					{
						capCardNubmer = cardLinkNumber;
						return capCardNubmer;
					}
				}
			}
		}
		catch (BusinessException e)
		{
			throw new  SecurityException(e);

		}
		catch (BusinessLogicException e)
		{
			throw new  SecurityException(e);
		}

	   return null;
	}

	/**
	 * Доступна ли стратегия для использования
	 * Проверяем у клиента наличие СAP карт,
	 *	1.	Смотрим карту входа.
	 *	2.	Если не нашли ищем по кард линкам.
	 * @param login логин
	 * @return есть ли карта
	 */
	public boolean isAvalible(CommonLogin login)
	{
		try
		{
			if (!StringHelper.isEmpty(getCapNumber(login)))
				return true;
				
		}
		catch (SecurityException e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
		return false;
			
	}
	
}
