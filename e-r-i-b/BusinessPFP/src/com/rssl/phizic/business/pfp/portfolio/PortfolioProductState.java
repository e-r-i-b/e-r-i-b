package com.rssl.phizic.business.pfp.portfolio;

/**
 * @author mihaylov
 * @ created 11.04.2012
 * @ $Author$
 * @ $Revision$
 */
/*статус продукта в портфеле*/
public enum PortfolioProductState
{
	SAVE,       //продукт сохранен в портфель
	ADD,        //продукт добавлен в портфель на форме редактирования, но ещё не сохранен
	DELETED;    //продукт удален из портфеля на форме редактирования, но удаление ещё не подтверждено
}
