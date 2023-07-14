package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import com.rssl.phizic.test.wsgateclient.webAPI.wsclient.exceptions.WebAPIException;
import com.rssl.phizic.test.wsgateclient.webAPI.wsclient.exceptions.WebAPILogicException;

import java.util.List;
import java.util.Map;

/**
 * @author Jatsky
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 */

public class WebAPIRequestHelper
{
	private static final WebAPISender sender = new WebAPISender();

	/**
	 * Послать запрос на получение информации о только что залогиневшемся клиенте.
	 * @param token токен аутентификации
	 * @param ip IP-адрес машины конечного клиента
	 * @return ответ
	 */
	public static String sendLoginRq(String url, String token, String ip, String isAuthenticationCompleted) throws WebAPIException, WebAPILogicException
	{
		String responseData = sender.sendRequest(new LoginRequestData(token, isAuthenticationCompleted), RequestConstants.LOGIN_OPERATION, ip, url);
		return responseData;
	}

	/**
	 * Послать запрос на выход.
	 * @param ip IP-адрес машины конечного клиента
	 * @return ответ
	 */
	public static String sendLogoffRq(String url, String ip) throws WebAPIException, WebAPILogicException
	{
		String responseData = sender.sendRequest(new LogoffRequestData(), RequestConstants.LOGOFF_OPERATION, ip, url);
		return responseData;
	}

	/**
	 * Послать запрос на получение списка продуктов.
	 * @param productTypes список типов продуктов
	 * @param ip IP-адрес машины конечного клиента
	 * @return ответ
	 */
	public static String sendGetProductList(String url, List<String> productTypes, String ip) throws WebAPIException, WebAPILogicException
	{
		String responseData = sender.sendRequest(new GetProductListRequestData(productTypes), RequestConstants.PRODUCT_LIST_OPERATION, ip, url);
		return responseData;
	}

	/**
	 * Послать запрос на получение детальной информации по продукту.
	 * @param ip IP-адрес машины конечного клиента
	 * @param id id продукта
	 * @param operation название операции
	 * @return ответ
	 */
	public static String sendGetProductDetail(String url, String ip, String id, String operation) throws WebAPIException, WebAPILogicException
	{
		String responseData = sender.sendRequest(new GetProductDetailRequestData(id), operation, ip, url);
		return responseData;
	}

	/**
	 * Послать запрос на получение выписки по карте.
	 * @param ip IP-адрес машины конечного клиента
	 * @param id id продукта
	 * @param from дата начала выписки
	 * @param to дата окончания  выписки
	 * @param count количество последних операций, которое необходимо отобразить.
	 * @return ответ
	 */
	public static String sendGetProductAbstract(String url, String ip, String id, String from, String to, String count, String operation) throws WebAPIException, WebAPILogicException
	{
		String responseData = sender.sendRequest(new GetProductAbstractRequestData(id, from, to, count), operation, ip, url);
		return responseData;
	}

	/**
	 * Простой запрос не содержащий дополнительных параметров в теле
	 *
	 * @param  ip IP-адрес машины конечного клиента
	 * @param  operation операция
	 * @return ответ
	 * @throws WebAPIException
	 * @throws WebAPILogicException
	 */
	public static String sendSimpleRequest(String url, final String ip, final String operation) throws WebAPIException, WebAPILogicException
	{
		return sender.sendRequest(new SimpleRequestData(), operation, ip, url);
	}

	/**
	 * Запрос на получение элементов визуального интерфейса
	 *
	 * @param ip IP-адрес машины конечного клиента
	 * @param exclude элементы визуального интерфейса которые не должны передаваться в ответе
	 * @return ответ
	 */
	public static String sendMenuOperationRequest(String url, String ip, String... exclude) throws WebAPIException, WebAPILogicException
	{
		return sender.sendRequest(new MenuRequestData(exclude), RequestConstants.MENU_OPERATION, ip, url);
	}

	/**
	 * Запрос на получение истории операций АЛФ
	 *
	 * @param ip IP-адрес машины конечного клиента
	 * @param params дополнительные параметры запроса
	 * @return Ответ
	 * @throws WebAPIException
	 */
	public static String sendSimpleParameterizedRequest(String url, String ip, Map<String, Object> params, String operation) throws WebAPIException, WebAPILogicException
	{
		return sender.sendRequest(new SimpleParameterizedData(params), operation, ip, url);
	}

	/**
	 * Запрос на добавление АЛФ операции
	 *
	 * @param ip IP-адрес машины конечного клиента
	 * @param parameters параметры запроса
	 * @return ответ
	 */
	public static String sendAddAlfOperationRequest(String url, String ip, Map<String, Object> parameters) throws WebAPIException, WebAPILogicException
	{
		return sender.sendRequest(new AddAlfOperationData(parameters), RequestConstants.ADD_ALF_OPERATION, ip, url);
	}

	/**
	 *
	 * @param ip IP-адрес машины конечного клиента
	 * @param incomeType Тип категорий(income/outcome)
	 * @param paginationSize Размер результирующей выборки
	 * @param paginationOffset Смещение относительно начала выборки
	 * @return Ответ
	 * @throws WebAPIException
	 */
	public static String sendALFCategoryListRequest(String url, String ip, String incomeType, String paginationSize, String paginationOffset) throws WebAPIException
	{
		return sender.sendRequest(new ALFCategoryListRequestData(incomeType, paginationSize, paginationOffset), RequestConstants.ALF_CATEGORY_LIST, ip, url);
	}

	/**
	 * Запрос на разбиение/редактирование АЛФ операции
	 *
	 * @param ip IP-адрес машины конечного клиента
	 * @param parameters параметры запроса
	 * @return
	 */
	public static String sendAlfEditRequest(String url, String ip, Map<String, Object> parameters) throws WebAPIException
	{
		return sender.sendRequest(new AlfEditOperationData(parameters), RequestConstants.ALF_EDIT_OPERATION, ip, url);
	}

	/**
	 * Запрос на редактирование группы операций
	 *
	 * @param ip IP-адрес машины конечного клиента
	 * @param parameters параметры запроса
	 * @return
	 */
	public static String sendAlfEditGroupRequest(String url, String ip, Map<String, Object> parameters) throws WebAPIException
	{
		return sender.sendRequest(new AlfEditOperationGroupData(parameters), RequestConstants.ALF_EDIT_GROUP_OPERATION, ip, url);
	}

	/**
	 *
	 * @param ip IP-адрес машины конечного клиента
	 * @param id Идентификатор категории
	 * @param name Название категории
	 * @param operationType Тип выполняемой операции
	 * @return Ответ
	 * @throws WebAPIException
	 */
	public static String sendALFCategoryEditRequest(String url, String ip, String id, String name, String operationType) throws WebAPIException
	{
		return sender.sendRequest(new ALFCategoryEditRequestData(id, name, operationType), RequestConstants.ALF_CATEGORY_EDIT, ip, url);
	}
}
