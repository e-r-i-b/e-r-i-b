package com.rssl.phizic.operations.dictionaries.pfp.risk;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;
import com.rssl.phizic.business.dictionaries.pfp.risk.RiskService;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 15.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������������� �����
 */

public class EditRiskOperation extends EditDictionaryEntityOperationBase
{
	private static final RiskService service = new RiskService();
	private Risk risk;

	/**
	 * ������������� ��������
	 */
	public void initializeNew()
	{
		risk = new Risk();
	}

	/**
	 * ������������� �������� ������������ ���������
	 * @param id ������������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		risk = service.getById(id, getInstanceName());

		if (risk == null)
			throw new ResourceNotFoundBusinessException("���� � id = " + id + " �� ������.", Risk.class);
	}

	public Risk getEntity()
	{
		return risk;
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		service.addOrUpdate(risk, getInstanceName());
	}
}
