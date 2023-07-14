package com.rssl.phizic.business.pfp.portfolio;

/**
 * @author mihaylov
 * @ created 28.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Текущее состояние портфелей клиента
 */
public enum PersonPortfoliosCondition
{
	EMPTY,//все портфели клиента пусты
	NOT_EMPTY,//портфели не пусты, но средства для их заполнения ещё есть
	FULL;//все портфели клиента заполнены	
}
