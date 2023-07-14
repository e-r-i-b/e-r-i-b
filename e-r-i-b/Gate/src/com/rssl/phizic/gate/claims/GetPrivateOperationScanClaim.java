package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.SynchronizableDocument;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Заявка на получение электронного документа на электронную почту
 * @author komarov
 * @ created 15.04.2014
 * @ $Author$
 * @ $Revision$
 */
public interface GetPrivateOperationScanClaim extends SynchronizableDocument
{
	/**
	 * @return ФИО клиента
	 */
	String   getFIO();

	/**
	 * @return номер счёта
	 */
	String   getAccountNumber();

	/**
	 * @return сумма в рублях
	 */
	BigDecimal getAmount();

	/**
	 * @return дата операции
	 */
	Calendar getSendOperationDate();

	/**
	 * @return код авторизации
	 */
	Long  getAuthorisationCode();

	/**
	 * @return адрес электронной почты
	 */
	String   getEMail();

	/**
	 * @return кредитная операция или нет
	 */
	Boolean   isDebit();

}
