package com.rssl.phizic.gate.claims.sbnkd;

/**
 * �������� ��� ���������� ����.
 *
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public interface ConcludeEDBODocument extends ClientInfoDocument
{
	/**
	 * @param eDBOBranchId ����� ������� ��������� ����.
	*/
	public void setEDBOBranchId(String eDBOBranchId);
	/**
	 * @return ����� ������� ��������� ����.
	*/
	public String getEDBOBranchId();
	/**
	 * @param eDBOAgencyId ����� ��������� ��������� ����.
	*/
	public void setEDBOAgencyId(String eDBOAgencyId);
	/**
	 * @return ����� ��������� ��������� ����.
	*/
	public String getEDBOAgencyId();
	/**
	 * @param eDBOPhone ����� ���������� �������� ����.
	*/
	public void setEDBOPhone(String eDBOPhone);
	/**
	 * @return ����� ���������� �������� ����.
	*/
	public String getEDBOPhone();
	/**
	 * @param eDBOPhoneOperator �������� ���������� �������� ����.
	*/
	public void setEDBOPhoneOperator(String eDBOPhoneOperator);
	/**
	 * @return �������� ���������� �������� ����.
	*/
	public String getEDBOPhoneOperator();

	/**
	 * @param fieldNum ����� ��������.
	 */
	void setEDBOOrderNumber(Long fieldNum);

	/**
	 * @return ����� ��������.
	 */
	Long getEDBOOrderNumber();

	/**
	 * @param fieldData1 �� ��������.
	 */
	void setEDBO_TB(String fieldData1);

	/**
	 * @return �� ��������.
	 */
	String getEDBO_TB();

	/**
	 * @return email ������������
	 */
	public String getEmail();

	/**
	 * @param email ������������
	 */
	public void setEmail(String email);

	/**
	 * @return ����� ����� �����
	 */
	public String getLastLogonCardNumber();

	/**
	 * @param lastLogonCardNumber - ����� �����
	 */
	public void setLastLogonCardNumber(String lastLogonCardNumber);

}
