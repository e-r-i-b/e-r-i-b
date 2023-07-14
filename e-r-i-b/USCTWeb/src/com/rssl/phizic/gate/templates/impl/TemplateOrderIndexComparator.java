package com.rssl.phizic.gate.templates.impl;

import com.rssl.phizic.common.AbstractCompatator;

/**
 * Компаратор шаблонов документов по полю TEMPLATE_ORDER_IND
 *
 * @author khudyakov
 * @ created 06.08.14
 * @ $Author$
 * @ $Revision$
 */
public class TemplateOrderIndexComparator extends AbstractCompatator<TemplateDocument>
{
	public int compare(TemplateDocument o1, TemplateDocument o2)
	{
		int index1 = o1.getTemplateInfo().getOrderInd();
		int index2 = o2.getTemplateInfo().getOrderInd();

		if (index1 > index2)
		{
			return -1;
		}

		if (index1 < index2)
		{
			return 1;
		}

		return 0;
	}
}
