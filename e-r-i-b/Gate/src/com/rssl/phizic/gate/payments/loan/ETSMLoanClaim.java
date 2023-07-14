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
 * ����������� ������ �� ������
 */
public interface ETSMLoanClaim extends SynchronizableDocument
{
	/**
	 * ����������� ��� �������� ��������� (���� �������� ������)
	 * @return ���� ���������� ������ (never null)
	 */
	Calendar getSigningDate();

	/**
	 * ���������� ������������� ��� ���������� ������
	 * ����������� �� ���� 6 �������������� ����������
	 * @return ��� ������������� � ��������� Transact SM (never null nor empty)
	 */
	String getClaimDrawDepartmentETSMCode();

	/**
	 * @return ��� ���� � ������� ��������� ������.
	 */
	String getTb();

	/**
	 * ����������� �� ���� ����� ���������� ������� (�� �������� ������)
	 * @return ��� (���) ���������� �������� (never null nor empty)
	 */
	String getLoanProductType();

	/**
	 * ����������� �� ���� ����� ���������� ������� (�� �������� ������)
	 * @return ��� ���������� �������� (never null nor empty)
	 */
	String getLoanProductCode();

	/**
	 * ����������� �� ���� ����� ���������� ������� (�� �������� ������)
	 * @return ��� ���������� ����������� (never null nor empty)
	 */
	String getLoanSubProductCode();

	/**
	 * ����������� �� ���� ����� ���������� ������� (�� �������� ������)
	 * @return ����� ������� (never null)
	 */
	Money getLoanAmount();

	/**
	 * ����������� �� ���� ����� ���������� ������� (�� �������� ������)
	 * @return ���� ������� � ������� (never null)
	 */
	Long getLoanPeriod();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return ������ ������ ������� (never null)
	 */
	LoanIssueMethod getLoanIssueMethod();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return ����� ����� � ��������� ��� ������ ������� (can be null)
	 * ����������� ��� ������ �� ��������� ����� � �� ��������� ������� ����
	 */
	String getLoanIssueAccount();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return ����� ����� ��� ������ ������� (can be null)
	 * ����������� ��� ������ �� ��������� �����
	 */
	String getLoanIssueCard();

	/**
	 * ����������� ��� �������� ��������� (��������� MAIN_DEBITOR)
	 * @return ��� ��������� ������ (never null)
	 */
	ApplicantType getApplicantType();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ��� ������� (never null)
	 */
	PersonName getApplicantName();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ������� ����� ��� ��� null, ���� ����� ��� �� ����
	 */
	NameChangeReason getApplicantNameChangeReason();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return �������� ������� ����� ��� ��� null, ���� ����� ��� �� ����
	 */
	String getApplicantNameChangeDescription();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ���� ����� ��� ��� null, ���� ����� ��� �� ����
	 */
	Calendar getApplicantNameChangeDate();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ���������� ��� ������� ��� null, ���� ����� ��� �� ����
	 */
	PersonName getApplicantPreviousName();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ��� ������� (never null)
	 */
	Gender getApplicantGender();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ���� �������� ������� (never null)
	 */
	Calendar getApplicantBirthDay();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ����� �������� ������� (never null)
	 */
	String getApplicantBirthPlace();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ����������� ������� (never null)
	 */
	Citizenship getApplicantCitizenship();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return true, ���� � ������� ���� ������ �������
	 */
	boolean getApplicantForeignPassportFlag();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ������������� ����� ������� (can be null)
	 */
	String getApplicantEmail();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ������ ���������� ��������� ������� (never empty)
	 */
	Collection<Phone> getApplicantPhones();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ��� ������� (can be null)
	 */
	String getApplicantTaxID();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ����������� ������� (never null)
	 */
	Education getApplicantEducation();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ���� ������������� ������� ����������� �������
	 */
	Integer getApplicantUnfinishedYear();

	/**
	 * ����������� �� ���� 3 ��������
	 * @return ����� ������������ ���������� ������� (never null)
	 */
	Address getApplicantResidenceAddress();

	/**
	 * ����������� �� ���� 3 ��������
	 * @return ����� ���������� ����������� ������� (can be null)
	 */
	Address getApplicantFixedAddress();

	/**
	 * ����������� �� ���� 3 ��������
	 * @return ����� ��������� ����������� ������� (can be null)
	 */
	Address getApplicantTemporaryAddress();

	/**
	 * ����������� �� ���� 3 ��������
	 * @return ���� ��������� ��������� ����������� ������� (can be null)
	 * not null, ���� ������ ����� ��������� �����������
	 */
	Calendar getApplicantResidenceExpiryDate();

	/**
	 * ����������� �� ���� 3 ��������
	 * @return ���� ���������� ������� � ���������� ������ �� ������ ���������� ������ (���)
	 */
	int getApplicantCityResidencePeriod();

	/**
	 * ����������� �� ���� 3 ��������
	 * @return ���� ���������� ������� �� ������������ ������ (���)
	 */
	int getApplicantActualResidencePeriod();

	/**
	 * ����������� �� ���� 3 ��������
	 * @return ����� ���������� ������� (never null)
	 */
	ResidenceRight getApplicantResidenceRight();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ������ ���������� �������� ������� (never null)
	 */
	Passport getApplicantPassport();

	/**
	 * ����������� �� ���� 1 ������������ ������
	 * @return ������ ����������� �������� ������� (can be null)
	 */
	Passport getApplicantPreviousPassport();

	/**
	 * ����������� �� ���� 2 ����� � ������������
	 * @return �������� ��������� ������� (never null)
	 */
	FamilyStatus getApplicantFamilyStatus();

	/**
	 * ����������� �� ���� 2 ����� � ������������
	 * @return ������ � ������� ������� (can be null)
	 */
	Spouse getApplicantSpouse();

	/**
	 * ����������� �� ���� 2 ����� � ������������
	 * @return ������ � ������������� �������, ������� ����� (can be empty)
	 */
	Collection<Relative> getApplicantRelatives();

	/**
	 * ����������� �� ���� 4 ������ � �����
	 * @return ��� ��������� ������� (never null)
	 */
	WorkOnContract getApplicantEmploymentType();

	/**
	 * ����������� �� ���� 4 ������ � �����
	 * @return �����������, � ������� �������/�������� ������ (null ��� ����������� � ������� ����������������)
	 */
	Organization getApplicantOrganization();

	/**
	 * ����������� �� ���� 4 ������ � �����
	 * @return ���������� � ��������� ������� (can be null)
	 */
	Employee getApplicantAsEmployee();

	/**
	 * ����������� �� ���� 4 ������ � �����
	 * @return ���������� � ��������� � ��, ���� ������ - ��������� �� (can be null)
	 */
	SBEmployee getApplicantAsSBEmployee();

	/**
	 * ����������� �� ���� 4 ������ � �����
	 * @return �������� ������� �������� ������� (can be null)
	 */
	String getApplicantOwnBusinessDescription();

	/**
	 * ����������� �� ���� 4 ������ � �����
	 * @return �������������� �������� ����� ������� � ������ (never null)
	 */
	BigDecimal getApplicantBasicIncome();

	/**
	 * ����������� �� ���� 4 ������ � �����
	 * @return �������������� �������������� ����� ������� � ������ (never null)
	 */
	BigDecimal getApplicantAdditionalIncome();

	/**
	 * ����������� �� ���� 4 ������ � �����
	 * @return �������������� �������������� ����� ������� � ������ (never null)
	 */
	BigDecimal getApplicantFamilyIncome();

	/**
	 * ����������� �� ���� 4 ������ � �����
	 * @return �������������� ������� ������� � ������ (never null)
	 */
	BigDecimal getApplicantExpenses();

	/**
	 * ����������� �� ���� 5 ������������� � �����
	 * @return ������������ � ������������� ������� (can be empty)
	 */
	Collection<RealEstate> getApplicantRealEstates();

	/**
	 * ����������� �� ���� 5 ������������� � �����
	 * @return ������������ �������� � ������������� ������� (can be empty)
	 */
	Collection<Vehicle> getApplicantVehicles();

	/**
	 * ����������� �� ���� 6 �������������� ���������� (��������� false)
	 * @return true, ���� ������ �������� �� ������� � ��������� ������������� ����������� ����� � ��������
	 */
	boolean getApplicantInsuranceFlag();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return ��������� ����� ��������������� ����� ������� (can be null)
	 */
	String getApplicantSNILS();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return true, ���� ������ �������� �� ������ ���������� ������ �� ���
	 */
	boolean getApplicantBKIRequestFlag();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return true, ���� ������ �������� �� �������������� ������ ���������� � ���
	 */
	boolean getApplicantBKIGrantFlag();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return true, ���� ������ �������� �� �������������� ������ ���������� � ���
	 */
	boolean getApplicantPFRGrantFlag();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return ��� �������� ��������� ������� ������� (can be null)
	 */
	String getApplicantCreditHistoryCode();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return �������� ������� �� ��������� ��������� ����� ��� "�������� ������" ��� �������� ������ ������� ������ true - �� false - ���
	 */
	boolean getApplicantGetCreditCardFlag();

	/**
	 * ����������� ��� �������� ��������� (��������� false)
	 * @return true, ���� ������ �������� ��������, ��������� ������� ��������
	 */
	boolean getApplicantSpecialAttentionFlag();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return ���������� ������������ ����� ��������� ������ � ������������� �������
	 * ���� ������ �� �������� ���������� ���������, ������������ 0
	 */
	int getApplicantSBCommonSharesCount();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return ���������� ����������������� ����� ��������� ������ � ������������� �������
	 * ���� ������ �� �������� ���������� ���������, ������������ 0
	 */
	int getApplicantSBPreferenceSharesCount();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return ������ ���������� ���� ������� (can be empty)
	 */
	Collection<String> getApplicantSalaryCards();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return ������ ���������� ���� ������� (can be empty)
	 */
	Collection<String> getApplicantPensionCards();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return ������ ���������� ��������� ������� (can be empty)
	 */
	Collection<String> getApplicantSalaryDeposits();

	/**
	 * ����������� �� ���� 6 �������������� ����������
	 * @return ������ ���������� ��������� ������� (can be empty)
	 */
	Collection<String> getApplicantPensionDeposits();

	/**
	 * �������� ������ ��������� ������ � Transact SM.
	 * �� ���� ��������� ���� ETSM
	 */
	void resetClaimStatus();

	/**
	 * ����������� ��� �������� ���������
	 * @return ����� ������
	 */
	String getOperationUID();

	/**
	 * ���������� ������� ������ ������
	 * @return true, ���� ������ ��������� ���������
	 */
	boolean getCompleteAppFlag();

	/**
	 * ���������� ����� ������
	 * @return
	 */
	ChannelType getChannel();

	/**
	 * @return ����� ��������� �������� �� ��������� ������������ ������ � ���
	 */
	ChannelCBRegAApproveType getChannelCBRegAApprove();

	/**
	 * @return ����� ��������� �������� �� ��������� ������������ ������ ���
	 */
	ChannelPFRRegAApproveType getChannelPFRRegAApprove();

	/**
	 * @return ������ �������� ������
	 */
	Collection<Question> getQuestions();

	Boolean getOwnerGuestMbk();

	/**
	 * ��������� ��������� (��), ����������� ������ �� ��������� � ETSM
	 */
	String getLoginKI();

	/**
	 * @return ������ TOP-UP. ����� ���� null ��� ������
	 */
	Set<ClaimOfferTopUp> getTopUps();
}
