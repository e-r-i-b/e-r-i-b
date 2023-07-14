package com.rssl.phizic.web.struts.layout;

import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.web.config.view.TemplateManager;
import com.rssl.phizic.dataaccess.query.PaginalDataSource;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * @author Krenev
 * @ created 16.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class NewCollectionTag extends BodyTagSupport
{
	private String name;//имя бина, содержащего коллекцию.
	private String property;// имя свойства бина содержащего коллекцию.
	private boolean first=true;//признак первой итерации.

	private String model;//Модель. Задавет параметры рендеринга панели.
	private PropertyReader defaultsReader;
	private TemplateManager templateManager;
	private String view; //имя view в view-config. Задает параметры отдельных столбцов.
	private Collection collection;

	public final int doStartTag() throws JspException
	{
		prepareGrid();
		//если коллекция пуста -> рендерим бланк и пропускаем боди.
		return EVAL_BODY_BUFFERED;
	}

	public int doAfterBody() throws JspException
	{
		if (first){
			StringBuffer buffer = new StringBuffer();
			doStartRendering(buffer);
			TagUtils.write(pageContext, buffer.toString());
			return EVAL_BODY_AGAIN;
		}
		//renderEndLine
		//if not has more{
		//  doEndRendering
		//  return SKIP_BODY;
		//}
		// renderStartLine
		// renderLine
		return EVAL_BODY_AGAIN;
	}

	private void prepareGrid() throws JspException
	{
/*
		if (model == null)
			throw new RuntimeException("You must set model attribute");

		SkinPlugin skinPlugin = SkinPlugin.getInstance(pageContext);

		defaultsReader = skinPlugin.getSkinConfig().getProperty(model);
		templateManager = skinPlugin.getViewConfig().getListTemplateManager(view);
*/
		prepareCollection();
	}

	private void doStartRendering(StringBuffer buffer)
	{
		//TODO StartRender
		//TODO RenderHeader
	}

	private void prepareCollection() throws JspException
	{
		//получаем коллекцию
		collection = (Collection) getBeanProperty(name, property);
		//инициализируем параметрами
		if (!(collection instanceof PaginalDataSource)){
			return;
		}

		PaginalDataSource paginalDataSource = (PaginalDataSource) collection;
		//todo установить параметры пагинации.
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setProperty(String property)
	{
		this.property = property;
	}

	public void release()
	{
		super.release();
		name = null;
		property = null;
		first = true;
		collection = null;
	}

	private Object getBeanProperty(String beanName, String propertyName) throws JspException
	{
		Object bean = pageContext.findAttribute(beanName);
		if (bean == null)
		{
			throw new JspException("Cannot find bean " + beanName);
		}
		if (propertyName == null)
		{
			return bean;
		}
		try
		{
			return PropertyUtils.getProperty(collection, propertyName);
		}
		catch (IllegalAccessException e)
		{
			throw new JspException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new JspException(e);
		}
		catch (NoSuchMethodException e)
		{
			throw new JspException(e);
		}
	}
}
