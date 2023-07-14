package com.rssl.phizic.common.types.basket;

/**
 * @author tisov
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 * Режим отображение баннера инвойсов на странице "Платежи и переводы"
 */

public enum InvoicesSubscriptionsPromoMode
{
	NOT_EXIST,     //Подписок нет, отображаем соответствующий баннер
	ONLY_AUTO,     //Есть только автоматически созданные подписки, отображаем соответствующий баннер
	EXIST          //Подписки есть, баннер не отображаем
}
