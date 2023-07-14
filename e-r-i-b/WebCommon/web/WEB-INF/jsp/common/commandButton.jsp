<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<%--
��� �������� ������.
- ��� ������� �� ������ ���������� validationFunction (���� �����������)
- ���� ��������� ������ ������� ������������ confirmText (���� ����������)
- ���� ������������ ���������� ���������� ������� �� ������ ������������ ������

	bundle              	- bundle � ������� ������ ����� ��� commandTextKey � commandHelpKey
	commandKey          	- ���� �������
	commandHelpKey      	- ���� ��� �������� �������
	commandTextKey      	- ���� ��� ��������� �������, ���� �� ������, ������������ commandKey
	image              	 	- �������� ����� � ���������
							  !!!��� buttonMenu ${globalUrl} � ${skinUrl} �� �������������, �� ��� �������� � �� ����� todo: ��������� �� �������
    imageUrl                - ������ ���� � ��������
	imageHover              - ���������� ��� ������ ������ � ������ ��� ���������
	imagePosition           - ��������� ��������
	validationFunction  	- ������� ���������
	confirmText         	- ����� ������������� ���������� �������
	isDefault             	- true/false ������ �� ��������� (�� �������� ����������� �� ������� Enter)
	postbackNavigation  	- ��� �������� �� ������� (� ����� ����) ����� ���������� �������� ���� ������
	width					- ������ ������, ���� �� ������� ������������ �������� ������
    typeBtn                 - ��� ������, ����� ���� defaultBtn, disableBtn, dictBtn
    autoRefresh             - true/false ������������� ��������������� ���������� �������� ����� ������ ����� ������� �� ������
    viewType                - ��� ����������� ������. �� ��������� buttonGreen. ��������� ��������: buttonGrey, simpleLink, boldLink, LoginButton, icon, uppercaseType. �� ��������� buttonGreen.
    enabled                 - ������� �� ������ (true - ������ �������, false - �� �������, ��� ������� ������ �� ����������)
    action                  - ����� �� ������� ���� ������� ������
    noPreLoader             - true - �� ���� ���������� ��������� ����� �������
    fromBanner              - ���� == <�����>, �� ��� ������� �� ������ ���������� ��������� ������ �� ���������� ������ � ������ ���������� �� �������� � ���������� �������� ��� �������
--%>

<c:set var="show" value="true"/>

<c:if test="${not empty imageHover}">
    <script type="text/javascript">
        <c:set var="htmlImageOver">
           onmouseenter="$(this).find('img').attr('src','${imageHover}');"
           <c:choose>
               <c:when test="${not empty image}">
                    onmouseleave="$(this).find('img').attr('src','${imagePath}/${image}');"
               </c:when>
               <c:when test="${not empty imageUrl}">
                    onmouseleave="$(this).find('img').attr('src','${imageUrl}');"
               </c:when>
           </c:choose>
        </c:set>
    </script>
</c:if>

<c:if test="${(not empty stateObject)}">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<bean:define id="object" name="form" property="${stateObject}"/>
	<c:choose>
		<c:when test="${phiz:checkEvent(object, commandKey)}">
			<c:set var="show" value="true"/>
		</c:when>
		<c:otherwise>
			<c:set var="show" value="false"/>
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="${show}">

<c:set var="contextName" value="${phiz:loginContextName()}"/>

<c:if test="${empty commandTextKey}">
    <c:set var="commandTextKey" value="${commandKey}"/>
</c:if>

<c:set var="commandText"><bean:message key="${commandTextKey}" bundle="${bundle}"/></c:set>
<c:set var="commandHelp"><bean:message key="${commandHelpKey}" bundle="${bundle}"/></c:set>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<script type="text/javascript">
    if(window.createCommandButton != undefined)
    {
        var button = createCommandButton('${commandKey}', '${commandText}');
        <c:if test="${isDefault}">setDefaultCommandButon(button);</c:if>
        <c:if test="${postbackNavigation}">setPostbackNavigationButton(button);</c:if>
        <c:if test="${not empty validationFunction}">button.setValidationFunction(
            function()
            {
                var r = ${validationFunction};
                return (typeof r == 'function') ? r() : r;
            });
        </c:if>
        <c:if test="${not empty confirmText}">button.setConfirmText('${confirmText}');</c:if>
        <c:if test="${not empty autoRefresh}">button.setAutoRefresh(${autoRefresh});</c:if>
    }
</script>

    <c:if test="${isDefault}"><c:set var="typeBtn" value="defaultBtn"/></c:if>

    <c:set var="onClickAction" value=""/>

    <c:if test="${enabled}">
        <c:set var="onClickAction">setTimeout(function() {findCommandButton('${commandKey}').click('', ${noPreLoader})}, getCommandButtonDelay())</c:set>

        <c:if test="${action!=''}">
            <c:set var="actionUrl" value="${phiz:calculateActionURL(pageContext,action)}"/>
            <c:set var="onClickAction">changeFormAction('${actionUrl}');  ${onClickAction}</c:set>
        </c:if>
        <c:if test="${not empty fromBanner}">
            <c:set var="onClickAction">bannerRequest(${fromBanner}, 'operation');  ${onClickAction}</c:set>
        </c:if>
    </c:if>

    <c:choose>
        <c:when test="${contextName == 'PhizIC' or contextName == 'WebPFP'}">
            <c:choose>
                <c:when test="${viewType == 'icon'}">
                    <img src="${imagePath}/${image}" alt="${commandHelp}" title="${commandText}"
                         class="iconButton" onclick="${onClickAction}"/>
                </c:when>
                <c:otherwise>
                    <div ${htmlImageOver} class="commandButton <c:if test="${not empty imageHover}">imageAndButton</c:if>"
                                          <c:if test="${not empty id}">id=${id}</c:if>>
                        <div onclick="${onClickAction}" class="${viewType} <c:if test="${not enabled}">disabled</c:if>">
                            <a>
                                <div class="left-corner">
                                    <c:if test="${not empty imageUrl && imagePosition=='left'}">
                                        <img src="${imageUrl}" alt="${commandHelp}" title="${commandHelp}"/>
                                    </c:if>
                                </div>
                                <div class="text" <c:if test="${not empty image}">style="padding-right: 5px;"</c:if>>
                                    <c:choose>
                                        <c:when test="${empty commandTextKey}">
                                            <span>${commandText}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span><bean:message key="${commandTextKey}" bundle="${bundle}"/></span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="right-corner">
                                    <c:if test="${not empty image && imagePosition=='right'}">
                                        <img style="margin-left: 5px;" src="${imagePath}/${image}" alt="${commandHelp}">
                                    </c:if>
                                </div>
                            </a>
                        </div>
                        <div class="clear"></div>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:when>

        <c:when test="${contextName == 'PhizIA' or contextName == 'CSA'}">
            <c:choose>
                <c:when test="${viewType == 'icon'}">
                    <img src="${imagePath}/${image}" alt="${commandHelp}" title="${commandText}"
                         class="iconButton" onclick="${onClickAction}"/>
                </c:when>
                <c:otherwise>
                    <div class="commandButton" <c:if test="${not empty id}">id=${id}</c:if>>
                        <div onclick="${onClickAction}" class="${viewType} <c:if test="${not enabled}">disabled</c:if>">
                            <div class="left-corner"></div>
                            <div class="text" <c:if test="${not empty image}">style="padding-right: 5px;"</c:if>>
                                <c:if test="${not empty image}">
                                    <img src="${imagePath}/${image}" alt="${commandHelp}" width="12px" height="12px">&nbsp;
                                </c:if>
                                <c:choose>
                                    <c:when test="${empty commandTextKey}">
                                        <span>${commandText}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span><bean:message key="${commandTextKey}" bundle="${bundle}"/></span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="right-corner"></div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>
</c:if>