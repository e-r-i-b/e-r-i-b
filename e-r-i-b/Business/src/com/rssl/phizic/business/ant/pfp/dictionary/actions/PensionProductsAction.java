package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ant.pfp.dictionary.PFPDictionaryRecordWrapper;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.PensionProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund.PensionFund;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Экшен загрузки пенсионных продуктов
 * @author koptyaev
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 */
public class PensionProductsAction extends ProductsAction<PensionProduct>
{
	private static final BigDecimalParser parser = new BigDecimalParser();

	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> pensionFunds;

	public void initialize(Map<PFPDictionaryConfig, Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>>> allRecords)
	{
		pensionFunds = allRecords.get(PFPDictionaryConfig.pensionFunds);
	}
	public void execute(Element element) throws Exception
	{
		String key = XmlHelper.getSimpleElementValue(element,"key");
		String name = XmlHelper.getSimpleElementValue(element,"name");

		String description = XmlHelper.getSimpleElementValue(element, "description");
		BigDecimal minIncome = parser.parse(XmlHelper.getSimpleElementValue(element, "minIncome"));
		BigDecimal maxIncome =  parser.parse(XmlHelper.getSimpleElementValue(element, "maxIncome"));
		BigDecimal defaultIncome =  parser.parse(XmlHelper.getSimpleElementValue(element, "defaultIncome"));

		boolean universal = getBooleanValue(XmlHelper.getSimpleElementValue(element, "universal"));
		boolean enabled = getBooleanValue(XmlHelper.getSimpleElementValue(element, "enabled"));
		BigDecimal entryFee = parser.parse(XmlHelper.getSimpleElementValue(element, "entryFee"));
		BigDecimal quarterlyFee = parser.parse(XmlHelper.getSimpleElementValue(element, "quarterlyFee"));
		Long minPeriod = Long.parseLong(XmlHelper.getSimpleElementValue(element, "minPeriod"));
		Long maxPeriod = Long.parseLong(XmlHelper.getSimpleElementValue(element, "maxPeriod"));
		Long defaultPeriod = Long.parseLong(XmlHelper.getSimpleElementValue(element, "defaultPeriod"));

		String pensionFundKey = XmlHelper.getSimpleElementValue(element, "pensionFund");
		PFPDictionaryRecordWrapper<PFPDictionaryRecord> pensionFundWrapper = pensionFunds.get(pensionFundKey);
		if (pensionFundWrapper == null)
			throw new BusinessLogicException("Не найден вклад с ключом " + pensionFundKey);
		PensionFund pensionFund = (PensionFund)pensionFundWrapper.getRecord();

		String imageUrl = XmlHelper.getSimpleElementValue(element,"imageUrl");

		PensionProduct product = new PensionProduct();
		product.setName(name);
		product.setUniversal(universal);
		product.setEnabled(enabled);
		product.setEntryFee(entryFee);
		product.setQuarterlyFee(quarterlyFee);
		product.setPensionFund(pensionFund);
		product.setMinPeriod(minPeriod);
		product.setMaxPeriod(maxPeriod);
		product.setDefaultPeriod(defaultPeriod);
		product.setTargetGroup(getTargetGroup(element));
		product.setMinIncome(minIncome);
		product.setMaxIncome(maxIncome);
		product.setDefaultIncome(defaultIncome);
		product.setDescription(description);

		addRecord(key, product, getImageValue(imageUrl));
	}
}
