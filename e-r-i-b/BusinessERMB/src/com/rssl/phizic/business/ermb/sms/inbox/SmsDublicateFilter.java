package com.rssl.phizic.business.ermb.sms.inbox;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.sms.config.SmsConfig;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.synchronization.types.SMSRq;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * @author Gulov
 * @ created 11.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ������ ������ �� ������������ ���������� ���.
 */
public class SmsDublicateFilter
{
	private static final SmsInBoxService service = new SmsInBoxService();

	private static final String EXIST_SMS_MESSAGE = "�������� �� ���������. ������� ��������� ������." +
			" ��� ���������� �������� �������� ����� ������� ��� ��������� ��� �������� ����� %s �����.";

	///////////////////////////////////////////////////////////////////////////

	public void execute(SMSRq request)
	{
		SmsInBox current = createSmsInbox(request);
		SmsInBox old = findInbox(current);
		if (old == null)
		{
			SmsConfig smsConfig = ConfigFactory.getConfig(SmsConfig.class);
			save(current, smsConfig.getDublicateExpireInterval());
		}
		else
			throw new UserErrorException(new TextMessage(String.format(EXIST_SMS_MESSAGE, getRemainingInterval(old, current))));
	}

	/**
	 * ���������� ���������� �����, ���������� �� ��������� �������� ���-�������.
	 * @param old - ������ ���-�������
	 * @param current - ����� ���-�������
	 * @return - ���������� �����
	 */
	private long getRemainingInterval(SmsInBox old, SmsInBox current)
	{
		SmsConfig smsConfig = ConfigFactory.getConfig(SmsConfig.class);
		Date date = DateUtils.addMinutes(old.getTime().getTime(), smsConfig.getDublicateExpireInterval());
		long result = (date.getTime() - current.getTime().getTimeInMillis()) / (1000 * 60);
		return result == 0 ? 1 : result;
	}

	private SmsInBox findInbox(SmsInBox current)
	{
		try
		{
			return service.findInbox(current);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException("������ ��������� ��������� ���", e);
		}
	}

	private void save(SmsInBox smsInBox, int remainingInterval)
	{
		try
		{
//			��������� ����� ���
			service.save(smsInBox);
		}
		catch (DublicateSmsInBoxException e)
		{
//			��������� ��������� ������� � ������������ ���
			throw new UserErrorException(new TextMessage(String.format(EXIST_SMS_MESSAGE, remainingInterval)), e);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException("������ ���������� ����� ��� " + smsInBox + " � ERMB_SMS_INBOX", e);
		}
	}

	private SmsInBox createSmsInbox(SMSRq request)
	{
		SmsInBox result = new SmsInBox();
		SmsConfig smsConfig = ConfigFactory.getConfig(SmsConfig.class);
		result.setUuid(request.getRqUID());
		result.setTime(request.getRqTime());
		result.setPhone(request.getPhone());
		result.setText(StringUtils.left(request.getText(), smsConfig.getSmsMaxLength()).toUpperCase());
		return result;
	}
}
