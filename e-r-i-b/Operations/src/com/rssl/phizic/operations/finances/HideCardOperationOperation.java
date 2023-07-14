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
 * Операция скрытия операции
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
	 * Инициализация операции
	 * @param id - идентификатор операции
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		clientOperation = cardOperationService.findById(id);

		if (clientOperation == null)
			throw new ResourceNotFoundBusinessException("Клиентская операция с id=" + id + " не найдена.", CardOperation.class);

		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		if (!login.getId().equals(clientOperation.getOwnerId()))
			throw new AccessException("Клиент с id = " + login.getId() + " не имеет доступа к операции с id = " + clientOperation.getId());
	}

	/**
	 * Скрывает операцию
	 */
	public void hide()
	{
		clientOperation.setHidden(true);
	}
	/**
	 * Возвращает видимость скрытой операции
	 */
	public void show()
	{
		clientOperation.setHidden(false);

	}

	/**
	 * Сохраняет операцию
	 * @throws BusinessException
	 */
	public void save() throws BusinessException
	{
		cardOperationService.addOrUpdate(clientOperation);
	}

	/**
	 * @return скрываемая операция
	 */
	public CardOperation getEntity()
	{
		return clientOperation;
	}

}
