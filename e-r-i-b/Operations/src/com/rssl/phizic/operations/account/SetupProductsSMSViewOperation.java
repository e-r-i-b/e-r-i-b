package com.rssl.phizic.operations.account;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.profile.ErmbResourseListener;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbResourseParamsEvent;
import com.rssl.phizic.business.profileSynchronization.products.ResourceInfoSynchronizationHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.operations.userprofile.AccountsAvailableSettings;
import com.rssl.phizic.security.ConfirmableObject;

import java.util.List;

/**
 * @author EgorovaA
 * @ created 29.05.2013
 * @ $Author$
 * @ $Revision$
 *
 * Операция изменения видимости продуктов клиента в смс-канале
 */
public class SetupProductsSMSViewOperation  extends SetupProductsSystemViewOperation
{
	private ErmbResourseParamsEvent event;
	private boolean newProductShowInSmsChanged;

	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	public void initialize() throws BusinessException, BusinessLogicException
	{
		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		PersonData personData = dataProvider.getPersonData();
		login = personData.getPerson().getLogin();

		accounts = personData.getAccountsAll();
		cards = personData.getCardsAll();
		loans = personData.getLoansAll();

		event = new ErmbResourseParamsEvent(personData.getPerson().getId());
		event.setOldData(cards, accounts, loans);

		ermbProfile = ErmbHelper.getErmbProfileByLogin(login);
	}

	/**
	 * Установить новые значения настроек видимости в смс-канале для счетов, вкладов, кредитов
	 * @param selectedAccountSMSIds Список идентификаторов (id) видимых счетов
	 * @param selectedCardSMSIds Список идентификаторов (id) видимых карт
	 * @param selectedLoanSMSIds Список идентификаторов (id) видимых кредитов
	 * @throws com.rssl.phizic.business.BusinessException Ошибка при работе с бизнес-сервисами
	 */
	public void updateResourcesSettings(String[] selectedAccountSMSIds,
	                                     String[] selectedCardSMSIds,
	                                     String[] selectedLoanSMSIds, boolean newProductShowInSms) throws BusinessException
	{
		updateResourceSettingsInErmb(selectedAccountSMSIds, accounts);
		updateResourceSettingsInErmb(selectedCardSMSIds, cards);
		updateResourceSettingsInErmb(selectedLoanSMSIds, loans);
		if (ermbProfile.getNewProductShowInSms() != newProductShowInSms)
		{
			ermbProfile.setNewProductShowInSms(newProductShowInSms);
			this.newProductShowInSms = newProductShowInSms;
			newProductShowInSmsChanged = true;
		}
	}

	private void updateResourceSettingsInErmb(String[] selectedIds, List<? extends ErmbProductLink> list)
	{
		for (ErmbProductLink link : list)
		{
			Long currentId = link.getId();
			Boolean esState = link.getShowInSms();

			if (esState != null)
			{
				Boolean newState = false;
				for (String accountId : selectedIds)
				{
					if (currentId.equals(Long.valueOf(accountId)))
					{
						newState = true;
						break;
					}
				}

				if (esState^newState)
				{
					link.setShowInSms(newState);
					addChangedResource(link);
				}
			}
		}
	}

	public ConfirmableObject getConfirmableObject()
	{
		if (confirmableObject == null)
			confirmableObject = new AccountsAvailableSettings(
					(List<AccountLink>)changedResources.get(AccountLink.class),
					(List<CardLink>)changedResources.get(CardLink.class),
					(List<LoanLink>)changedResources.get(LoanLink.class), newProductShowInSmsChanged ? newProductShowInSms : null, false);

		return confirmableObject;
	}

	public void saveConfirm() throws BusinessException, BusinessLogicException
	{
		updateLink(CardLink.class, Card.class);
		updateLink(LoanLink.class, Loan.class);
		updateAccountLinks();

		if (newProductShowInSmsChanged)
			profileService.addOrUpdate(ermbProfile);

		ResourceInfoSynchronizationHelper.updateResourceInfo(CardLink.class);
		ResourceInfoSynchronizationHelper.updateResourceInfo(LoanLink.class);
		ResourceInfoSynchronizationHelper.updateResourceInfo(AccountLink.class);

		event.setNewData(cards, accounts, loans);
		ErmbResourseListener updateListener = ErmbUpdateListener.getListener();
		updateListener.onResoursesUpdate(event);
	}

	@Override
	public boolean noChanges()
	{
		return super.noChanges() && !newProductShowInSmsChanged;
	}
}
