package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceCompany;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author akrenev
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 * экшен для парсинга страховой компании из xml
 */
public class InsuranceCompaniesAction extends DictionaryRecordsActionBase<InsuranceCompany>
{
	public void execute(Element element) throws BusinessException, BusinessLogicException
	{
		String key  = XmlHelper.getSimpleElementValue(element, "key");
		String name = XmlHelper.getSimpleElementValue(element, "name");
		String imageUrl = XmlHelper.getSimpleElementValue(element, "imageUrl");

		InsuranceCompany company = new InsuranceCompany();
		company.setName(name);
		addRecord(key, company, getImageValue(imageUrl));
	}
}
