package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ant.pfp.dictionary.PFPDictionaryRecordWrapper;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.TableViewParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexFundInvestmentProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.ForComplexProductDiscriminator;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct;
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
 * ����� ��� �������� ������������ ��������������� �������� (����� + ���) �� xml
 */
public class ComplexFundInvestmentProductsAction extends ProductsAction<ComplexFundInvestmentProduct>
{
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> accounts;   //������������ ������
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> funds;      //������������ ����
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> productTypes;

	public void initialize(Map<PFPDictionaryConfig, Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>>> allRecords)
	{
		productTypes = allRecords.get(PFPDictionaryConfig.productTypes);
		accounts = allRecords.get(PFPDictionaryConfig.accountProducts);
		funds = allRecords.get(PFPDictionaryConfig.fundProducts);
	}

	public void execute(Element element) throws BusinessException, BusinessLogicException
	{
		String description = XmlHelper.getSimpleElementValue(element, "description");
		Map<PortfolioType, ProductParameters> parameters = getParameters(element);
		String accountKey = XmlHelper.getSimpleElementValue(element, "account");

		ProductTypeParameters type = (ProductTypeParameters)(productTypes.get("COMPLEX_INVESTMENT").getRecord());
		//�������� ����
		PFPDictionaryRecordWrapper<PFPDictionaryRecord> accountWrapper = accounts.get(accountKey);
		if (accountWrapper == null)
			throw new BusinessLogicException("�� ������ ����� � ������ " + accountKey);

		PFPDictionaryRecord account = accountWrapper.getRecord();
		if (!(account instanceof AccountProduct))
			throw new BusinessLogicException("�� ������ ����� � ������ " + accountKey);
		if (ForComplexProductDiscriminator.fund != ((AccountProduct) account).getForComplex())
			throw new BusinessLogicException("����� � ������ " + accountKey + " �� ����� ���� �������� � ������������ ��������������� �������� (����� + ���)");

		//�������� ������ �����
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
						throw new BusinessLogicException("�� ������ ��� � ������ " + key);
					PFPDictionaryRecord fundProduct = fundWrapper.getRecord();
					if (!(fundProduct instanceof FundProduct))
						throw new BusinessLogicException("�� ������ ��� � ������ " + key);
					if (ForComplexProductDiscriminator.fund != ((FundProduct) fundProduct).getForComplex())
						throw new BusinessLogicException("��� � ������ " + key + " �� ����� ���� �������� � ������������ ��������������� �������� (����� + ���)");
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
	                    //������������ ���������, �� ������� ������� ������� �� ������ ������� � �������� ����� � �� ����
	                    for (TableColumn c:tableColumns)
	                    {
		                    if (c.getValue().equals(index))
		                    {
			                    tableColumn = c;
		                    }
	                    }
	                    String value = XmlHelper.getSimpleElementValue(element,"value");
	                    //������ ������, ���� ����� �������� ������
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
		//������� ��������
		ComplexFundInvestmentProduct product = new ComplexFundInvestmentProduct();
		product.setDescription(description);
		product.setParameters(parameters);
		product.setUniversal(universal);
		product.setTargetGroup(getTargetGroup(element));
		product.setTableParameters(tableParameters);
		product.setAccount((AccountProduct) account);
		product.setFundProducts(fundProducts);
		addRecord(accountKey, product);
	}
}
