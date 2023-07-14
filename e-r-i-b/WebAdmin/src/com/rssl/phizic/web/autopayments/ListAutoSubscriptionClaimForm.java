package com.rssl.phizic.web.autopayments;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.DateTimePeriodMultiFieldValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: vagin
 * @ created: 17.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListAutoSubscriptionClaimForm extends PersonFormBase
{
	public static final Form FORM = createForm();
	//true ���� ���� ������ ��� ��������� ����� �������.
	private boolean errorFilter = false;

	private static Form createForm()
	{
		List<Field> fields = new ArrayList<Field>();
	    FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("receiver");
	    fieldBuilder.setDescription("����������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
	    fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("state");
	    fieldBuilder.setDescription("��������� ������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
	    fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("�laimType");
	    fieldBuilder.setDescription("��� ������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
	    fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("������ �");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new DateFieldValidator("dd.MM.yyyy", "����������, ������� ������ � ������� ��.��.����."));
		fieldBuilder.addValidators(new RequiredFieldValidator("������� ��������� ����"));
		fields.add(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("������ ��");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new DateFieldValidator("dd.MM.yyyy", "����������, ������� ������ � ������� ��.��.����."));
		fieldBuilder.addValidators(new RequiredFieldValidator("������� �������� ����"));
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
	    formBuilder.setFields(fields);

		DateTimeCompareValidator dateTimeValidator = new DateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "");
		dateTimeValidator.setMessage("���� ������ ������� �� ������ ���� ������ ���� ��������� �������. ����������, ������� ������ ����.");
		formBuilder.addFormValidators(dateTimeValidator);

		DateTimePeriodMultiFieldValidator hourPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"");
		hourPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.MONTH_TYPE_PERIOD);
		hourPeriodMultiFieldValidator.setLengthPeriod(1);
		hourPeriodMultiFieldValidator.setMessage("����������, ������� ������ �� ����� 1 ������.");
		formBuilder.addFormValidators(hourPeriodMultiFieldValidator);

		return formBuilder.build();	
	}

	public boolean isErrorFilter()
	{
		return errorFilter;
	}

	public void setErrorFilter(boolean errorFilter)
	{
		this.errorFilter = errorFilter;
	}
}
