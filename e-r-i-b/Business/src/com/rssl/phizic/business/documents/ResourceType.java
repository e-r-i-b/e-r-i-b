package com.rssl.phizic.business.documents;

import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;

/**
 * @author krenev
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 * Тип объекта, с которым производятся действие.
 */
public enum ResourceType
{
	/**
	 * Счёт клиента
	 */
	ACCOUNT(AccountLink.class),

	/**
	 * Чужой счёт
	 */
	EXTERNAL_ACCOUNT(null),

	/**
	 * Карта клиента
	 */
	CARD(CardLink.class),

	/**
	 * Чужая карта
	 */
	EXTERNAL_CARD(null),

	/**
	 * Кредит
	 */
	LOAN(LoanLink.class),

	/**
	 * Счёт депо
	 */
	DEPO_ACCOUNT(DepoAccountLink.class),

	/**
	 * ОМС (Обезличенный Металлический Счет)
	 */
	IM_ACCOUNT(IMAccountLink.class),

	LOYALTY_PROGRAM(LoyaltyProgramLink.class),
	/**
	 * страховой продукт
	 */
	INSURANCE_APP(InsuranceLink.class),
	/**
	 * сберсертификат
	 */
	SECURITY_ACCOUNT(SecurityAccountLink.class),

	/**
	 * Пустой объект
	 */
	NULL(null)
	;

	///////////////////////////////////////////////////////////////////////////

	private final Class<? extends ExternalResourceLink> resourceLinkClass;

	private ResourceType(Class<? extends ExternalResourceLink> resourceLinkClass)
	{
		this.resourceLinkClass = resourceLinkClass;
	}

	/**
	 * @return класс линка, релевантного этому типу ресурса
	 */
	public Class<? extends ExternalResourceLink> getResourceLinkClass()
	{
		return resourceLinkClass;
	}

	public static ResourceType fromValue(String type)
	{
		if (CardLink.class.getName().equals(type))
		{
			return CARD;
		}
		if (AccountLink.class.getName().equals(type))
		{
			return ACCOUNT;
		}
		if (IMAccountLink.class.getName().equals(type))
		{
			return IM_ACCOUNT;
		}
		if (InsuranceLink.class.getName().equals(type))
		{
			return INSURANCE_APP;
		}
		if (LoyaltyProgramLink.class.getName().equals(type))
		{
			return LOYALTY_PROGRAM;
		}
		if (DepoAccountLink.class.getName().equals(type))
		{
			return DEPO_ACCOUNT;
		}
		if (LoanLink.class.getName().equals(type))
		{
			return LOAN;
		}
		return NULL;
	}
}
