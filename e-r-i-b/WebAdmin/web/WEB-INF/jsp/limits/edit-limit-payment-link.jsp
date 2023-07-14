<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/departments/groupsRiskPersonalTransfer" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="departmentsEdit">
<tiles:put name="submenu" type="string" value="GroupsRisk"/>

<tiles:put name="data" type="string">
    <tiles:insert definition="paymentForm" flush="false">
	<tiles:put name="name" value="������ ����� �� ���������"/>
        <tiles:put name="description"
                   value="�� ������ ��������� �� ������ ���������� ����� ��� ���������� ��������, ������� ����� ��������� ������. ��� ����� ��������� ���� ����� � ������� ������ �����������."/>
    <tiles:put name="data">

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                �� ���� � ������ �����
            </tiles:put>
            <tiles:put name="needMargin" value="true"/>
            <tiles:put name="data">
                <c:set var="selectProperty" value="phizicalExternalAccountPayment"/>
                <%@ include file="/WEB-INF/jsp/limits/group/group-risk-selector.jsp" %>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                �� ����� � ������ �����
            </tiles:put>
            <tiles:put name="needMargin" value="true"/>
            <tiles:put name="data">
                <c:set var="selectProperty" value="phizicalExternalCardPayment"/>
                <%@ include file="/WEB-INF/jsp/limits/group/group-risk-selector.jsp" %>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                �� ����� � ���������, �� ���� � ���������, �� ����� � ��������� �� ������ ��������
            </tiles:put>
            <tiles:put name="needMargin" value="true"/>
            <tiles:put name="data">
                <c:set var="selectProperty" value="phizicalInternalPayment"/>
                <%@ include file="/WEB-INF/jsp/limits/group/group-risk-selector.jsp" %>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                ����� � ���. �����
            </tiles:put>
            <tiles:put name="needMargin" value="true"/>
            <tiles:put name="data">
                <c:set var="selectProperty" value="internalSocialPayment"/>
                <%@ include file="/WEB-INF/jsp/limits/group/group-risk-selector.jsp" %>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                ������������� �������� �� ����\����� �����
            </tiles:put>
            <tiles:put name="needMargin" value="true"/>
            <tiles:put name="data">
                <c:set var="selectProperty" value="conversionOperation"/>
                <%@ include file="/WEB-INF/jsp/limits/group/group-risk-selector.jsp" %>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                ������� ��. ���� �� ��������� ����������
            </tiles:put>
            <tiles:put name="needMargin" value="true"/>
            <tiles:put name="data">
                <c:set var="selectProperty" value="juridicalPayment"/>
                <%@ include file="/WEB-INF/jsp/limits/group/group-risk-selector.jsp" %>
            </tiles:put>
        </tiles:insert>

        </tiles:put>
        <tiles:put name="buttons">
           <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="newsBundle"/>
                <tiles:put name="postbackNavigation"    value="true"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</tiles:put>
</tiles:insert>

</html:form>
