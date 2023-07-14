package com.rssl.phizic.web.struts.layout;

import com.rssl.phizic.web.common.ServletContextPropertyReader;
import com.rssl.phizic.web.config.view.ViewConfig;
import com.rssl.phizic.web.config.view.ViewConfigImpl;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;
import javax.xml.bind.JAXBException;

/**
 * @author Evgrafov
 * @ created 24.07.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

public class SkinPlugin implements PlugIn
{
	private static final org.apache.commons.logging.Log log = LogFactory.getLog(SkinPlugin.class);

	private static final String CONTEXT_KEY = SkinPlugin.class.getName();
	private static final String SKIN_PATH   = "/WEB-INF/skin/";

	private ServletContext servletContext;
	private String         templates;
	private String         viewConfigXml = "/WEB-INF/view-config.xml";

	private SkinConfig     skinConfig;
	private ViewConfig     viewConfig;

	/**
	 * Получить инстанс SkinPlugin из ServletContext
	 * @param context контекст
	 * @return инстанс plugin'a
	 */
	public static SkinPlugin getInstance(ServletContext context)
	{
		SkinPlugin plugin = (SkinPlugin) context.getAttribute(CONTEXT_KEY);

		if(plugin == null)
			throw new RuntimeException("SkinPlugin not found");

		return plugin;
	}

	/**
	 * Получить инстанс SkinPlugin из ServletContext
	 * @param pageContext контекст
	 * @return инстанс plugin'a
	 */
	public static SkinPlugin getInstance(PageContext pageContext)
	{
		return getInstance(pageContext.getServletContext());
	}

	/**
	 * @return список файлов ресурсов разделенных ';'. В имени ресурса начальные и конечные whitespace игнорируются.
	 */                                
	public String getTemplates()
	{
		return templates;
	}

	/**
	 * @param templates see getter's docs
	 */
	public void setTemplates(String templates)
	{
		this.templates = templates;
	}

	/**
	 * @return файл настройки view-config
	 */
	public String getViewConfigXml()
	{
		return viewConfigXml;
	}

	/**
	 * @param resource файл настройки view-config
	 */
	public void setViewConfigXml(String resource)
	{
		this.viewConfigXml = resource;
	}

	/**
	 * @return SkinConfig
	 */
	public SkinConfig getSkinConfig()
	{
		return skinConfig;
	}

	/**
	 * @return ViewConfig
	 */
	public ViewConfig getViewConfig()
	{
		return viewConfig;
	}

	public void init(ActionServlet servlet, ModuleConfig config) throws ServletException
	{
		servletContext = servlet.getServletContext();
		log.info("SkinPlugin starting...");

		if(templates == null)
			throw new ServletException("Не задано свойство resources");

		if(viewConfigXml == null)
			throw new ServletException("Не задано свойство viewConfigXml");

		skinConfig = loadSkinConfig();
		viewConfig = loadViewConfig();

		servletContext.setAttribute(CONTEXT_KEY, this);

		log.info("SkinPlugin started");
	}

	private ViewConfig loadViewConfig() throws ServletException
	{
		log.info("Loading view config");

		try
		{
			InputStream is = servletContext.getResourceAsStream(viewConfigXml);
			if(is == null)
				log.warn("Файл настроек для ViewConfig не найден :" + viewConfigXml);
			return new ViewConfigImpl(is);
		}
		catch (JAXBException e)
		{
			throw new ServletException("Ошибка при чтении XML из " + viewConfigXml, e);
		}
	}

	private SkinConfig loadSkinConfig() throws ServletException
	{
		String[] templatesArray = templates.split(";");

		Map<String, ServletContextPropertyReader> readerByFile = new HashMap<String, ServletContextPropertyReader>();

		for (String template : templatesArray)
		{
			ServletContextPropertyReader fileReader = createReader(servletContext, template.trim());
			readerByFile.put(template, fileReader);
		}

		return new SkinConfig(readerByFile);
	}

	private ServletContextPropertyReader createReader(ServletContext servletContext, String template) throws ServletException
	{
		try
		{
			String resourcePath = SKIN_PATH + template + ".properties";
			ServletContextPropertyReader reader = new ServletContextPropertyReader(servletContext, resourcePath);

			log.info("Resource for template [" + template + "] is loaded");

			return reader;
		}
		catch (IOException e)
		{
			throw new ServletException("Failed to create reader for resource:" + template, e);
		}
	}

	public void destroy()
	{
		servletContext.removeAttribute(CONTEXT_KEY);

		servletContext = null;
		templates      = null;
		skinConfig     = null;
		viewConfigXml  = null;
		viewConfig     = null;

		log.info("SkinPlugin destroyed");
	}
}