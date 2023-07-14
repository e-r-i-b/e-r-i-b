<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>

 <c:set var="globalImagePath" value="${globalUrl}/images"/>

<tiles:insert definition="printDoc">
	<tiles:put name="pageTitle" value="../../sbrfHeader.jsp"/>
    <tiles:put name="data" type="string">
        <html:form action="/private/accounts/print" show="true">
        <c:set var="form" value="${PrintAccountAbstractForm}"/>
        <c:set var="accountAbstract" value="${form.accountAbstract}"/>
        <c:set var="cardAccountAbstract" value="${form.cardAccountAbstract}"/>

        <c:choose>
            <c:when test="${!form.copying}">
                <c:choose>
                    <c:when test="${empty accountAbstract and empty cardAccountAbstract
                                      and accountAbstract != null and cardAccountAbstract != null}">
                        <table width="100%">
                            <tr>
                                <td align="center" class="messageTab"><br>
                                    ��&nbsp;�������&nbsp;��&nbsp;������&nbsp;�����,<br>
                                    ����������������&nbsp;���������&nbsp;�������!
                                </td>
                            </tr>
                        </table>
                    </c:when>
                    <c:otherwise>

                        <c:forEach items="${accountAbstract}" var="listElement">
                            <c:set var="accountLink" value="${listElement.key}"/>
                            <c:set var="account" value="${accountLink.value}"/>
                            <c:set var="resourceAbstract" value="${listElement.value}"/>
                            <c:set var="accountBalans" value="${fn:substring(account.number, 0,5)}"/>
                            <c:set var="office" value="${accountLink.office}"/>
                            <%--
                            ������ ������� ����� ���������� ������ ��� ��������� ������(������ � �����) � �����!!!
                            �������� ������(������) [42301-42307, 42601-42507],
                            �������� ������(�����) [40817 � 40820, ���� ��� �� �������� ���, �.�. � ��� �� ��������� �����]
                            --%>
                            <c:choose>
                                <c:when test="${accountBalans != '40817' && accountBalans != '40820'}">
                                     <%--������� �� �������� ����� �� ������ --%>
                                    <%@ include file="/WEB-INF/jsp-sbrf/abstr/abstractDepositPrintForm.jsp"%>
                                </c:when>
                                <c:otherwise>
                                    <%-- ������� �� �������� ����� --%>
                                    <%@ include file="/WEB-INF/jsp-sbrf/abstr/abstractAccountPrintForm.jsp"%>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:forEach items="${cardAccountAbstract}" var="listElement">
                            <c:set var="accountLink" value="${listElement.key}"/>
                            <c:set var="account" value="${accountLink.account}"/>
                            <c:set var="resourceAbstract" value="${listElement.value}"/>
                            <c:set var="office" value="${accountLink.office}"/>

                            <%@ include file="/WEB-INF/jsp-sbrf/abstr/abstractAccountPrintForm.jsp"%>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <%@ include file="/WEB-INF/jsp-sbrf/abstr/information.jsp"%>
            </c:otherwise>
        </c:choose>
        </html:form>
	</tiles:put>
</tiles:insert>

<script type="text/javascript">
    doOnLoad(function()
    {
        var ExtendedAbstractWindowOpened = window.opener.document.getElementById('ExtendedAbstractWindowOpened');
        if (ExtendedAbstractWindowOpened)
            ExtendedAbstractWindowOpened.value = '0';
    });
</script>