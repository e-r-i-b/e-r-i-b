<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<c:set var="form" value="${ListLocaleForm}"/>
<c:set var="url"/>
<c:choose>
    <c:when test="${form.isCSA == true}">
        <c:set var="url" value="/configure/locale/csa/load"/>
        <c:set var="listUrl" value="/configure/locale/csa/list"/>
        <c:set var="instance" value="CSA"/>
        <c:set var="submenuTab" value="LocalesCSA"/>
    </c:when>
    <c:otherwise>
        <c:set var="url" value="/configure/locale/load"/>
        <c:set var="editUrl" value="/configure/locale/list"/>
        <c:set var="instance" value=""/>
        <c:set var="submenuTab" value="Locales"/>
    </c:otherwise>
</c:choose>

<html:form action="${url}" onsubmit="return setEmptyAction(event);" enctype="multipart/form-data">
    <tiles:insert definition="localeEdit">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <%-- Пункт левого меню --%>
        <tiles:put name="submenu" type="string" value="${submenuTab}"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <html:hidden property="id"/>
                <tiles:put name="id" value="locale"/>
                <tiles:put name="name"><bean:message bundle="localeBundle" key="loadform.name"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="localeBundle" key="loadform.title"/></tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="localeBundle" key="button.load"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:file property="content" size="100"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" >
                        <tiles:put name="bundle" value="localeBundle"/>
                        <tiles:put name="commandKey" value="button.load"/>
                        <tiles:put name="commandHelpKey" value="button.load.help"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" >
                        <tiles:put name="bundle" value="localeBundle"/>
                        <tiles:put name="action" value="${editUrl}"/>
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    </tiles:insert>
                </tiles:put>

            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>