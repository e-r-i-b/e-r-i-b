package com.rssl.phizic.web.common.mobile.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.phizic.web.common.client.mail.ListMailFormBase;

/**
 * Ѕазовый класс дл€ форм списков писем в мобильном приложении
 * @author Dorzhinov
 * @ created 09.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListMailMobileFormBase extends ListMailFormBase
{
	//in
	private String from;
	private String to;
	private Long num;
	private boolean hasAttach;
	private String subject;

	protected static FormBuilder createMobileFilter()
	{
		FormBuilder formBuilder = createMobileDateFields();
		createAdditionalFields(formBuilder);
		return formBuilder;
	}

	private static FormBuilder createMobileDateFields()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FIELD_FROM_DATE_NAME);
		fieldBuilder.setDescription("ƒата начала периода");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FIELD_TO_DATE_NAME);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setDescription("ƒата окончани€ периода");
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, FIELD_FROM_DATE_NAME);
		compareValidator.setBinding(CompareValidator.FIELD_O2, FIELD_TO_DATE_NAME);
		compareValidator.setMessage(" онечна€ дата должна быть больше либо равна начальной!");
		formBuilder.setFormValidators(compareValidator);

		return formBuilder;
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

	public Long getNum()
	{
		return num;
	}

	public void setNum(Long num)
	{
		this.num = num;
	}

	public boolean hasAttach()
	{
		return hasAttach;
	}

	public void setHasAttach(boolean hasAttach)
	{
		this.hasAttach = hasAttach;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}
}
