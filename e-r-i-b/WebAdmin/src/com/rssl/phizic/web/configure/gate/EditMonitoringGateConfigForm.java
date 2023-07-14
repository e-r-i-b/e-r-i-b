package com.rssl.phizic.web.configure.gate;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.common.forms.validators.MultiLineTextValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.gate.monitoring.InactiveType;
import com.rssl.phizic.gate.monitoring.MonitoringGateState;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditMonitoringGateConfigForm extends EditFormBase
{
	public static final String SERVICE_NAME_FIELD_NAME = "serviceName";
	public static final String STATE_FIELD_NAME = "state";
	public static final String EDITING_STATE_FIELD_NAME = "editingState";
	public static final String ID_FIELD_NAME = "id";
	public static final String USE_FIELD_NAME = "use";
	public static final String TIME_FIELD_NAME = "time";
	public static final String COUNT_FIELD_NAME = "count";
	public static final String PERCENT_FIELD_NAME = "percent";
	public static final String TIMEOUT_FIELD_NAME = "timeout";
	public static final String MESSAGE_TEXT_FIELD_NAME = "messageText";
	public static final String RECOVERY_TIME_FIELD_NAME = "recoveryTime";
	public static final String AVAILABLE_CHANGE_INACTIVE_TYPE_FIELD_NAME = "availableChangeInactiveType";
	public static final String INACTIVE_TYPE_FIELD_NAME = "inactiveType";

	public static final Form EDIT_FORM = createEditForm();

	public static final int MAX_TEXT_SYMBOL_COUNT = 500;

	@SuppressWarnings({"OverlyLongMethod", "ReuseOfLocalVariable"})
	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SERVICE_NAME_FIELD_NAME);
		fieldBuilder.setDescription("������������ �������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("������������� ������������ ������� �� ��������."));
		formBuilder.addField(fieldBuilder.build());

		List<String> states = new ArrayList<String>();
		for (MonitoringGateState state: MonitoringGateState.values())
			states.add(state.name());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EDITING_STATE_FIELD_NAME);
		fieldBuilder.setDescription("������������� ���������.");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("������������� ��������� �� ��������."),
				new ChooseValueValidator(states, "������������� ��������� �� ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(USE_FIELD_NAME);
		fieldBuilder.setDescription("�������� �������.");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("����������, ������� �������� �������� �������."));
		formBuilder.addField(fieldBuilder.build());

		NumericRangeValidator numericRangeValidator = new NumericRangeValidator();
		numericRangeValidator.setMessage("�������� ������� �������� ���������� �������� ������ ���� � ��������� 1 - 99999");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "99999");
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(TIME_FIELD_NAME);
		fieldBuilder.setDescription("������ �������� ���������� ��������.");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("����������, ������� ������ �������� ���������� ��������."), numericRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		numericRangeValidator = new NumericRangeValidator();
		numericRangeValidator.setMessage("�������� ���������� �������� ���������� �������� ������ ���� � ��������� 1 - 99999");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "99999");
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(COUNT_FIELD_NAME);
		fieldBuilder.setDescription("��������� �������� ���������� ��������.");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("����������, ������� ��������� �������� ���������� ��������."), numericRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		numericRangeValidator = new NumericRangeValidator();
		numericRangeValidator.setMessage("�������� ���������� �������� ��������� �������� ������ ���� � ��������� 1 - 100");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PERCENT_FIELD_NAME);
		fieldBuilder.setDescription("��������� �������� �������� ��������.");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("����������, ������� ��������� �������� �������� ��������."), numericRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		numericRangeValidator = new NumericRangeValidator();
		numericRangeValidator.setMessage("�������� ������� ������ �� ������ ������ ���� � ��������� 1 - 99999");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "99999");
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(TIMEOUT_FIELD_NAME);
		fieldBuilder.setDescription("����� ������ �� ������.");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("����������, ������� ����� ������ �� ������."), numericRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MESSAGE_TEXT_FIELD_NAME);
		fieldBuilder.setDescription("����� ���������.");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("����������, ������� ����� ���������."),
				new MultiLineTextValidator("���� ����� ��������� �� ������ ���������", MAX_TEXT_SYMBOL_COUNT));
		formBuilder.addField(fieldBuilder.build());

		numericRangeValidator = new NumericRangeValidator();
		numericRangeValidator.setMessage("�������� ������� �������������� ������� ������ ���� � ��������� 1 - 9999");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "9999");
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(RECOVERY_TIME_FIELD_NAME);
		fieldBuilder.setDescription("����� �������������� �������.");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("����������, ������� ����� �������������� �������."),
				numericRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		List<String> types = new ArrayList<String>();
		for (InactiveType type: InactiveType.values())
			types.add(type.name());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(INACTIVE_TYPE_FIELD_NAME);
		fieldBuilder.setDescription("��� �������������.");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator("��� ������������� �� ��������.");
		requiredFieldValidator.setEnabledExpression(new RhinoExpression("form.".concat(AVAILABLE_CHANGE_INACTIVE_TYPE_FIELD_NAME).concat(" == true")));
		fieldBuilder.addValidators(requiredFieldValidator,
				new ChooseValueValidator(types, "��� ������������� �� ��������."));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
