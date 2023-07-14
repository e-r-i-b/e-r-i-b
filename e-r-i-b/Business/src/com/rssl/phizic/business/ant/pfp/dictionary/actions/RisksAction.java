package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * Ёкшен загрузки рисков
 * @author koptyaev
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 */
public class RisksAction extends DictionaryRecordsActionBase<Risk>
{
	public void execute(Element element) throws Exception
	{
		String key  = XmlHelper.getSimpleElementValue(element, "key");
		String name = XmlHelper.getSimpleElementValue(element, "riskname");

		Risk risk = new Risk();
		risk.setName(name);

		addRecord(key, risk);
	}
}
