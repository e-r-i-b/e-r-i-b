package com.rssl.phizic.business.dictionaries.pfp.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.util.List;

/**
 * @author akrenev
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ � ��������� ���
 */

public class ProductService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * ����� �������
	 * @param id ������������� ��������
	 * @param productClass ����� ��������
	 * @param <P> ����� ��������
	 * @return �������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public <P extends ProductBase> P getById(final Long id, Class<P> productClass) throws BusinessException
	{
		return getById(id, productClass, null);
	}

	/**
	 * ����� �������
	 * @param id ������������� ��������
	 * @param productClass ����� ��������
	 * @param <P> ����� ��������
	 * @param instance ��� �������� ������ ��
	 * @return �������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public <P extends ProductBase> P getById(final Long id, Class<P> productClass, String instance) throws BusinessException
	{
		return service.findById(productClass, id, instance);
	}

	/**
	 * @param ids ��������������
	 * @param productClass ����� ��������
	 * @param <P> ����� ��������
	 * @param instance ��� �������� ������ ��
	 * @return ������ ��������� �� ���������������
	 * @throws BusinessException
	 */
	public <P extends ProductBase> List<P> getListByIds(Long[] ids, Class<P> productClass, String instance) throws BusinessException
	{
		return service.getListByIds(productClass, ids, instance);
	}

	/**
	 * ��������� �������
	 * @param product ����������� �������
	 * @param <P> ����� ��������
	 * @param instance ��� �������� ������ ��
	 * @return �������
	 * @throws BusinessException
	 */
	public <P extends ProductBase> P addOrUpdate(final P product, String instance) throws BusinessException
	{
		return service.addOrUpdate(product, instance);
	}

	/**
	 * ������� �������
	 * @param product ��������� �������
	 * @param instance ��� �������� ������ ��
	 * @throws BusinessException
	 */
	public void remove(final MultiBlockDictionaryRecordBase product, String instance) throws BusinessException
	{
		service.remove(product, instance);
	}
}
