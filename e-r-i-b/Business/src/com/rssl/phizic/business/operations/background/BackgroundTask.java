package com.rssl.phizic.business.operations.background;

import com.rssl.phizic.business.BusinessException;

import java.util.Calendar;
import java.util.Properties;

/**
 * ��������� ������� ������
 * ���������� ��� ���������� ����������� ���������� ������ ������������ ���� ��������.
 */
public interface BackgroundTask<R extends TaskResult>
{

	/**
	 * @return ������������� ������
	 */
	public Long getId();

	/**
	 * @return ������������� ���������/���������� ������� ������
	 */
	public Long getOwnerId();

	/**
	 * @return ���� �������� ������
	 */
	public Calendar getCreationDate();

	/**
	 * @return ������ ������
	 */
	public TaskState getState();

	/**
	 * @return ����������� ��������� ����������
	 */
	public Properties getProperties() throws BusinessException;

	/**
	 * �������� ��������� ���������� ������
	 * ���� ������ ��� �� ����������� ������������ null
	 * @return ���������� ����������
	 */
	public R getResult();

	/**
	 * �����, ����������� ������ ���, ��� �� ��������� ���������.
	 * ���������� ������ ������ ������������ �� ������� ����������� ���������� ������,
	 * ������� �� ����� ��� ��������� ������(��������, ����� ��� ����������).
	 * ����� ������ ����� ������ ��������� ������ � ��������� ����������
	 * @param result ��������� ��������� ������ �� ����� ���� null
	 */
	public void executed(R result);

	/**
	 * ����� ������������ �������� ��������, �� ������ ������� ���� ������� ������� ������
	 * @return �������� ��������
	 */
	public String getOperationClassName();
}
