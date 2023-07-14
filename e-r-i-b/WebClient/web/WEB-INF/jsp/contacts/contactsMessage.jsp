<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/showContactsMessage">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="login">
        <tiles:put name="pageTitle"><bean:message key="application.title" bundle="commonBundle"/></tiles:put>
        <tiles:put name="headerGroup" value="true"/>
        <tiles:put type="string" name="data">
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="color" value="greenTop"/>
                <tiles:put name="title" value="Новые возможности"/>
                <tiles:put name="data" type="string">
                    <br/>
                    <span class="word-wrap">
                        ${phiz:processBBCode(form.message)}
                    </span>
                    <div class="buttonsArea">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.next"/>
                            <tiles:put name="commandTextKey" value="button.next"/>
                            <tiles:put name="commandHelpKey" value="button.next.help"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                        </tiles:insert>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

