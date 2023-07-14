package com.rssl.phizic.business.ext.sbrf.commissions;

/**
 * @author vagin
 * @ created 24.09.13
 * @ $Author$
 * @ $Revision$
 * ��������� ����������� ���� ������������� � �������.
 */
public class CommissionsTBSetting
{
	private Long id;
	private String TB;          //����� ��, � ������� �������� �������� ���������
	private String paymentType; //��� ��������� GateDocument.getType()
    private boolean show;       //���������� �������� � ������� ��� �������� � �������� ��������(������������ � ���?)
	private boolean showRub;    //���������� �������� � ������� ��� �������� � ��������� ������� �������� (������������ � ���?)

	/**
	 * ����������� ��������� ����������� ���� �������� � �������.
	 * @param TB  - ����� ��
	 * @param paymentType - ��� �������(�������� ���)
	 * @param show - true/false(����������/�� ��������� ����� ��������)
	 */
	public CommissionsTBSetting(String TB, String paymentType, boolean show, boolean showRub)
	{
		this.TB = TB;
		this.paymentType = paymentType;
		this.show = show;
		this.showRub = showRub;
	}

	public CommissionsTBSetting() {}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPaymentType()
	{
		return paymentType;
	}

	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}

	public boolean isShow()
	{
		return show;
	}

	public void setShow(boolean show)
	{
		this.show = show;
	}

	public String getTB()
	{
		return TB;
	}

	public void setTB(String TB)
	{
		this.TB = TB;
	}

	public boolean isShowRub()
	{
		return showRub;
	}

	public void setShowRub(boolean showRub)
	{
		this.showRub = showRub;
	}
}
