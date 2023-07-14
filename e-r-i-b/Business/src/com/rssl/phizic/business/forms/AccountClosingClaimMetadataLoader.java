package com.rssl.phizic.business.forms;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FormException;
import com.rssl.phizic.business.documents.metadata.DocumentMetadatalessExtendedMetadataLoaderBase;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.payments.forms.FormParser;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerException;

/**
 * @author Kidyaev
 * @ created 19.02.2007
 * @ $Author$
 * @ $Revision$
 */

public class AccountClosingClaimMetadataLoader extends DocumentMetadatalessExtendedMetadataLoaderBase
{
	private static final String DESTINATION_ACCOUNT_TYPE_FIELD_NAME = "destination-account-type";
	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws FormException
	{
		try
		{
			ExtendedMetadata extendedMetadata = new ExtendedMetadata();
			extendedMetadata.setOriginal(metadata);
			extendedMetadata.addAllDictionaries(metadata.getDictionaries());

			FormBuilder formBuilder = new FormBuilder();
			Form        form        = metadata.getForm();
			formBuilder.addFields(form.getFields());
			formBuilder.addFormValidators(form.getFormValidators());

			String accountType = fieldSource.getValue(DESTINATION_ACCOUNT_TYPE_FIELD_NAME);
			addPaymentFields(extendedMetadata, accountType, formBuilder);
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

	private void addPaymentFields(ExtendedMetadata extendedMetadata, String key, FormBuilder formBuilder) throws BusinessException
	{
		if ( key == null || key.equals("") )
			return;

		try
		{
			FormParser formParser         = new FormParser();
			Element    dictionary         = extendedMetadata.getDictionaries().get(key);
			Element    paymentFormElement = XmlHelper.selectSingleNode(dictionary, "payment-form");

			if ( paymentFormElement == null )
				throw new BusinessException("не найден элемент <payment-form>");

			formParser.parse(paymentFormElement);

			Form paymentForm = formParser.getForm();

			formBuilder.addFields(paymentForm.getFields());
			formBuilder.addFormValidators(paymentForm.getFormValidators());
		}
		catch (TransformerException e)
		{
			throw  new BusinessException(e);
		}
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		String accountType = fieldSource.getValue(DESTINATION_ACCOUNT_TYPE_FIELD_NAME);
		if( accountType == null )
			throw new FormException("Не задан ["+DESTINATION_ACCOUNT_TYPE_FIELD_NAME+"]");

		return accountType;
	}
}
