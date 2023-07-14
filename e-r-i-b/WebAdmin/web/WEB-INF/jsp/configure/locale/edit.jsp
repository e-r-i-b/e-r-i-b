<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<c:set var="form" value="${EditLocaleForm}"/>
<c:set var="url"/>
<c:choose>
    <c:when test="${form.isCSA == true}">
        <c:set var="url" value="/configure/locale/csa/edit"/>
        <c:set var="submenuTab" value="LocalesCSA"/>
    </c:when>
    <c:otherwise>
        <c:set var="url" value="/configure/locale/edit"/>
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
                <tiles:put name="name"><bean:message bundle="localeBundle" key="editform.name"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="localeBundle" key="editform.title"/></tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="localeBundle" key="locale.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(name)" size="30"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="localeBundle" key="locale.id"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <c:set var="isOldLocale" value="${not empty form.fields.id}"/>
                            <c:choose>
                                <c:when test="${isOldLocale}">
                                    <input type="text" value="${form.fields.id}" disabled="disabled"/>
                                    <html:hidden property="field(id)"/>
                                </c:when>
                                <c:otherwise>
                                    <html:text property="field(id)" size="10"/>
                                </c:otherwise>
                            </c:choose>
                        </tiles:put>
                    </tiles:insert>

                    <div <c:if test="${form.isCSA}">class="displayNone"</c:if>>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="localeBundle" key="locale.flag"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false"/>
                        </tiles:put>
                    </tiles:insert>
                    </div>
                    <c:set var="isDefaultLocale" value="${phiz:isDefaultLocale(form.fields.id)}"/>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="localeBundle" key="locale.channels"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <b>
                                <html:checkbox property="field(eribAvailable)"   disabled="${isDefaultLocale}"/><bean:message bundle="localeBundle" key="locale.eribAvailable"/> <br>
                                <html:checkbox property="field(mapiAvailable)"   disabled="${isDefaultLocale}"/><bean:message bundle="localeBundle" key="locale.mapiAvailable"/> <br>
                                <html:checkbox property="field(atmApiAvailable)" disabled="${isDefaultLocale}"/><bean:message bundle="localeBundle" key="locale.atmApiAvailable"/> <br>
                                <html:checkbox property="field(webApiAvailable)" disabled="${isDefaultLocale}"/><bean:message bundle="localeBundle" key="locale.webApiAvailable"/> <br>
                                <html:checkbox property="field(ermbAvailable)"   disabled="${isDefaultLocale}"/><bean:message bundle="localeBundle" key="locale.ermbAvailable"/> <br>
                            </b>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="localeBundle" key="locale.state"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <b>
                                <html:radio value="ENABLED"  property="field(state)" disabled="${isDefaultLocale}"/><bean:message bundle="localeBundle" key="locale.state.ENABLED"/> <br>
                                <html:radio value="DISABLED" property="field(state)" disabled="${isDefaultLocale}"/><bean:message bundle="localeBundle" key="locale.state.DISABLED"/><br>
                                <c:if test="${isDefaultLocale}">
                                    <html:hidden property="field(state)"/>
                                </c:if>
                            </b>
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" >
                        <tiles:put name="bundle" value="localeBundle"/>
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" >
                        <tiles:put name="bundle" value="localeBundle"/>
                        <tiles:put name="action" value="/configure/locale/list.do"/>
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    </tiles:insert>
                </tiles:put>

            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>