<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/autotransfer/claims" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="fromStart" value="${form.fromStart}"/>

<tiles:insert definition="autoSubscriptions">
<tiles:put name="submenu" type="string" value="AutoTransfersClaims"/>
<tiles:put name="pageTitle" type="string" value="Ñïèñîê çàÿâîê"/>  
<tiles:put name="menu" type="string">
    <c:if test="${phiz:impliesServiceRigid('EmployeeCreateP2PAutoTransferClaim')}">
        <tiles:insert definition="clientButton" flush="false" service="EmployeeCreateP2PAutoTransferClaim">
            <tiles:put name="commandTextKey"    value="button.addTransfer"/>
            <tiles:put name="commandHelpKey"    value="button.addTransfer.help"/>
            <tiles:put name="bundle"            value="autopaymentsBundle"/>
            <tiles:put name="action"            value="/private/payments/edit.do?form=CreateP2PAutoTransferClaim"/>
            <tiles:put name="viewType"          value="blueBorder"/>
        </tiles:insert>
    </c:if>
    <%@ include file="/WEB-INF/jsp-sbrf/autopayments/resetClientInfoButton.jsp" %>
</tiles:put>

<tiles:put name="filter" type="string">
        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label"     value="filter.autoPay.name"/>
            <tiles:put name="bundle"    value="autopaymentsBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="name"      value="name"/>
            <tiles:put name="maxlength" value="20"/>
        </tiles:insert>
        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label"     value="filter.period.creation.dates"/>
            <tiles:put name="bundle"    value="autopaymentsBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="data">
                <bean:message bundle="autopaymentsBundle" key="filter.creation.date.from"/>
                &nbsp;
                <span style="font-weight:normal;overflow:visible;cursor:default;">
                    <input type="text"
                            size="10" name="filter(fromDate)" class="dot-date-pick"
                            value="<bean:write name="form" property="filter(fromDate)" format="dd.MM.yyyy"/>"/>
                </span>
                &nbsp;<bean:message bundle="autopaymentsBundle" key="filter.creation.date.to"/>&nbsp;
                <span style="font-weight:normal;cursor:default;">
                    <input type="text"
                            size="10" name="filter(toDate)" class="dot-date-pick"
                            value="<bean:write name="form" property="filter(toDate)" format="dd.MM.yyyy"/>"/>
                </span>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label"     value="filter.receiver"/>
            <tiles:put name="bundle"    value="autopaymentsBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="name"      value="receiver"/>
            <tiles:put name="maxlength" value="160"/>
        </tiles:insert>
        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label"     value="filter.stateClaim"/>
            <tiles:put name="bundle"    value="autopaymentsBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="data">
                   <html:select property="filter(state)">
                       <html:option value="ALL">Âñå</html:option>
                       <html:option value="INITIAL_LONG_OFFER,DRAFT,INITIAL"><bean:message key="documentState.INITIAL_LONG_OFFER" bundle="autopaymentsBundle"/></html:option>
                       <html:option value="SAVED"><bean:message key="documentState.SAVED" bundle="autopaymentsBundle"/></html:option>
                       <html:option value="EXECUTED"><bean:message key="documentState.EXECUTED" bundle="autopaymentsBundle"/></html:option>
                       <html:option value="REFUSED"><bean:message key="documentState.REFUSED" bundle="autopaymentsBundle"/></html:option>
                       <html:option value="ERROR,UNKNOW"><bean:message key="documentState.ERROR" bundle="autopaymentsBundle"/></html:option>
                       <html:option value="DISPATCHED"><bean:message key="documentState.DISPATCHED" bundle="autopaymentsBundle"/></html:option>
                   </html:select>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label"     value="filter.claimType"/>
            <tiles:put name="bundle"    value="autopaymentsBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="data">
                   <html:select property="filter(ñlaimType)">
                       <html:option value="CA,EA,DC,RC,CC">Âñå</html:option>
                        <html:option value="CA"><bean:message bundle="autopaymentsBundle" key="claimType.CREATE"/></html:option>
                        <html:option value="EA"><bean:message bundle="autopaymentsBundle" key="claimType.EDIT"/></html:option>
                        <html:option value="DC"><bean:message bundle="autopaymentsBundle" key="claimType.DELAY"/></html:option>
                        <html:option value="RC"><bean:message bundle="autopaymentsBundle" key="claimType.RECOVERY"/></html:option>
                        <html:option value="CC"><bean:message bundle="autopaymentsBundle" key="claimType.CLOSE"/></html:option>
                   </html:select>
            </tiles:put>
        </tiles:insert>
    </tiles:put>

<tiles:put name="data" type="string">
    <%@ include file="/WEB-INF/jsp-sbrf/autopayments/list-autosubscription-claims-data.jsp" %>
</tiles:put>
</tiles:insert>
</html:form>
