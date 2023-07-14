package com.rssl.phizic.config.loyalty;

/**
 * User: Moshenko
 * Date: 18.07.2011
 * Time: 16:42:58
 */
public interface LoyaltyConfigMBean
{
	/**
	 * URL ����� ����������
	 * @return
	 */
	public  String getLoyaltyUrl();

	/**
	 * alias ���������� ����������� � javatrast store
	 * @return
	 */
	public String getLoyaltyPubCert();
	/**
	 * alias ���������� ����������� � javatrast store
	 * @return
	 */
	public String getLoyaltyPrivCert();

	/**
	 * ��� ��������� ������������
	 * @return
	 */
	public String getStoreType();

	/**
	 * ���� � ���������
	 * @return
	 */
	public String getStorePath();

	/**
	 *  ������ � ���������
	 * @return
	 */
	public String getStorePassword();


}
