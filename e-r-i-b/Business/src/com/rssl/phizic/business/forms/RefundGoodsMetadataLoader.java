package com.rssl.phizic.business.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.DocumentMetadatalessExtendedMetadataLoaderBase;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.*;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Загрузчик расширенных данных заявки возврата товара
 * @author gladishev
 * @ created 15.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class RefundGoodsMetadataLoader extends DocumentMetadatalessExtendedMetadataLoaderBase
{
	private static final String RETURNED_GOODS_DICTIONARY_NAME = "returnedGoodsDictionary.xml";

	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws BusinessException, BusinessLogicException
	{
		ExtendedMetadata newMetadata = new ExtendedMetadata();
		newMetadata.setOriginal(metadata);
		newMetadata.addAllDictionaries(metadata.getDictionaries());
		newMetadata.addDictionary(RETURNED_GOODS_DICTIONARY_NAME, buildDictionaryFromXmlField(fieldSource.getValue("returnedGoods")));

		FormBuilder formBuilder = new FormBuilder();
		Form form = metadata.getForm();
		formBuilder.addFields(form.getFields());
		formBuilder.addFormValidators(form.getFormValidators());
		formBuilder.setName(form.getName());
		formBuilder.setDetailedDescription(form.getDetailedDescription());
		formBuilder.setDescription(form.getDescription());
		formBuilder.setConfirmDescription(form.getConfirmDescription());

		newMetadata.setExtendedForm(formBuilder.build());

		return newMetadata;
	}

	private Element buildDictionaryFromXmlField(String xmlField) throws BusinessException
	{
		try
		{
			Document document = XmlHelper.parse(xmlField);
			return document.getDocumentElement();
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return null;
	}
}
