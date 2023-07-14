package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.resources.Resource;
import com.rssl.phizic.common.types.Currency;

import java.io.Serializable;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 12.10.2005 Time: 12:23:52 */
public interface ExternalResourceLink extends Resource, Serializable
{
	/**
	 * @return вдентифкатор сущности во внешнее системе, на которую ссылается Link
	 */
    String getExternalId();

	/**
	 * @return логин владельца, к которому привязана ссылка на сущность.
	 */
	Long getLoginId();

	/**
	 * @return сущность из внешнеей системы, на которую ссылается Link
	 */
    Object getValue() throws BusinessException, BusinessLogicException;

	String getNumber();

	Currency getCurrency();

	String getName();

	String getDescription();

	/**
	 * @return уникальный в разрезе всех (разнотипных) ссылок код
	 */
	String getCode();
	/**
	 * Сбросить состояние линка.
	 * Очистить кеш и тп
	 */
	void reset() throws BusinessLogicException, BusinessException;

	/**
	 * Тип продукта(счёт, мет. счёт и т.д)
	 * @return
	 */
    ResourceType getResourceType();

	/**
	 * @return true, если линк доступен для использования
	 */
	Boolean getShowInSystem();

	/**
	 * @return возвращает шаблон
	 */
	String getPatternForFavouriteLink();

	/**
	 * 
	 * @return возвращает класс оффлайн продукта
	 */
	public Class getStoredResourceType();
}
