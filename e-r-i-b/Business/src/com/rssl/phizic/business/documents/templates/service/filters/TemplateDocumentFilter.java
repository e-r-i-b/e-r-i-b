package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;

/**
 * Фильтр шаблонов документов
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateDocumentFilter
{
	/**
	 * Доступен ли шаблон
	 * @param template шаблон
	 * @return true - доступен
	 */
	boolean accept(TemplateDocument template);
}
