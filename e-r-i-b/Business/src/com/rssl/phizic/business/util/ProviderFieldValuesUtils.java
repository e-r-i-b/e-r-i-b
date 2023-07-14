package com.rssl.phizic.business.util;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.common.types.BusinessFieldSubType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.apache.commons.collections.CollectionUtils;

import java.util.Iterator;
import java.util.List;

/**
 * ��������� ������ ��� ������������ ��������� �������� ��� ����� ���������
 * @author niculichev
 * @ created 12.08.13
 * @ $Author$
 * @ $Revision$
 */
public class ProviderFieldValuesUtils
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String ITUNES_DEFAULT_VALUE_FIELD_NAME = "RecIdentifier";
	private static final AddressBookService addressBookService = new AddressBookService();

	/**
	 * �������� ��� ���� ���������� ���������� ������ �������� �� ���������
	 * (������� ������ ������ ����� ������ ����������)
	 * @param synchKey ����������
	 * @param code ����������
	 * @param externalId ������������� ����
	 * @return �������� �� ���������
	 */
	public static String getDefaultValue(String synchKey, String code, String externalId)
	{
		// ��� iTunes �������� ���� ������ ����� �������� ������ �������� ������� �� ���������
		if(isITunesProvider(synchKey, code) && ITUNES_DEFAULT_VALUE_FIELD_NAME.equals(externalId))
		{
			try
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
				String phoneNumber = MobileBankManager.getMainPhoneForCurrentUser();

				return PhoneNumberFormat.SIMPLE_NUMBER.format(PhoneNumber.fromString(phoneNumber));
			}
			catch (Exception e)
			{
				log.error("�� ������� �������� ������ ���������� �������", e);
				return null;
			}
		}

		return null;
	}

	/**
	 * �������� �� ������ ������ ��������� ITunes
	 * @param synchKey ����������
	 * @param code ����������
	 * @return true - ��������
	 * @deprecated ������� �� ����������� ����������
	 */
	@Deprecated
	public static boolean isITunesProvider(String synchKey, String code)
	{
		try
		{
			return AutoPaymentHelper.isIQWProvider(synchKey) && code.equals(ConfigFactory.getConfig(DocumentConfig.class).getiTunesProvider());
		}
		catch (Exception e)
		{
			log.error("�� ������� ���������� �������� �� ��������� ITunes", e);
			return false;
		}
	}

	/**
	 * �������� �������������  ����������� ���������� ��� � �������� ���������� �������� ��������
	 * ��� �������� ������������
	 * @return ������������� ����������� ���������� � ��������������� ��������� ���������
	 */
	public static Contact getDefaultValueDictionary(BusinessFieldSubType subType, String value) throws BusinessException
	{
		if(!PersonContext.isAvailable())
			return null;

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if(personData == null)
			return null;

		List<Contact> contacts = addressBookService.getClientContacts(personData.getLogin().getId());
		Iterator<Contact> iter = contacts.iterator();
		while (iter.hasNext())
		{
			if (!iter.next().getPhone().equals(value))
				iter.remove();
		}

		if(CollectionUtils.isEmpty(contacts))
			return null;

		return contacts.get(0);
	}
}
