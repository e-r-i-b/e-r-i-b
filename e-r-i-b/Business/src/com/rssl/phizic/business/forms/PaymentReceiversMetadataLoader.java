package com.rssl.phizic.business.forms;

import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FormException;
import com.rssl.phizic.business.documents.metadata.DocumentMetadatalessExtendedMetadataLoaderBase;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.dictionaries.receivers.PaymentReceiversDictionary;
import com.rssl.phizic.business.dictionaries.receivers.generated.*;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import javax.xml.parsers.DocumentBuilder;

/**
 * @author Evgrafov
 * @ created 22.11.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 2892 $
 */

public class PaymentReceiversMetadataLoader  extends DocumentMetadatalessExtendedMetadataLoaderBase
{
	private static final String APPOINTMENT_FIELD_NAME = "appointment";
	private static final String RECEIVER_KEY_FIELD_NAME = "receiverKey";

	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws FormException
	{
		try
		{
			String  appointment = fieldSource.getValue(APPOINTMENT_FIELD_NAME);
			String  receiverKey = fieldSource.getValue(RECEIVER_KEY_FIELD_NAME);

			PaymentReceiversDictionary dictionary         = new PaymentReceiversDictionary();
			ReceiverDescriptor         receiverDescriptor = dictionary.getReceiverDescriptor(appointment, receiverKey);

			if ( receiverDescriptor == null )
				throw new FormException("Не найденo описание получателя "+receiverKey);

			//noinspection unchecked
			List<FieldDescriptor> fieldDescriptors = receiverDescriptor.getFieldDescriptors();
			PaymentReceiversExtendedFieldsBuilder builder = new PaymentReceiversExtendedFieldsBuilder(fieldDescriptors);

			ExtendedMetadata extendedMetadata = new ExtendedMetadata();
			extendedMetadata.setOriginal(metadata);
			extendedMetadata.addAllDictionaries(metadata.getDictionaries());
			extendedMetadata.addDictionary("extendedFields.xml", createXmlExtendedFields(fieldDescriptors));

			FormBuilder formBuilder = new FormBuilder();
			formBuilder.addFields(metadata.getForm().getFields());
			formBuilder.addFields(builder.build());
			formBuilder.addFormValidators(metadata.getForm().getFormValidators());

			formBuilder.setName(appointment);
			PaymentDescriptor paymentDescriptions = dictionary.getPaymentDescription(appointment);
			formBuilder.setDescription(paymentDescriptions.getDescription());
			formBuilder.setDetailedDescription(paymentDescriptions.getDetailedDescription());

			if (receiverDescriptor.getFormValidators() != null)
			{
				List<FormValidatorDescriptor> formValidatorDescriptors = receiverDescriptor.getFormValidators().getFormValidator();
				PaymentReceiversExtendedFormValidatorsBuilder validatorsBuilder = new  PaymentReceiversExtendedFormValidatorsBuilder(formValidatorDescriptors);
				formBuilder.addFormValidators(validatorsBuilder.build());
			}

			extendedMetadata.setExtendedForm(formBuilder.build());

			return extendedMetadata;
		}
		catch(FormException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new FormException(e);
		}
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		String  appointment = fieldSource.getValue(APPOINTMENT_FIELD_NAME);
		String  receiver = fieldSource.getValue(RECEIVER_KEY_FIELD_NAME);

		if( appointment == null )
			throw new FormException("Не задан ["+APPOINTMENT_FIELD_NAME+"]");
		if( receiver == null )
			throw new FormException("Не задан ["+RECEIVER_KEY_FIELD_NAME+"]");

		return appointment + "\t" + receiver;
	}

	private Element createXmlExtendedFields(List<FieldDescriptor> fieldDescriptors)
	{
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document        document        = documentBuilder.newDocument();
		Element         root            = document.createElement("fields");

		document.appendChild(root);

		for (FieldDescriptor fieldDescriptor : fieldDescriptors )
		{
			Element fieldElement = XmlHelper.appendSimpleElement(root, "field");

			fieldElement.setAttribute("name", fieldDescriptor.getName());
			fieldElement.setAttribute("description", fieldDescriptor.getDescription());
			fieldElement.setAttribute("comment", getCommentField(fieldDescriptor));
			fieldElement.setAttribute("length", fieldDescriptor.getLength());
			fieldElement.setAttribute("mandatory", fieldDescriptor.getMandatory());
		}

		return document.getDocumentElement();
	}

    private String getCommentField(FieldDescriptor fieldDescriptor)
	{
		if (fieldDescriptor != null)
		{
			List<ValidatorDescriptor> validatorDescriptors = fieldDescriptor.getValidators().getValidatorDescriptor();
			for (ValidatorDescriptor validatorDescriptor : validatorDescriptors )
			    if (validatorDescriptor.getClassName().equals("com.rssl.common.forms.validators.RegexpFieldValidator"))
				    return validatorDescriptor.getMessage();
		}

		return "";
	}
}
