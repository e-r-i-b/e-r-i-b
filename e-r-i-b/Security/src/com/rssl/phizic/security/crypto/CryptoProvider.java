package com.rssl.phizic.security.crypto;

import com.rssl.phizic.security.SecurityLogicException;

import java.util.Iterator;

/**
 * @author Roshka
 * @ created 29.08.2006
 * @ $Author$
 * @ $Revision$
 */

public interface CryptoProvider
{
	/**
	 * ����� ����������� �� ��� ID
	 * @param certId ID �����������
	 * @return ��������� ����������
	 * throws SecurityLogicException ���������� �� ������
	 */
	Certificate findCertificate(String certId) throws SecurityLogicException;

	/**
	 * ��������� ������ � ���
	 * @param certId - ������������� ����������� � �������� ������
	 * @param data ������, ������� ���� ���������
	 * @return ���
	 */
	Signature makeSignature(String certId, String data) throws SecurityException;

	/**
	 * ��������� ������ � ���
	 * @param certId - ������������� ����������� � �������� ������
	 * @param data ������
	 * @param signature ���
	 * @return ���������� ��������
	 */
	 CheckSignatureResult checkSignature(String certId, String data, Signature signature) throws SecurityException;

	/**
	 * @return �������� ������������
	 */
	Iterator<Certificate> enumerateCertificates();

	/**
	 * @return �������� �������� �� ����������
	 */
	Iterator<CertificateRequest> enumerateCertRequest();

	/**
	 * ��������� ���������� �� ������ � ���������.
	 * @param data ����� ���������� ����������
	 */
	void loadCertFromFileToStorage(byte[] data);

	/**
	 * �������� ���������� � �����������
	 * @param data ������ �� �����
	 * @return ����������� �� ������ ����������
	 */
	Certificate getCertInFileInfo(byte[] data);

	/**
	 * ������� ���������� �� ���������
	 * @param cert ���������� ������� ���� �������
	 */
	void deleteCertificate(Certificate cert);
}
