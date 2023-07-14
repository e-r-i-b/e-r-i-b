package com.rssl.phizic.business.schemes;

/**
 * @author Evgrafov
 * @ created 19.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 4221 $
 */

public class AccessCategory
{
	// Системная категория, используется для скрымия функционала и др. системных потребностей
	public static final String CATEGORY_SYSTEM   = "S";
	//Категория отосящаяся к клиентам банка
	public static final String CATEGORY_CLIENT   = "C";

	// Категория отосящаяся к сотрудникам банка, выполняющим административные функции
	public static final String CATEGORY_ADMIN    = "A";
	// Категория отосящаяся к сотрудникам банка, выполняющим функции операциониста
	public static final String CATEGORY_EMPLOYEE = "E";

}
