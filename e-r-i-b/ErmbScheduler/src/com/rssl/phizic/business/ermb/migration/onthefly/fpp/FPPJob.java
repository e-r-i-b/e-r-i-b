package com.rssl.phizic.business.ermb.migration.onthefly.fpp;

import org.quartz.*;

import java.io.InvalidClassException;

/**
 * @author Erkin
 * @ created 27.08.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Джоб для миграции на-лету файла прямого потока (ФПП) Мобильного Банка по Картам (МБК).
 * Перехватывает ФПП МБК с подключениями клиентов к услуге "Мобильный Банк" в МБК
 * и подключает клиентов к ЕРМБ.
 * Клиенты подключаются не все, а только из Пилотной Зоны (ПЗ).
 */
public class FPPJob implements Job, InterruptableJob
{
	/**
	 * Флажок "выполнение джоба было прервано"
	 */
	private transient volatile boolean interrupted = false;

	///////////////////////////////////////////////////////////////////////////

	public void execute(JobExecutionContext context) throws JobExecutionException
	{

	}

	public void interrupt() throws UnableToInterruptJobException
	{
		interrupted = true;
	}
}
