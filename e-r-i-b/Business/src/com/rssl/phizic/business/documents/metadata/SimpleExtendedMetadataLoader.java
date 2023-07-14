package com.rssl.phizic.business.documents.metadata;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.CommissionsListHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.payments.forms.meta.receivers.ExtendedMetadataLoaderBase;
import com.rssl.phizic.business.payments.forms.meta.receivers.TemplateFieldBuilder;
import com.rssl.phizic.common.types.documents.FormType;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 31.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class SimpleExtendedMetadataLoader extends ExtendedMetadataLoaderBase
{
	public Metadata load(Metadata metadata, FieldValuesSource fieldSource)
	{
		return metadata;
	}

	public Metadata load(Metadata metadata, BusinessDocument document) throws BusinessException
	{
		try
		{
			if (document instanceof AbstractPaymentDocument)
			{
				Map<String, Element> dictionaries = new HashMap<String, Element>();

				Element dictionary = CommissionsListHelper.getCommissionDictionary(((AbstractPaymentDocument) document));
				dictionaries.put("writeDownOperations.xml", dictionary);
				return load(metadata, metadata.getForm().getFields(), dictionaries, metadata.getForm().getDescription());
			}
			return metadata;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Metadata load(Metadata metadata, TemplateDocument template) throws BusinessException
	{
		try
		{
			if (FormType.isExternalDocument(template.getFormType()))
			{
				Map<String, Element> dictionaries = new HashMap<String, Element>();

				Element dictionary = CommissionsListHelper.getEmptyCommissionDictionary();
				dictionaries.put("writeDownOperations.xml", dictionary);
				return load(metadata, metadata.getForm().getFields(), dictionaries, metadata.getForm().getDescription());
			}
			return metadata;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Metadata load(Metadata metadata, BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		try
		{
			Map<String, Element> dictionaries = new HashMap<String, Element>();

			if (document instanceof AbstractPaymentDocument)
			{
				Element dictionary = CommissionsListHelper.getCommissionDictionary(((AbstractPaymentDocument) document));
				dictionaries.put("writeDownOperations.xml", dictionary);
			}
			return load(metadata, new TemplateFieldBuilder(metadata, template).buildFields(), dictionaries, metadata.getForm().getDescription());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource)
	{
		return null;
	}
}
