package com.rssl.phizic.operations.account;

import com.rssl.phizic.auth.modes.ConfirmStrategySource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.profileSynchronization.products.ResourceInfoSynchronizationHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.operations.access.NullConfirmStrategySource;
import com.rssl.phizic.operations.userprofile.AccountsAvailableSettings;
import com.rssl.phizic.operations.userprofile.DeviceType;
import com.rssl.phizic.security.ConfirmableObject;

import java.util.List;

/**
 * @author lukina
 * @ created 19.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class SetupProductsMobileViewOperation  extends SetupProductsSystemViewOperation
{
	private boolean isOnlyUnvisible;                 // выбрано только отключение видимости продуктов

	public void initialize() throws BusinessException, BusinessLogicException
	{
		super.initialize();
		isOnlyUnvisible = true;
	}

	/**
	 * Установить новые значения настроек видимости в iPhone/iPad для счетов
	 * @param selectedIds Список идентификаторов (id) видимых счетов
	 * @throws BusinessException
	 */
	private void updateAccountSettingsInMobile(String[] selectedIds) throws BusinessException
	{
		for (AccountLink accountLink : accounts)
		{
			Long currentId = accountLink.getId();
			Boolean state = accountLink.getShowInMobile();
			if (state != null)
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

				if (state^newState)
				{
					accountLink.setShowInMobile(newState);
					addChangedResource(accountLink);
					if (newState)
						isOnlyUnvisible = false;
				}
			}
		}
	}

	/**
	 * Установить новые значения настроек видимости в iPhone/iPad для карт
	 * @param selectedIds Список идентификаторов (id) видимых карт
	 * @throws BusinessException
	 */
	private void updateCardSettingsInMobile(String[] selectedIds) throws BusinessException
	{
		for (CardLink cardLink : cards)
		{
			Long currentId = cardLink.getId();
			boolean state = cardLink.getShowInMobile();
			boolean newState = false;

			for (String cardId : selectedIds)
			{
				if (currentId.equals(Long.valueOf(cardId)))
				{
					newState = true;
					break;
				}
			}

			if (state^newState)
			{
				cardLink.setShowInMobile(newState);
				addChangedResource(cardLink);
				if (newState)
						isOnlyUnvisible = false;
			}
		}
	}

	/**
	 * Установить новые значения настроек видимости в iPhone/iPad для кредитов
	 * @param selectedIds Список идентификаторов (id) видимых кредитов
	 * @throws BusinessException
	 */
	private void updateLoanSettingsInMobile(String[] selectedIds) throws BusinessException
	{
		for (LoanLink loanLink : loans)
		{
			Long currentId = loanLink.getId();
			boolean state = loanLink.getShowInMobile();
			boolean newState = false;

			for (String loanId : selectedIds)
			{
				if (currentId.equals(Long.valueOf(loanId)))
				{
					newState = true;
					break;
				}
			}

			if (state^newState)
			{
				loanLink.setShowInMobile(newState);
				addChangedResource(loanLink);
				if (newState)
						isOnlyUnvisible = false;
			}
		}
	}

	/**
	 * Установить новые значения настроек видимости в iPhone/iPad для ОМС
	 * @param selectedIds Список идентификаторов (id) видимых ОМС
	 * @throws BusinessException
	 */
	private void updateIMAccountSettingsInMobile(String[] selectedIds) throws BusinessException
	{
		for (IMAccountLink imAccountLink : imAccounts)
		{
			Long currentId = imAccountLink.getId();
			Boolean state = imAccountLink.getShowInMobile();
			if (state != null)
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

				if (state^newState)
				{
					imAccountLink.setShowInMobile(newState);
					addChangedResource(imAccountLink);
					if (newState)
						isOnlyUnvisible = false;
				}
			}
		}
	}

	/**
	 * Установить новые значения настроек видимости в iPhone/iPad
	 * @param selectedAccountIds Список идентификаторов (id) видимых счетов
	 * @param selectedCardIds Список идентификаторов (id) видимых карт
	 * @param selectedLoanIds Список идентификаторов (id) видимых кредитов
	 * @param selectedIMAccountIds Список идентификаторов (id) видимых ОМС
	 * @throws BusinessException Ошибка при работе с бизнес-сервисами
	 * @throws BusinessLogicException Ошибка отправки СМС с подтверждением
	 */
	public void updateResourcesSettingsInMobile(String[] selectedAccountIds, String[] selectedCardIds, String[] selectedLoanIds, String[] selectedIMAccountIds) throws BusinessException, BusinessLogicException
	{
		updateAccountSettingsInMobile(selectedAccountIds);
		updateCardSettingsInMobile(selectedCardIds);
		updateLoanSettingsInMobile(selectedLoanIds);
		updateIMAccountSettingsInMobile(selectedIMAccountIds);
	}


	public ConfirmableObject getConfirmableObject()
	{
		if (confirmableObject == null)
			confirmableObject = new AccountsAvailableSettings(
					(List<AccountLink>)changedResources.get(AccountLink.class),
					(List<CardLink>)changedResources.get(CardLink.class),
					(List<LoanLink>)changedResources.get(LoanLink.class),
					(List<IMAccountLink>)changedResources.get(IMAccountLink.class),
					DeviceType.MOBILE);
		return confirmableObject;
	}


	public void saveConfirm() throws BusinessException, BusinessLogicException
	{
		updateLink(CardLink.class, Card.class);
		updateLink(LoanLink.class, Loan.class);
		updateAccountLinks();
		updateLink(IMAccountLink.class, IMAccount.class);

		ResourceInfoSynchronizationHelper.updateResourceInfo(CardLink.class);
		ResourceInfoSynchronizationHelper.updateResourceInfo(LoanLink.class);
		ResourceInfoSynchronizationHelper.updateResourceInfo(AccountLink.class);
		ResourceInfoSynchronizationHelper.updateResourceInfo(IMAccountLink.class);
	}

	public ConfirmStrategySource getStrategy()
	{
		return isOnlyUnvisible ? new NullConfirmStrategySource() : super.getStrategy();
	}
}
