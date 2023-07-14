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

public class RemoteRetailWrapper implements RetailWrapper
{
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private Retail retail;
	private String host;
	private int port;
	private int version;

	/**
	 * �����������
	 * @param host - ����
	 * @param port - ����
	 * @param version - ������
	 */
	public RemoteRetailWrapper(String host, int port, int version) throws Exception
	{
		this.host = host;
		this.port = port;
		this.version = version;
		retail = Retail.Factory.getInstance(port, host, version);
	}

	/**
	 * ������������ ������� ������
	 * @param host - ����
	 * @param port - ����
	 * @param version - ������
	 */
	public void activate(String host, int port, int version) throws Exception
	{
		rwl.readLock().lock();
		try
		{
			if (!needActivate(host, port, version))
				return;
		}
		finally
		{
			rwl.readLock().unlock();
		}

		rwl.writeLock().lock();
		try
		{
			if (!needActivate(host, port, version))
				return;

			this.host = host;
			this.port = port;
			this.version = version;
			retail = Retail.Factory.getInstance(port, host, version);
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

	private boolean needActivate(String host, int port, int version)
	{
		return !this.host.equals(host) || this.port != port || this.version != version;
	}
}
