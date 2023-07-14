package com.rssl.phizic.business.forms;

import com.rssl.common.forms.FormValidatorBuilder;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.common.forms.validators.strategy.DocumentValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.claims.generated.FormValidatorDescriptor;
import com.rssl.phizic.business.loans.claims.generated.ParameterDescriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krenev
 * @ created 02.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimExtendedFormValidatorsBuilder
{
	private List<FormValidatorDescriptor> formValidatorDescriptors;


	public LoanClaimExtendedFormValidatorsBuilder(List<FormValidatorDescriptor> formValidatorDescriptors)
	{
		this.formValidatorDescriptors = formValidatorDescriptors;
	}

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

				List<FormValidatorDescriptor.FieldRefDescriptor> fieldRefDescriptors = formValidatorDescriptor.getFieldRef();
				for (FormValidatorDescriptor.FieldRefDescriptor fieldRefDescriptor : fieldRefDescriptors)
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
        return formValidators;	}
}
