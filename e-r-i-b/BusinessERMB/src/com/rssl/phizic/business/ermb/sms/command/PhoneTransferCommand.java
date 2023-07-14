package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.annotation.OptionalParameter;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.payment.PersonPaymentManager;
import com.rssl.phizic.payment.PersonPaymentManagerImpl;
import com.rssl.phizic.payment.PhoneTransferTask;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 13.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���-������� ������� �� ������ ��������
 */
public class PhoneTransferCommand extends CommandBase
{
	/**
	 * ����� �������� �������� �������� ������������ (can be null)
	 */
	private String chargeOffAlias;

	/**
	 * ����� �������� ���������� (never null)
	 */
	private PhoneNumber destinationPhone;

	/**
	 * ����� (never null)
	 */
	private BigDecimal amount;

	///////////////////////////////////////////////////////////////////////////

	@OptionalParameter
	void setChargeOffAlias(String chargeOffAlias)
	{
		this.chargeOffAlias = chargeOffAlias;
	}

	@MandatoryParameter
	void setDestinationPhone(PhoneNumber destinationPhone)
	{
		this.destinationPhone = destinationPhone;
	}

	@MandatoryParameter
	void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		//�������� ��������� ����� "������� ������"
		ErmbProfileImpl profile = getErmbProfile();
		if (!profile.getFastServiceAvailable())
			throw new UserErrorException(new TextMessage("����� ���������� ����� �������� ������ ���������.\n" +
					"���������� ���������� ����� �������� ������ ����� � �������� ������  www.sberbank.ru"));

		//�������� �������� ����������: ��������� ��������
		for (String number : profile.getPhoneNumbers())
		{
			if (number.equals(PhoneNumberFormat.MOBILE_INTERANTIONAL.format(destinationPhone)))
				throw new UserErrorException(new TextMessage("�������� �� ���������. � ������� ������ ��� �������. ��� �������� ������� ������� ����������."));
		}

		PersonPaymentManager paymentManager = new PersonPaymentManagerImpl(getModule(), getPerson());
		PersonBankrollManager bankrollManager = getPersonBankrollManager();

		// ����� ������������ �������� ��������� �� ��������� <= �������� ���� �� �����
		BankrollProductLink chargeOffLink;
		if (StringHelper.isEmpty(chargeOffAlias))
			chargeOffLink = getPriorityCardLink(new Money(amount, bankrollManager.getRURCurrency()));
		else
			//noinspection unchecked
			chargeOffLink = bankrollManager.findProductBySmsAlias(chargeOffAlias, CardLink.class);

		if (chargeOffLink == null || !chargeOffLink.getShowInSms())
			throw new UserErrorException(messageBuilder.buildPhoneTransferBadSenderError());

		PhoneTransferTask task = paymentManager.createPhoneTransferTask();
		/*  ���� � ���-������� ������ ����� ��������, �� ���������� ���
			����� (����� � ��� �� ������) - ���������� "������������" �����
				���� � ������������ ����� ���������� ���������������� ���-�����, �� ���������� ���
				����� (����������������  ����� �� ����������) - ���������� �������������� ���-�����
		*/
		task.setFromResourceAlias(StringUtils.defaultIfEmpty(chargeOffAlias, StringUtils.defaultIfEmpty(chargeOffLink.getErmbSmsAlias(), chargeOffLink.getAutoSmsAlias())));
		task.setFromResourceCode(chargeOffLink.getCode());
		task.setChargeOffLink((CardLink)chargeOffLink);
		task.setPhone(destinationPhone);
		task.setAmount(amount);

		task.execute();
	}

	private BankrollProductLink getPriorityCardLink(Money amount)
	{
		try
		{
			return ErmbHelper.getPriorityCardLink(amount);
		}
		catch (BusinessException e)
		{
			throw new UserErrorException(messageBuilder.buildPhoneTransferBadSenderError());
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(messageBuilder.buildPhoneTransferBadSenderError());
		}
	}

	public String getChargeOffAlias()
	{
		return chargeOffAlias;
	}

	public PhoneNumber getDestinationPhone()
	{
		return destinationPhone;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	@Override
	public String toString()
	{
		return String.format("�������_��_������_��������[%s -> %s, �����: %s]",
				chargeOffAlias, MaskUtil.maskPhoneNumber(destinationPhone), amount);
	}

	public String getCommandName()
	{
		return "PHONE_TRANSFER";
	}
}
