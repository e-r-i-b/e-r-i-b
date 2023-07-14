/**
 * User: usachev
 * Date: 09.12.14
 */
package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.fund.initiator.FundRequest;

/**
 *   Ограничения на "Исходящий запрос" по Краудгифтингу
 */
public interface FundRequestRestriction extends Restriction
{
	/**
	 * Попадает ли "Исходящий запрос" под ограничения
	 * @param fundRequest исходящий запрос
	 * @return Да, если не попадает под ограничения. Нет, если попадает.
	 */
	public boolean accept(FundRequest fundRequest);
}
