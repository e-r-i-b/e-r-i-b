package com.rssl.phizic.web.client.ext.sbercred.abstr;

import com.rssl.phizic.web.client.abstr.ShowAccountAbstractForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.text.ParseException;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Krenev
 * @ created 02.11.2007
 * @ $Author$
 * @ $Revision$
 */
public class ShowAbstractForm extends ShowAccountAbstractForm
{
	private static final int MONTH_PERIOD = 3;

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = super.validate(mapping, request);
		if(errors.size()!= 0)
			return errors;
		try
		{
			Calendar fromDate = (Calendar) getFromDate().clone();
			fromDate.add(Calendar.MONTH, MONTH_PERIOD);
			if (fromDate.before(getToDate()))
			{
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.adstract.long.period", MONTH_PERIOD));
			}
		}
		catch (ParseException e)
		{
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wrongDateFormat"));
		}
		return errors;
	}
}
