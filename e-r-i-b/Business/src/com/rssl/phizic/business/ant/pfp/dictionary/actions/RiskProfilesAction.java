package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 * экшен для парсинга риск-профиля из xml
 */
public class RiskProfilesAction extends DictionaryRecordsActionBase<RiskProfile>
{
	public void execute(Element element) throws BusinessException, BusinessLogicException
	{
		String name = XmlHelper.getSimpleElementValue(element, "name");
		SegmentCodeType segment = SegmentCodeType.valueOf(XmlHelper.getSimpleElementValue(element,"segment"));
		String minWeight = XmlHelper.getSimpleElementValue(element, "minWeight");
		String maxWeight = XmlHelper.getSimpleElementValue(element, "maxWeight");
		String description = XmlHelper.getSimpleElementValue(element, "description");
		boolean deleted = getBooleanValue(XmlHelper.getSimpleElementValue(element, "deleted"));

		Map<ProductType, Long> productsWeights = new HashMap<ProductType, Long>();
		productsWeights.put(ProductType.account,        getLongValue(XmlHelper.getSimpleElementValue(element, ProductType.account.name())));
		productsWeights.put(ProductType.IMA,            getLongValue(XmlHelper.getSimpleElementValue(element, ProductType.IMA.name())));
		productsWeights.put(ProductType.fund,           getLongValue(XmlHelper.getSimpleElementValue(element, ProductType.fund.name())));
		productsWeights.put(ProductType.insurance,      getLongValue(XmlHelper.getSimpleElementValue(element, ProductType.insurance.name())));
		productsWeights.put(ProductType.trustManaging,  getLongValue(XmlHelper.getSimpleElementValue(element, ProductType.trustManaging.name())));

		RiskProfile profile = new RiskProfile();
		profile.setName(name);
		profile.setSegment(segment);
		profile.setDeleted(deleted);
		profile.setMinWeight(getLongValue(minWeight));
		profile.setMaxWeight(getLongValue(maxWeight));
		profile.setDescription(description);
		profile.setProductsWeights(productsWeights);
		addRecord(minWeight + "-" + maxWeight + "-" + segment.getDescription(), profile);
	}
}
