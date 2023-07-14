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
	 * �������� �� ������: "����������, ����������� ������ �� �����, �������� �������� ������������?"
	 * @return
	 */
	public int getAccountProvidersAllowed() throws BusinessException
	{
		// 1. ������� �� ������������
		Person clientPerson = getClientPerson();
		// 1.A ������������ - ������
		if (clientPerson != null)
		{
			switch (clientPerson.getCreationType())
			{
				// ������ ����
				case UDBO:
					return ALL_PROVIDERS_ALOWED;

				// ������ "�� ���������" (����)
				case SBOL:
					return ALL_PROVIDERS_ALOWED;

				// ��������� ������
				case CARD:
					return NO_PROVIDERS_ALOWED;

				// ��������� ������ � ������ ������
				case ANONYMOUS:
				default:
					return NO_PROVIDERS_ALOWED;
			}
		}

		// 1.B ������������ - ���������
		return ALL_PROVIDERS_ALOWED;
	}

	/**
	 * �������� �� ������: "����������, ����������� ������ � �����, �������� �������� ������������?"
	 * @return
	 */
	public int getCardProvidersAllowed() throws BusinessException
	{
		// 1. ������� �� ������������
		Person clientPerson = getClientPerson();

		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
        //���� ����� ����� ��� ���������� �� �������� ��
		if (Application.PhizIA == applicationConfig.getLoginContextApplication())
            return ALL_PROVIDERS_ALOWED;

        if (clientPerson == null)
            throw new BusinessException("�������� ������� ����������.");

		// ���� ������� ������ �� �������� ������� �� ���������� ������������ �������,
		// ���������� ��� �� ��������
		if (!isBillingPaymentsImplied())
			return NO_PROVIDERS_ALOWED;

		switch (clientPerson.getCreationType())
		{
			// ������ ����
			case UDBO:
				return isESBSupported() ? ALL_PROVIDERS_ALOWED : FEDERAL_PROVIDERS_ALLOWED;

			// ������ "�� ���������" (����)
			case SBOL:
				// ���� ������������� ������������ �� �� ����� �������� �� ����� ������������, ����� ������ ����������� �����������
				return isESBSupported() ? ALL_PROVIDERS_ALOWED : FEDERAL_PROVIDERS_ALLOWED;

			// ��������� ������
			case CARD:
				// ����� �������� � ���������� ���������� ������ ����������� �����������
				return FEDERAL_PROVIDERS_ALLOWED;

			// ��������� ������ � ������ ������
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
	 * ���������� �������� ������������ �������
	 * @return ������������ ������� ���� null, ���� ������� ������������ �� ������
	 */
	protected Person getClientPerson()
	{
		Person clientPerson = null;
		if (PersonContext.isAvailable())
			clientPerson = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		return clientPerson;
	}

	/**
	 * �������� �� ������: "������ ����������� ����� �������� �������� ������������?"
	 * @return
	 */
	private boolean isBillingPaymentsImplied()
	{
		return AuthModule.getAuthModule().implies(new ServicePermission("RurPayJurSB"));
	}

	/**
	 * �������� �� ������: "������� ������� ������� ��� ������������ �����, � �������� �������� ������� ������������ (������)?"
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
