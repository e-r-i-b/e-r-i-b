package com.rssl.phizic.common.types.mail;

/**
 * @author mihaylov
 * @ created 22.05.14
 * @ $Author$
 * @ $Revision$
 */
public interface Constants
{
	public String GET_INCOME_MAIL_LIST_REQUEST_NAME = "getIncomeMailListRq";
	public String GET_OUTCOME_MAIL_LIST_REQUEST_NAME = "getOutcomeMailListRq";
	public String GET_REMOVED_MAIL_LIST_REQUEST_NAME = "getRemovedMailListRq";
	public String GET_MAIL_STATISTICS_REQUEST_NAME = "getMailStatisticsRq";
	public String GET_MAIL_AVERAGE_TIME_REQUEST_NAME = "getMailAverageTimeRq";
	public String GET_MAIL_FIRST_DATE_REQUEST_NAME = "getMailFirstDateRq";
	public String GET_MAIL_EMPLOYEE_STATISTICS_REQUEST_NAME = "getMailEmployeeStatisticsRq";
	public String PARAMETERS_TAG_NAME = "parameters";
	public String ORDER_PARAMETERS_TAG_NAME = "orderParameters";
	public String MAX_RESULTS_TAG_NAME = "maxResults";
	public String FIRST_RESULT_TAG_NAME = "firstResult";

	public String QUEUE_NAME           = "jms/resources/MultiNodeResourcesOutputQueue";
	public String FACTORY_NAME         = "jms/resources/MultiNodeResourcesOutputQCF";
}
