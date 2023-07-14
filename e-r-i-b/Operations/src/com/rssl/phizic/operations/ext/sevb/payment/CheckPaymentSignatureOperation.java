package com.rssl.phizic.operations.ext.sevb.payment;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.phizic.business.documents.metadata.converters.FormDataConverter;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.security.GOSTUtils;
import com.rssl.phizic.utils.StringUtils;
import org.hibernate.Session;

import java.util.Arrays;

/**
 * @author Evgrafov
 * @ created 02.04.2007
 * @ $Author: Roshka $
 * @ $Revision: 3947 $
 */

public class CheckPaymentSignatureOperation extends OperationBase
{
	private static final BusinessDocumentService paymentService = new BusinessDocumentService();

	private BusinessDocument payment;
	private boolean           success;
	private DocumentSignature signature;
	private String            documentContent;
	private Metadata metadata;

	public BusinessDocument getPayment()
	{
		return payment;
	}

	public void initialize(final Long paymentId) throws BusinessException, BusinessLogicException
	{
		try
		{
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        payment = paymentService.findById(paymentId);
			        if (payment == null)
				        throw new BusinessLogicException("Платеж не найден ID=" + paymentId);

			        signature = payment.getSignature();
			        if (signature == null)
				        throw new BusinessLogicException("Платеж не имеет подписи ID=" + paymentId);

			        metadata = MetadataCache.getExtendedMetadata(payment);

			        signature.getText(); //fetch lazy
			        return null;
		        }
		    });
		}
		catch (TemporalDocumentException e)
		{
			throw new TemporalBusinessException(e);
		}
		catch(BusinessException e)
		{
			throw e;
		}
		catch(BusinessLogicException e)
		{
			throw e;
		}
		catch(Exception e)
		{
		   throw new BusinessException(e);
		}

	}

	//TODO этот метод из-за кривого способа получения метаданных (там HttpServletRequert фигурирует)
	@Transactional
	public void validate() throws BusinessException
	{
		String text = signature.getText();
		String[] strings = text.split(":");

		// данные для подписи

		DocumentFieldValuesSource fieldValuesSource =  new DocumentFieldValuesSource(metadata, payment);
		FormDataConverter fdc = new FormDataConverter(metadata.getForm(), fieldValuesSource);
		byte[] bytes = fdc.toSignableForm();

		byte[] privateKey = StringUtils.fromHexString( strings[0] );

		byte[] storedSignature = StringUtils.fromHexString( strings[1]);

		byte[] calculatedSignature = GOSTUtils.hmac(privateKey, bytes);

		success = Arrays.equals(storedSignature, calculatedSignature);

		documentContent = new String(bytes);
	}

	public boolean isSuccess()
	{
		return success;
	}

	public DocumentSignature getSignature()
	{
		return signature;
	}

	public String getDocumentContent()
	{
		return documentContent;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}
}