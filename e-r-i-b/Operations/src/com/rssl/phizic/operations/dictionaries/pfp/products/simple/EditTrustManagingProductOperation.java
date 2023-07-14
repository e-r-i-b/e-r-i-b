package com.rssl.phizic.operations.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriodService;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductHelper;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.TrustManagingProduct;
import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;
import com.rssl.phizic.business.dictionaries.pfp.risk.RiskService;
import com.rssl.phizic.operations.dictionaries.pfp.products.EditProductOperationBase;

import java.util.List;

/**
 * @author akrenev
 * @ created 16.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������������� �������� "������������� �����������"
 */

public class EditTrustManagingProductOperation extends EditProductOperationBase<TrustManagingProduct>
{
	private static final RiskService riskService = new RiskService();
	private static final InvestmentPeriodService investmentPeriodService = new InvestmentPeriodService();

	protected Class<TrustManagingProduct> getProductClass()
	{
		return TrustManagingProduct.class;
	}

	@Override
	protected DictionaryProductType getProductType()
	{
		return DictionaryProductType.TRUST_MANAGING;
	}

	protected TrustManagingProduct getNewProduct()
	{
		TrustManagingProduct trustManagingProduct = new TrustManagingProduct();
		trustManagingProduct.setParameters(ProductHelper.getNewProductParametersMap());
		return trustManagingProduct;
	}

	/**
	 * @return ������ ���� ������
	 * @throws BusinessException
	 */
	public List<Risk> getAllRisks() throws BusinessException
	{
		return riskService.getAll(getInstanceName());
	}

	/**
	 * �������� ���� �� ��������������
	 * @param id ������������� �����
	 * @return ������ ���� ������
	 * @throws BusinessException
	 */
	public Risk getRiskById(Long id) throws BusinessException
	{
		return riskService.getById(id, getInstanceName());
	}

	/**
	 * @return ������ ���� "���������� ��������������"
	 * @throws BusinessException
	 */
	public List<InvestmentPeriod> getAllInvestmentPeriod() throws BusinessException
	{
		return investmentPeriodService.getAll(getInstanceName());
	}

	/**
	 * �������� "�������� ��������������" �� ��������������
	 * @param id ������������� "��������� ��������������"
	 * @return "�������� ��������������"
	 * @throws BusinessException
	 */
	public InvestmentPeriod getInvestmentPeriodById(Long id) throws BusinessException
	{
		return investmentPeriodService.getById(id, getInstanceName());
	}
}
