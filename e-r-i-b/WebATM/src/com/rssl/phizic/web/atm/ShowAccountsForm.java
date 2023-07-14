package com.rssl.phizic.web.atm;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.gate.loans.ScheduleItem;
import com.rssl.phizic.logging.operations.LogEntry;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.*;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 13.10.2005 Time: 14:06:52 */
public class ShowAccountsForm extends ActionFormBase
{

	private List<AccountLink> accounts = new LinkedList<AccountLink>();
    private String[] selectedAccountsIds=new String[0];
	private List<CardLink> cards = new ArrayList<CardLink>();
	private String[] selectedCardsIds=new String[0];
	private List<DepositLink> deposits = new ArrayList<DepositLink>();
	private List<IMAccountLink> imAccounts = new ArrayList<IMAccountLink>();
	private Long warningPeriod;
	private boolean isAllCardDown;
	private boolean isAllAccountDown;
	private boolean isAllLoanDown;
	private boolean isAllIMAccountDown;
	private String[] showProductType;

	private String [] selectedDepositsIds=new String[0];
	private List<LogEntry> logEntries = new ArrayList<LogEntry>();
	private ActivePerson user;	

	private List<LoanLink> loans = new ArrayList<LoanLink>();
	private Long selectedLinkId;
	private Map<String, ScheduleItem> scheduleItems = new HashMap<String, ScheduleItem>();

    public ActivePerson getUser() {
        return user;
    }

    public void setUser(ActivePerson user) {
        this.user = user;
    }

    public String[] getSelectedAccountsIds()
    {
        return selectedAccountsIds;
    }

    public void setSelectedAccountsIds(String[] selectedAccountsIds)
    {
        this.selectedAccountsIds = selectedAccountsIds;
    }

	public List<AccountLink> getAccounts()
	{
		return accounts;
	}

	public void setAccounts(List<AccountLink> accounts)
	{
		this.accounts = accounts;
	}

	public List<CardLink> getCards()
	{
		return cards;
	}

	public void setCards(List<CardLink> cards)
	{
		this.cards = cards;
	}

	public List<DepositLink> getDeposits()
	{
		return deposits;
	}

	public void setDeposits(List<DepositLink> deposits)
	{
		this.deposits = deposits;
	}


	public void setSelectedCardsIds(String[] selectedCardsIds)
	{
	    this.selectedCardsIds = selectedCardsIds;
	}

	public String[] getSelectedCardsIds()
    {
        return selectedCardsIds;
    }

	public List<LogEntry> getLogEntries()
	{
		return logEntries;
	}

	public void setLogEntries(List<LogEntry> logEntries)
	{
		this.logEntries = logEntries;
	}

    Map <String,Object> getFilterFields()
	{
	   return new HashMap<String,Object>();
	}

	public Long getWarningPeriod()
	{
		return warningPeriod;
	}

	public void setWarningPeriod(Long warningPeriod)
	{
		this.warningPeriod = warningPeriod;
	}

	public List<LoanLink> getLoans()
	{
		return loans;
	}

	public void setLoans(List<LoanLink> loans)
	{
		this.loans = loans;
	}

	public Map<String, ScheduleItem> getScheduleItems()
	{
		return scheduleItems;
	}

	public void setScheduleItems(Map<String, ScheduleItem> scheduleItems)
	{
		this.scheduleItems = scheduleItems;
	}

	public String[] getSelectedDepositsIds()
	{
		return selectedDepositsIds;
	}

	public void setSelectedDepositsIds(String[] selectedDepositsIds)
	{
		this.selectedDepositsIds = selectedDepositsIds;
	}

	public Long getSelectedLinkId()
	{
		return selectedLinkId;
	}

	public void setSelectedLinkId(Long selectedLinkId)
	{
		this.selectedLinkId = selectedLinkId;
	}

	public List<IMAccountLink> getImAccounts()
	{
		return imAccounts;
	}

	public void setImAccounts(List<IMAccountLink> imAccounts)
	{
		this.imAccounts = imAccounts;
	}

	/**
	 *
	 * @return true - если не удалось получит информацию ни по одному кредиту, т.к. были ошибки бэк офиса.
	 */
	public boolean isAllLoanDown()
	{
		return isAllLoanDown;
	}

	public void setAllLoanDown(boolean isAllLoanDown)
	{
		this.isAllLoanDown = isAllLoanDown;
	}

	/**
	 *
	 * @return true - если не удалось получит информацию по всем картам, т.к. были ошибки бэк офиса.
	 */
	public boolean isAllCardDown()
	{
		return isAllCardDown;
	}

	public void setAllCardDown(boolean isAllCardDown)
	{
		this.isAllCardDown = isAllCardDown;
	}

	/**
	 *
	 * @return true - если не удалось получит информацию по всем счетам, т.к. были ошибки бэк офиса.
	 */
	public boolean isAllAccountDown()
	{
		return isAllAccountDown;
	}

	public void setAllAccountDown(boolean isAllAccountDown)
	{
		this.isAllAccountDown = isAllAccountDown;
	}

	/**
	 *
	 * @return true - если не удалось получит информацию по всем ОМС, т.к. были ошибки бэк офиса.
	 */
	public boolean isAllIMAccountDown()
	{
		return isAllIMAccountDown;
	}

	public void setAllIMAccountDown(boolean allIMAccountDown)
	{
		isAllIMAccountDown = allIMAccountDown;
	}

	/**
	 * Отвечает за отображение только конкретного продукта
	 * Используется в протоколе взаимодействия с мобильными устройствами
	 * @return null, "cards", "loans", "accounts", "imaccounts"
	 */

	public String[] getShowProductType()
	{
		return showProductType;
	}

	public void setShowProductType(String[] showProductType)
	{
		this.showProductType = showProductType;
	}
}
