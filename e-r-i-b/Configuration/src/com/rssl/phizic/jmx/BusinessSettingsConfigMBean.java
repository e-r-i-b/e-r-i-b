package com.rssl.phizic.jmx;

/**
 * Конфиг для бизнес-настроек, для которых требуется возможность изменения с использованием jmx
 @author Pankin
 @ created 15.04.2011
 @ $Author$
 @ $Revision$
 */
public interface BusinessSettingsConfigMBean
{
	/**
	 * Задать диапазон карт поддерживающих CAP пароль.
	 * @return
	 */
	public String getCAPCompatibleCardNumbers();

	/**
	 * Получить специфичные замены кодов ТБ 
	 * @return строка вида "xx:yy,xx1:yy1"
	 */
	public String getSpecificTBReplacements();

	/**
	 * Доступна ли перепривязка счетов клиентам при совпадении имени, отчества и ДР
	 * @return true - доступна
	 */
	public boolean isChangeAccountOwnerEnabled();
}
