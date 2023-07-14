package com.rssl.phizgate.common.messaging.retail.jni.pool;

import com.rssl.api.retail.Retail;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ������� ��� ��������� jni ������ � ��������� ������
 * @author gladishev
 * @ created 23.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class LocateRetailWrapper implements RetailWrapper
{
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private Retail retail;
	private String location;
	private int version;

	/**
	 * �����������
	 * @param location - �����
	 * @param version - ������
	 */
	public LocateRetailWrapper(String location, int version) throws Exception
	{
		this.location = location;
		this.version = version;
		retail = Retail.Factory.getInstance(location, version);
	}

	/**
	 * ������������ ������� ������
	 * @param location - �����
	 * @param version - ������
	 */
	public void activate(String location, int version) throws Exception
	{
		rwl.readLock().lock();
		try
		{
			if (!needActivate(location, version))
				return;
		}
		finally
		{
			rwl.readLock().unlock();
		}

		rwl.writeLock().lock();
		try
		{
			if (!needActivate(location, version))
				return;

			this.location = location;
			this.version = version;
			retail = Retail.Factory.getInstance(location, version);
		}
		finally
		{
			rwl.writeLock().unlock();
		}
	}

	public Retail getRetail()
	{
		return retail;
	}

	private boolean needActivate(String location, int version)
	{
		return !this.location.equals(location) || this.version != version;
	}
}
