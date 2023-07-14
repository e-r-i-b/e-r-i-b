package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Пластиковая карта. Содержит основные данные по карте. Эти данные не изменяются в процессе жизни карты.
 */
public interface Card extends Serializable
{
	/**
	 * Внешний ID карты
	 * Domain: ExternalID
	 *
	 * @return id карты
	 */
	String getId();

	/**
	 * Описание катры (Visa Classic, MasterCard, Maestro Cirrus etc)
	 * Domain: Text
	 *
	 * @return описание карты
	 */
	String getDescription();

	/**
	 * @return Код описания карточного статуса.
	 */
	StatusDescExternalCode getStatusDescExternalCode();

	/**
	 * Описание катры (Visa Classic, MasterCard, Maestro Cirrus etc)
	 *
	 * @return описание катры
	 * @deprecated используйте getDescription()
	 */
	@Deprecated
	String getType();

	/**
	 * Номер карты
	 * Domain: CardNumber
	 *
	 * @return номер карты
	 */
	String getNumber();

	/**
	 * Дата выпуска карты (то, что выбито на карте)
	 * Domain: Date
	 *
	 * @return дата выпуска карты
	 */
	Calendar getIssueDate();

	/**
	 * Дата окончания срока действия карты (то, что выбито на карте).
	 * Используется везде кроме вывода даты окончания срока карты на экран ЕРИБ см. CHG032182.
	 * Domain: Date
	 *
	 * @return дата окончания срока действия карты
	 */
	Calendar getExpireDate();

	/**
	 *
	 * Используется только для вывода даты окончания срока карты на экран ЕРИБ см. CHG032182
	 *
	 * @return окончания срока действия карты. Может возвращать null.
	 */
	String getDisplayedExpireDate();

	/**
	 * Признак основной карты
	 * @return true - основная карта, false - нет
	 */
	boolean isMain();
	
   /**
    * Получение типа карты(дебетовая, кредитная, овердрафтная).
    * Значения из CardType
    * @return тип карты
    */
   CardType getCardType();

	   /**
    * Расходный лимит на карте.
    * Если возвращает null - значит безлимитная.
    *
    * @return Если возвращает null - значит безлимитная.
    */
   Money getAvailableLimit();
   /**
    * Подразделение, в котором открыта карта
    *
    * @return Подразделение, в котором открыта карта или null, если нельзя получить эту информацию
    */
   Office getOffice();

	/**
	 * код подразделения банка с нескорректированным тербанком (не используются синонимы для замены)
	 * @return
	 */
	Code getOriginalTbCode();

   /**
    * Тип дополнительной карты.
    *
    * @return null для основной карты, тип для дополнительной
    */
   AdditionalCardType getAdditionalCardType();
   /**
    * Описание статуса карты
    *
    * @return строковое представление статуса карты
    */
   String getStatusDescription();
   /**
    * Получение текущего статуса карты(активная, закрытая, заблокированная...)
    *
    * @return CardState
    */
   CardState getCardState();

	/**
    * Получение текущего статуса карточного счета
	* на момент реализации метода заполненяется только для арестованных карточных счетов,
	* в остальных случаях возвращается null
    *
    * @return AccountState
    */
   AccountState getCardAccountState();
   /**
    * Получить валюту карты.
    */
   Currency getCurrency();
   /**
    * Получить номер основной карты для дополнительной.
    */
   String getMainCardNumber();

	/**
	 * Признак того, что карта является виртуальной
	 *
 	 * @return true - карта виртуальная, false - обычная карта
	 */
   boolean isVirtual();

   /**
    * Получить номер СКС
	*
	* @return String
	*/
   String getPrimaryAccountNumber();

   /**
    * Получить внешний идентификатор СКС
	*
	* @return String
	*/
   String getPrimaryAccountExternalId();

	/**
	 * Вид счета
	 * @return Long
	 */
	Long getKind();

	/**
	 * Подвид счета
	 * @return Long
	 */
	Long getSubkind();

	/**
	 * @return тип карты.
	 */
	String getUNICardType();

	/**
	 * Тип счета.
	 * Допустимые значения:
	 * B – Корпоративная
	 * D – Студенческая
	 * I – Личная
	 * K – Кредитная в рамках «Кредитная фабрики»
	 * L – Льготная
	 * M – Молодежная
	 * N – Зарплатная с овердрафтом
	 * O – Зарплатная с овердрафтом для сотрудников Сбербанка России
	 * P – Представительская
	 * R – Привилегированная
	 * S – Социальная
	 * U – Корпоративная для сотрудников Сбербанка России
	 * X – Студенческая (в рамках зарплатного проекта)
	 * Z – Зарплатная
	 * C – Кредитная
	 * F – Momentum
	 * Карта зарплатная, если тип in ('Z', 'O', 'N'), иначе карта личная
	 * @return тип счета.
	 */
	String getUNIAccountType();

	/**
	 * @return Вид карты
	 */
	CardLevel getCardLevel();

	/**
	 * @return признак принадлежности карты к бонусной программе
	 */
	CardBonusSign getCardBonusSign();

	/**
	 * @return внешний идентификатор клиента
	 */
	String getClientId();

	/**
	 * @return использовать ли подписку
	 */
	boolean isUseReportDelivery();

	/**
	 * @return e-mail
	 */
	String getEmailAddress();

	/**
	 * @return тип данных отчета в подписке
	 */
	ReportDeliveryType getReportDeliveryType();

	/**
	 * @return язык данных отчета в подписке
	 */
	ReportDeliveryLanguage getReportDeliveryLanguage();

	/**
	 * Лимит покупок.
	 * null - значит лимита нет.
	 * @return доступный лимит на покупки
	 */
	Money getPurchaseLimit();

	/**
	 * Лимит на снятия наличных
	 *
	 * @return лимит на снятия наличных или null, если карта безлимитная
	 */
	Money getAvailableCashLimit();

	/**
	 * Фамилия владельца карты (то, что выбито на карте)
	 * Domain: Text
	 *
	 * @return фамилия владельца карты
	 */
	String getHolderName();

	/**
	 * Номер договора
	 * @return номер договора
	 */
	String getContractNumber();

    /**
     * Дата очередного отчёта по счету карты
     * @return дата отчёта
     */
    Calendar getNextReportDate();

	/**
	 * @return Лимит овердрафта
	 */
	Money getOverdraftLimit();

	/**
	 * @return Общая сумма долга
	 */
	Money getOverdraftTotalDebtSum();

	/**
	 * Сумма минимального платежа.
	 *
	 * @return Сумма минимального платежа или null, если для этой карты такой суммы нет.
	 */
	Money getOverdraftMinimalPayment();

	/**
	 * Дата минимального платежа.
	 *
	 * @return Дата минимального платежа или null, если для данной карты не предусмотрено
	 */
	Calendar getOverdraftMinimalPaymentDate();

	/**
	 * @return Сумма собственных средств.
	 */
	Money getOverdraftOwnSum();

	/**
	 * @return владелец карты
	 */
	Client getCardClient();
}