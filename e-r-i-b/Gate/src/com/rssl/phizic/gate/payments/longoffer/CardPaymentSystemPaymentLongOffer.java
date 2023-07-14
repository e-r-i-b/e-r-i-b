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
 * Длительное поручение на перевод с карты по биллинговой технологии
 */
public interface CardPaymentSystemPaymentLongOffer extends CardPaymentSystemPayment, AutoSubscriptionClaim
{
	/**
	 * Схема для регулярного автоплатежа(если null значит недоступен)
	 * @return
	 */
	public AlwaysAutoPayScheme getAlwaysAutoPayScheme();

	/**
	 * Установить схему для регулярного автоплатежа
	 * @param scheme - схема
	 */
	public void setAlwaysAutoPayScheme(AlwaysAutoPayScheme scheme);

	/**
	 * Схема для порогового автоплатежа(если null значит недоступен)
	 * @return
	 */
	public ThresholdAutoPayScheme getThresholdAutoPayScheme();

	/**
	 * Установить схему для порогого автоплатежа
	 * @param scheme - схема
	 */
	public void setThresholdAutoPayScheme(ThresholdAutoPayScheme scheme);

	/**
	 * Схема для автоплатежа по выставленному счету(если null значит недоступен)
	 * @return
	 */
	public InvoiceAutoPayScheme getInvoiceAutoPayScheme();

	/**
	 * Установить схему для автоплатежу по выставленному счету
	 * @param scheme - схема
	 */
	public void setInvoiceAutoPayScheme(InvoiceAutoPayScheme scheme);

	/**
	 * Признак наличия комиссии при осуществлении платежей по подписке на автоплатеж в соответствии с тарифами банка.
	 * @return true - комиссия взывмается
	 */
	public boolean isWithCommision();

	/**
	 * Установить признак наличия комиссии при осуществлении платежей по подписке на автоплатеж в соответствии с тарифами банка
	 * @param withCommision true - комиссия взывмается 
	 */
	public void setWithCommision(boolean withCommision);

	/**
	 * @return Код группы услуг
	 */
	String getGroupService();

	/**
	 * Установить код группы услуг
	 * @param groupService код группы услуг
	 */
	void setGroupService(String groupService);

	/**
	 * @return внешний идентификатор автоподписки, которая приостанавливается
	 */
	String getLongOfferExternalId();
}
