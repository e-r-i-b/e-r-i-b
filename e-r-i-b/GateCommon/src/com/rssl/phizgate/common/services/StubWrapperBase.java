package com.rssl.phizgate.common.services;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ������� ����� ������� ����� ����������������� ������ ��� ���������� �������
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
	 * @return ����
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
	 * �������� ����
	 */
	protected abstract void updateStub();

	/**
	 * �������� ������������ ������� �����
	 * @return false - ���������� �������� �������� �����, true - �� ����
	 */
	protected abstract boolean isActualStub();

	protected void setStub(S stub)
	{
		this.stub = stub;
	}
}
