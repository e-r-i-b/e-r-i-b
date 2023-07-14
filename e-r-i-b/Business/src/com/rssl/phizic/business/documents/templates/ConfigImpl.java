package com.rssl.phizic.business.documents.templates;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * ������ ����
 *
 * @author khudyakov
 * @ created 29.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class ConfigImpl extends Config
{
	private static final String DB_SOURCE_NAME                          = "com.rssl.iccs.usct.db.source.name";
	private static final String OLD_DOCUMENT_DATE                       = "com.rssl.iccs.payment.olddocument.date";                                 //����, �� ������� �������� ���� �������
	private static final String BILLING_SYSTEMS_ADAPTERS                = "com.rssl.iccs.payment.olddocument.billing.systems.adapters";             //������ ���. ������ ����
	private static final String DB_LINK_NAME                            = "com.rssl.iccs.dbserver.log.dblink.name";
	private static final String ENABLED_PROPERTY_NAME                   = "com.rssl.iccs.is.usct.enabled";
	private static final String WEB_SERVICE_URL_PROPERTY_NAME           = "com.rssl.gate.usct.web.service.url";                                     //��� �� ����
	private static final String MESSAGE_LOG_ACTIVITY_PROPERTY_NAME      = "com.rssl.gate.usct.web.message.log.activity";                            //��������� ���������� ����������� ��������� �/� ���� � ����

	private String oldDocumentDate;
	private String[] billingSystemsAdapter;
	private String dbLinkName;
	private boolean enabled;
	private String webServiceUrl;
	private boolean messageLoggingEnabled;

	public ConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		oldDocumentDate = getProperty(OLD_DOCUMENT_DATE);
		billingSystemsAdapter = getProperty(BILLING_SYSTEMS_ADAPTERS).split(";");
		dbLinkName = getProperty(DB_LINK_NAME);
		enabled = getBoolProperty(ENABLED_PROPERTY_NAME);
		webServiceUrl = getProperty(WEB_SERVICE_URL_PROPERTY_NAME);
		messageLoggingEnabled = getBoolProperty(MESSAGE_LOG_ACTIVITY_PROPERTY_NAME);
	}

	/**
	 * @return data-source/name
	 */
	public String getDbSourceName()
	{
		return getProperty(DB_SOURCE_NAME);
	}

	public String[] getBillingSystemsAdapter()
	{
		return billingSystemsAdapter;
	}

	public String getOldDocumentDate()
	{
		return oldDocumentDate;
	}

	/**
	 * @return ��� db link
	 */
	public String getDbLinkName()
	{
		return dbLinkName;
	}

	/**
	 * @return true - ������������ ��������� � ����
	 */
	public boolean isUSCTEnabled()
	{
		return enabled;
	}

	/**
	 * @return true - ������������ ��������� � ����
	 */
	public String getWebServiceUrl()
	{
		return webServiceUrl;
	}

	/**
	 * @return true - ������������ ��������� � ����
	 */
	public boolean isMessageLoggingEnabled()
	{
		return messageLoggingEnabled;
	}
}
