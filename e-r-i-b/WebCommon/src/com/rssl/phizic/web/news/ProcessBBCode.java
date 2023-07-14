package com.rssl.phizic.web.news;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessBBCode implements Serializable
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String CR_LF = "(?:\r\n|\r|\n)?";

	/**
	 * Преобразует строку с BB кодами в html. К строке применяется html escaping.
	 * @param string строка с кодами
	 * @return html
	 */
	public static String preparePostText(String string)
	{
		return preparePostText(string, true);
	}

	/**
	 * Преобразует строку с BB кодами в html
	 * @param string строка с кодами
	 * @param needEscapeHtml признак применения html escaping-а к строке
	 * @return html
	 */
	public static String preparePostText(String string, boolean needEscapeHtml)
	{
		try
		{
		StringBuffer buffer = new StringBuffer(string);

		processNestedTags(buffer,
				"list",
				"<ol type=\"{BBCODE_PARAM}\">",
				"</ol>",
				"<ul>",
				"</ul>",
				"<li>",
				true,
				true,
				true);


		String str = buffer.toString();
		if (needEscapeHtml)
			str = StringEscapeUtils.escapeHtml(str);

		for(Tags tag : Tags.values())
		{
			str = replaceRegExp(str, tag.getRegExp(), tag.getSubstitutionWithTag());
		}
		return str;
		}
		catch (Exception e)
		{
			log.error("Ошибка получения html в функции processBBCode", e);
			return "";
		}
	}

	/**
	 * имеет небольшие отличая от preparePostText. не может обрабатывать некоторые теги
	 * @param string строка с кодами
	 * @return html
	 */
	public static String preparePostTextForCSA(String string)
	{
		try
		{
		StringBuffer buffer = new StringBuffer(string);

		processNestedTags(buffer,
				"list",
				"<ol type=\"{BBCODE_PARAM}\">",
				"</ol>",
				"<ul>",
				"</ul>",
				"<li>",
				true,
				true,
				true);


		String str = buffer.toString();
		str = StringEscapeUtils.escapeHtml(str);

		for(Tags tag : Tags.values())
		{
			if (tag == Tags.FIO_TAG || tag == Tags.PHONE_TAG || tag == Tags.BIRHTDAY_TAG)
				continue;
			str = replaceRegExp(str, tag.getRegExp(), tag.getSubstitutionWithTag());
		}
		return str;
		}
		catch (Exception e)
		{
			log.error("Ошибка получения html в функции processBBCode", e);
			return "";
		}
	}

	/**
	 * заменяет все вхождения регулярного выражения regexp на target в строке str
	 * цикл используется для корректной обработки вложенных тегов
	 * @param str - строка, в которой происходит поиск
	 * @param regexp - регулярное выражение
	 * @param target - строка, на которую заменяем регулярное выражение
	 */
	public static String replaceRegExp(String str, String regexp, String target)
	{
		String tmp = str;
		Pattern patt = Pattern.compile(regexp);
		Matcher match = patt.matcher(tmp);
        while (match.find())
        {
		  tmp = tmp.replaceAll(regexp, target);
	      match = patt.matcher(tmp);
        }
	    return tmp;
	}

	private static void processNestedTags(
			StringBuffer buffer,
			String tagName,
			String openSubstWithParam,
			String closeSubstWithParam,
			String openSubstWithoutParam,
			String closeSubstWithoutParam,
			String internalSubst,
			boolean processInternalTags,
			boolean acceptParam,
			boolean requiresQuotedParam)
	{
		String str = buffer.toString();

		Stack<MutableCharSequence> openStack = new Stack<MutableCharSequence>();
		Set<MutableCharSequence> subsOpen = new HashSet<MutableCharSequence>();
		Set<MutableCharSequence> subsClose = new HashSet<MutableCharSequence>();
		Set<MutableCharSequence> subsInternal = new HashSet<MutableCharSequence>();

		String openTag = CR_LF + "\\["
				+ tagName
				+ (acceptParam ? (requiresQuotedParam ? "(?:=\"(.*?)\")?" : "(?:=\"?(.*?)\"?)?") : "")
				+ "\\]"
				+ CR_LF;
		String closeTag = CR_LF + "\\[/" + tagName + "\\]" + CR_LF;

		String patternString = "(" + openTag + ")|(" + closeTag + ")";

		if (processInternalTags)
		{
			String internTag = CR_LF + "\\[\\*\\]" + CR_LF;
			patternString += "|(" + internTag + ")";
		}

		Pattern tagsPattern = Pattern.compile(patternString);
		Matcher matcher = tagsPattern.matcher(str);

		int openTagGroup;
		int paramGroup;
		int closeTagGroup;
		int internalTagGroup;

		if (acceptParam)
		{
			openTagGroup = 1;
			paramGroup = 2;
			closeTagGroup = 3;
			internalTagGroup = 4;
		}
		else
		{
			openTagGroup = 1;
			paramGroup = -1; // INFO
			closeTagGroup = 2;
			internalTagGroup = 3;
		}

		while (matcher.find())
		{
			int length = matcher.end() - matcher.start();
			MutableCharSequence matchedSeq = new MutableCharSequence(str, matcher.start(), length);

			// test opening tags
			if (matcher.group(openTagGroup) != null)
			{
				if (acceptParam && (matcher.group(paramGroup) != null))
				{
					matchedSeq.param = matcher.group(paramGroup);
				}

				openStack.push(matchedSeq);

				// test closing tags
			}
			else if ((matcher.group(closeTagGroup) != null) && !openStack.isEmpty())
			{
				MutableCharSequence openSeq = openStack.pop();

				if (acceptParam)
				{
					matchedSeq.param = openSeq.param;
				}

				subsOpen.add(openSeq);
				subsClose.add(matchedSeq);

				// test internal tags
			}
			else if (processInternalTags && (matcher.group(internalTagGroup) != null)
					&& (!openStack.isEmpty()))
			{
				subsInternal.add(matchedSeq);
			}
			else
			{
				// assert (false);
			}
		}

		//noinspection CollectionDeclaredAsConcreteClass
		LinkedList<MutableCharSequence> subst = new LinkedList<MutableCharSequence>();
		subst.addAll(subsOpen);
		subst.addAll(subsClose);
		subst.addAll(subsInternal);

		Collections.sort(subst, new Comparator<MutableCharSequence>()
		{
			public int compare(MutableCharSequence s1, MutableCharSequence s2)
			{
				return -(s1.start - s2.start);
			}
		});

		buffer.delete(0, buffer.length());

		int start = 0;

		while (!subst.isEmpty())
		{
			MutableCharSequence seq = subst.removeLast();
			buffer.append(str.substring(start, seq.start));

			if (subsClose.contains(seq))
			{
				if (seq.param != null)
				{
					buffer.append(closeSubstWithParam);
				}
				else
				{
					buffer.append(closeSubstWithoutParam);
				}
			}
			else if (subsInternal.contains(seq))
			{
				buffer.append(internalSubst);
			}
			else if (subsOpen.contains(seq))
			{
				Matcher m = Pattern.compile(openTag).matcher(str.substring(seq.start,
						seq.start + seq.length));

				if (m.matches())
				{
					if (acceptParam && (seq.param != null))
					{
						buffer.append( //
								openSubstWithParam.replaceAll("\\{BBCODE_PARAM\\}", seq.param));
					}
					else
					{
						buffer.append(openSubstWithoutParam);
					}
				}
			}

			start = seq.start + seq.length;
		}

		buffer.append(str.substring(start));
	}

	private static class MutableCharSequence implements CharSequence
	{
		private CharSequence base;
		private int start;
		private int length;
		private String param = null;

		MutableCharSequence()
		{
		}

		MutableCharSequence(CharSequence base, int start, int length)
		{
			reset(base, start, length);
		}

		/** @see java.lang.CharSequence#length() */
		public int length()
		{
			return this.length;
		}

		/** @see java.lang.CharSequence#charAt(int) */
		public char charAt(int index)
		{
			return this.base.charAt(this.start + index);
		}

		/** @see java.lang.CharSequence#subSequence(int,int) */
		public CharSequence subSequence(int pStart, int end)
		{
			return new MutableCharSequence(this.base,
					this.start + pStart,
					this.start + (end - pStart));
		}

		public CharSequence reset(CharSequence pBase, int pStart, int pLength)
		{
			this.base = pBase;
			this.start = pStart;
			this.length = pLength;

			return this;
		}

		/** @see java.lang.Object#toString() */
		public String toString()
		{
			StringBuffer sb = new StringBuffer();

			for (int i = this.start; i < (this.start + this.length); i++)
			{
				sb.append(this.base.charAt(i));
			}

			return sb.toString();
		}
	}
}
