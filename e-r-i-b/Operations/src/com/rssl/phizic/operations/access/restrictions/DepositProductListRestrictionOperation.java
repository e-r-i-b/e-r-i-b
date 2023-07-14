package com.rssl.phizic.operations.access.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.operations.restrictions.ListDepositPoductRestrictionData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * �������� ��� ������ � ������������� �� �����.
 * @author Roshka
 * @ created 19.04.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1379 $
 */

public class DepositProductListRestrictionOperation extends RestrictionOperationBase<ListDepositPoductRestrictionData>
{
	private DepositProductService depositProductService = new DepositProductService();

	/**
	 * �������� ������ ���� ���������� ���������.
	 * @return
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<DepositProduct> getAllProducts() throws BusinessException
	{
		return depositProductService.getAllProducts();
	}

	/**
	 * ���������� ����������� ��� ���������
	 * @param ids ������ id ���������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void setProductIds(List<Long> ids) throws BusinessException
	{
		Set<Long> idSet = new HashSet<Long>(ids);

		List<DepositProduct> products = getAllProducts();
		List<DepositProduct> selectedProducts = new ArrayList<DepositProduct>();

		for (DepositProduct product: products)
		{
			if (idSet.contains(product.getId()))
				selectedProducts.add(product);
		}
		getRestrictionData().setProducts(selectedProducts);
	}

	/**
	 * ��. ������� � base {@link com.rssl.phizic.operations.access.restrictions.RestrictionOperationBase#createNew(Long, Long, Long)}
	 */
	protected ListDepositPoductRestrictionData createNew(Long loginId, Long serviceId, Long operationId)
	{
		ListDepositPoductRestrictionData data = new ListDepositPoductRestrictionData();
		data.setLoginId(loginId);
		data.setServiceId(serviceId);
		data.setOperationId(operationId);
		data.setProducts(new ArrayList<DepositProduct>());

        return data;
	}
}