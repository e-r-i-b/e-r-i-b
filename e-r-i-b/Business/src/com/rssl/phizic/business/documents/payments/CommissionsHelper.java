package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.utils.StringHelper;

/**
 * Created with IntelliJ IDEA.
 * User: tisov
 * Date: 14.08.15
 * Time: 16:51
 * ������ �� ���������
 */
public class CommissionsHelper
{

	/**
	 * ������������ ��������� � ��������
 	 * @param commissionValue - �������� ��������
	 * @param tariffPlanESB - �������� ����
	 * @param person - ������
	 * @return
	 * @throws BusinessException
	 */
	public static String getCommissionMessage(String commissionValue, String tariffPlanESB, ActivePerson person) throws BusinessException
	{

		if (StringHelper.isEmpty(tariffPlanESB))
			return DocumentHelper.getSettingMessage("commission.info.message") + " " + commissionValue;
		ProfileService profileService = new ProfileService();
		Profile profile = profileService.findByLogin(person.getLogin());
		TariffPlanConfig activePersonTarifPlanCode = TariffPlanHelper.getActualTariffPlanByCode(TariffPlanHelper.getTariffPlanCode(profile.getTariffPlanCode()));
		if (activePersonTarifPlanCode != null && !TariffPlanHelper.isUnknownTariffPlan(activePersonTarifPlanCode.getCode()) && tariffPlanESB.equals(activePersonTarifPlanCode.getName()))
			return DocumentHelper.getSettingMessage("commission.discount.info.message") + " " + activePersonTarifPlanCode.getName();
		return DocumentHelper.getSettingMessage("commission.info.message") + " " + commissionValue;
	}

	/**
	 * ������������ ��������� � ��������
	 * @param commissionValue - �������� ��������
	 * @param tariffPlanESB - �������� ����
	 * @param loginId - ����� �������
	 * @return
	 * @throws BusinessException
	 */
	public static String getCommissionMessage(String commissionValue, String tariffPlanESB, Long loginId) throws BusinessException
	{
	 	return getCommissionMessage(commissionValue, tariffPlanESB, new PersonService().findByLoginId(loginId));
	}

}
