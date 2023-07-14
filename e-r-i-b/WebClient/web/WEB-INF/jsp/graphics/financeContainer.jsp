<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--

    ���������� ��� "������ ��������"

    data - ����, ������ ���������� ��������
    title - ��������� ��������

    infoText - ��������� � �������� ������� �������� �� ��������� �� ����������
    infoIco - ������ � ���� ������, ������ ������ � CommonSkin!!!

    page - ������������ �������� myFinance ��� structure
    showTitle - �������� ����������� ��������� ��������
    showFavourite - �������� ����������� ����� ����� ��� ���������� � ���������.
                        ��� showFavourite = false ������ "�������� � ���������" ������������ �� �����
                        �� ��������� showFavourite = true
--%>


<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>
<c:set var="clientPfpEditServiceAvailable" value="${phiz:hasAccessToPFP()}"/>
<div class="clear"></div>

<c:if test="${showTitle && title != ''}">
    <h2 class="financeConteinerTitle">${title}</h2>
    <div class="clear"></div>    
</c:if>
<c:if test="${infoText != ''}">
    <table class="paymentHeader">
        <tr>
            <c:if test="${infoIco!=''}">
                <td>
                    <img class="icon" src="${image}/${infoIco}" alt=""/>
                </td>
            </c:if>
            <td>
                <h3>
                    <p>
                        ${infoText}
                    </p>
                </h3>
            </td>
        </tr>
    </table>

</c:if>

<c:if test="${showFavourite && title != ''}">
    <tiles:insert definition="addToFavouriteButton" flush="false">
        <tiles:put name="formName"><c:out value="${title}"/></tiles:put>
    </tiles:insert>

</c:if>
${data}
<br/>
<c:catch>
    <c:if test="${not empty form.lastModified}" >
        <br/>
        <span class="asterisk">*</span>��� ���������� �������� ������������ ������ �� ���������, ����������� ��
        <bean:write name='form' property="lastModified.time" format="dd.MM.yyyy" ignore="true"/>.
    </c:if>
</c:catch>
<div class="clear"></div>
