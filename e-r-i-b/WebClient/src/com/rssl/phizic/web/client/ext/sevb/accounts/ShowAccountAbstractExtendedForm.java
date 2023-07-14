package com.rssl.phizic.web.client.ext.sevb.accounts;

import com.rssl.phizic.web.client.abstr.ShowAccountAbstractForm;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.utils.DateHelper;

import java.util.List;
import java.util.Calendar;
import java.text.ParseException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Omeliyanchuk
 * @ created 09.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountAbstractExtendedForm extends ShowAccountAbstractForm
{
	Boolean copying = false;
	private static final int MONTH_PERIOD = 3;
	private List<Account> accounts;

	public Boolean getCopying()
	{
		return copying;
	}

	public void setCopying(Boolean copying)
	{
		this.copying = copying;
	}

	public List<Account> getAccounts()
	{
		return accounts;
	}

	public void setAccounts(List<Account> accounts)
	{
		this.accounts = accounts;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = super.validate(mapping, request);
		if(errors.size()!= 0)
			return errors;
		try
		{
			Calendar fromDate = (Calendar) getFromDate().clone();
			fromDate.add(Calendar.MONTH, MONTH_PERIOD);
			if (fromDate.before(DateHelper.getCurrentDate()) || fromDate.before(getToDate()))
			{
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"ћаксимальный период дл€ построени€ выписки Ц "+ MONTH_PERIOD + " мес€ца. »змените период выписки", false));
			}
		}
		catch (ParseException e)
		{
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wrongDateFormat"));
		}
		return errors;
	}
}
