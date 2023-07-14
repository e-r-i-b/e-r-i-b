<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/mail/subjects/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mailMain">
        <tiles:put name="submenu" type="string" value="MailSubjects"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="action" value="/mail/subjects/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message key="edit.subject.title" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="edit.subject.description" bundle="mailBundle"/>
                </tiles:put>

                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.subject.name" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(subject)" value="${form.fields.subject}" maxlength="50" size="50"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">        
                    <tiles:insert definition="commandButton" flush="false" operation="EditSubjectOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandTextKey" value="button.subject.save"/>
                        <tiles:put name="commandHelpKey" value="button.subject.save.help"/>
                        <tiles:put name="bundle"  value="mailBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>

        </tiles:put>

    </tiles:insert>

</html:form>