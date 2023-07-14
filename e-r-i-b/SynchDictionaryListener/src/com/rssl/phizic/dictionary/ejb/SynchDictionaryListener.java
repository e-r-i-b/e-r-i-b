package com.rssl.phizic.dictionary.ejb;

import com.rssl.phizgate.common.credit.bki.BKIConstants;
import com.rssl.phizgate.common.credit.bki.BKIResponse;
import com.rssl.phizgate.common.credit.etsm.ETSMLoanClaimConstants;
import com.rssl.phizgate.common.profile.MBKCastService;
import com.rssl.phizgate.common.profile.PhoneUpdate;
import com.rssl.phizic.business.access.AccessSchemeUpdater;
import com.rssl.phizic.business.bki.CreditProfileUpdater;
import com.rssl.phizic.business.dictionaries.synchronization.information.SynchronizationStateService;
import com.rssl.phizic.business.dictionaries.synchronization.notification.NotificationEntity;
import com.rssl.phizic.business.dictionaries.synchronization.notification.NotificationService;
import com.rssl.phizic.business.loanclaim.etsm.*;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.config.events.RefreshPhizIcConfigEvent;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.context.node.events.UpdateNodeContextEvent;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.ws.currency.sbrf.EsbEribRatesBackServiceImpl;
import com.rssl.phizic.ws.currency.sbrf.EsbEribRatesProxyBackServiceImpl;
import com.rssl.phizic.ws.esberiblistener.depo.EsbEribSecurityDicUpdater;
import com.rssl.phizic.ws.esberiblistener.depo.ProxyEsbEribSecurityDicUpdater;
import com.rssl.phizic.ws.esberiblistener.esberib.ProxySecurityDicUpdater;
import com.rssl.phizic.ws.esberiblistener.esberib.SecurityDicUpdater;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.IFXRq_Type;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
/**
 * Обработчик сообщений на обновление шинных справочников
 * @author gladishev
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 */
public class SynchDictionaryListener implements MessageDrivenBean, MessageListener
{
	private static final MBKCastService mbkCastService = new MBKCastService(null);

	public void ejbCreate() throws EJBException
	{
	}

	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException
	{
	}

	public void ejbRemove() throws EJBException
	{
	}

	public void onMessage(Message message)
	{
		ApplicationInfo.setCurrentApplication(Application.Listener);
		try
		{
			ObjectMessage objectMessage = (ObjectMessage) message;
			String jmsType = message.getJMSType();

			if (ProxyEsbEribSecurityDicUpdater.SECURITY_DIC_XML_REQUEST.equals(jmsType))
			{
				Document document = XmlHelper.parse((String) objectMessage.getObject());
				EsbEribSecurityDicUpdater updater = new EsbEribSecurityDicUpdater(document);
				updater.updateSecurityDictionary();
			}
			else if (ProxySecurityDicUpdater.SECURITY_DIC_IFX_REQUEST.equals(jmsType))
			{
				SecurityDicUpdater updater = new SecurityDicUpdater((IFXRq_Type) objectMessage.getObject());
				updater.doIFX();
			}
			else if (EsbEribRatesProxyBackServiceImpl.RATES_DIC_REQUEST.equals(jmsType))
			{
				EsbEribRatesBackServiceImpl updater = new EsbEribRatesBackServiceImpl();
				updater.doIFX((String) objectMessage.getObject());
			}
			else if (NotificationService.SYNCHRONIZE_DICTIONARY_JMS_TYPE_NAME.equals(jmsType))
			{
				new SynchronizationStateService().doWait((NotificationEntity) objectMessage.getObject());
			}
			else if (DbPropertyService.REPLICATE_PROPERTIES_MESSAGE_KEY.equals(jmsType))
			{
				DbPropertyService.updateProperties(((String)objectMessage.getObject()));
				EventSender.getInstance().sendEvent(new RefreshPhizIcConfigEvent());
			}
			else if (ETSMLoanClaimConstants.STATUS_JMS_TYPE.equals(jmsType))
			{
				LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
				if (loanClaimConfig.isUseXSDRelease19Version())
				{
					ETSMLoanClaimUpdaterRelease19 updater = new ETSMLoanClaimUpdaterRelease19();
					updater.update((String) objectMessage.getObject());
				} else if (loanClaimConfig.isUseXSDRelease16Version())
				{
					ETSMLoanClaimUpdaterRelease16 updater = new ETSMLoanClaimUpdaterRelease16();
					updater.update((String) objectMessage.getObject());
				}
				else
				{
					ETSMLoanClaimUpdaterRelease13 updater = new ETSMLoanClaimUpdaterRelease13();
					updater.update((String) objectMessage.getObject());
				}
			}
			else if (ETSMLoanClaimConstants.ERIB_SEND_ETSM_TYPE.equals(jmsType))
			{
				LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
				//Появилась только с 16-го релиза
				if (loanClaimConfig.isUseXSDRelease16Version() ||
						loanClaimConfig.isUseXSDRelease19Version())
				{
					ETSMCreateNewLoanClaim updater = new ETSMCreateNewLoanClaim();
					updater.update((String) objectMessage.getObject());
				}
			}
			else if (ETSMLoanClaimConstants.INITIATE_CONSUMER_JMS_TYPE.equals(jmsType))
			{
				ETSMLoanOfferUpdaterRelease19 updater = new ETSMLoanOfferUpdaterRelease19();
				updater.update((String) objectMessage.getObject());
			}
			else if (AccessSchemeUpdater.REPLICATE_ACCESS_SCHEME_JMS_TYPE.equals(jmsType))
			{
				AccessSchemeUpdater updater = new AccessSchemeUpdater();
				updater.update((List) objectMessage.getObject());
			}
			else if (UpdateNodeContextEvent.TYPE.equals(jmsType))
			{
				EventSender.getInstance().sendEvent((UpdateNodeContextEvent) objectMessage.getObject());
			}
			else if (BKIConstants.REPORT_JMS_TYPE.equals(jmsType))
			{
				CreditProfileUpdater updater = new CreditProfileUpdater();
				updater.update((BKIResponse) objectMessage.getObject());
			}
			else if (MBKCastService.START_ACTUALIZE_MESSAGE_NAME.equals(jmsType))
			{
				mbkCastService.addLastPhoneUpdateInfo((PhoneUpdate)objectMessage.getObject());
			}
			else
				LogFactory.getLog(LogModule.Core.name()).error("Неизвестный тип jms-сообщения:" + jmsType);

		}
		catch (Exception e)
		{
			LogFactory.getLog(LogModule.Core.name()).error(e.getMessage(), e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}
}
