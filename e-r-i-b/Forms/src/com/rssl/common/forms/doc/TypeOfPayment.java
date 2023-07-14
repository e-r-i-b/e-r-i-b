package com.rssl.common.forms.doc;

/**
 * Тип операции (операция на внешние счета/карты, операция на внутренние счета/карты).
 *
 * @author khudyakov
 * @ created 19.10.2012
 * @ $Author$
 * @ $Revision$
 */
public enum TypeOfPayment
{
	EXTERNAL_PAYMENT_OPERATION,         //операция на внешние счета/карты
	INTERNAL_PAYMENT_OPERATION,         //операция на внутренние счета/карты
	NOT_PAYMENT_OPEATION                //операция без списания денежных средств
}
