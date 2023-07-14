package com.rssl.phizic.gate.einvoicing;

/**
 * �������� ��������� ����� (���������, ������� �� �������������)
 * @author gladishev
 * @ created 24.12.2014
 * @ $Author$
 * @ $Revision$
 */

public class FacilitatorProvider
{
	private Long id;
	private String facilitatorCode;
	private String code;
	private String name;
	private String inn;
	private String url;
	private boolean deleted;
	private boolean mobileCheckoutEnabled;
	private boolean einvoiceEnabled;
	private boolean mbCheckEnabled;

	/**
	 * @return ������������� ������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ���������� ������������� ������
	 * @param id - ������������� ������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��� ������������
	 */
	public String getFacilitatorCode()
	{
		return facilitatorCode;
	}

	/**
	 * ���������� ��� ������������
	 * @param facilitatorCode - ��� ������������
	 */
	public void setFacilitatorCode(String facilitatorCode)
	{
		this.facilitatorCode = facilitatorCode;
	}

	/**
	 * @return ��� ���������� �����
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * ���������� ��� ���������� �����
	 * @param code - ��� ���������� �����
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @return ������������ ����������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ���������� ������������ ����������
	 * @param name - ������������ ����������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ��� ����������
	 */
	public String getInn()
	{
		return inn;
	}

	/**
	 * ���������� ��� ����������
	 * @param inn - ��� ����������
	 */
	public void setInn(String inn)
	{
		this.inn = inn;
	}

	/**
	 * @return ��� ����������
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * ���������� ��� ����������
	 * @param url - ��� ����������
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * @return true - ��������� ������
	 */
	public boolean isDeleted()
	{
		return deleted;
	}

	/**
	 * ���������� ������� ���������� ����������
	 * @param deleted - ������� ���������� ����������
	 */
	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}

	/**
	 * @return ����������� �� mobile-checkout
	 */
	public boolean isMobileCheckoutEnabled()
	{
		return mobileCheckoutEnabled;
	}

	/**
	 * ���������� ������� ����������� �� mobile-checkout
	 * @param mobileCheckoutEnabled - ������� ����������� �� mobile-checkout
	 */
	public void setMobileCheckoutEnabled(boolean mobileCheckoutEnabled)
	{
		this.mobileCheckoutEnabled = mobileCheckoutEnabled;
	}

	/**
	 * @return ����������� �� e-invoicing
	 */
	public boolean isEinvoiceEnabled()
	{
		return einvoiceEnabled;
	}

	/**
	 * ���������� ������� ����������� �� e-invoicing
	 * @param einvoiceEnabled - ������� ����������� �� e-invoicing
	 */
	public void setEinvoiceEnabled(boolean einvoiceEnabled)
	{
		this.einvoiceEnabled = einvoiceEnabled;
	}

	/**
	 * @return ����������� ��� �������� � ��
	 */
	public boolean isMbCheckEnabled()
	{
		return mbCheckEnabled;
	}

	/**
	 * ���������� ������� ����������� ��� �������� � ��
	 * @param mbCheckEnabled - ������� �����������
	 */
	public void setMbCheckEnabled(boolean mbCheckEnabled)
	{
		this.mbCheckEnabled = mbCheckEnabled;
	}
}
