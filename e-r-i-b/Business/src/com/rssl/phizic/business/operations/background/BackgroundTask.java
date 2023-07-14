package com.rssl.phizic.business.operations.background;

import com.rssl.phizic.business.BusinessException;

import java.util.Calendar;
import java.util.Properties;

/**
 * Интерфейс фоновой задачи
 * Реализации для сохранения специфичных параметров должны использовать свои атрибуты.
 */
public interface BackgroundTask<R extends TaskResult>
{

	/**
	 * @return идетнификатор задачи
	 */
	public Long getId();

	/**
	 * @return идентификатор владельца/создетаеля фоновой задачи
	 */
	public Long getOwnerId();

	/**
	 * @return дата создания задачи
	 */
	public Calendar getCreationDate();

	/**
	 * @return статус задачи
	 */
	public TaskState getState();

	/**
	 * @return специфичные настройки выполнения
	 */
	public Properties getProperties() throws BusinessException;

	/**
	 * получить результат исполнения задачи
	 * Если задача еще не исполнилась возвращается null
	 * @return резушльтат исполнения
	 */
	public R getResult();

	/**
	 * Метод, оповещающий задачу том, что ее выполение завершено.
	 * Реализации метода должны позаботиться об очистке специфичных параметров задачи,
	 * которые не важны для просмотра задачи(например, файлы для репликации).
	 * Также данный метод должен перевести задачу в состоение исполенени
	 * @param result результат исполения задачи не может быть null
	 */
	public void executed(R result);

	/**
	 * Метод возвращающий название операции, на основе которой была создана фоновая задача
	 * @return название операции
	 */
	public String getOperationClassName();
}
