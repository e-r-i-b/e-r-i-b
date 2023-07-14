<%--
  User: bogdanov
  Date: 29.09.2013

  страница для пользователя, который зашел по логину iPas, у клиента есть регистрации в ЦСА и действует режим жесткой регистрации.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:set var="appName"><bean:message bundle="commonBundle" key="application.title"/></c:set>

    <tiles:insert definition="login">
        <tiles:put name="headerGroup" value="true"/>
        <tiles:put name="pageTitle" type="string" value="${appName}"/>
        <tiles:put type="string" name="data">
            <html:form action="/login/self-registration" styleClass="CheckOldPasswordForm" show="true" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();">
                <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
                <tiles:insert page="../common/layout/messages.jsp" flush="false">
                    <tiles:importAttribute name="messagesBundle" ignore="true"/>
                    <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
                    <c:set var="bundleName" value="${messagesBundle}"/>
                </tiles:insert>
                <br/>
                <tiles:insert definition="roundBorder" flush="false">
                    <tiles:put name="title"><bean:message bundle="commonBundle" key="text.SBOL.enter"/></tiles:put>
                    <tiles:put name="data">

                        <div class="title">
                            <h2>${form.titlePreRegistrationMessage}</h2>
                        </div>
                        
                        <div class="selfRegistrationHeader">
                            ${form.preRegistrationMessage}
                        </div>

                        <div class="buttonsArea">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey"    value="button.exit"/>
                                <tiles:put name="commandHelpKey"    value="button.exit.help"/>
                                <tiles:put name="bundle"            value="securityBundle"/>
                                <tiles:put name="action"            value="/logoff.do?toLogin=true"/>
                                <tiles:put name="isDefault"         value="true"/>
                            </tiles:insert>
                        </div>

                    </tiles:put>
                </tiles:insert>
            </html:form>
        </tiles:put>
    </tiles:insert>
