package com.rssl.phizic.test.web.mbv;

import com.rssl.phizic.business.mbv.MBVMessageHelper;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.gate.mbv.MbvClientIdentity;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.test.webgate.depomobilebank.generated.MBVMigratorServiceLocator;
import com.rssl.phizic.test.webgate.depomobilebank.generated.MBVMigratorSoapBindingStub;
import com.rssl.phizic.test.webgate.depomobilebank.generated.SendMessage;
import com.rssl.phizic.test.webgate.depomobilebank.generated.SendMessageResponse;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Moshenko
 * Date: 12.09.13
 * Time: 17:34
 */
public class MBVAction extends LookupDispatchAction
{
    private static final String FORWARD_START = "Start";
    private static final String FORWARD_INFO = "Info";
    private static final String FORWARD_GET_CLIENT_BY_PHONE = "GetClientByPhoneFwd";
    private static final String FORWARD_ACC_PH = "ClientAccPhFwd";
    private static final String FORWARD_BEGIN = "BeginMigrationFwd";
    private static final String FORWARD_COMMIT = "CommitMigrationFwd";
    private static final String FORWARD_ROLLBACK = "RollbackMigrationwd";
    private static final String FORWARD_DISC_BY_PHONE = "DiscByPhoneFwd";


    private MBVMessageHelper messageHelper = new MBVMessageHelper();

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("getClientByPhone","getClientByPhone");
        map.put("clientAccPh","clientAccPh");
        map.put("beginMigration","beginMigration");
        map.put("commitMigration","commitMigration");
        map.put("rollbackMigration","rollbackMigration");
        map.put("discByPhone","discByPhone");
        map.put("Back","start");
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return mapping.findForward(FORWARD_START);
    }

    public ActionForward getClientByPhone(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        MBVForm  frm = (MBVForm)form;
        String phoneNumber = frm.getPhoneNumber();
        if (StringHelper.isEmpty(phoneNumber))
            return mapping.findForward(FORWARD_GET_CLIENT_BY_PHONE);

        SendMessage rq = new SendMessage();
        String rqStr = messageHelper.getGetClientByPhoneRqStr(phoneNumber);
        rq.setMessage(rqStr);
        SendMessageResponse rs = getWebServiceStub(frm.getMBVMigratorUrl()).sendMessage(rq);
        frm.setMessagesText(rqStr + "\n" + rs.getSendMessageReturn());

        return mapping.findForward(FORWARD_INFO);
    }

    public ActionForward clientAccPh(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        MBVForm  frm = (MBVForm)form;
        if (StringHelper.isEmpty(frm.getFirstName()))
            return mapping.findForward(FORWARD_ACC_PH);
        SendMessage rq = new SendMessage();

        MbvClientIdentity identity = getClientIdentity(frm.getFirstName(),frm.getLastName(),frm.getMiddleName(),
                frm.getBirthday(),frm.getIdType(),frm.getIdNum(),frm.getIdSeries());

        String rqStr = messageHelper.getClientAccPhRqStr(identity);
        rq.setMessage(rqStr);
        SendMessageResponse rs = getWebServiceStub(frm.getMBVMigratorUrl()).sendMessage(rq);
        frm.setMessagesText(rqStr + "\n" + rs.getSendMessageReturn());
        return mapping.findForward(FORWARD_INFO);
    }

    public ActionForward beginMigration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        MBVForm  frm = (MBVForm)form;
        if (StringHelper.isEmpty(frm.getFirstName()))
            return mapping.findForward(FORWARD_BEGIN);
        SendMessage rq = new SendMessage();

        MbvClientIdentity identity = getClientIdentity(frm.getFirstName(),frm.getLastName(),frm.getMiddleName(),
                                                    frm.getBirthday(),frm.getIdType(),frm.getIdNum(),frm.getIdSeries());

        String rqStr = messageHelper.getBeginRqStr(identity);
        rq.setMessage(rqStr);

        SendMessageResponse rs = getWebServiceStub(frm.getMBVMigratorUrl()).sendMessage(rq);
        frm.setMessagesText(rqStr + "\n" + rs.getSendMessageReturn());
        return mapping.findForward(FORWARD_INFO);
    }

    public ActionForward commitMigration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        MBVForm  frm = (MBVForm)form;
	    String migId = frm.getMigrationID();
        if (StringHelper.isEmpty(migId))
            return mapping.findForward(FORWARD_COMMIT);
        SendMessage rq = new SendMessage();

	    String  rqStr = messageHelper.getCommitRqStr(migId);
        rq.setMessage(rqStr);

        SendMessageResponse rs = getWebServiceStub(frm.getMBVMigratorUrl()).sendMessage(rq);
        frm.setMessagesText(rqStr + "\n" + rs.getSendMessageReturn());
        return mapping.findForward(FORWARD_INFO);
    }

    public ActionForward rollbackMigration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        MBVForm  frm = (MBVForm)form;
	    String migId = frm.getMigrationID();
	    if (StringHelper.isEmpty(migId))
            return mapping.findForward(FORWARD_ROLLBACK);
        SendMessage rq = new SendMessage();

        String  rqStr = messageHelper.getRollbackRqStr(migId);
        rq.setMessage(rqStr);

        SendMessageResponse rs = getWebServiceStub(frm.getMBVMigratorUrl()).sendMessage(rq);
        frm.setMessagesText(rqStr + "\n" + rs.getSendMessageReturn());
        return mapping.findForward(FORWARD_INFO);
    }

    public ActionForward discByPhone(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        MBVForm  frm = (MBVForm)form;
        if (StringHelper.isEmpty(frm.getPhoneNumber()))
            return mapping.findForward(FORWARD_DISC_BY_PHONE);
        SendMessage rq = new SendMessage();

        String  rqStr = messageHelper.getDiscByPhoneStr(frm.getPhoneNumber());
        rq.setMessage(rqStr);

        SendMessageResponse rs = getWebServiceStub(frm.getMBVMigratorUrl()).sendMessage(rq);
        frm.setMessagesText(rqStr + "\n" + rs.getSendMessageReturn());
        return mapping.findForward(FORWARD_INFO);
    }


    private MbvClientIdentity getClientIdentity(String firstName,
                                             String lastName,
                                             String middleName,
                                             String birthday,
                                             String docType,
                                             String docSeries,
                                             String docNum) throws ParseException {
        MbvClientIdentity identity = new MbvClientIdentity();
        identity.setFirstName(firstName);
        identity.setSurName(lastName);
        identity.setPatrName(middleName);
        identity.setBirthDay(DateHelper.parseCalendar(birthday));
        identity.setDocType(PersonDocumentType.valueOf(PassportTypeWrapper.getClientDocumentType(docType)));
        identity.setDocNumber(docSeries);
        identity.setDocSeries(docNum);
        return identity;
    }

    private MBVMigratorSoapBindingStub getWebServiceStub(String url)
    {
        try
        {
            MBVMigratorServiceLocator locator = new MBVMigratorServiceLocator();
            locator.setMBVMigratorEndpointAddress(url);

            return (MBVMigratorSoapBindingStub)locator.getMBVMigrator();
        }
        catch (ServiceException e)
        {
            throw new ConfigurationException("Не удалось подключиться к веб-сервису " + url, e);
        }
    }
}
