package com.rssl.phizic.operations.templates;

import com.rssl.phizic.business.template.BanksDocumentService;
import com.rssl.phizic.business.template.BanksDocument;
import com.rssl.phizic.business.template.DocTemplateService;
import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.OperationBase;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 11.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class DownloadBankDocumentOperation extends OperationBase
{
	private static final BanksDocumentService service = new BanksDocumentService();


	private BanksDocument document;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		document = service.getById(id);
	}

	public BanksDocument getBanksDocument()
	{
		return document;
	}
		
}                              
