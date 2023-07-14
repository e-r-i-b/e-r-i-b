package com.rssl.phizic.business.documents.strategies.limits;

/**
 * @author khudyakov
 * @ created 17.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class Constants
{
	public static final String WAIT_ADDITIONAL_CONFIRM_BY_REASON_ERROR_MESSAGE  = "Для платеж id = %s необходимо дополнительное подтверждение по причине: %s";
	public static final String BLOCK_DOCUMENT_OPERATION_ERROR_MESSAGE           = "При обработке документа id = %s превышен заградительноый лимит, документ не подтвержден.";
	public static final String PROCESS_DOCUMENT_ERROR_MESSAGE                   = "При обработке документа id = %s возникла ошибка, все значения лимитов будут возвращены к начальному состоянию.";
	public static final String ROLLBACK_LIMIT_ERROR_MESSAGE                     = "При откате значений лимитов транзакции с externalId = %s произошла ошибка. Откат не выполнен.";
	public static final String NOT_FOUND_LIMIT_ERROR_MESSAGE                    = "При проверке документа documentId = %s не найден лимит банка %s";
	public static final String TIME_OUT_ERROR_MESSAGE                           = "При проведении платежа id = %s возникла ситуация time-out";
	public static final String BLOCK_LIMIT_OPERATIONS_EXCEEDED_MESSAGE          = "За последние 24 часа Вы совершили платежи и переводы на сумму %s. при суточном лимите в %s. Пожалуйста, уменьшите сумму операции или повторите перевод позднее.";
	public static final String BLOCK_LIMIT_OPERATION_EXCEEDED_MESSAGE           = "Вы не можете создать %s на сумму большую суточного лимита. Пожалуйста, измените сумму и повторите операцию.";
	public static final String EXCEEDED_AMOUNT_BY_TEMPLATE_PAYMENT_MESSAGE      = "Превышена разрешенная сумма оплаты по шаблону.";
	public static final String USER_LOCK_ERROR_MESSAGE                          = "Не удалось заблокировать логин клиента при учете лимитов для платежа id = %s";
	public static final String REQUIRE_ADDITIONAL_CHECK_ERROR_MESSAGE           = "Превышен лимит на совершение операции id = %s, необходима дополнительная проверка.";
	public static final String IMPOSSIBLE_PERFORM_OPERATION_ERROR_MESSAGE       = "Превышен лимит на совершение операции id = %s, выполнение операции невозможно.";
	public static final String WRONG_RESTRICTION_LIMIT_TYPE_ERROR_MESSAGE       = "Некорректный тип ограничения: %s для лимита id = %s";
	public static final String WRONG_OPERATION_LIMIT_TYPE_ERROR_MESSAGE         = "Некорректный тип операции: %s для лимита id = %s";
}
