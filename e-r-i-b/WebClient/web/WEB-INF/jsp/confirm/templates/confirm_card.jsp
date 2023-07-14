<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:if test="${not confirmRequest.error}">
<div id="paymentForm" onkeypress="onEnterKey(event);">
<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title">
        <c:choose>
            <c:when test="${confirmableObject == 'login'}">
                ��� ������������� ����� ������� ������ �
            </c:when>
           <c:when test="${confirmableObject == 'securitySettings'}">
                ��� ������������� �������� ������������ ������� ������ �
            </c:when>
            <c:when test="${confirmableObject == 'personalSettings'}">
                ��� ������������� ������������ ���������� ������� ������ �
            </c:when>
            <c:when test="${confirmableObject == 'viewSettings'}">
                 ��� ������������� �������� ����������
            </c:when>
            <c:when test="${confirmableObject == 'activePerson'}">
                 ��� ���������� ��������  ������� ������ �
            </c:when>
            <c:otherwise>
                ��� ������������� ������� ������� ������ �
            </c:otherwise>
        </c:choose>
				<c:out value="${confirmRequest.passwordNumber}"/>
				� ���� �
				<c:out value="${confirmRequest.cardNumber}"/>
    </tiles:put>
    <tiles:put name="isNecessary" value="true"/>
    <tiles:put name="data">
        <input type="text" name="$$confirmCardPassword" maxlength="10"/>
    </tiles:put>

    <c:choose>
            <c:when test="${confirmableObject == 'login'}">
              <c:set var="confirmText" value="�����"/>
            </c:when>
            <c:otherwise>
              <c:set var="confirmText" value="�����������"/>
            </c:otherwise>
    </c:choose>

    <tiles:put name="description">������� ������ � ��� ���� � ������� ������ �${confirmText}�
        <c:if test="${anotherStrategy}">
            ����� �� ������ ������������ ��� �������������
            <div class="simple">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.changeToSMS"/>
                    <tiles:put name="commandTextKey" value="button.changeToSMS"/>
                    <tiles:put name="commandHelpKey" value="button.changeToSMS.help"/>
                    <tiles:put name="viewType" value="simpleLink"/>
                    <tiles:put name="bundle" value="securityBundle"/>
                </tiles:insert>
            </div>
        </c:if>

    </tiles:put>
</tiles:insert>
<script type="text/javascript">
    function setFocus()
    {
        getElement("$$confirmCardPassword").focus();
    }
    doOnLoad(setFocus);
</script>
</c:if>
<c:if test="${confirmRequest.error}">
            <div class="error"><c:out value="${confirmRequest.errorMessage}"/></div>
</c:if>
</div>