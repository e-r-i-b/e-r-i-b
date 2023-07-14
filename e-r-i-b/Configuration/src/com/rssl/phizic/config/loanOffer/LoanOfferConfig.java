package com.rssl.phizic.config.loanOffer;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Nady
 * @ created 16.06.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Конфиг для кредитных оферт. Задача КУКО-3
 */
public class LoanOfferConfig extends Config
{
	private static final String UKO_COMMONCONDITIONS_EMPLOYEES = "com.rssl.iccs.crediting.offer.uko.commonConditions.employees";
	private static final String UKO_COMMONCONDITIONS_SALARIES = "com.rssl.iccs.crediting.offer.uko.commonConditions.salaries";
	private static final String UKO_COMMONCONDITIONS_PENSIONERS = "com.rssl.iccs.crediting.offer.uko.commonConditions.pensioners";
	private static final String UKO_COMMONCONDITIONS_ACCREDITS = "com.rssl.iccs.crediting.offer.uko.commonConditions.accredits";
	private static final String UKO_COMMONCONDITIONS_STREET = "com.rssl.iccs.crediting.offer.uko.commonConditions.street";

	private static final String UKO_PREAPPROVED_EMPLOYEES = "com.rssl.iccs.crediting.offer.uko.preapproved.employees";
	private static final String UKO_PREAPPROVED_SALARIES = "com.rssl.iccs.crediting.offer.uko.preapproved.salaries";
	private static final String UKO_PREAPPROVED_PENSIONERS = "com.rssl.iccs.crediting.offer.uko.preapproved.pensioners";
	private static final String UKO_PREAPPROVED_ACCREDITS = "com.rssl.iccs.crediting.offer.uko.preapproved.accredits";
	private static final String UKO_PREAPPROVED_STREET = "com.rssl.iccs.crediting.offer.uko.preapproved.street";

	private static final String VSP_COMMONCONDITIONS_EMPLOYEES = "com.rssl.iccs.crediting.offer.vsp.commonConditions.employees";
	private static final String VSP_COMMONCONDITIONS_SALARIES = "com.rssl.iccs.crediting.offer.vsp.commonConditions.salaries";
	private static final String VSP_COMMONCONDITIONS_PENSIONERS = "com.rssl.iccs.crediting.offer.vsp.commonConditions.pensioners";
	private static final String VSP_COMMONCONDITIONS_ACCREDITS = "com.rssl.iccs.crediting.offer.vsp.commonConditions.accredits";
	private static final String VSP_COMMONCONDITIONS_STREET = "com.rssl.iccs.crediting.offer.vsp.commonConditions.street";

	private static final String VSP_PREAPPROVED_EMPLOYEES = "com.rssl.iccs.crediting.offer.vsp.preapproved.employees";
	private static final String VSP_PREAPPROVED_SALARIES = "com.rssl.iccs.crediting.offer.vsp.preapproved.salaries";
	private static final String VSP_PREAPPROVED_PENSIONERS = "com.rssl.iccs.crediting.offer.vsp.preapproved.pensioners";
	private static final String VSP_PREAPPROVED_ACCREDITS = "com.rssl.iccs.crediting.offer.vsp.preapproved.accredits";
	private static final String VSP_PREAPPROVED_STREET = "com.rssl.iccs.crediting.offer.vsp.preapproved.street";

	private static final String OFFER_DISPLAY_COUNT_ON_MAIN_PAGE = "com.rssl.iccs.crediting.offer.offerDisplayCountOnMainPage";
	private static final String SEND_ACCEPT_FACT_TO_FRAUD_MONITORING = "com.rssl.iccs.crediting.offer.sendAcceptFactToFraudMonitoring";

	public static final String CHANGE_CREDIT_LIMIT_FEEDBACK = "com.rssl.iccs.changeCreditLimit.send.accept";

	private boolean ukoCommonConditionsEmployees;
	private boolean ukoCommonConditionsSalaries;
	private boolean ukoCommonConditionsPensioners;
	private boolean ukoCommonConditionsAccredits;
	private boolean ukoCommonConditionsStreet;

	private boolean ukoPreapprovedEmployees;
	private boolean ukoPreapprovedSalaries;
	private boolean ukoPreapprovedPensioners;
	private boolean ukoPreapprovedAccredits;
	private boolean ukoPreapprovedStreet;

	private boolean vspCommonConditionsEmployees;
	private boolean vspCommonConditionsSalaries;
	private boolean vspCommonConditionsPensioners;
	private boolean vspCommonConditionsAccredits;
	private boolean vspCommonConditionsStreet;

	private boolean vspPreapprovedEmployees;
	private boolean vspPreapprovedSalaries;
	private boolean vspPreapprovedPensioners;
	private boolean vspPreapprovedAccredits;
	private boolean vspPreapprovedStreet;

	private int offerDisplayCountOnMainPage;
	private boolean sendAcceptFactToFraudMonitoring;
	//отправка отклика "согласие" в CRM
	private boolean needSendFeedbackAccept;

	public LoanOfferConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		ukoCommonConditionsEmployees = getBoolProperty(UKO_COMMONCONDITIONS_EMPLOYEES);
		ukoCommonConditionsSalaries = getBoolProperty(UKO_COMMONCONDITIONS_SALARIES);
		ukoCommonConditionsPensioners = getBoolProperty(UKO_COMMONCONDITIONS_PENSIONERS);
		ukoCommonConditionsAccredits = getBoolProperty(UKO_COMMONCONDITIONS_ACCREDITS);
		ukoCommonConditionsStreet = getBoolProperty(UKO_COMMONCONDITIONS_STREET);

		ukoPreapprovedEmployees = getBoolProperty(UKO_PREAPPROVED_EMPLOYEES);
		ukoPreapprovedSalaries = getBoolProperty(UKO_PREAPPROVED_SALARIES);
		ukoPreapprovedPensioners = getBoolProperty(UKO_PREAPPROVED_PENSIONERS);
		ukoPreapprovedAccredits = getBoolProperty(UKO_PREAPPROVED_ACCREDITS);
		ukoPreapprovedStreet = getBoolProperty(UKO_PREAPPROVED_STREET);

		vspCommonConditionsEmployees = getBoolProperty(VSP_COMMONCONDITIONS_EMPLOYEES);
		vspCommonConditionsSalaries = getBoolProperty(VSP_COMMONCONDITIONS_SALARIES);
		vspCommonConditionsPensioners = getBoolProperty(VSP_COMMONCONDITIONS_PENSIONERS);
		vspCommonConditionsAccredits = getBoolProperty(VSP_COMMONCONDITIONS_ACCREDITS);
		vspCommonConditionsStreet = getBoolProperty(VSP_COMMONCONDITIONS_STREET);

		vspPreapprovedEmployees = getBoolProperty(VSP_PREAPPROVED_EMPLOYEES);
		vspPreapprovedSalaries = getBoolProperty(VSP_PREAPPROVED_SALARIES);
		vspPreapprovedPensioners = getBoolProperty(VSP_PREAPPROVED_PENSIONERS);
		vspPreapprovedAccredits = getBoolProperty(VSP_PREAPPROVED_ACCREDITS);
		vspPreapprovedStreet = getBoolProperty(VSP_PREAPPROVED_STREET);

		offerDisplayCountOnMainPage = getIntProperty(OFFER_DISPLAY_COUNT_ON_MAIN_PAGE);
		sendAcceptFactToFraudMonitoring = getBoolProperty(SEND_ACCEPT_FACT_TO_FRAUD_MONITORING);

		needSendFeedbackAccept = getBoolProperty(CHANGE_CREDIT_LIMIT_FEEDBACK);
	}

	public boolean isUkoCommonConditionsEmployees()
	{
		return ukoCommonConditionsEmployees;
	}

	public boolean isUkoCommonConditionsSalaries()
	{
		return ukoCommonConditionsSalaries;
	}

	public boolean isUkoCommonConditionsPensioners()
	{
		return ukoCommonConditionsPensioners;
	}

	public boolean isUkoCommonConditionsAccredits()
	{
		return ukoCommonConditionsAccredits;
	}

	public boolean isUkoCommonConditionsStreet()
	{
		return ukoCommonConditionsStreet;
	}

	public boolean isUkoPreapprovedEmployees()
	{
		return ukoPreapprovedEmployees;
	}

	public boolean isUkoPreapprovedSalaries()
	{
		return ukoPreapprovedSalaries;
	}

	public boolean isUkoPreapprovedPensioners()
	{
		return ukoPreapprovedPensioners;
	}

	public boolean isUkoPreapprovedAccredits()
	{
		return ukoPreapprovedAccredits;
	}

	public boolean isUkoPreapprovedStreet()
	{
		return ukoPreapprovedStreet;
	}

	public boolean isVspCommonConditionsEmployees()
	{
		return vspCommonConditionsEmployees;
	}

	public boolean isVspCommonConditionsSalaries()
	{
		return vspCommonConditionsSalaries;
	}

	public boolean isVspCommonConditionsPensioners()
	{
		return vspCommonConditionsPensioners;
	}

	public boolean isVspCommonConditionsAccredits()
	{
		return vspCommonConditionsAccredits;
	}

	public boolean isVspCommonConditionsStreet()
	{
		return vspCommonConditionsStreet;
	}

	public boolean isVspPreapprovedEmployees()
	{
		return vspPreapprovedEmployees;
	}

	public boolean isVspPreapprovedSalaries()
	{
		return vspPreapprovedSalaries;
	}

	public boolean isVspPreapprovedPensioners()
	{
		return vspPreapprovedPensioners;
	}

	public boolean isVspPreapprovedAccredits()
	{
		return vspPreapprovedAccredits;
	}

	public boolean isVspPreapprovedStreet()
	{
		return vspPreapprovedStreet;
	}

	public int getOfferDisplayCountOnMainPage()
	{
		return offerDisplayCountOnMainPage;
	}

	public boolean isSendAcceptFactToFraudMonitoring()
	{
		return sendAcceptFactToFraudMonitoring;
	}

	/**
	 * @return необходимо ли отправлять отклик "согласие" для предложения об изменении кредитного лимита
	 */
	public boolean isNeedSendFeedbackAccept()
	{
		return needSendFeedbackAccept && (ApplicationInfo.getCurrentApplication() == Application.PhizIC);
	}
}
