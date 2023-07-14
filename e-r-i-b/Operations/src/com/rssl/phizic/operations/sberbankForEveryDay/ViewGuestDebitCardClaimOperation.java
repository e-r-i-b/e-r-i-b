package com.rssl.phizic.operations.sberbankForEveryDay;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.gate.claims.sbnkd.IssueCardService;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * Операция для просмотра заявки клиента на дебетовые карты
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ViewGuestDebitCardClaimOperation extends OperationBase implements EditEntityOperation
{
	private static final IssueCardService issueCardService = new IssueCardService();
	private IssueCardDocumentImpl document;

	/**
	 * Инициализация
	 * @param id - идентификатор заявки
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		try
		{
			document = issueCardService.getClaim(id);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	public void save() throws BusinessException, BusinessLogicException
	{
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return document;
	}
}
