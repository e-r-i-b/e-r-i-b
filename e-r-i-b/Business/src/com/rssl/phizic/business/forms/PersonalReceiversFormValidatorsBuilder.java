package com.rssl.phizic.business.forms;

import com.rssl.common.forms.FormValidatorBuilder;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.common.forms.validators.strategy.DocumentValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.FormValidatorDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.ParameterDescriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Egorova
 * @ created 22.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class PersonalReceiversFormValidatorsBuilder
{
	private List<FormValidatorDescriptor> formValidatorDescriptors;


/**
* Создать валидаторы формы для платежа
* @param formValidatorDescriptors дескрипторы дополнительных валидаторов форм платежа
 */
   public PersonalReceiversFormValidatorsBuilder(List<FormValidatorDescriptor> formValidatorDescriptors)
    {
	    this.formValidatorDescriptors = formValidatorDescriptors;
    }

/**
 * @return список доп валидаторов для получателя
*/
    public List<MultiFieldsValidator> build() throws BusinessException
{
		List<MultiFieldsValidator> formValidators = new ArrayList<MultiFieldsValidator>();

		for ( FormValidatorDescriptor formValidatorDescriptor : formValidatorDescriptors )
		{
			try
			{

				String                validatorClassName  = formValidatorDescriptor.getClassName();
				FormValidatorBuilder validatorBuilder = new FormValidatorBuilder(validatorClassName);

				validatorBuilder.setMessage(formValidatorDescriptor.getMessage());

				//noinspection unchecked
				List<ParameterDescriptor> parameterDescriptors = formValidatorDescriptor.getParameterDescriptors();
				for ( ParameterDescriptor parameterDescriptor : parameterDescriptors)
				{
				   validatorBuilder.setParameter(parameterDescriptor.getName(), parameterDescriptor.getValue());
				}
				//noinspection unchecked
				List<FormValidatorDescriptor.FieldRefType> fieldRefDescriptors = formValidatorDescriptor.getFieldRef();
				for (FormValidatorDescriptor.FieldRefType fieldRefDescriptor : fieldRefDescriptors)
				{
				   validatorBuilder.setBinding(fieldRefDescriptor.getName(),fieldRefDescriptor.getContent().get(0).toString());
				}

				if (!formValidatorDescriptor.isTemplate())
				{
					validatorBuilder.setMode(DocumentValidationStrategy.MODE);
				}
				formValidators.add(validatorBuilder.build());
			}
			catch (Exception e)
			{
				throw new BusinessException(e);
			}
		}
        return formValidators;
	}
}
