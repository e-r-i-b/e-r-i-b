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
 * ќпераци€ удалени€ операции (клиент может удал€ть только те операции, которые сам добавил)
 */
public class RemoveCardOperationOperation extends OperationBase
{
	private CardOperation clientOperation;
	private static final CardOperationService cardOperationService = new CardOperationService();

	/**
	 * »нициализаци€ операции
	 * @param id - идентификатор операции
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		clientOperation = cardOperationService.findById(id);

		if (clientOperation == null)
			throw new ResourceNotFoundBusinessException(" лиентска€ операци€ с id=" + id + " не найдена.", CardOperation.class);

		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		if (!login.getId().equals(clientOperation.getOwnerId()))
			throw new AccessException(" лиент с id = " + login.getId() + " не имеет доступа к операции с id = " + clientOperation.getId());

		if (clientOperation.getOperationType() != OperationType.OTHER)
			throw new BusinessException("Ќевозможно удалить данную операцию. ћожно удал€ть только траты наличными.");
	}

	/**
	 * ”дал€ет операцию
	 * @throws BusinessException
	 */
	public void remove() throws BusinessException
	{
		cardOperationService.remove(clientOperation);
	}

	/**
	 * @return удал€ема€ операци€
	 */
	public CardOperation getEntity()
	{
		return clientOperation;
	}
}
