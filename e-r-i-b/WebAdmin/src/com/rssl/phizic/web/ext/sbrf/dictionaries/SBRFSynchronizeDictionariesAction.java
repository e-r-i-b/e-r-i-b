package com.rssl.phizic.web.ext.sbrf.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.DictionaryConfig;
import com.rssl.phizic.business.dictionaries.DictionaryDescriptor;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.dictionaries.SynchronizeResult;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.dictionaries.SynchronizeDictionariesOperation;
import com.rssl.phizic.operations.dictionaries.loanclaim.LoadAddressDictionariesOperation;
import com.rssl.phizic.operations.dictionaries.sbnkd.SBNKDDictionariesOperation;
import com.rssl.phizic.utils.files.NoCloseInputStream;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.configure.SynchronizeDictionariesActionBase;
import com.rssl.phizic.web.configure.SynchronizeDictionariesForm;
import com.rssl.phizic.web.log.ArrayLogParametersReader;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kosyakov
 * @ created 20.09.2006
 * @ $Author: shapin $
 * @ $Revision: 80984 $
 */
public class SBRFSynchronizeDictionariesAction extends SynchronizeDictionariesActionBase
{
	private static final String FORWARD_RESULTS = "Results";

	protected Map<String, String> getKeyMethodMap ()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.loadDictionaries", "load");
		map.put("button.loadLoanClaimDict", "loadLoanClaimDict");
		map.put("button.loadSBNKDDict", "loadSBNKDDict");
		return map;
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		for (DictionaryDescriptor descriptor : ConfigFactory.getConfig(DictionaryConfig.class).getDescriptors())
		{
			if(checkAccess(SynchronizeDictionariesOperation.class, descriptor.getName()))
				return createOperation(SynchronizeDictionariesOperation.class, descriptor.getName());
		}

		return createOperation(SynchronizeDictionariesOperation.class, "LoadAddressDictionariesService");
	}

	@Override
	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                             HttpServletResponse response) throws Exception
	{
		SynchronizeDictionariesForm frm = (SynchronizeDictionariesForm) form;
		SynchronizeDictionariesOperation operation = null;

		for (String serviceName : frm.getSelected())
		{
			operation = createOperation(SynchronizeDictionariesOperation.class, serviceName);
		}

		boolean  temporary = frm.isTemporary();
		FormFile content   = frm.getContent();
		String fileType    = frm.getFileType();

		ActionMessages actionMessages = new ActionMessages();
		if (!temporary && (content == null || content.getFileName().length() == 0))
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Файл не выбран", false));
		}
		else if (frm.getSelected() == null || frm.getSelected().length == 0)
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Справочники не выбраны", false));
		}
		else
		{
			NoCloseInputStream stream = null;
			try
			{
				if (temporary)
				{
					stream = new NoCloseInputStream( new ByteArrayInputStream(new byte[0]) );
				}
				else
				{
					stream = new NoCloseInputStream( content.getInputStream() );
				}

				List<SynchronizeResult> synchronizeResultList = null;
				if (fileType.equals(SynchronizeDictionariesForm.FILE_TYPE_XML))
					synchronizeResultList = operation.synchronizeXml(new ArrayList<String>(Arrays.asList(frm.getSelected())), stream, content.getFileName(), temporary);
				else
					synchronizeResultList = operation.synchronizeCsv(Arrays.asList(frm.getSelected()), stream);
				frm.setSynchronizeResults(synchronizeResultList);
			}
			catch(IOException ignored)
			{
				actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Некорректный формат файла.", false));
			}
			finally
			{
				if (stream != null)
				{
					stream.closeStream();
				}
			}
		}

		ActionForward actionForward;
		if (actionMessages.size() > 0)
		{
			saveErrors(request, actionMessages);
			actionForward = mapping.findForward(FORWARD_START);
		}
		else
		{
			actionForward = mapping.findForward(FORWARD_RESULTS);
		}

		frm.setDescriptors(operation.getEntity());
		addLogParameters(new ArrayLogParametersReader("Выбранные справочники", frm.getSelected()));
		return actionForward;
	}

	public ActionForward loadLoanClaimDict(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SynchronizeDictionariesForm frm = (SynchronizeDictionariesForm) form;
		LoadAddressDictionariesOperation addressOperation = createOperation(LoadAddressDictionariesOperation.class, "LoadAddressDictionariesService");

		FormFile content   = frm.getLoanClaimDictFile();
		String[] selected  = frm.getLcDictSelected();

		ActionMessages actionMessages = new ActionMessages();
		if (content == null || content.getFileName().length() == 0)
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Файл не выбран", false));
		}
		else if (selected == null || selected.length==0)
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Справочники не выбраны", false));
		}
		else
		{
			NoCloseInputStream stream = null;
			try
			{
				stream = new NoCloseInputStream( content.getInputStream() );
				List<SynchronizeResult> result = new ArrayList<SynchronizeResult>();
				if (selected.length>1)
					throw new BusinessLogicException("Загружать справочники можно по одному. Пожалуйста, выберите один справочник для загрузки данных в систему.");
				else
				{
					result.add(addressOperation.load(selected[0], stream));
					frm.setSynchronizeResults(result);
				}
			}
			catch(IOException ignored)
			{
				actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Некорректный формат файла.", false));
			}
			catch (BusinessLogicException ex)
			{
				actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(),false));
			}
			finally
			{
				if (stream != null)
				{
					stream.closeStream();
				}
			}
		}

		ActionForward actionForward;
		if (actionMessages.size() > 0)
		{
			saveErrors(request, actionMessages);
			actionForward = mapping.findForward(FORWARD_START);
		}
		else
		{
			actionForward = mapping.findForward(FORWARD_RESULTS);
		}
		SynchronizeDictionariesOperation operation = createOperation(SynchronizeDictionariesOperation.class , "LoadAddressDictionariesService");
		frm.setDescriptors(operation.getEntity());
		addLogParameters(new ArrayLogParametersReader("Выбранные справочники для заявки на кредит", frm.getLcDictSelected()));
		return actionForward;
	}

	public ActionForward loadSBNKDDict(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SynchronizeDictionariesForm frm = (SynchronizeDictionariesForm) form;
		SBNKDDictionariesOperation operation = createOperation(SBNKDDictionariesOperation.class, "SBNKDDictionaries");

		FormFile content   = frm.getSbnkdDictionaryFile();
		String[] selected  = frm.getSbnkdDictionarySelected();

		ActionMessages actionMessages = new ActionMessages();
		if (content == null || content.getFileName().length() == 0)
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Файл не выбран", false));
		}
		else if (selected == null || selected.length==0)
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Справочники не выбраны", false));
		}
		else
		{
			NoCloseInputStream stream = null;
			try
			{
				stream = new NoCloseInputStream( content.getInputStream() );
				List<SynchronizeResult> result = new ArrayList<SynchronizeResult>();
				if (selected.length > 1)
					throw new BusinessLogicException("Загружать справочники можно по одному. Пожалуйста, выберите один справочник для загрузки данных в систему.");
				else
				{
					result.add(operation.load(stream, content.getFileName(), selected[0]));
					frm.setSynchronizeResults(result);
				}
			}
			catch(IOException ignored)
			{
				actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Некорректный формат файла.", false));
			}
			catch (BusinessLogicException ex)
			{
				actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(),false));
			}
			finally
			{
				if (stream != null)
				{
					stream.closeStream();
				}
			}
		}
		ActionForward actionForward;
		if (actionMessages.size() > 0)
		{
			saveErrors(request, actionMessages);
			actionForward = mapping.findForward(FORWARD_START);
		}
		else
		{
			actionForward = mapping.findForward(FORWARD_RESULTS);
		}
		SynchronizeDictionariesOperation op = createOperation(SynchronizeDictionariesOperation.class , "SBNKDDictionaries");
		frm.setDescriptors(op.getEntity());
		addLogParameters(new ArrayLogParametersReader("Выбранные справочники для заявки на дебетовую карту (СБНКД)", frm.getSbnkdDictionarySelected()));
		return actionForward;
	}
}
