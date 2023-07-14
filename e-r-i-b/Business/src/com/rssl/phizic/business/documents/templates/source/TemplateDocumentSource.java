package com.rssl.phizic.business.documents.templates.source;

import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

/**
 * @author mihaylov
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 */

public interface TemplateDocumentSource extends DocumentSource
{
	/**
	 * @return шаблон для редактирования
	 */
	TemplateDocument getTemplate();
}
