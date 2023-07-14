package com.rssl.phizic.business.documents.payments.source;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.metadata.Metadata;

/**
 * @author Evgrafov
 * @ created 13.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 49377 $
 */

public interface DocumentSource
{
	/**
	 * @return документ для редактирования
	 */
	BusinessDocument getDocument();

	/**
	 * @return имя формы документа
	 */
	Metadata getMetadata();
}
