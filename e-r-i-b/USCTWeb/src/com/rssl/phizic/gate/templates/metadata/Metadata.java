package com.rssl.phizic.gate.templates.metadata;

import com.rssl.phizic.gate.templates.impl.TemplateDocument;

/**
 * Метаданные шаблона документа
 *
 * @author khudyakov
 * @ created 30.04.14
 * @ $Author$
 * @ $Revision$
 */
public interface Metadata
{
	/**
	 * @return наименование
	 */
	String getName();

	/**
	 * Создать шаблон документа
	 * @return шабдлон документа
	 */
	TemplateDocument createTemplate() throws Exception;
}
