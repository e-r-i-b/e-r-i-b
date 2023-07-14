package com.rssl.phizic.web.log.csa;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.validators.DateTimePeriodMultiFieldValidator;
import com.rssl.phizic.logging.operations.CSAOperations;
import com.rssl.phizic.logging.operations.CSAOperationsState;

/**
 * @author vagin
 * @ created 25.10.2012
 * @ $Author$
 * @ $Revision$
 * ����� ������� �������� ������������� ���
 */
public class ViewCSAOperationLogForm extends ViewCSASystemLogForm
{
	public static final Form OPERATION_LOG_FILTER_FORM = createFilterForm();

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = getPartlyFormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxRows");
		fieldBuilder.setDescription("������������ ���������� �������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("type");
		fieldBuilder.setDescription("��������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("state");
		fieldBuilder.setDescription("������ ��������");
		formBuilder.addField(fieldBuilder.build());

		//����������� ��������������� ����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("searchInDayInterval");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("�������� �� ����� � ��������� ���");
		fieldBuilder.setValueExpression(new RhinoExpression("form." + CLIENT_FIO_FIELD_NAME + " !='' || form." + DOCUMENT_NUMBER_FIELD_NAME + " !='' || form." + LOGIN_FIELD_NAME + " !=''"));
		formBuilder.addField(fieldBuilder.build());

		//����������� ��������������� ����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("searchInHourInterval");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("�������� �� ����� � ��������� ����");
		fieldBuilder.setValueExpression(new RhinoExpression("form." + CLIENT_FIO_FIELD_NAME + " =='' && form." + DOCUMENT_NUMBER_FIELD_NAME + " =='' && form." + LOGIN_FIELD_NAME + " ==''"));
		formBuilder.addField(fieldBuilder.build());

		DateTimePeriodMultiFieldValidator monthPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		monthPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.MONTH_TYPE_PERIOD);
		monthPeriodMultiFieldValidator.setEnabledExpression(new RhinoExpression("Boolean(form.searchInMonthInterval)"));
		monthPeriodMultiFieldValidator.setLengthPeriod(1);
		monthPeriodMultiFieldValidator.setMessage("����������, ������� ������ �� ����� 30 ����.");

		//���� ��������� ������ ���� �� �����, �� ����������� � ���� ����
		DateTimePeriodMultiFieldValidator dayPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		dayPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.DAY_TYPE_PERIOD);
		dayPeriodMultiFieldValidator.setEnabledExpression(new RhinoExpression("!Boolean(form.searchInMonthInterval) && Boolean(form.searchInDayInterval)"));
		dayPeriodMultiFieldValidator.setMessage("����������, ������� ������ �� ����� ������ ��� ��� ��������� ����: ������, �������, ���� ��������, ������.");

		//���� ��� ���� �� ���������, �� ����������� ���� ���
		DateTimePeriodMultiFieldValidator hourPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		hourPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.HOUR_TYPE_PERIOD);
		hourPeriodMultiFieldValidator.setEnabledExpression(new RhinoExpression("Boolean(form.searchInHourInterval)"));
		hourPeriodMultiFieldValidator.setMessage("����������, ������� ������ �� ����� 1 ����");

		formBuilder.addFormValidators(monthPeriodMultiFieldValidator, dayPeriodMultiFieldValidator, hourPeriodMultiFieldValidator);

		return formBuilder.build();
	}

	public CSAOperationsState[] getCSAOperationsStates()
	{
		return CSAOperationsState.values();
	}

	public CSAOperations[] getCSAOperations()
	{
		return CSAOperations.values();
	}
}
