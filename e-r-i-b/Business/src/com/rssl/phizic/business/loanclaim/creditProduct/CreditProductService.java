package com.rssl.phizic.business.loanclaim.creditProduct;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author Moshenko
 * @ created 04.01.2014
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������ � ���������� ����������
 */
public class CreditProductService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * @return ��� ��������� ��������.
	 * @throws BusinessException
	 */
	public List<CreditProduct> getAll() throws BusinessException
	{
		return simpleService.getAll(CreditProduct.class);
	}

	/**
	 * @param product ��������� �������
	 * @throws BusinessException
	 */
	public CreditProduct addOrUpdate(CreditProduct product) throws BusinessException
	{
		return simpleService.addOrUpdate(product);
	}

	/**
	 * @param product ��������� �������
	 * @throws BusinessException
	 */
	public void remove(CreditProduct product) throws BusinessException
	{
		simpleService.remove(product);
	}

	/**
	 *
	 * @param id id
	 * @return ��������� �������
	 * @throws BusinessException
	 */
	public CreditProduct findById(Long id) throws  BusinessException
	{
		return simpleService.findById(CreditProduct.class,id);
	}

	/**
	 *
	 * ���������� ��������� ������� �� ����
	 *
	 * @param  productCode ��� ���������� ��������
	 * @return CreditProduct
	 */
	public List<CreditProduct> findByCode(final String productCode) throws BusinessException
	{
		return simpleService.find(DetachedCriteria.forClass(CreditProduct.class)
														 .add(Expression.eq("code", productCode)) );
	}
}
