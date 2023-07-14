package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.*;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbProfileEvent;
import com.rssl.phizic.business.ermb.profile.events.ErmbResourseParamsEvent;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.operations.account.SetupProductsSystemViewOperation;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.ConfirmableObject;

import java.util.List;

/**
 * Операция настройки SMS-оповещений по продуктам
 * @author Rtischeva
 * @ created 06.11.13
 * @ $Author$
 * @ $Revision$
 */
public class SetupResourcesSmsNotificationOperation extends SetupProductsSystemViewOperation
{
	private boolean showResourcesSmsNotificationBlock;
	private boolean isTariffAllowCardSmsNotification;
	private boolean isTariffAllowAccountSmsNotification;
	private boolean isTariffAllowLoanSmsNotification;
	private ErmbProfileImpl ermbProfile;
	private ErmbResourseParamsEvent resourceEvent;
	private boolean newProductNotification;
	private boolean newProductNotificationChanged;
	private ErmbProfileEvent profileEvent;

	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	public void initialize() throws BusinessException, BusinessLogicException
	{
		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		PersonData personData = dataProvider.getPersonData();

		Person person = personData.getPerson();
		login = person.getLogin();
		accounts = personData.getAccountsAll();
		cards = personData.getCardsAll();
		loans = personData.getLoansAll();

		ermbProfile = ErmbHelper.getErmbProfileByPerson(person);
		showResourcesSmsNotificationBlock = ermbProfile != null && !ErmbHelper.isCommonAttributeUseInProductsAvailable();
		if (ermbProfile != null)
		{
			ErmbPermissionCalculator permissionCalculator = new ErmbPermissionCalculator(ermbProfile);

			isTariffAllowCardSmsNotification = permissionCalculator.impliesCardNotification();
			isTariffAllowAccountSmsNotification = permissionCalculator.impliesAccountNotification();
			isTariffAllowLoanSmsNotification = permissionCalculator.impliesLoanNotification();
			newProductNotification = ermbProfile.getNewProductNotification();
		}
	}

	/**
	 * Установить новые значения настроек оповещений в смс-канале для счетов, вкладов, кредитов
	 * @param selectedAccountIds Список идентификаторов (id) счетов, по которым будут отправляться оповещения
	 * @param selectedCardIds Список идентификаторов (id) карт, по которым будут отправляться оповещения
	 * @param selectedLoanIds Список идентификаторов (id) кредитов, по которым будут отправляться оповещения
	 * @throws com.rssl.phizic.business.BusinessException Ошибка при работе с бизнес-сервисами
	 */
	public void updateResourcesNotificationSettings(String[] selectedAccountIds,
	                                     String[] selectedCardIds,
	                                     String[] selectedLoanIds, boolean newProductsNotification) throws BusinessException
	{
		resourceEvent = new ErmbResourseParamsEvent(ermbProfile);
		resourceEvent.setOldData(cards, accounts, loans);

		if (isTariffAllowAccountSmsNotification)
			updateResourceSmsNotification(selectedAccountIds, accounts);

		if (isTariffAllowCardSmsNotification)
			updateResourceSmsNotification(selectedCardIds, cards);

		if (isTariffAllowLoanSmsNotification)
			updateResourceSmsNotification(selectedLoanIds, loans);

		if (ermbProfile.getNewProductNotification() != newProductsNotification)
		{
			profileEvent = new ErmbProfileEvent(ErmbHelper.copyProfile(ermbProfile));
			ermbProfile.setNewProductNotification(newProductsNotification);
			profileEvent.setNewProfile(ermbProfile);

			this.newProductNotification = newProductsNotification;
			newProductNotificationChanged = true;
		}
	}

	private void updateResourceSmsNotification(String[] selectedIds, List<? extends ErmbProductLink> list)
	{
		for (ErmbProductLink link : list)
		{
			Long currentId = link.getId();
			Boolean esState = link.getErmbNotification();

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
					link.setErmbNotification(newState);
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
								(List<LoanLink>)changedResources.get(LoanLink.class), newProductNotificationChanged ? newProductNotification : null, true);
		return confirmableObject;
	}

	@Override
	public void saveConfirm() throws BusinessException, BusinessLogicException
	{
		updateLink(CardLink.class, Card.class);
		updateLink(LoanLink.class, Loan.class);
		updateAccountLinks();

		ErmbUpdateListener updateListener = ErmbUpdateListener.getListener();
		resourceEvent.setNewData(cards, accounts, loans);
		updateListener.onResoursesUpdate(resourceEvent);

		if (newProductNotificationChanged)
		{
			updateListener.beforeProfileUpdate(profileEvent);
			profileService.addOrUpdate(ermbProfile);
			updateListener.afterProfileUpdate(profileEvent);
		}
	}

	@Override
	public boolean noChanges()
	{
		return super.noChanges() && !newProductNotificationChanged;
	}

	public boolean isShowResourcesSmsNotificationBlock()
	{
		return showResourcesSmsNotificationBlock;
	}

	public boolean isTariffAllowCardSmsNotification()
	{
		return isTariffAllowCardSmsNotification;
	}

	public boolean isTariffAllowAccountSmsNotification()
	{
		return isTariffAllowAccountSmsNotification;
	}

	public boolean isNewProductNotificationChanged()
	{
		return newProductNotificationChanged;
	}

	public void setNewProductNotificationChanged(boolean newProductNotificationChanged)
	{
		this.newProductNotificationChanged = newProductNotificationChanged;
	}

	public boolean isNewProductNotification()
	{
		return newProductNotification;
	}

	public void setNewProductNotification(boolean newProductNotification)
	{
		this.newProductNotification = newProductNotification;
	}
}
