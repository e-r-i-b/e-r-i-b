package com.rssl.phizic.operations.finances;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;

/**
 * �������� ������� ��������
 * @author koptyaev
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 */
public class HideCardOperationOperation extends OperationBase
{
	private CardOperation clientOperation;
	private static final CardOperationService cardOperationService = new CardOperationService();

	/**
	 * ������������� ��������
	 * @param id - ������������� ��������
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		clientOperation = cardOperationService.findById(id);

		if (clientOperation == null)
			throw new ResourceNotFoundBusinessException("���������� �������� � id=" + id + " �� �������.", CardOperation.class);

		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		if (!login.getId().equals(clientOperation.getOwnerId()))
			throw new AccessException("������ � id = " + login.getId() + " �� ����� ������� � �������� � id = " + clientOperation.getId());
	}

	/**
	 * �������� ��������
	 */
	public void hide()
	{
		clientOperation.setHidden(true);
	}
	/**
	 * ���������� ��������� ������� ��������
	 */
	public void show()
	{
		clientOperation.setHidden(false);

	}

	/**
	 * ��������� ��������
	 * @throws BusinessException
	 */
	public void save() throws BusinessException
	{
		cardOperationService.addOrUpdate(clientOperation);
	}

	/**
	 * @return ���������� ��������
	 */
	public CardOperation getEntity()
	{
		return clientOperation;
	}

}
