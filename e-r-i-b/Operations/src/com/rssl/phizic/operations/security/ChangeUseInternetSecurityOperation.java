package com.rssl.phizic.operations.security;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.profileSynchronization.PersonSettingsManager;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.userSettings.SettingsProcessor;
import com.rssl.phizic.userSettings.UserPropertiesConfig;

/**
 * �������� ��� ��������� ��������� ��������� ���������� �� �������� ������������
 * @author Rydvanskiy
 * @ created 10.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class ChangeUseInternetSecurityOperation extends OperationBase
{
	private static final PersonService service = new PersonService();
	private ActivePerson person;

	/**
	 * ������������� ���������
	 * @param login
	 * @throws BusinessException
	 */
	public void initialize(Login login) throws BusinessException
	{
		person = service.findByLogin(login);
	}

	/**
	 * ����� ����������� �������� �������
	 * @return
	 * @throws BusinessException
	 */
	public ActivePerson getPerson() throws BusinessException
	{
		return person;
	}

	/**
	 * ����� ��������������� ���� �������� � ��������� ��������� ������������
	 * @param useInternetSecurity
	 * @throws BusinessException
	 */
	public void setUseInternetSecurity(final Boolean useInternetSecurity) throws BusinessException
	{
		UserPropertiesConfig.processUserSettingsWithoutPersonContext(person.getLogin(), new SettingsProcessor<Void>()
		{
			public Void onExecute(UserPropertiesConfig userProperties)
			{
				userProperties.setUseInternetSecurity(useInternetSecurity);
				PersonSettingsManager.savePersonData(PersonSettingsManager.NEED_USE_INTERNET_SECURITY_KEY, useInternetSecurity);
				return null;
			}
		});
	}
}