package com.rssl.phizic.business.forms;

import com.rssl.common.forms.FormValidatorBuilder;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.common.forms.validators.strategy.DocumentValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.receivers.generated.FormValidatorDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.generated.ParameterDescriptor;

import java.util.ArrayList;
import java.util.List;



/**
 * @author Egorova
 * @ created 09.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class PaymentReceiversExtendedFormValidatorsBuilder
{
	private List<FormValidatorDescriptor> formValidatorDescriptors;


/**
* Создать валидаторы формы для платежа
* @param formValidatorDescriptors дескрипторы дополнительных валидаторов форм платежа
 */
   public PaymentReceiversExtendedFormValidatorsBuilder(List<FormValidatorDescriptor> formValidatorDescriptors)
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
//			MultiFieldsValidator[] multiFieldsValidators = buildMultiFieldValidators(formValidatorDescriptor);

			try
			{

				String                validatorClassName  = formValidatorDescriptor.getClassName();
				FormValidatorBuilder  validatorBuilder = new FormValidatorBuilder(validatorClassName);

				validatorBuilder.setMessage(formValidatorDescriptor.getMessage());
	
				//noinspection unchecked
				List<ParameterDescriptor> parameterDescriptors = formValidatorDescriptor.getParameterDescriptors();
				for ( ParameterDescriptor parameterDescriptor : parameterDescriptors)
				{
				   validatorBuilder.setParameter(parameterDescriptor.getName(), parameterDescriptor.getValue());
				}

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
