package com.rssl.phizic.config.loanreport;

import com.rssl.phizic.config.PropertyCategory;

/**
 * @author Rtischeva
 * @ created 08.10.14
 * @ $Author$
 * @ $Revision$
 */
public final class CreditBureauConstants
{
	public static final PropertyCategory PROPERTY_CATEGORY = PropertyCategory.Phizic;

	public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

	public static final String BKI_JOB_SCHEDULE_START_DAY = "com.rssl.iccs.loanreport.bureau.job.request.day";
	public static final String BKI_JOB_SCHEDULE_START_TIME = "com.rssl.iccs.loanreport.bureau.job.request.startTime";
	public static final String BKI_JOB_SCHEDULE_END_TIME = "com.rssl.iccs.loanreport.bureau.job.request.endTime";
	public static final String BKI_JOB_ENABLED = "com.rssl.iccs.loanreport.bureau.job.enabled";
	public static final String BKI_JOB_SUSPENDED = "com.rssl.iccs.loanreport.bureau.job.suspended";
	public static final String BKI_JOB_PACK_SIZE = "com.rssl.iccs.loanreport.bureau.job.process.pack.size";
	public static final String BKI_JOB_LAST_START_TIME = "com.rssl.iccs.loanreport.bureau.job.start.time";
	public static final String BKI_JOB_LAST_START_DAY = "com.rssl.iccs.loanreport.bureau.job.lastTry.period.from";
	public static final String BKI_JOB_LAST_END_DAY = "com.rssl.iccs.loanreport.bureau.job.lastTry.period.to";
	public static final String SERVICE_PROVIDER_OKB_ID = "com.rssl.iccs.loanreport.service.provider.okb.id";
	public static final String BKI_TIMEOUT_MINUTES = "com.rssl.iccs.loanreport.bki.timeout.minutes";
	public static final String BKI_URL = "com.rssl.iccs.loanreport.bki.url";

	public static final String PDF_RESOURCES_PATH = "com.rssl.iccs.loanreport.pdf.resources.path";
	public static final String OKB_ADRRESS = "com.rssl.iccs.loanreport.okb.address";
	public static final String OKB_PHONE = "com.rssl.iccs.loanreport.okb.phone";

	public static final String BKI_PDF_BUTTON_SHOW = "com.rssl.iccs.loanreport.bki.pdf.button.show";
}
