package com.rssl.phizic.operations.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankBusinessService;
import com.rssl.phizic.business.ext.sbrf.mobilebank.RegistrationProfile;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.mobilebank.QuickServiceStatusCode;
import com.rssl.phizic.operations.ConfirmableOperationBase;

/**
 * Операция служит для изменения статуса Быстрых сервисов 
 *
 * User: Balovtsev
 * Date: 24.05.2012
 * Time: 10:41:41
 */
public class ChangeQuickServiceStatusOperation extends ConfirmableOperationBase
{
	private static final MobileBankBusinessService mobileBankBusinessService = new MobileBankBusinessService();

	private RegistrationProfile profile;
	private ActivePerson        person;

	/**
	 *
	 * При инициализации указываем для какого профиля будем менять статус сервиса
	 *
	 * @param profile изменяемый профиль
	 */
	public void initialize(RegistrationProfile profile)
	{
		this.profile = profile;
		this.person  = PersonContext.getPersonDataProvider().getPersonData().getPerson();
	}

	protected void saveConfirm() throws BusinessException
	{
		QuickServiceStatusCode newCode = null;

		switch( profile.getQuickServiceStatusCode() )
		{
			case QUICK_SERVICE_STATUS_ON:
			{
				newCode = QuickServiceStatusCode.QUICK_SERVICE_STATUS_OFF;
				break;
			}
			case QUICK_SERVICE_STATUS_OFF:
			{
				newCode = QuickServiceStatusCode.QUICK_SERVICE_STATUS_ON;
				break;
			}
		}

		profile.setQuickServiceStatusCode( mobileBankBusinessService.setQuickServiceStatus(profile.getMainPhoneNumber(), newCode) );
	}

	public RegistrationProfile getConfirmableObject()
	{  
		return profile;
	}

	public ActivePerson getPerson()
	{
		return person;
	}
}
