package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.AgeCategory;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author akrenev
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * экшен для парсинга возрастной категории из xml
 */

public class AgeCategoriesAction extends DictionaryRecordsActionBase<AgeCategory>
{
	public void execute(Element element) throws BusinessException, BusinessLogicException
	{
		String minAge = XmlHelper.getSimpleElementValue(element, "minAge");
		String maxAge = XmlHelper.getSimpleElementValue(element, "maxAge");
		String weight = XmlHelper.getSimpleElementValue(element, "weight");

		AgeCategory product = new AgeCategory();
		product.setMinAge(getLongValue(minAge));
		product.setMaxAge(getLongValue(maxAge));
		product.setWeight(getLongValue(weight));
		addRecord(minAge + "-" + maxAge, product);
	}
}
