package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.bankcells.CellType;
import com.rssl.phizic.business.dictionaries.bankcells.TermOfLease;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.EditEntityOperation;

import java.util.*;

/**
 * ����������/�������� ������ [ ��� ������ / ���� ������ ]
 * @author Kidyaev
 * @ created 16.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class ChangeCellTypeTermsOfLeaseOperation extends OperationBase implements EditEntityOperation
{
	private static final SimpleService simpleService = new SimpleService();

	private CellType                         cellType;

	/**
	 * @param cellTypeId ID ���� �������� ������
	 * @throws BusinessException
	 */
	public void initialize(Long cellTypeId) throws BusinessException, BusinessLogicException
	{
		cellType = simpleService.findById(CellType.class, cellTypeId);
		if (cellType == null){
			throw new BusinessLogicException("�������� ������ �� �������. id="+cellTypeId);
		}
	}


	/**
	 * @return ��� �������� ������
	 */
	public CellType getEntity()
	{
		return cellType;
	}

	public void setTermOfLeases(List<Long> ids) throws BusinessException
	{
		Set<TermOfLease> termsOfLease = cellType.getTermsOfLease();
		termsOfLease.clear();
		for (Long id:ids){
			termsOfLease.add((TermOfLease) simpleService.findById(TermOfLease.class, id));
		}
	}

	/**
	 * ��������� ���������
	 * @throws BusinessException
	 */
	public void save() throws BusinessException
	{
		simpleService.update(cellType);
	}
}
