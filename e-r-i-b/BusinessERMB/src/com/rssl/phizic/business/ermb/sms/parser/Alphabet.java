package com.rssl.phizic.business.ermb.sms.parser;

import java.text.CharacterIterator;

/**
 * @author Erkin
 * @ created 01.04.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ���-�������
 *
 * �����! ��� ���������� ����������, ����� ������� ������ ���� ��������� � �������.
 */
class Alphabet
{
	@SuppressWarnings("OverlyLongMethod")
	static CharType getCharType(char c)
	{
		switch (c)
		{
			// ����� ��������
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':

			// ����� ���������
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':

			// ����� �������� �������
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':

			// ����� ��������� �������
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
				return CharType.LETTER;

			// �����
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				return CharType.DIGIT;

			case '_':
				return CharType.UNDERSCORE;

			case '"':
				return CharType.DOUBLE_QUOTE;

			case '\'':
				return CharType.SINGLE_QUOTE;

			case '-':
				return CharType.HYPHEN;

			case '.':
				return CharType.DOT;

			case '#':
				return CharType.SHARP;

			case ',':
			case '?':
			case '!':
			case ':':
			case ';':
			case '(': case ')':
			case '[': case ']':
			case '{': case '}':
			case '~':
			case '@':
			case '^':
			case '&':
			case '|':
			case '\\':
			case '$':
			case '�':
			case '+':
			case '*':
			case '/':
			case '%':
			case '=':
			case '<': case '>':
				return CharType.SPECIAL;

			case ' ':
			case '\t':
			case '\n':
				return CharType.WHITESPACE;

			case CharacterIterator.DONE:
				return CharType.EOF;

			default:
				return CharType.UNKNOWN;
		}
	}

	static boolean isDot(char c)
	{
		return getCharType(c) == CharType.DOT;
	}

	static boolean isComma(char c)
	{
		return getCharType(c) == CharType.COMMA;
	}


	static boolean isDigit(char c)
	{
		return getCharType(c) == CharType.DIGIT;
	}

	static boolean isEOF(char c)
	{
		return c == CharacterIterator.DONE;
	}

	static boolean isWhiteSpace(char c)
	{
		return getCharType(c) == CharType.WHITESPACE;
	}

	static boolean isUnknownChar(char c)
	{
		return getCharType(c) == CharType.UNKNOWN;
	}
}
