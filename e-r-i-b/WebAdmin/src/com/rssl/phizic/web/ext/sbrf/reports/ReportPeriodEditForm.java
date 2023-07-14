package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.ListUtil;

import java.util.Date;

/**
 * @author Mescheryakova
 * @ created 19.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ReportPeriodEditForm extends ReportEditForm
{
	public static final Form EDIT_FORM = createForm();

	protected static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
        FieldBuilder fieldBuilder;

	    DateNotInFutureValidator notInFutureValidator = new DateNotInFutureValidator(DATESTAMP);
	    notInFutureValidator.setMessage("���� ��������� ������� �� ����� ���� ������ " + DateHelper.toString(new Date()) + ".");

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("endDate");
        fieldBuilder.setDescription("���� ��������� �������");
	    fieldBuilder.setType("date");
	    fieldBuilder.addValidators (
			new RequiredFieldValidator(),
			new DateFieldValidator(DATESTAMP, "������� ���������� ���� ��������� ������� � ������� ��.��.����."),
			notInFutureValidator
		);
	    fieldBuilder.setParser(dateParser);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �������");
		fieldBuilder.setName("periodType");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new ChooseValueValidator(ListUtil.fromArray(new String[]{"day","month", "week"}))
		);
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addFields(ReportEditForm.EDIT_FORM.getFields());

		CompareValidator dateTimeCompareValidator = new CompareValidator();
		dateTimeCompareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(CompareValidator.FIELD_O1, "startDate");
		dateTimeCompareValidator.setBinding(CompareValidator.FIELD_O2, "endDate");
		dateTimeCompareValidator.setMessage("�������� ���� �� ����� ���� ������ ���������!");		

	    CompareValidator minDateValidator = new CompareValidator();
		minDateValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		minDateValidator.setBinding(CompareValidator.FIELD_O1, "minDate");
		minDateValidator.setBinding(CompareValidator.FIELD_O2, "endDate");
		minDateValidator.setMessage("���� ��������� ������� ���������� ������ ������� �� ������� ��������� � ���� ���.");

	    CompareValidator maxDateValidator = new CompareValidator();
		maxDateValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		maxDateValidator.setBinding(CompareValidator.FIELD_O1, "endDate");
		maxDateValidator.setBinding(CompareValidator.FIELD_O2, "maxDate");
		maxDateValidator.setMessage("���� ��������� ������� ���������� ������ ������� �� ������� ��������� � ���� ���.");

	    DatePeriodMultiFieldValidator dateWeekPeriodValidator = new DatePeriodMultiFieldValidator();
		dateWeekPeriodValidator.setEnabledExpression(new RhinoExpression("form.periodType == 'week'"));
		dateWeekPeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_PERIOD_TYPE, "periodType");
		dateWeekPeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_FROM, "startDate");
		dateWeekPeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_TO, "endDate");
		dateWeekPeriodValidator.setMessage("��� ������������ ������ �� ������ ������ ������ ���� ������ ����� 7 ����. ����������, ������� ������ ����.");

	    DatePeriodMultiFieldValidator dateMonthPeriodValidator = new DatePeriodMultiFieldValidator();
		dateMonthPeriodValidator.setEnabledExpression(new RhinoExpression("form.periodType == 'month'"));
		dateMonthPeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_PERIOD_TYPE, "periodType");
		dateMonthPeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_FROM, "startDate");
		dateMonthPeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_TO, "endDate");
		dateMonthPeriodValidator.setMessage("��� ������������ ������ �� ����� ������ ������ ���� ������ �� 28 �� 31 ���. ����������, ������� ������ ����.");

		formBuilder.addFormValidators(ReportEditForm.createForm(
				"���� ������ ������� ���������� ������ ������� �� ������� ��������� � ���� ���.").getFormValidators());
	    formBuilder.addFormValidators(dateTimeCompareValidator);
		formBuilder.addFormValidators(maxDateValidator);
	    formBuilder.addFormValidators(minDateValidator);
	    formBuilder.addFormValidators(dateWeekPeriodValidator);
	    formBuilder.addFormValidators(dateMonthPeriodValidator);
		return formBuilder.build();
 	}
}
