package com.rssl.phizic.config.loyalty;

import com.rssl.phizic.utils.CihperHelper;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author gladishev
 * @ created 14.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyHelper
{
	private static final String SHA1_ERROR = "����������: ������ ��� ������� �������� �������� ��������� ������(SHA1)";
	private static final Log log = LogFactory.getLog(LoyaltyHelper.class);

	/**
	 * ��������� �� ��������� ��� �� ������ ����� �������
	 * @param cardNumber - ����� �����
	 * @return ���
	 */
	public static String generateHash(String cardNumber)
	{
		if (StringHelper.isEmpty(cardNumber))
			return null;

		CihperHelper cihperHelper = new CihperHelper();
		//��������� SHA1 ���������
		String sha1String = null;
		try
		{
			sha1String = cihperHelper.SHA1(cardNumber);
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error(SHA1_ERROR,e);
			return null;
		}
		catch (UnsupportedEncodingException e)
		{
			log.error(SHA1_ERROR,e);
			return null;
		}
		return sha1String;
	}

	/**
	 * �������������� ������ �������� ��� ����� ����������� � ��
	 * @param phone - ����� ��������
	 * @return ��������������� ����� ��������
	 */
	public static String formatPhone(String phone)
	{
		PhoneNumber number = PhoneNumber.fromString(phone);
		return "+7" + " (" + number.operator() + ") <span class='loyaltyPhone'>���</span> " + number.abonent().substring(3,5) + " " + number.abonent().substring(5,7);
	}
}
