package com.rssl.phizic.web.common.client.abstr;

import com.rssl.common.forms.parsers.DateParser;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.business.resources.external.MockAccountLink;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.gate.bankroll.DepositAbstract;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.ActionFormBase;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Omeliyanchuk
 * @ created 08.12.2006
 * @ $Author$
 * @ $Revision$
 */
//TODO надо бы установит "родственные связи" между PrintAccountAbstractForm ShowAccountAbstractForm и акшеами
//TODO спишком много копипаста!!!!	
public class PrintAccountAbstractForm extends ActionFormBase
{
	private DateParser dateParser = new DateParser("dd/MM/yyyy");
	private String typeExtract; 
	// период
	private Calendar fromDate = DateHelper.getOperDate();
	private Calendar toDate = DateHelper.getCurrentDate();
	private String fromDateString = String.format("%1$td/%1$tm/%1$tY", fromDate);
	private String toDateString = String.format("%1$td/%1$tm/%1$tY", toDate);
	/** собственно данные выписки */
	private ActivePerson user;    // текущий пользователь
	private Client client;
	private Map<AccountLink, AccountAbstract> accountAbstract; // key - Account, value - AccountAbstract
	private Map<CardLink, CardAbstract> cardAbstract;    // key - Card, value - CardAbstract
	//Временная заглушка. Пока не реализуем полноценную выписку по карте.
	private Map<MockAccountLink, AccountAbstract> cardAccountAbstract;    // key - MockAccountLink, value - AccountAbstract
	private Map<DepositLink, DepositAbstract> depositAbstract;
	private boolean backError;

	public Calendar getFromDate() throws ParseException
    {
	    if (StringHelper.isEmpty(fromDateString))
		    return null;
	    fromDate.setTime(dateParser.parse(fromDateString));
		return fromDate;
	}

	public void setFromDate(Calendar fromDate)
	{
		this.fromDate = fromDate;
		fromDateString = String.format("%1$td/%1$tm/%1$tY", this.fromDate);
	}

	public String getFromDateString()
	{
		return fromDateString;
	}

	public void setFromDateString(String fromDateString) throws ParseException
	{
		this.fromDateString = fromDateString;
	}

	public Calendar getToDate() throws ParseException
	{
	    if (StringHelper.isEmpty(toDateString))
		    return null;
		toDate.setTime(dateParser.parse(toDateString));
		return toDate;
	}

	public void setToDate(Calendar toDate)
	{
		this.toDate = toDate;
		toDateString = String.format("%1$td/%1$tm/%1$tY", this.toDate);
	}

	public String getToDateString()
	{
		return toDateString;
	}

	public void setToDateString(String toDateString) throws ParseException
	{
		this.toDateString = toDateString;
	}

	public ActivePerson getUser()
	{
		return user;
	}

	public void setUser(ActivePerson user)
	{
		this.user = user;
	}

	public Map<AccountLink, AccountAbstract> getAccountAbstract()
	{
		return accountAbstract;
	}

	public void setAccountAbstract(Map<AccountLink, AccountAbstract> accountAbstract)
	{
		this.accountAbstract = accountAbstract;
	}

	public Map<CardLink, CardAbstract> getCardAbstract()
	{
		return cardAbstract;
	}

	public void setCardAbstract(Map<CardLink, CardAbstract> cardAbstract)
	{
		this.cardAbstract = cardAbstract;
	}
	
	//Временная заглушка. Пока не реализуем полноценную выписку по карте.
	public Map<MockAccountLink, AccountAbstract> getCardAccountAbstract()
	{
		return cardAccountAbstract;
	}
	//Временная заглушка. Пока не реализуем полноценную выписку по карте.
	public void setCardAccountAbstract(Map<MockAccountLink, AccountAbstract> cardAccountAbstract)
	{
		this.cardAccountAbstract = cardAccountAbstract;
	}

	public String getOperDate()
	{
		return String.format("dd.MM.yyyy",DateHelper.getOperDate());
	}

	public String getCurrentDate()
	{
		return String.format("dd.MM.yyyy",DateHelper.getCurrentDate().getTime());
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
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wrongDateFormat"));
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

	public Client getClient()
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	public String getTypeExtract()
	{
		return typeExtract;
	}

	public void setTypeExtract(String typeExtract)
	{
		this.typeExtract = typeExtract;
	}

	public Map<DepositLink, DepositAbstract> getDepositAbstract()
	{
		return depositAbstract;
	}

	public void setDepositAbstract(Map<DepositLink, DepositAbstract> depositAbstract)
	{
		this.depositAbstract = depositAbstract;
	}

	public void setBackError(boolean backError)
	{
		this.backError = backError;
	}

	public boolean isBackError()
	{
		return backError;
	}
}
