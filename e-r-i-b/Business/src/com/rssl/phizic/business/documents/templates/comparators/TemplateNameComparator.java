package com.rssl.phizic.business.documents.templates.comparators;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.AbstractCompatator;


/**
 * Компаратор шаблонов документов по названию шаблона
 *
 * @author khudyakov
 * @ created 09.07.14
 * @ $Author$
 * @ $Revision$
 */
public class TemplateNameComparator extends AbstractCompatator<TemplateDocument>
{
	public int compare(TemplateDocument ts, TemplateDocument that)
	{
		if (!isObjectsEquals(ts.getTemplateInfo().getName(), that.getTemplateInfo().getName()))
		{
			return -1;
		}
		return 0;
	}
}
