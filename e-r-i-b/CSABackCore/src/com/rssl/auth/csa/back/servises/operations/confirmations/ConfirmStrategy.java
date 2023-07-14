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
 * ��������� ��������� �������������
 */

public interface ConfirmStrategy<C, I extends ConfirmationInfo>
{
	/**
	 * �������������� ��������� (�������� ���������� � ������)
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws SystemException
	 * @throws ServiceUnavailableException
	 */
	public void initialize() throws Exception;

	/**
	 * @return ���������� � ������
	 */
	public C getConfirmCodeInfo();

	/**
	 * ������������ ������������ ������
	 * ��������, � ������ ���, �� ���������� ���'��
	 * ��������, � ������ iPas, �� ������ �� ������, �.�. ��� ��� ����������� �� ����
	 * � ������ push ��������� ���������� push ���������
	 * @throws Exception
	 */
	public void publishCode() throws Exception;

	/**
	 * �������� ����������� �������������
	 */
	public void checkConfirmAllowed();

	/**
	 * �������� ������
	 * @param password ��������� �������� ������
	 * @return true -- ������ �������
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws SystemException
	 * @throws ServiceUnavailableException
	 */
	public boolean check(String password) throws Exception;

	/**
	 * @return true -- ������ ��������� ��������� (��������, ��������� ��� ������� ����� ������)
	 */
	public boolean isFailed();

	/**
	 * @return ������ �������������
	 */
	public I getConfirmationInfo();
}