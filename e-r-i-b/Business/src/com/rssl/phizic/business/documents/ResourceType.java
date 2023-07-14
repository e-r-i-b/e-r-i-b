package com.rssl.phizic.business.documents;

import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;

/**
 * @author krenev
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 * ��� �������, � ������� ������������ ��������.
 */
public enum ResourceType
{
	/**
	 * ���� �������
	 */
	ACCOUNT(AccountLink.class),

	/**
	 * ����� ����
	 */
	EXTERNAL_ACCOUNT(null),

	/**
	 * ����� �������
	 */
	CARD(CardLink.class),

	/**
	 * ����� �����
	 */
	EXTERNAL_CARD(null),

	/**
	 * ������
	 */
	LOAN(LoanLink.class),

	/**
	 * ���� ����
	 */
	DEPO_ACCOUNT(DepoAccountLink.class),

	/**
	 * ��� (������������ ������������� ����)
	 */
	IM_ACCOUNT(IMAccountLink.class),

	LOYALTY_PROGRAM(LoyaltyProgramLink.class),
	/**
	 * ��������� �������
	 */
	INSURANCE_APP(InsuranceLink.class),
	/**
	 * ��������������
	 */
	SECURITY_ACCOUNT(SecurityAccountLink.class),

	/**
	 * ������ ������
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
	 * @return ����� �����, ������������ ����� ���� �������
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
