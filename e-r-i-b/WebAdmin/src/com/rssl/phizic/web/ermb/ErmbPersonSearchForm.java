package com.rssl.phizic.web.ermb;

import com.rssl.phizic.web.persons.SearchPersonForm;

/**
 * User: Moshenko
 * Date: 27.05.2013
 * Time: 12:07:49
 * Поиск клиента в контексте возможности создавать профиль ЕРМБ
 */
public class ErmbPersonSearchForm extends SearchPersonForm
{
	private boolean searchByPhone;

	/**
	 * @return режим поиска клиента (true - поиск по номеру телефона, false - поиск по ФИО + ДУЛ + ДР + ТБ)
	 */
	public boolean isSearchByPhone()
	{
		return searchByPhone;
	}

	/**
	 * задать режим поиска клиента (true - поиск по номеру телефона, false - поиск по ФИО + ДУЛ + ДР + ТБ)
	 * @param searchByPhone режим поиска
	 */
	@SuppressWarnings("UnusedDeclaration") // вызывается стратсом при обработке запроса
	public void setSearchByPhone(boolean searchByPhone)
	{
		this.searchByPhone = searchByPhone;
	}
}
