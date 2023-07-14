package com.rssl.phizic.gate.loyalty;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * информация по программе лояльности клиента
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public interface LoyaltyProgram extends Serializable
{
	/**
	 * @return внешний идентификатор программы лояльности.
	 */
	String getExternalId();

	/**
	 * @return количество накопленных баллов
	 */
	BigDecimal getBalance();

	/**
	 * @return номер телефона клиента
	 */
	String getPhone();

	/**
	 * @return адрес электронной почты клиента
	 */
	String getEmail();
}
