<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute/>

<%--
��������� ��� ����������� ������� � ��������� �� �������

graphId - ������������� �������
productId - ������������� ������ ��������
x - ������� �������� (�� �������)
y - ������ �������� (�� �������)
title - ��������� ��������
image - ������ ���� � ������
--%>

<script type="text/javascript">
    $(document).ready(function(){
        <%-- ������ ���������� ������� ��������, �.�. ie6 �� ������������ �������� min-height, � ���� ������������ expression, �� �������� �������� --%>
        $('#${productId}').hover(function(){
            var textBlock = $(this).find('.lightGrayWithoutTopRC');
            textBlock.css('height', 'auto');
            if (textBlock.height() <= 90)
                textBlock.css('height', '90px');
        }, function() {
            $(this).find('.lightGrayWithoutTopRC').css('height', '90px');
        });

        productsGraphUtils.setProductOnGraphRowPosition('${graphId}', '${productId}', ${y});
        productsGraphUtils.setProductOnGraphColPosition('${graphId}', '${productId}', ${x});
    });
</script>

<c:set var="content">
    <div class="productOnGraphImg">
        <img src="${image}" border="0" alt="${title}"/>
    </div>
    <div class="productOnGraphTitle">${title}</div>
</c:set>
<c:set var="url" value="${fn:trim(url)}"/>

<div id="${productId}" class="productOnGraph" onclick="window.location = '${url}';">
    <tiles:insert definition="roundBorder" flush="false">
        <tiles:put name="color" value="lightGrayWithoutTop"/>
        <tiles:put name="data">${content}</tiles:put>
    </tiles:insert>
</div>