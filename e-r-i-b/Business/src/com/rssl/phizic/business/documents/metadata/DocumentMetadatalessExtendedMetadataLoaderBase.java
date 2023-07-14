package com.rssl.phizic.business.documents.metadata;

import com.rssl.common.forms.FormException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

/**
 * @author krenev
 * @ created 20.12.2010
 * @ $Author$
 * @ $Revision$
 * Базовый класс для загрузчиков расширенных метаданных документов, не содержащих в себе мету в составе данных
 * инкапсулирует логику загрузки меты по документу
 * метаданные для существующего документа грузятся через источник данных полей сущестующего документа
 */
public abstract class DocumentMetadatalessExtendedMetadataLoaderBase implements ExtendedMetadataLoader
{
	public Metadata load(Metadata metadata, BusinessDocument document) throws FormException, BusinessException, BusinessLogicException
	{
		return load(metadata, new DocumentFieldValuesSource(metadata,document));
	}

	public Metadata load(Metadata metadata, TemplateDocument template)
	{
		return metadata;
	}

	public Metadata load(Metadata metadata, BusinessDocument document, TemplateDocument template)
	{
		return metadata;
	}
}
