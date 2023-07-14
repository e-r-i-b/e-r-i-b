package com.rssl.phizic.business.forms;

import com.rssl.common.forms.FormException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.metadata.DocumentMetadatalessExtendedMetadataLoaderBase;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.utils.BeanHelper;
import org.w3c.dom.Element;

import java.util.Map;

/**
 * @author Krenev
 * @ created 13.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class RecallPaymentMetadataLoader  extends DocumentMetadatalessExtendedMetadataLoaderBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final String RECALLED_DOCUMENT_FORM_FIELD_NAME = "recalled-document-form-name";
	private static final String PARENT_DOCUMENT_ID_FIELD_NAME = "parentId";
	/**
	 * Загружает расширенные данные о форме
	 * @param metadata
	 * @param fieldSource @return расширенные данные
	 */
	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws FormException
	{
		String formNameValue = fieldSource.getValue(RECALLED_DOCUMENT_FORM_FIELD_NAME);
		if (formNameValue == null)
			formNameValue = getParentDocumentFormName(fieldSource);

		MetadataImpl newMetadata = new MetadataImpl();
		BeanHelper.copyProperties(newMetadata, metadata);

		newMetadata.setListFormName(formNameValue);

		newMetadata.addTemplate("html", metadata.getTemplates("html"));
		newMetadata.addTemplate("print", metadata.getTemplates("print"));
		newMetadata.addTemplate("xml", metadata.getTemplates("xml"));
		for (Map.Entry<String, Element> entry : metadata.getDictionaries().entrySet())
		{
			newMetadata.addDictionary(entry.getKey(), entry.getValue());
		}
		return newMetadata;
	}

	/**
	 * Ключ идентифицирующий метаданные для этого документа. Если ключ одинаковый то и
	 * loadExtendedFields должна возвращать одинаковые данные.
	 * @param fieldSource
	 * @return ключ
	 */
	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		String formNameValue = fieldSource.getValue(RECALLED_DOCUMENT_FORM_FIELD_NAME);
		if (formNameValue == null)
			return getParentDocumentFormName(fieldSource);
		return formNameValue;
	}

	private String getParentDocumentFormName(FieldValuesSource fieldSource) throws FormException
	{
		String parentId = fieldSource.getValue(PARENT_DOCUMENT_ID_FIELD_NAME);
		if (parentId == null){
		    throw new FormException("Не задан ["+PARENT_DOCUMENT_ID_FIELD_NAME+"]");
		}
		try
		{
			BusinessDocument document = businessDocumentService.findById(Long.parseLong(parentId));
			return document.getFormName();
		}
		catch (BusinessException e)
		{
			throw new FormException("Не найден платеж id=" + parentId, e);
		}
	}
}
