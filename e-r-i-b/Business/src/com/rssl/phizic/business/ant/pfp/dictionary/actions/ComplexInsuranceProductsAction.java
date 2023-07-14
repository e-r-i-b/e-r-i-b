package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ant.pfp.dictionary.PFPDictionaryRecordWrapper;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.TableViewParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexInsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.ForComplexProductDiscriminator;
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
 * экшен для парсинга комплексного страхового продукта из xml
 */
public class ComplexInsuranceProductsAction extends ProductsAction<ComplexInsuranceProduct>
{
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> accounts;     //распарсенные вклады
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> insurances;   //распарсенные страховые продукты
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> productTypes;

	public void initialize(Map<PFPDictionaryConfig, Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>>> allRecords)
	{
		productTypes = allRecords.get(PFPDictionaryConfig.productTypes);
		accounts = allRecords.get(PFPDictionaryConfig.accountProducts);
		insurances = allRecords.get(PFPDictionaryConfig.insuranceProducts);
	}

	public void execute(Element element) throws BusinessException, BusinessLogicException
	{
		String description = XmlHelper.getSimpleElementValue(element, "description");
		String minSum = XmlHelper.getSimpleElementValue(element, "minSum");
		String minSumInsurance = XmlHelper.getSimpleElementValue(element, "minSumInsurance");
		String accountKey = XmlHelper.getSimpleElementValue(element, "account");

		//получаем счет
		PFPDictionaryRecordWrapper<PFPDictionaryRecord> accountWrapper = accounts.get(accountKey);
		if (accountWrapper == null)
			throw new BusinessLogicException("Не найден вклад с ключом " + accountKey);

		PFPDictionaryRecord account = accountWrapper.getRecord();
		if (!(account instanceof AccountProduct))
			throw new BusinessLogicException("Не найден вклад с ключом " + accountKey);
		if (ForComplexProductDiscriminator.insurance != ((AccountProduct) account).getForComplex())
			throw new BusinessLogicException("Вклад с ключом " + accountKey + " не может быть добавлен к комплексному инвистиционному продукту (вклад + ПИФ + ОМС)");

		ProductTypeParameters type = (ProductTypeParameters)(productTypes.get("COMPLEX_INSURANCE").getRecord());
		//получаем список страховых продуктов
		final List<InsuranceProduct> insuranceProducts = new ArrayList<InsuranceProduct>();
		try
		{
			Element insuranceProductsElement = XmlHelper.selectSingleNode(element, "insuranceProducts");
			XmlHelper.foreach(insuranceProductsElement, "insuranceProduct", new ForeachElementAction()
			{
				public void execute(Element element) throws BusinessLogicException
				{
					String key = XmlHelper.getElementText(element);
					PFPDictionaryRecordWrapper insuranceWrapper = insurances.get(key);
					if (insuranceWrapper == null)
						throw new BusinessLogicException("Не найден страховой продукт с ключом " + key);
					PFPDictionaryRecord insuranceProduct = insuranceWrapper.getRecord();
					if (!(insuranceProduct instanceof InsuranceProduct))
						throw new BusinessLogicException("Не найден страховой продукт с ключом " + key);
					if (!((InsuranceProduct)insuranceProduct).isForComplex())
						throw new BusinessLogicException("Страховой продукт с ключом " + key + " не может быть добавлен к комплексному страховому продукту");
					insuranceProducts.add((InsuranceProduct) insuranceProduct);
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
		ComplexInsuranceProduct product = new ComplexInsuranceProduct();
		product.setDescription(description);
		product.setMinSum(getBigDecimalValue(minSum));
		product.setUniversal(universal);
		product.setTargetGroup(getTargetGroup(element));
		product.setTableParameters(tableParameters);
		product.setMinSumInsurance(getBigDecimalValue(minSumInsurance));
		product.setAccount((AccountProduct) account);
		product.setInsuranceProducts(insuranceProducts);
		addRecord(accountKey, product);
	}
}
