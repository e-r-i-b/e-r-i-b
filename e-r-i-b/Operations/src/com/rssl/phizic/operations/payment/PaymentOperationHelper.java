package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.ExtendedMetadataLoader;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.metadata.source.TemplateFieldValueSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

public class PaymentOperationHelper
{
	/**
	 *
	 * @param metadata метаданны≈
	 * @param document документ
	 * @return строка идентифицирующа€ метаданные
	 */
	public static String calculateMetadataPath(Metadata metadata, BusinessDocument document) throws BusinessException
	{
		ExtendedMetadataLoader metadataLoader = metadata.getExtendedMetadataLoader();
		String path = metadata.getName();
		if(metadataLoader != null)
		{
			path += "/" + metadataLoader.getExtendedMetadataKey(new DocumentFieldValuesSource(metadata,document));
		}
		return path;
	}

	/**
	 * @param metadata метаданны≈
	 * @param template шаблон
	 * @return строка идентифицирующа€ метаданные
	 */
	public static String calculateMetadataPath(Metadata metadata, TemplateDocument template) throws BusinessException
	{
		ExtendedMetadataLoader metadataLoader = metadata.getExtendedMetadataLoader();
		String path = metadata.getName();
		if(metadataLoader != null)
		{
			path += "/" + metadataLoader.getExtendedMetadataKey(new TemplateFieldValueSource(template));
		}
		return path;
	}

}
