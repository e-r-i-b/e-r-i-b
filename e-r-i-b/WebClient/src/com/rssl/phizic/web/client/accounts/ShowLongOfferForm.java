package com.rssl.phizic.web.client.accounts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 04.08.2006
 * @ $Author$
 * @ $Revision$
 */
public class ShowLongOfferForm  extends ActionFormBase
{
	String html;
	private ActivePerson user;    // текущий пользователь

	/** фильтр выписки */
	private List<String> resourceLinks = new ArrayList<String>();
	private List<String> resourceNames = new ArrayList<String>();

	// выбранные счета и карты
	private String[] selectedResources = new String[0];

	// период
	private Calendar fromDate = DateHelper.getPreviousMonth();
	private Calendar toDate = DateHelper.getCurrentDate();
	private String fromDateString = DateHelper.toString(fromDate.getTime());
	private String toDateString = DateHelper.toString(toDate.getTime());

	public String getHtml()
	{
		return html;
	}

	public void setHtml(String html)
	{
		this.html = html;
	}

	public void setResources(List<AccountLink> list) throws BusinessException
	{
		resourceLinks = new ArrayList<String>();
		resourceNames = new ArrayList<String>();
		for (AccountLink link : list)
		{
			Account a = link.getValue();
			StringBuffer name = new StringBuffer(a.getNumber());
			name.append("(");
			name.append(a.getCurrency().getName());
			name.append(", ");
			name.append(a.getDescription());
			name.append(")");
			resourceNames.add(name.toString());
			resourceLinks.add("a:" + link.getId());
		}
	}

	public List<String> getResourceLinks()
	{
		return resourceLinks;
	}


	public List<String> getResourceNames()
	{
		return resourceNames;
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

		public String getOperDate()
		{
			return DateHelper.toString( DateHelper.getPreviousMonth().getTime() );
		}

		public String getCurrentDate()
		{
			return DateHelper.toString(DateHelper.getCurrentDate().getTime());
		}
}
