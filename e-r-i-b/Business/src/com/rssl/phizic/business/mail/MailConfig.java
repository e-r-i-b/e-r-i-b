package com.rssl.phizic.business.mail;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author bogdanov
 * @ created 23.01.14
 * @ $Author$
 * @ $Revision$
 */

public class MailConfig extends Config
{
	private static final String PROPERTY_DEFAULT_SUBJECT_ID  = "com.rssl.iccs.mail.subjects.DEFAULT_MAIL_SUBJECT_ID";
	private static final String PROPERTY_DEFAULT_MULTI_BLOCK_SUBJECT_ID  = "com.rssl.iccs.multiBlock.mail.subjects.DEFAULT_MAIL_SUBJECT_ID";
	public static final String LIST_MESSAGE_KEY = "com.rssl.iccs.list.message";
	public static final String CONFIRM_MESSAGE_KEY = "com.rssl.iccs.confirm.message";
	private static final String PROPERTY_ATTENTION_KEY = "com.rssl.iccs.attention.message";
	public static final String PROPERTY_LAST_MONTH_DELETE = "com.rssl.iccs.mail.archive.deleted.mail.lastMonth";
	public static final String LAST_MONTH_INCOMING_PATH_KEY = "com.rssl.iccs.mail.archive.incoming.mail.lastMonth";
	public static final String LAST_MONTH_OUTGOING_PATH_KEY = "com.rssl.iccs.mail.archive.outgoing.mail.lastMonth";
	public static final String ARCHIVE_PATH_KEY = "com.rssl.iccs.mail.archive.folder";
	public static final String PROPERTY_CLIENT_TEXT_LENGTH   = "com.rssl.iccs.settings.mail.client_text_length";
	public static final String PROPERTY_EMPLOYEE_TEXT_LENGTH = "com.rssl.iccs.settings.mail.employee_text_length";
	private static final String MAIL_ATTACH_SIZE_KEY = "com.rssl.iccs.mail.attach.size";
	private static final String ATTENTION_BY_PHONE = "com.rssl.iccs.attention.by_phone";
	public static final String DEFAULT_MESSAGE_TYPE_PROPERTY_KEY = "com.rssl.iccs.user.defaulttype.mailNotification";
	private static final String PROPERTY_DEFAULT_PERIOD  = "com.rssl.iccs.mail.subjects.DEFAULT_MAIL_PERIOD";


	private long defaultSubjectId;
	private long defaultMultiBlockSubjectId;
	private String listMessage;
	private String confirmMessage;
	private String attentionMessage;
	private int lastMonthDelete;
	private String archivePath;
	private int lastMonthIncoming;
	private int lastMonthOutgoing;
	private long clientTextLength;
	private long employeeTextLength;
	private int mailAttachSize;
	private String attntionByPhone;
	private String defaultMessageType;
	private long defaultPeriod;

	public MailConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		defaultSubjectId = getLongProperty(PROPERTY_DEFAULT_SUBJECT_ID);
		defaultMultiBlockSubjectId = getLongProperty(PROPERTY_DEFAULT_MULTI_BLOCK_SUBJECT_ID);
		listMessage = getProperty(LIST_MESSAGE_KEY);
		confirmMessage = getProperty(CONFIRM_MESSAGE_KEY);
		attentionMessage = getProperty(PROPERTY_ATTENTION_KEY);
		lastMonthDelete = getIntProperty(PROPERTY_LAST_MONTH_DELETE);
		lastMonthIncoming = getIntProperty(LAST_MONTH_INCOMING_PATH_KEY);
		lastMonthOutgoing = getIntProperty(LAST_MONTH_OUTGOING_PATH_KEY);
		archivePath = getProperty(ARCHIVE_PATH_KEY);
		clientTextLength = getLongProperty(PROPERTY_CLIENT_TEXT_LENGTH);
		employeeTextLength = getLongProperty(PROPERTY_EMPLOYEE_TEXT_LENGTH);
		mailAttachSize = getIntProperty(MAIL_ATTACH_SIZE_KEY);
		attntionByPhone = getProperty(ATTENTION_BY_PHONE);
		defaultMessageType = getProperty(DEFAULT_MESSAGE_TYPE_PROPERTY_KEY);
		defaultPeriod =  getLongProperty(PROPERTY_DEFAULT_PERIOD);
	}

	public long getDefaultSubjectId()
	{
		return defaultSubjectId;
	}

	public long getDefaultMultiBlockSubjectId()
	{
		return defaultMultiBlockSubjectId;
	}

	public String getConfirmMessage()
	{
		return confirmMessage;
	}

	public String getListMessage()
	{
		return listMessage;
	}

	public String getAttentionMessage()
	{
		return attentionMessage;
	}

	public int getLastMonthDelete()
	{
		return lastMonthDelete;
	}

	public int getLastMonthIncoming()
	{
		return lastMonthIncoming;
	}

	public int getLastMonthOutgoing()
	{
		return lastMonthOutgoing;
	}

	public String getArchivePath()
	{
		return archivePath;
	}

	public long getClientTextLength()
	{
		return clientTextLength;
	}

	public long getEmployeeTextLength()
	{
		return employeeTextLength;
	}

	public int getMailAttachSize()
	{
		return mailAttachSize;
	}

	public String getAttntionByPhone()
	{
		return attntionByPhone;
	}

	public String getDefaultMessageType()
	{
		return defaultMessageType;
	}

	public long getDefaultPeriod()
	{
		return defaultPeriod;
	}
}
