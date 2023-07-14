package com.rssl.phizic.operations.pereodicalTask;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.pereodicalTask.PereodicalTask;
import com.rssl.phizic.business.pereodicalTask.PereodicalTaskResult;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author akrenev
 * @ created 17.02.2014
 * @ $Author$
 * @ $Revision$
 */

public abstract class PeriodicalTaskOperationBase<T extends PereodicalTask, R extends Restriction> extends OperationBase<R>
{
	protected static final SimpleService simpleService = new SimpleService();

	/**
	 * ѕроинициализировать операцию данными из фоновой задачи
	 * @param backroundTask фонова€ задача
	 */
	public abstract void initialize(T backroundTask) throws BusinessException, BusinessLogicException;

	/**
	 * —оздать и зарегистрировать фоновую задачу.
	 * ¬се пользовательские параметры, необходимые дл€ выполнени€ задачи должны братьс€ из
	 * проинициализированной операции и сохран€тьс€ в задаче
	 * @return фонова€ задача, проинициализирована€ пользовательскими данными.
	 */
	public abstract T createBackroundTask() throws BusinessException, BusinessLogicException;

	/**
	 * ¬ыполнить действие(операцию).
	 * ƒанный метод инкапсулирует бизнес логику исполениеи€ операции и используетс€ как при онлайн,
	 * так и при фоновом выполнении.
	 * @return результат выполнени€ действи€
	 */
	public abstract PereodicalTaskResult execute() throws BusinessException, BusinessLogicException;

	/**
	 * «арегистрировать фоновую задачу
	 * @param backgroundTask фонова€ задача
	 * @return зарегистрированаа€ фонова€ задача
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	protected T registerBackgroundTask(T backgroundTask) throws BusinessException
	{
		return registerBackgroundTask(backgroundTask, null);
	}

	/**
	 * «арегистрировать фоновую задачу
	 * @param backgroundTask фонова€ задача
	 * @param instanceName им€ экземпл€р Ѕƒ
	 * @return зарегистрированаа€ фонова€ задача
	 * @throws BusinessException
	 */
	protected T registerBackgroundTask(T backgroundTask, String instanceName) throws BusinessException
	{
		return simpleService.addOrUpdate(backgroundTask, instanceName);
	}
}
