package com.rssl.phizic.config.addressbook;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ������ ��� �������� ���������� �� �������� �����.
 *
 * @author bogdanov
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 */

public class AddressBookConfig extends Config
{

	public static final String LAST_UPDATE_TIME = "com.rssl.iccs.addressbook.lastupdatetime";
	public static final String TIME_FOR_START_SYNCHRONIZATION = "com.rssl.iccs.addressbook.timeforstart";
	private static final String PERIOD_INCOGNITO_SYNCHRONIZATION = "com.rssl.iccs.addressbook.incognito.synchronization";
	public static final String MBK_ARHIVE_PHONES_DICTIONARY_PATH = "com.rssl.iccs.addressbook.smart.phones.dictionaries.file";
	public static final String SHOW_SB_CLIENT_ATTRIBUTE = "com.rssl.iccs.addressbook.smart.show.sb.client.attribute";
	private static final String SBEKCLIENT_ALLOW_ERMB_SYNC = "com.rssl.iccs.addressbook.sberclient.synchronize.ermb";
	private static final String SBERCLIENT_SYNC_PACKSIZE = "com.rssl.iccs.addressbook.sberclient.synchronize.packsize";
	private static final String ALLOW_ACTUALIZE_ADDRESS_BOOK = "com.rssl.iccs.addressbook.allowactualize";
	public static final String FILL_FROM_P2P = "com.rssl.iccs.addressbook.fromP2P";
	public static final String AMOUNT_P2P_TO_ADD = "com.rssl.iccs.addressbook.amountP2P.toAdd";
	public static final String FILL_FROM_SERVICE_PAYMENTS = "com.rssl.iccs.addressbook.fromServicePayments";
	public static final String ALLOW_ONE_CONFIRM = "com.rssl.iccs.addressbook.allowOneConfirm";
	public static final String SHOW_LINK_CONFORM_CONTACT_ANDROID = "com.rssl.iccs.addressbook.show.link.confirmContact.android";
	public static final String TIMESTAMP = "HH:mm";

	private Long lastSynchronization;
	private Date timeForStartSynchronization;
	private File mbkDictionaryPath;
	private int incognitoPeriodSynchronization;
	private boolean showSbClientAttribute;
	private int sberclietnSyncPackSize;
	private boolean sberClientAllowERBMSync;
	private boolean allowActualizeAddressBook;
	private boolean fillAddressBookFromP2P;
	private int amountP2PToAdd;
	private boolean fillAddressBookFromServicePayments;
	private boolean allowOneConfirm;
	private boolean showLinkConfirmContactAndroid;

	/**
	 * @param reader �����.
	 */
	public AddressBookConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return ����� ��������� �������������.
	 */
	public Long getLastSynchronization()
	{
		return lastSynchronization;
	}

	/**
	 * @return ���� � ����������� ���������, ����������� �� �������� ���� ���.
	 */
	public File getMbkDictionaryPath()
	{
		return mbkDictionaryPath;
	}

	/**
	 * @return ����� ��� �������������.
	 */
	public Date getTimeForStartSynchronization()
	{
		return timeForStartSynchronization;
	}

	/**
	 * @return ������ ������������� �������� ���������. (�����������)
	 */
	public int getIncognitoPeriodSynchronization()
	{
		return incognitoPeriodSynchronization*60*1000;
	}

	/**
	 * @return ���������� �� ������� ������� ���������
	 */
	public boolean getShowSbClientAttribute()
	{
		return showSbClientAttribute;
	}

	/**
	 * @return ������ ����� ��� ������������ ������ ���.
	 */
	public int getSberclietnSyncPackSize()
	{
		return sberclietnSyncPackSize;
	}

	/**
	 * @return ��������� �� ������������� ������� ��������� � ����.
	 */
	public boolean isSberClientAllowERBMSync()
	{
		return sberClientAllowERBMSync;
	}

	/**
	 * @return ��������� �� ������������ �������� ����� ��������.
	 */
	public boolean isAllowActualizeAddressBook()
	{
		return allowActualizeAddressBook;
	}

	/**
	 * @return ������� ��������� �� ��� P2P ���������
	 */
	public boolean isFillAddressBookFromP2P()
	{
		return fillAddressBookFromP2P;
	}

	/**
	 * @return ���������� P2P ��������� ��� ��������, ��� ������� ���������� ���������� �������� � �������� �����
	 */
	public int getAmountP2PToAdd()
	{
		return amountP2PToAdd;
	}

	/**
	 * @return ������� ��������� �� ��� ������ �����
	 */
	public boolean isFillAddressBookFromServicePayments()
	{
		return fillAddressBookFromServicePayments;
	}

	/**
	 * @return �������: ��������� ������������� ����������� ������� ���� ��� �� ����� ���������� ������
	 */
	public boolean isAllowOneConfirm()
	{
		return allowOneConfirm;
	}

	/**
	 * @return �������: ����������� ������ ��� ������������� �������� ��� Android-���������
	 */
	public boolean isShowLinkConfirmContactAndroid()
	{
		return showLinkConfirmContactAndroid;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP);

		lastSynchronization = getLongProperty(LAST_UPDATE_TIME, -1);
		try
		{
			timeForStartSynchronization = sdf.parse(getProperty(TIME_FOR_START_SYNCHRONIZATION, "00:00"));
		}
		catch (ParseException ignore)
		{
		}
		mbkDictionaryPath = new File(getProperty(MBK_ARHIVE_PHONES_DICTIONARY_PATH, "C:\\"));
		incognitoPeriodSynchronization = getIntProperty(PERIOD_INCOGNITO_SYNCHRONIZATION, 1440);
		showSbClientAttribute = getBoolProperty(SHOW_SB_CLIENT_ATTRIBUTE);
		sberClientAllowERBMSync = getBoolProperty(SBEKCLIENT_ALLOW_ERMB_SYNC, true);
		sberclietnSyncPackSize = getIntProperty(SBERCLIENT_SYNC_PACKSIZE, 10000);
		allowActualizeAddressBook = getBoolProperty(ALLOW_ACTUALIZE_ADDRESS_BOOK, true);
		fillAddressBookFromP2P = getBoolProperty(FILL_FROM_P2P, true);
		amountP2PToAdd = getIntProperty(AMOUNT_P2P_TO_ADD, 1);
		fillAddressBookFromServicePayments = getBoolProperty(FILL_FROM_SERVICE_PAYMENTS, true);
		allowOneConfirm = getBoolProperty(ALLOW_ONE_CONFIRM, true);
		showLinkConfirmContactAndroid = getBoolProperty(SHOW_LINK_CONFORM_CONTACT_ANDROID, false);
	}
}
