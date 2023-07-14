package com.rssl.phizic.web.common.client.ext.sbrf.resources;

import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;

/**
 * Обновление в профайле из PersonContext информации по тарифному плану клиента с учетом
 * доступности права на "Льготные курсы".
 *
 *  Экшен должен вызываться ПОСЛЕ установки PersonContext
 * @ author: Gololobov
 * @ created: 12.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class AddOrUpdateClientTarifPlanCodeType implements AthenticationCompleteAction
{
	private static final ProfileService profileService = new ProfileService();
	private static final PersonService personService = new PersonService();

	public void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException
	{
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			ActivePerson person = personData.getPerson();

			// Проверить актуальность ТП
			TariffPlanConfig tariffPlanConfig = TariffPlanHelper.getActualTariffPlanByCode(person.getTarifPlanCodeType());
			if (tariffPlanConfig == null)
			{
				person.setTarifPlanCodeType(TariffPlanHelper.getUnknownTariffPlanCode());
				person.setTarifPlanConnectionDate(null);
				personService.update(person);
			}

			String personCurrentTarifPlanCodeType =
					!PermissionUtil.impliesService("ReducedRateService") || StringHelper.isEmpty(person.getTarifPlanCodeType()) ?
							TariffPlanHelper.getUnknownTariffPlanCode() : person.getTarifPlanCodeType();

			Profile profile = personData.getProfile();
			String profileTarifPlanCodeType = TariffPlanHelper.getTariffPlanCode(profile.getTariffPlanCode());

			if (StringHelper.isEmpty(profile.getTariffPlanCode()) || !StringHelper.equalsNullIgnore(profileTarifPlanCodeType, personCurrentTarifPlanCodeType))
			{
				profile.setTariffPlanCode(personCurrentTarifPlanCodeType);
				profileService.update(profile);
			}
		}
		catch (BusinessException e)
		{
			throw new SecurityException(e);
		}
	}
}
