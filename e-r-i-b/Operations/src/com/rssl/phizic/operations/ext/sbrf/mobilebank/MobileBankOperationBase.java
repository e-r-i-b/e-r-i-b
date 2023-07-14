package com.rssl.phizic.operations.ext.sbrf.mobilebank;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankBusinessService;
import com.rssl.phizic.business.ext.sbrf.mobilebank.RegistrationProfile;
import com.rssl.phizic.business.ext.sbrf.mobilebank.RegistrationShortcut;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * @author Erkin
 * @ created 05.05.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class MobileBankOperationBase extends OperationBase
{
	protected static final MobileBankBusinessService mobileBankService = new MobileBankBusinessService();

	private ActivePerson person;

	private Login login;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ������������� ��������
	 */
	protected void initialize() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		this.person = personData.getPerson();
		this.login = person.getLogin();
	}

	protected ActivePerson getPerson()
	{
		return person;
	}

	protected Login getLogin()
	{
		return login;
	}

	/**
	 * ��������� ��������� �����������
	 * @return ������ ��������� ����������� (��� SMS-��������)
	 */
	public List<RegistrationShortcut> getRegistrationShortcuts() throws BusinessException, BusinessLogicException
	{
		return mobileBankService.getRegistrationShortcuts(getLogin());
	}

	/**
	 * ��������� �������� ����������� �� �������� � �����
	 * @param phoneCode - �������������� ����� �������� (���-��� ������ � ���� ������)
	 * @param cardCode - �������������� ����� ����� (��������� N ���� ������)
	 * @return ������� ����������� (��� SMS-��������)
	 */
	protected RegistrationShortcut getRegistrationShortcut(String phoneCode, String cardCode) throws BusinessException, BusinessLogicException
	{
		return mobileBankService.getRegistrationShortcut(getLogin(), phoneCode, cardCode);
	}

	/**
	 * ��������� �������� �����������
	 * @return ������ �������� ����������� (��� SMS-��������)
	 */
	public List<RegistrationProfile> getRegistrationProfiles() throws BusinessException, BusinessLogicException
	{
		return mobileBankService.getRegistrationProfiles(getLogin());
	}
}
