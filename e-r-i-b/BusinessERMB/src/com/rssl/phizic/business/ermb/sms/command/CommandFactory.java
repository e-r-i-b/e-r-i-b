package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias;
import com.rssl.phizic.security.ConfirmToken;
import com.rssl.phizic.utils.PhoneNumber;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Erkin
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� �� ������������ ���-������
 */
public class CommandFactory
{
	/**
	 * ������ ���-������� ������
	 * @param productAlias - ����� �������� ��� null
	 * @return ����� ���-������� ������
	 */
	public Command createBalanceCommand(String productAlias)
	{
		BalanceCommand command = new BalanceCommand();
		command.setProductAlias(productAlias);
		return command;
	}

	/**
	 * ������ ���-������� ����
	 * @return ����� ���-������� ����
	 */
	public Command createCurrencyRateCommand()
	{
		return new CurrencyRateCommand();
	}

	/**
	 * ������ ���-������� �������
	 * @param productAlias - ����� �������� ��� null
	 * @return ����� ���-������� �������
	 */
	public Command createHistoryCommand(String productAlias)
	{
		HistoryCommand command = new HistoryCommand();
		command.setProductAlias(productAlias);
		return command;
	}

	/**
	 * ������ ���-������� ����������
	 * @param productAlias - ����� �������� ��� null
	 * @return ����� ���-������� ����������
	 */
	public Command createProductInfoCommand(String productAlias)
	{
		ProductInfoCommand command = new ProductInfoCommand();
		command.setProductAlias(productAlias);
		return command;
	}

	/**
	 * ������ ���-������� ������� �������
	 * @return ����� ���-������� ������� �������
	 */
	public Command createQuickServicesCommand()
	{
		return new QuickServicesCommand();
	}

	/**
	 * ������ ���-������� "�������� ������� �����"
	 * @return ����� ���-������� �����
	 */
	public Command createShowTariffCommand()
	{
		return new ShowTariffCommand();
	}

	/**
	 * ������ ���-������� "������� �����"
	 * @param name - ��� �������� ������ (never null)
	 * @return ����� ���-������� �����
	 */
	public Command createChangeTariffCommand(String name)
	{
		ChangeTariffCommand command = new ChangeTariffCommand();
		command.setName(name);
		return command;
	}

	/**
	 * ������ ���-������� ������� ����� ������ �������
	 * @param chargeOffAlias - ����� �������� �������� (never null)
	 * @param destinationAlias - ����� �������� ���������� (never null)
	 * @param amount - ����� ���������� (never null)
	 * @return ����� ���-������� �������
	 */
	public Command createInternalTransferCommand(String chargeOffAlias, String destinationAlias, BigDecimal amount)
	{
		InternalTransferCommand command = new InternalTransferCommand();
		command.setChargeOffAlias(chargeOffAlias);
		command.setDestinationAlias(destinationAlias);
		command.setAmount(amount);
		return command;
	}

	/**
	 * ������ ���-������� ������� ����� �������
	 * @param chargeOffAlias - ����� �������� �������� (never null)
	 * @param destinationCardNumber - ����� ����� ���������� (never null)
	 * @param amount - ����� ���������� (never null)
	 * @return ����� ���-������� �������
	 */
	public Command createTransferCommand(String chargeOffAlias, String destinationCardNumber, BigDecimal amount)
	{
		CardTransferCommand command = new CardTransferCommand();
		command.setChargeOffAlias(chargeOffAlias);
		command.setDestinationCard(destinationCardNumber);
		command.setAmount(amount);
		return command;
	}

	/**
	 * ������ ���-������� ������� �� ������ �������� � ��������� ��������� ��������
	 * @param chargeOffAlias - ����� �������� �������� (never null)
	 * @param destinationPhone - ������� ���������� (never null)
	 * @param amount - ����� ���������� (never null)
	 * @return ����� ���-������� �������
	 */
	public Command createPhoneTransferCommand(String chargeOffAlias, PhoneNumber destinationPhone, BigDecimal amount)
	{
		PhoneTransferCommand command = new PhoneTransferCommand();
		command.setChargeOffAlias(chargeOffAlias);
		command.setDestinationPhone(destinationPhone);
		command.setAmount(amount);
		return command;
	}

	/**
	 * ������ ���-������� ������� �� ������ �������� ��� �������� ��������� ��������
	 * @param destinationPhone - ������� ���������� (never null)
	 * @param amount - ����� ���������� (never null)
	 * @return ����� ���-������� �������
	 */
	public Command createPhoneTransferCommand(PhoneNumber destinationPhone, BigDecimal amount)
	{
		PhoneTransferCommand command = new PhoneTransferCommand();
		command.setDestinationPhone(destinationPhone);
		command.setAmount(amount);
		return command;
	}

	/**
	 * ������ ���-������� �������
	 * @param receiverAlias - ����� ����������, �� �������� ������������� �������
	 * @return ����� ���-������� �������
	 */
	public Command createTemplateCommand(String receiverAlias)
	{
		TemplateCommand command = new TemplateCommand();
		command.setReceiverAlias(receiverAlias);
		return command;
	}

	/**
	 * ������ ���-������� �������������, ��������� ��� �������������
	 * @param confirmCode - ��� �������������
	 * @return ����� ���-������� �������������
	 */
	public Command createConfirmCommand(String confirmCode)
	{
		ConfirmCommand command = new ConfirmCommand();
		command.setConfirmCode(confirmCode);
		return command;
	}

	/**
	 * ������ ���-������� �������������, ��������� ����� �������������
	 * @param confirmToken - ����� �������������
	 * @return ����� ���-������� �������������
	 */
	public Command createConfirmCommand(ConfirmToken confirmToken)
	{
		ConfirmCommand command = new ConfirmCommand();
		command.setConfirmToken(confirmToken);
		return command;
	}

	/**
	 * ������ ���-������� ����������
	 * @param productAlias - ����� ��������
	 * @param blockCode - ��� ����������
	 * @return ����� ���-������� ����������
	 */
	public Command createProductBlockCommand(String productAlias, Integer blockCode)
	{
		ProductBlockCommand command = new ProductBlockCommand();
		command.setProductAlias(productAlias);
		command.setBlockCode(blockCode);
		return command;
	}

	/**
	 * ������� ���-������� "������ ���������� ��������" � ��������� ������ �������� ��� ������
	 * (������ ������ ������)
	 * @param phoneNumber - ����� �������� ��� ������ (never null)
	 * @param amount - ����� ������ (never null)
	 * @param productAlias - �������� �������� (can be null)
	 * @return ����� ���-������� "������ ���������� ��������"
	 */
	public Command createRechargePhoneCommand(PhoneNumber phoneNumber, BigDecimal amount, String productAlias)
	{
		RechargePhoneCommand command = new  RechargePhoneCommand();
		command.setPhoneNumber(phoneNumber);
		command.setAmount(amount);
		command.setProductAlias(productAlias);
		return command;
	}

	/**
	 * ������� ���-������� "������ ���������� ��������" ��� �������� ������ �������� ��� ������
	 * (������ ������ ������)
	 * @param amount - ����� ������ (never null)
	 * @param productAlias - �������� �������� (can be null)
	 * @return ����� ���-������� "������ ���������� ��������"
	 */
	public Command createRechargePhoneCommand(BigDecimal amount, String productAlias)
	{
		RechargePhoneCommand command = new RechargePhoneCommand();
		command.setAmount(amount);
		command.setProductAlias(productAlias);
		return command;
	}

	/**
	 * ������� ���-������� "������ �����"
	 * @param  serviceProviderSmsAlias - ���-����� ����������
	 * @param  requisites - ��������� ��� ������
	 * @return ����� ���-������� "������ �����"
	 */
	public Command createServicePaymentCommand(ServiceProviderSmsAlias serviceProviderSmsAlias, List<String> requisites)
	{
		ServicePaymentCommand command = new ServicePaymentCommand();
		command.setServiceProviderSmsAlias(serviceProviderSmsAlias);
		command.setRequisites(requisites);
		return command;
	}

	/**
	 * ������� ���-������� "������ �� �������"
	 * @param  template - ������
	 * @param amount - �����
	 * @param card - ����� �����
	 * @return ����� ���-������� "������ �� �������"
	 */
	public Command createTemplatePaymentCommand(TemplateDocument template, BigDecimal amount, String card)
	{
		TemplatePaymentCommand command = new TemplatePaymentCommand();
		command.setTemplate(template);
		command.setAmount(amount);
		command.setCardAlias(card);
		return command;
	}

	/**
	 * ������� ���-������� "������ �������"
	 * @param loanAlias - ����� �������
	 * @param amount -�����
	 * @param cardAlias - ����� ����� ��������
	 * @return ����� ���-������� "������ �������"
	 */
	public Command createLoanPaymentCommand(String loanAlias, BigDecimal amount, String cardAlias)
	{
		LoanPaymentCommand command = new LoanPaymentCommand();
		command.setLoanAlias(loanAlias);
		command.setAmount(amount);
		command.setCardAlias(cardAlias);
		return command;
	}

	/**
	 * ������� ���-������� "������ �������"
	 * @return ����� ���-������� "������ �������"
	 */
	public Command createLoanPaymentCommand()
	{
		LoanPaymentCommand command = new LoanPaymentCommand();
		command.setLoanAlias(null);
		command.setAmount(null);
		command.setCardAlias(null);
		return command;
	}

	/**
	 * ������� �������� "�����������/�������� ������� � ��������� ���������� �������� �� ���������"
	 * @return ����� ���-������� "������� �� ���������"
	 */
	public Command createLoyaltyCommand()
	{
		return new SberThanksCommand();
	}

	/**
	 * ������� ���-������� ����������� �����������
	 * @param phone - ����� ��������
	 * @param amount - �����
	 * @param threshold - �����
	 * @param limit - �����
	 * @param cardAlias - ����� �����
	 * @return ����� ���-������� ����������� �����������
	 */
	public Command createCreateAutoPaymentCommand(PhoneNumber phone, BigDecimal amount, BigDecimal threshold, BigDecimal limit, String cardAlias)
	{
		CreateAutoPaymentCommand command = new CreateAutoPaymentCommand();
		command.setPhoneNumber(phone);
		command.setAmount(amount);
		command.setThreshold(threshold);
		command.setLimit(limit);
		command.setCardAlias(cardAlias);
		return command;
	}

	/**
	 * ������� ���-������� ���������� �����������
	 * @param phone ����� ��������
	 * @param cardAlias ����� �����
	 * @return
	 */
	public Command createRefuseAutoPaymentCommand(PhoneNumber phone, String cardAlias)
	{
		RefuseAutoPaymentCommand command = new RefuseAutoPaymentCommand();
		command.setPhoneNumber(phone);
		command.setCardAlias(cardAlias);
		return command;
	}

	/**
	 * ������ ���-������� ����������
	 * @param productAlias - ����� �������� (can't be null)
	 * @param blockCode - ��� ���������� (can't be null)
	 * @return ����� ���-������� ����������
	 */
	public Command createCardIssueCommand(String productAlias, Integer blockCode)
	{
		CardIssueCommand command = new CardIssueCommand();
		command.setProductAlias(productAlias);
		command.setIssueCode(blockCode);
		return command;
	}
}
