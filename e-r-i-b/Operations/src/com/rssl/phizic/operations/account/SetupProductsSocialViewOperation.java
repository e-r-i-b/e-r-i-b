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
 * @author sergunin
 * @ created 31.08.2014
 * @ $Author$
 * @ $Revision$
 */

public class SetupProductsSocialViewOperation extends SetupProductsSystemViewOperation
{
	private boolean isOnlyUnvisible;                 // выбрано только отключение видимости продуктов

	public void initialize() throws BusinessException, BusinessLogicException
	{
		super.initialize();
		isOnlyUnvisible = true;
	}

	/**
	 * Установить новые значения настроек видимости в соц. приложениях для счетов
	 * @param selectedIds Список идентификаторов (id) видимых счетов
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	private void updateAccountSettingsInSocial(String[] selectedIds) throws BusinessException
	{
		for (AccountLink accountLink : accounts)
		{
			Long currentId = accountLink.getId();
			Boolean state = accountLink.getShowInSocial();
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
					accountLink.setShowInSocial(newState);
					addChangedResource(accountLink);
					if (newState)
						isOnlyUnvisible = false;
				}
			}
		}
	}

	/**
	 * Установить новые значения настроек видимости в соц. приложениях для карт
	 * @param selectedIds Список идентификаторов (id) видимых карт
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	private void updateCardSettingsInSocial(String[] selectedIds) throws BusinessException
	{
		for (CardLink cardLink : cards)
		{
			Long currentId = cardLink.getId();
			boolean state = cardLink.getShowInSocial();
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
				cardLink.setShowInSocial(newState);
				addChangedResource(cardLink);
				if (newState)
						isOnlyUnvisible = false;
			}
		}
	}

	/**
	 * Установить новые значения настроек видимости в соц. приложениях для кредитов
	 * @param selectedIds Список идентификаторов (id) видимых кредитов
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	private void updateLoanSettingsInSocial(String[] selectedIds) throws BusinessException
	{
		for (LoanLink loanLink : loans)
		{
			Long currentId = loanLink.getId();
			boolean state = loanLink.getShowInSocial();
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
				loanLink.setShowInSocial(newState);
				addChangedResource(loanLink);
				if (newState)
						isOnlyUnvisible = false;
			}
		}
	}

	/**
	 * Установить новые значения настроек видимости в соц. приложениях для ОМС
	 * @param selectedIds Список идентификаторов (id) видимых ОМС
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	private void updateIMAccountSettingsInSocial(String[] selectedIds) throws BusinessException
	{
		for (IMAccountLink imAccountLink : imAccounts)
		{
			Long currentId = imAccountLink.getId();
			Boolean state = imAccountLink.getShowInSocial();
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
					imAccountLink.setShowInSocial(newState);
					addChangedResource(imAccountLink);
					if (newState)
						isOnlyUnvisible = false;
				}
			}
		}
	}

	/**
	 * Установить новые значения настроек видимости в соц. приложениях
	 * @param selectedAccountIds Список идентификаторов (id) видимых счетов
	 * @param selectedCardIds Список идентификаторов (id) видимых карт
	 * @param selectedLoanIds Список идентификаторов (id) видимых кредитов
	 * @param selectedIMAccountIds Список идентификаторов (id) видимых ОМС
	 * @throws com.rssl.phizic.business.BusinessException Ошибка при работе с бизнес-сервисами
	 * @throws com.rssl.phizic.business.BusinessLogicException Ошибка отправки СМС с подтверждением
	 */
	public void updateResourcesSettingsInSocial(String[] selectedAccountIds, String[] selectedCardIds, String[] selectedLoanIds, String[] selectedIMAccountIds) throws BusinessException, BusinessLogicException
	{
		updateAccountSettingsInSocial(selectedAccountIds);
		updateCardSettingsInSocial(selectedCardIds);
		updateLoanSettingsInSocial(selectedLoanIds);
		updateIMAccountSettingsInSocial(selectedIMAccountIds);
	}


	public ConfirmableObject getConfirmableObject()
	{
		if (confirmableObject == null)
			confirmableObject = new AccountsAvailableSettings(
					(List<AccountLink>)changedResources.get(AccountLink.class),
					(List<CardLink>)changedResources.get(CardLink.class),
					(List<LoanLink>)changedResources.get(LoanLink.class),
					(List<IMAccountLink>)changedResources.get(IMAccountLink.class),
					DeviceType.SOCIAL);
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
