<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
    ��������� ��� ����������� ����� ������ ����� �/��� ��������

    title - ��������� �����
    currencyRateItems - ����� ������ ����� ������� �� ����������� "currencyRateTemplateItem"
    currencyRateBottomLink - ���� �� ����� ������ ������ (���� "currencyRateBottomLink" ������, �� ���� �� ������������)
    currencyRateBottomLinkTitle - ����� ����� �� ����� ������ ������, ������������ ������������ (���� "currencyRateBottomLink" ������, �� ���� �� ������������)
    metallRateItems - ����� ������ �������� ������� �� ����������� "currencyRateTemplateItem"
    metallRateBottomLink - ���� �� ����� �������/������� ������� (���� "metallRateItems" ������, �� ���� �� ������������)
    metallRateBottomLinkTitle - ����� ����� �� ����� �������/������� �������, ������������ ������������ (���� "metallRateItems" ������, �� ���� �� ������������)
--%>
<tiles:importAttribute/>
<tiles:insert definition="roundBorderLight" flush="false">
    <%--<tiles:put name="title" value="${title}"/>--%>
    <tiles:put name="color" value="grayBorder css3"/>
    <tiles:put name="data">
        <div class="grayTitle">
            <span>${title}</span>
        </div>
        <div class="clear"></div>
        <%--���� �����--%>
        <div class="currencyRateContainer">
            <div class="currencyRate">
                <div class="rateTitle">
                    <div class="rateText">&nbsp;</div>
                    <div class="rateText text-gray">�������</div>
                    <div class="rateText text-gray">�������</div>
                </div>
                ${currencyRateItems}
            </div>
            <c:if test="${not empty currencyRateBottomLink}">
                <div class="currencyRateBottomLink">
                    <a class="blueGrayLink courseLink" onclick="return redirectResolved();" href="${currencyRateBottomLink}">
                        ${currencyRateBottomLinkTitle}
                    </a>
                </div>
            </c:if>
        </div>
        <%--���� ��������--%>
        <div class="currencyRateContainer">
            <div class="currencyRate">
                <div class="rateTitle">
                    <div class="rateText">&nbsp;</div>
                    <div class="rateText text-gray">�������</div>
                    <div class="rateText text-gray">�������</div>
                </div>
                ${metallRateItems}
            </div>
            <c:if test="${not empty metallRateBottomLink}">
                <div class="currencyRateBottomLink currencyRateBottom">
                    <a class="blueGrayLink courseLink" onclick="return redirectResolved();" href="${metallRateBottomLink}">
                        ${metallRateBottomLinkTitle}
                    </a>
                </div>
            </c:if>
        </div>
        <div class="currencyRateDivider"></div>
        <%--�������������� ���������--%>
        <div class="currencyRateContainer">
            <div class="currencyRateFooter text-gray">
                � ������ ���������� �������� �������� ����� ����� ����������. � ���� ������ �� ����������� �������� ���.
            </div>
        </div>
    </tiles:put>
</tiles:insert>