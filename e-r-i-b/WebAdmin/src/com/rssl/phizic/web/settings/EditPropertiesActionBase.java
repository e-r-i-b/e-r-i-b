package com.rssl.phizic.web.settings;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.PropertiesPageMode;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts.action.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Базовый экшен редактирования настроек системы
 * 
 * @author gladishev
 * @ created 31.01.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditPropertiesActionBase<T extends EditPropertiesOperation> extends EditActionBase
{
	protected static final Map<Class, PropertySerializer> serializers = new HashMap<Class, PropertySerializer>();
	private static final String ERROR_NODES_REPLICATE_MESSAGE = "Отправка настроек для репликации в блоки [%s] завершилась ошибкой.";
	private static final String SUCCESSFULL_REPLICATE_MESSAGE = "Настройки успешно реплицированы.";
	private static final String TIME_FORMAT = "HH:mm";
	private static final char NODES_LIST_SEPARATOR = '|';
	private static final String PAGE_TOKEN_ATTRIBUTE_KEY = "PAGE_TOKEN";

	static
	{
		PropertySerializer defaultSerializer = new PropertySerializer()
		{
			public String serialise(Object data)
			{
				return data.toString();
			}
		};

		serializers.put(String.class, defaultSerializer);
		serializers.put(Integer.class, defaultSerializer);
		serializers.put(BigInteger.class, defaultSerializer);
		serializers.put(Boolean.class, defaultSerializer);
		serializers.put(Long.class, defaultSerializer);
		serializers.put(BigDecimal.class, defaultSerializer);
		serializers.put(Date.class, new PropertySerializer()
		{
			public String serialise(Object data)
			{
				return DateHelper.dateToString("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS", (Date) data);
			}
		});
		serializers.put(Time.class, new PropertySerializer()
		{
			public String serialise(Object data)
			{
				return DateFormatUtils.format((Time)data, TIME_FORMAT);
			}
		});
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.replicate", "replicate");
		map.put("button.refresh", "viewSyncInfo");
		return map;
	}

	public ActionForward replicate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPropertiesFormBase frm = (EditPropertiesFormBase) form;
		frm.setPageMode(PropertiesPageMode.REPLICATE);
		if (ArrayUtils.isEmpty(frm.getSelectedNodes()) || ArrayUtils.isEmpty(frm.getPropertiesForReplicate()))
			throw new BusinessException("Не выбраны элементы для репликации.");

		T operation = getEditOperation();
		initializeReplicationOperation(operation, frm, new HashSet<String>(Arrays.asList(frm.getPropertiesForReplicate())));

		List<Long> replicate = operation.replicate(Arrays.asList(frm.getSelectedNodes()));
		String message = replicate.isEmpty() ? SUCCESSFULL_REPLICATE_MESSAGE : String.format(ERROR_NODES_REPLICATE_MESSAGE, StringUtils.join(replicate, ", "));

		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false), null);

		updateFormSyncInfoData(frm, operation);
		updateFormAdditionalData(frm, createViewOperation(frm));
		return createSuccessReplicateActionForward(mapping, form, request, response);
	}

	public ActionForward viewSyncInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPropertiesFormBase frm = (EditPropertiesFormBase) form;
		frm.setPageMode(PropertiesPageMode.VIEW_SYNC_INFO);
		EditPropertiesOperation operation = getEditOperation();
		operation.initialiizeViewSyncInfo(frm.getReplicationGUID(),
				DateHelper.toCalendar(DateHelper.parseStringTime(frm.getDate())),
				new ArrayList<String>(Arrays.asList(StringUtils.split(frm.getReplicatedNodes(), NODES_LIST_SEPARATOR))));
		frm.setSyncInfo(operation.getSyncInfo());
		return mapping.findForward(FORWARD_START);
	}

	protected ActionForward createSuccessReplicateActionForward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		EditPropertiesFormBase frm = (EditPropertiesFormBase) form;
		ActionForward forward= new ActionForward(mapping.findForward(FORWARD_SUCCESS));
		forward.setPath(forward.getPath() + "?" + "operation" + "=" + "button.refresh"
				+ "&" + "replicatedNodes" + "=" + StringUtils.join(frm.getSelectedNodes(), NODES_LIST_SEPARATOR)
				+ "&" + "replicationGUID" + "=" + frm.getReplicationGUID()
				+ "&" + "date" + "=" + DateHelper.dateToString("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS", DateHelper.toDate(frm.getReplicationDate()))
				+ "&" + PAGE_TOKEN_ATTRIBUTE_KEY + "=" + request.getParameter(PAGE_TOKEN_ATTRIBUTE_KEY));
		return forward;
	}

	/**
	 * Сохраняем в форму параметры для просмотра информации о состоянии синхронизации настроек
	 */
	private void updateFormSyncInfoData(EditPropertiesFormBase form, EditPropertiesOperation operation)
	{
		form.setReplicationGUID(operation.getGuid());
		form.setReplicationDate(operation.getReplicationDate());
	}

	protected abstract T getEditOperation() throws BusinessException;

	protected T getViewOperation() throws BusinessException, BusinessLogicException
	{
		return getEditOperation();
	}

	protected Form getEditForm(EditFormBase frm)
	{
		EditPropertiesFormBase form = (EditPropertiesFormBase) frm;
		return form.getForm();
	}

	@Override
	protected T createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		T operation = getEditOperation();
		operation.initialize(getCategory(frm));
		return operation;
	}

	@Override
	protected T createViewOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		T operation = getViewOperation();
		initializeViewOperation(operation, (EditPropertiesFormBase) frm);
		return operation;
	}

	protected void initializeViewOperation(T operation, EditPropertiesFormBase form) throws BusinessException, BusinessLogicException
	{
		operation.initialize(getCategory(form), form.getFieldKeys());
	}

	protected void initializeReplicationOperation(T operation, EditPropertiesFormBase form, Set<String> propertyKeys) throws BusinessException, BusinessLogicException
	{
		operation.initializeReplicate(getCategory(form), propertyKeys);
	}

	protected final void updateForm(EditFormBase form, Object entity) throws Exception
	{
		//noinspection unchecked
		Map<String, String> map = (Map<String, String>) entity;
		for (String key : map.keySet())
			form.setField(key, map.get(key));
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		//noinspection unchecked
		Map<String, String> properties = (Map<String, String>) entity;
		for (String key : data.keySet())
		{
			Object obj = data.get(key);
			properties.put(key, obj != null ? getSerializer(obj.getClass()).serialise(obj) : null);
		}
	}

	protected PropertySerializer getSerializer(Class aClass)
	{
		return serializers.get(aClass);
	}

	protected PropertyCategory getCategory(EditFormBase form)
	{
		return PropertyCategory.Phizic;
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation op) throws Exception
	{
		EditPropertiesFormBase form = (EditPropertiesFormBase) frm;
		EditPropertiesOperation operation = (EditPropertiesOperation) op;
		boolean isReplication = form.getPageMode() == PropertiesPageMode.REPLICATE;
		if (isReplication || initNodes())
			form.setNodes(operation.getNodes());

		if (isReplication)
		{
			form.setBlackListFieldForReplication(operation.getBlackListFieldForReplication());
		}
	}

	protected boolean initNodes()
	{
		return false;
	}
}
