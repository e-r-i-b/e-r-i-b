package com.rssl.phizgate.common.providers;

import java.util.Calendar;

/**
 * �������� ����������
 * @author gladishev
 * @ created 14.01.2015
 * @ $Author$
 * @ $Revision$
 */

public class ProviderPropertiesEntry
{
	private long providerId;
	private boolean mbCheckEnabled;
	private boolean mcheckoutDefaultEnabled;
	private boolean einvoiceDefaultEnabled;
	private boolean mbCheckDefaultEnabled;
	private Calendar updateDate;
	private boolean useESB;

	/**
	 * ������������� ���������� �����
	 * @return
	 */
	public long getProviderId()
	{
		return providerId;
	}

	/**
	 * ���������� ������������� ���������� �����
	 * @param providerId - ������������� ���������� �����
	 */
	public void setProviderId(long providerId)
	{
		this.providerId = providerId;
	}

	/**
	 * @return ���� ����������
	 */
	public Calendar getUpdateDate()
	{
		return updateDate;
	}

	/**
	 * ���������� ���� ����������
	 * @param updateDate - ���� ����������
	 */
	public void setUpdateDate(Calendar updateDate)
	{
		this.updateDate = updateDate;
	}

	/**
	 * @return �������� �������� ����������� ��-��������� mobile checkout ��� ����������� ���
	 */
	public boolean isMbCheckDefaultEnabled()
	{
		return mbCheckDefaultEnabled;
	}

	/**
	 * ���������� �������� �������� ����������� ��-��������� mobile checkout ��� ����������� ���
	 * @param mbCheckDefaultEnabled - �������� �������� ����������� ��-��������� mobile checkout ��� ����������� ���
	 */
	public void setMbCheckDefaultEnabled(boolean mbCheckDefaultEnabled)
	{
		this.mbCheckDefaultEnabled = mbCheckDefaultEnabled;
	}

	/**
	 * @return �������� �������� ����������� ��-��������� einvoicing ��� ����������� ���
	 */
	public boolean isEinvoiceDefaultEnabled()
	{
		return einvoiceDefaultEnabled;
	}

	/**
	 * ���������� �������� �������� ����������� ��-��������� einvoicing ��� ����������� ���
	 * @param einvoiceDefaultEnabled - �������� �������� ����������� ��-��������� einvoicing ��� ����������� ���
	 */
	public void setEinvoiceDefaultEnabled(boolean einvoiceDefaultEnabled)
	{
		this.einvoiceDefaultEnabled = einvoiceDefaultEnabled;
	}

	/**
	 * @return �������� �������� ����������� ��-��������� �������� �� ��� ����������� ���
	 */
	public boolean isMcheckoutDefaultEnabled()
	{
		return mcheckoutDefaultEnabled;
	}

	/**
	 * ���������� �������� �������� "����������� ��-��������� �������� �� ��� ����������� ���"
	 * @param mcheckoutDefaultEnabled
	 */
	public void setMcheckoutDefaultEnabled(boolean mcheckoutDefaultEnabled)
	{
		this.mcheckoutDefaultEnabled = mcheckoutDefaultEnabled;
	}

	/**
	 * @return �������� �������� "��������� �������� ��"
	 */
	public boolean isMbCheckEnabled()
	{
		return mbCheckEnabled;
	}

	/**
	 * ���������� ������� "��������� �������� ��"
	 * @param mbCheckEnabled - �������� ��������
	 */
	public void setMbCheckEnabled(boolean mbCheckEnabled)
	{
		this.mbCheckEnabled = mbCheckEnabled;
	}

	/**
	 * ���������� �������� �������� "������ ����� ���"
	 * @return �������� ��������
	 */
	public boolean isUseESB()
	{
		return useESB;
	}

	/**
	 * ���������� ������� "������ ����� ���"
	 * @param useESB - �������� ��������
	 */
	public void setUseESB(boolean useESB)
	{
		this.useESB = useESB;
	}
}
