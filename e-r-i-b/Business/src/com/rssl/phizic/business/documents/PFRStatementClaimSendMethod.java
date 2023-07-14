package com.rssl.phizic.business.documents;

/**
 * @author Erkin
 * @ created 14.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Способ отправки заявки на выписку из ПФР
 */
public enum PFRStatementClaimSendMethod
{
	/**
	 * Отправлять заявку используя паспортные данные way
	 */
	USING_PASPORT_WAY,

	/**
	 * Отправлять заявку используя СНИЛС (Страховой Номер Индивидуального Лицевого Счёта)
	 */
	USING_SNILS;

	public static final PFRStatementClaimSendMethod DEFAULT = USING_PASPORT_WAY;
}
