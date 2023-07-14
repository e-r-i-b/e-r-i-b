package com.rssl.phizic.business.ermb.migration.list.config;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import javax.xml.bind.annotation.*;

/**
 * @author Puzikov
 * @ created 25.12.13
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name="config")
@XmlType(name = "Config")
@XmlAccessorType(XmlAccessType.NONE)
@PlainOldJavaObject
public class ErmbListMigrationConfigBean
{
	@XmlElement(name = "rollback-access", required = true)
	private boolean rollbackAccess;
	@XmlElement(name = "service-url", required = true)
	private String serviceUrl;
	@XmlElement(name = "csv-max-file-size", required = true)
	private int csvFileMaxSize;
	@XmlElement(name = "csv-shared-dir", required = true)
	private String csvSharedDir;
	@XmlElement(name = "log-dir", required = true)
	private String logDir;
	@XmlElement(name = "migration-block-sequence", required = true)
	private String migrationBlockSequence;
	@XmlElement(name = "inflatable-block-number", required = true)
	private long inflatableBlockNumber;
	@XmlElement(name = "migration-batch-size", required = true)
	private int migrationBatchSize;
	@XmlElement(name = "tb", required = true)
	private String tb;
	@XmlElement(name = "default-welcome-sms", required = true)
	private boolean defaultWelcomeSms;

	public boolean isRollbackAccess()
	{
		return rollbackAccess;
	}

	public void setRollbackAccess(boolean rollbackAccess)
	{
		this.rollbackAccess = rollbackAccess;
	}

	public String getServiceUrl()
	{
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl)
	{
		this.serviceUrl = serviceUrl;
	}

	public int getCsvFileMaxSize()
	{
		return csvFileMaxSize;
	}

	public void setCsvFileMaxSize(int csvFileMaxSize)
	{
		this.csvFileMaxSize = csvFileMaxSize;
	}

	public String getCsvSharedDir()
	{
		return csvSharedDir;
	}

	public void setCsvSharedDir(String csvSharedDir)
	{
		this.csvSharedDir = csvSharedDir;
	}

	public String getLogDir()
	{
		return logDir;
	}

	public void setLogDir(String logDir)
	{
		this.logDir = logDir;
	}

	public String getMigrationBlockSequence()
	{
		return migrationBlockSequence;
	}

	public void setMigrationBlockSequence(String migrationBlockSequence)
	{
		this.migrationBlockSequence = migrationBlockSequence;
	}

	public long getInflatableBlockNumber()
	{
		return inflatableBlockNumber;
	}

	public void setInflatableBlockNumber(long inflatableBlockNumber)
	{
		this.inflatableBlockNumber = inflatableBlockNumber;
	}

	public int getMigrationBatchSize()
	{
		return migrationBatchSize;
	}

	public void setMigrationBatchSize(int migrationBatchSize)
	{
		this.migrationBatchSize = migrationBatchSize;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public boolean getDefaultWelcomeSms()
	{
		return defaultWelcomeSms;
	}

	public void setDefaultWelcomeSms(boolean defaultWelcomeSms)
	{
		this.defaultWelcomeSms = defaultWelcomeSms;
	}
}
