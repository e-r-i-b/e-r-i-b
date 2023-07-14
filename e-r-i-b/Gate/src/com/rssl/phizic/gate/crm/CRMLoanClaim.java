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
 * ��������� ������ � CRM
 */
public interface CRMLoanClaim
{
	/**
	 *  ����������  ����� ������ �� ������� ������� (�����/������������� ������ � ����) [1] [����. �����=50]
	 */
	public String getNumber();
	public void setNumber(String number);
	/**
	 * �������� ������, �� �� ����� �������� ������ (1=Web, 2=�� ����������, 3=�� �������, 4=���, 5=����-����, 6=����-��, 7=����-��, 8=����-��, 9=����-��������) [1]
	 */
	public ChannelType getChannelType();
	public void setChannelType(ChannelType channel);
	/**
	 *  ����� ����������, ����������� ������ � ���� (Login ���������� � �����) [0-1]
	 */
	public String getEmployerLogin();
	public void setEmployerLogin(String employer);
	/**
	 *  ��� ����������, ����������� ������ ���� [0-1]
	 */
	public String getEmployerFIO();
	public void setEmployerFIO(String fio);
	/**
	 * ��� ������� [1]
	 */
	public String getFirstName();
	public void setFirstName(String firstName);
	/**
	 * ������� ������� [1]
	 */
	public String getLastName();
	public void setLastName(String lastName);
	/**
	 * �������� ������� [0-1]
	 */
	public String getMiddleName();
	public void setMiddleName(String middleName);
	/**
	 * ���� �������� [1]
	 */
	public Calendar getBirthDay();
	public void setBirthDay(Calendar birthDay);
	/**
	 * ����� �������� ��: ����� + ����� ����� ������ [1]
	 */
	public String getPassportNumber();
	public void setPassportNumber(String passportNumber);
	/**
	 *  ����� ����� � Way (������������� ����� � ��������� �������). ��� ������,  ���� ������ ����������� � ������ ��ѻ [0-1]
	 */
	public String getWayCardNumber();
	public void setWayCardNumber(String wayCardNumber);
	/**
	 * ������������� ��������� ��������.  ����������� � ������ ������� ������� � �������� [0-1]
	 */
	public String getCampaingMemberId();
	public void setCampaingMemberId(String campaingMemberId);
	/**
	 * ��������� ������� [1]
	 */
	public PhoneNumber getMobilePhone();
	public void setMobilePhone(PhoneNumber mobilePhone);
	/**
	 * ������� ������� [0-1]
	 */
	public PhoneNumber getWorkPhone();
	public void setWorkPhone(PhoneNumber workPhone);
	/**
	 * ��������������  ������� [0-1]
	 */
	public PhoneNumber getAddPhone();
	public void setAddPhone(PhoneNumber addPhone);
	/**
	 * ������������ �������� �� ����������� ��������� ��������� ����  [1]
	 *
	 */
	 public String getProductName();
	 public void setProductName(String productName);

	/**
	 * ��� ���� �������� � TSM [0-1]
	 */
	public String getTargetProductType();
	public void setTargetProductType(String targetProductType);
	/**
	 * ��� �������� � TSM [0-1]
	 */
	public String getTargetProduct();
	public void setTargetProduct(String targetProduct);
	/**
	 * ��� �������� ������, ����� ������� ������� � ������ ("Consumer Credit") [1]
	 */
//	public String getProductType();
//	public void setProductType(String productType);
	/**
	 * ��� ����������� � TSM [0-1]
	 */
	public String getTargetProductSub();
	public void setTargetProductSub(String targetProductSub);
	/**
	 * ������������� ����� �������/������ [1]
	 */
	public Money getLoanAmount();
	public void setLoanAmount(Money currency);
	/**
	 * ���� ������������.  ���-�� �������. ����� ����� � ��������� 1-360. [1]
	 */
	public int getDuration();
	public void setDuratione(int duration);

	/**
	 * ���������� ������, %.  ����� � ��������� �� ���� ������ ����� ������� � ��������� �� 0 �� 100. [1]
	 */
	public BigDecimal getInterestRate();
	public void setInterestRate(BigDecimal interestRate);
	/**
	 * ����������� [0-1]
	 */
	public String getComments();
	public void setComments(String comments);
	/**
	 * ������������� ����� �� ����������� �������������� �����[1]
	 */
	public Code getOfficeCode();
	public void setOfficeCode(Code code);

	/**
	 * ����������� ���� ������ � ��� [0-1]
	 */
	public Calendar getPlannedVisitDate();
	public void setPlannedVisitDate(Calendar dateTime);

	/**
	 * ����������� ����� ������ � ��� [0-1]
	 *   1	� 09:00 �� 11:00
	 *	 2	� 11:00 �� 13:00
	 *   3	� 13:00 �� 15:00
	 *   4	� 15:00 �� 17:00
	 *   5	� 17:00 �� 19:00
	 *   6	� 19:00 �� 20:00
	 */
	public Integer getPlannedVisitTime();
	public void setPlannedVisitTime(Integer plannedVisitTime);
}
