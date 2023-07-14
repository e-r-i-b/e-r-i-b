package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.business.documents.FilterPaymentForm;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.ListLimitActionForm;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 31.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class ViewDocumentListFormBase extends ListLimitActionForm
{
	private String status;
	private List forms;
	private Date curDate = new Date();
	private List<FilterPaymentForm> filterPaymentForms;

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public List getForms()
	{
		return forms;
	}

	public void setForms(List forms)
	{
		this.forms = forms;
	}

	public String getCurDate()
	{
		return DateHelper.toString(curDate);
	}

	public void setCurDate(Date curDate)
	{
		this.curDate = curDate;
	}

	public Date getFromDate() throws ParseException
	{
		Object obj = getFilter("fromDateString");
		if (obj == null)
		{
			return null;
		}
		String fromDate = (String) obj;
		if (fromDate.equals(""))
		{
			return null;
		}
		return DateHelper.parseDate(fromDate);
	}

	public Date getToDate() throws ParseException
	{
		Object obj = getFilter("toDateString");
		if (obj == null)
		{
			return null;
		}
		String toDate = (String) obj;
		if (toDate.equals(""))
		{
			return null;
		}
		return DateHelper.parseDate(toDate);
	}

	public List<FilterPaymentForm> getFilterPaymentForms()
	{
		return filterPaymentForms;
	}

	public void setFilterPaymentForms(List<FilterPaymentForm> filterPaymentForms)
	{
		this.filterPaymentForms = filterPaymentForms;
	}
}
