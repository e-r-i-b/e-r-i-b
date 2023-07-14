package com.rssl.phizic.gate.loyalty;

import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;
import java.util.Calendar;
import java.io.Serializable;

/**
 * информация по операции по программе лояльности
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public interface LoyaltyProgramOperation extends Serializable
{
	/**
	 * @return дата-время операции
	 */
	Calendar getOperationDate();

	/**
	 * @return тип операции(списание/зачисленние баллов)
	 */
	LoyaltyOperationKind getOperationKind();

	/**
	 * @return баланс операции(в бонусных баллах)
	 */
	BigDecimal getOperationalBalance();

	/**
	 * @return сумма операции в валюте(по идее присутствует только при зачислении баллов)
	 */
	Money getMoneyOperationalBalance();

	/**
	 * @return описание операции
	 */
	String getOperationInfo();
}
