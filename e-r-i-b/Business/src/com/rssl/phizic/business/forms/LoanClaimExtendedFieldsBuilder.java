package com.rssl.phizic.business.forms;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FieldValidatorBuilder;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.ExpressionFactory;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.strategy.DocumentValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.claims.LoanClaimDefinitionProvider;
import com.rssl.phizic.business.loans.claims.generated.FieldDescriptor;
import com.rssl.phizic.business.loans.claims.generated.ParameterDescriptor;
import com.rssl.phizic.business.loans.claims.generated.ValidatorDescriptor;
import com.rssl.phizic.business.loans.products.LoanProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krenev
 * @ created 16.01.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimExtendedFieldsBuilder
{
	private final ExpressionFactory expressionFactory = new ExpressionFactory();
	private String kindId;

	/**
	 *  Создать доп. поля для заявки
	 * @param kindId описание заявки
	 */
	public LoanClaimExtendedFieldsBuilder(String kindId)
	{
		if (kindId == null)
			throw new IllegalArgumentException("Значение аргумента [kindId] не может быть null");
		this.kindId = kindId;
	}

	/**
	 * @return список доп полей для получателя
	 */
	public List<Field> build(LoanProduct loanProduct) throws BusinessException
	{
		LoanClaimDefinitionProvider definitionProvider = new LoanClaimDefinitionProvider();
		//noinspection unchecked
		return build(definitionProvider.getLoanDefinitionFields(kindId), loanProduct);
	}

	public List<Field> build(List<FieldDescriptor> entities, LoanProduct loanProduct) throws BusinessException
	{
		List<Field> fields = build(entities, "");
		for (int i = 0; i < loanProduct.getGuarantorsCount(); i++)
		{
			String prefix = String.format(LoanClaimMetadataLoader.GUARANTORS_PREFIX, i + 1);
			fields.addAll(build(entities, prefix));
		}
		return fields;
	}

	private List<Field> build(List<FieldDescriptor> entities, String prefix) throws BusinessException
	{
		List<Field> formFields = new ArrayList<Field>();
		for (FieldDescriptor descriptor : entities)
		{
			if (prefix.length()==0 || descriptor.isGuarantor()){
				formFields.add(buildField(descriptor, prefix));
			}
		}
		return formFields;
	}

	private Field buildField(FieldDescriptor fieldDescriptor, String prefix) throws BusinessException
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		String fieldName = fieldDescriptor.getName();

		fieldBuilder.setName(prefix + fieldName);
		String source = String.format(BusinessDocument.EXTENDED_FIELD_SOURCE_PATTERN, prefix + fieldDescriptor.getName());
		if ("date".equals(fieldDescriptor.getType())){
			source="concat(substring("+source+",9,2), '.', substring("+source+",6,2), '.' ,substring("+source+",1,4))";
		}
		fieldBuilder.setSource(source);
		fieldBuilder.setDescription(fieldDescriptor.getDescription());
		fieldBuilder.setType(fieldDescriptor.getType());
		String value = fieldDescriptor.getValue();
		if (value != null && value.length() > 0)
			fieldBuilder.setValueExpression(createExpression(value, prefix));
		String enabled = fieldDescriptor.getEnabled();
		if (enabled != null && enabled.length() > 0)
			fieldBuilder.setEnabledExpression(createExpression(enabled, prefix));

		String inital = fieldDescriptor.getInital();
		if (inital!= null && prefix.length()==0){
			fieldBuilder.setInitalValueExpression(expressionFactory.newExpression(inital));
		}

		String guarantorInitalDescriptor = fieldDescriptor.getGuarantorInital();
		if (guarantorInitalDescriptor!= null && prefix.length()!=0){
			fieldBuilder.setInitalValueExpression(expressionFactory.newExpression(guarantorInitalDescriptor));
		}

		List<FieldValidator> fieldValidators = buildFieldValidators(fieldDescriptor.getValidators());
		if (Boolean.parseBoolean(fieldDescriptor.getMandatory()))
		{
			fieldBuilder.addValidators(new RequiredFieldValidator());
		}
		fieldBuilder.addValidators(fieldValidators.toArray(new FieldValidator[fieldValidators.size()]));
		return fieldBuilder.build();
	}

	private Expression createExpression(String value, String prefix)
	{
		String expression = LoanClaimMetadataLoader.prepareScript(value, prefix);
		return expressionFactory.newExpression(expression);
	}

	private List<FieldValidator> buildFieldValidators(FieldDescriptor.ValidatorsDescriptor validators) throws BusinessException
	{
		if (validators == null)
		{
			return new ArrayList<FieldValidator>();
		}
		try
		{
			List<FieldValidator> fieldValidators = new ArrayList<FieldValidator>();

			for (Object object : validators.getValidator())
			{
				ValidatorDescriptor validatorDescriptor = (ValidatorDescriptor) object;
				String validatorClassName = validatorDescriptor.getClassName();
				FieldValidatorBuilder validatorBuilder = new FieldValidatorBuilder(validatorClassName);

				validatorBuilder.setMessage(validatorDescriptor.getMessage());
				//noinspection unchecked
				List<ParameterDescriptor> parameterDescriptors = validatorDescriptor.getParameter();

				for (ParameterDescriptor parameterDescriptor : parameterDescriptors)
				{
					validatorBuilder.setParameter(parameterDescriptor.getName(), parameterDescriptor.getValue());
				}
				if (!validatorDescriptor.isTemplate())
				{
					validatorBuilder.setMode(DocumentValidationStrategy.MODE);
				}
				fieldValidators.add(validatorBuilder.build());
			}

			return fieldValidators;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
