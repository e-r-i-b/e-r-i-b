package com.rssl.phizic.gate.payments.template;

import com.rssl.phizic.common.types.documents.State;

import java.io.Serializable;

/**
 * Информация по шаблону
 *
 * @author khudyakov
 * @ created 17.05.2013
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateInfo extends Serializable
{
	/**
	 * @return название шаблона
	 */
	String getName();

	/**
	 * Установить название шаблона
	 * @param name название шаблона
	 */
	void setName(String name);

	/**
	 * @return используется/доступен ли в мАПИ
	 */
	boolean isUseInMAPI();

	/**
	 * Установить признак использования в мАПИ
	 * @param useInMAPI true - используется
	 */
	void setUseInMAPI(boolean useInMAPI);

	/**
	 * @return используется/доступен ли в устройствах самообслуживания
	 */
	boolean isUseInATM();

	/**
	 * Установить признак использования в устройствах самообслуживания
	 * @param useInATM true - используется
	 */
	void setUseInATM(boolean useInATM);

	/**
	 * @return используется/доступен ли в ЕРМБ
	 */
	boolean isUseInERMB();

	/**
	 * Установить признак использования в ЕРМБ
	 * @param useInERMB true - используется
	 */
	void setUseInERMB(boolean useInERMB);

	/**
	 * @return используется/доступен ли в ЕРИБ
	 */
	boolean isUseInERIB();

	/**
	 * Установить признак использования в ЕРИБ
	 * @param useInERIB true - используется
	 */
	void setUseInERIB(boolean useInERIB);

	/**
	 * @return индекс очередности при покаже
	 */
	int getOrderInd();

	/**
	 * Установить индекс очередности показе
	 * @param orderInd индекс очередности при показе
	 */
	void setOrderInd(int orderInd);

	/**
	 * @return видимость в различного рода меню, списках
	 */
	boolean isVisible();

	/**
	 * Установить видимость в различного рода меню, списках
	 * @param visible видимость в различного рода меню, списках
	 */
	void setVisible(boolean visible);

	/**
	 * @return статус активности шаблона документа. Принимаемые значения: удален клиентом, активен
	 */
	State getState();

	/**
	 * Установить статус документа
	 * @param state статус
	 */
	void setState(State state);
}
