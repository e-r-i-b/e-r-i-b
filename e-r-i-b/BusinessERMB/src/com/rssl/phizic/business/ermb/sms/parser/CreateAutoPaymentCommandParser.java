package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.person.ErmbProfile;
import com.rssl.phizic.utils.PhoneNumber;

import java.math.BigDecimal;

import static com.rssl.phizic.business.ermb.sms.parser.Lexeme.PHONE;

/**
 * ������ ���-������� ����������� �����������
 * @author Rtischeva
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateAutoPaymentCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		//����������	= �������_���������� ����������� ������� ����������� ����� ����������� �����
		//              | �������_���������� ����������� ������� ����������� ����� ����������� ����� ����������� �����
		//              | �������_���������� ����������� ������� ����������� �����
		//              | �������_���������� ����������� ������� ����������� ����� ����������� �����

		Command command = parseCreateAutoPaymentCommandV1();

		if (command == null)
			command = parseCreateAutoPaymentCommandV2();

		if (command == null)
			command = parseCreateAutoPaymentCommandV3();

		if (command == null)
			command = parseCreateAutoPaymentCommandV4();

		return command;

	}

	private Command parseCreateAutoPaymentCommandV1()
	{
		//���������� = �������_���������� ����������� ������� ����������� ����� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordCreateAutoPayment() && parseDelim())
		{
			ParseTransaction phoneTx = beginLexeme(PHONE);
			PhoneNumber phone = parsePhone(phoneTx);
			if (phone != null && parseDelim())
			{
				if (isClientPhone(phone))
				{
					BigDecimal amount = parseAmount();
					if (amount != null && parseDelim())
					{
						BigDecimal threshold = parseAmount();
						if (threshold != null)
						{
							if (parseEOF())
							{
								// SUCCESS. ������� �������� �����, �������, ����� � ����� ���� �������� �����, �������, ����� � �����. ������� ���������� ������� �� ����� �����������, ����������� ����� ������
								completeLexeme(tx);
								return commandFactory.createCreateAutoPaymentCommand(phone, amount, threshold, amount, String.valueOf(threshold));
							}

							// FAIL-F. ����� ������ ��� ���-�� ���
							addError(messageBuilder.buildAutoPayBadThresholdMessage(phone));
							cancelLexeme(tx);
							return null;
						}

						// FAIL-E. �� ������ ��� ����������� ������ �����
						addError(messageBuilder.buildAutoPayBadThresholdMessage(phone));
						cancelLexeme(tx);
						return null;
					}
					// FAIL-D. �� ������� ��� ����������� ������� �����
					addError(messageBuilder.buildAutoPayBadAmountMessage(phone));
					cancelLexeme(tx);
					return null;
				}
				// FAIL-C. ������ �� ���� ����� ��������
				addError(messageBuilder.buildAutoPayIncorrectYourPhoneMessage(phone));
				cancelLexeme(tx);
				return null;
			}

			cancelLexeme(phoneTx);
			// FAIL-B. �� ������ ��� ����������� ������ �������
			addError(messageBuilder.buildAutoPayIncorrectPhoneMessage(parseString()));
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseCreateAutoPaymentCommandV2()
	{
		//���������� = �������_���������� ����������� ������� ����������� ����� ����������� ����� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordCreateAutoPayment() && parseDelim())
		{
			ParseTransaction phoneTx = beginLexeme(PHONE);
			PhoneNumber phone = parsePhone(phoneTx);
			if (phone != null && parseDelim())
			{
				if (isClientPhone(phone))
				{
					BigDecimal amount = parseAmount();
					if (amount != null && parseDelim())
					{
						BigDecimal threshold = parseAmount();
						if (threshold != null && parseDelim())
						{
							String card = parseProduct();
							if (card != null)
							{
								if (parseEOF())
								{
									// SUCCESS. ������� �������� �����, �������, �����, ����� � �����
									completeLexeme(tx);
									return commandFactory.createCreateAutoPaymentCommand(phone, amount, threshold, null, card);
								}

								// FAIL-G. ����� ����� ��� ���-�� ���
								addError(messageBuilder.buildAutoPayIncorrectCardMessage(phone));
								cancelLexeme(tx);
								return null;
							}

							// FAIL-F. ������ ������ ����� ���-�� ����������
							addError(messageBuilder.buildAutoPayIncorrectCardMessage(phone));
							cancelLexeme(tx);
							return null;
						}

						// FAIL-E. �� ������ ��� ����������� ������ �����
						addError(messageBuilder.buildAutoPayBadThresholdMessage(phone));
						cancelLexeme(tx);
						return null;
					}
					// FAIL-D. �� ������� ��� ����������� ������� �����
					addError(messageBuilder.buildAutoPayBadAmountMessage(phone));
					cancelLexeme(tx);
					return null;
				}
				// FAIL-C. ������ �� ���� ����� ��������
				addError(messageBuilder.buildAutoPayIncorrectYourPhoneMessage(phone));
				cancelLexeme(tx);
				return null;
			}
			cancelLexeme(phoneTx);
			// FAIL-B. �� ������ ��� ����������� ������ �������
			addError(messageBuilder.buildAutoPayIncorrectPhoneMessage(parseString()));
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseCreateAutoPaymentCommandV3()
	{
		//���������� =  �������_���������� ����������� ������� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordCreateAutoPayment() && parseDelim())
		{
			ParseTransaction phoneTx = beginLexeme(PHONE);
			PhoneNumber phone = parsePhone(phoneTx);
			if (phone != null && parseDelim())
			{
				if (isClientPhone(phone))
				{
					BigDecimal limit = parseAmount();
					if (limit != null)
					{
						if (parseEOF())
						{
							// SUCCESS. ������� �������� �����, ������� � �����
							completeLexeme(tx);
							return commandFactory.createCreateAutoPaymentCommand(phone, null, null, limit, null);
						}

						// FAIL-E. ����� ������ ��� ���-�� ���
						addError(messageBuilder.buildAutoPayBadLimitMessage(phone));
						cancelLexeme(tx);
						return null;
					}

					// FAIL-D. �� ������ ��� ����������� ������ �����
					addError(messageBuilder.buildAutoPayBadLimitMessage(phone));
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. ������ �� ���� ����� ��������
				addError(messageBuilder.buildAutoPayIncorrectYourPhoneMessage(phone));
				cancelLexeme(tx);
				return null;
			}
			cancelLexeme(phoneTx);
			// FAIL-B. �� ������ ��� ����������� ������ �������
			addError(messageBuilder.buildAutoPayIncorrectPhoneMessage(parseString()));
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseCreateAutoPaymentCommandV4()
	{
		//���������� =  �������_���������� ����������� ������� ����������� ����� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordCreateAutoPayment() && parseDelim())
		{
			ParseTransaction phoneTx = beginLexeme(PHONE);
			PhoneNumber phone = parsePhone(phoneTx);
			if (phone != null && parseDelim())
			{
				if (isClientPhone(phone))
				{
					BigDecimal limit = parseAmount();
					if (limit != null && parseDelim())
					{
						String card = parseProduct();
						if (card != null)
						{
							if (parseEOF())
							{
								// SUCCESS. ������� �������� �����, �������, ����� � �����
								completeLexeme(tx);
								return commandFactory.createCreateAutoPaymentCommand(phone, null, null, limit, card);
							}

							// FAIL-F. ����� ����� ��� ���-�� ���
							addError(messageBuilder.buildAutoPayIncorrectCardMessage(phone));
							cancelLexeme(tx);
							return null;
						}

						// FAIL-E. ������ ������ ����� ���-�� ����������
						addError(messageBuilder.buildAutoPayIncorrectCardMessage(phone));
						cancelLexeme(tx);
						return null;
					}

					// FAIL-D. ����� ������ ��� ��������� ���
					addError(messageBuilder.buildAutoPayBadLimitMessage(phone));
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. ������ �� ���� ����� ��������
				addError(messageBuilder.buildAutoPayIncorrectYourPhoneMessage(phone));
				cancelLexeme(tx);
				return null;
			}
			cancelLexeme(phoneTx);
			// FAIL-B. �� ������ ��� ����������� ������ �������
			addError(messageBuilder.buildAutoPayIncorrectPhoneMessage(parseString()));
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordCreateAutoPayment()
	{
		return parseCommandKeyword(getKeywords());
	}

	private boolean isClientPhone(PhoneNumber phone)
	{
		ErmbProfile profile = getPerson().getErmbProfile();
		boolean isClientPhone = false;
		for (String string :profile.getPhoneNumbers())
		{
			PhoneNumber number = PhoneNumber.fromString(string);
			if (phone.equals(number))
				isClientPhone = true;
		}
		return isClientPhone;
	}
}
