package com.rssl.phizic.business.operations.background;

import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author krenev
 * @ created 19.07.2011
 * @ $Author$
 * @ $Revision$
 * Операции, которые могут выполняться в фоне
 */
public interface BackgroundOperation<TR extends TaskResult, T extends BackgroundTask<TR>, R extends Restriction> extends Operation<R>
{
	/**
	 * Проинициализировать операцию данными из фоновой задачи
	 * @param backroundTask фоновая задача
	 */
	void initialize(T backroundTask) throws BusinessException, BusinessLogicException;

	/**
	 * Создать и зарегистрировать фоновую задачу.
	 * Все пользовательские параметры, необходимые для выполнения задачи должны браться из
	 * проинициализированной операции и сохраняться в задаче
	 * @return фоновая задача, проинициализированая пользовательскими данными.
	 */
	T createBackroundTask() throws BusinessException, BusinessLogicException;

	/**
	 * Выполнить действие(операцию).
	 * Данный метод инкапсулирует бизнес логику исполениеия операции и используется как при онлайн,
	 * так и при фоновом выполнении.
	 * @return результат выполнения действия
	 */
	TR execute() throws BusinessException, BusinessLogicException;
}
