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
 * Вспомогательный класс для отображения списка автоплатежей
 */
class RegularPaymentsListContainer
{
	private List<EditableExternalResourceLink> payments;    //список всех автоплатежей


	RegularPaymentsListContainer(List<EditableExternalResourceLink> regularPayments)
	{
		this.payments = regularPayments;
	}

	/**
	 * Отобрать только активные автоплатежи
	 * @return список активных автоплатежей
	 */
	public List<EditableExternalResourceLink> getOnlyActualPayments()
	{
		return LongOfferUtils.getOnlyActivePayments(payments);
	}

	/**
	 * Отобрать только приостановленные автоплатежи
	 * @return список приостановленных автоплатежей
	 */
	public List<EditableExternalResourceLink> getOnlySuspendedPayments()
	{
		return LongOfferUtils.getOnlySuspendedPayments(payments);
	}

	/**
	 * Отобрать только ожидающие подтверждения платежи
	 * @return список ожидающих подтверждения платежей
	 */
	public List<EditableExternalResourceLink> getOnlyWaitingPayments()
	{
		return LongOfferUtils.getOnlyWaitingConfirmPayments(payments);
	}

	/**
	 * Отобрать автоплатежи из архива
	 * @return список автоплатежей из архива
	 */
	public List<EditableExternalResourceLink> getOnlyArchivePayments()
	{
		return LongOfferUtils.getOnlyArchivePayments(payments);
	}

	/**
	 * @return список всех платежей
	 */
	public List<EditableExternalResourceLink> getPayments()
	{
		return payments;
	}

	/**
	 * Отобрать коллекцию платежей, активных в контексте мАПИ и АТМ
	 * @return сет активных в мАПИ и АТМ автоплатежей
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