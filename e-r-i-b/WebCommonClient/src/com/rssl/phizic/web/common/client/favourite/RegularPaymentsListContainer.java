package com.rssl.phizic.web.common.client.favourite;

import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.web.common.client.ext.sbrf.LongOfferUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author tisov
 * @ created 10.04.15
 * @ $Author$
 * @ $Revision$
 * ��������������� ����� ��� ����������� ������ ������������
 */
class RegularPaymentsListContainer
{
	private List<EditableExternalResourceLink> payments;    //������ ���� ������������


	RegularPaymentsListContainer(List<EditableExternalResourceLink> regularPayments)
	{
		this.payments = regularPayments;
	}

	/**
	 * �������� ������ �������� �����������
	 * @return ������ �������� ������������
	 */
	public List<EditableExternalResourceLink> getOnlyActualPayments()
	{
		return LongOfferUtils.getOnlyActivePayments(payments);
	}

	/**
	 * �������� ������ ���������������� �����������
	 * @return ������ ���������������� ������������
	 */
	public List<EditableExternalResourceLink> getOnlySuspendedPayments()
	{
		return LongOfferUtils.getOnlySuspendedPayments(payments);
	}

	/**
	 * �������� ������ ��������� ������������� �������
	 * @return ������ ��������� ������������� ��������
	 */
	public List<EditableExternalResourceLink> getOnlyWaitingPayments()
	{
		return LongOfferUtils.getOnlyWaitingConfirmPayments(payments);
	}

	/**
	 * �������� ����������� �� ������
	 * @return ������ ������������ �� ������
	 */
	public List<EditableExternalResourceLink> getOnlyArchivePayments()
	{
		return LongOfferUtils.getOnlyArchivePayments(payments);
	}

	/**
	 * @return ������ ���� ��������
	 */
	public List<EditableExternalResourceLink> getPayments()
	{
		return payments;
	}

	/**
	 * �������� ��������� ��������, �������� � ��������� ���� � ���
	 * @return ��� �������� � ���� � ��� ������������
	 */
	public Set<EditableExternalResourceLink> getActiveSet()
	{
		Set<EditableExternalResourceLink> activeSet = new HashSet<EditableExternalResourceLink>();
		List<EditableExternalResourceLink> onlyActualPaymentsForAPIAndATM = LongOfferUtils.getOnlyActivePaymentsForAPIAndATM(this.payments);
		for (EditableExternalResourceLink link: onlyActualPaymentsForAPIAndATM)
		{
			activeSet.add(link);
		}
		return activeSet;
	}
}