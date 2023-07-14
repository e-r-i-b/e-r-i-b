package com.rssl.phizic.web.common.socialApi.finances.targets;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.TargetType;
import com.rssl.phizic.business.profile.images.ImageService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.finances.targets.CreateAccountTargetOperation;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Balovtsev
 * @version 03.10.13 9:10
 */
public class SaveTargetMobileAction extends TargetsEditMobileAction
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(CreateAccountTargetOperation.class);
	}

	@Override
	protected Map<? extends String, ? extends String> getAdditionalFormProcessorValueSource(EditFormBase frm, EditEntityOperation o) throws Exception
	{
		Map<String, String> source = new HashMap<String, String>();
		source.put("type", ((CreateAccountTargetOperation) o).getEntity().getDictionaryTarget().name());
		return source;
	}

	@Override
	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		ActionMessages actionMessages = new ActionMessages();

		CreateAccountTargetOperation op = (CreateAccountTargetOperation) operation;
		TargetsEditMobileForm form = (TargetsEditMobileForm) frm;
		HttpServletRequest request = currentRequest();
		//кривой депрекейшен
		//noinspection deprecation
		if (ServletFileUpload.isMultipartContent(request))
		{
			FormFile file = form.getFile();
			op.initialize(TargetType.valueOf(form.getType()), file.getFileData(), ImageService.getFileExtention(file.getFileName()));
		}
		else
		{
			op.initialize(TargetType.valueOf(form.getType()));
		}

		if (op.tooManyTargets())
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(op.getTooManyTargetsMessage(), false));
		}

		return actionMessages;
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		TargetsEditMobileForm form = (TargetsEditMobileForm) frm;
		form.setTarget((AccountTarget) operation.getEntity());
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
	}
}
