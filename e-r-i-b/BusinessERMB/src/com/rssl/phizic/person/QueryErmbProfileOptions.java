package com.rssl.phizic.person;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

/**
* @author Erkin
* @ created 02.08.2014
* @ $Author$
* @ $Revision$
*/
@PlainOldJavaObject
@SuppressWarnings("PublicField")
public class QueryErmbProfileOptions
{
	/**
	 * Флажок: искать профиль по ФИО+ДУЛ+ДР+ТБ в анкете
	 */
	public boolean findByActualIdentity;

	/**
	 * Флажок: искать профиль по ФИО+ДУЛ+ДР+ТБ в истории смены ФИО ДУЛ
	 */
	public boolean findByOldIdentity;
}
