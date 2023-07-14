package com.rssl.phizic.web.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.filters.FilterParametersByUrl;
import com.rssl.phizic.business.filters.FilterParametersService;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.ServletContextPropertyReader;
import com.rssl.phizic.web.struts.layout.SkinPlugin;

import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 16.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class SaveFilterParameterHelper
{
	private static final FilterParametersService filterParametersService = new FilterParametersService();
	private static final String PAGINATION_OFFSET_KEY = "pagination.offset.key";
	private static final String PAGINATION_SIZE_KEY = "pagination.size.key";
	private static final String PAGINATION_MODE = "list";
	private static final String GRID_INDEX_KEY = "com.rssl.phizic.web.struts.layout.TemplatedCollectionTag.GRID_INDEX_KEY";

	/**
	 * Получить сохраненные параметры
	 * Если сохраненных параметров нет, возвращаем пустую Map'у
	 * @param form
	 * @param operation
	 * @return сохраненные параметры фильтрации
	 * @throws BusinessException
	 */
	public static Map<String, Object> loadFilterParameters(ListFormBase form, ListEntitiesOperation operation)
			throws BusinessException
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		String sessionId = request.getSession().getId();
		String url = request.getRequestURI();
		FilterParametersByUrl filterParameters = filterParametersService.getBySessionIdAndUrl(sessionId,url);
		if(filterParameters != null)
		{
			setPaginationAttribure(filterParameters);
			return filterParameters.getParameters();
		}		
		return Collections.emptyMap();
	}

	/**
	 * Передать параметры фильтрации.
	 * Метод сохраняет их в БД (вместе с URL)
	 * @param filterParams
	 * @throws BusinessException
	 */
	public static void saveFilterParameters(Map<String, Object> filterParams) throws BusinessException
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		String sessionId = request.getSession().getId();
		String requestURI = request.getRequestURI();
		FilterParametersByUrl filterParameters = filterParametersService.getBySessionIdAndUrl(sessionId, requestURI);
		if (filterParameters == null)
			filterParameters = new FilterParametersByUrl();
		filterParameters.setSessionId(sessionId);
		filterParameters.setUrl(requestURI);
		filterParams.put(getOffsetKey(), request.getParameter(getOffsetKey()) != null ? request.getParameter(getOffsetKey()) : 0);
		if (request.getParameter(getSizeKey()) != null)
			filterParams.put(getSizeKey(), request.getParameter(getSizeKey()));

		filterParameters.setParameters(filterParams);
		setPaginationAttribure(filterParameters);
		filterParametersService.addOrUpdate(filterParameters);
	}

	private static void setPaginationAttribure(FilterParametersByUrl filterParameters)
	{
		if(filterParameters == null)
			return;

		HttpServletRequest request = WebContext.getCurrentRequest();
		if (filterParameters.getParameters().get( getOffsetKey() ) != null)
			request.setAttribute( getOffsetKey(), filterParameters.getParameters().get( getOffsetKey()));
		if (filterParameters.getParameters().get(getSizeKey()) != null)
			request.setAttribute(getSizeKey(), filterParameters.getParameters().get(getSizeKey()));
	}

	private static Integer getIndexOffset()
	{
		Integer index = (Integer) WebContext.getCurrentRequest().getAttribute(GRID_INDEX_KEY);
		if (index == null)
			index = 0;

		return index;
	}

	private static String getOffsetKey()
	{
		return  getValueByKey(PAGINATION_OFFSET_KEY);
	}

	private static String getSizeKey()
	{
		return  getValueByKey(PAGINATION_SIZE_KEY);
	}

	private static String getValueByKey(String key)
	{
		return  String.format( getReader().getProperty(key), getIndexOffset());
	}

	private static ServletContextPropertyReader getReader()
	{
		SkinPlugin skinPlugin = SkinPlugin.getInstance(WebContext.getCurrentRequest().getSession().getServletContext());
		return skinPlugin.getSkinConfig().getProperty(PAGINATION_MODE);
	}
}
