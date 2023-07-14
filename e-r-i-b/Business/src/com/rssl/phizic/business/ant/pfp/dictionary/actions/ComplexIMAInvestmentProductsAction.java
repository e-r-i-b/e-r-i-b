package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ant.pfp.dictionary.PFPDictionaryRecordWrapper;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.TableViewParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexIMAInvestmentProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.ForComplexProductDiscriminator;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.IMAProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.types.TableColumn;
import com.rssl.phizic.business.dictionaries.pfp.products.types.TableColumnComparator;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author akrenev
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 * 
 * экшен для парсинга комплексного инвистиционного продукта (вклад + ПИФ + ОМС) из xml
 */
public class ComplexIMAInvestmentProductsAction extends ProductsAction<ComplexIMAInvestmentProduct>
{
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> accounts;  //распарсенные вклады
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> funds;     //распарсенные ПИФы
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> imaList;   //распарсенные ОМСы
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> productTypes;

	public void initialize(Map<PFPDictionaryConfig, Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>>> allRecords)
	{
		productTypes = allRecords.get(PFPDictionaryConfig.productTypes);
		accounts = allRecords.get(PFPDictionaryConfig.accountProducts);
		funds = allRecords.get(PFPDictionaryConfig.fundProducts);
		imaList = allRecords.get(PFPDictionaryConfig.imaProducts);
	}

	public void execute(Element element) throws BusinessException, BusinessLogicException
	{
		String description = XmlHelper.getSimpleElementValue(element, "description");
		Map<PortfolioType, ProductParameters> parameters = getParameters(element);
		String accountKey = XmlHelper.getSimpleElementValue(element, "account");
		ProductTypeParameters type = (ProductTypeParameters)(productTypes.get("COMPLEX_INVESTMENT").getRecord());
		//получаем счет
		PFPDictionaryRecordWrapper<PFPDictionaryRecord> accountWrapper = accounts.get(accountKey);
		if (accountWrapper == null)
			throw new BusinessLogicException("Не найден вклад с ключом " + accountKey);

		PFPDictionaryRecord account = accountWrapper.getRecord();
		if (!(account instanceof AccountProduct))
			throw new BusinessLogicException("Не найден вклад с ключом " + accountKey);
		if (ForComplexProductDiscriminator.ima != ((AccountProduct) account).getForComplex())
			throw new BusinessLogicException("Вклад с ключом " + accountKey + " не может быть добавлен к комплексному инвистиционному продукту (вклад + ПИФ + ОМС)");

		//получаем список ПИФов
		final List<FundProduct> fundProducts = new ArrayList<FundProduct>();
		try
		{
			Element fundProductsElement = XmlHelper.selectSingleNode(element, "fundProducts");
			XmlHelper.foreach(fundProductsElement, "fundProduct", new ForeachElementAction()
			{
				public void execute(Element element) throws BusinessLogicException
				{
					String key = XmlHelper.getElementText(element);
					PFPDictionaryRecordWrapper<PFPDictionaryRecord> fundWrapper = funds.get(key);
					if (fundWrapper == null)
						throw new BusinessLogicException("Не найден ПИФ с ключом " + key);
					PFPDictionaryRecord fundProduct = fundWrapper.getRecord();
					if (!(fundProduct instanceof FundProduct))
						throw new BusinessLogicException("Не найден ПИФ с ключом " + key);
					if (ForComplexProductDiscriminator.ima != ((FundProduct) fundProduct).getForComplex())
						throw new BusinessLogicException("ПИФ с ключом " + key + " не может быть добавлен к комплексному инвистиционному продукту (вклад + ПИФ + ОМС)");
					fundProducts.add((FundProduct) fundProduct);
				}
			});
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		//получаем список ОМСов
		final List<IMAProduct> imaProducts = new ArrayList<IMAProduct>();
		try
		{
			Element imaProductsElement = XmlHelper.selectSingleNode(element, "imaProducts");
			XmlHelper.foreach(imaProductsElement, "imaProduct", new ForeachElementAction()
			{
				public void execute(Element element) throws BusinessLogicException
				{
					String key = XmlHelper.getElementText(element);
					PFPDictionaryRecordWrapper<PFPDictionaryRecord> imaWrapper = imaList.get(key);
					if (imaWrapper == null)
						throw new BusinessLogicException("Не найден ОМС с ключом " + key);
					PFPDictionaryRecord imaProduct = imaWrapper.getRecord();
					if (!(imaProduct instanceof IMAProduct))
						throw new BusinessLogicException("Не найден ОМС с ключом " + key);
					if (ForComplexProductDiscriminator.ima != ((IMAProduct) imaProduct).getForComplex())
						throw new BusinessLogicException("ОМС с ключом " + key + " не может быть добавлен к комплексному инвистиционному продукту (вклад + ПИФ + ОМС)");
					imaProducts.add((IMAProduct) imaProduct);
				}
			});
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
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
		boolean universal = getBooleanValue(XmlHelper.getSimpleElementValue(element, "universal"));

		//создаем сущность
		ComplexIMAInvestmentProduct product = new ComplexIMAInvestmentProduct();
		product.setDescription(description);
		product.setUniversal(universal);
		product.setTargetGroup(getTargetGroup(element));
		product.setTableParameters(tableParameters);
		product.setParameters(parameters);
		product.setAccount((AccountProduct) account);
		product.setFundProducts(fundProducts);
		product.setImaProducts(imaProducts);
		addRecord(accountKey, product);
	}
}
