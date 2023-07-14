package com.rssl.phizic.gate.bankroll;

/**
 * Транзакция по счёту
 */
public interface AccountTransaction extends TransactionBase
{
	/**
	 * Возвращает название корреспондента
	 * Domain: Text
	 *
	 * @return название корреспондента или null, если корреспондент отсутствует
	 */
	String getCounteragent();

	/**
	 * Возвращает номер счёта корреспондента
	 * Domain: AccountNumber
	 *
	 * @return номер счёта корреспондента или null, если корреспондент отсутствует
	 */
	String getCounteragentAccount();

	/**
	 * Возвращает банк корреспондента, текстовое описание (например, наименование + БИК)
	 * Domain: Text
	 *
	 * @return БИК или null, если корреспондент отсутствует
	 */
	String getCounteragentBank();

	/**
	 * Возвращает название банка корреспондента
	 *
	 * @return название банка корреспондента
	 */
	String getCounteragentBankName();

	/**
	 * Возвращает счёт контировки
	 * 
	 * @return счёт контировки
	 */
	String getBookAccount();

	/**
	 * Возвращает корсчёт банка получателя
	 *
	 * @return корсчёт банка получателя
	 */
	String getCounteragentCorAccount();

	/**
	 * Возвращает шифр операции
	 *
	 * @return шифр операции
	 */
	String getOperationCode();

	/**
	 * Возвращает номер документа, соответствующий операции
	 *
	 * @return номер документа, соответствующий операции
	 */
	String getDocumentNumber();

	/**
	 * @return дополнительная иинформация по карте
	 */
	CardUseData getCardUseData();
}
