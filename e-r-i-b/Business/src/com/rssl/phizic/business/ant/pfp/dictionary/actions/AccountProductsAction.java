package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ant.pfp.dictionary.PFPDictionaryRecordWrapper;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.TableViewParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AdvisableSum;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.ForComplexProductDiscriminator;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.types.TableColumn;
import com.rssl.phizic.business.dictionaries.pfp.products.types.TableColumnComparator;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author akrenev
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * экшен для парсинга счета из xml
 */
public class AccountProductsAction extends ProductsAction<AccountProduct>
{
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> productTypes;

	public void initialize(Map<PFPDictionaryConfig, Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>>> allRecords)
	{
		productTypes = allRecords.get(PFPDictionaryConfig.productTypes);
	}
	public void execute(Element element) throws BusinessException, BusinessLogicException, ParseException
	{
		String key  = XmlHelper.getSimpleElementValue(element, "key");
		String name = XmlHelper.getSimpleElementValue(element, "name");
		String imageUrl = XmlHelper.getSimpleElementValue(element, "imageUrl");
		String forComplex = XmlHelper.getSimpleElementValue(element, "forComplex");
		Map<PortfolioType, ProductParameters> parameters = getParameters(element);
		String sumFactor = XmlHelper.getSimpleElementValue(element, "sumFactor");
		String description = XmlHelper.getSimpleElementValue(element, "description");
		String advisableSum = XmlHelper.getSimpleElementValue(element, "advisableSum");
		String minIncome = XmlHelper.getSimpleElementValue(element, "minIncome");
		String maxIncome = XmlHelper.getSimpleElementValue(element, "maxIncome");
		String defaultIncome = XmlHelper.getSimpleElementValue(element, "defaultIncome");
		String axisX = XmlHelper.getSimpleElementValue(element, "axisX");
		String axisY = XmlHelper.getSimpleElementValue(element, "axisY");

		boolean universal = getBooleanValue(XmlHelper.getSimpleElementValue(element, "universal"));
		boolean enabled = getBooleanValue(XmlHelper.getSimpleElementValue(element, "enabled"));

		//Для раздела счета - ключ "ACCOUNT", сам раздел завернут в обертку, поэтому получать только так
	    ProductTypeParameters type = (ProductTypeParameters)(productTypes.get("ACCOUNT").getRecord());

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


		AccountProduct product = new AccountProduct();
		product.setName(name);
		product.setAdvisableSum(StringHelper.isEmpty(advisableSum) ? null : AdvisableSum.valueOf(advisableSum));
		product.setDescription(description);
		product.setTargetGroup(super.getTargetGroup(element));
		product.setForComplex(ForComplexProductDiscriminator.valueOf(forComplex));
		product.setParameters(parameters);
		product.setSumFactor(getBigDecimalValue(sumFactor));
		product.setMinIncome(getBigDecimalValue(minIncome));
		product.setMaxIncome(getBigDecimalValue(maxIncome));
		product.setDefaultIncome(getBigDecimalValue(defaultIncome));
		if (StringHelper.isNotEmpty(axisX))
			product.setAxisX(Long.parseLong(axisX));
		if (StringHelper.isNotEmpty(axisX))
			product.setAxisY(Long.parseLong(axisY));
		product.setUniversal(universal);
		product.setEnabled(enabled);
		product.setTableParameters(tableParameters);
		addRecord(key, product, getImageValue(imageUrl));
	}
}
