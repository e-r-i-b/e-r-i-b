package com.rssl.phizic.gate.payments.longoffer;

import com.rssl.phizic.gate.longoffer.autopayment.AlwaysAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.InvoiceAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;

/**
 * @author krenev
 * @ created 29.04.2011
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ��������� �� ������� � ����� �� ����������� ����������
 */
public interface CardPaymentSystemPaymentLongOffer extends CardPaymentSystemPayment, AutoSubscriptionClaim
{
	/**
	 * ����� ��� ����������� �����������(���� null ������ ����������)
	 * @return
	 */
	public AlwaysAutoPayScheme getAlwaysAutoPayScheme();

	/**
	 * ���������� ����� ��� ����������� �����������
	 * @param scheme - �����
	 */
	public void setAlwaysAutoPayScheme(AlwaysAutoPayScheme scheme);

	/**
	 * ����� ��� ���������� �����������(���� null ������ ����������)
	 * @return
	 */
	public ThresholdAutoPayScheme getThresholdAutoPayScheme();

	/**
	 * ���������� ����� ��� �������� �����������
	 * @param scheme - �����
	 */
	public void setThresholdAutoPayScheme(ThresholdAutoPayScheme scheme);

	/**
	 * ����� ��� ����������� �� ������������� �����(���� null ������ ����������)
	 * @return
	 */
	public InvoiceAutoPayScheme getInvoiceAutoPayScheme();

	/**
	 * ���������� ����� ��� ����������� �� ������������� �����
	 * @param scheme - �����
	 */
	public void setInvoiceAutoPayScheme(InvoiceAutoPayScheme scheme);

	/**
	 * ������� ������� �������� ��� ������������� �������� �� �������� �� ���������� � ������������ � �������� �����.
	 * @return true - �������� ����������
	 */
	public boolean isWithCommision();

	/**
	 * ���������� ������� ������� �������� ��� ������������� �������� �� �������� �� ���������� � ������������ � �������� �����
	 * @param withCommision true - �������� ���������� 
	 */
	public void setWithCommision(boolean withCommision);

	/**
	 * @return ��� ������ �����
	 */
	String getGroupService();

	/**
	 * ���������� ��� ������ �����
	 * @param groupService ��� ������ �����
	 */
	void setGroupService(String groupService);

	/**
	 * @return ������� ������������� ������������, ������� ������������������
	 */
	String getLongOfferExternalId();
}
