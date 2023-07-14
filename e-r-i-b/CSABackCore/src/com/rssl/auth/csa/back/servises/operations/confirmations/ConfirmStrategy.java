package com.rssl.auth.csa.back.servises.operations.confirmations;

import com.rssl.auth.csa.back.integration.ipas.ServiceUnavailableException;
import com.rssl.phizic.common.types.exceptions.SystemException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author akrenev
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * интерфейс стратегии подтверждения
 */

public interface ConfirmStrategy<C, I extends ConfirmationInfo>
{
	/**
	 * инициализируем стратегию (получаем информацию о пароле)
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws SystemException
	 * @throws ServiceUnavailableException
	 */
	public void initialize() throws Exception;

	/**
	 * @return информация о пароле
	 */
	public C getConfirmCodeInfo();

	/**
	 * Публикование одноразового пароля
	 * например, в случае смс, мы отправляем СМС'ку
	 * например, в случае iPas, мы ничего не делаем, т.к. код уже опубликован на чеке
	 * в случае push сообщения отправляем push сообщение
	 * @throws Exception
	 */
	public void publishCode() throws Exception;

	/**
	 * проверка возможности подтверждения
	 */
	public void checkConfirmAllowed();

	/**
	 * проверка пароля
	 * @param password введенный клиентом пароль
	 * @return true -- пароль валиден
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws SystemException
	 * @throws ServiceUnavailableException
	 */
	public boolean check(String password) throws Exception;

	/**
	 * @return true -- данная стратегия провалена (например, мсчерпаны все попытки ввода пароля)
	 */
	public boolean isFailed();

	/**
	 * @return детали подтверждения
	 */
	public I getConfirmationInfo();
}