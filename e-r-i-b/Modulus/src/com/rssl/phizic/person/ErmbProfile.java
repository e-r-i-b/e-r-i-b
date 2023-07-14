package com.rssl.phizic.person;

import com.rssl.phizic.common.types.Entity;

import java.io.Serializable;
import java.util.Set;

/**
 * @author EgorovaA
 * @ created 14.11.2012
 * @ $Author$
 * @ $Revision$
 */
public interface ErmbProfile extends Entity, Serializable
{
	/**
	 * Идентификатор профиля ЕРМБ. Должен совпадать с ЕИК.
	 * @return id профиля ЕРМБ (совпадает с ЕИК)
	 */
	Long getId();

	boolean isServiceStatus();

	/**
	 * Получение главного номера моб.телефона ЕРМБ-профиля
	 * @return номер телефона
	 */
	 String getMainPhoneNumber();

	/**
	 * Получение полного списка номеров моб. телефонов
	 * @return список номер
	 */
	Set<String> getPhoneNumbers();

	/**
	 * Актуальный номер версии профиля
	 * @return номер версии
	 */
	Long getProfileVersion();

	/**
	 * Подтвержденный номер версии профиля
	 * @return номер версии
	 */
	Long getConfirmProfileVersion();

}
