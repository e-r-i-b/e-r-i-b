<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
helpText - текст подсказки
helpTextOnclick  - функция, выполняющаяся при клике на подсказку
onClickHandler - js код, выполняющийся в дополнение к разворачиванию/сворачиванию пункта меню
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

<input type="hidden" name="userProfileSecurityToggle${userProfileSecurityCounter}" value="<c:out value='${needOpen}'/>"/>

<c:choose>
    <c:when test="${(not empty data) && (empty URL)}">
        <c:if test="${action !=''}">
            <c:set var="URL">${phiz:calculateActionURL(pageContext, action)}</c:set>
        </c:if>

        <c:set var="onClick">userProfileSecurityToggle(this, ${userProfileSecurityCounter}, '${style}');</c:set>
        <c:set var="aditionalData">
            <div class="openContainer" id="Block${userProfileSecurityCounter}"
                <c:if test="${not empty defaultCommandButon}">onclick="setDefaultCommandButon(findCommandButton('${defaultCommandButon}'));"</c:if> >
                <tiles:insert definition="roundBorder" flush="false">
                    <tiles:put name="color" value="greenShadow"/>
                    <tiles:put name="data">
                        <div class="newUserProfileSecurityTitleSeleted">
                            <a class="newUserProfileSecurityTitleSeletedText" onclick="${onClick}">${title}</a>
                            <c:if test="${text !=''}">
                                <div class="hint">${text}</div>
                            </c:if>
                            <c:if test="${helpText != ''}">
                                <div class="helpText">
                                    <a class="blueGrayLinkDotted" onclick="${helpTextOnclick}">${helpText}</a>
                                </div>
                            </c:if>
                        </div>

                        ${data}

                        <div class="clear"></div>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:set>
        <div class="newSettingsContainer">
            <div onclick="${onClick}${onClickHandler}" class="closeContainer" id="temp${userProfileSecurityCounter}" name="${nameForOpen}" >
                <h1>
                    <a class="greenLinkDotted">${title}</a>
                </h1>

                <c:if test="${text !=''}">
                    <div class="hint">${text}</div>
                </c:if>
            </div>
            <div class="innerOperationsCategiries">
                <div> ${aditionalData}</div>
            </div>
            <div class="clear"></div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="newSettingsContainer">
            <div class="closeContainer" id="temp${userProfileSecurityCounter}">
                <h1>
                    <a class="greenLinkDotted" href="${URL}">${title}</a>
                </h1>

                <div class="hint">${text}</div>
            </div>
            ${aditionalData}
        </div>
    </c:otherwise>
</c:choose>

<c:if test="${userProfileSecurityCounter == 1}">
    <script type="text/javascript">

        function userProfileSecurityToggle(obj, id, style)
        {
            var elements = $(".openContainer");
            for (i = 1; i <= elements.length; i++)
            {
                var element = $("#"+"Block"+i);
                var titleElement = $("#"+"temp"+i);
                if (element.css('display') == "block")
                {
                    titleElement.show();
                    element.hide();
                    getElement("userProfileSecurityToggle"+i).value = "false";
                }
                else if (i == id)
                {
                    element.show();
                    if(style == 'id3')
                        resizeProductListDescription();
                    titleElement.hide();
                    getElement("userProfileSecurityToggle"+i).value = "true";
                }
            }
        }
    </script>
</c:if>

<%--после перезагрузки страницы нужно открыть блок в котором что то меняли--%>
<c:if test="${needOpen}">
    <script type="text/javascript">
        $(document).ready(function()
        {
            document.getElementById("temp${userProfileSecurityCounter}").onclick();
        });
    </script>
</c:if>