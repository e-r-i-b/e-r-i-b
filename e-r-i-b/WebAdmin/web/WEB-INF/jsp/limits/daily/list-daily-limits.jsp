<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="globalImagePath"    value="${globalUrl}/images"/>
<c:set var="imagePath"          value="${skinUrl}/images"/>

<html:form action="/limits/general/list" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="limitsMain">
        <c:set var="channel">
            <c:choose>
                <c:when test="${param['channel'] == 'general'}">general</c:when>
                <c:when test="${param['channel'] == 'mobile'}">mobile</c:when>
                <c:when test="${param['channel'] == 'atm'}">atm</c:when>
                <c:when test="${param['channel'] == 'ermb'}">ermb</c:when>
                <c:when test="${param['channel'] == 'social'}">social</c:when>
            </c:choose>
        </c:set>
        <c:set var="form"       value="${phiz:currentForm(pageContext)}"/>
        <c:set var="simpleUrl"  value="/limits/${channel}/edit?departmentId=${form.departmentId}&channel=${channel}"/>
        <c:set var="baseUrl"    value="${phiz:calculateActionURL(pageContext, simpleUrl)}" scope="request"/>
        <c:set var="bundle"     value="limitsBundle" scope="request"/>

        <tiles:put name="submenu"   type="string" value="GeneralList/${channel}"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="page.general.list.name" bundle="${bundle}"/>
        </tiles:put>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" operation="EditLimitOperation" service="LimitsManagment" flush="false">
                <tiles:put name="commandTextKey"    value="button.add"/>
                <tiles:put name="commandHelpKey"    value="button.add.help"/>
                <tiles:put name="bundle"            value="${bundle}"/>
                <tiles:put name="action"            value="${simpleUrl}"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>

            <c:if test="${not empty form.data || not empty form.imsiLimit}">
                <tiles:insert definition="commandButton" flush="false" operation="RemoveLimitOperation" service="ConfirmLimitsManagment">
                    <tiles:put name="commandKey"     value="button.remove"/>
                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
                    <tiles:put name="bundle"         value="${bundle}"/>
                    <tiles:put name="validationFunction">
                         function()
                        {
                            var checkBoxList = $("form :checkbox:checked");
                            if(checkBoxList.size() < 1)
                                return groupError('�������� ���� �� ���� �����!');
                            return true;
                        }
                    </tiles:put>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>
        </tiles:put>

        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="general.label.typeLimit"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <span style="font-weight:normal;cursor:default;">
                        <html:select property="filter(limitType)">
                            <html:option value="">��� ���� �������</html:option>
                            <html:option value="OBSTRUCTION_FOR_AMOUNT_OPERATIONS">�������� ������������ �������������� ������</html:option>
                            <html:option value="IMSI">������ ��� IMSI</html:option>
                            <c:if test="${channel == 'ermb'}">
                                <html:option value="EXTERNAL_PHONE">����� �� ���������� ���� ��� ������ ������ ��������</html:option>
                                <html:option value="EXTERNAL_CARD">����� �� ���������� ���� ��� ��������� �� ����� �����</html:option>
                            </c:if>
                            <c:if test="${channel == 'mobile'}">
                                <html:option value="EXTERNAL_CARD">����� �� ���������� ��� ��������� �� ����� �����</html:option>
                            </c:if>
                        </html:select>
                    </span>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"     value="filter.period.creation.date"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <bean:message bundle="${bundle}" key="filter.creation.date.from"/>
                    &nbsp;
                    <span style="font-weight:normal;overflow:visible;cursor:default;">
                        <input type="text"
                               size="10" name="filter(fromCreationDate)" class="dot-date-pick"
                               value="<bean:write name="form" property="filter(fromCreationDate)" format="dd.MM.yyyy"/>"/>
                    </span>
                    &nbsp;<bean:message bundle="${bundle}" key="filter.creation.date.to"/>&nbsp;
                    <span style="font-weight:normal;cursor:default;">
                        <input type="text"
                               size="10" name="filter(toCreationDate)" class="dot-date-pick"
                               value="<bean:write name="form" property="filter(toCreationDate)" format="dd.MM.yyyy"/>"/>
                    </span>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"     value="filter.period.start.date"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <bean:message bundle="${bundle}" key="filter.start.date.from"/>
                    &nbsp;
                    <span style="font-weight:normal;overflow:visible;cursor:default;">
                        <input type="text"
                               size="10" name="filter(fromStartDate)" class="dot-date-pick"
                               value="<bean:write name="form" property="filter(fromStartDate)" format="dd.MM.yyyy"/>"/>
                    </span>
                    &nbsp;<bean:message bundle="${bundle}" key="filter.start.date.to"/>&nbsp;
                    <span style="font-weight:normal;cursor:default;">
                        <input type="text"
                               size="10" name="filter(toStartDate)" class="dot-date-pick"
                               value="<bean:write name="form" property="filter(toStartDate)" format="dd.MM.yyyy"/>"/>
                    </span>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"     value="filter.amount.daily"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name"      value="amount"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"     value="filter.status"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <span style="font-weight:normal;cursor:default;">
                        <html:select property="filter(status)">
                            <html:option value="">��� �������</html:option>
                            <html:option value="1">���������</html:option>
                            <html:option value="2">������</html:option>
                            <html:option value="3">�����</html:option>
                            <html:option value="4">��������</html:option>
                        </html:select>
                    </span>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <%--������ �������������� ������--%>
            <h3>
                <bean:message bundle="${bundle}" key="obstruction.title"/>
            </h3>
            <bean:message bundle="${bundle}" key="obstruction.text"/>

            <tiles:insert page="/WEB-INF/jsp/limits/daily/daily-limit.jsp" flush="false">
                <tiles:put name="items"             value="data"/>
                <tiles:put name="selectedBeanName"  value="selectedObstructionIds"/>
            </tiles:insert>

            <%--������ IMSI ������--%>
            <h3>
                <bean:message bundle="${bundle}" key="imsi.title"/>
            </h3>
            <bean:message bundle="${bundle}" key="imsi.text"/>

            <tiles:insert page="/WEB-INF/jsp/limits/daily/daily-limit.jsp" flush="false">
                <tiles:put name="items"             value="imsiLimit"/>
                <tiles:put name="selectedBeanName"  value="selectedIMSIIds"/>
            </tiles:insert>

            <c:if test="${channel == 'mobile'}">
                <%--������ ������� �� ���������� ��� ��������� �� ����� �����--%>
                <h3>
                    <bean:message bundle="${bundle}" key="mobile.external.card.title"/>
                </h3>
                <bean:message bundle="${bundle}" key="mobile.external.card.text"/>

                <tiles:insert page="/WEB-INF/jsp/limits/daily/daily-limit.jsp" flush="false">
                    <tiles:put name="items"             value="mobileExternalCardLimit"/>
                    <tiles:put name="selectedBeanName"  value="selectedMobileExtendedCardIds"/>
                </tiles:insert>
            </c:if>

            <c:if test="${channel == 'ermb'}">
                <%--������ ������� �� ���������� ���� ��� ������ ������ ��������--%>
                <h3>
                    <bean:message bundle="${bundle}" key="ermb.external.telephone.title"/>
                </h3>
                <bean:message bundle="${bundle}" key="ermb.external.telephone.text"/>

                <tiles:insert page="/WEB-INF/jsp/limits/daily/daily-limit.jsp" flush="false">
                    <tiles:put name="items"             value="ermbExternalTelephone"/>
                    <tiles:put name="selectedBeanName"  value="selectedERMBExternalTelephoneIds"/>
                </tiles:insert>

                <%--������ ������� �� ���������� ���� ��� ��������� �� ����� �����--%>
                <h3>
                    <bean:message bundle="${bundle}" key="ermb.external.card.title"/>
                </h3>
                <bean:message bundle="${bundle}" key="ermb.external.card.text"/>

                <tiles:insert page="/WEB-INF/jsp/limits/daily/daily-limit.jsp" flush="false">
                    <tiles:put name="items"             value="ermbExternalCard"/>
                    <tiles:put name="selectedBeanName"  value="selectedERMBExternalCardIds"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>