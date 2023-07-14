package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductParameters;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author akrenev
 * @ created 21.06.2013
 * @ $Author$
 * @ $Revision$
 */

abstract class ProductsAction<P extends ProductBase> extends DictionaryRecordsActionBase<P>
{
	private static final String TAG_NAME = "parameters";
	private static final String PORTFOLIO_TYPE_ATTRIBUTE_NAME = "portfolio";
	private static final String PORTFOLIO_PARAMETERS_TAG_NAME = "portfolioParameters";

	Map<PortfolioType, ProductParameters> getParameters(Element element) throws BusinessException
	{
		try
		{
			final Map<PortfolioType, ProductParameters> map = new HashMap<PortfolioType, ProductParameters>(2);
			Element portfolioParametersElement = XmlHelper.selectSingleNode(element, PORTFOLIO_PARAMETERS_TAG_NAME);
			XmlHelper.foreach(portfolioParametersElement, TAG_NAME, new ForeachElementAction()
			{
				public void execute(Element element)
				{
					String portfolioTypeName = element.getAttribute(PORTFOLIO_TYPE_ATTRIBUTE_NAME);
					PortfolioType portfolioType = PortfolioType.valueOf(portfolioTypeName);
					String minSum = XmlHelper.getSimpleElementValue(element, "minSum");
					map.put(portfolioType, new ProductParameters(getBigDecimalValue(minSum)));
				}
			});

			return map;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	Set<SegmentCodeType> getTargetGroup(Element element) throws BusinessException
	{
		try
		{
			Element tGroup = XmlHelper.selectSingleNode(element,"targetGroups");
			final Set<SegmentCodeType> targetGroup = new HashSet<SegmentCodeType>();
			if (tGroup != null)
			{
				XmlHelper.foreach(tGroup, "targetGroup", new ForeachElementAction()
				{
					public void execute(Element element) throws BusinessLogicException
					{
						targetGroup.add(SegmentCodeType.valueOf(XmlHelper.getElementText(element)));
					}
				});
			}
			return targetGroup;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
