<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 08.09.2008
  Time: 14:57:45
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<%--
¬кладка главного меню.

	activity   - признак активности вкладки
	module, s  -  дл€ определени€ доступа (показываем вкладку или нет)
	action     - struts action куда осуществл€етс€ переход
	text       - название вкладки
	title      - текст подсказки к названию вкладки
	insetWidth - ширина вкладки, если требуетс€ задать точное значение
	isImplement- реализован функционал или нет, если нет - выводим об этом сообщение, если не требуетс€ скрывать вкладку
	image      - путь к изображению (необходимо дл€ вкладки "Ќастройки")
	positionItem - позици€ пункта меню
	novelty - отображать иконку новинка
	enabledLink - доступность перехода по ссылке пункта меню (работает с учЄтом значени€ activity)
--%>
<c:set var="contextName" value="${phiz:loginContextName()}"/>
<c:if test="${contextName == 'PhizIA'}">
    <div class="buttDiv menuItems"
            <c:set var="currentUrl" value="${phiz:calculateActionURL(pageContext,action)}"/>
            <c:if test="${not empty insetWidth}"> style="width:${insetWidth};" </c:if>
            <c:choose>
                <c:when test="${empty action && !fake}">
                    onclick="alert('Ќе задан action')"
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${isImplement}">
                            onclick="if (!redirectResolved()) return false; loadNewAction('mmi${action}',''); window.location='${currentUrl}';"
                        </c:when>
                        <c:otherwise>
                            onclick="alert('ƒанна€ функци€ не реализована')"
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
            >
        <c:choose>
            <c:when test="${activity}">
                <%--јктивна€ вкладка--%>
                <div class="leftMenuItem leftMenuItemActive activeItem">
                    <div class="rightMenuItem rightMenuItemActive activeItem">
                        <div class="centerMenuItem centerMenuItemActive activeItem">
                            ${text}
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <%--Ќеактивна€ вкладка--%>
                <div class="leftMenuItem">
                    <div class="rightMenuItem">
                        <div class="centerMenuItem" <c:if test="${not empty title}">title="${title}"</c:if>>
                            ${text}
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <script type="text/javascript">
        $(document).ready(function(){
            $('.menuItems').live("mouseenter",
                function(){
                    $(this).addClass('mmInsetMOver');
                });

            $('.menuItems').live("mouseleave",
                function(){
                    $(this).removeClass('mmInsetMOver');
                });
        })
    </script>
</c:if>

<c:if test="${contextName == 'PhizIC'}">
    <c:if test="${not empty insetWidth}">
        <c:set var="style">style="width: ${insetWidth}px;"</c:set>
    </c:if>
    <c:if test="${fake}">
        <c:set var="buttonFakeClass" value="fakeButton"/>
    </c:if>
    <c:if test="${activity}">
        <c:set var="insetClass">active ${positionItem}Active</c:set>
    </c:if>
    <td class="${insetClass} ${buttonFakeClass} ${positionItem}" ${style}>
        <span class="menuItems <c:if test="${mode == 'Payments' || mode == 'moreSbol'}"> relative</c:if>">
            <c:if test="${mode == 'Payments'}">
                <c:set var="paymentsAndTransfersNumber" value="${phiz:getClientInvoicesNewCounter()}"/>
                <c:if test="${paymentsAndTransfersNumber > 0 && paymentsAndTransfersNumber < 10}">
                    <div class="newInvoicesNumber" id="newInvoicesCounter">
                        ${paymentsAndTransfersNumber}
                    </div>
                </c:if>
                <c:if test="${paymentsAndTransfersNumber >= 10}">
                    <div class="invoiceLeft">
                        <div class="invoiceRight">
                            <div class="invoiceCenter">
                                ${paymentsAndTransfersNumber}
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:if>
            <c:if test="${!fake}">
               <a
                   <c:if test="${(not activity) || (activity && enabledLink)}">
                       href="${phiz:calculateActionURL(pageContext,action)}"
                   </c:if>
                   <c:if test="${not empty title}">
                       title="${title}"
                   </c:if>
                   onclick="return redirectResolved();"
                   >
                   <c:choose>
                       <c:when test="${novelty and mode == 'moreSbol'}">
                           <span class="relative">
                               ${text}
                               <div class="newItemIcon"></div>
                           </span>
                       </c:when>
                       <c:otherwise>
                           ${text}
                       </c:otherwise>
                   </c:choose>
                   <c:if test="${novelty && mode != 'moreSbol'}">
                       <img src='${imagePath}/newGroup.png' class='newGroup'/>
                   </c:if>
                   <c:if test="${not empty image}">
                       <div class="options"></div>
                   </c:if>
               </a>
            </c:if>
        </span>
    </td>
</c:if>