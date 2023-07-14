package com.rssl.phizic.business.forms;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FieldValidatorBuilder;
import com.rssl.common.forms.expressions.ExpressionFactory;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.strategy.DocumentValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.receivers.generated.FieldDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.generated.ParameterDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.generated.ValidatorDescriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kidyaev
 * @ created 22.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class PaymentReceiversExtendedFieldsBuilder
{
	private static final ExpressionFactory expressionFactory = new ExpressionFactory();
	private List<FieldDescriptor> fieldDescriptors;


	/**
	 * —оздать доп. пол€ дл€ платежа
	 * @param fieldDescriptors дескрипторы дополнительных полей платежа
	 */
    public PaymentReceiversExtendedFieldsBuilder(List<FieldDescriptor> fieldDescriptors)
    {
	    if ( fieldDescriptors == null )
	        throw new IllegalArgumentException("«начение аргумента [fieldDescriptors] не может быть null");

	    //noinspection AssignmentToCollectionOrArrayFieldFromParameter
	    this.fieldDescriptors = fieldDescriptors;
    }

	/**
	 * @return список доп полей дл€ получател€
	 */
    public List<Field> build() throws BusinessException
	{
        List<Field> formFields = new ArrayList<Field>();

		for ( FieldDescriptor fieldDescriptor : fieldDescriptors )
		{
			FieldBuilder fieldBuilder = new FieldBuilder();
			String       fieldName    = fieldDescriptor.getName();

			fieldBuilder.setName(fieldName);
			fieldBuilder.setSource(fieldDescriptor.getSource());
			fieldBuilder.setDescription(fieldDescriptor.getDescription());
			fieldBuilder.setType(fieldDescriptor.getType());
			String inital = fieldDescriptor.getInital();
			if (inital !=null){
				fieldBuilder.setInitalValueExpression(expressionFactory.newExpression(inital));
			}

			FieldValidator[] fieldValidators = buildFieldValidators(fieldDescriptor.getValidators());
			fieldBuilder.setValidators(fieldValidators);

			formFields.add( fieldBuilder.build() );
		}

        return formFields;
    }

	private FieldValidator[] buildFieldValidators(FieldDescriptor.ValidatorsType validators) throws BusinessException
    {
	    try
	    {
		    List<FieldValidator> fieldValidators = new ArrayList<FieldValidator>();

		    for ( Object object : validators.getValidatorDescriptor() )
		    {
			    ValidatorDescriptor   validatorDescriptor = (ValidatorDescriptor) object;
			    String                validatorClassName  = validatorDescriptor.getClassName();
			    FieldValidatorBuilder validatorBuilder    = new FieldValidatorBuilder(validatorClassName);

			    validatorBuilder.setMessage(validatorDescriptor.getMessage());
			    //noinspection unchecked
			    List<ParameterDescriptor> parameterDescriptors = validatorDescriptor.getParameterDescriptors();

			    for ( ParameterDescriptor parameterDescriptor : parameterDescriptors)
			    {
				    validatorBuilder.setParameter(parameterDescriptor.getName(), parameterDescriptor.getValue());
			    }
			    if (!validatorDescriptor.isTemplate())
			    {
				    validatorBuilder.setMode(DocumentValidationStrategy.MODE);
			    }
			    fieldValidators.add(validatorBuilder.build());
		    }

		    FieldValidator[] result = new FieldValidator[ fieldValidators.size() ];
            fieldValidators.toArray(result);

		    return result;
	    }
	    catch (Exception e)
	    {
		    throw new BusinessException(e);
	    }
    }
}
