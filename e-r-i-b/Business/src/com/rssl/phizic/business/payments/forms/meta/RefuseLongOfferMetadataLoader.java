package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FormException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.metadata.DocumentMetadatalessExtendedMetadataLoaderBase;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.forms.ExtendedMetadata;

/**
 * @author osminin
 * @ created 14.12.2010
 * @ $Author$
 * @ $Revision$
 *
 * Загрузчик дополнительных полей платежа "Отмена регулярного платежа"
 */
public class RefuseLongOfferMetadataLoader extends DocumentMetadatalessExtendedMetadataLoaderBase
{
	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws FormException, BusinessException
	{
		ExtendedMetadata extendedMetadata = new ExtendedMetadata();
		extendedMetadata.setOriginal(metadata);

		Form form = metadata.getForm();

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addFields(metadata.getForm().getFields());
		formBuilder.setName(form.getName());
		formBuilder.setDetailedDescription(form.getDetailedDescription());
		formBuilder.setDescription(form.getDescription());
		formBuilder.setFormValidators(form.getFormValidators());

		extendedMetadata.addAllDictionaries(metadata.getDictionaries());
		extendedMetadata.setExtendedForm(formBuilder.build());
		return extendedMetadata;
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return "refuse-long-offer-extended-fields";
	}
}
