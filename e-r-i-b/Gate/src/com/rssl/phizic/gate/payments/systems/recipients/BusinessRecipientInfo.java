package com.rssl.phizic.gate.payments.systems.recipients;

import java.math.BigDecimal;

/**
 * Информация о поставщике услуг
 *
 * @author khudyakov
 * @ created 20.03.2011
 * @ $Author$
 * @ $Revision$
 */
public interface BusinessRecipientInfo extends Recipient, RecipientInfo
{
	/**
	 * @return максимальная комиссия
	 */
	public BigDecimal getMaxCommissionAmount();

	/**
	 * @return минимальная комиссия
	 */
	public BigDecimal getMinCommissionAmount();

	/**
	 * @return процентная ставка комиссии
	 */
	public BigDecimal getCommissionRate();

	/**
	 * @return true - поддерживает запрос реквизитов онлайн
	 */
	public Boolean isPropsOnline();

	/**
	 * @return true - поддерживает задолженность
	 */
	public Boolean isDeptAvailable();
}