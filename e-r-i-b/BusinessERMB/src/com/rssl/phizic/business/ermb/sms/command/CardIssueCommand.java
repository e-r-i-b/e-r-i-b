package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.payment.CardIssueTask;
import com.rssl.phizic.payment.PersonPaymentManager;
import com.rssl.phizic.payment.PersonPaymentManagerImpl;

/**
 * @author Gulov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���-������� ����������
 */
public class CardIssueCommand extends CommandBase
{
	/**
	 * ����� ������������ �������� (never null)
	 */
	private String productAlias;

	/**
	 * ��� ����������� (never null)
	 * 0 � ����� ��������;
	 * 1 � ����� �������;
	 * 2 � �������������;
	 * 3 � ������ ���-����
	 * 4 � ������ ������/����� ��� ���. �������������
	 */
	private Integer issueCode;

	/**
	 * ���� �������� (�� �������� � ����)
	 */
	private transient BankrollProductLink productLink;

	@MandatoryParameter
	public void setProductAlias(String productAlias)
	{
		this.productAlias = productAlias;
	}

	@MandatoryParameter
	public void setIssueCode(Integer issueCode)
	{
		this.issueCode = issueCode;
	}

	@Override
	protected void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());

		BankrollProductLink link = getProductLink();
		if (link == null)
			throw new UserErrorException(messageBuilder.buildCardIssueNotFoundMessage());

		if (link instanceof CardLink)
		{
			PersonPaymentManager paymentManager = new PersonPaymentManagerImpl(getModule(), getPerson());
			CardIssueTask task = paymentManager.createCardIssueTask();
			task.setCardLink(getProductLink());
			task.setIssueCode(issueCode);
			task.setBlockingProductAlias(productAlias);
			task.execute();
		}
		else
			throw new UserErrorException(messageBuilder.buildCardIssueNotFoundMessage());
	}

	private BankrollProductLink getProductLink()
	{
		if (productLink == null)
			productLink = findProductLink();
		return productLink;
	}

	private BankrollProductLink findProductLink()
	{
		PersonBankrollManager bankrollManager = getPersonBankrollManager();
		return bankrollManager.findProductBySmsAlias(productAlias, CardLink.class);
	}

	@Override
	public String toString()
	{
		return String.format("����������[%s, ��� �������: %s]", productAlias, issueCode);
	}

	public String getCommandName()
	{
		return "CARD_ISSUE";
	}
}
