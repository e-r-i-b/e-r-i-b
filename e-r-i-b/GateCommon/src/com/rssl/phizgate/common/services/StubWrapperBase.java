package com.rssl.phizgate.common.services;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Базовый класс обертки стаба неинтегрированных шлюзов для обновления свойств
 * @author gladishev
 * @ created 09.01.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class StubWrapperBase<S extends java.rmi.Remote>
{
	protected final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	protected volatile S stub = null;

	public StubWrapperBase(S stub)
	{
		this.stub = stub;
	}

	/**
	 * @return стаб
	 */
	public S getStub()
	{
		rwl.readLock().lock();
		try
		{
			if (isActualStub())
				return stub;
		}
		finally
		{
			rwl.readLock().unlock();
		}

		rwl.writeLock().lock();
		try
		{
			updateStub();
			return stub;
		}
		finally
		{
			rwl.writeLock().unlock();
		}
	}

	/**
	 * Обновить стаб
	 */
	protected abstract void updateStub();

	/**
	 * Проверка актуальности свойств стаба
	 * @return false - необходимо обновить свойства стаба, true - не надо
	 */
	protected abstract boolean isActualStub();

	protected void setStub(S stub)
	{
		this.stub = stub;
	}
}
