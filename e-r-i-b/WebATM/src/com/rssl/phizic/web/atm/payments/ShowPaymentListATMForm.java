package com.rssl.phizic.web.atm.payments;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.business.documents.FilterPaymentForm;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.FilterPeriodHelper;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentListFormBase;
import com.rssl.phizic.web.common.ListLimitActionForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author Rydvanskiy
 * @ created 03.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowPaymentListATMForm extends ListLimitActionForm
{
	public static final String CREATION_TYPE = "creationType";
	public static final String FORM_ID = "formId";
	public static final String PAYMENT_STATUS = "paymentStatus";

	public static final Form FORM = createForm();

	private List forms;
	private Date curDate = new Date();
	private List<FilterPaymentForm> filterPaymentForms;

	private String creationType;
	private String from;
	private String to;
	private String[] form;               //Название форм для поиска
	private String status;              //Статус документа для поиска

	private static Form createForm()
	{
		FormBuilder formBuilder = FilterPeriodHelper.createFilterPeriodFormBuilder(FilterPeriodHelper.PERIOD_TYPE_MONTH);

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип канала создания документа");
		fieldBuilder.setName(CREATION_TYPE);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public String getCreationType()
	{
		return creationType;
	}

	public void setCreationType(String creationType)
	{
		this.creationType = creationType;
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

	public String[] getForm()
	{
		return form;
	}

	public void setForm(String[] form)
	{
		this.form = form;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
}
