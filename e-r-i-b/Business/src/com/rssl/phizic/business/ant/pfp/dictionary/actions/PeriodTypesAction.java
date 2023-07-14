package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.PeriodType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author akrenev
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 * экшен для парсинга типа периода из xml
 */
public class PeriodTypesAction extends DictionaryRecordsActionBase<PeriodType>
{
	public void execute(Element element) throws BusinessException, BusinessLogicException
	{
		String key  = XmlHelper.getSimpleElementValue(element, "key");
		String name = XmlHelper.getSimpleElementValue(element, "name");
		String months = XmlHelper.getSimpleElementValue(element, "months");

		PeriodType periodType = new PeriodType();
		periodType.setName(name);
		periodType.setMonths(getLongValue(months));
		addRecord(key, periodType);
	}
}
