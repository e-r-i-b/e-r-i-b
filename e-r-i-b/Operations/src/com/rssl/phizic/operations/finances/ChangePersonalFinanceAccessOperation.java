package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author rydvanskiy
 * @ created 25.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class ChangePersonalFinanceAccessOperation extends OperationBase
{
	private static final ProfileService profileService = new ProfileService();

	private Person person;
	/**
	 * ������� ������������
	 */
	private Profile profile;

	public void initialize() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		person = personData.getPerson();
		profile = personData.getProfile();
	}

	/**
	 *
	 * @return ���������� �� ������������ ������������ �������
	 */
	public Boolean  isShowPersonalFinance()
	{
		return profile.isShowPersonalFinance();
	}

	/**
	 * ����� ��� ��������� �������� ��������������������� ��������
	 * @param showPesonalFinance
	 */
	public void setShowPersonalFinance(Boolean showPesonalFinance)
	{
		profile.setShowPersonalFinance(showPesonalFinance);
		if (showPesonalFinance)
			profile.setStartUsePersonalFinance(DateHelper.startOfDay(Calendar.getInstance()));
	}
	
	/**
	 * ��������� ��������� ������� � ������������ ��������
	 */
	public void save() throws BusinessException
	{
		profileService.update(profile);
	}
}
