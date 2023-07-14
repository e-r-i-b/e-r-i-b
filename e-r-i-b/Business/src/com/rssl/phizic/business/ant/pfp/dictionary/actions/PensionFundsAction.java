package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund.PensionFund;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * Ёкшен загрузки пенсионных фондов
 * @author koptyaev
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 */
public class PensionFundsAction extends DictionaryRecordsActionBase<PensionFund>
{
	public void execute(Element element) throws Exception
	{
		String key = XmlHelper.getSimpleElementValue(element,"key");
		String name = XmlHelper.getSimpleElementValue(element,"name");
		String imageUrl = XmlHelper.getSimpleElementValue(element,"imageUrl");

		PensionFund fund = new PensionFund();
		fund.setName(name);
		addRecord(key,fund,getImageValue(imageUrl));
	}
}
