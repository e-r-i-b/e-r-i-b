package com.rssl.phizic.gate.clients;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.common.types.SegmentCodeType;

import java.util.Calendar;
import java.util.List;
import java.io.Serializable;

/**
 * Описание клента
 */
public interface Client extends Serializable
{
	/**
	 * ID клиента во внешней системе
	 *
	 * Domain: ExternalID
	 *
	 * @return внешний ID
	 */
	String getId();

	/**
	 * Внутренний ID клиента
	 *
	 * @return loginId клиента
	 */
	Long getInternalId();

	/**
    * Внутренний ID клиента
    *
    * @return loginId клиента
    */
	Long getInternalOwnerId();


	/**
	 * Идентификатор клиента для отображения в пользовательском интерфейсе
	 *
	 * @return идентификатор клиента
	 */
	String getDisplayId();

	/**
	 * Краткое имя клиента - ИВАНОВ А. Б..
	 * Domain: Text
	 *
	 * @return краткое имя
	 */
	String getShortName();

	/**
	 * Полное имя клиента ИВАНОВ ИВАН ИВАНОВИЧ
	 * Domain: Text
	 *
	 * @return полное имя
	 */
	String getFullName();

	/**
	 * Имя клиента - ИВАН
	 * Domain: Text
	 *
	 * @return имя
	 */
	String getFirstName();

	/**
	 * Фамилия клиента - ИВАНОВ
	 * Domain: Text
	 *
	 * @return фамилия
	 */
	String getSurName();

	/**
	 * Отчество клиента ИВАНОВИЧ
	 * Domain: Text
	 *
	 * @return отчество
	 */
	String getPatrName();

	/**
	 * Дата рождения
	 * Domain: Date
	 *
	 * @return дата
	 */
	Calendar getBirthDay();

	/**
	 * Место рождения (населенный пункт)
	 * Domain: Text
	 *
	 * @return место рождения
	 */
	String getBirthPlace();

	/**
	 * Email
	 * Domain: Text
	 *
	 * @return email
	 */
	String getEmail();

	/**
	 * Домашний телефон
	 * Domain: Text
	 *
	 * @return номер
	 */
	String getHomePhone();

	/**
	 * Рабочий телефон
	 * Domain: Text
	 *
	 * @return номер
	 */
	String getJobPhone();

	/**
	 * Мобильный телефон
	 * Domain: Text
	 *
	 * @return номер
	 */
	String getMobilePhone();

   /**
    * @return Мобильный оператор
    */
    String getMobileOperator();

	/**
	 * Пол
	 * Domain: ClientSex
	 *
	 * @return M или F
	 */
	String getSex();

	/**
	 * ИНН
	 * Domain: INN
	 *
	 * @return ИНН
	 */
	String getINN();

	/**
	 * Резидент или нет
	 *
	 * @return true=резидент
	 */
	boolean isResident();

   /**
    * Юридический адрес
    *
    * @return юридический адрес
    */
   Address getLegalAddress();

   /**
    * Фактический адрес
    *
    * @return фактический адрес
    */
   Address getRealAddress();
	
   /**
    * Получить список документов клиента.
    */
   List<? extends ClientDocument> getDocuments();

	/**
	 * Гражданство
	 */
	String getCitizenship();

	/**
	 * @return офис обслуживания клиента
	 */
	Office getOffice();

	/**
	 * @return Номер договора
	 */
	String getAgreementNumber();

	/**
	 * @return Дата начала обслуживания
	 */
	Calendar getInsertionDate();

	/**
	 * @return Дата прекращения обслуживания
	 */
	Calendar getCancellationDate();

	/**
	 * @return состояние клиента
	 */
	ClientState getState();
	
	/**
	 * @return Подписал ли клиент универсальный договор банковсого обслуживания. true - подписал.
	 */
	boolean isUDBO();

	/**
	 * @return Код сегмента, к которому относят клиента
	 */
	SegmentCodeType getSegmentCodeType();

	/**
	 * @return Код тарифного плана
	 */
	String getTarifPlanCodeType();

	/**
	 * @return Дата подключения тарифного плана
	 */
	Calendar getTarifPlanConnectionDate();

	/**
	 * @return Номер менеджера, за которым закреплён клиент
	 */
	String getManagerId();

	/**
	 * @return Тер. банк клиентского менеджера
	 */
	String getManagerTB();

	/**
	 * @return ОСБ клиентского менеджера
	 */
	String getManagerOSB();

	/**
	 * @return ВСП клиентского менеджера
	 */
	String getManagerVSP();

	/**
	 * @return пол клиента
	 */
	String getGender();
}
