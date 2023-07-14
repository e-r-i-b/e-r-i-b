package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.services.Service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Evgrafov
 * @ created 17.01.2006
 * @ $Author: pankin $
 * @ $Revision: 28273 $
 */

public interface AssignAccessOperation<R extends Restriction> extends Operation<R>
{
	/**
	 * @return Логин для которого назначаются права
	 */
	CommonLogin getLogin();

	/**
	 * @return операции (value) по схемам (key)
	 */
	Map<Service, List<OperationDescriptor>> getServicesTuple() throws BusinessException;

	/**
	 * Возвращает текущую схему доступа для пользователя
	 * @return AccessScheme, если схема задана, иначе - null
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	AccessScheme getCurrentAccessScheme () throws BusinessException;

	/**
	*Возвращает назначенную схему доступа
	* @return AccessSheme
	*/
	AccessScheme getNewAccessScheme();

	/**
	 * Назначить новую схему
	 * @param accessSchemeId - ID новой схемы
	 * @throws BusinessException
	 */
	void setNewAccessSchemeId ( Long accessSchemeId ) throws BusinessException, BusinessLogicException;


	/**
	 * Назначить персональную схему
	 * @param services - id услуг, входящих в схему
	 */
	void setPersonalScheme ( List<Long> services) throws BusinessException;

	/**
	 * Сохранить все изменения в БД
	 * @throws BusinessException
	 */
	void assign () throws BusinessException, BusinessLogicException;

	/**
	 * @return тип доступа
	 */
	AccessType getAccessType();

	/**
	 * @param flag разрешить запретить тип доступа
	 */
	void setNewAccessTypeAllowed(boolean flag);

	/**
	 * @return true = тип доступа разрешен
	 */
	boolean getAccessTypeAllowed();

	/**
	 * @return насторйки политики безопасности
	 */
	Properties getProperties();

	/**
	 * Установить значение свойства политики безопасности
	 * @param name имя свойства
	 * @param value значение
	 */
	void setNewProperty(String name, String value);

	/**
	 * Политика безопасности для которой выполняются настнойки
	 * @return политика
	 */
	AccessPolicy getPolicy();

	/**
	 * Хелперы для получения доступных схем и услуг
	 * @return список схем
	 */
	List<AssignAccessHelper> getHelpers() throws BusinessException;

	/**
	 * @param category установить новую категорию доступа
	 */
	void setNewCategory(String category);

	/**
	 * @return текущая категория схемы
	 */
	String getCategory() throws BusinessException;
}
