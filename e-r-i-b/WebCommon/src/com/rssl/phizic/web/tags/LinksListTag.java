package com.rssl.phizic.web.tags;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author Rydvanskiy
 * @ created 28.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class LinksListTag extends BodyTagSupport
{
	private static final int FIRST_STEP_SHOW = 0;
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	//Количество после которого ссылки скрываются
	private Integer num = null;
	//Имя класса для списка
	private String styleClass;
	//Элементы списка
	protected ArrayList items;
	//Заголовок списка
	private String title;
	//Стиль заголовка списка
	private String titleStyleClass;
	//Имя ресурса для которого будет строиться список
	private String listSourceName = null;
	//Дополнительная картинка для списка
	private String image = null;

	public String getStyleClass()
	{
		return styleClass;
	}

	public void setStyleClass(String styleClass)
	{
		this.styleClass = styleClass;
	}


	public void setNum(Integer num)
	{
		this.num = num;
	}

	public Integer getNum()
	{
		return num;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTitleStyleClass()
	{
		return titleStyleClass;
	}

	public void setTitleStyleClass(String titleStyleClass)
	{
		this.titleStyleClass = titleStyleClass;
	}

	public String getListSourceName()
	{
		return listSourceName;
	}

	public void setListSourceName(String listSourceName)
	{
		this.listSourceName = listSourceName;
	}

	public void addItem (StringBuffer item)
	{
		items.add(item);
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public int doStartTag () throws JspException
	{
		items = new ArrayList();
		return super.doStartTag();
	}

	public int doEndTag () throws JspException
	{
		String additionalAttributes = StringHelper.isNotEmpty(listSourceName) ? " name=\""+listSourceName+"\" " : "";
		String displayNone = "display: none;";
		StringBuffer buff = new StringBuffer();
		int i=0;
		boolean haveHiden = false;

		buff.append("<ul class='");
		if (!StringHelper.isEmpty(styleClass))
			buff.append( styleClass + " " );
		buff.append("linksList'>");

		//Определение шага раскрытия списка
		int stepShowNum = 0;
		String stepShow = null;
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData != null)
			{
				ActivePerson person = personData.getPerson();
				Map<String, String> stepShowMap = person.getStepShowPersonalMenuLinkListMap();
				if (!MapUtils.isEmpty(stepShowMap))
					stepShow = person.getStepShowPersonalMenuLinkListMap().get(listSourceName);
				stepShowNum = !StringHelper.isEmpty(stepShow) ? Integer.valueOf(stepShow) : 0;
			}
		}
		catch (NumberFormatException e)
		{
			log.info("Не верный формат данных при фиксировании шага раскрытия списка :"+stepShow);
		}

		//Признак необходимости добавить заголовок списка
		boolean needAddTitle = (!StringHelper.isEmpty(listSourceName) && !StringHelper.isEmpty(title));

		//Добавление заголовка списка, если необходимо
		if (needAddTitle)
		{
			String styleWithoutImage = items.size() == 0 ? " style= 'background-image: none;'" : "";
			String hideTitleStyle = stepShowNum > 0 ? " hide" : "";
			String titleStyle = (!StringHelper.isEmpty(titleStyleClass) ? "class='" + titleStyleClass+hideTitleStyle+"'" : "") + styleWithoutImage;

			buff.append("<li "+titleStyle);
			if (items.size() > 0)
			{
				String temp = "><div class='greenTitle'"+ additionalAttributes+">"+
								"<div style=\"margin-right: 15px;\">"+title + (!StringHelper.isEmpty(image) ? image : "") + "</div>"+
								"</div>";
				buff.append(temp);
			}
			else
			{
				buff.append("><div class='greenTitle'><span class='alongTitle'>");
				buff.append(title);
				buff.append("</span></div></li></ul>");
				outJsp(buff);
				return super.doEndTag();
			}
		}

		//Добавление элементов списка
		for (i = 0; i < items.size() ; i++)
		{
			StringBuffer item = (StringBuffer) items.get(i);
			if (num == i)
			{
				buff.append("\n<ul class='linksListHidden'"+(stepShowNum > FIRST_STEP_SHOW ? "" : " style='"+displayNone+"'")+" id='"+listSourceName+"'>\n");
				haveHiden = true;
			}

			buff.append(item + "\n");
		}

		buff.append(haveHiden ? "</li></ul>\n" : "</ul>\n");
		outJsp(buff);
		return super.doEndTag();
	}

	protected void outJsp(StringBuffer result) {
            try {
                pageContext.getOut().print(result.toString());
            } catch (IOException e){}
    }

	public void release() {
		super.release();
		num = null;
		items = null;
		title = null;
		titleStyleClass = null;
		listSourceName = null;
	}
}
