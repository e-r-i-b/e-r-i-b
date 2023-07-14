package com.rssl.phizic.business.ermb;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;

/**
 * @author Dorzhinov
 * @ created 13.08.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ����� ��� ������ ������ ������ ��������
 * @param <T>    ��� ������������� ��������
 */
public abstract class OurPhoneSearcher<T>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ��������� �� ��������� � ����� �� "�����" ������� ���������
	 *
	 * @param testingPhone    ����������� �������
	 * @param ourPhone    "���" �������
	 * @return true - ���� ����������� ������� - "���"
	 * �����! ����� �� ������ ����������� ����������
	 */
	public boolean isEquals(String testingPhone, String ourPhone)
	{
		// A. �������: testingPhone �� ���������� � ��������� � ourPhone �� �������
		if (StringUtils.equals(testingPhone, ourPhone))
			return true;

		// ��������� �������� ����� ������� (���� testingPhone �� ����������)
		PhoneNumber testingPhoneNumber;
		try
		{
			testingPhoneNumber = PhoneNumber.fromString(testingPhone);
		}
		catch (NumberFormatException ignored)
		{
			testingPhoneNumber = null;
		}

		try
		{
			// B. ��������� ��������������� ��������
			PhoneNumber ourPhoneNumber = PhoneNumber.fromString(ourPhone);
			if (testingPhoneNumber != null && ourPhoneNumber.equals(testingPhoneNumber))
				return true;

			// C. �� �������: testingPhone ������ � ������������� ����, ������� ������ �� ��������.
			// �.�. �� ��������, � ����� ������� ������ ������������� �������, ���������� ���������� ��� �������
			for (PhoneNumberFormat phoneFormat : PhoneNumberFormat.values())
			{
				String ourHiddenPhone = phoneFormat.formatAsHidden(ourPhoneNumber);
				if (ourHiddenPhone.equals(testingPhone))
					return true;
			}

			return false;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * ���� "���" ����� ��������
	 *
	 * @param phone    ����������� �������
	 * @param ourPhones    "����" ��������
	 * @return ��������� "���" ������� ��� null
	 * �����! ����� �� ������ ����������� ����������
	 */
	public String selectOurPhone(String phone, Collection<String> ourPhones)
	{
		for (String ourPhone : ourPhones)
		{
			if (isEquals(phone, ourPhone))
				return ourPhone;
		}

		return null;
	}

	/**
	 * ����������� ���� ����������� ������� �� ��� ������ ����� "�����" ��� �������� ������
	 * @return T
	 */
	public abstract T returnOnFail();

	/**
	 * ����������� ���� ����������� ������� - "���"
	 * @param ourPhone    "���" �������
	 * @return T
	 */
	public abstract T returnIfOur(String ourPhone);
}
