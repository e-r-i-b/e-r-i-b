package com.rssl.phizic.business.forms;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FieldValidatorBuilder;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.strategy.DocumentValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.FieldDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.ParameterDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.ValidatorDescriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Egorova
 * @ created 21.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class PersonalReceiversFieldsBuilder
{
	private List<FieldDescriptor> fieldDescriptors;

	/**
	 * Создать поля для формы получателя
	 * @param fieldDescriptors дескрипторы полей получателя
	 */
    public PersonalReceiversFieldsBuilder(List<FieldDescriptor> fieldDescriptors)
    {
	    if ( fieldDescriptors == null )
	        throw new IllegalArgumentException("Значение аргумента [fieldDescriptors] не может быть null");

	    //noinspection AssignmentToCollectionOrArrayFieldFromParameter
	    this.fieldDescriptors = fieldDescriptors;
    }

	/**
	 * @return список полей для получателя
	 */
    public List<Field> build() throws BusinessException
	{

        List<Field> formFields = new ArrayList<Field>();

		for ( FieldDescriptor fieldDescriptor : fieldDescriptors )
		{
			FieldBuilder fieldBuilder = new FieldBuilder();
			String       fieldName    = fieldDescriptor.getName();

			fieldBuilder.setName(fieldName);
			fieldBuilder.setDescription(fieldDescriptor.getDescription());

			if (fieldDescriptor.getType()!=null)
				fieldBuilder.setType(fieldDescriptor.getType());

			FieldValidator[] fieldValidators = buildFieldValidators(fieldDescriptor);
			fieldBuilder.addValidators(fieldValidators);

			formFields.add( fieldBuilder.build() );
		}

		//Стандартное поле для ВСЕХ получателей. 
        return formFields;
    }

	private FieldValidator[] buildFieldValidators(FieldDescriptor fieldDescriptor) throws BusinessException
    {
	   	try
		{
			List<FieldValidator> fieldValidators = new ArrayList<FieldValidator>();
			if(fieldDescriptor.isRequired())
			{
				fieldValidators.add(new RequiredFieldValidator());
			}
			if(fieldDescriptor.getMaxlength()!=null || fieldDescriptor.getMinlength() != null)
			{
				LengthFieldValidator lengthValidator = new LengthFieldValidator();
				if(fieldDescriptor.getMaxlength()!=null) lengthValidator.setParameter("maxlength",fieldDescriptor.getMaxlength());
				if(fieldDescriptor.getMinlength()!=null) lengthValidator.setParameter("minlength",fieldDescriptor.getMinlength());
				fieldValidators.add(lengthValidator);
			}
			if (fieldDescriptor.getFormat()!=null)
			{
				RegexpFieldValidator regexpValidator = new RegexpFieldValidator();
				regexpValidator.setParameter("regexp",fieldDescriptor.getFormat().getRegexp());
				regexpValidator.setMessage(fieldDescriptor.getFormat().getMessage());
				fieldValidators.add(regexpValidator);
			}
			FieldDescriptor.ValidatorsType validators = fieldDescriptor.getValidators();
			if (validators != null)
			{
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
