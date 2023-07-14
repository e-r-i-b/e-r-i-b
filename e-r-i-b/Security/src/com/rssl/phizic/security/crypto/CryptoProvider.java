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
	 * Поиск сертификата по его ID
	 * @param certId ID сертификата
	 * @return Найденный сертификат
	 * throws SecurityLogicException сертификат не найден
	 */
	Certificate findCertificate(String certId) throws SecurityLogicException;

	/**
	 * Подписать данные с ЭЦП
	 * @param certId - идентификатор сертификата с закрытым ключом
	 * @param data данные, которые надо подписать
	 * @return ЭЦП
	 */
	Signature makeSignature(String certId, String data) throws SecurityException;

	/**
	 * Проверить данные с ЭЦП
	 * @param certId - идентификатор сертификата с открытым ключом
	 * @param data данные
	 * @param signature ЭЦП
	 * @return результаты проверки
	 */
	 CheckSignatureResult checkSignature(String certId, String data, Signature signature) throws SecurityException;

	/**
	 * @return итератор сертификатов
	 */
	Iterator<Certificate> enumerateCertificates();

	/**
	 * @return итератор запросов на сертификат
	 */
	Iterator<CertificateRequest> enumerateCertRequest();

	/**
	 * загружает сертификат из буфера в хранилище.
	 * @param data буфер содержащий сертификат
	 */
	void loadCertFromFileToStorage(byte[] data);

	/**
	 * получить информацию о сертификате
	 * @param data данные из файла
	 * @return прочитанная из буфера информация
	 */
	Certificate getCertInFileInfo(byte[] data);

	/**
	 * удаляет сертификат из хранилища
	 * @param cert сертификат который надо удалить
	 */
	void deleteCertificate(Certificate cert);
}
