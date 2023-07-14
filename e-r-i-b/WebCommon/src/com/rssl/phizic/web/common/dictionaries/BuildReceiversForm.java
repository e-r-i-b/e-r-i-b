package com.rssl.phizic.web.common.dictionaries;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.dictionaries.receivers.personal.PaymentPersonalReceiversDictionary;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiverDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.FieldDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.FormValidatorDescriptor;
import com.rssl.phizic.business.forms.PersonalReceiversFieldsBuilder;
import com.rssl.phizic.business.forms.PersonalReceiversFormValidatorsBuilder;
import com.rssl.phizic.business.BusinessException;

import java.util.List;

/**
 * @author Egorova
 * @ created 20.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class BuildReceiversForm
{
	protected static Form createForm(String kind)
	{
		try
		{
			PaymentPersonalReceiversDictionary dictionary = new PaymentPersonalReceiversDictionary();
			ReceiverDescriptor receiverDescriptor = dictionary.getReceiverDescriptor(kind);

				if ( receiverDescriptor == null )
					throw new FormException("Не найден получатель платежа в receivers.xml [kind = "+kind+"].");

			//noinspection unchecked
			List<FieldDescriptor> fieldDescriptors = receiverDescriptor.getFieldDescriptors();
			PersonalReceiversFieldsBuilder builder = new PersonalReceiversFieldsBuilder(fieldDescriptors);

			FormBuilder formBuilder = new FormBuilder();

			formBuilder.addFields(builder.build());
			if (receiverDescriptor.getFormValidators() != null)
			{
				//noinspection unchecked
				List<FormValidatorDescriptor> formValidatorDescriptors = receiverDescriptor.getFormValidators().getFormValidator();
				PersonalReceiversFormValidatorsBuilder validatorsBuilder = new PersonalReceiversFormValidatorsBuilder(formValidatorDescriptors);
				formBuilder.addFormValidators(validatorsBuilder.build());
			}
			return formBuilder.build();
		}
		catch(BusinessException e)
		{
			throw new FormException("Не построились поля из файла."+e);
		}
	}
}
