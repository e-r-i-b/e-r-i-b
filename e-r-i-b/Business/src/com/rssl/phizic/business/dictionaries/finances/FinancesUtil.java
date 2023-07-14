package com.rssl.phizic.business.dictionaries.finances;

/**
 * @author lepihina
 * @ created 18.04.14
 * $Author$
 * $Revision$
 * Утилитный класс для работы с сущностями АЛФ
 */
public class FinancesUtil
{
	private static final MerchantCategoryCodeService merchantCategoryCodeService = new MerchantCategoryCodeService();

	/**
	 * Список mcc-кодов, привязанных к категории, строкой
	 * @param categoryExternalId - внешний идентификатор категории
	 * @return список строкой
	 */
	public static String getMCCByCategoryAsString(String categoryExternalId)
	{
		return merchantCategoryCodeService.getByCategoryAsString(categoryExternalId);
	}
}
