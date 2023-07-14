package com.rssl.phizic.web.security;

/**
 * @author Evgrafov
 * @ created 28.12.2005
 * @ $Author: rtishcheva $
 * @ $Revision: 72237 $
 */

public abstract class Constants
{
	public static final String FILTER_FLAG = "com.rssl.phizic.web.security.filter.flag";

	public static final String MESSAGE_PREFIX = "PHIZ_GATE_EXCEPTION_START";
	public static final String MESSAGE_SUFFIX = "PHIZ_GATE_EXCEPTION_END";
	public static final String TEMPORAL_MESSAGE_PREFIX = "PHIZ_TEMPORAL_GATE_EXCEPTION_START";
	public static final String TEMPORAL_MESSAGE_SUFFIX = "PHIZ_TEMPORAL_GATE_EXCEPTION_END";
	public static final String LOGIC_MESSAGE_PREFIX = "PHIZ_GATE_LOGIC_EXCEPTION_START";
	public static final String LOGIC_MESSAGE_SUFFIX = "PHIZ_GATE_LOGIC_EXCEPTION_END";
	public static final String LOGIC_ERROR_CODE_PREFIX = "PHIZ_GATE_LOGIC_EXCEPTION_ERROR_CODE_PREFIX";
	public static final String LOGIC_ERROR_CODE_SUFFIX = "PHIZ_GATE_LOGIC_EXCEPTION_ERROR_CODE_SUFFIX";
	public static final String TIMEOUT_MESSAGE_PREFIX = "PHIZ_GATE_TIMEOUT_EXCEPTION_START";
	public static final String TIMEOUT_MESSAGE_SUFFIX = "PHIZ_GATE_TIMEOUT_EXCEPTION_END";
	public static final String OFFLINE_SERVICE_MESSAGE_PREFIX = "PHIZ_GATE_OFFLINE_SERVICE_EXCEPTION_START";
	public static final String OFFLINE_SERVICE_MESSAGE_SUFFIX = "PHIZ_GATE_OFFLINE_SERVICE_EXCEPTION_END";
	public static final String INACTIVE_SERVICE_MESSAGE_PREFIX = "PHIZ_GATE_INACTIVE_SERVICE_EXCEPTION_START";
	public static final String INACTIVE_SERVICE_MESSAGE_SUFFIX = "PHIZ_GATE_INACTIVE_SERVICE_EXCEPTION_END";

    public static final String IS_LIGHT_SCHEME_PARAM_NAME = "isLightScheme"; //ключ параметра риквеста, определ€ющего используетс€ ли light-схема прав в mAPI.
}
