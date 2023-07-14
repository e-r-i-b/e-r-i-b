package com.rssl.phizic.operations.pfp.admin;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 11.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� ������ ������� ����������� ���
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
	 * @return ������������� ������������
	 */
	public Long getPfpId()
	{
		return pfpId;
	}

	/**
	 * ������ ������������� ������������
	 * @param pfpId ������������� ������������
	 */
	public void setPfpId(Long pfpId)
	{
		this.pfpId = pfpId;
	}

	/**
	 * @return ���� �������� ������������
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * ������ ���� �������� ������������
	 * @param creationDate ���� �������� ������������
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return ���� ���������� ������������
	 */
	public Calendar getExecutionDate()
	{
		return executionDate;
	}

	/**
	 * ������ ���� ���������� ������������
	 * @param executionDate ���� ���������� ������������
	 */
	public void setExecutionDate(Calendar executionDate)
	{
		this.executionDate = executionDate;
	}

	/**
	 * @return ��� ������� ������������
	 */
	public String getStateCode()
	{
		return stateCode;
	}

	/**
	 * ������ ��� ������� ������������
	 * @param stateCode ��� ������� ������������
	 */
	public void setStateCode(String stateCode)
	{
		this.stateCode = stateCode;
	}

	/**
	 * @return �������� ������� ������������
	 */
	public String getStateDescription()
	{
		return stateDescription;
	}

	/**
	 * ������ �������� ������� ������������
	 * @param stateDescription �������� ������� ������������
	 */
	public void setStateDescription(String stateDescription)
	{
		this.stateDescription = stateDescription;
	}

	/**
	 * @return ���������� �����
	 */
	public Long getTargets()
	{
		return targets;
	}

	/**
	 * ������ ���������� �����
	 * @param targets ���������� �����
	 */
	public void setTargets(Long targets)
	{
		this.targets = targets;
	}

	/**
	 * @return �������� ����-�������
	 */
	public String getRiskProfileName()
	{
		return riskProfileName;
	}

	/**
	 * ������ �������� ����-�������
	 * @param riskProfileName �������� ����-�������
	 */
	public void setRiskProfileName(String riskProfileName)
	{
		this.riskProfileName = riskProfileName;
	}

	/**
	 * @return ������� �������
	 */
	public String getUserTB()
	{
		return userTB;
	}

	/**
	 * ������ ������� �������
	 * @param userTB ������� �������
	 */
	public void setUserTB(String userTB)
	{
		this.userTB = userTB;
	}

	/**
	 * @return ��� �������
	 */
	public String getUserFirstName()
	{
		return userFirstName;
	}

	/**
	 * ������ ��� �������
	 * @param userFirstName ��� �������
	 */
	public void setUserFirstName(String userFirstName)
	{
		this.userFirstName = userFirstName;
	}

	/**
	 * @return ������� �������
	 */
	public String getUserSurName()
	{
		return userSurName;
	}

	/**
	 * ������ ������� �������
	 * @param userSurName ������� �������
	 */
	public void setUserSurName(String userSurName)
	{
		this.userSurName = userSurName;
	}

	/**
	 * @return �������� �������
	 */
	public String getUserPatrName()
	{
		return userPatrName;
	}

	/**
	 * ������ �������� �������
	 * @param userPatrName �������� �������
	 */
	public void setUserPatrName(String userPatrName)
	{
		this.userPatrName = userPatrName;
	}

	/**
	 * @return �� �������
	 */
	public Calendar getUserBirthday()
	{
		return userBirthday;
	}

	/**
	 * ������ �� �������
	 * @param userBirthday �� �������
	 */
	public void setUserBirthday(Calendar userBirthday)
	{
		this.userBirthday = userBirthday;
	}

	/**
	 * @return �������� ���������
	 */
	public String getUserDocumentName()
	{
		return userDocumentName;
	}

	/**
	 * ������ �������� ���������
	 * @param userDocumentName �������� ���������
	 */
	public void setUserDocumentName(String userDocumentName)
	{
		this.userDocumentName = userDocumentName;
	}

	/**
	 * @return ��� ���������
	 */
	public String getUserDocumentType()
	{
		return userDocumentType;
	}

	/**
	 * ������ ��� ���������
	 * @param userDocumentType ��� ���������
	 */
	public void setUserDocumentType(String userDocumentType)
	{
		this.userDocumentType = userDocumentType;
	}

	/**
	 * @return ����� ���������
	 */
	public String getUserDocumentSeries()
	{
		return userDocumentSeries;
	}

	/**
	 * ������ ����� ���������
	 * @param userDocumentSeries ����� ���������
	 */
	public void setUserDocumentSeries(String userDocumentSeries)
	{
		this.userDocumentSeries = userDocumentSeries;
	}

	/**
	 * @return ����� ���������
	 */
	public String getUserDocumentNumber()
	{
		return userDocumentNumber;
	}

	/**
	 * ������ ����� ���������
	 * @param userDocumentNumber ����� ���������
	 */
	public void setUserDocumentNumber(String userDocumentNumber)
	{
		this.userDocumentNumber = userDocumentNumber;
	}

	/**
	 * @return ����������� ������
	 */
	public BigDecimal getUserVirtualBalance()
	{
		return userVirtualBalance;
	}

	/**
	 * ������ ����������� ������
	 * @param userVirtualBalance ����������� ������
	 */
	public void setUserVirtualBalance(BigDecimal userVirtualBalance)
	{
		this.userVirtualBalance = userVirtualBalance;
	}

	/**
	 * @return ��� ��������� �����
	 */
	public String getUserCreditCardType()
	{
		return userCreditCardType;
	}

	/**
	 * ������ ��� ��������� �����
	 * @param userCreditCardType ��� ��������� �����
	 */
	public void setUserCreditCardType(String userCreditCardType)
	{
		this.userCreditCardType = userCreditCardType;
	}

	/**
	 * @return e-mail �������
	 */
	public String getUserEmail()
	{
		return userEmail;
	}

	/**
	 * ������ e-mail �������
	 * @param userEmail e-mail �������
	 */
	public void setUserEmail(String userEmail)
	{
		this.userEmail = userEmail;
	}

	/**
	 * @return ��������� �������� �������
	 */
	public String getUserMobilePhone()
	{
		return userMobilePhone;
	}

	/**
	 * ������ ��������� �������� �������
	 * @param userMobilePhone ��������� �������� �������
	 */
	public void setUserMobilePhone(String userMobilePhone)
	{
		this.userMobilePhone = userMobilePhone;
	}

	/**
	 * @return ��� ���������
	 */
	public String getManagerFIO()
	{
		return managerFIO;
	}

	/**
	 * ������ ��� ���������
	 * @param managerFIO ��� ���������
	 */
	public void setManagerFIO(String managerFIO)
	{
		this.managerFIO = managerFIO;
	}

	/**
	 * @return ������������� ���������
	 */
	public String getManagerId()
	{
		return managerId;
	}

	/**
	 * ������ ������������� ���������
	 * @param managerId ������������� ���������
	 */
	public void setManagerId(String managerId)
	{
		this.managerId = managerId;
	}

	/**
	 * @return ��� ���������
	 */
	public String getManagerOSB()
	{
		return managerOSB;
	}

	/**
	 * ������ ��� ���������
	 * @param managerOSB ��� ���������
	 */
	public void setManagerOSB(String managerOSB)
	{
		this.managerOSB = managerOSB;
	}

	/**
	 * @return ��� ���������
	 */
	public String getManagerVSP()
	{
		return managerVSP;
	}

	/**
	 * ������ ��� ���������
	 * @param managerVSP ��� ���������
	 */
	public void setManagerVSP(String managerVSP)
	{
		this.managerVSP = managerVSP;
	}

	/**
	 * @return �������� ������
	 */
	public String getChannelName()
	{
		return channelName;
	}

	/**
	 * @param channelName �������� ������
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
