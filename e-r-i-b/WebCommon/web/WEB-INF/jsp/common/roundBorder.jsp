<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%--
    ��������� ��� ����������� �����
    data - ������
    info - �������������� ��������� ������������
    title - ��������� �����
    contentTitle - ��������� ����� � �������� (��� �������������)
    control - ������� ������ ���� ���������� � ��������
    color - ����(�����) �����������
        �������� ���������  �����:
            lightGreen - ������ ������� ������������ � ��������
            greenTop - ����� � ������� ������� ������ � ������ ��� ���������
            greenBold - ������ ������� ����� � �����
            gray - �����
            lightGray - ������ ����� ����� ��� �����������. ������������ ��� ����������� ������
            greenGradient - ����� �����, ������ ����� ���������� � ������� �������� (���� ������ � ������ ����)
            green - ������ ������� ����� (�� ������������, ��� ������� ����� ������� �������� � ����� �� roundBorder.css)
            shadow - ����������� � �����
            sTable - ����� ����������� ��� ������� � ���������� �� ������ ��������� (�� ������������, ��� ������� ����� ������� �������� � ����� �� roundBorder.css)
            red - ������� ������� ����� (�� ������������, ��� ������� ����� ������� �������� � ����� �� roundBorder.css)
            orange - ��������� ������� �����. (�� ������������, ��� ������� ����� ������� �������� � ����� �� roundBorder.css)
            whiteTop - ����� � ����� ������� ������ � ������ ��� ���������
            orangeTop - ����� � ��������� ������� ������ � ������ ��� ���������
            whiteAndGray - ������-����� ����� ��� �������
            creamyTop - �������� ����� ��� ������� ����
            hoar - ����� ����� ��� ����� ��������

    ����������: ��� ��� IE6 �� ������������ ������� CSS ������ (class1.class2) �� ��� ����
                ����� ����� ����� ������������ ���� � ����� ����������� �������� ����������� ���� ${color}RTL.
                ������ ���� r-top ���������� ��� �������� ����� ������������� ���� ������������ ������.
                ���������� ���� RT r-top ���������������� ��� (round top).
    outerData - ������ �� �������� �����
    rightDataArea - ������ � ������ �������

--%>

<tiles:importAttribute/>

<c:if test="${color == ''}">
    <c:set var="color" value=""/>
</c:if>

<%-- ����������� title ��� �������� � ��������� ������� --%>
<c:if test="${not empty title}">
    <c:set var="title">
        <div class="${color}Title" <c:if test="${!empty controlLeft or !empty control}">style="float:left;"</c:if>>
            <span>${title}</span>
        </div>
    </c:set>
</c:if>


<c:if test="${(color == 'greenTop' || color == 'whiteTop' || color == 'orangeTop' || color == 'gray' || color == 'greenGradient' || color == 'creamyTop'  || color == '') && !empty control}">
    <c:set var="title">
        ${title}
        <div class="${color}CBT">${control}</div>
        <div class="clear"></div>
    </c:set>
</c:if>


<c:if test="${!empty controlLeft}">
    <c:set var="title">
        ${title}
        <div class="${color}CTL">${controlLeft}</div>
        <div class="clear"></div>
    </c:set>
</c:if>

<c:if test="${!empty contentTitle}">
    <c:set var="contentTitle">
        <span class="contentTitle size24">
            ${contentTitle}
        </span>
        <div class="clear"></div>
    </c:set>
</c:if>

<c:if test="${not empty rightDataArea}">
    <div class="rightDataArea">
        ${rightDataArea}
    </div>
</c:if>

<div class="workspace-box ${color} <c:if test="${not empty rightDataArea}">float</c:if>" <c:if test="${not empty style}">style="${style}"</c:if>>
    <c:set var="blinking" value=""/>
    <c:if test="${color == 'orangeTop'}">
        <c:set var="blinking" value="blinking"/>
        <div class="whiteTopRT blinking">
            <div class="whiteTopRTL r-top-left"><div class="whiteTopRTR r-top-right"><div class="whiteTopRTC r-top-center"></div></div></div>
        </div>
    </c:if>

    <div class="${color}RT r-top ${blinking}">
        <div class="${color}RTL r-top-left"><div class="${color}RTR r-top-right"><div class="${color}RTC r-top-center">
            ${title}
        <div class="clear"></div>
        </div></div></div>
    </div>
    <div class="${color}RCL r-center-left">
        <div class="${color}RCR r-center-right">
            <div class="${color}RC r-content">
                <div class="r-content-data">
                    ${contentTitle}
                    ${data}
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="${color}RBL r-bottom-left">
        <div class="${color}RBR r-bottom-right">
            <div class="${color}RBC r-bottom-center"></div>
        </div>
     </div>
</div>

<div class="clear"></div>
<c:if test="${not empty outerData}">
    <div class="outerData">
        ${outerData}
    </div>
</c:if>