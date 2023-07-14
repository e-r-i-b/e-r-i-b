<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/mail/archiving/unarchive">
<tiles:insert definition="mailEdit">
<tiles:put name="submenu" type="string" value="UnArchivingMail"/>
<tiles:put name="needSave" type="string" value="false"/>

<c:set var="form" value="${UnArchiveMailForm}"/>
<tiles:put name="data" type="string">
    <tiles:insert definition="paymentForm" flush="false">
	    <tiles:put name="name" value="Разархивировать"/>
    	<tiles:put name="data">
            <html:hidden property="defaultParameter(folder)"/>
            <html:hidden property="defaultParameter(fromPeriod)"/>
            <html:hidden property="defaultParameter(toPeriod)"/>
            <html:hidden property="defaultParameter(showUnArchivingMailToClient)"/>
            <html:hidden property="defaultParameter(ANSWER)"/>
            <html:hidden property="defaultParameter(READ)"/>
            <html:hidden property="defaultParameter(DRAFT)"/>
            <html:hidden property="defaultParameter(NEW_EPLOYEE_MAIL)"/>
            <html:hidden property="defaultParameter(ANSWER_EPLOYEE_MAIL)"/>
            <html:hidden property="defaultParameter(NONE)"/>
            <html:hidden property="defaultParameter(subject)"/>
            <html:hidden property="defaultParameter(type)"/>
            <html:hidden property="defaultParameter(isAttach)"/>
            <html:hidden property="defaultParameter(userSurName)"/>
            <html:hidden property="defaultParameter(userFirstName)"/>
            <html:hidden property="defaultParameter(userPatrName)"/>
            <html:hidden property="defaultParameter(employeeSurName)"/>
            <html:hidden property="defaultParameter(employeeFirstName)"/>
            <html:hidden property="defaultParameter(employeePatrName)"/>
            <html:hidden property="defaultParameter(employeeLogin)"/>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.path" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="field(folder)" size="30" maxlength="128"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.date" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    с <html:text property="field(fromPeriod)" styleClass="dot-date-pick"/>&nbsp;
                    по <html:text property="field(toPeriod)" styleClass="dot-date-pick"/>
                    <div>
                        <html:checkbox name="form" property="field(showUnArchivingMailToClient)" value="true">
                            <bean:message bundle="mailBundle"  key="unarchiving.show.unarchive.mail"/>
                        </html:checkbox>
                    </div>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.status" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <table>
                        <tr>
                            <td>
                                <html:checkbox name="form" property="field(ANSWER)" value="true">
                                    <bean:message bundle="mailBundle" key="label.statusAnswer"/>
                                </html:checkbox>&nbsp;
                            </td>
                            <td>
                                <html:checkbox name="form" property="field(READ)" value="true">
                                    <bean:message bundle="mailBundle" key="label.statusReceived"/>
                                </html:checkbox>&nbsp;
                            </td>
                            <td>
                                <html:checkbox name="form" property="field(DRAFT)" value="true">
                                    <bean:message  bundle="mailBundle" key="label.statusDraft"/>
                                </html:checkbox>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <html:checkbox name="form" property="field(NEW_EPLOYEE_MAIL)" value="true">
                                    <bean:message  bundle="mailBundle" key="label.statusNewEmplM"/>
                                </html:checkbox>&nbsp;
                            </td>
                            <td>
                                <html:checkbox name="form" property="field(ANSWER_EPLOYEE_MAIL)" value="true">
                                    <bean:message  bundle="mailBundle" key="label.statusAnswerEmpl"/>
                                </html:checkbox>&nbsp;
                            </td>
                            <td>
                                <html:checkbox name="form" property="field(NONE)" value="true">
                                    <bean:message  bundle="mailBundle" key="label.statusNone"/>
                                </html:checkbox>&nbsp;
                            </td>
                        </tr>
                    </table>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.subject" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="field(subject)" maxlength="100"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.mailType" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:select property="field(type)">
                        <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                        <html:option value="CONSULTATION"><bean:message key="mailType.CONSULTATION" bundle="mailBundle"/></html:option>
                        <html:option value="COMPLAINT"><bean:message key="mailType.COMPLAINT" bundle="mailBundle"/></html:option>
                        <html:option value="CLAIM"><bean:message key="mailType.CLAIM" bundle="mailBundle"/></html:option>
                        <html:option value="GRATITUDE"><bean:message key="mailType.GRATITUDE" bundle="mailBundle"/></html:option>
                        <html:option value="IMPROVE"><bean:message key="mailType.IMPROVE" bundle="mailBundle"/></html:option>
                        <html:option value="OTHER"><bean:message key="mailType.OTHER" bundle="mailBundle"/></html:option>
                    </html:select>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.attached" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:select property="field(isAttach)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                        <html:option value="true"><bean:message key="label.yes" bundle="mailBundle"/></html:option>
                        <html:option value="false"><bean:message key="label.no" bundle="mailBundle"/></html:option>
                    </html:select>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.surName" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="field(userSurName)" maxlength="30"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.firstName" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="field(userFirstName)" maxlength="30"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.patrName" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="field(userPatrName)" maxlength="30"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.surNameEmpl" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="field(employeeSurName)" maxlength="30"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.firstNameEmpl" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="field(employeeFirstName)" maxlength="30"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.patrNameEmpl" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="field(employeePatrName)" maxlength="30"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.login" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="field(employeeLogin)" maxlength="30"/>
                </tiles:put>
            </tiles:insert>

            <script type="text/javascript">

                function clearField(name)
                {
                    var field = $("[name='field(" + name + ")']");
                    var defaultValue = $("[name='defaultParameter(" + name + ")']").val();
                    if (field.is('[type=checkbox]'))
                    {
                        if (field.val() == defaultValue)
                        {
                            field.attr('checked', 'checked');
                        }
                        else
                        {
                            field.removeAttr('checked');
                        }
                    }
                    else
                    {
                        field.val(defaultValue);
                    }
                }

                function clearData()
                {
                    clearField("folder");
                    clearField("fromPeriod");
                    clearField("toPeriod");
                    clearField("showUnArchivingMailToClient");
                    clearField("ANSWER");
                    clearField("READ");
                    clearField("DRAFT");
                    clearField("NEW_EPLOYEE_MAIL");
                    clearField("ANSWER_EPLOYEE_MAIL");
                    clearField("NONE");
                    clearField("subject");
                    clearField("type");
                    clearField("isAttach");
                    clearField("userSurName");
                    clearField("userFirstName");
                    clearField("userPatrName");
                    clearField("employeeSurName");
                    clearField("employeeFirstName");
                    clearField("employeePatrName");
                    clearField("employeeLogin");
                }
            </script>
	    </tiles:put>
	    <tiles:put name="buttons">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey"     value="button.clear"/>
                <tiles:put name="commandHelpKey" value="button.clear"/>
                <tiles:put name="bundle"  value="mailBundle"/>
                <tiles:put name="onclick" value="clearData()"/>
            </tiles:insert>
		    <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.unarchivate"/>
                <tiles:put name="commandHelpKey" value="button.unarchivate"/>
                <tiles:put name="bundle" value="mailBundle"/>
		    </tiles:insert>
	    </tiles:put>
    </tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>
