package com.rssl.phizic.config.loyalty;

/**
 * User: Moshenko
 * Date: 18.07.2011
 * Time: 16:42:58
 */
public interface LoyaltyConfigMBean
{
	/**
	 * URL сайта лояльность
	 * @return
	 */
	public  String getLoyaltyUrl();

	/**
	 * alias публичного сертификата в javatrast store
	 * @return
	 */
	public String getLoyaltyPubCert();
	/**
	 * alias приватного сертификата в javatrast store
	 * @return
	 */
	public String getLoyaltyPrivCert();

	/**
	 * тип хранилища сертификатов
	 * @return
	 */
	public String getStoreType();

	/**
	 * путь к хранилищу
	 * @return
	 */
	public String getStorePath();

	/**
	 *  пароль к хранилищу
	 * @return
	 */
	public String getStorePassword();


}
