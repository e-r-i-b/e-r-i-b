package com.rssl.phizic.operations.finances;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.business.finances.OperationType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author lepihina
 * @ created 05.03.14
 * $Author$
 * $Revision$
 * �������� �������� �������� (������ ����� ������� ������ �� ��������, ������� ��� �������)
 */
public class RemoveCardOperationOperation extends OperationBase
{
	private CardOperation clientOperation;
	private static final CardOperationService cardOperationService = new CardOperationService();

	/**
	 * ������������� ��������
	 * @param id - ������������� ��������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		clientOperation = cardOperationService.findById(id);

		if (clientOperation == null)
			throw new ResourceNotFoundBusinessException("���������� �������� � id=" + id + " �� �������.", CardOperation.class);

		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		if (!login.getId().equals(clientOperation.getOwnerId()))
			throw new AccessException("������ � id = " + login.getId() + " �� ����� ������� � �������� � id = " + clientOperation.getId());

		if (clientOperation.getOperationType() != OperationType.OTHER)
			throw new BusinessException("���������� ������� ������ ��������. ����� ������� ������ ����� ���������.");
	}

	/**
	 * ������� ��������
	 * @throws BusinessException
	 */
	public void remove() throws BusinessException
	{
		cardOperationService.remove(clientOperation);
	}

	/**
	 * @return ��������� ��������
	 */
	public CardOperation getEntity()
	{
		return clientOperation;
	}
}
