package com.rssl.phizic.web.actions.payments.forms.autosubscription;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.gate.autopayments.ScheduleItem;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author basharin
 * @ created 30.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowAutoSubscriptionInfoForm extends EditFormBase
{
	public static final Form FILTER_FORM = createFilterForm();

	private AutoSubscriptionLink link;
	private AutoSubscriptionDetailInfo autoSubscriptionInfo;
	private boolean showPaymentForPeriod = false;
	private List<ScheduleItem> scheduleItems;
	private String textUpdateSheduleItemsError;
	private AccountState autoSubscriptionCardState;

	public AutoSubscriptionLink getLink()
	{
		return link;
	}

	public void setLink(AutoSubscriptionLink link)
	{
		this.link = link;
	}

	public boolean isShowPaymentForPeriod()
	{
		return showPaymentForPeriod;
	}

	public void setShowPaymentForPeriod(boolean showPaymentForPeriod)
	{
		this.showPaymentForPeriod = showPaymentForPeriod;
	}

	public List<ScheduleItem> getScheduleItems()
	{
		return scheduleItems;
	}

	public void setScheduleItems(List<ScheduleItem> scheduleItems)
	{
		this.scheduleItems = scheduleItems;
	}


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

	public String getTextUpdateSheduleItemsError()
	{
		return textUpdateSheduleItemsError;
	}

	public void setTextUpdateSheduleItemsError(String textUpdateSheduleItemsError)
	{
		this.textUpdateSheduleItemsError = textUpdateSheduleItemsError;
	}

	public AutoSubscriptionDetailInfo getAutoSubscriptionInfo()
	{
		return autoSubscriptionInfo;
	}

	public void setAutoSubscriptionInfo(AutoSubscriptionDetailInfo autoSubscriptionInfo)
	{
		this.autoSubscriptionInfo = autoSubscriptionInfo;
	}

	public AccountState getAutoSubscriptionCardState()
	{
		return autoSubscriptionCardState;
	}

	public void setAutoSubscriptionCardState(AccountState autoSubscriptionCardState)
	{
		this.autoSubscriptionCardState = autoSubscriptionCardState;
	}
}
