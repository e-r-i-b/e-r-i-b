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
	 * ������� ������ �� ��������� ���������� � ������ ��� �������������� �������.
	 * @param token ����� ��������������
	 * @param ip IP-����� ������ ��������� �������
	 * @return �����
	 */
	public static String sendLoginRq(String url, String token, String ip, String isAuthenticationCompleted) throws WebAPIException, WebAPILogicException
	{
		String responseData = sender.sendRequest(new LoginRequestData(token, isAuthenticationCompleted), RequestConstants.LOGIN_OPERATION, ip, url);
		return responseData;
	}

	/**
	 * ������� ������ �� �����.
	 * @param ip IP-����� ������ ��������� �������
	 * @return �����
	 */
	public static String sendLogoffRq(String url, String ip) throws WebAPIException, WebAPILogicException
	{
		String responseData = sender.sendRequest(new LogoffRequestData(), RequestConstants.LOGOFF_OPERATION, ip, url);
		return responseData;
	}

	/**
	 * ������� ������ �� ��������� ������ ���������.
	 * @param productTypes ������ ����� ���������
	 * @param ip IP-����� ������ ��������� �������
	 * @return �����
	 */
	public static String sendGetProductList(String url, List<String> productTypes, String ip) throws WebAPIException, WebAPILogicException
	{
		String responseData = sender.sendRequest(new GetProductListRequestData(productTypes), RequestConstants.PRODUCT_LIST_OPERATION, ip, url);
		return responseData;
	}

	/**
	 * ������� ������ �� ��������� ��������� ���������� �� ��������.
	 * @param ip IP-����� ������ ��������� �������
	 * @param id id ��������
	 * @param operation �������� ��������
	 * @return �����
	 */
	public static String sendGetProductDetail(String url, String ip, String id, String operation) throws WebAPIException, WebAPILogicException
	{
		String responseData = sender.sendRequest(new GetProductDetailRequestData(id), operation, ip, url);
		return responseData;
	}

	/**
	 * ������� ������ �� ��������� ������� �� �����.
	 * @param ip IP-����� ������ ��������� �������
	 * @param id id ��������
	 * @param from ���� ������ �������
	 * @param to ���� ���������  �������
	 * @param count ���������� ��������� ��������, ������� ���������� ����������.
	 * @return �����
	 */
	public static String sendGetProductAbstract(String url, String ip, String id, String from, String to, String count, String operation) throws WebAPIException, WebAPILogicException
	{
		String responseData = sender.sendRequest(new GetProductAbstractRequestData(id, from, to, count), operation, ip, url);
		return responseData;
	}

	/**
	 * ������� ������ �� ���������� �������������� ���������� � ����
	 *
	 * @param  ip IP-����� ������ ��������� �������
	 * @param  operation ��������
	 * @return �����
	 * @throws WebAPIException
	 * @throws WebAPILogicException
	 */
	public static String sendSimpleRequest(String url, final String ip, final String operation) throws WebAPIException, WebAPILogicException
	{
		return sender.sendRequest(new SimpleRequestData(), operation, ip, url);
	}

	/**
	 * ������ �� ��������� ��������� ����������� ����������
	 *
	 * @param ip IP-����� ������ ��������� �������
	 * @param exclude �������� ����������� ���������� ������� �� ������ ������������ � ������
	 * @return �����
	 */
	public static String sendMenuOperationRequest(String url, String ip, String... exclude) throws WebAPIException, WebAPILogicException
	{
		return sender.sendRequest(new MenuRequestData(exclude), RequestConstants.MENU_OPERATION, ip, url);
	}

	/**
	 * ������ �� ��������� ������� �������� ���
	 *
	 * @param ip IP-����� ������ ��������� �������
	 * @param params �������������� ��������� �������
	 * @return �����
	 * @throws WebAPIException
	 */
	public static String sendSimpleParameterizedRequest(String url, String ip, Map<String, Object> params, String operation) throws WebAPIException, WebAPILogicException
	{
		return sender.sendRequest(new SimpleParameterizedData(params), operation, ip, url);
	}

	/**
	 * ������ �� ���������� ��� ��������
	 *
	 * @param ip IP-����� ������ ��������� �������
	 * @param parameters ��������� �������
	 * @return �����
	 */
	public static String sendAddAlfOperationRequest(String url, String ip, Map<String, Object> parameters) throws WebAPIException, WebAPILogicException
	{
		return sender.sendRequest(new AddAlfOperationData(parameters), RequestConstants.ADD_ALF_OPERATION, ip, url);
	}

	/**
	 *
	 * @param ip IP-����� ������ ��������� �������
	 * @param incomeType ��� ���������(income/outcome)
	 * @param paginationSize ������ �������������� �������
	 * @param paginationOffset �������� ������������ ������ �������
	 * @return �����
	 * @throws WebAPIException
	 */
	public static String sendALFCategoryListRequest(String url, String ip, String incomeType, String paginationSize, String paginationOffset) throws WebAPIException
	{
		return sender.sendRequest(new ALFCategoryListRequestData(incomeType, paginationSize, paginationOffset), RequestConstants.ALF_CATEGORY_LIST, ip, url);
	}

	/**
	 * ������ �� ���������/�������������� ��� ��������
	 *
	 * @param ip IP-����� ������ ��������� �������
	 * @param parameters ��������� �������
	 * @return
	 */
	public static String sendAlfEditRequest(String url, String ip, Map<String, Object> parameters) throws WebAPIException
	{
		return sender.sendRequest(new AlfEditOperationData(parameters), RequestConstants.ALF_EDIT_OPERATION, ip, url);
	}

	/**
	 * ������ �� �������������� ������ ��������
	 *
	 * @param ip IP-����� ������ ��������� �������
	 * @param parameters ��������� �������
	 * @return
	 */
	public static String sendAlfEditGroupRequest(String url, String ip, Map<String, Object> parameters) throws WebAPIException
	{
		return sender.sendRequest(new AlfEditOperationGroupData(parameters), RequestConstants.ALF_EDIT_GROUP_OPERATION, ip, url);
	}

	/**
	 *
	 * @param ip IP-����� ������ ��������� �������
	 * @param id ������������� ���������
	 * @param name �������� ���������
	 * @param operationType ��� ����������� ��������
	 * @return �����
	 * @throws WebAPIException
	 */
	public static String sendALFCategoryEditRequest(String url, String ip, String id, String name, String operationType) throws WebAPIException
	{
		return sender.sendRequest(new ALFCategoryEditRequestData(id, name, operationType), RequestConstants.ALF_CATEGORY_EDIT, ip, url);
	}
}
