package com.rssl.phizic.web.client.accounts;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.ActionFormBase;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Omeliyanchuk
 * @ created 07.08.2006
 * @ $Author$
 * @ $Revision$
 */
public class PrintLongOfferForm  extends ActionFormBase
{
	private String[] selectedResources = new String[0];  // выбранные счета и карты

	// период
	private boolean isValid = false;
	private Calendar fromDate = DateHelper.getOperDate();
	private Calendar toDate = DateHelper.getCurrentDate();
	private String fromDateString = DateHelper.toString(fromDate.getTime());
	private String toDateString = DateHelper.toString(toDate.getTime());
	private String message = "";

	private ActivePerson user;    // текущий пользователь

	private List<String> HTML = new ArrayList<String>();

	public boolean getIsValid()
	{
		return isValid;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}

	public void setIsValid(boolean isValid)
	{
		this.isValid = isValid;
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

	public String getOperDate()
	{
		return DateHelper.toString(DateHelper.getOperDate().getTime());
	}

	public String getCurrentDate()
	{
		return DateHelper.toString(DateHelper.getCurrentDate().getTime());
	}

	public String getFormDate()
	{
		Calendar calendar = Calendar.getInstance();
		DateFormatSymbols dfs = new DateFormatSymbols(new Locale("ru"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", dfs);
        return sdf.format(calendar.getTime());
	}

	public String getFormTime()
	{
		Calendar calendar = Calendar.getInstance();
		DateFormatSymbols dfs = new DateFormatSymbols(new Locale("ru"));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", dfs);
        return sdf.format(calendar.getTime());
	}

	public List<String> getHTML()
	{
		return HTML;
	}

	public void addHTML(String HTML)
	{
		this.HTML.add(HTML);
	}

	public void setHTML(List<String> HTML)
	{
		this.HTML = HTML;
	}
}
