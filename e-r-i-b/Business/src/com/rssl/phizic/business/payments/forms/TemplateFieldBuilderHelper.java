package com.rssl.phizic.business.payments.forms;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.types.SubType;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.payments.forms.validators.DublicateFieldNameValidator;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 04.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class TemplateFieldBuilderHelper
{
	private static final DublicateFieldNameValidator dublicateFieldNameValidator = new DublicateFieldNameValidator();

	/**
	 * ������������ ������ �������� �������� ����� � �����������(�����������)
	 * @param fields ������ �������� �������� ����� ��� ���������
	 * @param subType ���. ��� ����
	 * @param externalPayment ������ �� ������������ ����������
	 * @param formData �������� �����
	 * @param billingCode ��� ��������
	 * @return ������������� ����(��������� ��� ������ ���� ����������).
	 */
	public static List<Field> adaptFields(List<? extends com.rssl.phizic.gate.payments.systems.recipients.Field> fields, Map<String, String> formData, SubType subType, boolean externalPayment, String billingCode) throws BusinessException
	{
		if (CollectionUtils.isEmpty(fields))
		{
			return new ArrayList<com.rssl.common.forms.Field>();
		}

		List<com.rssl.common.forms.Field> result = new ArrayList<Field>(fields.size());
		for (com.rssl.phizic.gate.payments.systems.recipients.Field field : fields)
		{
			Field newField = adaptField(field, formData, subType, externalPayment, billingCode);
			if (dublicateFieldNameValidator.validate(newField.getName()))
			{
				result.add(newField);
				continue;
			}
			throw new BusinessException("� ������� ���������� ������������� ����:"+ newField.getName() + ". ������ ����������.");
		}
		return result;
	}

	/**
	 * ������������ �������� �������� ����� � �����������(�����������)
	 * @param field ����
	 * @param formData ������ �� �������
	 * @param subType ���. ��� ����
	 * @param externalPayment ������ �� ������������ ����������
	 * @param billingCode ��� ��������
	 * @return ���� ���������� �����
	 */
	public static Field adaptField(com.rssl.phizic.gate.payments.systems.recipients.Field field, Map<String, String> formData, SubType subType, boolean externalPayment, String billingCode)
	{
		FieldBuilder fb = ExtendedFieldBuilderHelper.getFieldBuilder(field, null, subType, billingCode);
		if (!field.isVisible() || !field.isEditable())
		{
			String value = StringHelper.isNotEmpty((String) field.getValue()) ? (String) field.getValue() : field.getDefaultValue();
			fb.setValueExpression(new ConstantExpression(value));
			fb.setInitalValueExpression(new ConstantExpression(value));
		}
		else if (isStatic(field, subType, externalPayment))
		{
			fb.setValueExpression(new ConstantExpression(formData.get(field.getExternalId())));
			fb.setInitalValueExpression(new ConstantExpression(formData.get(field.getExternalId())));
			fb.setSubType(SubType.STATIC);
		}
		return fb.build();
	}

	/**
	 * ������������ �������� ���� � ������ ����������� ����������
	 * @param field ����
	 * @param value ��������
	 * @return ����
	 */
	public static Field adaptField(Field field, String value) throws BusinessException
	{
		return adaptField(field, value, false);
	}
	/**
	 * ������������ �������� ���� � ������ ����������� ����������
	 * @param field ����
	 * @param value ��������
	 * @param isNeedDynamic ������������� ������� ������������
	 * @return ����
	 */
	public static Field adaptField(Field field, String value, boolean isNeedDynamic) throws BusinessException
	{
		try
		{
			if (SubType.STATIC != field.getSubType() || isNeedDynamic)
			{
				return field;
			}

			FieldBuilder fb = new FieldBuilder();

			fb.setName(field.getName());
			fb.setDescription(field.getDescription());
			fb.setType(field.getType().getName());
			fb.setSubType(field.getSubType());
			fb.setSource(field.getSource());

			Object fieldValue = field.getType().getDefaultParser().parse(value);

			fb.setValueExpression(new ConstantExpression(fieldValue));
			fb.setInitalValueExpression(new ConstantExpression(fieldValue));
			fb.setMask(field.getMask());
			fb.addValidators(field.getValidators().toArray(new FieldValidator[field.getValidators().size()]));

			return fb.build();
		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}
	}

	private static boolean isStatic(com.rssl.phizic.gate.payments.systems.recipients.Field field, SubType subType, boolean externalPayment)
	{
		if (externalPayment)
		{
			//� ������ �� ������������ ���������� ��� ���� ������������
			return false;
		}

		//��� ������ ����������
		return SubType.STATIC == subType && !field.isMainSum() && (field.isKey() || field.isSaveInTemplate());
	}
}
