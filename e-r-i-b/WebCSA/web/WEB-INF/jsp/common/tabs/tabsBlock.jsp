<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:useAttribute name="id"/>
<div class="workspace-box greenTop withoutTop" id="${id}">
    <div class="top-content">
        <div class="secondMenuContainer tabs-names">
            <ul class="newSecondMenu newSecondMenu3">
                <tiles:useAttribute name="tabs"/>
                <c:set var="isInitHeadTab" value="true" scope="request"/>
                <logic:iterate id="tab"  indexId="index" collection="${tabs}">
                    <c:set var="isLast" value="${index == (fn:length(tabs) - 1)}"/>
                    <c:set var="isFirst" value="${index == 0}"/>
                    <c:set var="style" value="${isFirst ? 'grayFirst' : (isLast ? 'tabsGrayLast' : ' ')}"/>
                    <li class="${style}">
                        <span>
                            <jsp:include page="${tab}"/>
                            <a href="#${tabId}">${tabName}</a>
                        </span>
                    </li>
                </logic:iterate>
                <c:set var="isInitHeadTab" value="false" scope="request"/>
            </ul>
        </div>
    </div>
    <div class="clear"></div>

    <div class="greenTopRCL r-center-left">
        <div class="greenTopRCR r-center-right">
            <div class="greenTopRC r-content tabs-contents">
                <c:set var="isWriteContent" value="true" scope="request"/>
                <c:forEach var="tab" items="${tabs}">
                    <jsp:include page="${tab}"/>
                </c:forEach>
                <c:set var="isWriteContent" value="false" scope="request"/>
            </div>
        </div>
    </div>
    <div class="greenTopRBL r-bottom-left">
        <div class="greenTopRBR r-bottom-right">
            <div class="greenTopRBC r-bottom-center"></div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function callbackTabs(nameTab)
    {
        if ($(nameTab + " .bx-wrapper").length == 0)
            initSliders(nameTab + "_sliders");
    }

    $("#${id}").ready(function(){
        initTabs("${id}", callbackTabs);
    });
</script>