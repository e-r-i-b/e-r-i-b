package com.rssl.phizic.web.erkcemployee.log.message;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.log.MessageLogForm;

/**
 * @author akrenev
 * @ created 01.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ERKCMessageLogForm extends MessageLogForm
{
	public static final Form FILTER_FORM = getFilterForm();
	
	private static Form getFilterForm()
	{
		FormBuilder builder = getPartlyFormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationUID");
		fieldBuilder.setDescription("������������� ��������");

		builder.addField(fieldBuilder.build());

		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);
		DateParser timeParser = new DateParser(TIMESTAMP_FORMAT);
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setEnabledExpression(new RhinoExpression("form.operationUID == ''"));

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("��������� ����");
		fieldBuilder.addValidators(requiredFieldValidator, new DateFieldValidator(DATESTAMP_FORMAT, "���� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);

		builder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromTime");
		fieldBuilder.setDescription("��������� �����");
		fieldBuilder.setValidators(new FieldValidator[]{new DateFieldValidator(TIMESTAMP_FORMAT, "����� ������ ���� � ������� ��:��")});
		fieldBuilder.setParser(timeParser);

		builder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("�������� ����");
		fieldBuilder.addValidators(requiredFieldValidator, new DateFieldValidator(DATESTAMP_FORMAT, "���� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);

		builder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toTime");
		fieldBuilder.setDescription("�������� �����");
		fieldBuilder.setValidators(new FieldValidator[]{new DateFieldValidator(TIMESTAMP_FORMAT, "����� ������ ���� � ������� ��:��")});
		fieldBuilder.setParser(timeParser);

		builder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");

		builder.setFormValidators(dateTimeCompareValidator);
		return builder.build();
	}
}
