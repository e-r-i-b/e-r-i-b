package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * Экшен загрузки горизонтов инвестирования
 * @author koptyaev
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 */
public class InvestmentPeriodsAction extends DictionaryRecordsActionBase<InvestmentPeriod>
{
	public void execute(Element element) throws Exception
	{
		String key  = XmlHelper.getSimpleElementValue(element, "key");
		String period = XmlHelper.getSimpleElementValue(element, "period");

		InvestmentPeriod investmentPeriod = new InvestmentPeriod();
		investmentPeriod.setPeriod(period);

		addRecord(key, investmentPeriod);
	}
}
