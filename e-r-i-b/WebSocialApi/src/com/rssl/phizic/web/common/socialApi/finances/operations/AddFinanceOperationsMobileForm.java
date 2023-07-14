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
 * Форма добавления новой операции
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
	 * @return название операции
	 */
	public String getOperationName()
	{
		return operationName;
	}

	/**
	 * @param operationName - название операции
	 */
	public void setOperationName(String operationName)
	{
		this.operationName = operationName;
	}

	/**
	 * @return сумма операции
	 */
	public String getOperationAmount()
	{
		return operationAmount;
	}

	/**
	 * @param operationAmount - сумма операции
	 */
	public void setOperationAmount(String operationAmount)
	{
		this.operationAmount = operationAmount;
	}

	/**
	 * @return дата операции
	 */
	public String getOperationDate()
	{
		return operationDate;
	}

	/**
	 * @param operationDate - дата операции
	 */
	public void setOperationDate(String operationDate)
	{
		this.operationDate = operationDate;
	}

	/**
	 * @return идентификатор категории операции
	 */
	public String getOperationCategoryId()
	{
		return operationCategoryId;
	}

	/**
	 * @param operationCategoryId - идентификатор категории операции
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
		moneyFieldValidator.setMessage("Значение суммы операции должно быть в диапазоне 0,00 - 9 999 999 999 999 999,99 руб");

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationName");
		fieldBuilder.setDescription("Название операции");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationAmount");
		fieldBuilder.setDescription("Сумма");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator, moneyFieldValidator);
		fields.add(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationDate");
		fieldBuilder.setDescription("Дата");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dateParser);
		fieldBuilder.addValidators(requiredFieldValidator, new DateNotInFutureValidator());
		fields.add(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationCategoryId");
		fieldBuilder.setDescription("Категория операции");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}
