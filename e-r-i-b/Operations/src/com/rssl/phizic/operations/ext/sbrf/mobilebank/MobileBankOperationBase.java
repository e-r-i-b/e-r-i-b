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
	 * »нициализаци€ операции
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
	 * ѕолучение шорткатов регистраций
	 * @return список шорткатов регистраций (без SMS-запросов)
	 */
	public List<RegistrationShortcut> getRegistrationShortcuts() throws BusinessException, BusinessLogicException
	{
		return mobileBankService.getRegistrationShortcuts(getLogin());
	}

	/**
	 * ѕолучение шортката регистрации по телефону и карте
	 * @param phoneCode - закодированный номер телефона (хеш-код номера в виде строки)
	 * @param cardCode - закодированный номер карты (последние N цифр номера)
	 * @return шорткат регистрации (без SMS-запросов)
	 */
	protected RegistrationShortcut getRegistrationShortcut(String phoneCode, String cardCode) throws BusinessException, BusinessLogicException
	{
		return mobileBankService.getRegistrationShortcut(getLogin(), phoneCode, cardCode);
	}

	/**
	 * ѕолучение профилей регистраций
	 * @return список профилей регистраций (без SMS-запросов)
	 */
	public List<RegistrationProfile> getRegistrationProfiles() throws BusinessException, BusinessLogicException
	{
		return mobileBankService.getRegistrationProfiles(getLogin());
	}
}
