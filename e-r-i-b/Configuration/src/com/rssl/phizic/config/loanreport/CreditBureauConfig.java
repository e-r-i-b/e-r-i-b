package com.rssl.phizic.config.loanreport;

import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.net.URL;

/**
 * User: Moshenko
 * Date: 30.09.14
 * Time: 11:15
 * Конфиг Кредитного Бюро
 */

/**
 * Конфиг по Кредитному Бюро
 * Не наследуется от Config, т.к. Config имеет неотключаемый 15-минутный кэш:
 * В задаче БКИ нужно читать настройки джоба БКИ так часто, как они меняются в АРМ Сотрудника.
 */
@PlainOldJavaObject
@SuppressWarnings("PublicField")
public class CreditBureauConfig
{
	/**
	 * Первый [ежемесячный] рабочий день джоба (never null)
	 */
	public DayOfMonth jobStartDay;

	/**
	 * Начало рабочего дня джоба (never null)
	 */
	public TimeOfDay jobStartTime;

	/**
	 * Конец рабочего дня джоба (never null)
	 */
	public TimeOfDay jobEndTime;

	/**
	 * Признак ативности джоба
	 */
	public boolean jobEnabled;

	/**
	 * Признак временной приостановки работы джоба
	 */
	public boolean jobSuspended;

	/**
	 * Размер пачки обрабатываемых клиентов
	 */
	public int jobPackSize;

	/**
	 * Путь к шрифтам(для выгрузки PDF)
	 */
	public String pfdResourcePath;

	/**
	 * Адрес ОКБ
	 */
	public String okbAddress;

	/**
	 * Телефон ОКБ
	 */
	public String okbPhone;

	/**
	 * Идентификационный номер поставщика услуг для ОКБ
	 */
	public UUID okbServiceProviderUUID;

	/**
	 * Таймаут БКИ
	 */
	public int bkiTimeoutInMinutes;

	/**
	 * URL ОКБ
	 */
	public URL okbURL;

	/**
	 * Признак отображения кнопки скачать отчет в формате pdf
	 */
	public boolean pdfButtonShow;

	/**
	 * JNDI-адрес очереди ЕРИБ -> CRM
	 */
	public final String eribToCrmQueueName = "jms/esb/EribToCrmQueue";

	/**
	 * JNDI-адрес фабрики соединений ЕРИБ -> CRM
	 */
	public final String eribToCrmQCFName = "jms/esb/EribToCrmQCF";

}
