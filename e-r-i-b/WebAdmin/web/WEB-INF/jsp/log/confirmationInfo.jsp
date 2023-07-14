<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/log/confirmationInfo" onsubmit="return setEmptyAction(event)">
<c:set var="isAudit" value="${param['isAudit']}"/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="dictionary">

<tiles:put name="pageTitle" type="string">
    <c:choose>
        <c:when test="${isAudit == 'true'}">
            <bean:message key="label.detail.confirm.payment"  bundle="logBundle" />
        </c:when>
        <c:otherwise>
           	<bean:message key="label.detail.confirm.entry" bundle="logBundle"/>
        </c:otherwise>
    </c:choose>
</tiles:put>

<tiles:put name="menu" type="string">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.log.cancel"/>
        <tiles:put name="commandHelpKey" value="button.log.cancel"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="image" value=""/>
        <tiles:put name="onclick" value="window.close();"/>
        <tiles:put name="viewType" value="blueBorder"/>
    </tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
    <tiles:insert definition="tableTemplate" flush="false">
        <tiles:put name="text">
            <c:choose>
                <c:when test="${isAudit == 'true'}">
                    <bean:message key="label.attempt.confirm.payment"  bundle="logBundle" />
                </c:when>
                <c:otherwise>
                    <bean:message key="label.attempt.logon" bundle="logBundle"/>
                </c:otherwise>
            </c:choose>
        </tiles:put>
        <tiles:put name="grid" >

            <sl:collection id="listElement" model="list" property="data" bundle="logBundle" >
                <sl:collectionItem title="�" value="${lineNumber}"/>
                <c:set var="lineNumber" value="${lineNumber + 1}"/>
                <sl:collectionItem title="label.datetime">
                    <fmt:formatDate value="${listElement.date.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                </sl:collectionItem>
                <sl:collectionItem title="label.action">
                    <c:choose>
                        <c:when test="${listElement.state == 'CONF_SUCCESS' or listElement.state == 'CONF_FAILED' or listElement.state == 'CONF_TIMEOUT'}">
                            ���� ������������ ���� �������������
                        </c:when>
                        <c:when test="${listElement.state == 'INIT_FAILED' or listElement.state == 'INIT_SUCCESS'}">
                            <c:choose>
                                <c:when test="${listElement.type == 'SMS'}">
                                    �������� ��� � ����������� ����� �������������
                                </c:when>
                                <c:when test="${listElement.type == 'PUSH'}">
                                    �������� PUSH � ����������� ����� �������������
                                </c:when>
                                <c:when test="${listElement.type == 'CARD'}">
                                    ������������� ������������� ������� �������
                                </c:when>
                                <c:when test="${listElement.type == 'CAP'}">
                                    ������������� ������������� �AP-�����
                                </c:when>
                            </c:choose>
                        </c:when>
                        <%--
                        ��������� ������ �����
	SUCCESSFUL("�������"),
	TIMOUT("��������� ��������"),
	SYSTEM_ERROR("�� ������� ��������� SMS-������"),
	CARD_ERROR("��� ��������� ������� �������"),
	CAP_ERROR("�� ������� ������������ ������������� CAP �������"),
	CLIENT_ERROR("������������ ���� ������"),
	NEW_PASSW("������ �� ������"),
                        --%>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${listElement.type == 'SMS'}">
                                    ���-�������������
                                </c:when>
                                <c:when test="${listElement.type == 'PUSH'}">
                                    PUSH-�������������
                                </c:when>
                                <c:when test="${listElement.type == 'CARD'}">
                                    ������������� ������� �������
                                </c:when>
                                <c:when test="${listElement.type == 'CAP'}">
                                    ������������� �� �AP-�����
                                </c:when>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </sl:collectionItem>

                <sl:collectionItem title="label.description">
                    <c:choose>
                        <c:when test="${listElement.state == 'CONF_SUCCESS' or listElement.state == 'CONF_FAILED' or listElement.state == 'CONF_TIMEOUT'}">
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${listElement.type == 'SMS'}">
                                    ����� ��������: ${listElement.recipient}</br>
                                    �������� IMSI: ${listElement.checkIMSI}</br>
                                    ���������: "${listElement.message}"
                                </c:when>
                                <c:when test="${listElement.type == 'PUSH'}">
                                    ��������� �����������: ${listElement.recipient}</br>
                                    ���������: "${listElement.message}"
                                </c:when>
                                <c:when test="${listElement.type == 'CARD'}">
                                    ��� � ${listElement.cardNumber}, ������ � ${listElement.passwordNumber}
                                </c:when>
                                <c:when test="${listElement.type == 'CAP'}">
                                    ����� �����: ${listElement.recipient}
                                </c:when>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </sl:collectionItem>

                <sl:collectionItem title="label.entries.state">
                    <c:choose>
                        <c:when test="${listElement.state == 'SUCCESSFUL'}">
                            <c:choose>
                                <c:when test="${isAudit == 'true'}">
                                    ������������� �������
                                </c:when>
                                <c:otherwise>
                                    �������� ����
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            ${listElement.state.description}
                        </c:otherwise>
                    </c:choose>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
        <tiles:put name="emptyMessage"> <bean:message key="label.confirm.logon.empty"  bundle="logBundle" /></tiles:put>
    </tiles:insert>
</tiles:put>

</tiles:insert>
</html:form>