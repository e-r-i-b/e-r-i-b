package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.SpecificGateConfig;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Валидируем лицевой счет для кредитных поставщиков на правильность заполнения.
 * Сверяем БИК поставщика с БИКом банка, который определяется по справочнику маршрутов кредитов:
 * из лицевого счета вытаскиваем 4 символа, начиная с 10го, и по ним находим БИК банка в справочнике
 *
 * Формат файла справочника creditsRoutes.csv
 * BankID,RouteAlpha,RouteNumber,BIK,Description
 */

public class CreditSimplePaymentSender extends SimplePaymentSender
{
	private static final String DEFAULT_FILE_NAME = "creditsRoutes.csv";
	private static final Map<String, String> creditsRoutes = new HashMap<String, String>();
	private static final String DELIMETER = ",";
	private static final int BIC_LENGTH = 9;
	private static BufferedReader reader;
	private static String lastCreditFilePath;
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public CreditSimplePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;

		Map<String, String> crdtRoutes = getCreditsRoutes();
		if (crdtRoutes.isEmpty())
			log.warn("Данные по маршрутизации из справочник маршрутов кредитов не было прочитаны.");

		String providerBIC = payment.getReceiverBank().getBIC();
		String bankId = getIdentifier(payment).toString().substring(9, 13);
		String bankBIC = crdtRoutes.get(bankId);
		/*
		Если не находим БИК отделаения по 4-м символам, то ищем по первым двум и обрезаем лидирующий 0, если таковой имеется.
		Ситуация с 2-х символьными кодами появляется тогда, когда ссудный счет принадлежит ГОСБам с отдельными
		номерами ТБ.
		 */
		if(bankBIC == null)
		{
			String tempBankId = StringHelper.removeLeadingZeros(bankId.substring(0,2));
			bankBIC = creditsRoutes.get(tempBankId);
		}

		if (StringHelper.isEmpty(bankBIC) || !StringHelper.appendLeadingZeros(bankBIC,BIC_LENGTH).equals(providerBIC))
			throw new GateLogicException("Проверьте правильность заполнения поля 'Лицевой счет'. БИК поставщика услуг и БИК банка не совпадают.");
	}

	private static Map<String, String> getCreditsRoutes()
	{
		SpecificGateConfig config = ConfigFactory.getConfig(SpecificGateConfig.class);
		lock.readLock().lock();
		try
		{
			if (StringHelper.equals(lastCreditFilePath, config.getCreditsRoutesDictionaryPath()))
				return creditsRoutes;
		}
		finally {
			lock.readLock().unlock();
		}

		lock.writeLock().lock();
		try
		{
			if (StringHelper.equals(lastCreditFilePath, config.getCreditsRoutesDictionaryPath()))
				return creditsRoutes;

			reloadCreditList();
			return creditsRoutes;
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}

	private static void reloadCreditList()
	{
		try
		{
			SpecificGateConfig config = ConfigFactory.getConfig(SpecificGateConfig.class);
			lastCreditFilePath = config.getCreditsRoutesDictionaryPath();
			reader = new BufferedReader(new FileReader(lastCreditFilePath + File.separator + DEFAULT_FILE_NAME));
			String line;
			while ((line = reader.readLine()) != null)
			{
				String [] parameters = line.split(DELIMETER);
				creditsRoutes.put(parameters[0],parameters[3]);
			}
		}
		catch (IOException e)
		{
			log.error("Ошибка при чтении файла " + DEFAULT_FILE_NAME, e);
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				log.error("Ошибка при чтении файла " + DEFAULT_FILE_NAME,e);
			}
		}
	}
}
