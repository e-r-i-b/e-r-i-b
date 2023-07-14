package com.rssl.phizic.auth;

/**
 * @author Egorova
 * @ created 23.06.2008
 * @ $Author$
 * @ $Revision$
 */
public enum BlockingReasonType
{
	/**
	 * заблокировано системно (из-за нехватки средств на счете клиента)
	 * todo: Переименовать в соответсвии со смыслом
	 */
	system,
	/**
	 * заблокировано сотрудником из АРМ Сотрудника
	 */
	employee,
	/**
	 * заблокировано изза большого количества неправильного ввода пароля
	 */
	wrongLogons,
	/**
	 * заблокировано из-за длительной неактивности
	 */
	longInactivity,
}
