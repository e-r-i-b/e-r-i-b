package com.rssl.phizic.web.ext.sbrf.technobreaks;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author niculichev
 * @ created 10.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListTechnoBreakForm extends ListFormBase
{
	public static final Form FILTER_FORM = createFilterForm();

	private static Form createFilterForm()
	{                   
		FormBuilder formBuilder = new FormBuilder();

		DateParser dateParser = new DateParser(Constants.DATESTAMP);
		DateParser timeParser = new DateParser(Constants.TIMESTAMP);

		FieldBuilder fb = new FieldBuilder();
	    fb.setName(Constants.ADAPTER_UUID);
	    fb.setDescription("������� �������");
	    fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.ADAPTER_NAME);
	    fb.setDescription("������� �������");
	    fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.FROM_DATE);
	    fb.setDescription("���� ������");
	    fb.setType(DateType.INSTANCE.getName());
		fb.setParser(dateParser);
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator(Constants.DATESTAMP, "������� ���� ������ �������� ���������������� �������� � ������� ��.��.����"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.FROM_TIME);
	    fb.setDescription("����� ������");
	    fb.setType(DateType.INSTANCE.getName());
		fb.setParser(timeParser);
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator(Constants.TIMESTAMP, "������� ����� ������ �������� ���������������� �������� � ������� ��:��:��"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.TO_DATE);
	    fb.setDescription("���� �����");
	    fb.setType(DateType.INSTANCE.getName());
		fb.clearValidators();
		fb.setParser(dateParser);
		fb.addValidators(new DateFieldValidator(Constants.DATESTAMP, "������� ���� ����� �������� ���������������� �������� � ������� ��.��.����"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.TO_TIME);
	    fb.setDescription("����� �����");
	    fb.setType(DateType.INSTANCE.getName());
		fb.setParser(timeParser);
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator(Constants.TIMESTAMP, "������� ����� ����� �������� ���������������� �������� � ������� ��:��:��"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.PERIODIC);
	    fb.setDescription("�������������");
	    fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(Constants.MESSAGE);
	    fb.setDescription("���������");
	    fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RegexpFieldValidator(".{0,200}", "��������� ������ ��������� �� ����� 200 ��������"));
		formBuilder.addField(fb.build());

		DateTimeCompareValidator dateTimeValidator = new DateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, Constants.FROM_DATE);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, Constants.FROM_TIME);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, Constants.TO_DATE);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, Constants.TO_TIME);
		dateTimeValidator.setMessage("���� ��������� ������ ���� ������ ���� ������ ������� �������� ���������������� ��������." +
				" ����������, ������� ������ ����.");
		formBuilder.addFormValidators(dateTimeValidator);

		fb = new FieldBuilder();
	    fb.setName(Constants.SHOW_WORKING);
	    fb.setDescription("�������� ������ �����������");
	    fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

}
