package com.rssl.phizic.gate.payments.loan;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.loanclaim.dictionary.*;
import com.rssl.phizic.gate.loanclaim.type.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Set;

/**
 * @author Erkin
 * @ created 13.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Расширенная заявка на кредит
 */
public interface ETSMLoanClaim extends SynchronizableDocument
{
	/**
	 * Заполняется при создании документа (дата создания заявки)
	 * @return дата подписания заявки (never null)
	 */
	Calendar getSigningDate();

	/**
	 * Возвращает подразделение для оформления заявки
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return код подразделения в кодировке Transact SM (never null nor empty)
	 */
	String getClaimDrawDepartmentETSMCode();

	/**
	 * @return Тер банк в котором оформлена заявка.
	 */
	String getTb();

	/**
	 * Заполняется на Шаге Выбор параметров кредита (до создания заявки)
	 * @return тип (вид) кредитного продукта (never null nor empty)
	 */
	String getLoanProductType();

	/**
	 * Заполняется на Шаге Выбор параметров кредита (до создания заявки)
	 * @return код кредитного продукта (never null nor empty)
	 */
	String getLoanProductCode();

	/**
	 * Заполняется на Шаге Выбор параметров кредита (до создания заявки)
	 * @return код кредитного субпродукта (never null nor empty)
	 */
	String getLoanSubProductCode();

	/**
	 * Заполняется на Шаге Выбор параметров кредита (до создания заявки)
	 * @return сумма кредита (never null)
	 */
	Money getLoanAmount();

	/**
	 * Заполняется на Шаге Выбор параметров кредита (до создания заявки)
	 * @return срок кредита в месяцах (never null)
	 */
	Long getLoanPeriod();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return способ выдачи кредита (never null)
	 */
	LoanIssueMethod getLoanIssueMethod();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return номер счета в Сбербанке для выдачи кредита (can be null)
	 * Обязательно для выдачи на имеющийся вклад и на имеющийся текущий счет
	 */
	String getLoanIssueAccount();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return номер карты для выдачи кредита (can be null)
	 * Обязательно для выдачи на имеющуюся карту
	 */
	String getLoanIssueCard();

	/**
	 * Заполняется при создании документа (константа MAIN_DEBITOR)
	 * @return тип участника сделки (never null)
	 */
	ApplicantType getApplicantType();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return ФИО заёмщика (never null)
	 */
	PersonName getApplicantName();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return причина смена ФИО или null, если смены ФИО не было
	 */
	NameChangeReason getApplicantNameChangeReason();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return описание причины смены ФИО или null, если смены ФИО не было
	 */
	String getApplicantNameChangeDescription();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return дата смены ФИО или null, если смены ФИО не было
	 */
	Calendar getApplicantNameChangeDate();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return предыдущие ФИО заёмщика или null, если смены ФИО не было
	 */
	PersonName getApplicantPreviousName();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return пол заёмщика (never null)
	 */
	Gender getApplicantGender();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return дата рождения заёмщика (never null)
	 */
	Calendar getApplicantBirthDay();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return место рождения заёмщика (never null)
	 */
	String getApplicantBirthPlace();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return гражданство заёмщика (never null)
	 */
	Citizenship getApplicantCitizenship();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return true, если у заёмщика есть загран паспорт
	 */
	boolean getApplicantForeignPassportFlag();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return электрическая почта заёмщика (can be null)
	 */
	String getApplicantEmail();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return список контактных телефонов заёмщика (never empty)
	 */
	Collection<Phone> getApplicantPhones();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return ИНН заёмщика (can be null)
	 */
	String getApplicantTaxID();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return образование заёмщика (never null)
	 */
	Education getApplicantEducation();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return курс неоконченного высшего образования заёмщика
	 */
	Integer getApplicantUnfinishedYear();

	/**
	 * Заполняется на Шаге 3 Прописка
	 * @return адрес фактического проживания заёмщика (never null)
	 */
	Address getApplicantResidenceAddress();

	/**
	 * Заполняется на Шаге 3 Прописка
	 * @return адрес постоянной регистрации заёмщика (can be null)
	 */
	Address getApplicantFixedAddress();

	/**
	 * Заполняется на Шаге 3 Прописка
	 * @return адрес временной регистрации заёмщика (can be null)
	 */
	Address getApplicantTemporaryAddress();

	/**
	 * Заполняется на Шаге 3 Прописка
	 * @return дата истечения временной регистрации заёмщика (can be null)
	 * not null, если указан адрес временной регистрации
	 */
	Calendar getApplicantResidenceExpiryDate();

	/**
	 * Заполняется на Шаге 3 Прописка
	 * @return срок проживания заёмщика в населенном пункте на момент заполнения анкеты (лет)
	 */
	int getApplicantCityResidencePeriod();

	/**
	 * Заполняется на Шаге 3 Прописка
	 * @return срок проживания заёмщика по фактическому адресу (лет)
	 */
	int getApplicantActualResidencePeriod();

	/**
	 * Заполняется на Шаге 3 Прописка
	 * @return право проживания заёмщика (never null)
	 */
	ResidenceRight getApplicantResidenceRight();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return данные настоящего паспорта заёмщика (never null)
	 */
	Passport getApplicantPassport();

	/**
	 * Заполняется на Шаге 1 Персональные данные
	 * @return данные предыдущего паспорта заёмщика (can be null)
	 */
	Passport getApplicantPreviousPassport();

	/**
	 * Заполняется на Шаге 2 Семья и родственники
	 * @return семейное положение заёмщика (never null)
	 */
	FamilyStatus getApplicantFamilyStatus();

	/**
	 * Заполняется на Шаге 2 Семья и родственники
	 * @return данные о супруге заёмщика (can be null)
	 */
	Spouse getApplicantSpouse();

	/**
	 * Заполняется на Шаге 2 Семья и родственники
	 * @return данные о родственниках заёмщика, включая детей (can be empty)
	 */
	Collection<Relative> getApplicantRelatives();

	/**
	 * Заполняется на Шаге 4 Работа и доход
	 * @return вид занятости заёмщика (never null)
	 */
	WorkOnContract getApplicantEmploymentType();

	/**
	 * Заполняется на Шаге 4 Работа и доход
	 * @return организация, в которой состоит/работает заёмщик (null для пенсионеров и частных предпринимателей)
	 */
	Organization getApplicantOrganization();

	/**
	 * Заполняется на Шаге 4 Работа и доход
	 * @return информация о занятости заёмщика (can be null)
	 */
	Employee getApplicantAsEmployee();

	/**
	 * Заполняется на Шаге 4 Работа и доход
	 * @return информация о занятости в СБ, если заёмщик - сотрудник СБ (can be null)
	 */
	SBEmployee getApplicantAsSBEmployee();

	/**
	 * Заполняется на Шаге 4 Работа и доход
	 * @return описание частной практики заёмщика (can be null)
	 */
	String getApplicantOwnBusinessDescription();

	/**
	 * Заполняется на Шаге 4 Работа и доход
	 * @return среднемесячный основной доход заёмщика в рублях (never null)
	 */
	BigDecimal getApplicantBasicIncome();

	/**
	 * Заполняется на Шаге 4 Работа и доход
	 * @return среднемесячный дополнительный доход заёмщика в рублях (never null)
	 */
	BigDecimal getApplicantAdditionalIncome();

	/**
	 * Заполняется на Шаге 4 Работа и доход
	 * @return среднемесячный дополнительный семьи заёмщика в рублях (never null)
	 */
	BigDecimal getApplicantFamilyIncome();

	/**
	 * Заполняется на Шаге 4 Работа и доход
	 * @return среднемесячные расходы заёмщика в рублях (never null)
	 */
	BigDecimal getApplicantExpenses();

	/**
	 * Заполняется на Шаге 5 Собственность и долги
	 * @return недвижимость в собственности заёмщика (can be empty)
	 */
	Collection<RealEstate> getApplicantRealEstates();

	/**
	 * Заполняется на Шаге 5 Собственность и долги
	 * @return транспортные средства в собственности заёмщика (can be empty)
	 */
	Collection<Vehicle> getApplicantVehicles();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация (константа false)
	 * @return true, если заёмщик согласен на участие в программе добровольного страхования жизни и здоровья
	 */
	boolean getApplicantInsuranceFlag();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return Страховой номер индивидуального счета заёмщика (can be null)
	 */
	String getApplicantSNILS();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return true, если заёмщик согласен на запрос информации банком из БКИ
	 */
	boolean getApplicantBKIRequestFlag();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return true, если заёмщик согласен на предоставление банком информации в БКИ
	 */
	boolean getApplicantBKIGrantFlag();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return true, если заёмщик согласен на предоставление банком информации в ПФР
	 */
	boolean getApplicantPFRGrantFlag();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return код субъекта кредитной истории заёмщика (can be null)
	 */
	String getApplicantCreditHistoryCode();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return Согласие клиента на получение кредитной карты ОАО "Сбербанк России" при принятии такого решения банком true - да false - нет
	 */
	boolean getApplicantGetCreditCardFlag();

	/**
	 * Заполняется при создании документа (константа false)
	 * @return true, если заёмщик является клиентом, требующим особого внимания
	 */
	boolean getApplicantSpecialAttentionFlag();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return количество обыкновенных акций Сбербанка России в собственности заёмщика
	 * если заёмщик не является акционером сбербанка, возвращается 0
	 */
	int getApplicantSBCommonSharesCount();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return количество привилегированных акций Сбербанка России в собственности заёмщика
	 * если заёмщик не является акционером сбербанка, возвращается 0
	 */
	int getApplicantSBPreferenceSharesCount();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return номера зарплатных карт заёмщика (can be empty)
	 */
	Collection<String> getApplicantSalaryCards();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return номера пенсионных карт заёмщика (can be empty)
	 */
	Collection<String> getApplicantPensionCards();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return номера зарплатных депозитов заёмщика (can be empty)
	 */
	Collection<String> getApplicantSalaryDeposits();

	/**
	 * Заполняется на Шаге 6 Дополнительная информация
	 * @return номера пенсионных депозитов заёмщика (can be empty)
	 */
	Collection<String> getApplicantPensionDeposits();

	/**
	 * Сбросить статус обработки заявки в Transact SM.
	 * То есть зачистить поля ETSM
	 */
	void resetClaimStatus();

	/**
	 * Заполняется при создании документа
	 * @return номер заявки
	 */
	String getOperationUID();

	/**
	 * Возвращает признак полной заявки
	 * @return true, если заявка заполнена полностью
	 */
	boolean getCompleteAppFlag();

	/**
	 * Возвращает канал продаж
	 * @return
	 */
	ChannelType getChannel();

	/**
	 * @return Канал получения согласия на обработку персональных данных в БКИ
	 */
	ChannelCBRegAApproveType getChannelCBRegAApprove();

	/**
	 * @return Канал получения согласия на обработку персональных данных ПФР
	 */
	ChannelPFRRegAApproveType getChannelPFRRegAApprove();

	/**
	 * @return Список вопросов анкеты
	 */
	Collection<Question> getQuestions();

	Boolean getOwnerGuestMbk();

	/**
	 * Кредитный инспектор (КИ), отправивший заявку на обработку в ETSM
	 */
	String getLoginKI();

	/**
	 * @return список TOP-UP. Может быть null или пустым
	 */
	Set<ClaimOfferTopUp> getTopUps();
}
