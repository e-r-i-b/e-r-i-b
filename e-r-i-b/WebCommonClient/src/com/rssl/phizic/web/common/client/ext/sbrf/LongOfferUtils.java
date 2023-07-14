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
 * Утилиты для работы с регулярными платежами и автоплатежами.
 *
 * @author bogdanov
 * @ created 10.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class LongOfferUtils
{
	/**
	 * Получить список линков автоплатежей, у которых как минимум один из параметров: название, алгоритм исполнения, сумма
	 * заполнен, тк если они все пустые, то пользователю отобразится пустая строчка. Проверять на jsp нельзя, тк тогда
	 * будет криво работать пэйджинг
	 * @param allLinks список всех линков автоплатежей
	 * @return список линков, которые можно отображать
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
	 * Активный ли автоплатеж.
	 *
	 * @param payment автоплатеж, длительное поручение или автоплатеж шинный.
	 * @return активный ли.
	 */
	public static boolean isActiveAutoPayment(ExternalResourceLink payment)
	{
		if (payment instanceof AutoPaymentLink)
		{
			AutoPaymentLink autoPaymentLink = (AutoPaymentLink) payment;
			AutoPayment autoPayment = autoPaymentLink.getValue();
			//если по каким то причинам не удалось получить автоплатеж-он не активный.
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
	 * Активный ли автоплатеж для API и ATM.
	 *
	 * @param payment автоплатеж, длительное поручение или автоплатеж шинный.
	 * @return активный ли.
	 */
	public static boolean isActiveAutoPaymentForAPIAndATM(ExternalResourceLink payment)
	{
		if (payment instanceof AutoPaymentLink)
		{
			AutoPaymentLink autoPaymentLink = (AutoPaymentLink) payment;
			AutoPayment autoPayment = autoPaymentLink.getValue();
			//если по каким то причинам не удалось получить автоплатеж-он не активный.
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
	 * Приостановлен ли автоплатеж.
	 *
	 * @param payment автоплатеж, длительное поручение или автоплатеж шинный.
	 * @return приостановлен ли.
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
	 * Ожидает ли подтверждения автоплатеж.
	 *
	 * @param payment автоплатеж, длительное поручение или автоплатеж шинный.
	 * @return ожидает подтверждения ли.
	 */
	public static boolean isWaitingConfirmAutoPayment(ExternalResourceLink payment)
	{
		if (payment instanceof AutoPaymentLink)
		{
			AutoPaymentLink autoPaymentLink = (AutoPaymentLink) payment;
			AutoPayment autoPayment = autoPaymentLink.getValue();
			//если по каким то причинам не удалось получить автоплатеж-он не активный.
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
	 * Архивный ли автоплатеж.
	 *
	 * @param payment автоплатеж, длительное поручение или автоплатеж шинный.
	 * @return архивный ли.
	 */
	public static boolean isArchiveAutoPayment(ExternalResourceLink payment)
	{
		if (payment instanceof AutoPaymentLink)
		{
			AutoPaymentLink autoPaymentLink = (AutoPaymentLink) payment;
			AutoPayment autoPayment = autoPaymentLink.getValue();
			//если по каким то причинам не удалось получить автоплатеж-он не активный.
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
	 * Сортирует список автоплатежей по порядку отображения.
	 * @param payments - список автоплатежей.
	 * @return отоортированный список автоплатежей.
	 */
	public static List<EditableExternalResourceLink> sortPaymentsByOrder(List<EditableExternalResourceLink> payments) throws BusinessException
	{
		LongOfferByOrderComparator comparator = new LongOfferByOrderComparator();
		Collections.sort(payments, comparator);
		return payments;
	}

	/**
	 * Возвращает список активных автоплатежей.
	 *
	 * @param payments платежи.
	 * @return активные платежи.
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
	 * Возвращает список активных автоплатежей для API и ATM.
	 *
	 * @param payments платежи.
	 * @return активные платежи.
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
	 * Возвращает список приостановленных автоплатежей.
	 *
	 * @param payments платежи.
	 * @return приостановленные автоплатежи.
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
	 * Возвращает список ожидающих подтверждения автоплатежей.
	 *
	 * @param payments платежи.
	 * @return ожидающие подтверждения автоплатежи.
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
	 * Возвращает список архивов автоплатежей.
	 *
	 * @param payments платежи.
	 * @return архивы-автоплатежи.
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
	 * Получает только автоплатежи
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
