<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>

<%--
��������� ��� ����������� �������� ������������ � ������� �������
style - ����� ����� (����., redContainer, ����� - red)
data - ������, ��������� � �����
URL - ������ �� ������ ��������
text - ��������� ��� ��������� �����
title - ��������� �����
defaultCommandButon - ������ ��-��������� ��� ����� � ������ �����
--%>
<tiles:importAttribute/>

<c:if test="${empty userProfileSecurityCounter}">
    <c:set var="userProfileSecurityCounter" value="0" scope="request"/>
</c:if>

<c:set var="userProfileSecurityCounter" value="${userProfileSecurityCounter+1}"  scope="request"/>


<%

    Long userProfileSecurityCounter = (Long)request.getAttribute("userProfileSecurityCounter");
    String needOpen = request.getParameter("userProfileSecurityToggle"+userProfileSecurityCounter);
    needOpen = needOpen == null?"false":needOpen;
    
%>

<c:set var="needOpen" value="<%= needOpen %>"/>

<input type="hidden" name="userProfileSecurityToggle${userProfileSecurityCounter}" value="<c:out value='${needOpen}'/> "/>

<tiles:insert definition="interfaceInfoBlock">
    <tiles:put name="title" value="${title}"/>
    <tiles:put name="text" value="${text}"/>
    <c:choose>
        <c:when test="${(not empty data) && (empty URL)}">
            <tiles:put name="titleId" value="temp${userProfileSecurityCounter}"/>
            <tiles:put name="onClick">userProfileSecurityToggle(this, ${userProfileSecurityCounter}, '${style}');</tiles:put>
            <tiles:put name="aditionalData">
                <div class="openContainer" id="${style}Block${userProfileSecurityCounter}"
                     <c:if test="${not empty defaultCommandButon}">onclick="setDefaultCommandButon(findCommandButton('${defaultCommandButon}'));"</c:if> >
                    ${data}
                    <div class="clear"></div>
                </div>
            </tiles:put>
            <tiles:put name="style" value="opening ${style}"/>
        </c:when>
        <c:otherwise>
            <tiles:put name="style" value="${style}"/>
            <tiles:put name="URL" value="${URL}"/>
        </c:otherwise>
    </c:choose>
</tiles:insert>

<c:if test="${userProfileSecurityCounter == 1}">
    <script type="text/javascript">

        function findHeader(parent)
        {
            var result;
            $(parent).children().each(function() {
                if (this.tagName.toLowerCase() == "h1")
                {
                    //this.style.backgroundPosition = "100% -119px";
                    result = this;
                }
            });
            return result;
        }

        function userProfileSecurityToggle(obj, id, style)
        {
            var element = $("#"+style+"Block"+id);
            var eventEl = obj;
            var parenEventEl = eventEl.parentNode;
            var h1 = findHeader(obj);
            if (element.css('display') == "block")
            {
                element.hide();
                h1.style.backgroundPosition = "0 7px";
                getElement("userProfileSecurityToggle"+id).value = "false";
            }
            else
            {
                element.show();
                if(style == "id3")
                    resizeProductListDescription();
                h1.style.backgroundPosition = "0 -35px";
                getElement("userProfileSecurityToggle"+id).value = "true";
            }
        }
    </script>
</c:if>


<c:if test="${needOpen}">
    <script type="text/javascript">
        document.getElementById("temp${userProfileSecurityCounter}").onclick();
    </script>
</c:if>