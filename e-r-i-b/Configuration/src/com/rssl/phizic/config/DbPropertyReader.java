package com.rssl.phizic.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ������ ������ ������ �������� ������� �� ����, ���� ��� ����������� �� ������� �� �������� �����
 * @author Roshka
 * @ created 06.02.2006
 * @ $Author: bogdanov $
 * @ $Revision: 56916 $
 */
public class DbPropertyReader extends PropertyReader
{
	private final String dbInstance;

	/**
	 * ������� � ���, ��� �������� ����������. � ���� ������ �� ������ ���������� ���������.
	 */
	private volatile boolean isUpdating = false;

	/**
	 * ��������� ����� ���������� ������..
	 */
	private final AtomicLong lastUpdateTime = new AtomicLong(0L);

	private long updatePeriod = -1;

	/**
	 * �������������� ���������� ������.
	 */
	synchronized void sendRefresh()
	{
		lastUpdateTime.set(0);
	}

	public String getFileName()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * @param category ��������� � �� (��� �� ��� ������� � class path, ���� ������ � ����� ������ ���������� �� ������������ ������ ��)
	 * @param dbInstance - ��� ��������� ��
	 */
	protected DbPropertyReader(String category, String dbInstance)
	{
		super(category);
		this.dbInstance = dbInstance;
	}

	protected Map<String, String> doRefresh()
	{
		//����� �������� ���������. ��������� ���: ���� ��������� ��� ����������� (�� ����� ���������� �������� ������ ������ ����� ������ ���������)
		//���� �� ��� �� ���� �� ��������� ��������� (��������� ��� ������ �� ����� ����������� ��������).
		isUpdating = lastUpdateTime.get() > 0;
		try
		{
			Map<String, String> temp = new HashMap<String, String>();
			List<Property> list = DbPropertyService.findProperties(category, dbInstance);
			for (Property property : list)
				temp.put(property.getKey(), property.getValue());

			lastUpdateTime.set(System.currentTimeMillis());
			return temp;
		}
		catch (Exception e)
		{
			throw new ConfigurationException(e.getMessage(), e);
		}
		finally
		{
			isUpdating = false;
		}
	}


	@Override
	protected boolean needUpdate(long configLastUpdateTime)
	{
		if (needUpdateReader())
			return true;

		return configLastUpdateTime < lastUpdateTime.get();
	}

	@Override
	protected boolean needUpdateReader()
	{
		if (isUpdating)
			return false;

		long lstUpdateTime = lastUpdateTime.get();
		return lstUpdateTime <= 0 || (0 < updatePeriod &&  lstUpdateTime + updatePeriod < System.currentTimeMillis()) || super.needUpdateReader();
	}

	/**
	 * @param periodForUpdate ������ ���������� ������.
	 */
	void setUpdatePeriod(int periodForUpdate)
	{
		updatePeriod = periodForUpdate;
	}
}
