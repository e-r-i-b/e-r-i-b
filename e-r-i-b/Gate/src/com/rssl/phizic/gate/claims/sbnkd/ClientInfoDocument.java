package com.rssl.phizic.gate.claims.sbnkd;

import com.rssl.phizic.gate.claims.sbnkd.impl.CardInfoImpl;

import java.util.Calendar;
import java.util.List;

/**
 * ���������� � �������.
 *
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public interface ClientInfoDocument
{
	/**
	 * @return rbTbBranch
	 */
	public String getRbTbBranch();

	/**
	 * @return ���� ��������.
	 */
	public Calendar getCreationDate();

	/**
	 * @param creationDate ���� ��������.
	 */
	public void setCreationDate(Calendar creationDate);

	/**
	 * @return ��.
	 */
	public Long getId();

	/**
	 * @param id ��.
	 */
	public void setId(Long id);

	/**
	 * @return �������� ������.
	 */
	public  boolean isGuest();

	/**
	 * @param guest �������� ������.
	 */
	public void setGuest(boolean guest);

	/**
	 * @return ������������� ���������.
	 */
	public Long getOwnerId();

	/**
	 * @param ownerId ������������� ���������.
	 */
	public void setOwnerId(Long ownerId);

	/**
	 * @return ����� ��������.
	 */
	public String getPhone();

	/**
	 * @param phone ����� ��������.
	 */
	public void setPhone(String phone);

	/**
	 * @return ������� �������������.
	 */
	public String getUID();

	/**
	 * @param UID ������� �������������.
	 */
	public void setUID(String UID);

	/**
	 * @return ���������� �� ������.
	 */
	public List<CardInfoImpl> getCardInfos();

	/**
	 * @param cardInfo ���������� �� ������.
	 */
	public void setCardInfos(List<CardInfoImpl> cardInfo);

	/**
	 * @param found ������ �� ��� ������ ����� ������� GetPrivateClient.
	 */
	public void setClientFound(boolean found);

	/**
	 * @return ������ �� ��� ������ ����� ������� GetPrivateClient.
	 */
	public boolean isClientFound();

	/**
	 * @return ��� ������.
	 */
	public String getOsb();

	/**
	 * @param osb ��� ������.
	 */
	public void setOsb(String osb);

	/**
	 * @return �� ������.
	 */
	public String getTb();

	/**
	 * @param tb �� ������.
	 */
	public void setTb(String tb);

	/**
	 * @return ��� ������.
	 */
	public String getVsp();

	/**
	 * @param vsp ��� ������.
	 */
	public void setVsp(String vsp);

	/**
	 * @param personBirthday ���� �������� �������.
	 */
	public void setPersonBirthday(Calendar personBirthday);

	/**
	 * @return ���� �������� �������.
	 */
	public Calendar getPersonBirthday();

	/**
	 * @param personBirthplace ����� �������� �������.
	 */
	public void setPersonBirthplace(String personBirthplace);

	/**
	 * @return ����� �������� �������.
	 */
	public String getPersonBirthplace();

	/**
	 * @param personTaxId ��� �������.
	 */
	public void setPersonTaxId(String personTaxId);

	/**
	 * @return ��� �������.
	 */
	public String getPersonTaxId();

	/**
	 * @param personCitizenship ����������� �������.
	 */
	public void setPersonCitizenship(String personCitizenship);

	/**
	 * @return ����������� �������.
	 */
	public String getPersonCitizenship();

	/**
	 * @param personGender ��� �������.
	 */
	public void setPersonGender(String personGender);

	/**
	 * @return ��� �������.
	 */
	public String getPersonGender();

	/**
	 * @param personResident ������-��������.
	 */
	public void setPersonResident(Boolean personResident);

	/**
	 * @return ������-��������.
	 */
	public Boolean isPersonResident();

	/**
	 * @param personLastName ������� �������.
	 */
	public void setPersonLastName(String personLastName);

	/**
	 * @return ������� �������.
	 */
	public String getPersonLastName();

	/**
	 * @param personFirstName ��� �������.
	 */
	public void setPersonFirstName(String personFirstName);

	/**
	 * @return ��� �������.
	 */
	public String getPersonFirstName();

	/**
	 * @param personMiddleName �������� �������.
	 */
	public void setPersonMiddleName(String personMiddleName);

	/**
	 * @return �������� �������.
	 */
	public String getPersonMiddleName();

	/**
	 * @param identityCardType ��� ��������� ��������������� ��������.
	 */
	public void setIdentityCardType(String identityCardType);

	/**
	 * @return ��� ��������� ��������������� ��������.
	 */
	public String getIdentityCardType();

	/**
	 * @param identityCardSeries ����� ��������� ��������������� ��������.
	 */
	public void setIdentityCardSeries(String identityCardSeries);

	/**
	 * @return ����� ��������� ��������������� ��������.
	 */
	public String getIdentityCardSeries();

	/**
	 * @param identityCardNumber ����� ��������� ��������������� ��������.
	 */
	public void setIdentityCardNumber(String identityCardNumber);

	/**
	 * @return ����� ��������� ��������������� ��������.
	 */
	public String getIdentityCardNumber();

	/**
	 * @param identityCardIssuedBy ��� ����� ���.
	 */
	public void setIdentityCardIssuedBy(String identityCardIssuedBy);

	/**
	 * @return ��� ����� ���.
	 */
	public String getIdentityCardIssuedBy();

	/**
	 * @return - ��� ����� ���
	 */
	public String getIdentityCardIssuedCode();

	/**
	 * @param identityCardIssuedCode - ��� ����� ���
	 */
	public void setIdentityCardIssuedCode(String identityCardIssuedCode);

	/**
	 * @param identityCardIssueDate ����� ����� ���.
	 */
	public void setIdentityCardIssueDate(Calendar identityCardIssueDate);

	/**
	 * @return ����� ����� ���.
	 */
	public Calendar getIdentityCardIssueDate();

	/**
	 * @param identityCardExpDate ���� �������� ���.
	 */
	public void setIdentityCardExpDate(Calendar identityCardExpDate);

	/**
	 * @return ���� �������� ���.
	 */
	public Calendar getIdentityCardExpDate();

	/**
	 * @param verified ������� ����������������� �������.
	 */
	public void setVerified(boolean verified);

	/**
	 * @return ������� ����������������� �������.
	 */
	public boolean isVerified();

	/**
	 * @param contactData ���������������� ���������� � ��������.
	 */
	public void setContactData(ContactData[] contactData);

	/**
	 * @return ���������������� ���������� � ��������.
	 */
	public ContactData[] getContactData();

	/**
	 * @param address ������.
	 */
	public void setAddress(FullAddress[] address);

	/**
	 * @return ������.
	 */
	public FullAddress[] getAddress();

	/**
	 * @param state ������.
	 */
	void setStatus(IssueCardStatus state);

	/**
	 * @return ������.
	 */
	IssueCardStatus getStatus();

	/**
	 * @return  ����������������� RbTbBranchId ������
	 */
	public String getConvertedRbTbBranchId();

	/**
	 * @param convertedRbTbBranchId ����������������� RbTbBranchId ������
	 */

	public void setConvertedRbTbBranchId(String convertedRbTbBranchId);

	/**
	 * @return ����������������� ����� ���������
	 */
	public String getConvertedAgencyId();

	/**
	 * @return ����������������� ����� ��
	 */
	public String getConvertedRegionId();

	/**
	 * @param convertedRegionId c���������������� ����� ��
	 */
	public void setConvertedRegionId(String convertedRegionId);
	/**
	 * @param convertedBranchId ����������������� ����� �������
	 */
	public void setConvertedBranchId(String convertedBranchId);

	/**
	 * @return ����������������� ����� �������
	 */
	public String getConvertedBranchId();

	/**
	 * @param convertedAgencyId ����������������� ����� ���������
	 */
	public void setConvertedAgencyId(String convertedAgencyId);

}
