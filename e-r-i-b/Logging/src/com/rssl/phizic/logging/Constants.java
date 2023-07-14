package com.rssl.phizic.logging;

import com.rssl.phizic.logging.system.LogModule;

/**
 * @author Kidyaev
 * @ created 27.03.2006
 * @ $Author: puzikov $
 * @ $Revision: 75900 $
 */
public final class Constants
{
	public final static String DB_INSTANCE_NAME = "Log";
	public final static String CSA_DB_LOG_INSTANCE_NAME = "CSA2Log";

	public final static String DEBUG = "D";
	public final static String INFO = "I";
	public final static String ERROR = "E";

	public final static LogModule LOG_MODULE_GATE = LogModule.Gate;
	public final static LogModule LOG_MODULE_CORE = LogModule.Core;
	public final static LogModule LOG_MODULE_SCHEDULER = LogModule.Scheduler;
	public final static LogModule LOG_MODULE_CACHE = LogModule.Cache;
	public final static LogModule LOG_MODULE_WEB = LogModule.Web;

	public final static String MESSAGE_LOG_PREFIX = "com.rssl.phizic.logging.messagesLog.";
	public final static String SYSTEM_LOG_PREFIX = "com.rssl.phizic.logging.systemLog.";
	public final static String USER_LOG_PREFIX  = "com.rssl.phizic.logging.operations.";
	public final static String ENTRIES_LOG_PREFIX = "com.rssl.phizic.logging.entries.";

	public final static String MESSAGE_LOG_ARCHIVE_NAME = "MessagesLog";
	public final static String SYSTEM_LOG_ARCHIVE_NAME = "SystemLog";
	public final static String USER_LOG_ARCHIVE_NAME  = "OperationsLog";
	public final static String ENTRIES_LOG_ARCHIVE_NAME  = "EntriesLog";

	public final static String AUTO_ARCHIVE_SUFFIX           = "archive.enable";
	public final static String AUTO_ARCHIVE_FOLDER_SUFFIX    = "archive.folder";
	public final static String AUTO_ARCHIVE_LEAVING_SUFFIX   = "archive.lastDays";
	public final static String AUTO_ARCHIVE_ARCH_PERIOD_SUFFIX = "archive.period";
	public final static String AUTO_ARCHIVE_ARCH_PERIOD_TYPE_SUFFIX = "archive.period.type";
	public final static String AUTO_ARCHIVE_ARCH_TIME_SUFFIX = "archive.archTime";

	public final static String AUTO_ARCHIVE_ARCH_PERIOD_DAY = "DAY";
	public final static String AUTO_ARCHIVE_ARCH_PERIOD_MONTH = "MONTH";

	public final static String AUTHENTICATION_FINISH_KEY = "default.authentification.finish";
	public final static String AUTHENTICATION_KEY = "default.authentification";
	public final static String LOGOFF_KEY = "default.logoff";
	public final static String COUNT_ACTIVE_SESSION_KEY_CLIENT = "count.active.session.phizic";
	public final static String COUNT_ACTIVE_SESSION_KEY_ADMIN = "count.active.session.phizia";
	public final static String EMPLOEE_AUTENTIFICATION_KEY = "emploee.autentication";
	public final static String CLIENT_AUTENTIFICATION_KEY = "client.autentication";
	public final static String CLIENT_MOBILE_AUTENTIFICATION_KEY = "client.mobile.autentication";
	public final static String CLIENT_IOS_AUTENTIFICATION_KEY = "client.ios.autentication";
	public final static String CLIENT_ATM_AUTENTIFICATION_KEY = "client.atm.autentication";

	public final static String SHOW_CARDS_ON_MAIN_PAGE_KEY = "com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction.cards";
	public final static String SHOW_ACCOUNTS_ON_MAIN_PAGE_KEY = "com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction.accounts";
	public final static String SHOW_LOANS_ON_MAIN_PAGE_KEY = "com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction.loans";
	public final static String SHOW_IMACCOUNTS_ON_MAIN_PAGE_KEY = "com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction.IMAccounts";
	public final static String SHOW_DEPOCCOUNTS_ON_MAIN_PAGE_KEY = "com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction.depoAccounts";

	public final static String EDIT_ERMB_MIGRATION_CONFLICT_KEY = "ermb.migration.conflict.edit";
}
