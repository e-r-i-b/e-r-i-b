package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentListFormBase;

/**
 * @author niculichev
 * @ created 18.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListPayWaitingConfirmForm extends ViewDocumentListFormBase
{
	public static String DATESTAMP = "dd.MM.yyyy";
	public static String TIMESTAMP = "HH:mm:ss";

	public static Form FILTER_FORM = createFilterForm();

	private Long person;
	private ActivePerson activePerson;
	private Boolean modified = false;

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public Long getPerson()
	{
		return person;
	}

	public void setPerson(Long person)
	{
		this.person = person;
	}


	private static Form createFilterForm()
	{
		DateParser dateParser = new DateParser(DATESTAMP);
		DateParser timeParser = new DateParser(TIMESTAMP);

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("fromDate");
		fb.setDescription("����");
		fb.setValidators(new DateFieldValidator(DATESTAMP, "���� ������ ���� � ������� ��.��.����"));
		fb.addValidators(new RequiredFieldValidator("������� ��������� ����"));

		fb.setParser(dateParser);
		formBuilder.addField(fb.build());


		fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("fromTime");
		fb.setDescription("�����");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator(TIMESTAMP, "����� ������ ���� � ������� ��:��:��"));
		fb.setParser(timeParser);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("toDate");
		fb.setDescription("����");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator(DATESTAMP, "���� ������ ���� � ������� ��.��.����"));
		fb.addValidators(new RequiredFieldValidator("������� �������� ����"));
		fb.setParser(dateParser);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("toTime");
		fb.setDescription("�����");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator(TIMESTAMP, "����� ������ ���� � ������� ��:��:��"));
		fb.setParser(timeParser);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("formName");
		fb.setDescription("��� ��������");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("receiverName");
		fb.setDescription("����������");
		fb.addValidators(new RegexpFieldValidator(".{0,100}", "������ ���� ���������� ������ ���� �� ����� 100 ��������."));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("state");
		fb.setDescription("������ ��������");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(LongType.INSTANCE.getName());
		fb.setName("number");
		fb.setDescription("� �������/������");
		fb.addValidators(new RegexpFieldValidator(".{0,10}", "������ ���� � �������/������ ������ ���� �� ����� 10 ��������."));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("additionConfirm");
		fb.setDescription("�����������");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("confirmEmployee");
		fb.setDescription("���������� ���������");
		fb.addValidators(new RegexpFieldValidator(".{0,50}", "������ ���� ���������� ��������� ������ ���� �� ����� 50 ��������."));
		formBuilder.addField(fb.build());

		// ���� ����������

		DateTimeCompareValidator dateTimeValidator = new DateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "toDate");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "toTime");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "fromDate");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "fromTime");
		dateTimeValidator.setMessage("���� � ���� \"�\" ������ ���� ������ ���� � ���� \"��\"");
		formBuilder.addFormValidators(dateTimeValidator);

		DateTimePeriodMultiFieldValidator hourPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		hourPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.MONTH_TYPE_PERIOD);
		hourPeriodMultiFieldValidator.setLengthPeriod(1);
		hourPeriodMultiFieldValidator.setMessage("����������, ������� ������ �� ����� 1 ������.");
		formBuilder.addFormValidators(hourPeriodMultiFieldValidator);

		return formBuilder.build();
	}

}
