<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%--
    showHelpLink    - ������� ������������ ����������� ������ "������"
--%>

<html:html>
    <%@ include file="/WEB-INF/jsp/common/html-head.jsp"%>
    <tiles:useAttribute name="needRegionSelector"/>
    <c:set var="needRegionSelector" value="${needRegionSelector && $$show_region_functionality}"/>
    <head>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/KeyboardUtils.js"></script>
    </head>
    <body>
        <tiles:insert definition="googleTagManager"/>
        <%-- ����� --%>
        <div id="workspaceCSA">
        <jsp:include page="header.jsp"/>
        <div class="head-info" id="head-info">
            <tiles:insert attribute="headinfo"/>
        </div>

        <%-- �������� ����� --%>
        <div id="wrapper">
            <div id="loading" style="left:-3300px;">
                <div id="loadingImg"><img src="${skinUrl}/skins/sbrf/images/ajax-loader64.gif"/></div>
                <span>����������, ���������,<br/> ��� ������ ��������������.</span>
            </div>
            <tiles:insert attribute="data"/>
        </div>
        </div>


        <tiles:useAttribute name="footer"/>
        <c:if test="${not empty footer}">
            <%-- ������ ���������� --%>
            <div id="footer">
                <tiles:insert attribute="footer"/>
            </div>
        </c:if>
    </body>
    <script type="text/javascript">
        $(document).ready(function(){
            // �������������� ���� "��������"
            clientBeforeUnload.init();
        });

        initCSA("${globalUrl}","${skinUrl}");

    </script>
</html:html>
