package com.rssl.phizic.gate.security;

import com.rssl.phizic.common.types.Money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * информация по сберегательному сертификату
 * @author lukina
 * @ created 04.09.13
 * @ $Author$
 * @ $Revision$
 */

public interface SecurityAccount extends Serializable
{
	/**
    * @return Внешний ID
    */
	String getId();

	/**
	 * @return Тип бланка
	 */
	String getBlankType();

	/**
	 * @return  Уникальный номер бланка
	 */
	String getSerialNumber();

	/**
	 * @return Сумма номинала ценной бумаги
	 */
	Money  getNominalAmount();

	/**
	 * @return  Сумма дохода по ценной бумаге
	 */
	Money  getIncomeAmt();

	/**
	 * @return Процентная ставка дохода по ценной бумаге
	 */
	BigDecimal getIncomeRate();

	/**
	 * @return Тип срока платежа по ценной бумаге
	 */
	String  getTermType();

	/**
	 * @return Срок оплаты ценной бумаги в днях
	 */
	Long      getTermDays();

	/**
	 * @return Дата составления (валютирования) ценной бумаги
	 */
	Calendar  getComposeDt();

	/**
	 * @return Дата начала срока оплаты ценной бумаги
	 */
	Calendar  getTermStartDt();

	/**
	 * @return Дата окончания срока оплаты ценной бумаги
	 */
	Calendar  getTermFinishDt();

	/**
	 * @return Дата наступления срока давности об оплате
	 */
	Calendar  getTermLimitDt();

	/**
	 * @return Признак нахождения на хранении в банке
	 */
	boolean getOnStorageInBank();
	/**
	 * @return  Договор хранения - номер договора
	 */
	String getDocNum();
	/**
	 * @return Договор хранения  - дата документа
	 */
	Calendar getDocDt();
	/**
	 * @return ВСП, осуществляющее учет договора хранения (Код подразделения Сбербанка)
	 */
	String getBankId();
	/**
	 * @return ВСП, осуществляющее учет договора хранения (Наименование)
	 */
	String getBankName();
	/**
	 * @return ВСП, осуществляющее учет договора хранения (Адрес)
	 */
	String getBankPostAddr();
	/**
	 * @return  Код подразделения Сбербанка, выдавшего сертификат
	 */
	String getIssuerBankId();
	/**
	 * @return  Наименование банка, выдавшего сертификат
	 */
	String getIssuerBankName();
}
