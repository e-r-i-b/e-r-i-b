package com.rssl.phizic.business.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * ���������������� ������ �������� ��� ���������
 * @author Pankin
 * @ created 17.11.2011
 * @ $Author$
 * @ $Revision$
 */

public enum SendSMSPreferredMethod
{
	// ������ �������
	PROFILE
			{
				public Collection<String> getPhones(ActivePerson person, boolean isNotification) throws BusinessException
				{
					//�������� �������� �� ������
					Collection<String> phones = getUserMobilePhone(person);
					//���� ���, �� �� ���������� �����
					if (CollectionUtils.isEmpty(phones))
						return MOBILE_BANK_ONLY.getPhones(person,isNotification);
					//���� ��� ��������� � �����, �� ��������� ����� �� ��
					if (isNotification)
						phones.addAll(MOBILE_BANK_ONLY.getPhones(person,isNotification));
					return phones;
				}
			},
	// ��������� ����
	MOBILE_BANK
			{
				public Collection<String> getPhones(ActivePerson person, boolean isNotification) throws BusinessException
				{
					//�������� �������� �� ���������� �����
					Collection<String> phones = MOBILE_BANK_ONLY.getPhones(person, isNotification);
					//���� ���, �� �� ������
					if (CollectionUtils.isEmpty(phones))
						return getUserMobilePhone(person);
					//���� ��� ��������� � �����, �� ��������� ����� �� ������
					if (isNotification)
						phones.addAll(getUserMobilePhone(person));

					return phones;
				}
			},
	// ������������ ������ ��������� ����
	MOBILE_BANK_ONLY
			{
				public Collection<String> getPhones(ActivePerson person, boolean isNotification) throws BusinessException
				{
					try
					{
						//�������� �������� ������ �� ���������� �����
						Collection<String> phones;
						if (isNotification)
							phones = StaticPersonData.getPhones(person.getLogin(), null);
						else
							phones = StaticPersonData.getPhones(person.getLogin(), false);

						if (phones == null)
							return Collections.emptyList();
						return phones;
					}
					catch (BusinessLogicException e)
					{
						throw new BusinessException(e);
					}
				}
			};

	protected Collection<String> getUserMobilePhone(ActivePerson person) throws BusinessException
	{
		String phone = person.getMobilePhone();
		if (StringHelper.isEmpty(phone))
			return Collections.emptyList();
	    Collection<String> phoneList = new ArrayList<String>();
		phoneList.add(phone);
		return phoneList;
	}

	/**
	 * �������� ������ ��������� � ����������� �� ��������� �����
	 * @param person ������
	 * @param isNotification - true, ���� ���������� ���������� � �����
	 * @return ������ ���������
	 * @throws BusinessException
	 */
	public Collection<String> getPhones(ActivePerson person, boolean isNotification) throws BusinessException
	{
		throw new UnsupportedOperationException("�� ����� ����� ��������� ��������");
	}
}
