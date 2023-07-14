package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ant.pfp.dictionary.PFPDictionaryRecordWrapper;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.TableViewParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.TrustManagingProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.types.TableColumn;
import com.rssl.phizic.business.dictionaries.pfp.products.types.TableColumnComparator;
import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.*;

/**
 * Экшен загрузки продуктов доверительного управления
 * @author koptyaev
 * @ created 30.07.13
 * @ $Author$
 * @ $Revision$
 */
public class TrustManagingProductsAction extends ProductsAction<TrustManagingProduct>
{
	private static final BigDecimalParser parser = new BigDecimalParser();
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> productTypes;
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> risks;
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> investmentPeriods;

	public void initialize(Map<PFPDictionaryConfig, Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>>> allRecords)
	{
		productTypes = allRecords.get(PFPDictionaryConfig.productTypes);
		risks = allRecords.get(PFPDictionaryConfig.risks);
		investmentPeriods = allRecords.get(PFPDictionaryConfig.investmentPeriods);
	}

	public void execute(Element element) throws Exception
	{
		String key  = XmlHelper.getSimpleElementValue(element, "key");
		//риск по ключу
		String riskKey = XmlHelper.getSimpleElementValue(element, "risk");
		PFPDictionaryRecordWrapper<PFPDictionaryRecord> riskWrapper = risks.get(riskKey);
		if (riskWrapper == null)
			throw new BusinessLogicException("Не найден риск с ключом " + riskKey);
		Risk risk = (Risk)riskWrapper.getRecord();
		//горизонт инвестирования по ключу
		String investmentPeriodKey = XmlHelper.getSimpleElementValue(element, "investmentPeriod");
		PFPDictionaryRecordWrapper<PFPDictionaryRecord> investmentPeriodWrapper = investmentPeriods.get(investmentPeriodKey);
		if (investmentPeriodWrapper == null)
			throw new BusinessLogicException("Не найден горизонт инвестирования с ключом " + investmentPeriodKey);
		InvestmentPeriod investmentPeriod = (InvestmentPeriod)investmentPeriodWrapper.getRecord();
		//параметры - волшебством наследования
		Map<PortfolioType, ProductParameters> parameters = getParameters(element);

		String name = XmlHelper.getSimpleElementValue(element, "name");
		String description = XmlHelper.getSimpleElementValue(element, "description");
		String imageUrl = XmlHelper.getSimpleElementValue(element, "imageUrl");
		BigDecimal minIncome = parser.parse(XmlHelper.getSimpleElementValue(element, "minIncome"));
		BigDecimal maxIncome =  parser.parse(XmlHelper.getSimpleElementValue(element, "maxIncome"));
		BigDecimal defaultIncome =  parser.parse(XmlHelper.getSimpleElementValue(element, "defaultIncome"));

		final Set<SegmentCodeType> targetGroup = new HashSet<SegmentCodeType>();
		XmlHelper.foreach(element, "targetGroup", new ForeachElementAction()
		{
			public void execute(Element element) throws BusinessLogicException
			{
				targetGroup.add(SegmentCodeType.valueOf(XmlHelper.getElementText(element)));
			}
		});

		String axisX = XmlHelper.getSimpleElementValue(element, "axisX");
		String axisY = XmlHelper.getSimpleElementValue(element, "axisY");

		boolean universal = getBooleanValue(XmlHelper.getSimpleElementValue(element, "universal"));
		boolean enabled = getBooleanValue(XmlHelper.getSimpleElementValue(element, "enabled"));

		//Для раздела счета - ключ "TRUST_MANAGING", сам раздел завернут в обертку, поэтому получать только так
	    ProductTypeParameters type = (ProductTypeParameters)(productTypes.get("TRUST_MANAGING").getRecord());
		TableViewParameters tableParameters = new TableViewParameters();
		try
		{
			if (type.isUseOnTable())
		    {
			    Element tableParametersElement = XmlHelper.selectSingleNode(element,"tableParameters");
			    final List<TableColumn> tableColumns =  type.getTableParameters().getColumns();
			    boolean useIcon = getBooleanValue(XmlHelper.getSimpleElementValue(tableParametersElement, "useIcon"));
			    tableParameters.setUseIcon(useIcon);
			    final Map<TableColumn, String> columns = new TreeMap<TableColumn, String>(new TableColumnComparator());
			    XmlHelper.foreach(tableParametersElement, "column", new ForeachElementAction()
                {
                    public void execute(Element element)
                    {
                        TableColumn tableColumn = null;
	                    String index = XmlHelper.getSimpleElementValue(element, "index");
	                    //квадратичная сложность, но другого способа выбрать из списка элемент с заданным полем я не знаю
	                    for (TableColumn c:tableColumns)
	                    {
		                    if (c.getValue().equals(index))
		                    {
			                    tableColumn = c;
		                    }
	                    }
	                    String value = XmlHelper.getSimpleElementValue(element,"value");
	                    //кладем только, если нашли ключевую запись
	                    if(tableColumn!=null)
                            columns.put(tableColumn, value);
                    }
                });
			    tableParameters.setColumns(columns);
		    }
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}

		TrustManagingProduct product = new TrustManagingProduct();
		product.setRisk(risk);
		product.setInvestmentPeriod(investmentPeriod);
		product.setName(name);
		product.setDescription(description);
		product.setMinIncome(minIncome);
		product.setMaxIncome(maxIncome);
		product.setDefaultIncome(defaultIncome);
		product.setTargetGroup(targetGroup);
		if (StringHelper.isNotEmpty(axisX))
			product.setAxisX(Long.parseLong(axisX));
		if (StringHelper.isNotEmpty(axisX))
			product.setAxisY(Long.parseLong(axisY));
		product.setParameters(parameters);
		product.setTableParameters(tableParameters);
		product.setUniversal(universal);
		product.setEnabled(enabled);

		addRecord(key, product, getImageValue(imageUrl));
	}
}
