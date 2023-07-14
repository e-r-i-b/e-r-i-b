package com.rssl.phizic.business.bki;

import com.rssl.phizic.utils.PhoneNumber;

import java.util.Calendar;

/**
 * Кредитный договор для отображения общих сведений о кредитном договоре в кредитной истории
 * @author Rtischeva
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 */
public class CreditContract
{
	/**
	 * Кредитор
	 */
	private String bankName;

	/**
	 * Тип финансирования
	 */
	private String financeType;

	/**
	 * Цель финансирования
	 */
	private String purposeOfFinance;

	/**
	 * Тип обеспечения
	 */
	private String typeOfSecurity;

	/**
	 * Тип владельца счета
	 */
	private String applicantType;

	/**
	 * Срок кредита
	 */
	private Duration duration;

	/**
	 * Причина закрытия
	 */
	private String reasonForClosure;

	/**
	 * Комментарий
	 */
	private String comment;

	/**
	 * Сумма кредита
	 */
	private Money amount;

	/**
	 * Кредитный лимит
	 */
	private Money creditLimit;

	/**
	 * Размер платежа
	 */
	private Money instalment;

	/**
	 * Остаток по кредиту. Всего к выплате.
	 */
	private Money balance;

	/**
	 * Сумма просрочки
	 */
	private Money arrearsBalance;

	/**
	 * Дата открытия
	 */
	private Calendar openDate;

	/**
	 * Дата последнего платежа
	 */
	private Calendar lastPaymentDate;

	/**
	 * Дата последнего пропущенного платежа
	 */
	private Calendar lastMissedPaymentDate;

	/**
	 * Дата исполнения обязательств
	 */
	private Calendar fulfilmentDate;

	/**
	 * Дата закрытия кредита (Фактическая)
	 */
	private Calendar closedDate;

	/**
	 * Дата судебного рассмотрения
	 */
	private Calendar litigationDate;

	/**
	 * Дата списания
	 */
   private Calendar writeOffDate;

	//////////////////////////////
	// Информация о заемщике

	/**
	 * Имя заемщика
	 */
	private String firstName;

	/**
	 * Фамилия заемщика
	 */
	private String surname;

	/**
	 * Отчество заемщика
	 */
	private String patronymic;

	/**
	 * Дата рождения
	 */
	private Calendar birthday;

	/**
	 * Место рождения
	 */
	private String birthPlace;

	/**
	 * Тип документа, удостоверяющего личность
	 */
	private String personDocumentType;

	/**
	 * Номер документа, удостоверяющего личность
	 */
	private String personDocumentNumber;

	/**
	 * Адрес регистрации
	 */
	private String registrationAddress;

	/**
	 * Адрес проживания
	 */
	private String residentialAddress;

	/**
	 * номер мобильного телефона
	 */
	private PhoneNumber mobilePhoneNumber;

	/**
	 * ИНН
	 */
	private String inn;

	/**
	 *  Номер свидетельства гос. пенсионного страхования
	 */
	private String pensionNumber;

	/**
	 * Номер свидетельства ИП
	 */
	private String entrepreneurNumber;

	//////////////////////////////
	// Источник кредитной истории

	/**
	 * Название субъекта, который предоставил кредит
	 */
	private String subscriberName;

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public String getFinanceType()
	{
		return financeType;
	}

	public void setFinanceType(String financeType)
	{
		this.financeType = financeType;
	}

	public String getPurposeOfFinance()
	{
		return purposeOfFinance;
	}

	public void setPurposeOfFinance(String purposeOfFinance)
	{
		this.purposeOfFinance = purposeOfFinance;
	}

	public String getTypeOfSecurity()
	{
		return typeOfSecurity;
	}

	public void setTypeOfSecurity(String typeOfSecurity)
	{
		this.typeOfSecurity = typeOfSecurity;
	}

	public String getApplicantType()
	{
		return applicantType;
	}

	public void setApplicantType(String applicantType)
	{
		this.applicantType = applicantType;
	}

	public Duration getDuration()
	{
		return duration;
	}

	public void setDuration(Duration duration)
	{
		this.duration = duration;
	}

	public String getReasonForClosure()
	{
		return reasonForClosure;
	}

	public void setReasonForClosure(String reasonForClosure)
	{
		this.reasonForClosure = reasonForClosure;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public Money getCreditLimit()
	{
		return creditLimit;
	}

	public void setCreditLimit(Money creditLimit)
	{
		this.creditLimit = creditLimit;
	}

	public Money getInstalment()
	{
		return instalment;
	}

	public void setInstalment(Money instalment)
	{
		this.instalment = instalment;
	}

	public Money getBalance()
	{
		return balance;
	}

	public void setBalance(Money balance)
	{
		this.balance = balance;
	}

	public Money getArrearsBalance()
	{
		return arrearsBalance;
	}

	public void setArrearsBalance(Money arrears)
	{
		this.arrearsBalance = arrears;
	}

	public Calendar getOpenDate()
	{
		return openDate;
	}

	public void setOpenDate(Calendar openDate)
	{
		this.openDate = openDate;
	}

	public Calendar getLastPaymentDate()
	{
		return lastPaymentDate;
	}

	public void setLastPaymentDate(Calendar lastPaymentDate)
	{
		this.lastPaymentDate = lastPaymentDate;
	}

	public Calendar getLastMissedPaymentDate()
	{
		return lastMissedPaymentDate;
	}

	public void setLastMissedPaymentDate(Calendar lastMissedPaymentDate)
	{
		this.lastMissedPaymentDate = lastMissedPaymentDate;
	}

	public Calendar getFulfilmentDate()
	{
		return fulfilmentDate;
	}

	public void setFulfilmentDate(Calendar fulfilmentDate)
	{
		this.fulfilmentDate = fulfilmentDate;
	}

	public Calendar getClosedDate()
	{
		return closedDate;
	}

	public void setClosedDate(Calendar closedDate)
	{
		this.closedDate = closedDate;
	}

	public Calendar getLitigationDate()
	{
		return litigationDate;
	}

	public void setLitigationDate(Calendar litigationDate)
	{
		this.litigationDate = litigationDate;
	}

	public Calendar getWriteOffDate()
	{
		return writeOffDate;
	}

	public void setWriteOffDate(Calendar writeOffDate)
	{
		this.writeOffDate = writeOffDate;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public String getPatronymic()
	{
		return patronymic;
	}

	public void setPatronymic(String patronymic)
	{
		this.patronymic = patronymic;
	}

	public Calendar getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	public String getBirthPlace()
	{
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace)
	{
		this.birthPlace = birthPlace;
	}

	public String getPersonDocumentType()
	{
		return personDocumentType;
	}

	public void setPersonDocumentType(String personDocumentType)
	{
		this.personDocumentType = personDocumentType;
	}

	public String getPersonDocumentNumber()
	{
		return personDocumentNumber;
	}

	public void setPersonDocumentNumber(String personDocumentNumber)
	{
		this.personDocumentNumber = personDocumentNumber;
	}

	public String getRegistrationAddress()
	{
		return registrationAddress;
	}

	public void setRegistrationAddress(String registrationAddress)
	{
		this.registrationAddress = registrationAddress;
	}

	public String getResidentialAddress()
	{
		return residentialAddress;
	}

	public void setResidentialAddress(String residentialAddress)
	{
		this.residentialAddress = residentialAddress;
	}

	public PhoneNumber getMobilePhoneNumber()
	{
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(PhoneNumber mobilePhoneNumber)
	{
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public String getInn()
	{
		return inn;
	}

	public void setInn(String inn)
	{
		this.inn = inn;
	}

	public String getPensionNumber()
	{
		return pensionNumber;
	}

	public void setPensionNumber(String pensionNumber)
	{
		this.pensionNumber = pensionNumber;
	}

	public String getEntrepreneurNumber()
	{
		return entrepreneurNumber;
	}

	public void setEntrepreneurNumber(String entrepreneurNumber)
	{
		this.entrepreneurNumber = entrepreneurNumber;
	}

	public String getSubscriberName()
	{
		return subscriberName;
	}

	public void setSubscriberName(String subscriberName)
	{
		this.subscriberName = subscriberName;
	}
}
