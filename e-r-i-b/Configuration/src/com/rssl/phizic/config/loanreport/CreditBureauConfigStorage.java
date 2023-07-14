package com.rssl.phizic.config.loanreport;

import static com.rssl.phizic.config.loanreport.CreditBureauConstants.*;

/**
 * @author Erkin
 * @ created 18.11.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Хранилище конфига БКИ
 */
public class CreditBureauConfigStorage
{
	/**
	 * Загрузить конфиг
	 * @return загруженный конфиг (never null)
	 */
	public CreditBureauConfig loadConfig()
	{
		PropertyReader reader = new PropertyReader();
		CreditBureauConfig config = new CreditBureauConfig();
		config.jobStartDay              = reader.readMandatoryDayOfMonth(BKI_JOB_SCHEDULE_START_DAY);
		config.jobStartTime             = reader.readMandatoryTimeOfDay(BKI_JOB_SCHEDULE_START_TIME);
		config.jobEndTime               = reader.readMandatoryTimeOfDay(BKI_JOB_SCHEDULE_END_TIME);
		config.jobEnabled               = reader.readMandatoryBoolean(BKI_JOB_ENABLED);
		config.jobSuspended             = reader.readMandatoryBoolean(BKI_JOB_SUSPENDED);
		config.jobPackSize              = reader.readMandatoryInteger(BKI_JOB_PACK_SIZE);
		config.pfdResourcePath          = reader.readMandatoryString(PDF_RESOURCES_PATH);
		config.okbAddress               = reader.readMandatoryString(OKB_ADRRESS);
		config.okbPhone                 = reader.readMandatoryString(OKB_PHONE);
		config.okbServiceProviderUUID   = reader.readMandatoryUUID(SERVICE_PROVIDER_OKB_ID);
		config.bkiTimeoutInMinutes      = reader.readMandatoryInteger(BKI_TIMEOUT_MINUTES);
		config.okbURL                   = reader.readMandatoryURL(BKI_URL);
		config.pdfButtonShow            = reader.readMandatoryBoolean(BKI_PDF_BUTTON_SHOW);
		return config;
	}
}
