package com.rssl.phizic.business.documents.forms;

import javax.xml.transform.Source;

/**
 * @author khudyakov
 * @ created 12.02.14
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentFormBuilder
{
	/**
	 * @param source ресурс
	 * @return построить форму документа
	 */
	String build(Source source);
}
