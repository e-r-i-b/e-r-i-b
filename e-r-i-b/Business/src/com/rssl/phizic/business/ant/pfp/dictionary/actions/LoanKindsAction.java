package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProduct;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 * экшен для парсинга типа кредита из xml
 */
public class LoanKindsAction extends DictionaryRecordsActionBase<LoanKindProduct>
{
	public void execute(Element element) throws BusinessException, BusinessLogicException
	{
		String key  = XmlHelper.getSimpleElementValue(element, "key");
		String name = XmlHelper.getSimpleElementValue(element, "name");
		BigDecimal fromAmount = new BigDecimal(XmlHelper.getSimpleElementValue(element, "fromAmount"));
		BigDecimal toAmount = new BigDecimal(XmlHelper.getSimpleElementValue(element, "toAmount"));
		Long fromPeriod = Long.valueOf(XmlHelper.getSimpleElementValue(element, "fromPeriod"));
		Long toPeriod = Long.valueOf(XmlHelper.getSimpleElementValue(element, "toPeriod"));
		Long defaultPeriod = Long.valueOf(XmlHelper.getSimpleElementValue(element, "defaultPeriod"));
		BigDecimal fromRate = new BigDecimal(XmlHelper.getSimpleElementValue(element, "fromRate"));
		BigDecimal toRate = new BigDecimal(XmlHelper.getSimpleElementValue(element, "toRate"));
		BigDecimal defaultRate =  new BigDecimal(XmlHelper.getSimpleElementValue(element, "defaultRate"));
		String imageUrl = XmlHelper.getSimpleElementValue(element, "imageUrl");

		LoanKindProduct product = new LoanKindProduct();
		product.setName(name);
		product.setFromAmount(fromAmount);
		product.setToAmount(toAmount);
		product.setFromPeriod(fromPeriod);
		product.setToPeriod(toPeriod);
		product.setDefaultPeriod(defaultPeriod);
		product.setFromRate(fromRate);
		product.setToRate(toRate);
		product.setDefaultRate(defaultRate);
		addRecord(key, product, getImageValue(imageUrl));
	}
}
