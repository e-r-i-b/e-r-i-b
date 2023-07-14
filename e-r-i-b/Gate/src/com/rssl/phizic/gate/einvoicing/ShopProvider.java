package com.rssl.phizic.gate.einvoicing;

import com.rssl.phizic.common.types.Entity;

/**
 * ��������� ����� ��� ������������ ��������-�������.
 *
 * @author bogdanov
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */

public interface ShopProvider extends Entity
{
	/**
	 * @return url �������� ����� ������.
	 */
	public String getBackUrl();

	/**
	 * @return ��� ��������.
	 */
	public String getCodeRecipientSBOL();

	/**
	 * @return ��� �����.
	 */
	public String getFormName();

	/**
	 * @return url ��� �������� �����.
	 */
	public String getUrl();

	/**
	 * @return �������� ����� ������.
	 */
	public boolean isAfterAction();

	/**
	 * @return �������� �� MobileCheckout.
	 */
	public boolean isAvailableMobileCheckout();

	/**
	 * @return �������� ������ ����� �������.
	 */
	public boolean isCheckOrder();

	/**
	 * @return �������� �� ��������� �������������.
	 */
	public boolean isFacilitator();

	/**
	 * @return ������� ������������ ����������.
	 */
	public boolean isFederal();

	/**
	 * @return ���������� � ��������-������� ���������� � ����� ��������.
	 */
	public boolean isSendChargeOffInfo();
}
