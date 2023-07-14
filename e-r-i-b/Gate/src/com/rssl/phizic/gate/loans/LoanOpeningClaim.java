package com.rssl.phizic.gate.loans;

import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;

import java.util.Iterator;

/**
 * @author Omeliyanchuk
 * @ created 10.01.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Заявка на получение кредита
 */
public interface LoanOpeningClaim extends SynchronizableDocument
{
   /**
    * Сумма запрашиваемого кредита
    *
    * @return сумма
    */
   Money getLoanAmount();
   /**
    * Собственные средства заемщика.
    * Может быть null.
    *
    * @return сумма
    */
   Money getSelfAmount();
   /**
    * Стоимость приобретаемой собственности (товара).
    * Может быть null.
    *
    * @return сумма
    */
   Money getObjectAmount();
   /**
    * Срок запрашиваемого кредита
    *
    * @return срок
    */
   DateSpan getDuration();
   /**
    * Внешний ID определяющий условия кредита
    * Domain: ExternalID
    *
    * @return id
    */
   String getConditionsId();
   /**
    * Внешний ID подразделения банка в котором предоставляется кредит
    * Domain: ExternalID
    *
    * @return внешний ID
    */
   String getOfficeExternalId();
   /**
    * Итератор ответов на вопросы анкеты
    *
    * @return итератор
    */
   Iterator<QuestionnaireAnswer> getQuestionnaireIterator();
   /**
    * Итератор по анкетам поручителей.
    * Если поручителей нет, то итератор на пустой список.
    *
    * @return итератор
    */
   Iterator<LoanOpeningClaim> getGuarantorClaimsIterator();
   /**
    * Сумма утвержденного кредита
    *
    * @return сумма
    */
   Money getApprovedAmount();
   /**
    * Установить утвержденную сумму. Заполняется гейтом
    *
    * @param amount сумма
    */
   void setApprovedAmount(Money amount);
   /**
    * Срок утвержденного кредита
    *
    * @return срок
    */
   DateSpan getApprovedDuration();
   /**
    * Установить утрержденный срок. Заполняется гейтом
    *
    * @param dateSpan Срок
    */
   void setApprovedDuration(DateSpan dateSpan);

	/**
	 * Номер заявки для отображения пользователю
	 * @return
	 */
	String getClaimNumber();

	/**
	 *  Установить номер заявки для отображения пользователю
 	 * @param claimNumber номер заявки в Loans
	 */
	void setClaimNumber(String claimNumber);
}
