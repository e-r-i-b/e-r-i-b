package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.LoginHelper;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.permissions.ServicePermission;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * @author Erkin
 * @ created 28.03.2011
 * @ $Author$
 * @ $Revision$
 */
abstract class ListServiceProvidersOperationBase extends OperationBase
{
	protected static final int NO_PROVIDERS_ALOWED = 0;

	protected static final int ALL_PROVIDERS_ALOWED = 1;

	protected static final int FEDERAL_PROVIDERS_ALLOWED = 2;

	private Boolean esbSupported = null;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Отвечает на вопрос: "Поставщики, принимающие оплату со счета, доступны текущему пользователю?"
	 * @return
	 */
	public int getAccountProvidersAllowed() throws BusinessException
	{
		// 1. Смотрим на пользователя
		Person clientPerson = getClientPerson();
		// 1.A Пользователь - клиент
		if (clientPerson != null)
		{
			switch (clientPerson.getCreationType())
			{
				// Клиент УДБО
				case UDBO:
					return ALL_PROVIDERS_ALOWED;

				// Клиент "по Заявлению" (СБОЛ)
				case SBOL:
					return ALL_PROVIDERS_ALOWED;

				// Карточный клиент
				case CARD:
					return NO_PROVIDERS_ALOWED;

				// Анонимный клиент и прочие случаи
				case ANONYMOUS:
				default:
					return NO_PROVIDERS_ALOWED;
			}
		}

		// 1.B Пользователь - сотрудник
		return ALL_PROVIDERS_ALOWED;
	}

	/**
	 * Отвечает на вопрос: "Поставщики, принимающие оплату с карты, доступны текущему пользователю?"
	 * @return
	 */
	public int getCardProvidersAllowed() throws BusinessException
	{
		// 1. Смотрим на пользователя
		Person clientPerson = getClientPerson();

		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
        //если зашли через АРМ сотрудника то доступно всё
		if (Application.PhizIA == applicationConfig.getLoginContextApplication())
            return ALL_PROVIDERS_ALOWED;

        if (clientPerson == null)
            throw new BusinessException("Контекст клиента недоступен.");

		// Если текущий клиент не обладает правами на совершение биллингового платежа,
		// поставщики ему не доступны
		if (!isBillingPaymentsImplied())
			return NO_PROVIDERS_ALOWED;

		switch (clientPerson.getCreationType())
		{
			// Клиент УДБО
			case UDBO:
				return isESBSupported() ? ALL_PROVIDERS_ALOWED : FEDERAL_PROVIDERS_ALLOWED;

			// Клиент "по Заявлению" (СБОЛ)
			case SBOL:
				// Если подразделение поддерживает БП то можем работать со всеми поставщиками, иначе только федеральных поставщиков
				return isESBSupported() ? ALL_PROVIDERS_ALOWED : FEDERAL_PROVIDERS_ALLOWED;

			// Карточный клиент
			case CARD:
				// Может работать с карточными переводами только федеральных поставщиков
				return FEDERAL_PROVIDERS_ALLOWED;

			// Анонимный клиент и прочие случаи
			case ANONYMOUS:
			default:
				return NO_PROVIDERS_ALOWED;
		}
	}


	public Integer getVersionAPI()
	{
		return MobileApiUtil.getApiVersionNumber().getSolid();
	}

	///////////////////////////////////////////////////////////////////////////

	public CommonLogin getLogin()
	{
		return LoginHelper.getCurrentUserLoginId();
	}

	/**
	 * Возвращает текущего пользователя клиента
	 * @return пользователя клиента либо null, если текущий пользователь не клиент
	 */
	protected Person getClientPerson()
	{
		Person clientPerson = null;
		if (PersonContext.isAvailable())
			clientPerson = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		return clientPerson;
	}

	/**
	 * Отвечает на вопрос: "Оплата биллинговых услуг доступна текущему пользователю?"
	 * @return
	 */
	private boolean isBillingPaymentsImplied()
	{
		return AuthModule.getAuthModule().implies(new ServicePermission("RurPayJurSB"));
	}

	/**
	 * Отвечает на вопрос: "Базовый Продукт включён для подразделния Банка, к которому приписан текущий пользователь (клиент)?"
	 * @return
	 */
	private boolean isESBSupported() throws BusinessException
	{
		if (esbSupported != null)
			return esbSupported;

		try
		{
			esbSupported = ESBHelper.isESBSupported(getLogin().getId());
			return esbSupported;
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
