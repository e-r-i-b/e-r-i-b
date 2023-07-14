package com.rssl.phizic.operations.pfp.admin;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 11.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Сущность записи журнала прохождения пфп
 */

public class UnloadPFPJournalEntity
{
	private Long pfpId;
	private Calendar creationDate;
	private Calendar executionDate;

	private String stateCode;
	private String stateDescription;
	private Long targets;
	private String riskProfileName;

	private String userTB;
	private String userFirstName;
	private String userSurName;
	private String userPatrName;
	private Calendar userBirthday;
	private String userDocumentName;
	private String userDocumentType;
	private String userDocumentSeries;
	private String userDocumentNumber;
	private BigDecimal userVirtualBalance;
	private BigDecimal userBalanceOtherBanks;
	private BigDecimal userBalanceCash;
	private BigDecimal totalBalanceInvestments;
	private String userCreditCardType;
	private String userEmail;
	private String userMobilePhone;

	private String managerFIO;
	private String managerId;
	private String managerOSB;
	private String managerVSP;
	private String channelName;

	/**
	 * @return идентификатор планирования
	 */
	public Long getPfpId()
	{
		return pfpId;
	}

	/**
	 * задать идентификатор планирования
	 * @param pfpId идентификатор планирования
	 */
	public void setPfpId(Long pfpId)
	{
		this.pfpId = pfpId;
	}

	/**
	 * @return дата создания планирования
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * задать дату создания планирования
	 * @param creationDate дата создания планирования
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return дата завершения планирования
	 */
	public Calendar getExecutionDate()
	{
		return executionDate;
	}

	/**
	 * задать дату завершения планирования
	 * @param executionDate дата завершения планирования
	 */
	public void setExecutionDate(Calendar executionDate)
	{
		this.executionDate = executionDate;
	}

	/**
	 * @return код статуса планирования
	 */
	public String getStateCode()
	{
		return stateCode;
	}

	/**
	 * задать код статуса планирования
	 * @param stateCode код статуса планирования
	 */
	public void setStateCode(String stateCode)
	{
		this.stateCode = stateCode;
	}

	/**
	 * @return описание статуса планирования
	 */
	public String getStateDescription()
	{
		return stateDescription;
	}

	/**
	 * задать описание статуса планирования
	 * @param stateDescription описание статуса планирования
	 */
	public void setStateDescription(String stateDescription)
	{
		this.stateDescription = stateDescription;
	}

	/**
	 * @return количество целей
	 */
	public Long getTargets()
	{
		return targets;
	}

	/**
	 * задать количество целей
	 * @param targets количество целей
	 */
	public void setTargets(Long targets)
	{
		this.targets = targets;
	}

	/**
	 * @return название риск-профиля
	 */
	public String getRiskProfileName()
	{
		return riskProfileName;
	}

	/**
	 * задать название риск-профиля
	 * @param riskProfileName название риск-профиля
	 */
	public void setRiskProfileName(String riskProfileName)
	{
		this.riskProfileName = riskProfileName;
	}

	/**
	 * @return тербанк Клиента
	 */
	public String getUserTB()
	{
		return userTB;
	}

	/**
	 * задать тербанк Клиента
	 * @param userTB тербанк Клиента
	 */
	public void setUserTB(String userTB)
	{
		this.userTB = userTB;
	}

	/**
	 * @return имя клиента
	 */
	public String getUserFirstName()
	{
		return userFirstName;
	}

	/**
	 * задать имя клиента
	 * @param userFirstName имя клиента
	 */
	public void setUserFirstName(String userFirstName)
	{
		this.userFirstName = userFirstName;
	}

	/**
	 * @return фамилия клиента
	 */
	public String getUserSurName()
	{
		return userSurName;
	}

	/**
	 * задать фамилию клиента
	 * @param userSurName фамилия клиента
	 */
	public void setUserSurName(String userSurName)
	{
		this.userSurName = userSurName;
	}

	/**
	 * @return отчество клиента
	 */
	public String getUserPatrName()
	{
		return userPatrName;
	}

	/**
	 * задать отчество клиента
	 * @param userPatrName отчество клиента
	 */
	public void setUserPatrName(String userPatrName)
	{
		this.userPatrName = userPatrName;
	}

	/**
	 * @return ДР клиента
	 */
	public Calendar getUserBirthday()
	{
		return userBirthday;
	}

	/**
	 * задать ДР клиента
	 * @param userBirthday ДР клиента
	 */
	public void setUserBirthday(Calendar userBirthday)
	{
		this.userBirthday = userBirthday;
	}

	/**
	 * @return название документа
	 */
	public String getUserDocumentName()
	{
		return userDocumentName;
	}

	/**
	 * задать название документа
	 * @param userDocumentName название документа
	 */
	public void setUserDocumentName(String userDocumentName)
	{
		this.userDocumentName = userDocumentName;
	}

	/**
	 * @return тип документа
	 */
	public String getUserDocumentType()
	{
		return userDocumentType;
	}

	/**
	 * задать тип документа
	 * @param userDocumentType тип документа
	 */
	public void setUserDocumentType(String userDocumentType)
	{
		this.userDocumentType = userDocumentType;
	}

	/**
	 * @return серия документа
	 */
	public String getUserDocumentSeries()
	{
		return userDocumentSeries;
	}

	/**
	 * задать серию документа
	 * @param userDocumentSeries серия документа
	 */
	public void setUserDocumentSeries(String userDocumentSeries)
	{
		this.userDocumentSeries = userDocumentSeries;
	}

	/**
	 * @return номер документа
	 */
	public String getUserDocumentNumber()
	{
		return userDocumentNumber;
	}

	/**
	 * задать номер документа
	 * @param userDocumentNumber номер документа
	 */
	public void setUserDocumentNumber(String userDocumentNumber)
	{
		this.userDocumentNumber = userDocumentNumber;
	}

	/**
	 * @return Виртуальный баланс
	 */
	public BigDecimal getUserVirtualBalance()
	{
		return userVirtualBalance;
	}

	/**
	 * задать Виртуальный баланс
	 * @param userVirtualBalance Виртуальный баланс
	 */
	public void setUserVirtualBalance(BigDecimal userVirtualBalance)
	{
		this.userVirtualBalance = userVirtualBalance;
	}

	/**
	 * @return тип кредитной карты
	 */
	public String getUserCreditCardType()
	{
		return userCreditCardType;
	}

	/**
	 * задать тип кредитной карты
	 * @param userCreditCardType тип кредитной карты
	 */
	public void setUserCreditCardType(String userCreditCardType)
	{
		this.userCreditCardType = userCreditCardType;
	}

	/**
	 * @return e-mail клиента
	 */
	public String getUserEmail()
	{
		return userEmail;
	}

	/**
	 * задать e-mail клиента
	 * @param userEmail e-mail клиента
	 */
	public void setUserEmail(String userEmail)
	{
		this.userEmail = userEmail;
	}

	/**
	 * @return мобильный телефоно клиента
	 */
	public String getUserMobilePhone()
	{
		return userMobilePhone;
	}

	/**
	 * задать мобильный телефоно клиента
	 * @param userMobilePhone мобильный телефоно клиента
	 */
	public void setUserMobilePhone(String userMobilePhone)
	{
		this.userMobilePhone = userMobilePhone;
	}

	/**
	 * @return ФИО менеджера
	 */
	public String getManagerFIO()
	{
		return managerFIO;
	}

	/**
	 * задать ФИО менеджера
	 * @param managerFIO ФИО менеджера
	 */
	public void setManagerFIO(String managerFIO)
	{
		this.managerFIO = managerFIO;
	}

	/**
	 * @return идентификатор менеджера
	 */
	public String getManagerId()
	{
		return managerId;
	}

	/**
	 * задать идентификатор менеджера
	 * @param managerId идентификатор менеджера
	 */
	public void setManagerId(String managerId)
	{
		this.managerId = managerId;
	}

	/**
	 * @return ОСБ менеджера
	 */
	public String getManagerOSB()
	{
		return managerOSB;
	}

	/**
	 * задать ОСБ менеджера
	 * @param managerOSB ОСБ менеджера
	 */
	public void setManagerOSB(String managerOSB)
	{
		this.managerOSB = managerOSB;
	}

	/**
	 * @return ВСП менеджера
	 */
	public String getManagerVSP()
	{
		return managerVSP;
	}

	/**
	 * задать ВСП менеджера
	 * @param managerVSP ВСП менеджера
	 */
	public void setManagerVSP(String managerVSP)
	{
		this.managerVSP = managerVSP;
	}

	/**
	 * @return название канала
	 */
	public String getChannelName()
	{
		return channelName;
	}

	/**
	 * @param channelName название канала
	 */
	public void setChannelName(String channelName)
	{
		this.channelName = channelName;
	}

	public BigDecimal getTotalBalanceInvestments()
	{
		return totalBalanceInvestments;
	}

	public void setTotalBalanceInvestments(BigDecimal totalBalanceInvestments)
	{
		this.totalBalanceInvestments = totalBalanceInvestments;
	}

	public BigDecimal getUserBalanceCash()
	{
		return userBalanceCash;
	}

	public void setUserBalanceCash(BigDecimal userBalanceCash)
	{
		this.userBalanceCash = userBalanceCash;
	}

	public BigDecimal getUserBalanceOtherBanks()
	{
		return userBalanceOtherBanks;
	}

	public void setUserBalanceOtherBanks(BigDecimal userBalanceOtherBanks)
	{
		this.userBalanceOtherBanks = userBalanceOtherBanks;
	}
}
