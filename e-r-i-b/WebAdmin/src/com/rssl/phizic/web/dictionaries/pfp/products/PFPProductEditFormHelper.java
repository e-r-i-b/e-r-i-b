package com.rssl.phizic.web.dictionaries.pfp.products;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.parsers.EnumParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.products.types.TableColumn;
import com.rssl.phizic.business.dictionaries.pfp.products.types.TableParameters;
import com.rssl.phizic.common.types.SegmentCodeType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 25.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class PFPProductEditFormHelper
{
	public static final String TARGET_GROUP_FIELD_NAME_PREFIX = "targetGroup";
	public static final String TABLE_PARAMETER_NAME_PREFIX = "tableParam";
	public static final String USE_ICON_PARAMETER_NAME = "useIcon";
	public static final String MIN_INCOME_FIELD_NAME = "minIncome";
	public static final String MAX_INCOME_FIELD_NAME = "maxIncome";
	public static final String DEFAULT_INCOME_FIELD_NAME = "defaultIncome";
	public static final String AXIS_X_PARAMETER_NAME = "axisXParam";
	public static final String AXIS_Y_PARAMETER_NAME = "axisYParam";

	private static List<Field> targetGroupFields = Collections.unmodifiableList(initTargetGroupFields());

	private static List<Field> initTargetGroupFields()
	{
		List<Field> resultList = new ArrayList<Field>();
		for (SegmentCodeType type : SegmentCodeType.values())
		{
			FieldBuilder fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(TARGET_GROUP_FIELD_NAME_PREFIX + type.name());
			fieldBuilder.setDescription("�������");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.setParser(new EnumParser<SegmentCodeType>(SegmentCodeType.class));
			fieldBuilder.addValidators(new EnumFieldValidator<SegmentCodeType>(SegmentCodeType.class, "������� �������� �� ������."));
			resultList.add(fieldBuilder.build());
		}
		return resultList;
	}

	/**
	 * @return ���� ��� ��������� ���������
	 */
	public static List<Field> getTargetGroupFields()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return targetGroupFields;
	}

	/**
	 * @param enabledExpression ��������� ��� ���������� ����������
	 * @return ��������� �������� ��������
	 */
	public static MultiFieldsValidator getTargetGroupValidator(Expression enabledExpression)
	{
		RequiredMultiFieldValidator validator = new RequiredMultiFieldValidator();
		for (SegmentCodeType type : SegmentCodeType.values())
			validator.setBinding(TARGET_GROUP_FIELD_NAME_PREFIX + type.name(), TARGET_GROUP_FIELD_NAME_PREFIX + type.name());
		validator.setMessage("����������, ������� ������� ��������, ��� ������� ������ ������������ ������ �������.");
		validator.setEnabledExpression(enabledExpression);
		return validator;
	}

	public static List<Field> getChartParametersFields(Expression enabledExpression)
	{
		List<Field> resultList = new ArrayList<Field>();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(AXIS_X_PARAMETER_NAME);
		fieldBuilder.setDescription("��� �������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("[1-5]","������� ���������� �� ��� ������� � ��������� �� 1 �� 5 ������������."),
				new RequiredFieldValidator("������� ���������� �� ��� ������� � ��������� �� 1 �� 5 ������������.")
		);
		fieldBuilder.setEnabledExpression(enabledExpression);
		resultList.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(AXIS_Y_PARAMETER_NAME);
		fieldBuilder.setDescription("��� �������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("[1-5]", "������� ���������� �� ��� ������� � ��������� �� 1 �� 5 ������������."),
				new RequiredFieldValidator("������� ���������� �� ��� ������� � ��������� �� 1 �� 5 ������������.")
		);
		fieldBuilder.setEnabledExpression(enabledExpression);
		resultList.add(fieldBuilder.build());

		return resultList;
	}
	/**
	 * @param tableParameters ��������� ����������� �������
	 * @param enabledExpression ��������� ��� ���������� �����
	 * @return ��������� �������� ��������
	 */
	public static List<Field> getTableParametersFields(TableParameters tableParameters, Expression enabledExpression)
	{
		List<Field> resultList = new ArrayList<Field>();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(USE_ICON_PARAMETER_NAME);
		fieldBuilder.setDescription("������������ ������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(enabledExpression);
		resultList.add(fieldBuilder.build());

		for (TableColumn tableColumn : tableParameters.getColumns())
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(TABLE_PARAMETER_NAME_PREFIX + tableColumn.getOrderIndex());
			fieldBuilder.setDescription("�������� ������� " + tableColumn.getValue());
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator("��������� ��������� ����������� � �������."), new LengthFieldValidator(BigInteger.valueOf(100)));
			fieldBuilder.setEnabledExpression(enabledExpression);
			resultList.add(fieldBuilder.build());
		}
		return resultList;
	}

	/**
	 * @return ������ ����� ��� ������� ����������
	 */
	public static List<Field> getIncomeFields()
	{
		List<Field> resultList = new ArrayList<Field>();
		resultList.add(getPercentField(MIN_INCOME_FIELD_NAME, "����������", false));
		resultList.add(getPercentField(MAX_INCOME_FIELD_NAME, "����������", false));
		resultList.add(getPercentField(DEFAULT_INCOME_FIELD_NAME, "���������� �� ���������", true));
		return resultList;
	}

	/**
	 * @return ������ ����������� �����
	 */
	public static List<MultiFieldsValidator> getIncomeFormValidators()
	{
		List<MultiFieldsValidator> resultList = new ArrayList<MultiFieldsValidator>();

		MultiFieldsValidator requiredMultiFieldValidator = new RequiredAllMultiFieldValidator();
		requiredMultiFieldValidator.setBinding(MIN_INCOME_FIELD_NAME, MIN_INCOME_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(MAX_INCOME_FIELD_NAME, MAX_INCOME_FIELD_NAME);
		requiredMultiFieldValidator.setEnabledExpression(new RhinoExpression("(" + getNotEmptyExpression(MIN_INCOME_FIELD_NAME) + ") || (" + getNotEmptyExpression(MAX_INCOME_FIELD_NAME) + ")"));
		requiredMultiFieldValidator.setMessage("����������, ������� ����������� � ������������ ���������� ��� ������� ��������.");
		resultList.add(requiredMultiFieldValidator);

		resultList.add(getCompareValidator(MIN_INCOME_FIELD_NAME, MAX_INCOME_FIELD_NAME,     "����������� ���������� ���������� �������� ������ ���� ������ ������������ ��������."));
		resultList.add(getCompareValidator(MIN_INCOME_FIELD_NAME, DEFAULT_INCOME_FIELD_NAME, "����������, ������� ���������� �� ��������� � �������� ��������� ��������� ���������� ����������."));
		resultList.add(getCompareValidator(DEFAULT_INCOME_FIELD_NAME, MAX_INCOME_FIELD_NAME, "����������, ������� ���������� �� ��������� � �������� ��������� ��������� ���������� ����������."));

		return resultList;
	}

	private static Field getPercentField(String name, String description, boolean required)
	{
		NumericRangeValidator incomeValidator = new NumericRangeValidator();
		incomeValidator.setMessage("���� " + description + " ������ ��������� ����� �� 0 �� 100");
		incomeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		incomeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new BigDecimalParser());
		if (required)
			fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(100((\\.|,)0)?|(\\d{1,2}((\\.|,)\\d)?))$", "���������� ��������� " + description + ". ������ ��� ���������� ������� ###.#."));

		return fieldBuilder.build();
	}

	private static MultiFieldsValidator getCompareValidator(String fieldName1, String fieldName2, String message)
	{
		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setMessage(message);
		compareValidator.setBinding(CompareValidator.FIELD_O1, fieldName1);
		compareValidator.setBinding(CompareValidator.FIELD_O2, fieldName2);
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareValidator.setEnabledExpression(new RhinoExpression("(" + getNotEmptyExpression(fieldName1) + ") || (" + getNotEmptyExpression(fieldName2) + ")"));
		return compareValidator;
	}

	private static String getNotEmptyExpression(String fieldName)
	{
		return "!isNaN(parseFloat(form.".concat(fieldName).concat("))");
	}
}
