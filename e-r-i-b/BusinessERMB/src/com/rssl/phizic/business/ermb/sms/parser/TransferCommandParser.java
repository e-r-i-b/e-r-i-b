package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.PhoneNumber;

import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 13.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ���-������� �������
 * ������� �� ����� �� "�����" ����� �� ��������!
 */
public class TransferCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// ������� = �������_������� ����������� ������� ����������� ������� ����������� �����
		//         | �������_������� ����������� ������� ����������� ������� ����������� �����
		//         | �������_������� ����������� ������� ����������� �����
		//         | �������_������� ����������� ����� ����������� �����_����� ����������� �����

		Command command = parseTransferCommandV1();

		if (command == null)
			command = parseTransferCommandV2();

		if (command == null)
			command = parseTransferCommandV3();

		if (command == null)
			command = parseTransferCommandV4();

		return command;
	}

	/**
	 * ��������� ������� ������� �� �������� "����� �����/����� -> ����� �����/�����"
	 * @return ������� ������� (����� ������ �������) ��� null, ���� ������� �� ��������
	 */
	private Command parseTransferCommandV1()
	{
		// ������� = �������_������� ����������� ������� ����������� ������� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordTransfer() && parseDelim())
		{
			String sender = parseProduct();
			if (sender != null && parseDelim())
			{
				String receiver = parseProduct();
				if (receiver != null && parseDelim())
				{
					BigDecimal amount = parseAmount();
					if (amount != null)
					{
						if (parseEOF())
						{
							// SUCCESS. ������� �������� �����, ����� �������� ��������, ����� �������� ���������� � �����
							completeLexeme(tx);
							return commandFactory.createInternalTransferCommand(sender, receiver, amount);
						}

						// FAIL-E. ����� ����� ���� ��� ���-��
						addError(messageBuilder.buildInternalCardTransferBadArgsError(getCardSmsAutoAliasLength()));
						cancelLexeme(tx);
						return null;
					}

					// FAIL-D. �� ������� ��� ����������� ������� �����
					addError(messageBuilder.buildCardTransferBadAmountError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. �� ������� ��� ����������� ������� �����/���� ����������
				addError(messageBuilder.buildCardOrPhoneTransferBadReceiverError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������� ��� ����������� ������� �����/���� �����������
			addError(messageBuilder.buildCardTransferBadSenderError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	/**
	 * ��������� ������� ������� �� �������� "����� ����� -> ����� �����"
	 * @return ������� ������� (�� �����) ��� null, ���� ������� �� ��������
	 */
	private Command parseTransferCommandV2()
	{
		// ������� = �������_������� ����������� ����� ����������� �����_����� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordTransfer() && parseDelim())
		{
			String sender = parseProduct();
			if (sender != null && parseDelim())
			{
				String receiver = parseOtherCard();
				if (receiver != null && parseDelim())
				{
					BigDecimal amount = parseAmount();
					if (amount != null)
					{
						if (parseEOF())
						{
							// SUCCESS. ������� �������� �����, ����� ����� ��������, ����� ����� ���������� � �����
							completeLexeme(tx);
							return commandFactory.createTransferCommand(sender, receiver, amount);
						}

						// FAIL-E. ����� ����� ���� ��� ���-��
						addError(messageBuilder.buildCardTransferBadArgsError(getCardSmsAutoAliasLength(), isOurCard(receiver)));
						cancelLexeme(tx);
						return null;
					}

					// FAIL-D. �� ������� ��� ����������� ������� �����
					addError(messageBuilder.buildCardTransferBadAmountError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. �� ������� ��� ����������� ������� �����/���� ����������
				addError(messageBuilder.buildCardOrPhoneTransferBadReceiverError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������� ��� ����������� ������� �����/���� �����������
			addError(messageBuilder.buildCardTransferBadSenderError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	/**
	 * ��������� ������� ������� �� �������� "����� �����/����� -> ����� ��������"
	 * @return ������� ������� (�� ��������) ��� null, ���� ������� �� ��������
	 */
	private Command parseTransferCommandV3()
	{
		// ������� = �������_������� ����������� ������� ����������� ������� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordTransfer() && parseDelim())
		{
			String sender = parseProduct();
			if (sender != null && parseDelim())
			{
				PhoneNumber receiver = parsePhone();
				if (receiver != null && parseDelim())
				{
					BigDecimal amount = parseAmount();
					if (amount != null)
					{
						if (parseEOF())
						{
							// SUCCESS. ������� �������� �����, ����� �������� ��������, ����� �������� � �����
							completeLexeme(tx);
							return commandFactory.createPhoneTransferCommand(sender, receiver, amount);
						}

						// FAIL-E. ����� ����� ���� ��� ���-��
						addError(messageBuilder.buildPhoneTransferBadArgsError());
						cancelLexeme(tx);
						return null;
					}

					// FAIL-D. �� ������� ��� ����������� ������� �����
					addError(messageBuilder.buildPhoneTransferBadAmountError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. �� ������ ��� ����������� ������ ������� ����������
				addError(messageBuilder.buildCardOrPhoneTransferBadReceiverError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������� ��� ����������� ������� �����/���� �����������
			addError(messageBuilder.buildPhoneTransferBadSenderError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	/**
	 * ��������� ������� ������� �� �������� "(�������� �����) -> ����� ��������"
	 * @return ������� ������� (�� ��������) ��� null, ���� ������� �� ��������
	 */
	private Command parseTransferCommandV4()
	{
		// ������� = �������_������� ����������� ������� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordTransfer() && parseDelim())
		{
			PhoneNumber receiver = parsePhone();
			if (receiver != null && parseDelim())
			{
				BigDecimal amount = parseAmount();
				if (amount != null)
				{
					if (parseEOF())
					{
						// SUCCESS. ������� �������� �����, ����� �������� � �����
						completeLexeme(tx);
						return commandFactory.createPhoneTransferCommand(receiver, amount);
					}

					// FAIL-D. ����� ����� ���� ��� ���-��
					addError(messageBuilder.buildPhoneTransferBadArgsError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. �� ������� ��� ����������� ������� �����
				addError(messageBuilder.buildPhoneTransferBadAmountError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������ ��� ����������� ������ �������
			addError(messageBuilder.buildCardOrPhoneTransferBadReceiverError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordTransfer()
	{
		return parseCommandKeyword(getKeywords());
	}

	private boolean isOurCard(String cardNumber)
	{
		try
		{
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Client client = ((ActivePerson)getPerson()).asClient();

			Pair<String, Office> stringOfficePair = new Pair<String, Office>(cardNumber, null);
			// noinspection unchecked
			GroupResult<Pair<String,Office>,Card> gresult = bankrollService.getCardByNumber(client, stringOfficePair);
			return GroupResultHelper.getOneResult(gresult) != null;
		}
		catch (LogicException e)
		{
			throw new InternalErrorException(e);
		}
		catch (SystemException e)
		{
			throw new InternalErrorException(e);
		}
	}
}
