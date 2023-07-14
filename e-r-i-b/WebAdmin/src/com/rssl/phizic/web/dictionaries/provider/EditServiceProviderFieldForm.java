package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.payments.forms.validators.DublicateFieldNameValidator;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigInteger;
import java.util.List;

/**
 * @author krenev
 * @ created 05.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditServiceProviderFieldForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();
	private Long fieldId;//������������ �������������� ����.
	private boolean editable;
	private List<RequisiteType> requisiteTypeList;
	//� id ��������� ������������ ����������.

	public Long getFieldId()
	{
		return fieldId;
	}

	public void setFieldId(Long fieldId)
	{
		this.fieldId = fieldId;
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	public boolean isEditable()
	{
		return editable;
	}

	public List<RequisiteType> getRequisiteTypeList()
	{
		return requisiteTypeList;
	}

	public void setRequisiteTypeList(List<RequisiteType> requisiteTypeList)
	{
		this.requisiteTypeList = requisiteTypeList;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ���� �� ������� �������");
		fieldBuilder.setName("exteranlId");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,40}", "�������� ��� �� ������� ������� �� ������ ��������� 40 ��������"),
				new RegexpFieldValidator("^[a-zA-Z_:]{1}[a-zA-Z0-9_:]*$", "���� ��� �� ������� ������� ����� ��������� ����� � ����� ���������� ��������, ������� �_� � �:�, �� �� ������ ���������� � �����"),
				new DublicateFieldNameValidator("� ����� ������� ���������� ������������� ����. ���������� ������� ������ ��� ���� �� ������� �������.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ���� ��� �������");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,60}", "�������� ������������ ���� ��� ������� �� ������ ��������� 60 ��������")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� � ���� ��� �������");
		fieldBuilder.setName("description");
		fieldBuilder.setType(StringType.INSTANCE.getName());

		MultiLineTextValidator descriptionValidator = new MultiLineTextValidator("�������� ����������� � ���� ��� ������� �� ������ ���������", 200);
		fieldBuilder.addValidators(descriptionValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� �� ����������");
		fieldBuilder.setName("hint");
		fieldBuilder.setType(StringType.INSTANCE.getName());

		MultiLineTextValidator hintValidator = new MultiLineTextValidator("�������� ��������� �� ���������� �� ������ ���������", 200);
		fieldBuilder.addValidators(hintValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ����");
		fieldBuilder.setName("type");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("(string|number|date|list|integer|set|money|choice)")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ������������ ��������");
		fieldBuilder.setName("extendedDescId");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.type == 'choice'"));
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(BigInteger.valueOf(50)));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ��������");
		fieldBuilder.setName("maxlength");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		NumericRangeValidator rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		rangeValidator.setMessage("�������� ���� ������������ ��a����� ������ ���� �� 0 �� 100");
		fieldBuilder.addValidators(rangeValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ��������");
		fieldBuilder.setName("minlength");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		rangeValidator.setMessage("�������� ���� ����������� �������� ������ ���� �� 0 �� 100");
		fieldBuilder.addValidators(rangeValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ��������� ��������");
		fieldBuilder.setName("numberPrecision");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "20");
		rangeValidator.setMessage("�������� ���� �������� ��������� �������� ������ ���� �� 0 �� 20");
		fieldBuilder.addValidators(rangeValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ��� ����������");
		fieldBuilder.setName("mandatory");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������");
		fieldBuilder.setName("editable");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName("visible");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����");
		fieldBuilder.setName("sum");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("key");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� � ����");
		fieldBuilder.setName("isForBill");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� � ����� SMS-��������� � ����������� ������� ��� ������������� �������");
		fieldBuilder.setName("isIncludeInSMS");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ��� ���������� � ������� �������");
		fieldBuilder.setName("isSaveInTemplate");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� �� ����� �������������");
		fieldBuilder.setName("isHideInConfirmation");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ �������");
		fieldBuilder.setName("businessSubType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(phone|wallet)"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� �� ���������");
		fieldBuilder.setName("defaultValue");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,50}", "�������� �������� �� ��������� �� ������ ��������� 50 ��������"),
				new RegexpFieldValidator("[^@&��]*","�� ������� ������������ �������, ��������, @, &, �, � . ����������, ������� ������ �������� � ���� �������� �� ���������.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ���������");
		fieldBuilder.setName("values");
		fieldBuilder.setType(StringType.INSTANCE.getName());

		Expression fieldTypeExpression = new RhinoExpression("form.type == 'list' ");
		RequiredFieldValidator dependFromTypeValidator = new RequiredFieldValidator();
		dependFromTypeValidator.setEnabledExpression(fieldTypeExpression);

		RegexpFieldValidator lineFeedNotCaontainsValidator =
				new RegexpFieldValidator(".*", "���� ��������� �������� �� ������ ��������� ������� �������� ������");
		RegexpFieldValidator lenghtValuesValidator = new RegexpFieldValidator(".{0,500}", "�������� ��������� �������� �� ������ ��������� 500 ��������");
		RegexpFieldValidator specialSimbolValidator = new RegexpFieldValidator("[^@&��]*","�� ������� ������������ �������, ��������, @, &, �, � . ����������, ������� ������ �������� � ���� ��������� ���������.");
		fieldBuilder.addValidators(dependFromTypeValidator, lineFeedNotCaontainsValidator, lenghtValuesValidator, specialSimbolValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����");
		fieldBuilder.setName("requisiteTypes");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new FieldRequisiteTypeValidator("�� ������� ������������ ��������� ����. ����������, �������� ������ ��������."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����");
		fieldBuilder.setName("mask");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
	    compareValidator.setBinding(CompareValidator.FIELD_O1, "minlength");
	    compareValidator.setBinding(CompareValidator.FIELD_O2, "maxlength");
	    compareValidator.setMessage("�� ����������� ������� ����������� ����� ����. ����������� ����� �� ������ ��������� ������������ ����� ����.");
	    fb.setFormValidators(compareValidator);

		return fb.build();
	}
}
