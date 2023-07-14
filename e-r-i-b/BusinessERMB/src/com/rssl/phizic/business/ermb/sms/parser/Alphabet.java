package com.rssl.phizic.business.ermb.sms.parser;

import java.text.CharacterIterator;

/**
 * @author Erkin
 * @ created 01.04.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Алфавит СМС-команды
 *
 * ВАЖНО! При расширении синтаксиса, новые символы должны быть добавлены в Алфавит.
 */
class Alphabet
{
	@SuppressWarnings("OverlyLongMethod")
	static CharType getCharType(char c)
	{
		switch (c)
		{
			// Буква строчная
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

			// Буква прописная
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

			// Буква строчная русская
			case 'а':
			case 'б':
			case 'в':
			case 'г':
			case 'д':
			case 'е':
			case 'ё':
			case 'ж':
			case 'з':
			case 'и':
			case 'й':
			case 'к':
			case 'л':
			case 'м':
			case 'н':
			case 'о':
			case 'п':
			case 'р':
			case 'с':
			case 'т':
			case 'у':
			case 'ф':
			case 'х':
			case 'ц':
			case 'ч':
			case 'ш':
			case 'щ':
			case 'ь':
			case 'ы':
			case 'ъ':
			case 'э':
			case 'ю':
			case 'я':

			// Буква прописная русская
			case 'А':
			case 'Б':
			case 'В':
			case 'Г':
			case 'Д':
			case 'Е':
			case 'Ё':
			case 'Ж':
			case 'З':
			case 'И':
			case 'Й':
			case 'К':
			case 'Л':
			case 'М':
			case 'Н':
			case 'О':
			case 'П':
			case 'Р':
			case 'С':
			case 'Т':
			case 'У':
			case 'Ф':
			case 'Х':
			case 'Ц':
			case 'Ч':
			case 'Ш':
			case 'Щ':
			case 'Ь':
			case 'Ы':
			case 'Ъ':
			case 'Э':
			case 'Ю':
			case 'Я':
				return CharType.LETTER;

			// Цифра
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
			case '№':
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
