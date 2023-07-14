package com.rssl.phizic.web.dictionaries.url;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.dictionaries.url.WhiteListUrl;
import com.rssl.phizic.operations.dictionaries.url.ListWhiteListUrlOperation;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.log.FormLogParametersReader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 13.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListWhiteListUrlAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("button.save", "save");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListWhiteListUrlForm frm = (ListWhiteListUrlForm) form;
		ListWhiteListUrlOperation operation = createOperation(ListWhiteListUrlOperation.class);

		frm.setData(operation.getMaskUrlList());
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListWhiteListUrlForm frm = (ListWhiteListUrlForm) form;
		ListWhiteListUrlOperation operation = createOperation(ListWhiteListUrlOperation.class);

		FormProcessor<ActionMessages, ?> processor = getFormProcessor(frm);
		if (processor.process())
		{
			int count =  NumericUtil.parseInt((String) frm.getField("urlMaskCount"));
			List<WhiteListUrl> newUrlMask = new ArrayList<WhiteListUrl>();
			List<Long> removeUrlIds =  new ArrayList<Long>();

			String regexpString = "";
			String regexpStringText = "";
			for (int i=0;  i <= count; i++)
			{
				String mask = StringHelper.getEmptyIfNull(frm.getField("urlMaskList"+i));
				String idStr = StringHelper.getEmptyIfNull(frm.getField("urlMaskListIds"+i));
				Long id = null;

				if (!idStr.equals(""))
					id = Long.parseLong(idStr);
				if (id != null && mask.equals(""))
				{
					removeUrlIds.add(id);
				}
				else
				{
					WhiteListUrl whiteListUrl = new WhiteListUrl(id, mask);
					newUrlMask.add(whiteListUrl);
					regexpString = regexpString + "|" + mask;
					regexpStringText = regexpStringText + "|"+ mask;
				}
			}

			if (!regexpString.equals(""))
			{
				regexpString = "^("+ regexpString.substring(1,regexpString.length()) + ").*";
				regexpStringText = "\\[url=(" + regexpStringText.substring(1,regexpStringText.length()) + ")[^\\[]*\\]";
			}

			operation.save(newUrlMask, removeUrlIds);
			saveMessage(request, "Настройки успешно сохранены.");
			//проверяем, хватит ли нового списка URL для всех баннеров
			List<String> namesList = operation.canNotEditAdvertisingList(regexpString, regexpStringText);
			if (CollectionUtils.isNotEmpty(namesList))
				saveMessage(request, "В системе созданы баннеры, использующие URL-адреса, которые не входят в данный список. " +
						"Пожалуйста, отредактируйте в этих баннерах ссылки на URL-адреса:\\n" + StringUtils.join(namesList, "\\n"));
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}

		return start(mapping,frm,request,response);
	}

	private FormProcessor<ActionMessages, ?> getFormProcessor(ListWhiteListUrlForm frm) throws Exception
	{
		Form form = frm.createEditForm();
		//Фиксируем данные, введенные пользователе
		addLogParameters(new FormLogParametersReader("Данные, введенные пользователем", form, frm.getFields()));

		return createFormProcessor(new MapValuesSource(frm.getFields()), form);
	}
}
