package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.common.forms.doc.DocumentSignature;

import java.io.Serializable;
import java.util.Map;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: erkin $
 * @ $Revision: 48487 $
 */
public interface ConfirmStrategy extends Serializable, Cloneable
{
	/**
	 * @return имя - тип стратегии
	 */
	ConfirmStrategyType getType();

	/**
	 * Требуются ли значения для создания запроса
	 * @return true == требуются
	 */
	boolean hasSignature();

	/**
	 * Создать запрос на подтверждение
	 * @param login логин для которого создается запрос
	 * @param value значение для запроса
	 * @param sessionId идентификатор текущей сессии
	 * @param preConfirm для передачи доп пораметров из preConfirmActions
	 * @return запрос, а в случае ошибки возвращает ErrorConfirmRequest
	 * @throws SecurityException невозможно создать запрос (нет карты ключей, сертификата etc)
	 */
	ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId,PreConfirmObject preConfirm) throws SecurityException, SecurityLogicException;;

	/**
	 * Проверить ответ
	 * @param login логин для которого выполняется проверка
	 * @param request запрос
	 * @param response ответ
	 * @return true - если платеж может быть подтвежден по чеку, false - только по СМС
	 * @throws SecurityLogicException неверный ответ (пароль, подпись etc)
	 */
	ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityLogicException, SecurityException;

	/**
	 * Действия, которые можно выполнить перед подтверждением
	 * @param callBackHandler CallBackHandler
	 * @throws SecurityLogicException, SecurityException неверный ответ (пароль, подпись etc)
	 */
	PreConfirmObject preConfirmActions(CallBackHandler callBackHandler) throws SecurityLogicException, SecurityException;

	/**
	 * получить подпись
	 * @param request
	 * @param confirmResponse
	 * @return
	 * @throws SecurityLogicException
	 * @throws SecurityException
	 */
	DocumentSignature createSignature(ConfirmRequest request, SignatureConfirmResponse confirmResponse) throws SecurityLogicException, SecurityException;

	/**
	 * получить ридер для стратегии
	 * @return
	 */
	ConfirmResponseReader getConfirmResponseReader();

	/**
	 * Сброс стратегии подтверждения для конкретного объекта подтверждения
	 * @param login - логин владельца объекта
	 * @param confirmableObject - объект подтверждения
	 */
	void reset(CommonLogin login, ConfirmableObject confirmableObject) throws SecurityDbException;

	/**
	 * получить сообщение об ошибке
	 * @return
	 */
	public Exception getWarning();

	/**
	 * установить сообщение об ошибке
	 * @return
	 */
	public void setWarning(Exception e);

	/**
	 * вернуть копию объекта
	 * @return
	 */
	public Object clone() throws CloneNotSupportedException;

	/**
	 * Фильтрация стратегии в зависимости от условий conditions
	 * @param conditions условия фильтрации
	 * @param userChoice значение типа стратегии подтверждения выбранного пользователем (смс, чек)
	 * @param object - объект подтверждения
	 * @return
	 */
	public boolean filter(Map<ConfirmStrategyType, List<StrategyCondition>> conditions, String userChoice, ConfirmableObject object);
}
