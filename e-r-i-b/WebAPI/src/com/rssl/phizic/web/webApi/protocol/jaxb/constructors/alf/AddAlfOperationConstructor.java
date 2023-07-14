package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateNotInFutureValidator;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.OperationType;
import com.rssl.phizic.operations.finances.AddCardOperationOperation;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.AbstractFinanceConstructor;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.AddAlfOperationRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.AddAlfOperationRequestBody;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.SimpleResponse;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Balovtsev
 * @since 14.05.2014
 */
public class AddAlfOperationConstructor extends AbstractFinanceConstructor<AddAlfOperationRequest, SimpleResponse>
{
	protected static final String FIELD_OPERATION_NAME        = "operationName";
	protected static final String FIELD_OPERATION_DATE        = "operationDate";
	protected static final String FIELD_OPERATION_AMOUNT      = "operationAmount";
	protected static final String FIELD_OPERATION_CATEGORY_ID = "operationCategoryId";

	@Override
	protected Form getForm()
	{
		return AddAlfOperation.FORM;
	}

	protected AddCardOperationOperation createAddAlfOperation() throws BusinessException, BusinessLogicException
	{
		AddCardOperationOperation operation = createOperation(AddCardOperationOperation.class, "AddOperationsService");
		operation.initialize();
		return operation;
	}

	@Override
	protected MapValuesSource getMapValueSource(AddAlfOperationRequest request)
	{
		AddAlfOperationRequestBody body = request.getBody();

		Map<String, Object> sources = new HashMap<String, Object>();
		sources.put(FIELD_OPERATION_NAME,        body.getOperationName());
		sources.put(FIELD_OPERATION_DATE,        body.getOperationDate());
		sources.put(FIELD_OPERATION_AMOUNT,      body.getOperationAmount());
		sources.put(FIELD_OPERATION_CATEGORY_ID, body.getOperationCategoryId());

		return new MapValuesSource(sources);
	}

	@Override
	protected SimpleResponse doMakeResponse(Map<String, Object> sources) throws Exception
	{
		AddCardOperationOperation operation = createAddAlfOperation();
		operation.setCategory((Long) sources.get(FIELD_OPERATION_CATEGORY_ID));

		CardOperation cardOperation = (CardOperation) operation.getEntity();
		cardOperation.setOperationType(OperationType.OTHER);
		cardOperation.setDate((Calendar) sources.get(FIELD_OPERATION_DATE));
		cardOperation.setDescription((String) sources.get(FIELD_OPERATION_NAME));
		cardOperation.setNationalAmount(new BigDecimal((Double) sources.get(FIELD_OPERATION_AMOUNT)).negate());
		operation.save();

		return new SimpleResponse();
	}

	private static class AddAlfOperation
	{
		public static final Form FORM = createForm();

		private static Form createForm()
		{
			List<Field> fields = new ArrayList<Field>();
			RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

			MoneyFieldValidator moneyFieldValidator = new MoneyFieldValidator();
			moneyFieldValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "9999999999999999.99");
			moneyFieldValidator.setMessage("Значение суммы операции должно быть в диапазоне 0,00 - 9 999 999 999 999 999,99 руб");

			DateParser dateParser = new DateParser();

			FieldBuilder fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(FIELD_OPERATION_NAME);
			fieldBuilder.setDescription("Название операции");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(requiredFieldValidator);
			fields.add(fieldBuilder.build());

			//noinspection ReuseOfLocalVariable
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(FIELD_OPERATION_AMOUNT);
			fieldBuilder.setDescription("Сумма");
			fieldBuilder.setType(MoneyType.INSTANCE.getName());
			fieldBuilder.addValidators(requiredFieldValidator, moneyFieldValidator);
			fields.add(fieldBuilder.build());

			//noinspection ReuseOfLocalVariable
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(FIELD_OPERATION_DATE);
			fieldBuilder.setDescription("Дата");
			fieldBuilder.setType(DateType.INSTANCE.getName());
			fieldBuilder.setParser(dateParser);
			fieldBuilder.addValidators(requiredFieldValidator, new DateNotInFutureValidator());
			fields.add(fieldBuilder.build());

			//noinspection ReuseOfLocalVariable
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(FIELD_OPERATION_CATEGORY_ID);
			fieldBuilder.setDescription("Категория операции");
			fieldBuilder.setType(LongType.INSTANCE.getName());
			fieldBuilder.addValidators(requiredFieldValidator);
			fields.add(fieldBuilder.build());

			FormBuilder formBuilder = new FormBuilder();
			formBuilder.setFields(fields);

			return formBuilder.build();
		}
	}
}
