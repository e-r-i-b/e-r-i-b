package com.rssl.phizic.web.common.socialApi.finances.operations;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateNotInFutureValidator;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * ����� ���������� ����� ��������
 * @author lepihina
 * @ created 15.01.14
 * @ $Author$
 * @ $Revision$
 */
public class AddFinanceOperationsMobileForm extends EditFormBase
{
	private static final String DATE_FORMAT = "dd.MM.yyyy";
	public static final Form EDIT_FORM = createForm();

	private String operationName;
	private String operationAmount;
	private String operationDate;
	private String operationCategoryId;

	/**
	 * @return �������� ��������
	 */
	public String getOperationName()
	{
		return operationName;
	}

	/**
	 * @param operationName - �������� ��������
	 */
	public void setOperationName(String operationName)
	{
		this.operationName = operationName;
	}

	/**
	 * @return ����� ��������
	 */
	public String getOperationAmount()
	{
		return operationAmount;
	}

	/**
	 * @param operationAmount - ����� ��������
	 */
	public void setOperationAmount(String operationAmount)
	{
		this.operationAmount = operationAmount;
	}

	/**
	 * @return ���� ��������
	 */
	public String getOperationDate()
	{
		return operationDate;
	}

	/**
	 * @param operationDate - ���� ��������
	 */
	public void setOperationDate(String operationDate)
	{
		this.operationDate = operationDate;
	}

	/**
	 * @return ������������� ��������� ��������
	 */
	public String getOperationCategoryId()
	{
		return operationCategoryId;
	}

	/**
	 * @param operationCategoryId - ������������� ��������� ��������
	 */
	public void setOperationCategoryId(String operationCategoryId)
	{
		this.operationCategoryId = operationCategoryId;
	}

	private static Form createForm()
	{
		DateParser dateParser = new DateParser(DATE_FORMAT);

		List<Field> fields = new ArrayList<Field>();
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		MoneyFieldValidator moneyFieldValidator = new MoneyFieldValidator();
		moneyFieldValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "9999999999999999.99");
		moneyFieldValidator.setMessage("�������� ����� �������� ������ ���� � ��������� 0,00 - 9 999 999 999 999 999,99 ���");

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationName");
		fieldBuilder.setDescription("�������� ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationAmount");
		fieldBuilder.setDescription("�����");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator, moneyFieldValidator);
		fields.add(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationDate");
		fieldBuilder.setDescription("����");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dateParser);
		fieldBuilder.addValidators(requiredFieldValidator, new DateNotInFutureValidator());
		fields.add(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationCategoryId");
		fieldBuilder.setDescription("��������� ��������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}
