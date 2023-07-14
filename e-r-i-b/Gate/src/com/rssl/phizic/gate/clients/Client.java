package com.rssl.phizic.gate.clients;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.common.types.SegmentCodeType;

import java.util.Calendar;
import java.util.List;
import java.io.Serializable;

/**
 * �������� ������
 */
public interface Client extends Serializable
{
	/**
	 * ID ������� �� ������� �������
	 *
	 * Domain: ExternalID
	 *
	 * @return ������� ID
	 */
	String getId();

	/**
	 * ���������� ID �������
	 *
	 * @return loginId �������
	 */
	Long getInternalId();

	/**
    * ���������� ID �������
    *
    * @return loginId �������
    */
	Long getInternalOwnerId();


	/**
	 * ������������� ������� ��� ����������� � ���������������� ����������
	 *
	 * @return ������������� �������
	 */
	String getDisplayId();

	/**
	 * ������� ��� ������� - ������ �. �..
	 * Domain: Text
	 *
	 * @return ������� ���
	 */
	String getShortName();

	/**
	 * ������ ��� ������� ������ ���� ��������
	 * Domain: Text
	 *
	 * @return ������ ���
	 */
	String getFullName();

	/**
	 * ��� ������� - ����
	 * Domain: Text
	 *
	 * @return ���
	 */
	String getFirstName();

	/**
	 * ������� ������� - ������
	 * Domain: Text
	 *
	 * @return �������
	 */
	String getSurName();

	/**
	 * �������� ������� ��������
	 * Domain: Text
	 *
	 * @return ��������
	 */
	String getPatrName();

	/**
	 * ���� ��������
	 * Domain: Date
	 *
	 * @return ����
	 */
	Calendar getBirthDay();

	/**
	 * ����� �������� (���������� �����)
	 * Domain: Text
	 *
	 * @return ����� ��������
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
	 * �������� �������
	 * Domain: Text
	 *
	 * @return �����
	 */
	String getHomePhone();

	/**
	 * ������� �������
	 * Domain: Text
	 *
	 * @return �����
	 */
	String getJobPhone();

	/**
	 * ��������� �������
	 * Domain: Text
	 *
	 * @return �����
	 */
	String getMobilePhone();

   /**
    * @return ��������� ��������
    */
    String getMobileOperator();

	/**
	 * ���
	 * Domain: ClientSex
	 *
	 * @return M ��� F
	 */
	String getSex();

	/**
	 * ���
	 * Domain: INN
	 *
	 * @return ���
	 */
	String getINN();

	/**
	 * �������� ��� ���
	 *
	 * @return true=��������
	 */
	boolean isResident();

   /**
    * ����������� �����
    *
    * @return ����������� �����
    */
   Address getLegalAddress();

   /**
    * ����������� �����
    *
    * @return ����������� �����
    */
   Address getRealAddress();
	
   /**
    * �������� ������ ���������� �������.
    */
   List<? extends ClientDocument> getDocuments();

	/**
	 * �����������
	 */
	String getCitizenship();

	/**
	 * @return ���� ������������ �������
	 */
	Office getOffice();

	/**
	 * @return ����� ��������
	 */
	String getAgreementNumber();

	/**
	 * @return ���� ������ ������������
	 */
	Calendar getInsertionDate();

	/**
	 * @return ���� ����������� ������������
	 */
	Calendar getCancellationDate();

	/**
	 * @return ��������� �������
	 */
	ClientState getState();
	
	/**
	 * @return �������� �� ������ ������������� ������� ���������� ������������. true - ��������.
	 */
	boolean isUDBO();

	/**
	 * @return ��� ��������, � �������� ������� �������
	 */
	SegmentCodeType getSegmentCodeType();

	/**
	 * @return ��� ��������� �����
	 */
	String getTarifPlanCodeType();

	/**
	 * @return ���� ����������� ��������� �����
	 */
	Calendar getTarifPlanConnectionDate();

	/**
	 * @return ����� ���������, �� ������� �������� ������
	 */
	String getManagerId();

	/**
	 * @return ���. ���� ����������� ���������
	 */
	String getManagerTB();

	/**
	 * @return ��� ����������� ���������
	 */
	String getManagerOSB();

	/**
	 * @return ��� ����������� ���������
	 */
	String getManagerVSP();

	/**
	 * @return ��� �������
	 */
	String getGender();
}
