package com.rssl.phizic.operations.finances.targets;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.finances.targets.AccountTargetService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.finances.FinancesConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author lepihina
 * @ created 01.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class BaseTargetOperation extends OperationBase
{
	private static final AccountTargetService targetService = new AccountTargetService();
	private static final String TOO_MANY_TARGETS_MESSAGE = "Вы не можете добавить новую цель, потому что Вы создали максимальное количество целей.";

	private Integer targetsCount;

	public void initialize() throws BusinessException
	{
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		targetsCount = targetService.countTargets(login);
	}

	/**
	 * Возвращает количество целей клиента
	 * @return количество целей
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Integer getTargetsCount() throws BusinessException
	{
		return targetsCount;
	}

	/**
	 * Проверят не создал ли клиент уже максимально возможное количество целей
	 * @return true - у клиента уже максимальное кол-во целей
	 * @throws BusinessException
	 */
	public boolean tooManyTargets() throws BusinessException
	{
		return targetsCount >= ConfigFactory.getConfig(FinancesConfig.class).getMaxClientTarget();
	}

	/**
	 * Возвращает текст ошибки "слишком много целей"
	 * @return сообщение
	 */
	public String getTooManyTargetsMessage()
	{
		return TOO_MANY_TARGETS_MESSAGE;
	}
}
