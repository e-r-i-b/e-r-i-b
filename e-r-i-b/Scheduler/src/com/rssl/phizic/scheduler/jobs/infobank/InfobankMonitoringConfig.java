package com.rssl.phizic.scheduler.jobs.infobank;

import com.rssl.phizic.config.*;

/**
 * Конфиг для чтения infobank.properties
 * @author tisov
 * @ created 24.10.13
 * @ $Author$
 * @ $Revision$
 */

public class InfobankMonitoringConfig extends Config
{
	private static final String RB_TB_BRCH_ID_KEY     = "RbTbBrchId";
	private static final String BIRTHDAY_KEY          = "Birthday";
	private static final String LAST_NAME_KEY         = "LastName";
	private static final String FIRST_NAME_KEY        = "FirstName";
	private static final String ID_TYPE_KEY           = "IdType";
	private static final String ID_SERIES_KEY         = "IdSeries";
	private static final String ID_NUM_KEY            = "IdNum";
	private static final String CARD_NUM_KEY          = "CardNum";
	private static final String ACCOUNT_TYPE_KEY      = "AcctType";

	private String rbTbBrchIdProperty;
	private String birthdayProperty;
	private String lastNameProperty;
	private String firstNameProperty;
	private String idTypeProperty;
	private String idSeriesProperty;
	private String idNumberProperty;
	private String cardNumberProperty;
	private String accountTypeProperty;

	public InfobankMonitoringConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		rbTbBrchIdProperty = getProperty(RB_TB_BRCH_ID_KEY);
		birthdayProperty = getProperty(BIRTHDAY_KEY);
		lastNameProperty = getProperty(LAST_NAME_KEY);
		firstNameProperty = getProperty(FIRST_NAME_KEY);
		idTypeProperty = getProperty(ID_TYPE_KEY);
		idSeriesProperty = getProperty(ID_SERIES_KEY);
		idNumberProperty = getProperty(ID_NUM_KEY);
		cardNumberProperty = getProperty(CARD_NUM_KEY);
		accountTypeProperty = getProperty(ACCOUNT_TYPE_KEY);
	}

	/**
	 * геттер для аксессора ид регионального банка, тербанка и т.д.
	 * @return аксессор ид
	 */
	public String getRbTbBrchId()
	{
		return rbTbBrchIdProperty;
	}

	/**
	 * геттер для аксессора даты рождения клиента
	 * @return аксессор даты рождения
	 */
	public String getBirthday()
	{
		return birthdayProperty;
	}

	/**
	 * геттер для аксессора фамилии клиента
	 * @return аксессор фамилии клиента
	 */
	public String getLastName()
	{
		return lastNameProperty;
	}

	/**
	 * геттер для аксессора имени клиента
	 * @return аксессор имени клиента
	 */
	public String getFirstName()
	{
		return firstNameProperty;
	}

	/**
	 * геттер для аксессора идентификатора типа удостоверения личности
	 * @return аксессор ид
	 */
	public String getIdType()
	{
		return idTypeProperty;
	}

	/**
	 * геттер для аксессора серии удостоверения личности
	 * @return аксессор серии
	 */
	public String getIdSeries()
	{
		return idSeriesProperty;
	}

	/**
	 * геттер для аксессора номера удостоверения личности
	 * @return аксессор номера
	 */
	public String getIdNumber()
	{
		return idNumberProperty;
	}

	/**
	 * геттер для аксессора номера карты
	 * @return аксессор номера карты
	 */
	public String getCardNumber()
	{
		return cardNumberProperty;
	}

	/**
	 * геттер для аксессора типа счёта
	 * @return аксессор типа счёта
	 */
	public String getAccountType()
	{
		return accountTypeProperty;
	}

}
