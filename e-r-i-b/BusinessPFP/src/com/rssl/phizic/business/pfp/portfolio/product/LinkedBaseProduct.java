package com.rssl.phizic.business.pfp.portfolio.product;

/**
 * @author mihaylov
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 * Интерфейс для базовых продуктов в портфеле, для которых поддерживается связывание с продуктами в других портфелях
 */
public interface LinkedBaseProduct
{
	/**
	 * @return идентификатор продукта
	 */
	Long getId();

	/**
	 * Установить идетнификатор связанного продукта в другом портфеле
	 * @param id - идентификатор связанного продукта
	 */
	void setLinkedProductId(Long id);

	/**
	 * @return идентификатор связанного продукта в другом портфеле
	 */
	Long getLinkedProductId();
}
