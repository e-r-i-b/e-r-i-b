package com.rssl.phizic.gate.mobilebank;

import java.util.List;
import java.io.Serializable;

/**
 * @author Erkin
 * @ created 22.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Связка "основная карта - связанные карты" с указанием тарифа.
 */
public interface MobileBankRegistration extends Serializable
{
	/**
	 * @return платежная карта регистрации.
	 */
	MobileBankCardInfo getMainCardInfo();

	/**
	 * @return список связанных (информационных) карт
	 */
	List<MobileBankCardInfo> getLinkedCards();

	/**
	 * @return статус регистрации
	 */
	MobileBankRegistrationStatus getStatus();

	/**
	 * @return тариф регистрации.
	 */
	MobileBankTariff getTariff();
}