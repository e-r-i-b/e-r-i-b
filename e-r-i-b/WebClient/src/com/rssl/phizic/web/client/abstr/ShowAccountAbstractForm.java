package com.rssl.phizic.web.client.abstr;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.gate.bankroll.DepositAbstract;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.FilterActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Krenev
 * @ created 17.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class ShowAccountAbstractForm extends FilterActionForm
{
	public static String DATESTAMP = "dd.MM.yyyy";
	private List<AccountLink> accountLinks = new ArrayList<AccountLink>();                                               
	private List<CardLink> cardLinks = new ArrayList<CardLink>();
	private List<DepositLink> depositLinks = new ArrayList<DepositLink>();

	private ActivePerson user;    // текущий пользователь

	private Map<AccountLink, AccountAbstract> accountAbstract;
	private Map<CardLink, CardAbstract> cardAbstract;
	private Map<DepositLink, DepositAbstract> depositAbstract;
	//выбранные счета и карты при приходе из списка(с selectedResources объедин€ть не стал, чтоб не св€зывать) 
	private String[] accountIds;					 
	private String[] cardIds;
	private String[] depositIds;
	//обозначение периода, за который необходимо построить выписку(возм. зн-ни€: day)
	private String datePeriod; 

	//TODO Ќадо бы это засунуть в параметры фильтра(Ќо как?)
	private String[] selectedResources = new String[0];  // выбранные счета и карты
	private Calendar fromDate;
	private Calendar toDate;
	//TODO дублирование сущностей. »спользовать fromDate, toDate
	private String fromDateString;
	private String toDateString;

	public String[] getAccountIds()
	{
		return accountIds;
	}

	public void setAccountIds(String[] accountIds)
	{
		this.accountIds = accountIds;
	}

	public String[] getCardIds()
	{
		return cardIds;
	}

	public void setCardIds(String[] cardIds)
	{
		this.cardIds = cardIds;
	}

	public String[] getDepositIds()
	{
		return depositIds;
	}

	public void setDepositIds(String[] depositIds)
	{
		this.depositIds = depositIds;
	}

	public String getDatePeriod()
	{
		return datePeriod;
	}

	public void setDatePeriod(String datePeriod)
	{
		this.datePeriod = datePeriod;
	}

	public List<AccountLink> getAccountLinks()
	{
		return accountLinks;
	}

	public void setAccountLinks(List<AccountLink> accountLinks)
	{
		this.accountLinks = accountLinks;
	}

	public List<CardLink> getCardLinks()
	{
		return cardLinks;
	}

	public void setCardLinks(List<CardLink> cardLinks)
	{
		this.cardLinks = cardLinks;
	}

	public ActivePerson getUser()
	{
		return user;
	}

	public void setUser(ActivePerson user)
	{
		this.user = user;
	}

	public String[] getSelectedResources()
	{
		return selectedResources;
	}

	public void setSelectedResources(String[] selectedResources)
	{
		this.selectedResources = selectedResources;
	}

	public void setAccountAbstract(Map<AccountLink, AccountAbstract> accountAbstract)
	{
		this.accountAbstract = accountAbstract;
	}

	public Map<AccountLink, AccountAbstract> getAccountAbstract()
	{
		return accountAbstract;
	}

	public Map<CardLink, CardAbstract> getCardAbstract()
	{
		return cardAbstract;
	}

	public void setCardAbstract(Map<CardLink, CardAbstract> cardAbstract)
	{
		this.cardAbstract = cardAbstract;
	}

	public Calendar getFromDate() throws ParseException
	{
		fromDate = DateHelper.parseCalendar(this.fromDateString);
		return fromDate;
	}

	public void setFromDate(Calendar fromDate)
	{
		this.fromDate = fromDate;
		fromDateString = DateHelper.toString(this.fromDate.getTime());
	}

	public Calendar getToDate() throws ParseException
	{
		toDate = DateHelper.parseCalendar(toDateString);
		return toDate;
	}

	public void setToDate(Calendar toDate)
	{
		this.toDate = toDate;
		toDateString = DateHelper.toString(this.toDate.getTime());
	}

	public String getToDateString()
	{
		return toDateString;
	}

	public void setToDateString(String toDateString) throws ParseException
	{
		this.toDateString = toDateString;
	}

	public String getFromDateString()
	{
		return fromDateString;
	}

	public void setFromDateString(String fromDateString) throws ParseException
	{
		this.fromDateString = fromDateString;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();

		try
		{
			tryParseRange(fromDateString);
			DateHelper.parseCalendar(toDateString);
		}
		catch (ParseException ex)
		{
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wrongDate"));
		}

		errors.add(super.validate(mapping, request));
		return errors;
	}

	private void tryParseRange(String str) throws ParseException
	{
		Calendar clnr = DateHelper.parseCalendar(str);
		if (clnr.get(Calendar.YEAR) > 9999)
			throw new ParseException("Too big date", 0);

	}

	public List<DepositLink> getDepositLinks()
	{
		return depositLinks;
	}

	public void setDepositLinks(List<DepositLink> depositLinks)
	{
		this.depositLinks = depositLinks;
	}

	public Map<DepositLink, DepositAbstract> getDepositAbstract()
	{
		return depositAbstract;
	}

	public void setDepositAbstract(Map<DepositLink, DepositAbstract> depositAbstract)
	{
		this.depositAbstract = depositAbstract;
	}
}
