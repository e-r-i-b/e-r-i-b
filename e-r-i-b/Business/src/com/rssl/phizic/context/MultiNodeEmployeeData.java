package com.rssl.phizic.context;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 23.02.14
 * @ $Author$
 * @ $Revision$
 *
 * Данные сотрудника, которые необходимо различать в одноблочном и многоблочных режимах.
 * В одноблочном режиме работы возвращаем данные о сотруднике из блока.
 * В многобочном режиме данные берем из ЦСА Админ.
 *
 * ВАЖНО!!! Правило работает только для функционала поддерживающего многоблочный режим.
 * В функционале не поддерживающем многоблочный режим всегда берем данные из блока.
 */
public interface MultiNodeEmployeeData extends Serializable
{

	/**
	 * @return идентификатор логина сотрудника
	 */
	Long getLoginId();

	/**
	 * @return доступ сотрудника ко всем ТБ
	 */
	boolean isAllTbAccess();

}
