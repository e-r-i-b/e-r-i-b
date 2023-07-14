package com.rssl.phizic.gate.claims.sbnkd;

/**
 * Документ для заключения УДБО.
 *
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public interface ConcludeEDBODocument extends ClientInfoDocument
{
	/**
	 * @param eDBOBranchId Номер филиала заведения УДБО.
	*/
	public void setEDBOBranchId(String eDBOBranchId);
	/**
	 * @return Номер филиала заведения УДБО.
	*/
	public String getEDBOBranchId();
	/**
	 * @param eDBOAgencyId Номер отделения заведения УДБО.
	*/
	public void setEDBOAgencyId(String eDBOAgencyId);
	/**
	 * @return Номер отделения заведения УДБО.
	*/
	public String getEDBOAgencyId();
	/**
	 * @param eDBOPhone Номер мобильного телефона УДБО.
	*/
	public void setEDBOPhone(String eDBOPhone);
	/**
	 * @return Номер мобильного телефона УДБО.
	*/
	public String getEDBOPhone();
	/**
	 * @param eDBOPhoneOperator Оператор мобильного телефона УДБО.
	*/
	public void setEDBOPhoneOperator(String eDBOPhoneOperator);
	/**
	 * @return Оператор мобильного телефона УДБО.
	*/
	public String getEDBOPhoneOperator();

	/**
	 * @param fieldNum номер договора.
	 */
	void setEDBOOrderNumber(Long fieldNum);

	/**
	 * @return номер договора.
	 */
	Long getEDBOOrderNumber();

	/**
	 * @param fieldData1 ТБ договора.
	 */
	void setEDBO_TB(String fieldData1);

	/**
	 * @return ТБ договора.
	 */
	String getEDBO_TB();

	/**
	 * @return email пользователя
	 */
	public String getEmail();

	/**
	 * @param email пользователя
	 */
	public void setEmail(String email);

	/**
	 * @return номер карты входа
	 */
	public String getLastLogonCardNumber();

	/**
	 * @param lastLogonCardNumber - карта входа
	 */
	public void setLastLogonCardNumber(String lastLogonCardNumber);

}
