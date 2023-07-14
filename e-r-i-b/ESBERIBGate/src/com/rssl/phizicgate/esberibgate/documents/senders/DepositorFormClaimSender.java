package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.claims.DepositorFormClaim;
import com.rssl.phizic.gate.depo.DepoAccountOwnerForm;
import com.rssl.phizic.gate.depo.DepoAccountOwnerFormGateService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.depo.DepoAccountRequestHelper;
import com.rssl.phizicgate.esberibgate.messaging.DepoAccountsResponseSerializer;
import com.rssl.phizicgate.esberibgate.ws.generated.DepoArRsType;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.Status_Type;

/**
 * @author lukina
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepositorFormClaimSender  extends AbstractOfflineClaimSenderBase
{
	private DepoAccountsResponseSerializer responseSerializer = new DepoAccountsResponseSerializer();
	public DepositorFormClaimSender(GateFactory factory)
	{
		super(factory);
	}

	protected IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if(document.getType() != DepositorFormClaim.class)
			throw new GateException("Ќеверный тип документа, должен быть DepositorFormClaim.");
		DepositorFormClaim claim = (DepositorFormClaim) document;
		DepoAccountRequestHelper helper = new DepoAccountRequestHelper(getFactory());
		return helper.createDepoArRq(claim,getClientDepartmentCode(claim));
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		DepositorFormClaim claim =(DepositorFormClaim) document;
		Status_Type status = ifxRs.getDepoArRs().getStatus();
		if (status.getStatusCode() != 0)
			//¬се ошибки пользовательские. ≈сли описание ошибки не пришло, то выдаем сообщение по умолчанию
			throwGateLogicException(status, DepoArRsType.class);
		claim.setExternalId(ifxRs.getDepoArRs().getDocNumber());

		//обновл€ем данные в анкете депонента 
		DepoAccountOwnerFormGateService service = GateSingleton.getFactory().service(DepoAccountOwnerFormGateService.class);
		DepoAccountOwnerForm form = responseSerializer.getDepoAccountOwnerForm(ifxRs, document.getInternalOwnerId());
		service.update(form);
		//TODO BUG024641: ѕеределать реализацию оповещени€ об приеме сообщени€ дл€ шины.  
		createNotification(ifxRs);
	}

	//отправл€ем оповещение о получении анкеты
	private void createNotification(IFXRs_Type ifxRs)
	{
		DepoAccountRequestHelper helper = new DepoAccountRequestHelper(getFactory());
		try
		{
			IFXRq_Type ifxRq = helper.createMessageRecvRq(ifxRs.getDepoArRs().getStatus(),  ifxRs.getDepoArRs().getDocNumber());
			IFXRs_Type response = doRequest(ifxRq);
			Status_Type status = response.getMessageRecvRs().getStatus();
			//if (status.getStatusCode() != 0)
				//TODO ничего не делаем, статус за€вки не должен изменитьс€
				//throw new GateLogicException(status.getStatusDesc());
		}
		catch (Exception ignored)
		{
			//TODO ничего не делаем, статус за€вки не должен изменитьс€
			/*throw new GateException(re);*/
		}
	}
}
