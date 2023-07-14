package com.rssl.phizic.web.ext.sbrf.products;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.pfr.PFRLink;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author potehin
 * @ created 21.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ProductsSetupForm extends ListFormBase
{
	private List<AccountLink> accounts = new ArrayList<AccountLink>();
	private String[] selectedAccountIds = new String[0];
	private String[] selectedAccountMobileIds = new String[0];
	private String[] selectedAccountSocialIds = new String[0];
	private String[] selectedAccountESIds = new String[0];
	private String[] selectedAccountSMSIds = new String[0];
	private List<CardLink> cards = new ArrayList<CardLink>();
	private String[] selectedCardIds = new String[0];
	private String[] selectedCardMobileIds = new String[0];
	private String[] selectedCardSocialIds = new String[0];
	private String[] selectedCardESIds = new String[0];
	private String[] selectedCardSMSIds = new String[0];
	private List<LoanLink> loans = new ArrayList<LoanLink>();
	private String[] selectedLoanIds = new String[0];
	private String[] selectedLoanMobileIds = new String[0];
	private String[] selectedLoanSocialIds = new String[0];
	private String[] selectedLoanSMSIds = new String[0];
	private String[] selectedLoanESIds = new String[0];
	private List<DepoAccountLink> depoAccounts = new ArrayList<DepoAccountLink>();
	private String[] selectedDepoAccountIds = new String[0];
	private List<IMAccountLink> imAccounts = new ArrayList<IMAccountLink>();
	private String[] selectedIMAccountIds = new String[0];
	private String[] selectedIMAccountMobileIds = new String[0];
	private String[] selectedIMAccountSocialIds = new String[0];
	private String[] selectedIMAccountESIds = new String[0];
	private List<SecurityAccountLink> securityAccounts = new ArrayList<SecurityAccountLink>();
	private String[] selectedSecurityAccountIds = new String[0];
	private boolean newProductsShowInSms;

	private ConfirmableObject confirmableObject;
	private PFRLink pfrLink;
	private boolean isPfrLinkSelected;
	private String SNILS;
	private ActivePerson activePerson;
	private Boolean modified = false;
	private String currentPage;
    private ConfirmStrategy confirmStrategy;

	private Integer[] sortedCardIds = new Integer[0];
	private Integer[] sortedAccountIds = new Integer[0];
	private Integer[] sortedLoanIds = new Integer[0];
	private Integer[] sortedIMAccountIds = new Integer[0];
	private Integer[] sortedDepoAccountIds = new Integer[0];

	public Integer[] getSortedCardIds()
	{
		return sortedCardIds;
	}

	public void setSortedCardIds(Integer[] sortedCardIds)
	{
		this.sortedCardIds = sortedCardIds;
	}

	public Integer[] getSortedAccountIds()
	{
		return sortedAccountIds;
	}

	public void setSortedAccountIds(Integer[] sortedAccountIds)
	{
		this.sortedAccountIds = sortedAccountIds;
	}

	public Integer[] getSortedLoanIds()
	{
		return sortedLoanIds;
	}

	public void setSortedLoanIds(Integer[] sortedLoanIds)
	{
		this.sortedLoanIds = sortedLoanIds;
	}

	public Integer[] getSortedIMAccountIds()
	{
		return sortedIMAccountIds;
	}

	public void setSortedIMAccountIds(Integer[] sortedIMAccountIds)
	{
		this.sortedIMAccountIds = sortedIMAccountIds;
	}

	public Integer[] getSortedDepoAccountIds()
	{
		return sortedDepoAccountIds;
	}

	public void setSortedDepoAccountIds(Integer[] sortedDepoAccountIds)
	{
		this.sortedDepoAccountIds = sortedDepoAccountIds;
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
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

	public String[] getSelectedAccountESIds()
	{
		return selectedAccountESIds;
	}

	public void setSelectedAccountESIds(String[] selectedAccountESIds)
	{
		this.selectedAccountESIds = selectedAccountESIds;
	}

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

	public String[] getSelectedLoanIds()
	{
		return selectedLoanIds;
	}

	public void setSelectedLoanIds(String[] selectedLoanIds)
	{
		this.selectedLoanIds = selectedLoanIds;
	}

	public List<LoanLink> getLoans()
	{
		return loans;
	}

	public void setLoans(List<LoanLink> loans)
	{
		this.loans = loans;
	}

	public List<DepoAccountLink> getDepoAccounts()
	{
		return depoAccounts;
	}

	public void setDepoAccounts(List<DepoAccountLink> depoAccounts)
	{
		this.depoAccounts = depoAccounts;
	}

	public String[] getSelectedDepoAccountIds()
	{
		return selectedDepoAccountIds;
	}

	public void setSelectedDepoAccountIds(String[] selectedDepoAccountIds)
	{
		this.selectedDepoAccountIds = selectedDepoAccountIds;
	}

	public List<IMAccountLink> getImAccounts()
	{
		return imAccounts;
	}

	public void setImAccounts(List<IMAccountLink> imAccounts)
	{
		this.imAccounts = imAccounts;
	}

	public String[] getSelectedIMAccountIds()
	{
		return selectedIMAccountIds;
	}

	public void setSelectedIMAccountIds(String[] selectedIMAccountIds)
	{
		this.selectedIMAccountIds = selectedIMAccountIds;
	}

	public String[] getSelectedAccountMobileIds()
	{
		return selectedAccountMobileIds;
	}

	public void setSelectedAccountMobileIds(String[] selectedAccountMobileIds)
	{
		this.selectedAccountMobileIds = selectedAccountMobileIds;
	}

	public String[] getSelectedCardMobileIds()
	{
		return selectedCardMobileIds;
	}

	public void setSelectedCardMobileIds(String[] selectedCardMobileIds)
	{
		this.selectedCardMobileIds = selectedCardMobileIds;
	}

	public String[] getSelectedLoanMobileIds()
	{
		return selectedLoanMobileIds;
	}

	public void setSelectedLoanMobileIds(String[] selectedLoanMobileIds)
	{
		this.selectedLoanMobileIds = selectedLoanMobileIds;
	}

	public String[] getSelectedIMAccountMobileIds()
	{
		return selectedIMAccountMobileIds;
	}

	public void setSelectedIMAccountMobileIds(String[] selectedIMAccountMobileIds)
	{
		this.selectedIMAccountMobileIds = selectedIMAccountMobileIds;
	}

    public String[] getSelectedAccountSocialIds()
    {
        return selectedAccountSocialIds;
    }

    public void setSelectedAccountSocialIds(String[] selectedAccountSocialIds)
    {
        this.selectedAccountSocialIds = selectedAccountSocialIds;
    }

    public String[] getSelectedCardSocialIds()
    {
        return selectedCardSocialIds;
    }

    public void setSelectedCardSocialIds(String[] selectedCardSocialIds)
    {
        this.selectedCardSocialIds = selectedCardSocialIds;
    }

    public String[] getSelectedLoanSocialIds()
    {
        return selectedLoanSocialIds;
    }

    public void setSelectedLoanSocialIds(String[] selectedLoanSocialIds)
    {
        this.selectedLoanSocialIds = selectedLoanSocialIds;
    }

    public String[] getSelectedIMAccountSocialIds()
    {
        return selectedIMAccountSocialIds;
    }

    public void setSelectedIMAccountSocialIds(String[] selectedIMAccountSocialIds)
    {
        this.selectedIMAccountSocialIds = selectedIMAccountSocialIds;
    }

    public List<SecurityAccountLink> getSecurityAccounts()
	{
		return securityAccounts;
	}

	public void setSecurityAccounts(List<SecurityAccountLink> securityAccounts)
	{
		this.securityAccounts = securityAccounts;
	}

	public String[] getSelectedSecurityAccountIds()
	{
		return selectedSecurityAccountIds;
	}

	public void setSelectedSecurityAccountIds(String[] selectedSecurityAccountIds)
	{
		this.selectedSecurityAccountIds = selectedSecurityAccountIds;
	}

	public String getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(String currentPage)
	{
		this.currentPage = currentPage;
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public PFRLink getPfrLink()
	{
		return pfrLink;
	}

	public void setPfrLink(PFRLink pfrLink)
	{
		this.pfrLink = pfrLink;
	}

	public boolean getPfrLinkSelected()
	{
		return isPfrLinkSelected;
	}

	public void setPfrLinkSelected(boolean isPfrLinkSelected)
	{
		this.isPfrLinkSelected = isPfrLinkSelected;
	}

	public String getSNILS()
	{
		return SNILS;
	}

	public void setSNILS(String SNILS)
	{
		this.SNILS = SNILS;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}

	public String[] getSelectedCardESIds()
	{
		return selectedCardESIds;
	}

	public void setSelectedCardESIds(String[] selectedCardESIds)
	{
		this.selectedCardESIds = selectedCardESIds;
	}

	public String[] getSelectedLoanESIds()
	{
		return selectedLoanESIds;
	}

	public void setSelectedLoanESIds(String[] selectedLoanESIds)
	{
		this.selectedLoanESIds = selectedLoanESIds;
	}

	public String[] getSelectedIMAccountESIds()
	{
		return selectedIMAccountESIds;
	}

	public void setSelectedIMAccountESIds(String[] selectedIMAccountESIds)
	{
		this.selectedIMAccountESIds = selectedIMAccountESIds;
	}

	public String[] getSelectedAccountSMSIds()
	{
		return selectedAccountSMSIds;
	}

	public void setSelectedAccountSMSIds(String[] selectedAccountSMSIds)
	{
		this.selectedAccountSMSIds = selectedAccountSMSIds;
	}

	public String[] getSelectedLoanSMSIds()
	{
		return selectedLoanSMSIds;
	}

	public void setSelectedLoanSMSIds(String[] selectedLoanSMSIds)
	{
		this.selectedLoanSMSIds = selectedLoanSMSIds;
	}

	public String[] getSelectedCardSMSIds()
	{
		return selectedCardSMSIds;
	}

	public void setSelectedCardSMSIds(String[] selectedCardSMSIds)
	{
		this.selectedCardSMSIds = selectedCardSMSIds;
	}

	public boolean isNewProductsShowInSms()
	{
		return newProductsShowInSms;
	}

	public void setNewProductsShowInSms(boolean newProductsShowInSms)
	{
		this.newProductsShowInSms = newProductsShowInSms;
	}
}
