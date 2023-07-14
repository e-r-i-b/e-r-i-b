package com.rssl.phizic.gate.einvoicing;

import com.rssl.phizic.common.types.Money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * ���������� � ������.
 *
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public interface ShopOrder extends Serializable
{
	/**
	* @return ������������� (PK)
	*/
	public Long getId();

	/**
	* @return ������� ������������� ������ � ����
	*/
	public String getUuid();

	/**
	* @return ���������� ������������� ������ � ����������
	*/
	public String getExternalId();

	/**
	* @return Partial, full,offline
	*/
	public TypeOrder getType();

	/**
	* @return ���� ����������� ������
	*/
	public Calendar getDate();

	/**
	* @return ���������� ������ ������
	*/
	public OrderState getState();

	/**
	* @return ������� �������, ���������� � ������ �������
	*/
	public ShopProfile getProfile();

	/**
	* @return ����� �������� ������� ��� MobileCheckout
	*/
	public String getPhone();

	/**
	* @return ��� ����������
	*/
	public String getReceiverCode();

	/**
	* @return ������������ ���������� (��� ����������� ������������)
	*/
	public String getReceiverName();

	/**
	* @return ����� ������.
	*/
	public Money getAmount();

	/**
	* @return �������� ������
	*/
	public String getDescription();

	/**
	* @return ����� ����� ����������
	*/
	public String getReceiverAccount();

	/**
	* @return ��� ����� ����������
	*/
	public String getBic();

	/**
	* @return ���. ���� ����� ����������
	*/
	public String getCorrAccount();

	/**
	* @return ��� ����������
	*/
	public String getInn();

	/**
	* @return ��� ����������
	*/
	public String getKpp();

	/**
	* @return ���������� � ������ ��� �������� �����
	*/
	public String getPrintDescription();

	/**
	* @return url ��������� ��� �������� ����� ������ ������ (��� partial/full)
	*/
	public String getBackUrl();

	/**
	* @return ����� �����, � ������� ������������ ������ ������
	*/
	public Long getNodeId();

	/**
	* @return ������������� ��������� ������ ������ � ��������
	*/
	public String getUtrrno();

	/**
	 * @return ��� ������ (��������-�������, ��������)
	 */
	public OrderKind getKind();

	/**
	 * @return �������� �� �����, ������� ����� MC.
	 */
	public boolean isMobileCheckout();

	public Calendar getDelayedPayDate();

	public Boolean getIsNew();

	public void setIsNew(Boolean isNew);

	/**
	 * @return ��� ��������� ���������� �� �������������
	 */
	public String getFacilitatorProviderCode();
}
