package com.rssl.phizic.business.sms;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Balovtsev
 * @version 20.05.13 16:48
 */
public final class MessageResourceVariablesReader
{
	private static final String[] RESERVED_WORDS        = {"true", "false", "gt", "gte", "lt", "lte", "as", "in", "using"};
	private static final char[]   DELIMITERS            = {'>', '<', '=', '&', '?', '|', '!', '/', ' '};
	private static final char     EXPRESSIONS_DELIMITER = ';';

	private int     textMark      = -1;
	private int     directiveMark = -1;

	private boolean closingTag;
	private boolean directive;
	private boolean userDirective;

	/**
	 *
	 */
	public MessageResourceVariablesReader()
	{
		Arrays.sort(DELIMITERS);
	}

	/**
	 *
	 * @param  template ������
	 * @return Set&lt;String&gt;
	 * @throws ParseException
	 */
	public Set<String> readVariables(final String template) throws ParseException
	{
		Set<String> expressions  = new HashSet<String>();

		StringBuffer dRawCharSet = new StringBuffer();
		StringBuffer tRawCharSet = new StringBuffer();

		char[] chars = template.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (isDirective(chars, i))
			{
				handleDirective(dRawCharSet, chars, i);

				//���� �� ��������� ��������� ������ ������ �
				continue;
			}
			else
			{
				//���������, ��������� ��� ��������� ���������
				flushRawCharSet(dRawCharSet, expressions, true);
			}

			if (!isUserDirective() && !isClosingTag(chars, i))
			{
				//������ ������� ����� ������
				handleInnerText(tRawCharSet, chars, i);

				// ��� �� ��� ������, ����� ������ ������ ��� ��������. ����� �� ���-�� ������������ � �������� �����������
				if (i + 1 == chars.length)
				{
					flushRawCharSet(tRawCharSet, expressions, false);
				}
			}
			else
			{
				//������� ����� ���, ������ ����� ������������� �� ��� �������
				flushRawCharSet(tRawCharSet, expressions, false);
			}
		}

		return filter(expressions);
	}

	private Set<String> filter(Set<String> expressions) throws ParseException
	{
		Set<String> variables = new HashSet<String>();
		for (String expression : expressions)
		{
			if (MessageResourceVariablesReaderUtil.hasUnclosed(expression, "'"))
			{
				throw new ParseException("���������� ���������� ������� " + "'" + " � ������:\\n" + expression, 0);
			}

			if (MessageResourceVariablesReaderUtil.hasUnclosed(expression, "\""))
			{
				throw new ParseException("���������� ���������� ������� " + "\"" + " � ������:\\n" + expression, 0);
			}

			if (MessageResourceVariablesReaderUtil.hasUnclosed(expression, "[", "]") ||
				MessageResourceVariablesReaderUtil.hasUnclosed(expression, "{", "}") ||
				MessageResourceVariablesReaderUtil.hasUnclosed(expression, "(", ")"))
			{
				throw new ParseException("���������� ���������� ������ � ������:\\n" + expression, 0);
			}

			if (!expression.startsWith("\"") && !expression.startsWith("'"))
			{
				boolean startDollar = expression.startsWith("$");
				boolean startBraces = expression.startsWith("{");

				if (startBraces || startDollar)
				{
					// ��������� � ������� {} ����� ��������� ��������� ����������
					String[] values = expression.replaceAll("[${}]", "").split(Arrays.toString(DELIMITERS));

					for (String value : values)
					{
						value = value.trim();
						if (StringHelper.isNotEmpty(value))
						{
							if (startDollar && StringUtils.isNumeric(value))
							{
								continue;
							}

							variables.add(value.trim());
						}
					}
				}
				else
				{
					variables.add(expression);
				}
			}
		}

		return variables;
	}

	/*
	 * ����� ��� ��������� ��������� �.� �������� � ����� ������:
	 * <#if someExpression.mayBePresent && someMethod.mayBePresent(something)>
	 */
	private void handleDirective(StringBuffer buffer, char[] chars, int position)
	{
		/*
		 * ���������� ��� ��� ���������� � �������, ��� � ���������
		 */
		if (isQuoted(chars, position, '\''))
		{
			buffer.append(chars[position]);
			return;
		}

		/*
		 * ��� � � �������
		 */
		if (isQuoted(chars, position, '\"'))
		{
			buffer.append(chars[position]);
			return;
		}

		/*
		 * ���������� ��������� ������������� � ���, ��� ��� ������, �������� someMethod.mayBePresent(something).
		 */
		if (isMethod(chars, position))
		{
			buffer.append(chars[position]);
			return;
		}

		/*
		 * ���������� ������� ���������, �������� someExpression.mayBePresent
		 */
		if (isSimpleExpression(chars, position))
		{
			buffer.append(chars[position]);
			return;
		}

		/*
		 * ���� ����� �� ����� ����� ������ ��������� ��������� ��� ��� �� ��������� ��� ����������. ���� ��� ���-����
		 * ���� ��������� �� ������������� ������� � ������
         */
		fixVariable(buffer);
	}

	/*
	 * ����� ��� ������ ����, ��� ������������� ����� �����, ��������:
	 *
	 * <#if>I`m handle it</#if>
	 */
	private void handleInnerText(StringBuffer buffer, char[] chars, int position)
	{
		// ��������� ��������� ���� %s, %d ...
		if (isPercentMark(chars, position))
		{
			buffer.append(chars[position]);
		}

		// ��������� ��������� ���� {expression}
		if (isMark(chars, position, '{'))
		{
			buffer.append(chars[position]);
		}

		// ��������� ��������� ���� ${expression}
		if (isMark(chars, position, '$'))
		{
			buffer.append(chars[position]);
		}

        if (!isLastCharacterInnerTextVariable())
        {
            return;
        }

		/*
		 * ���� ����� �� ����� ����� ������ ��������� ��������� ��� ��� �� ��������� ��� ����������. ���� ��� ���-����
		 * ���� ��������� �� ������������� ������� � ������
         */
		fixVariable(buffer);
	}

	private void flushRawCharSet(StringBuffer rawCharacterSet, Set<String> expressions, boolean skipFirst)
	{
		if (rawCharacterSet.length() == 0)
		{
			return;
		}

		//������� ��� ���������� ��������� ���������
		int count = 0;

		//�������
		for (String value : rawCharacterSet.toString().split( String.valueOf(EXPRESSIONS_DELIMITER) ))
		{
			//���������� ������ ����� ���� �����. � ����� ��� ������ ��� ���������, ��������� ������ ����� ���������
			if (count++ == 0 && skipFirst)
			{
				continue;
			}

			if (StringUtils.isNumeric(value))
			{
				continue;
			}

			boolean exclude = false;

			//��������� ��������� ��������� �����
			for (String reserved : RESERVED_WORDS)
			{
				value = value.trim();
				if (reserved.equals(value))
				{
					exclude = true;
					break;
				}
			}

			if (exclude)
			{
				continue;
			}

			expressions.add(value);
		}

		/*
		 * ����� ���� ��� ��������� ���������:
		 * 1. ���������� ������� �����;
		 * 2. ������� �����.
		 */
		textMark      = -1;
		directiveMark = -1;

		rawCharacterSet.delete(0, rawCharacterSet.length());
	}

	/*
	 * ���������� �� ��� �������� � ��������, ����� ��� �������� ������� ���� ���������� ���������� �������.
	 * ����� ���� ��� ��� �������� � �������� �� ����������� � ������ ��������� ����������.
	 */
	private boolean isQuoted(char[] chars, int position, char quote)
	{
		char current = chars[position];

		//���� ������ ��� �� ���-�� ����������
		if (directiveMark > 0)
		{
			//�� ��������� �� ������� �� ���
			if (chars[directiveMark] == quote)
			{
				// ���� ��� ���...

				// �� ��������� ������� ������, ���� �� ���� �������� �������� - ���������� ������
				if (chars[position]  == quote)
				{
					directiveMark = -1;
				}

				// ���� ��� ���-�� �� ������������ - ���������� ������
				if (Arrays.binarySearch(DELIMITERS, current) > -1)
				{
					directiveMark = -1;
				}

				// ������� ������ ���, ���� ��������� ������������ ����������, �.� �������
				return true;
			}

			// ��������� ��� ���� ������ ��������� ����-�� � ������, �������
			return false;
		}

		//������ �� �� ��� �� ����������, ��������� �������� �� ������� ������ ��� ��� �� ����
		if (current == quote)
		{
			/*
			 * �������� ���� ��� ����� ������� ��������  ���-�� ����
			 */
			if (position - 1 < 0)
			{
				// �.� ����� �������� �����... �������� ������ �����-��. ������ ����� �� �������� ��������
				directiveMark = position;
				return true;
			}

			/*
			 * ���� �����-�� ����������� ����, �� ������� �� ��� �����
			 */
			if (Arrays.binarySearch(DELIMITERS, chars[position - 1]) > -1)
			{
				directiveMark = position;
				return true;
			}
		}

		return false;
	}

	/*
	 * ���������� ������� ��������� ���� expression.mayBePresent
	 */
	private boolean isSimpleExpression(char[] chars, int position)
	{
		char current = chars[position];

		// ������� �� �������� ����� �������� �� ��� ������ ���������
		if (directiveMark > -1)
		{
			//���� ����� ��������� �� ������ �� ��� ������� ����������
			if (Character.isLetter(chars[directiveMark]))
			{
				// ���� �����-�� �����������(�������� �� ������ ')' �����, ����� ��������� ���������� ��������������)...
				if (Arrays.binarySearch(DELIMITERS, current) > -1 || current == ')')
				{
					// ����������� ������
					directiveMark = -1;
					return false;
				}

				// ���������� ������
				return true;
			}
		}

		// ������ ������� ��������� ���������� � �����
		if (Character.isLetter(current))
		{
			for (int i = position; i < chars.length; i++)
			{
				// � ����� ��������� ��� ��������
				if (Character.isLetter(chars[i]) || chars[i] == '.' || chars[i] == '_')
				{
					continue;
				}

				// � �� ����� ��������� �����������
				if (Arrays.binarySearch(DELIMITERS, chars[i]) > -1)
				{
					directiveMark = position;
					return true;
				}
			}
		}

		return false;
	}

	private boolean isMethod(char[] chars, int position)
	{
		// ���� ��� ���������� �����, �� ����� ���������� ������ ��� �����������
		if (directiveMark > -1)
		{
			if (chars[directiveMark] == '(')
			{
				// ����� ������ - ������������� ������, �� �� ������...
				if (chars[position] == ')')
				{
					int bracesCount = 0;
					//�������� ������� ������
					for (int i=directiveMark; i<position; i++)
					{
						if (chars[i] == '(')
						{
							bracesCount += 1;
							continue;
						}

						if (chars[i] == ')')
						{
							bracesCount -= 1;
						}
					}

					// ���� �� �� �������, �� ��� ������� �� ����� ������
					directiveMark = bracesCount == 0 ? -1 : directiveMark;
				}

				// ����������� ������ ������ ��� ������ ������ ������ ����� ���� ��� ��� ������, ����� �������� �� DELIMITERS
				if (chars[position] == ' ')
				{
					return true;
				}

				// ����� ������ - �����������
				if (Arrays.binarySearch(DELIMITERS, chars[position]) > -1)
				{
					directiveMark = -1;
					return false;
				}

				return true;
			}

			return false;
		}

		// �������� ����� �� ���
		if (Character.isLetter(chars[position]))
		{
			for (int i = position; i < chars.length; i++)
			{
				// ������������ �� ������������� ������ ����� �������, ���� �� �� ��� �����
				if (chars[i] == '(')
				{
					directiveMark = i;
					return true;
				}

				// ���� ��������� ���-�� �� ������������ �� ������� ��� �� �����
				if (Arrays.binarySearch(DELIMITERS, chars[i]) > -1)
				{
					directiveMark = -1;
					return false;
				}
			}
		}

		return false;
	}

	private boolean isMark(char[] chars, int position, char markChar)
	{
		if (textMark == -1)
		{
			//�������� ������ ���������
			if (chars[position] == markChar)
			{
				textMark = position;
				return true;
			}
		}
		else
		{
			if (chars[textMark] == markChar)
			{
				//��������� ������ ��� ���������� �������� �.� ��������� ������� ��� ��� -> {expression}
				if (chars[position] == '}')
				{
					textMark = -1;
				}

				/*
				 * ���� ��������� ��� ����������� ������ ���� ������, �� ���������� ������ ���� �� ������
				 * ���������� ���������, ���� �� ������ ���������
				 */
				if (textMark > -1)
				{
					// ��������� �������� � ������� ��������, � ������ ��� ����� ��� :(
					if (textMark + 1 == chars.length)
					{
						textMark = -1;
						return false;
					}

					// ������ ������ ����
					if (position + 1 == chars.length)
					{
						textMark = -1;
						return false;
					}

					// ��� ������ ������ ��������� ���������
					char nextChar = chars[position + 1];
					if (nextChar == '$' || nextChar == '{')
					{
						textMark = -1;
						return false;
					}
				}

				return true;
			}
		}

		return false;
	}

	private boolean isPercentMark(char[] chars, int position)
	{
		if (textMark == -1)
		{
			if (chars[position] == '%')
			{
				//���������� ������� � ������� ������� ������ ���������, ���� ��� ������ ���������
				int nextCharIndex = position + 1;
				if (nextCharIndex < chars.length && Character.isLetter(chars[nextCharIndex]))
				{
					textMark = position;
				}

				return true;
			}
		}
		else
		{
			if (chars[textMark] == '%')
			{
				if (Character.isLetter(chars[position]))
				{
					return true;
				}

				textMark = -1;
			}
		}

		return false;
	}

	private void fixVariable(StringBuffer characterSet)
	{
		if (characterSet.length() > 0)
		{
			if (characterSet.charAt(characterSet.length() - 1) == EXPRESSIONS_DELIMITER)
			{
				return;
			}

			characterSet.append(EXPRESSIONS_DELIMITER);
		}
	}

	private boolean isClosingTag(char[] chars, int position)
	{
		if (chars[position] == '<')
		{
			for (int i= position+1; i<chars.length; i++)
			{
				if (chars[i] == ' ')
				{
					continue;
				}

				closingTag = (chars[i] == '/');
				break;
			}
		}

		if (chars[position] == '>')
		{
			if (closingTag)
			{
				closingTag = !closingTag;
			}
		}

		return closingTag;
	}

	private boolean isDirective(char[] chars, int position)
	{
		if (chars[position] == '<')
		{
			for (int i= position+1; i<chars.length; i++)
			{
				if (chars[i] == ' ')
				{
					continue;
				}

				directive     = (chars[i] == '#');
				userDirective = (chars[i] == '@');
				break;
			}
		}

		if (chars[position] == '>')
		{
			if (directive)
			{
				directive = !directive;
			}

			if (userDirective)
			{
				userDirective = !userDirective;
			}
		}

		return directive;
	}

	private boolean isUserDirective()
	{
		return userDirective;
	}

    public boolean isLastCharacterInnerTextVariable()
    {
        return textMark == -1;
    }
}