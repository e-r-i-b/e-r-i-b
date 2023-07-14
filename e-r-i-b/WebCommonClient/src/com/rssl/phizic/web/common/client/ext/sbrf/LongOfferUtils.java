package com.rssl.phizic.web.common.client.ext.sbrf;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.client.favourite.LongOfferByDateComparator;
import com.rssl.phizic.web.common.client.favourite.LongOfferByOrderComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ������� ��� ������ � ����������� ��������� � �������������.
 *
 * @author bogdanov
 * @ created 10.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class LongOfferUtils
{
	/**
	 * �������� ������ ������ ������������, � ������� ��� ������� ���� �� ����������: ��������, �������� ����������, �����
	 * ��������, �� ���� ��� ��� ������, �� ������������ ����������� ������ �������. ��������� �� jsp ������, �� �����
	 * ����� ����� �������� ��������
	 * @param allLinks ������ ���� ������ ������������
	 * @return ������ ������, ������� ����� ����������
	 */
	public static List<AutoPaymentLink> getNotEmptyAutoPaymentLinks(List<AutoPaymentLink> allLinks)
	{
		List<AutoPaymentLink> notEmptyLinks = new ArrayList<AutoPaymentLink>(allLinks.size());
		for (AutoPaymentLink link : allLinks)
		{
			AutoPayment payment = link.getValue();
			if (!StringHelper.isEmpty(link.getName()) || !StringHelper.isEmpty(link.getExecutionEventType()) || (payment != null && payment.getAmount() != null))
				notEmptyLinks.add(link);
		}
		return notEmptyLinks;
	}

	public static List<LongOfferLink> getNotEmptyLongOfferLinks(List<LongOfferLink> allLinks) throws BusinessException, BusinessLogicException
	{
		List<LongOfferLink> notEmptyLinks = new ArrayList<LongOfferLink>(allLinks.size());
		for (LongOfferLink link : allLinks)
		{
			LongOffer payment = link.getValue();
			if (!StringHelper.isEmpty(link.getName()) || !StringHelper.isEmpty(link.getExecutionEventType()) || payment.getAmount() != null)
				notEmptyLinks.add(link);
		}
		return notEmptyLinks;
	}

	/**
	 * �������� �� ����������.
	 *
	 * @param payment ����������, ���������� ��������� ��� ���������� ������.
	 * @return �������� ��.
	 */
	public static boolean isActiveAutoPayment(ExternalResourceLink payment)
	{
		if (payment instanceof AutoPaymentLink)
		{
			AutoPaymentLink autoPaymentLink = (AutoPaymentLink) payment;
			AutoPayment autoPayment = autoPaymentLink.getValue();
			//���� �� ����� �� �������� �� ������� �������� ����������-�� �� ��������.
			if(autoPayment == null)
				return false;
			AutoPaymentStatus status = autoPayment.getReportStatus();
			return (status == AutoPaymentStatus.ACTIVE || status == AutoPaymentStatus.UPDATING);
		}
		else if (payment instanceof AutoSubscriptionLink)
		{
			AutoSubscriptionLink autoSubscriptionLink = (AutoSubscriptionLink) payment;
			AutoPayStatusType status = autoSubscriptionLink.getValue().getAutoPayStatusType();
			return (status == AutoPayStatusType.Active || status == AutoPayStatusType.Confirmed);
		}
		else
		{
			return true;
		}
	}

	/**
	 * �������� �� ���������� ��� API � ATM.
	 *
	 * @param payment ����������, ���������� ��������� ��� ���������� ������.
	 * @return �������� ��.
	 */
	public static boolean isActiveAutoPaymentForAPIAndATM(ExternalResourceLink payment)
	{
		if (payment instanceof AutoPaymentLink)
		{
			AutoPaymentLink autoPaymentLink = (AutoPaymentLink) payment;
			AutoPayment autoPayment = autoPaymentLink.getValue();
			//���� �� ����� �� �������� �� ������� �������� ����������-�� �� ��������.
			if(autoPayment == null)
				return false;
			AutoPaymentStatus status = autoPayment.getReportStatus();
			return !(status == AutoPaymentStatus.BLOCKED ||
					status == AutoPaymentStatus.DELETED ||
					status == AutoPaymentStatus.NO_CREATE);
		}
		else if (payment instanceof AutoSubscriptionLink)
		{
			AutoSubscriptionLink autoSubscriptionLink = (AutoSubscriptionLink) payment;
			AutoPayStatusType status = autoSubscriptionLink.getValue().getAutoPayStatusType();
			return status == AutoPayStatusType.Active;
		}
		else
		{
			return true;
		}
	}

	/**
	 * ������������� �� ����������.
	 *
	 * @param payment ����������, ���������� ��������� ��� ���������� ������.
	 * @return ������������� ��.
	 */
	public static boolean isSuspendedAutoPayment(ExternalResourceLink payment)
	{
		if (payment instanceof AutoSubscriptionLink)
		{
			AutoSubscriptionLink autoSubscriptionLink = (AutoSubscriptionLink) payment;
			AutoPayStatusType status = autoSubscriptionLink.getValue().getAutoPayStatusType();
			return (status == AutoPayStatusType.Paused);
		}
		else
		{
			return false;
		}
	}

	/**
	 * ������� �� ������������� ����������.
	 *
	 * @param payment ����������, ���������� ��������� ��� ���������� ������.
	 * @return ������� ������������� ��.
	 */
	public static boolean isWaitingConfirmAutoPayment(ExternalResourceLink payment)
	{
		if (payment instanceof AutoPaymentLink)
		{
			AutoPaymentLink autoPaymentLink = (AutoPaymentLink) payment;
			AutoPayment autoPayment = autoPaymentLink.getValue();
			//���� �� ����� �� �������� �� ������� �������� ����������-�� �� ��������.
			if(autoPayment == null)
				return false;
			AutoPaymentStatus status = autoPayment.getReportStatus();
			return (status == AutoPaymentStatus.NEW);
		}
		else if (payment instanceof AutoSubscriptionLink)
		{
			AutoSubscriptionLink autoSubscriptionLink = (AutoSubscriptionLink) payment;
			AutoPayStatusType status = autoSubscriptionLink.getValue().getAutoPayStatusType();
			return (status == AutoPayStatusType.New || status == AutoPayStatusType.OnRegistration || status == AutoPayStatusType.WaitForAccept);
		}
		else
		{
			return false;
		}
	}

	/**
	 * �������� �� ����������.
	 *
	 * @param payment ����������, ���������� ��������� ��� ���������� ������.
	 * @return �������� ��.
	 */
	public static boolean isArchiveAutoPayment(ExternalResourceLink payment)
	{
		if (payment instanceof AutoPaymentLink)
		{
			AutoPaymentLink autoPaymentLink = (AutoPaymentLink) payment;
			AutoPayment autoPayment = autoPaymentLink.getValue();
			//���� �� ����� �� �������� �� ������� �������� ����������-�� �� ��������.
			if(autoPayment == null)
				return false;
			AutoPaymentStatus status = autoPayment.getReportStatus();

			return (status == AutoPaymentStatus.BLOCKED || status == AutoPaymentStatus.DELETED);
		}
		else if (payment instanceof AutoSubscriptionLink)
		{
			AutoSubscriptionLink autoSubscriptionLink = (AutoSubscriptionLink) payment;
			AutoPayStatusType status = autoSubscriptionLink.getValue().getAutoPayStatusType();
			return (status == AutoPayStatusType.Closed);
		}
		else
		{
			return false;
		}
	}

	public static List<EditableExternalResourceLink> sortPaymentsByDate(List<EditableExternalResourceLink> payments)
	{
		LongOfferByDateComparator comparator = new LongOfferByDateComparator();
		Collections.sort(payments, comparator);
		return payments;
	}

	/**
	 * ��������� ������ ������������ �� ������� �����������.
	 * @param payments - ������ ������������.
	 * @return ��������������� ������ ������������.
	 */
	public static List<EditableExternalResourceLink> sortPaymentsByOrder(List<EditableExternalResourceLink> payments) throws BusinessException
	{
		LongOfferByOrderComparator comparator = new LongOfferByOrderComparator();
		Collections.sort(payments, comparator);
		return payments;
	}

	/**
	 * ���������� ������ �������� ������������.
	 *
	 * @param payments �������.
	 * @return �������� �������.
	 */
	public static List<EditableExternalResourceLink> getOnlyActivePayments(List<EditableExternalResourceLink> payments)
	{
		List<EditableExternalResourceLink> onlyActivePayments = new ArrayList<EditableExternalResourceLink>(payments.size());
		for (EditableExternalResourceLink link : payments)
		{
			if (isActiveAutoPayment(link))
			{
				onlyActivePayments.add(link);
			}
		}
		return onlyActivePayments;
	}

	/**
	 * ���������� ������ �������� ������������ ��� API � ATM.
	 *
	 * @param payments �������.
	 * @return �������� �������.
	 */
	public static List<EditableExternalResourceLink> getOnlyActivePaymentsForAPIAndATM(List<EditableExternalResourceLink> payments)
	{
		List<EditableExternalResourceLink> onlyActivePaymentsForAPIAndATM = new ArrayList<EditableExternalResourceLink>(payments.size());
		for (EditableExternalResourceLink link : payments)
		{
			if (isActiveAutoPaymentForAPIAndATM(link))
			{
				onlyActivePaymentsForAPIAndATM.add(link);
			}
		}
		return onlyActivePaymentsForAPIAndATM;
	}

	/**
	 * ���������� ������ ���������������� ������������.
	 *
	 * @param payments �������.
	 * @return ���������������� �����������.
	 */
	public static List<EditableExternalResourceLink> getOnlySuspendedPayments(List<EditableExternalResourceLink> payments)
	{
		List<EditableExternalResourceLink> onlySuspendedPayments = new ArrayList<EditableExternalResourceLink>(payments.size());
		for (EditableExternalResourceLink link : payments)
		{
			if (isSuspendedAutoPayment(link))
			{
				onlySuspendedPayments.add(link);
			}
		}
		return onlySuspendedPayments;
	}

   	/**
	 * ���������� ������ ��������� ������������� ������������.
	 *
	 * @param payments �������.
	 * @return ��������� ������������� �����������.
	 */
	public static List<EditableExternalResourceLink> getOnlyWaitingConfirmPayments(List<EditableExternalResourceLink> payments)
	{
		List<EditableExternalResourceLink> onlyWaitingConfirmPayments = new ArrayList<EditableExternalResourceLink>(payments.size());
		for (EditableExternalResourceLink link : payments)
		{
			if (isWaitingConfirmAutoPayment(link))
			{
				onlyWaitingConfirmPayments.add(link);
			}
		}
		return onlyWaitingConfirmPayments;
	}

	/**
	 * ���������� ������ ������� ������������.
	 *
	 * @param payments �������.
	 * @return ������-�����������.
	 */
	public static List<EditableExternalResourceLink> getOnlyArchivePayments(List<EditableExternalResourceLink> payments)
	{
		List<EditableExternalResourceLink> onlyArchivePayments = new ArrayList<EditableExternalResourceLink>(payments.size());
		for (EditableExternalResourceLink link : payments)
		{
			if (isArchiveAutoPayment(link))
			{
				onlyArchivePayments.add(link);
			}
		}
		return onlyArchivePayments;
	}

	/**
	 * �������� ������ �����������
	 */
	public static List<AutoPaymentLink> getAllAutoPayments(List<EditableExternalResourceLink> payments)
	{
		List<AutoPaymentLink> onlyActualPayments = new ArrayList<AutoPaymentLink>(payments.size());
		for (EditableExternalResourceLink link : payments)
		{
			if (link instanceof AutoPaymentLink)
			{
				onlyActualPayments.add((AutoPaymentLink) link);	
			}
		}
		return onlyActualPayments;
	}
}
