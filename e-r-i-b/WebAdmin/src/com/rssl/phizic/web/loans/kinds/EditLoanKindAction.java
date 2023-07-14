package com.rssl.phizic.web.loans.kinds;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.operations.loans.kinds.EditLoanKindOperation;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.web.validators.FileSizeLimitValidator;
import com.rssl.phizic.web.validators.FileNotEmptyValidator;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.xml.sax.SAXException;
import org.hibernate.exception.ConstraintViolationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author gladishev
 * @ created 19.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditLoanKindAction extends EditActionBase
{
	private static final int MAX_FILE_SIZE = 100 * 1024;

	protected Map<String, String> getAdditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.download.claim.desciption", "download");

		return map;
	}

	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditLoanKindOperation operation = createOperation(EditLoanKindOperation.class);
		Long id = form.getId();
		if (id != null && id != 0)
		{
			operation.initialize(id);
		}
		else
		{
			operation.initializeNew();
		}
		return operation;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		LoanKind kind = (LoanKind) entity;

		frm.setField("name", kind.getName());
		if(frm instanceof EditLoanKindForm)
		{
			frm.setField("calculator", kind.isCalculator());
			frm.setField("target", kind.isTarget());
			frm.setField("description", kind.getDescription());
		}
	}

	protected  Form getEditForm(EditFormBase frm)
	{
		if(frm instanceof EditLoanKindForm)
			return EditLoanKindForm.FORM;
		else
			return EditLoanKindSimpleForm.FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		LoanKind loanKind = (LoanKind) entity;

		loanKind.setName((String) data.get("name"));
		loanKind.setCalculator((Boolean) data.get("calculator"));
		loanKind.setTarget((Boolean) data.get("target"));
		loanKind.setDescription((String) data.get("description"));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult) throws BusinessException, BusinessLogicException
	{
		LoanKind loanKind = (LoanKind) editOperation.getEntity();

		if(form instanceof EditLoanKindForm)
		{
			EditLoanKindForm frm = (EditLoanKindForm) form;
			loanKind.setClaimDescription(conver2string(frm.getClaimDescription()));
			loanKind.setDetailsTemplate(conver2string(frm.getDetailsTemplate()));
		}
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase form, EditEntityOperation operation) throws BusinessException
	{
		ActionMessages msgs = new ActionMessages();

		if(form instanceof EditLoanKindForm)
		{
			EditLoanKindForm frm = (EditLoanKindForm) form;
			msgs.add(validateFile(frm.getClaimDescription()));
			msgs.add(validateFile(frm.getDetailsTemplate()));
		}

		return msgs;
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm)
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			return super.doSave(operation, mapping, frm);
		}
		catch (ConstraintViolationException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.cannot-add-or-update-loankind"));
		}
		catch (Exception e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		saveErrors(currentRequest(), msgs);
		return mapping.findForward(FORWARD_START);
	}

	private ActionMessages validateFile(FormFile formFile) throws BusinessException
	{
		ActionMessages msgs = new ActionMessages();

		msgs.add(FileNotEmptyValidator.validate(formFile));
		msgs.add(FileSizeLimitValidator.validate(formFile, MAX_FILE_SIZE));
		msgs.add(validateXml(formFile));

		return msgs;
	}

	public void download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditLoanKindForm frm = (EditLoanKindForm) form;
		Long kindId = frm.getId();
		if (kindId == null)
			return;

		EditLoanKindOperation operation = createOperation(EditLoanKindOperation.class);
		operation.initialize(kindId);

		LoanKind loanKind = operation.getEntity();
		String data = loanKind.getClaimDescription();

		try
		{
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			buffer.write(data.getBytes());

			response.setContentLength(buffer.size());

			response.setContentType("application/x-file-download");
			response.setHeader("Content-disposition", "attachment; filename=loan_kind_claim_description.xml");

			ServletOutputStream outStream = response.getOutputStream();
			buffer.writeTo(outStream);
			outStream.flush();
			outStream.close();

			addLogParameters(new CompositeLogParametersReader(
					new SimpleLogParametersReader("Выгружаемая сущность", "Имя файла", "loan_kind_claim_description.xml"),
					new BeanLogParemetersReader("Вид кредита", loanKind)
			));
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	private ActionMessages validateXml(FormFile file) throws BusinessException
	{
		ActionMessages msgs = new ActionMessages();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try
		{
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			documentBuilder.parse(file.getInputStream());
		}
		catch (SAXException e)
		{
			ActionMessage message = new ActionMessage("errors.xmlValidate",file.getFileName());
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		catch (MalformedURLException e)
		{
			ActionMessage message = new ActionMessage("errors.malformedURL",file.getFileName());
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		catch (IOException e)
		{
			ActionMessage message = new ActionMessage("errors.fileNotSelected");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}

		return msgs;
	}

	private String conver2string(FormFile formFile)
	{
		try
		{
			return new String(formFile.getFileData());
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
