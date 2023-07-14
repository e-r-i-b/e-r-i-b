package com.rssl.phizic.web.news;

import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumberUtil;

import java.util.Calendar;

/**
 * Enum тегов
 * @author komarov
 * @ created 13.03.2012
 * @ $Author$
 * @ $Revision$
 */

public enum Tags
{
	BR_TAG("(\r\n|\n\r|\n|\r)", "<br>", ""),
    COLOR_TAG("\\[color=['\"]?(.*?[^'\"])['\"]?\\](.*?)\\[/color\\]", "<span style='color:$1'>$2</span>", "$2"),
    SIZE_TAG("\\[size=['\"]?([0-9]|[1-2][0-9])['\"]?\\](.*?)\\[/size\\]", "<span style='font-size:$1px'>$2</span>", "$2"),
	B_TAG("\\[b\\](.*?)\\[/b\\]","<b>$1</b>", "$1"),
    U_TAG("\\[u\\](.*?)\\[/u\\]", "<u>$1</u>", "$1"),
    I_TAG("\\[i\\](.*?)\\[/i\\]", "<i>$1</i>" , "$1"),
    IMG_TAG("\\[img\\](.*?)\\[/img\\]", "<img src='$1' border='0' alt='0'>", ""),
    URL_BLANK_TAG("\\[url\\](.*?)\\[/url\\]", "<a href='$1' target='_blank'>$1</a>", "$1"),
    FAQ_TAG( "\\[faq=(.*?)\\](.*?)\\[/faq\\]", "<a href='#' onclick='openFAQ(\"$1\"); return false;'>$2</a>", "$2"),
    URL_NEW_TAG( "\\[url=['\"]?(.*?[^'\"])['\"]?\\](.*?)\\[/url\\]", "<a href='$1' target='_new'>$2</a>", "$2"),
    EMAIL_TAG("\\[email\\](.*?)\\[/email\\]", "<a href='mailto:$1'>$1</a>", "$1"),
	LEFT_TAG("\\[left\\](.*?)\\[/left\\]", "<div align='left'>$1</div>", "$1"),
	CENTER_TAG("\\[center\\](.*?)\\[/center\\]", "<div align='center'>$1</div>", "$1"),
	RIGHT_TAG("\\[right\\](.*?)\\[/right\\]", "<div align='right'>$1</div>", "$1"),
	JUSTIFY_TAG("\\[justify\\](.*?)\\[/justify\\]", "<div align='justify'>$1</div>", "$1"),
	PERCENT_TAG("\\[percent name=['\"]?([^'\"\\]]*)['\"]?/\\]","<span class='percentClass $1'></span>" +
			"<script type='text/javascript'>" +
			"eventManager.subscribe('updatePercent', function(params){\\$('.$1').text(params.$1value+'%');})" +
			"</script>", ""),
	//Теги которые нужно обрабатывать отдельно т.к подставляемые данные зависят от контекста.
	FIO_TAG("\\[fio/\\]", "", "")
	{
		public String getSubstitutionWithTag()
		{
			PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
			if (dataProvider != null)
			{
				PersonData data = dataProvider.getPersonData();
				if (data != null)
				{
					return data.getPerson().getFirstPatrName();
				}
			}
			return "<i>Имя Отчество</i>";
		}
	},
	PHONE_TAG("\\[phone/\\]", "", "")
	{
		public String getSubstitutionWithTag()
		{
			try
			{
				PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
				if (dataProvider != null)
				{
					PersonData data = dataProvider.getPersonData();
					if (data != null)
					{
						return PhoneNumberUtil.getCutPhoneNumber(MobileBankManager.getMainPhoneForCurrentUser());
					}
				}
				return "<i>+7 (XXX) *** XXXX</i>";
			}
			catch (Exception ignore)
			{
				return "";
			}
		}
	},
	BIRHTDAY_TAG("\\[birhtDay/\\]", "", "")
	{
		public String getSubstitutionWithTag()
		{
			PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
			if (dataProvider != null)
			{
				PersonData data = dataProvider.getPersonData();
				if (data != null)
				{
					Calendar birhtDay = data.getPerson().getBirthDay();
					return birhtDay == null ? "<i>дата рождения</i>" : DateHelper.formatDateWithStringMonth(birhtDay);
				}
			}
			return "<i>дата рождения</i>";
		}
	};

	private String regExp;
	private String substitutionWithTag;
	private String substitutionWhithoutTag;

	/**
	 * @param regExp Регулярное выражение соответствующее тегу
	 * @param substitutionWithTag подстановка для получения HTML
	 * @param substitutionWhithoutTag подставновка для получения текста
	 */
	Tags(String regExp, String substitutionWithTag, String substitutionWhithoutTag)
	{
		this.regExp = regExp;
		this.substitutionWithTag = substitutionWithTag;
		this.substitutionWhithoutTag = substitutionWhithoutTag;

	}

	public String getRegExp()
	{
		return regExp;
	}

	public String getSubstitutionWithTag()
	{
		return substitutionWithTag;
	}

	public String getSubstitutionWhithoutTag()
	{
		return substitutionWhithoutTag;
	}

}
