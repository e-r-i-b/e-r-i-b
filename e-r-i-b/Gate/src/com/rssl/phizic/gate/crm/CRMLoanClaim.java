package com.rssl.phizic.gate.crm;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.PhoneNumber;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 12.12.14
 * Time: 17:05
 * Кредитная заявка в CRM
 */
public interface CRMLoanClaim
{
	/**
	 *  Уникальный  номер заявки во внешней системе (номер/идентификатор заявки в ЕРИБ) [1] [макс. длина=50]
	 */
	public String getNumber();
	public void setNumber(String number);
	/**
	 * Источник заявки, он же Канал создания заявки (1=Web, 2=ТМ внутренний, 3=ТМ внешний, 4=ВСП, 5=ЕРИБ-СБОЛ, 6=ЕРИБ-МП, 7=ЕРИБ-УС, 8=ЕРИБ-МБ, 9=ЕРИБ-ГОСТЕВОЙ) [1]
	 */
	public ChannelType getChannelType();
	public void setChannelType(ChannelType channel);
	/**
	 *  Логин сотрудника, оформившего заявку в ЕРИБ (Login сотрудника в СУДИР) [0-1]
	 */
	public String getEmployerLogin();
	public void setEmployerLogin(String employer);
	/**
	 *  ФИО сотрудника, оформившего заявку ЕРИБ [0-1]
	 */
	public String getEmployerFIO();
	public void setEmployerFIO(String fio);
	/**
	 * Имя клиента [1]
	 */
	public String getFirstName();
	public void setFirstName(String firstName);
	/**
	 * Фамилия клиента [1]
	 */
	public String getLastName();
	public void setLastName(String lastName);
	/**
	 * Отчество клиента [0-1]
	 */
	public String getMiddleName();
	public void setMiddleName(String middleName);
	/**
	 * Дата рождения [1]
	 */
	public Calendar getBirthDay();
	public void setBirthDay(Calendar birthDay);
	/**
	 * Номер паспорта РФ: серия + номер через пробел [1]
	 */
	public String getPassportNumber();
	public void setPassportNumber(String passportNumber);
	/**
	 *  Номер карты в Way (идентификатор карты в карточной системе). Для случая,  если клиент откликнулся в канале «УС» [0-1]
	 */
	public String getWayCardNumber();
	public void setWayCardNumber(String wayCardNumber);
	/**
	 * Идентификатор участника кампании.  Заполняется в случае участия клиента в кампании [0-1]
	 */
	public String getCampaingMemberId();
	public void setCampaingMemberId(String campaingMemberId);
	/**
	 * Мобильный телефон [1]
	 */
	public PhoneNumber getMobilePhone();
	public void setMobilePhone(PhoneNumber mobilePhone);
	/**
	 * Рабочий телефон [0-1]
	 */
	public PhoneNumber getWorkPhone();
	public void setWorkPhone(PhoneNumber workPhone);
	/**
	 * Дополнительный  телефон [0-1]
	 */
	public PhoneNumber getAddPhone();
	public void setAddPhone(PhoneNumber addPhone);
	/**
	 * Наименование продукта из справочника кредитных продуктов ЕРИБ  [1]
	 *
	 */
	 public String getProductName();
	 public void setProductName(String productName);

	/**
	 * Код типа продукта в TSM [0-1]
	 */
	public String getTargetProductType();
	public void setTargetProductType(String targetProductType);
	/**
	 * Код продукта в TSM [0-1]
	 */
	public String getTargetProduct();
	public void setTargetProduct(String targetProduct);
	/**
	 * Тип продукта заявки, самый верхний уровень – кредит ("Consumer Credit") [1]
	 */
//	public String getProductType();
//	public void setProductType(String productType);
	/**
	 * Код субпродукта в TSM [0-1]
	 */
	public String getTargetProductSub();
	public void setTargetProductSub(String targetProductSub);
	/**
	 * Запрашиваемая сумма кредита/лимита [1]
	 */
	public Money getLoanAmount();
	public void setLoanAmount(Money currency);
	/**
	 * Срок кредитования.  Кол-во месяцев. Целое число в интервале 1-360. [1]
	 */
	public int getDuration();
	public void setDuratione(int duration);

	/**
	 * Процентная ставка, %.  Число с точностью до двух знаков после запятой в интервале от 0 до 100. [1]
	 */
	public BigDecimal getInterestRate();
	public void setInterestRate(BigDecimal interestRate);
	/**
	 * Комментарии [0-1]
	 */
	public String getComments();
	public void setComments(String comments);
	/**
	 * Подразделение Банка из справочника «Подразделения банка»[1]
	 */
	public Code getOfficeCode();
	public void setOfficeCode(Code code);

	/**
	 * Планируемое дата визита в ВСП [0-1]
	 */
	public Calendar getPlannedVisitDate();
	public void setPlannedVisitDate(Calendar dateTime);

	/**
	 * Планируемое время визита в ВСП [0-1]
	 *   1	с 09:00 до 11:00
	 *	 2	с 11:00 до 13:00
	 *   3	с 13:00 до 15:00
	 *   4	с 15:00 до 17:00
	 *   5	с 17:00 до 19:00
	 *   6	с 19:00 до 20:00
	 */
	public Integer getPlannedVisitTime();
	public void setPlannedVisitTime(Integer plannedVisitTime);
}
