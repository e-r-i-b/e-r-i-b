package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.*;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.forms.ExtendedMetadata;
import com.rssl.phizic.business.payments.forms.meta.receivers.ExtendedMetadataLoaderBase;

import java.util.List;

/**
 * Загрузчик доп.полей платежа погашения кредита
 * (загружает поля разбивки платежа)
 * @author gladishev
 * @ created 07.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanPaymentMetadataLoader extends ExtendedMetadataLoaderBase
{
	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws FormException, BusinessException
	{
		ExtendedMetadata newMetadata = new ExtendedMetadata();
		newMetadata.setOriginal(metadata);
		Form form = metadata.getForm();

		newMetadata.addAllDictionaries(metadata.getDictionaries());
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setName(form.getName());
		formBuilder.setDetailedDescription(form.getDetailedDescription());
		formBuilder.setDescription(form.getDescription());
		formBuilder.addFields(metadata.getForm().getFields());
		
		LoanPaymentExtendedFieldsBuilder fieldsBuilder = new LoanPaymentExtendedFieldsBuilder();

		List<Field> fieldList = fieldsBuilder.buildFields();
		formBuilder.addFields(fieldList);

		formBuilder.addFormValidators(form.getFormValidators());
		newMetadata.setExtendedForm(formBuilder.build());
		return newMetadata;
	}

	public Metadata load(Metadata metadata, BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		return load(metadata, new DocumentFieldValuesSource(metadata,document));
	}

	public Metadata load(Metadata metadata, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		return metadata;
	}

	public Metadata load(Metadata metadata, BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		//TODO уйти от двойственности при создании/редактировании шаблона/документа по шаблону (запрос)
		if (document == null)
		{
			return load(metadata, template);
		}

		return load(metadata, new DocumentFieldValuesSource(metadata, document));
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return "loan-payment-extended-fields";
	}
}
