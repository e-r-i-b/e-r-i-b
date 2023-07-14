package com.rssl.phizic.web.client.userprofile;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Форма редактирования настроек смс-оповещений по продуктам
 * @author lukina
 * @ created 10.07.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditProductNotificationsForm  extends EditFormBase
{
	private List<CardLink> cards = new ArrayList<CardLink>();
	private String[] selectedCardIds = new String[0];
	private List<AccountLink> accounts = new ArrayList<AccountLink>();
	private String[] selectedAccountIds = new String[0];
	private List<LoanLink> loans = new ArrayList<LoanLink>();
	private String[] selectedLoanIds = new String[0];

	private ConfirmableObject productsSmsNotificationConfirmableObject;
	private ConfirmStrategy productsSmsNotificationConfirmStrategy;
	private ConfirmableObject confirmableObject;

	private boolean newProductsNotification;
	private boolean showResourcesSmsNotificationBlock;
	private boolean isTariffAllowCardSmsNotification;
	private boolean isTariffAllowAccountSmsNotification;

	public List<CardLink> getCards()
	{
		return cards;
	}

	public void setCards(List<CardLink> cards)
	{
		this.cards = cards;
	}

	public String[] getSelectedCardIds()
	{
		return selectedCardIds;
	}

	public void setSelectedCardIds(String[] selectedCardIds)
	{
		this.selectedCardIds = selectedCardIds;
	}

	public List<AccountLink> getAccounts()
	{
		return accounts;
	}

	public void setAccounts(List<AccountLink> accounts)
	{
		this.accounts = accounts;
	}

	public String[] getSelectedAccountIds()
	{
		return selectedAccountIds;
	}

	public void setSelectedAccountIds(String[] selectedAccountIds)
	{
		this.selectedAccountIds = selectedAccountIds;
	}

	public List<LoanLink> getLoans()
	{
		return loans;
	}

	public void setLoans(List<LoanLink> loans)
	{
		this.loans = loans;
	}

	public String[] getSelectedLoanIds()
	{
		return selectedLoanIds;
	}

	public void setSelectedLoanIds(String[] selectedLoanIds)
	{
		this.selectedLoanIds = selectedLoanIds;
	}

	public ConfirmableObject getProductsSmsNotificationConfirmableObject()
	{
		return productsSmsNotificationConfirmableObject;
	}

	public void setProductsSmsNotificationConfirmableObject(ConfirmableObject productsSmsNotificationConfirmableObject)
	{
		this.productsSmsNotificationConfirmableObject = productsSmsNotificationConfirmableObject;
	}

	public ConfirmStrategy getProductsSmsNotificationConfirmStrategy()
	{
		return productsSmsNotificationConfirmStrategy;
	}

	public void setProductsSmsNotificationConfirmStrategy(ConfirmStrategy productsSmsNotificationConfirmStrategy)
	{
		this.productsSmsNotificationConfirmStrategy = productsSmsNotificationConfirmStrategy;
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public boolean isNewProductsNotification()
	{
		return newProductsNotification;
	}

	public void setNewProductsNotification(boolean newProductsNotification)
	{
		this.newProductsNotification = newProductsNotification;
	}

	public boolean isShowResourcesSmsNotificationBlock()
	{
		return showResourcesSmsNotificationBlock;
	}

	public void setShowResourcesSmsNotificationBlock(boolean showResourcesSmsNotificationBlock)
	{
		this.showResourcesSmsNotificationBlock = showResourcesSmsNotificationBlock;
	}

	public boolean isTariffAllowCardSmsNotification()
	{
		return isTariffAllowCardSmsNotification;
	}

	public void setTariffAllowCardSmsNotification(boolean tariffAllowCardSmsNotification)
	{
		isTariffAllowCardSmsNotification = tariffAllowCardSmsNotification;
	}

	public boolean isTariffAllowAccountSmsNotification()
	{
		return isTariffAllowAccountSmsNotification;
	}

	public void setTariffAllowAccountSmsNotification(boolean tariffAllowAccountSmsNotification)
	{
		isTariffAllowAccountSmsNotification = tariffAllowAccountSmsNotification;
	}
}
