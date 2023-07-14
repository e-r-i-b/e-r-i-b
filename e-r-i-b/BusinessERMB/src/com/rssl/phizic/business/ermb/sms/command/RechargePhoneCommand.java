package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.common.forms.validators.passwords.SmsPasswordConfig;
import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.mobileOperator.ErmbMobileOperator;
import com.rssl.phizic.business.ermb.mobileOperator.ErmbMobileOperatorService;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.payment.PersonPaymentManager;
import com.rssl.phizic.payment.PersonPaymentManagerImpl;
import com.rssl.phizic.payment.RechargePhoneTask;
import com.rssl.phizic.security.PersonConfirmManager;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * ���-������� "������ ���������� ��������"
 * @author Rtischeva
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class RechargePhoneCommand extends CommandBase
{
	private final int confirmCodeLength = ConfigFactory.getConfig(SmsPasswordConfig.class).getSmsPasswordLength();

	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	private static final ErmbMobileOperatorService ermbMobileOperatorService = new ErmbMobileOperatorService();

	/**
	 * ����� ��������, ������� ������� ������, ���� ��� �����������
	 */
	private PhoneNumber phoneNumber;

	/**
	 * ����� ��������, ������� ������� ������, ���� ����� ������������ �����
	 */
	private String productAlias;

	/**
	 * ����� ������
	 */
	private BigDecimal amount;

	/**
	 * ���� ��������, ���������� �� ������ productAlias
	 */
	private transient BankrollProductLink link;

	public PhoneNumber getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getProductAlias()
	{
		return productAlias;
	}

	public void setProductAlias(String productAlias)
	{
		this.productAlias = productAlias;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public void doExecute()
	{
		String amountString = amount.toPlainString();
		//���� ����� ����� �� ���� ������ ������ ����� ���� ������������� � � ���� ���������� ��� �������������, ������� ���������� �� ����� ����������� ������ �������, ���������� ��������� �� ������
		if (amountString.length() == confirmCodeLength - 1 && similarConfirmCodeExists(amountString))
			throw new UserErrorException(messageBuilder.buildIncorrectConfirmCodeMessage());

		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		ErmbProfileImpl profile = getErmbProfile();
		//�������� ��������� ����� "������� ������"
		if (!profile.getFastServiceAvailable())
			throw new UserErrorException(messageBuilder.buildRechargePhoneQuickServiceDisabledMessage());

		PersonBankrollManager bankrollManager = getPersonBankrollManager();

		PhoneNumber clientMainPhoneNumber = PhoneNumber.fromString(profile.getMainPhoneNumber());

		boolean ownPhone = false;

		if (phoneNumber == null)
		{
			phoneNumber = clientMainPhoneNumber;
			ownPhone = true;
		}
		else
		{
			ownPhone = isOwnPhone(phoneNumber, profile);
		}

		ServiceProviderBase provider = null;

		try
		{
			provider = serviceProviderService.getMobileOperatorByPhoneNumber(phoneNumber);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}

		if (provider == null)
			throw new UserErrorException(messageBuilder.buildNotFoundRechargePhoneProvider());


		ErmbMobileOperator mobileOperator = null;
		try
		{
			mobileOperator = ermbMobileOperatorService.getByPhone(phoneNumber);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}

		if (mobileOperator == null)
			throw new InternalErrorException("�� ��������� ��������� �������� ��� �������� " + phoneNumber);

		if (productAlias == null)
			link = getPriorityCardLink(new Money(amount, bankrollManager.getRURCurrency()));
		else
			link = bankrollManager.findProductBySmsAlias(productAlias, CardLink.class);

		if (link == null)
			throw new UserErrorException(messageBuilder.buildPaymentCardNotFoundMessage());
		if (!link.getShowInSms())
			throw new UserErrorException(messageBuilder.buildPaymentCardWrongMessage());

		//���������, ��� ����� ������� �� ��������� � ���������� 4 ������� �� ����� ����� �������
		Collection<? extends BankrollProductLink> cardLinks = bankrollManager.getCards();
		isValidAmount(cardLinks);

		CardLink cardLink = (CardLink) link;

		PersonPaymentManager paymentManager = new PersonPaymentManagerImpl(getModule(), getPerson());
		RechargePhoneTask task = paymentManager.createRechargePhoneTask();
		task.setFromResourceCode(cardLink.getCode());
		task.setProviderKey(provider.getId());
		task.setAmountExternalId(getMainSumExternalId(provider));
		task.setAmount(amount);
		task.setPhoneNumber(PhoneNumberFormat.SIMPLE_NUMBER.format(phoneNumber));
		task.setRechargePhoneExternalId(getKeyExternalId(provider));
		task.setNotNeedConfirm(ownPhone);
		task.setOperatorName(mobileOperator.getName());
		task.execute();
	}

	private boolean similarConfirmCodeExists(String potentialConfirmCode)
	{
		PersonConfirmManager confirmManager = getPersonConfirmManager();
		return confirmManager.similarConfirmCodeExists(potentialConfirmCode, getPhoneTransmitter());
	}

	private CardLink getPriorityCardLink(Money amount)
	{
		try
		{
			return ErmbHelper.getPriorityCardLink(amount);
		}
		catch (BusinessException e)
		{
			throw new UserErrorException(messageBuilder.buildPaymentCardNotFoundMessage(), e);
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(messageBuilder.buildPaymentCardNotFoundMessage(), e);
		}
	}

	/**
	 * ���������, ���� �� ������� ����� ��������� ����-�������
	 * @param phoneNumber - �������
	 * @param profile - ����-�������
	 * @return true - ����, false  - ���
	 */
	private boolean isOwnPhone(PhoneNumber phoneNumber, ErmbProfileImpl profile)
	{
		Set<String> phones = profile.getPhoneNumbers();
		for (String phone : phones)
		{
			if (PhoneNumber.fromString(phone).equals(phoneNumber))
				return true;
		}
		return false;
	}

	private void isValidAmount(Collection<? extends BankrollProductLink> cardLinks)
	{
		for (BankrollProductLink card : cardLinks)
		{
			if (card.getAutoSmsAlias().equals(String.valueOf(amount)))
				throw new UserErrorException(messageBuilder.buildRechargePhoneAliasEqualsAmountMessage());
		}
	}

	@Override
	public String toString()
	{
		if (phoneNumber != null && productAlias != null)
			return String.format("�������[�������:%s, �����:%s, �����:%s]", phoneNumber, amount, productAlias);

		if (phoneNumber != null)
			return String.format("�������[�������:%s, �����:%s]", phoneNumber, amount);

		if (productAlias != null)
			return String.format("�������[�����:%s, �����:%s]", amount, productAlias);

		return String.format("�������[�����:%s]", amount);
	}

	private String getMainSumExternalId(ServiceProviderBase provider)
	{
		List<FieldDescription> fields = provider.getFieldDescriptions();
		for (FieldDescription field : fields)
		{
			if (field.isMainSum())
			{
				return field.getExternalId();
			}
		}
		throw new InternalErrorException("� ���������� " + provider.getId() + " �� ������ ���� � ������� ������");
	}

	private String getKeyExternalId(ServiceProviderBase provider)
	{
		List<FieldDescription> fields = provider.getFieldDescriptions();
		for (FieldDescription field : fields)
		{
			if (field.isKey())
			{
				return field.getExternalId();
			}
		}
		throw new InternalErrorException("� ���������� " + provider.getId() + " �� ������ �������� ����");
	}

	public String getCommandName()
	{
		return "RECHARGE_PHONE";
	}
}
