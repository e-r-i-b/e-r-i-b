<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<html:form action="/private/userprofile/promoCodes">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="showList" value="${phiz:impliesService('ShowClientPromoCodeList')}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="newUserProfile">
        <tiles:put name="data" type="string">
            <tiles:insert definition="profileTemplate" flush="false">
                <tiles:put name="title">Промо-коды</tiles:put>
                <tiles:put name="activeItem">promoCodes</tiles:put>
                <tiles:put name="data">

                    <script type="text/javascript">

                        doOnLoad(function()
                        {
                            <c:set var="promoCodeBlockedMessage" value="${phiz:getPromoCodeBlockedMessage()}"/>
                            <c:if test="${not empty promoCodeBlockedMessage}">
                                $('#msg12BlockText').html('${phiz:processBBCodeAndEscapeHtml(promoCodeBlockedMessage, false)}');
                                $('#msg12Block').show();
                            </c:if>

                            var inputPromo = $('input[name=clientPromo]');
                            if (inputPromo.length > 0)
                                inputPromo[0].setAttribute('oninput', "upperCasePromo()");
                        })

                        function upperCasePromo()
                        {
                            var element = $('#promoCodeInput');
                            element.val(element.val().toUpperCase());
                        }

                        function showDeletePromoWindow(event, promoId) {
                            $('#promoToDelete').val(promoId);
                            win.open('deletePromo');
                            event.preventDefault();
                            event.stopPropagation();
                        }

                        function doNothing(event) {
                            event.preventDefault();
                            event.stopPropagation();
                        }

                        function deletePromo() {
                            var promoId = $('#promoToDelete').val();
                            <c:set var="deleteAction" value="${phiz:calculateActionURL(pageContext,'/private/userprofile/promoCodes.do?operation=button.remove&id=')}" />
                            changeFormAction('${deleteAction}' + promoId);
                            createCommandButton('button.remove','').click('', false);
                            win.close('deletePromo');
                        }

                        function addPromo() {
                            var params = {};
                            params['operation'] = 'button.insert';
                            params['clientPromo'] = $('input[name=clientPromo]').val();
                            ajaxQuery(convertAjaxParam (params), "${phiz:calculateActionURL(pageContext, '/private/async/userprofile/promoCodes')}",
                                function (data)
                                {
                                    if (data.errorMessageNumber == '')
                                    {
                                        window.location = "${phiz:calculateActionURL(pageContext,'private/deposits/products/list.do?form=AccountOpeningClaim&withPromo=true')}";
                                    }
                                    else
                                    {
                                        var errorDiv = $("#errors");
                                        errorDiv.find(".title").html(data.errorMessageTitle);
                                        errorDiv.find(".messageContainer").html(data.errorMessageText);
                                        errorDiv.show();
                                        if (data.errorMessageNumber != 'MSG011')
                                        {
                                            $('#promoCodeInput').addClass('errPromo');
                                        }
                                        else
                                        {
                                            $('#msg12BlockText').html(data.error12MessageText);
                                            $('#msg12Block').show();
                                        }
                                    }
                                },
                                "json", null);
                        }

                        function getOffset( el ) {
                            var _x = 0;
                            var _y = 0;
                            while( el && !isNaN( el.offsetLeft ) && !isNaN( el.offsetTop ) ) {
                                _x += el.offsetLeft - el.scrollLeft;
                                _y += el.offsetTop - el.scrollTop;
                                el = el.offsetParent;
                            }
                            return { top: _y, left: _x };
                        }

                        $(document).ready(function() {
                            $( "div.undeletable" )
                                .mouseover(function(event) {
                                    var currentElement = $('#undeletable');
                                    currentElement.removeClass('farAway');
                                    var eventElement = event.target || event.srcElement;
                                    var xPosition = (getOffset(eventElement).left - currentElement.width() + 10) + 'px';
                                    var yPosition = (getOffset(eventElement).top + 35) + 'px';
                                    currentElement.css({
                                            position: 'absolute',
                                            top: yPosition,
                                            left: xPosition,
                                            zIndex: 100
                                        });
                                    currentElement.show();
                                })
                                .mouseout(function() {
                                    var currentElement = $('#undeletable');
                                    currentElement.hide();
                                    currentElement.addClass('farAway');
                                    currentElement.css({
                                        position: 'relative'
                                    });
                            });
                        });

                        function showFullInfo(id) {
                            $('#promoFull' + id).show();
                            $('#promoShort' + id).hide();
                            return false;
                        }

                        function hideFullInfo(id) {
                            $('#promoShort' + id).show();
                            $('#promoFull' + id).hide();
                            return false;
                        }

                    </script>

                    <%-- место для ввода промокода, показывается почти вседа --%>
                    <c:if test="${phiz:impliesService('ClientPromoCode')}">
                        <div class="showPromoBtn mainPromo">
                            <div id="insertPromoRow" class="insertPromoRow">
                                <html:text property="clientPromo" value="${frm.clientPromo}" styleClass="customPlaceholder search-promo"
                                           maxlength="256" titleKey="promo.code.input.message"  bundle="promocodesBundle" styleId="promoCodeInput"/>

                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.add"/>
                                    <tiles:put name="commandHelpKey" value="button.add"/>
                                    <tiles:put name="bundle" value="paymentsBundle"/>
                                    <tiles:put name="onclick" value="addPromo();"/>
                                    <tiles:put name="image" value=""/>
                                    <tiles:put name="isDefault" value="true"/>
                                </tiles:insert>
                                <div id="msg12Block" class="blockedContent">
                                    <span id="msg12BlockText" class="lockText"></span>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:choose>
                        <%-- есть есть введённые промокоды --%>
                        <%-- список ввеедённых промокодов --%>
                        <c:when test="${(not empty form.activePromoCodes or not empty form.archivePromoCodes) and showList}">
                            <c:if test="${not empty form.activePromoCodes}">
                                <div id="activePromoCode">
                                    <table class="promoCodeList">
                                        <tr>
                                            <th class="align-left">промо-код</th>
                                            <th colspan="2" class="align-right"><span class="nowrapWhiteSpace">дата окончания</span></th>
                                        </tr>
                                        <logic:iterate id="listItem" name="form" property="activePromoCodes">
                                            <tr id="promoShort${listItem.id}" class="promoShort" onclick="showFullInfo(${listItem.id});">
                                                <td class="promoTitle"><span class="promoName"><c:out value="${listItem.name}"/></span></td>
                                                <td class="shortDescription"><span><c:out value="${listItem.promoCodeDeposit.nameS}"/></span></td>
                                                <td class="endDate"><span class="nowrapWhiteSpace"><c:out value="${phiz:calendarToPromoFormat(listItem.endDate)}"/></span></td>
                                            </tr>
                                            <tr id="promoFull${listItem.id}" class="promoFull" onclick="hideFullInfo(${listItem.id});">
                                                <td colspan="3">
                                                    <div class="relative">
                                                        <div class="shadowCell css3">
                                                            <div class="fullTitle promoTitle">
                                                                <span class="promoName fullPromoName"><c:out value="${listItem.name}"/></span>
                                                                <c:if test="${not empty listItem.promoCodeDeposit and not empty listItem.used and listItem.promoCodeDeposit.numUse - listItem.used > 0}">
                                                                    <c:set var="avail" value="${listItem.promoCodeDeposit.numUse - listItem.used}"/>
                                                                    <p class="promoCodeDeposit">Можно применить ${avail} ${phiz:calculateOccasions(avail)}</p>
                                                                </c:if>
                                                            </div>
                                                            <div class="date align-right"><c:out value="${phiz:calendarToPromoFormat(listItem.endDate)}"/></div>

                                                            <div class="shortDescription"><c:out value="${listItem.promoCodeDeposit.nameS}"/></div>
                                                            <div class="fullDescription"><c:out value="${listItem.promoCodeDeposit.nameF}"/></div>
                                                            <div class="clear"></div>
                                                            <div class="details">
                                                                <a href=${phiz:calculateActionURL(pageContext,'private/deposits/products/list.do?form=AccountOpeningClaim&withPromo=true')}>Условия промо-вклада</a>
                                                            </div>

                                                            <c:choose>
                                                                <c:when test="${listItem.promoCodeDeposit.abRemove}">
                                                                    <div class="deletePromo delete" onclick="showDeletePromoWindow(event, ${listItem.id});return false;">
                                                                    </div>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <div class="undeletable" onclick="doNothing(event);return false;"></div>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            <div class="clear"></div>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </logic:iterate>
                                    </table>
                                    <%-- Подсказка - нельзя удалить --%>
                                    <div id="undeletable" class="undeletableMessage farAway">
                                        <div class="clientPromoItemBorder"></div>
                                        <div class="clientPromoItem"></div>
                                        <bean:message key="promo.code.is.undeletable" bundle="promocodesBundle"/>
                                    </div>
                                </div>

                                <%--Подтверждение удаления--%>
                                <tiles:insert definition="window" flush="false">
                                    <tiles:put name="id" value="deletePromo"/>
                                    <tiles:put name="data">
                                        <input id="promoToDelete" type="hidden"/>

                                        <h2>${phiz:processBBCodeAndEscapeHtml(form.messageSeven.title, false)}</h2>
                                        <div class="promoMessage">${phiz:processBBCodeAndEscapeHtml(form.messageSeven.text, false)}</div>

                                        <div class="buttonsArea separateBtn">
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey" value="button.remove"/>
                                                <tiles:put name="commandHelpKey" value="button.remove"/>
                                                <tiles:put name="bundle" value="promocodesBundle"/>
                                                <tiles:put name="onclick" value="deletePromo();"/>
                                                <tiles:put name="viewType" value="buttonGrey"/>
                                            </tiles:insert>
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                                <tiles:put name="commandHelpKey" value="button.cancel"/>
                                                <tiles:put name="isDefault" value="true"/>
                                                <tiles:put name="bundle" value="promocodesBundle"/>
                                                <tiles:put name="onclick" value="win.close('deletePromo');"/>
                                            </tiles:insert>
                                        </div>
                                    </tiles:put>
                                </tiles:insert>

                            </c:if>


                            <div class="clear"></div>
                            <%-- этот блок должен быть скрывабельный --%>
                            <c:if test="${not empty form.archivePromoCodes}">
                                <span onclick="showArchivePromo();">
                                     <div class="showHiddenPromo hidePromo" id="hide-show-switch">
                                         <a class="blueGrayLinkDotted">Архив промо-кодов</a>
                                         <div class="promoArrows"></div>
                                     </div>
                                    <div class="clear"></div>
                                </span>

                                <script type="text/javascript">
                                    function showArchivePromo()
                                    {
                                        if (document.getElementById("archivePromoCode").className == 'invisible'){
                                            document.getElementById("archivePromoCode").className = '';
                                            document.getElementById("hide-show-switch").className = 'showHiddenPromo hidePromo';}
                                        else {
                                            document.getElementById("hide-show-switch").className = 'showHiddenPromo';
                                            document.getElementById("archivePromoCode").className = 'invisible';
                                        }
                                    }
                                </script>

                                <div class="hide-switch">
                                    <div id="archivePromoCode">

                                        <div class="archivePromoCode">
                                            <table class="promoCodeList">
                                                <logic:iterate id="listItem" name="form" property="archivePromoCodes">
                                                    <tr id="promoShort${listItem.id}" class="promoShort" onclick="showFullInfo(${listItem.id});">
                                                        <td class="promoTitle"><span class="promoName"><c:out value="${listItem.name}"/></span></td>
                                                        <td class="shortDescription"><span class="closeReason"><bean:message bundle="promocodesBundle" key="${listItem.closeReason}"/></span></td>
                                                        <td></td>
                                                    </tr>
                                                    <tr id="promoFull${listItem.id}" class="promoFull" onclick="hideFullInfo(${listItem.id});">
                                                        <td colspan="3">
                                                            <div class="relative">
                                                                <div class="shadowCell css3">
                                                                    <div class="fullTitle promoTitle">
                                                                        <span class="promoName fullPromoName"><c:out value="${listItem.name}"/></span>
                                                                    </div>
                                                                    <div class="clear"></div>
                                                                    <div class="shortDescription"><c:out value="${listItem.promoCodeDeposit.nameS}"/></div>
                                                                    <div class="fullDescription"><c:out value="${listItem.promoCodeDeposit.nameF}"/></div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </logic:iterate>
                                            </table>
                                        </div>

                                    </div>
                                </div>
                            </c:if>

                        </c:when>

                        <%-- если введённых промокодов нет --%>
                        <%-- реклама 1 и 2 --%>
                        <c:otherwise>
                            <div id="promoCodeInvite">
                                <div id="promoUpLeft">
                                    <div class="promoTitle">${phiz:processBBCodeAndEscapeHtml(form.messageNine.title, false)}</div>
                                    <div class="promoMessage">${phiz:processBBCodeAndEscapeHtml(form.messageNine.text,false)}</div>
                                </div>
                                <div id="promoDownRight">
                                    <div class="promoTitle">${phiz:processBBCodeAndEscapeHtml(form.messageTen.title, false)}</div>
                                    <div class="promoMessage">${phiz:processBBCodeAndEscapeHtml(form.messageTen.text,false)}</div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
