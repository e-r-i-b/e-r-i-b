package com.rssl.phizic.web.common.client.autopayments;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.gate.longoffer.autopayment.ScheduleItem;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * Form для отображения детальной информации автоплатежей типа AutoPayment
 * @author osminin
 * @ created 10.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class ShowAutoPaymentInfoForm extends EditFormBase
{
	private AutoPaymentLink link;
	private String requisiteName;
	private List<ScheduleItem> scheduleItems;

	public static final Form FILTER_FORM = createFilterForm();

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		String format = "dd/MM/yyyy";
		DateParser parser = new DateParser(format);

		DateFieldValidator dateValidator = new DateFieldValidator(format);
		dateValidator.setMessage("Введите дату в формате " + format + ".");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Начальная дата");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(parser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(dateValidator, new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Конечная дата");
		fieldBuilder.setName("toDate");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(parser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(dateValidator, new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "fromDate");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "toDate");
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}

	public AutoPaymentLink getLink()
	{
		return link;
	}

	public void setLink(AutoPaymentLink link)
	{
		this.link = link;
	}

	public List<ScheduleItem> getScheduleItems()
	{
		return scheduleItems;
	}

	public void setScheduleItems(List<ScheduleItem> scheduleItems)
	{
		this.scheduleItems = scheduleItems;
	}

	public String getRequisiteName()
	{
		return requisiteName;
	}

	public void setRequisiteName(String requisiteName)
	{
		this.requisiteName = requisiteName;
	}
}
