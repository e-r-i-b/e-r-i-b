package com.rssl.phizic.gate.insurance;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.io.Serializable;

/**
 * Информация о страховом/НПФ продукте
 * @author lukina
 * @ created 27.02.2013
 * @ $Author$
 * @ $Revision$
 */

public interface InsuranceApp  extends Serializable
{
	/**
    * Внешний ID страхового продукта
    * @return ID
    */
	String getId();

	/**
	 * @return  ID (референс) страховки, по которой запрашиваются данные
	 */
	String getReference();

	/**
	 * @return  Наименование бизнес-процесса, в рамках которого оформлена страховка
	 */
	String getBusinessProcess();

	/**
	 * @return Наименование типа продукта, в рамках которого оформлена страховка
	 */
	String getProductType();

	/**
	 * @return Статус страховки для отображения.
	 */
	String getStatus();

	/**
	 * @return Страховая компания для отображения
	 */
	String getCompany();

	/**
	 * @return Страховая программа для отображения
	 */
	String getProgram();

	/**
	 * @return Дата начала действия страховки
	 */
	Calendar getStartDate();

	/**
	 * @return Дата окончания действия страховки
	 */
	Calendar getEndDate();

	/**
	 * @return Номер СНИЛС для пенсионного страхования
	 */
	String getSNILS();

	/**
	 * @return Страховая сумма
	 */
	Money getAmount();

	/**
	 * @return Описание страховых рисков
	 */
	String getRisk();

	/**
	 * @return Дополнительная информация (№ и дата кредитного договора (при
     * условии оформления страховки в рамках кредитной сделки)
	 */
	String getAdditionalInfo();

	/**
	 * @return Описание реквизитов договора страхования: Серия, номер и дата
     * выдачи полиса
	 */
	PolicyDetails getPolicyDetails();
}
