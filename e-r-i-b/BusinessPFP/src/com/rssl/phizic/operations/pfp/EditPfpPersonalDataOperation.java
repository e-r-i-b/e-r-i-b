package com.rssl.phizic.operations.pfp;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.pfp.PersonTarget;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.CreditCardFilter;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.security.PermissionUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author mihaylov
 * @ created 25.03.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция для заполнения личных данных клиента. Определения целей клиента.
 */
public class EditPfpPersonalDataOperation extends EditPfpOperationBase
{
	private static final State INITIAL = new State("INITIAL");
	private static final CreditCardFilter CREDIT_CARD_FILTER = new CreditCardFilter();
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	protected void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException, BusinessLogicException
	{
		checkState(INITIAL);
	}

	/**
	 * @return персона клиента
	 */
	public ActivePerson getPerson()
	{
		return super.getPerson();
	}

	/**
	 * Сохранение профиля
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */	
	public void savePersonalData() throws BusinessException, BusinessLogicException
	{
		fireEvent(DocumentEvent.SAVE_PERSONAL_DATA);
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

	/**
	 * @return количество кредитных карт
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public int getCreditCardCount() throws BusinessException, BusinessLogicException
	{
		List<CardLink> cardLinkList = externalResourceService.getLinks(getPerson().getLogin(),CardLink.class);
		if (CollectionUtils.isEmpty(cardLinkList))
			return 0;

		CollectionUtils.filter(cardLinkList, CREDIT_CARD_FILTER);
		return cardLinkList.size();
	}

	/**
	 * @return Возвращает сумму средств на всех счетах и картах клиента в рублевом эквиваленте по курсу ЦБ
	 * @throws BusinessException
	 */
	public Money getPersonShortTermAssets() throws BusinessException
	{
		try
		{
			String tarifPlanCodeType = !PermissionUtil.impliesService("ReducedRateService") ?
					TariffPlanHelper.getUnknownTariffPlanCode() : PersonHelper.getActivePersonTarifPlanCode();
			Money shortTermAssets = PersonHelper.getPersonAccountsMoney(tarifPlanCodeType);
			shortTermAssets = shortTermAssets.add(PersonHelper.getPersonCardsMoney(tarifPlanCodeType));
			if (PermissionUtil.impliesService("SecurityAccountService"))
				shortTermAssets = shortTermAssets.add(PersonHelper.getPersonSecurityAccountMoney(tarifPlanCodeType));
			return shortTermAssets;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return Возвращает сумму средств на ОМС клиента в рублевом эквиваленте по курсу ЦБ
	 * @throws BusinessException
	 */
	public Money getPersonIMAccounts() throws BusinessException
	{
		try
		{
			String tarifPlanCodeType = !PermissionUtil.impliesService("ReducedRateService") ?
					TariffPlanHelper.getUnknownTariffPlanCode() : PersonHelper.getActivePersonTarifPlanCode();
			return PersonHelper.getPersonIMAccountMoney(tarifPlanCodeType);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return возвращает список целей клиента
	 */
	public List<PersonTarget> getPersonTargetList()
	{
		return personalFinanceProfile.getPersonTargets();
	}

}
