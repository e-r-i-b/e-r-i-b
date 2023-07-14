package com.rssl.phizic.gate.templates.metadata;

import com.rssl.phizic.gate.templates.impl.*;
import com.rssl.phizic.utils.ClassHelper;

/**
 * Метаданные шаблона документа.
 *
 * @author khudyakov
 * @ created 30.04.14
 * @ $Author$
 * @ $Revision$
 */
public class MetadataImpl implements Metadata
{
	private String name;
	private Class<? extends TemplateDocument> templateClass;


	public MetadataImpl(String name, Class<? extends TemplateDocument>  templateClass)
	{
		this.name = name;
		this.templateClass = templateClass;
	}

	public String getName()
	{
		return name;
	}

	public Class<? extends TemplateDocument> getTemplateClass()
	{
		return templateClass;
	}

	public TemplateDocument createTemplate() throws Exception
	{
		return ClassHelper.newInstance(getTemplateClass());
	}
}
