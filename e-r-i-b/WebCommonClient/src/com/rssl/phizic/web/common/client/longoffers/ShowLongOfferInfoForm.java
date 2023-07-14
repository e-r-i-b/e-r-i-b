package com.rssl.phizic.web.common.client.longoffers;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.resources.external.LongOfferLink;
import com.rssl.phizic.gate.longoffer.ScheduleItem;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * Form для отображения детальной информации автоплатежей типа LongOffer
 * @author osminin
 * @ created 30.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class ShowLongOfferInfoForm extends EditFormBase
{
	private LongOfferLink longOfferLink;
	private String receiverAccount;
	private String receiverCard; 
	private String payerAccount;
	private List<ScheduleItem> scheduleItems;

	public static final Form DATE_FORM = createDateForm();
	public static final Form NAME_FORM = createNameForm();

	private String abstractMsgError;

	private static Form createNameForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("longOfferName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "Название должен быть не более 100 символов"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	private static Form createDateForm()
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

	public LongOfferLink getLongOfferLink()
	{
		return longOfferLink;
	}

	public void setLongOfferLink(LongOfferLink longOfferLink)
	{
		this.longOfferLink = longOfferLink;
	}

	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public String getPayerAccount()
	{
		return payerAccount;
	}

	public void setPayerAccount(String payerAccount)
	{
		this.payerAccount = payerAccount;
	}

	public String getReceiverCard()
	{
		return receiverCard;
	}

	public void setReceiverCard(String receiverCard)
	{
		this.receiverCard = receiverCard;
	}

	public List<ScheduleItem> getScheduleItems()
	{
		return scheduleItems;
	}

	public void setScheduleItems(List<ScheduleItem> scheduleItems)
	{
		this.scheduleItems = scheduleItems;
	}

	public String getAbstractMsgError()
	{
		return abstractMsgError;
	}

	public void setAbstractMsgError(String abstractMsgError)
	{
		this.abstractMsgError = abstractMsgError;
	}
}
