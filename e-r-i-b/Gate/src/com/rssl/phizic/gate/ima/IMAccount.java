package com.rssl.phizic.gate.ima;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public interface IMAccount extends Serializable
{
	/**
	 * ID счета во внешней системе
	 * @return Id счета во внешней системе
	 */
	String getId();

	/**
	 * Номер счета
	 *
	 * @return номер счета
	 */
	String getNumber();

	/**
	 * Наименование ОМС
	 * @return наименование ОМС
	 */
	String getName();

	/**
	 * @return Вид металла
	 */
	Currency getCurrency();

	/**
	 * Остаток по счету ОМС
	 * @return Остаток по счету ОМС в граммах
	 */
	Money getBalance();

	/**
	 * Максимальная сумма списания
	 * @return максимальная сумма списания
	 */
	Money getMaxSumWrite();

	/**
	 * @return Номер договора счета ОМС
	 */
	String getAgreementNumber();

	/**
	 * Дата открытия ОМС
	 * @return Дата открытия ОМС
	 */
	Calendar getOpenDate();

	/**
	 * Дата закрытия ОМС
	 * @return Дата закрытия ОМС
	 */
	Calendar getClosingDate();

   /**
    * Получение текущего статуса ОМС (Открыт, Закрыт)
    *
    * @return IMAccountState
    */
   IMAccountState getState();

	/**
	 * Получение офиса текущего ОМС
	 *
 	 * @return Office
	 */
   Office getOffice();	
}
