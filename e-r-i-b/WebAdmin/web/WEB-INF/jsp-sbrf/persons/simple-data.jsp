<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/autopayment/person/showInfo" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="autoSubscriptions">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:put name="submenu" type="string" value="GeneralInfo"/>
        <tiles:put name="menu" type="string">
            <%@ include file="/WEB-INF/jsp-sbrf/autopayments/resetClientInfoButton.jsp" %>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="showPersonInformationForm" flush="false">
                <tiles:put name="formName"><bean:message key="label.client.info.form.title" bundle="autopaymentsBundle"/></tiles:put>
                <tiles:put name="formDescription"><bean:message key="label.client.info.form.description" bundle="autopaymentsBundle"/></tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
