package com.rssl.phizic.common.types;

/**
 * @author Omeliyanchuk
 * @ created 14.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Тип приложения
 * при изменении надо исправить в журналах
 * дополнить файл /com/rssl/phizic/web/log/resources.properties
 * При добавлении нового mAPI, необходимо добавить также в com.rssl.phizic.business.payments.forms.meta.TransformType
 * Допустимая длина названий значений - до 20 символов.
 */
public enum Application
{
	/**
	 * Тесты
	 */
	JUnitTest,

	/**
	 * Слушатель шины для сбрф
	 */
	ESBERIBListener,

	/**
	 * Слушатель IQWave для сбрф
	 */
	IQWaveListener,

	/**
	 * Слушатель COD для сбрф
	 */
	Listener,

	/**
	 * 
	 */
	WSGateListener,

	/**
	 * Рассылки
	 */
	Messaging,

	/**
	 * Обработчик расписаний
	 */
	Scheduler,

	/**
	 * СМС-банкинг
	 */
	SmsBanking,

	/**
	 * Шлюз
	 */
	Gate,

    /**
	 * mAPI v5.x.x
	 */
	mobile5,

    /**
     * mAPI v6.x.x
     */
    mobile6,

	/**
	 * mAPI 7.x.x
	 */
	mobile7,

	/**
	 * mAPI 8.x.x
	 */
	mobile8,

	/**
	 * mAPI 9.x.x
	 */
	mobile9,

	/**
	 * АТМ
	 */
	atm,

	/**
	 * SocialApi
	 */
	socialApi,

	/**
	 * WebAPI
	 */
	WebAPI,

	/**
	 * Слушатель курсов валют для сбрф
	 */
	WebCurrency,

	/**
	 * Клиентское приложение
	 */
	PhizIC,

	/**
	 * АРМ сотрудника
	 */
	PhizIA,

	/**
	 * Тестовый веб-модуль
	 */
	WebTest,

	/**
	 * Заглушка к ЦСА
	 */
	CSA,

	/**
	 * Слушатель интернет-магазинов 
	 */
	WebShopListener,

	/**
	 * Front часть CSA
	 */
	CSAFront,
	
	/**
	 * Back часть CSA
	 */
	CSABack,

	/**
	 * ЦСА Админ
	 */
	CSAAdmin,

	/**
	 * Приоложение для работы с лимитами
	 */
	LimitsApp,

	/**
	 * Любое другое
	 * @deprecated по-хорошему встречаться не должна
	 */
	Other,
	
	/**
	 * приложение мониторнига
	 */
	Monitoring,

	/**
	 * слушатель АС Филиал
	 */
	ASFilialListener,

	/**
	 * СМС-канал ЕРМБ
	 */
	ErmbSmsChannel,

	/**
	 * Служебный канал ЕРМБ
	 */
	ErmbAuxChannel,

	/**
	 * Транспортный канал ЕРМБ
	 */
	ErmbTransportChannel,

	/**
	 * EjbTest
	 */
	EjbTest,

	PhizGateGorod,

	SBCMSService,
	PhizGateCod,
	WebATM,
	WebLog,
	WebPFP,

	ErmbOSS,

	EventListener,

	/**
	 * Выполнение задач миграции
	 */
	ERMBListMigrator ,

	CsaErmbListener,

	/**
	 * Слушатель уведомлений от way4 об изменении данных клиента.
	 */
	Way4NotifyListener,

	/**
	 * ЕСУШ
	 */
	USCT,

	/**
	 * Слушатель обратной очереди информации по кредитам (БКИ, TSM)
	 */
	CreditProxyListener,

	/**
	 * Слушатель очередей орзины платежей
	 */
	BasketProxyListener,

	/**
	 * Обработчик списания абонентской платы ЕРМБ
	 */
	ErmbSubscribeFee,

	/**
	 * Слушатель очереди подключений МБК (прокси)
	 */
	ErmbMbkListener,

	/**
	 * Внеблочный джоб-маршрутизатор p2p-запросов МБК.
	 * Джоб получает запросы из ХП МБК и кладёт их в очередь, одну на все блоки.
	 * Из очереди запрос достаёт MBK_P2P_Processor любого блока.
	 */
	MBK_P2P_Proxy,

	/**
	 * Внутриблочный обработчик p2p-запросов МБК.
	 * Слушает очередь от MBK_P2P_Proxy, обрабатывает запрос и отвечает в МБК напрямую
	 */
	MBK_P2P_Processor,

	/**
	 * тестовое приложение
	 */
	TestApp,

	/**
	 * Шлюз к приложению ФМ
	 */
	FMBackGate,
	/**
	 * Листенер сообщений ФМ
	 */
	FMListener,
	/**
	 * мдм
	 */
	MDM,
	/**
	 * слушатель мдм
	 */
	MDMListener,
	;
}
