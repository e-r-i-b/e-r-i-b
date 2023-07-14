package com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;

import java.util.List;

/**
 * @author akrenev
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ���������� ������
 */

public class PensionFundService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * �������� �� id
	 * @param id �������������
	 * @param instance ��� �������� ������ ��
	 * @return ���������� ����
	 * @throws BusinessException
	 */
	public PensionFund findById(Long id, String instance) throws BusinessException
	{
		return service.findById(PensionFund.class, id, instance);
	}

	/**
	 * @param instance ��� �������� ������ ��
	 * @return ������ ���� ���������� ������
	 * @throws BusinessException
	 */
	public List<PensionFund> getAll(String instance) throws BusinessException
	{
		return service.getAll(PensionFund.class, instance);
	}

	/**
	 * �������� ��� ��������
	 * @param fund ���������� ����
	 * @param instance ��� �������� ������ ��
	 * @return ����������� ���������� ����
	 * @throws BusinessException
	 */
	public PensionFund addOrUpdate(PensionFund fund, String instance) throws BusinessException
	{
		return service.addOrUpdate(fund, instance);
	}

	/**
	 * �������
	 * @param fund ���������� ����
	 * @param instance ��� �������� ������ ��
	 * @throws BusinessException
	 */
	public void remove(PensionFund fund, String instance) throws BusinessException
	{
		service.remove(fund, instance);
	}
}
