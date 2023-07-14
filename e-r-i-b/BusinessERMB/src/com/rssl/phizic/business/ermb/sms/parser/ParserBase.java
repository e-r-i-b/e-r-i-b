package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.bankroll.BankrollEngine;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.ermb.sms.command.CommandFactory;
import com.rssl.phizic.business.ermb.sms.messaging.MessageBuilder;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.annotation.NonThreadSafe;
import com.rssl.phizic.common.types.annotation.Statefull;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;

import java.math.BigDecimal;
import java.util.Collection;

import static com.rssl.phizic.business.ermb.sms.parser.Lexeme.*;
import static java.lang.Character.toUpperCase;

/**
 * @author Erkin
 * @ created 21.11.2012
 * @ $Author$
 * @ $Revision$
 */

@Statefull
@NonThreadSafe
abstract class ParserBase
{
	private static final Dictionary PHONE_PREFIXES = new Dictionary("8", "+7", "7");

	@SuppressWarnings("ProtectedField")
	protected final Log log = PhizICLogFactory.getLog(LogModule.Core);

	@SuppressWarnings("ProtectedField")
	protected final CommandFactory commandFactory = new CommandFactory();

	@SuppressWarnings("ProtectedField")
	protected final MessageBuilder messageBuilder = new MessageBuilder();

	private Module module;

	private Person person;

	private String phone;

	private Scanner scanner;

	private static final int autoSmsAliasLengthDefaultValue = 4;

	private PersonBankrollManager personBankrollManager;

	/**
	 * �������� ����� ���-�������
	 * (�� ��������������)
	 */
	private String text;

	///////////////////////////////////////////////////////////////////////////

	protected Module getModule()
	{
		return module;
	}

	public void setModule(Module module)
	{
		this.module = module;
	}

	protected Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
	}

	protected String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	protected Scanner getScanner()
	{
		return scanner;
	}

	public void setScanner(Scanner scanner)
	{
		this.scanner = scanner;
	}

	protected String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	///////////////////////////////////////////////////////////////////////////
	// ������ ������: ������ �������, ��������� �������, ��������� ������� 

	protected ParseTransaction beginLexeme(Lexeme lexeme)
	{
		return new ParseTransaction(lexeme, scanner.getText(), scanner.getPosition());
	}

	protected void completeLexeme(ParseTransaction tx)
	{
		tx.setEnd(scanner.getPosition());
	}

	protected void cancelLexeme(ParseTransaction tx)
	{
		scanner.setPosition(tx.getBegin());
	}

	///////////////////////////////////////////////////////////////////////////
	// ������� �������������� �����: �����������, ����� � �.�.

	protected boolean parseCommandKeyword(Dictionary keywords)
	{
		ParseTransaction tx = beginLexeme(COMMAND_KEYWORD);

		String keyword = parseAnySample(keywords);

		if (keyword != null) {
			completeLexeme(tx);
			return true;
		}

		cancelLexeme(tx);
		return false;
	}

	protected boolean parseDelim()
	{
		// ����������� = ������ | ����� | ����� | �������

		ParseTransaction tx = beginLexeme(DELIMITER);

		char c = scanner.getCurrentChar();
		switch (Alphabet.getCharType(c))
		{
			case WHITESPACE:
			case HYPHEN:
			case DOT:
			case SHARP:
				scanner.gotoNextChar();
				completeLexeme(tx);
				return true;

			default:
				cancelLexeme(tx);
				return false;
		}
	}

	protected String parseProduct()
	{
		// ������� = 4*7(�����) | �����

		ParseTransaction tx = beginLexeme(PRODUCT);

		String number = parseNumber(4, 7);
		if (number != null)
		{
			completeLexeme(tx);
			return number;
		}

		String name = parseAlias();
		if (name != null)
		{
			completeLexeme(tx);
			return name;
		}

		cancelLexeme(tx);
		return null;
	}

	@SuppressWarnings("MagicNumber")
	protected String parseOtherCard()
	{
		// �����_����� = 16*16(�����) | 18*18(�����)

		ParseTransaction tx = beginLexeme(OTHER_CARD);

		String number = parseNumber(16, 18);
		if (number != null)
		{
			if (number.length() == 16 || number.length() == 18)
			{
				completeLexeme(tx);
				return number;
			}
		}

		cancelLexeme(tx);
		return null;
	}

	protected PhoneNumber parsePhone()
	{
		return parsePhone(beginLexeme(PHONE));
	}
	protected PhoneNumber parsePhone(ParseTransaction tx)
	{
		// ������� = 10*10(�����) | �8�10*10(�����) | �+7�10*10(�����) | �7�10*10(�����)

		String prefix = parseAnySample(PHONE_PREFIXES);
		if (prefix != null)
		{
			String number = parseNumber(10, 10);
			if (number != null) {
				completeLexeme(tx);
				return PhoneNumberFormat.SIMPLE_NUMBER.parse(number);
			}
		}

		String number = parseNumber(10, 10);
		if (number != null) {
			completeLexeme(tx);
			return PhoneNumberFormat.SIMPLE_NUMBER.parse(number);
		}

		cancelLexeme(tx);
		return null;
	}

	protected BigDecimal parseAmount()
	{
		// ����� = (1*(�����))

		return parseAmount(Integer.MAX_VALUE);
	}

	protected BigDecimal parseAmount(int maxLength)
	{
		// ����� = (1*(�����))

		ParseTransaction tx = beginLexeme(AMOUNT);
		String number = null;
		int length = 0;
		while (length < maxLength)
		{
			char c = scanner.getCurrentChar();
			//���� ��������� . ��� , �� ������� ��� �������.
			if (Alphabet.isComma(c) || Alphabet.isDot(c))
			{
				cancelLexeme(tx);
				return null;
			}
			else if (!Alphabet.isDigit(c))
				break;

			scanner.gotoNextChar();
			length++;
		}

		if (length >= 1)
		{
			completeLexeme(tx);
			number = tx.getLexemeValue();
		}

		if (number != null) {
			return new BigDecimal(number);
		}

		cancelLexeme(tx);
		return null;
	}

	protected String parseTariff()
	{
		// ��������_������	= 1*(�����|�����|����������|�����������)

		ParseTransaction tx = beginLexeme(TARIFF);

		while (true)
		{
			char c = scanner.getCurrentChar();
			switch (Alphabet.getCharType(c))
			{
				default:
					scanner.gotoNextChar();
					break;

				case EOF:
					completeLexeme(tx);
					return tx.getLexemeValue();
			}
		}
	}

	protected String parseAlias()
	{
		// ����� = (����� | �������������) (*(����� | ����� | �������������))

		ParseTransaction tx = beginLexeme(ALIAS);

		char c = scanner.getCurrentChar();
		switch (Alphabet.getCharType(c))
		{
			case LETTER:
			case UNDERSCORE:
				scanner.gotoNextChar();

				while (true)
				{
					c = scanner.getCurrentChar();
					switch (Alphabet.getCharType(c))
					{
						case LETTER:
						case DIGIT:
						case UNDERSCORE:
							scanner.gotoNextChar();
							break;

						default:
							completeLexeme(tx);
							return tx.getLexemeValue();
					}
				}
		}

		cancelLexeme(tx);
		return null;
	}

	protected String parseNumber(int minLength, int maxLength)
	{
		ParseTransaction tx = beginLexeme(NUMBER);

		int length = 0;
		while (length < maxLength)
		{
			char c = scanner.getCurrentChar();
			if (!Alphabet.isDigit(c))
				break;

			scanner.gotoNextChar();
			length++;
		}

		if (length >= minLength)
		{
			completeLexeme(tx);
			return tx.getLexemeValue();
		}

		cancelLexeme(tx);
		return null;
	}

	@SuppressWarnings("OverlyLongMethod")
	protected String parseString()
	{
		// ������ = ������	= (1*(����� | ����� | ����_������))
		//        | <�> *(����� | ����� | ����_������ | ����������� | <�>) <�>
		//        | <�> *(����� | ����� | ����_������ | ����������� | <�>) <�>

		ParseTransaction tx = beginLexeme(STRING);

		char c = scanner.getCurrentChar();
		switch (Alphabet.getCharType(c))
		{
			// A. ������ ���������� � ������� �������
			case DOUBLE_QUOTE:
				scanner.gotoNextChar();

				while (true)
				{
					c = scanner.getCurrentChar();
					switch (Alphabet.getCharType(c))
					{
						// A.A C����� ������ => ��� ������
						default:
							scanner.gotoNextChar();
							break;

						// A.B ����������� ������� ������� => ������ ������� ���������
						case DOUBLE_QUOTE:
							scanner.gotoNextChar();
							completeLexeme(tx);
							String string = tx.getLexemeValue();
							return string.substring(1, string.length()-1);

						// A.C ����� ������ => ��������� ������ �� �������
						case EOF:
							cancelLexeme(tx);
							return null;
					}
				}

			// B. ������ ���������� � ��������� �������
			case SINGLE_QUOTE:
				scanner.gotoNextChar();

				while (true)
				{
					c = scanner.getCurrentChar();
					switch (Alphabet.getCharType(c))
					{
						// B.A C����� ������ => ��� ������
						default:
							scanner.gotoNextChar();
							break;

						// B.B ����������� ��������� ������� => ������ ������� ���������
						case SINGLE_QUOTE:
							scanner.gotoNextChar();
							completeLexeme(tx);
							String string = tx.getLexemeValue();
							return string.substring(1, string.length()-1);

						// B.C ����� ������ => ��������� ������ �� �������
						case EOF:
							cancelLexeme(tx);
							return null;
					}
				}

            // C. ������ ���������� � ������������� �������
			case LETTER:
			case DIGIT:
			case UNDERSCORE:
			case SPECIAL:
				scanner.gotoNextChar();

				while (true)
				{
					c = scanner.getCurrentChar();
					switch (Alphabet.getCharType(c))
					{
						// C.A C����� ������ => ��� ������
						case LETTER:
						case DIGIT:
						case UNDERSCORE:
						case SPECIAL:
							scanner.gotoNextChar();
							break;

						// C.B ������ ������ => ������ ������� ���������
						default:
							completeLexeme(tx);
							return tx.getLexemeValue();
					}
				}

			// D. ������ �� ���������
			default:
				cancelLexeme(tx);
				return null;
		}
	}

	protected boolean parseEOF()
	{
		ParseTransaction tx = beginLexeme(EOF);

		char c = scanner.getCurrentChar();
		if (Alphabet.isEOF(c))
		{
			completeLexeme(tx);
			return true;
		}

		cancelLexeme(tx);
		return false;
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ������� ������� �� ������� "�������� ��������"
	 * @param dictionary - ������� �������� 
	 * @return ��������� ������� ��� null, ���� �� ���� ������� �� ��������
	 */
	private String parseAnySample(Dictionary dictionary)
	{
		for (String word : dictionary.getWords())
		{
			if (parseSample(word))
				return word;
		}
		return null;
	}

	/**
	 * ������� ������� �� ������� "�������"
	 * @param sample - �������
	 * @return true, ���� ������� ��������
	 */
	protected boolean parseSample(String sample)
	{
		int begin = scanner.getPosition();
		for (int i=0; i<sample.length(); i++)
		{
			char input = toUpperCase(scanner.getCurrentChar());
			char stand = toUpperCase(sample.charAt(i));
			if (input != stand)
			{
				scanner.setPosition(begin);
				return false;
			}

			scanner.gotoNextChar();
		}
		return true;
	}

	protected int getCardSmsAutoAliasLength()
	{
		Collection<CardLink> cardLinks = (Collection<CardLink>) getPersonBankrollManager().getCards();
		if (!cardLinks.isEmpty())
		{
			CardLink cardLink = cardLinks.iterator().next();
			return cardLink.getAutoSmsAlias().length();
		}
		return autoSmsAliasLengthDefaultValue;
	}

	private PersonBankrollManager getPersonBankrollManager()
	{
		if (personBankrollManager == null)
		{
			BankrollEngine bankrollEngine = getModule().getBankrollEngine();
			if (bankrollEngine == null)
				throw new UnsupportedOperationException("BankrollEngine �� �������� � ������ " + getModule().getName());
			personBankrollManager = bankrollEngine.createPersonBankrollManager(getPerson());
		}
		return personBankrollManager;
	}
}
