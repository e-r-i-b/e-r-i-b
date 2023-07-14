package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ant.pfp.dictionary.PFPDictionaryRecordWrapper;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author akrenev
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 * экшен для парсинга типа страхового продукта из xml
 */
public class InsuranceTypesAction extends DictionaryRecordsActionBase<InsuranceType>
{
	public void execute(Element element) throws BusinessException, BusinessLogicException
	{
		String key  = XmlHelper.getSimpleElementValue(element, "key");
		String name = XmlHelper.getSimpleElementValue(element, "name");
		String description = XmlHelper.getSimpleElementValue(element, "description");
		String parentKey = XmlHelper.getSimpleElementValue(element, "parentKey");
		String imageUrl = XmlHelper.getSimpleElementValue(element, "imageUrl");

		//создание сущности
		InsuranceType type = new InsuranceType();
		type.setName(name);
		type.setDescription(description);
		if (StringHelper.isNotEmpty(parentKey))
		{
			PFPDictionaryRecordWrapper<InsuranceType> insuranceTypeWrapper = getRecords().get(parentKey);
			if (insuranceTypeWrapper == null)
				throw new BusinessLogicException("Невозможно добавить тип страховки с ключом " + key);
			type.setParent(insuranceTypeWrapper.getRecord());
		}
		
		addRecord(key, type, getImageValue(imageUrl));
	}
}
