package com.rssl.phizic.config.loanclaim;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * User: Moshenko
 * Date: 24.06.15
 * Time: 13:04
 */
public class EtsmConfig extends Config
{
	//URL web ��������  ��� ��������� �����  �� ������� �� ETSM, �������� �� ������� ���(�� ���������� ����)
	private static final String URL_WS_ETSM_OFFER_KEY  = "com.rssl.iccs.credit.etsm.webservice.url";

	private static final String DB_INSTANCE_NAME = "com.rssl.iccs.etsm.db.instance.name";

	private static final String ESB_CREDIT_QUEUE_NAME = "jms/etsm/LoanClaimQueue";

	private static final String ESB_CREDIT_QCF_NAME = "jms/etsm/LoanClaimQCF";


	private String etsnOfferUrl;
	private String dbInstanceName;

	/**
	 * ����� ������ ������ ����������� ������ �����������.
	 *
	 * @param reader �����.
	 */
	public EtsmConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		etsnOfferUrl = getProperty(URL_WS_ETSM_OFFER_KEY);
		dbInstanceName = getProperty(DB_INSTANCE_NAME);
	}

	public String getEtsnOfferUrl()
	{
		return etsnOfferUrl;
	}

	public String getDbInstanceName()
	{
		return dbInstanceName;
	}

	/**
	 * @return ��� ������� ��� �������� �������� � ��� �� �������� (��� � ���)
	 */
	public String getEsbCreditQueueName()
	{
		return ESB_CREDIT_QUEUE_NAME;
	}

	/**
	 * @return ��� ��������-������� �������� ��� �������� �������� � ��� �� �������� (��� � ���)
	 */
	public String getEsbCreditQCFName()
	{
		return ESB_CREDIT_QCF_NAME;
	}

}