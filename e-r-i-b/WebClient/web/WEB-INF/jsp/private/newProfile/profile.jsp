<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images/profile/"/>
<c:set var="bundle" value="userprofileBundle"/>
<html:form action="/private/userprofile/userSettings" enctype="multipart/form-data">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="newPersonalInfo" value="${phiz:getNewPersonalInfo()}"/>
    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/async/userprofile/profileNovelties')}"/>
    <c:set var="person" value="${phiz:getPersonInfo()}"/>
    <c:set var="closedTutorial" value="${phiz:isPromoState('CLOSED')}"/>
    <tiles:insert definition="newUserProfile">
        <tiles:put name="data" type="string">
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.Jcrop.js"></script>
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/uploaderObject.js"></script>
            <script type="text/javascript">
                $(document).ready(function()
                {
                    var windowId = 'personRegionsDiv';
                    var selectRegion = function(myself)
                    {
                        ensureElement("region").innerHTML = myself.currentRegionName;
                        win.close(windowId);
                        reloadProfile();
                    };
                    var parameters =
                    {
                        windowId: windowId,
                        click:
                        {
                            useAjax: true,
                            getParametersCallback: function(id)
                            {
                                return "setCnt=false&needSave=true" + (id > 0? '&id=' + id: '');
                            },
                            afterActionCallback: function (myself, msg)
                            {
                                if (trim(msg) != '' && trim(msg) != 'OK')
                                    $("#personRegionsDiv").html(msg);
                                else
                                    selectRegion(myself);
                            }
                        },
                        choose:
                        {

                            useAjax: true,
                            getParametersCallback: function(id)
                            {
                                return "setCnt=false&needSave=true&select=true" + (id > 0? '&id=' + id: '');
                            },
                            afterActionCallback: function (myself, msg)
                            {
                               selectRegion(myself);
                            }
                        }
                    };
                    initializeRegionSelector(parameters);
                });

                function closePromo()
                {
                    hideMinimizedPromo();
                    callAjaxActionMethod('${url}', 'setPromoClosed');
                }

                function hideMinimizedPromo()
                {
                    $('.promo-minimized').hide();
                }

                function reloadProfile(){
                    window.location.reload();
                }

            </script>
            <tiles:insert definition="profileTemplate" flush="false">
                <tiles:put name="activeItem">profile</tiles:put>
                <tiles:put name="data">
                    <c:if test="${phiz:impliesOperation('UploadAvatarOperation', '') or
                                phiz:impliesOperation('ViewPaymentsBasketOperation', 'PaymentBasketManagment') or
                                phiz:impliesOperation('ViewAddressBookOperation', 'AddressBook')}">
                        <c:if test="${not closedTutorial}">
                            <div class="promo-minimized css3"
                                        <c:if test="${phiz:isPromoState('SHOWING')}">
                                            style="display: none;"
                                        </c:if>
                                    >
                                Быстрое знакомство с новинками вашего профиля.
                                <a class="showTutorial" onclick="hideMinimizedPromo()">Да, хочу посмотреть</a>
                                <a class="closePromo" title="Закрыть" onclick="closePromo()"></a>
                            </div>
                        </c:if>
                    </c:if>
                    <br/>
                    <div class="float avatarContainer">
                        <tiles:insert definition="userImage" flush="false">
                            <tiles:put name="selector" value="AVATAR"/>
                            <tiles:put name="imagePath" value="${phiz:avatarPath('AVATAR', null)}"/>
                            <tiles:put name="imgStyle" value="avatarBig"/>
                        </tiles:insert>
                        <c:if test="${phiz:impliesOperation('UploadAvatarOperation', '')}">
                            <c:choose>
                                <c:when test="${form.hasAvatar}">
                                    <div>
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.changePhoto"/>
                                            <tiles:put name="commandHelpKey" value="button.changePhoto"/>
                                            <tiles:put name="bundle" value="userprofileBundle"/>
                                            <tiles:put name="viewType" value="grayProfileButton"/>
                                            <tiles:put name="onclick" value="win.open('addAvatar');"/>
                                        </tiles:insert>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div>
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.addPhoto"/>
                                            <tiles:put name="commandHelpKey" value="button.addPhoto"/>
                                            <tiles:put name="bundle" value="userprofileBundle"/>
                                            <tiles:put name="viewType" value="whiteProfileButton"/>
                                            <tiles:put name="onclick" value="win.open('addAvatar');"/>
                                        </tiles:insert>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </div>

                    <div class="personInfo">
                        <span class="userFIO">
                            <bean:write name="form" property="field(fio)"/>
                        </span>
                        <c:if test="${not empty form.fields.mobilePhone}">
                            <div class="infoRow">
                                Мобильный телефон:<br/>
                                <span class="phoneNumber"><bean:write name="form" property="field(mobilePhone)"/></span>
                            </div>
                        </c:if>
                        <c:if test="${not empty form.fields.jobPhone}">
                            <div class="infoRow">
                                Рабочий телефон:<br/>
                                <span class="phoneNumber">${phiz:getCutPhoneNumber(form.fields.jobPhone)}</span>
                            </div>
                        </c:if>
                        <c:if test="${not empty form.fields.homePhone}">
                            <div class="infoRow">
                                Домашний телефон:<br/>
                                <span class="phoneNumber">${phiz:getCutPhoneNumber(form.fields.homePhone)}</span>
                            </div>
                        </c:if>

                        <div class="infoRow regions">
                            Регион оплаты:<br/>
                            <div onclick="win.open('personRegionsDiv'); return false">
                                <div>
                                    <span id="region" class="regionUserSelect userRegionLink">
                                        <bean:write name="form" property="field(regionName)"/>
                                    </span>
                                </div>
                                <div class="clear"></div>
                            </div>

                            <c:set var="regionUrl" value="${phiz:calculateActionURL(pageContext,'/dictionaries/regions/list')}"/>
                            <tiles:insert definition="window" flush="false">
                                <tiles:put name="id" value="personRegionsDiv"/>
                                <tiles:put name="loadAjaxUrl" value="${regionUrl}?isOpening=true"/>
                                <tiles:put name="styleClass" value="regionsDiv"/>
                            </tiles:insert>
                        </div>
                        <div class="infoRow">
                            E-mail:<br/>
                            <span class="phoneNumber userEmail"><bean:write name="form" property="field(email)"/></span>
                            <c:choose>
                                <c:when test="${form.fields.email == null || form.fields.email == ''}">
                                    <c:set var="btnType" value="addInfo"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="btnType" value="changeInfo"/>
                                </c:otherwise>
                            </c:choose>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.${btnType}"/>
                                <tiles:put name="commandHelpKey" value="button.changeInfo"/>
                                <tiles:put name="bundle" value="userprofileBundle"/>
                                <tiles:put name="onclick" value="win.open('editEmailDiv');"/>
                                <tiles:put name="viewType" value="lightGrayProfileButton"/>
                            </tiles:insert>
                            <c:set var="editEmail" value="${phiz:calculateActionURL(pageContext,'/private/async/userprofile/editEmail')}"/>
                            <tiles:insert definition="window" flush="false">
                                <tiles:put name="id" value="editEmailDiv"/>
                                <tiles:put name="loadAjaxUrl" value="${editEmail}"/>
                                <tiles:put name="styleClass" value="editEmailDiv"/>
                                <tiles:put name="closeCallback" value="reloadProfile"/>
                            </tiles:insert>
                            <html:link action="/private/userprofile/userNotification.do" styleClass="notificationLink">Настройка оповещений</html:link>
                        </div>
                    </div>
                    <div class="clear"></div>

                    <div class="documentsTitle">Мои документы, идентификаторы</div>
                    <div class="documentContainer">
                        <%--ДУЛы--%>
                        <c:forEach var="document" items="${form.personDocumentList}" varStatus="listIndex">
                            <c:set var="passportClass" value="personalDUL"/>
                            <c:if test="${phiz:isForeignDocument(document)}">
                                <c:set var="passportClass" value="personalDUL_foreign"/>
                            </c:if>
                            <c:set var="personDocType" value="${document.documentType}"/>
                            <%--названия у документов кривые, поэтому размер шрифта считаем "руками"--%>
                            <c:choose>
                                <c:when test="${personDocType == 'IMMIGRANT_REGISTRATION'}">
                                    <c:set var="addClass" value="font13"/>
                                </c:when>
                                <c:when test="${personDocType == 'MILITARY_IDCARD' || personDocType == 'TEMPORARY_PERMIT'}">
                                    <c:set var="addClass" value="font16"/>
                                </c:when>
                                <c:when test="${personDocType == 'FOREIGN_PASSPORT_OTHER'}">
                                    <c:set var="addClass" value="font12"/>
                                </c:when>
                                <c:when test="${personDocType == 'OTHER_NOT_RESIDENT' || personDocType == 'FOREIGN_PASSPORT_LEGAL'}">
                                    <c:set var="addClass" value="font11px"/>
                                </c:when>
                            </c:choose>
                            <div class="${passportClass}" onclick="win.open('personalDUL${listIndex.count}'); return false">
                                <table class="personDoc">
                                    <tr><td class="docName ${addClass}"><bean:message key="document.type.${personDocType}" bundle="userprofileBundle"/></td></tr>
                                    <tr><td class="docNumber">${document.documentSeries} ${phiz:getCutDocumentNumber(document.documentNumber)}</td></tr>
                                </table>
                            </div>

                            <tiles:insert definition="windowLight" flush="false">
                                <tiles:put name="id" value="personalDUL${listIndex.count}"/>
                                <tiles:put name="styleClass" value="personalDocument"/>
                                <tiles:put name="data">
                                    <div class="${passportClass}_window containLink">
                                        <div class="personalDULIcon"></div>
                                        <div class="personalDULName"><bean:message key="document.type.${personDocType}" bundle="userprofileBundle"/></div>
                                        <div class="clear"></div>

                                        <tiles:insert definition="invoicesSticker" flush="false">
                                            <tiles:put name="documentType" value="${personDocType}"/>
                                            <tiles:put name="cssClass" value="dulLinkList"/>
                                        </tiles:insert>

                                        <div class="personalDULDetails">
                                            <table>
                                                <tr>
                                                    <c:if test="${document.documentSeries != ''}"><td class="detailsTitle">Серия</td></c:if>
                                                    <c:if test="${document.documentNumber != ''}"><td class="detailsTitle">Номер</td></c:if>
                                                </tr>
                                                <tr class="detailsNumber">
                                                    <c:if test="${document.documentSeries != ''}"><td>${document.documentSeries}</td></c:if>
                                                    <c:if test="${document.documentNumber != ''}"><td>${phiz:getCutDocumentNumber(document.documentNumber)}</td></c:if>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </tiles:put>
                            </tiles:insert>
                        </c:forEach>
                        <c:if test="${phiz:impliesService('EditIdentifierBasket')}">
                            <%@ include file="basketIdentifier.jsp"%>
                        </c:if>

                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

    <c:if test="${newPersonalInfo}">
        <script type="text/javascript">
            $(document).ready(function(){
                callAjaxActionMethod('${url}', 'setOldPersonalInfo');
            });
        </script>
    </c:if>
    <c:if test="${phiz:impliesOperation('UploadAvatarOperation', '')}">
        <tiles:insert definition="window" flush="false">
            <tiles:put name="id" value="addAvatar"/>
            <tiles:put name="styleClass" value="addAvatar"/>
            <tiles:put name="loadAjaxUrl" value="${phiz:calculateActionURL(pageContext,'/private/async/userprofile/uploadAvatar')}"/>
            <tiles:put name="closeCallback" value="closeAvatarLoad"/>
        </tiles:insert>

        <tiles:insert definition="window" flush="false">
            <tiles:put name="id" value="addAvatarRules"/>
            <tiles:put name="styleClass" value="addAvatar"/>
            <tiles:put name="loadAjaxUrl" value="${phiz:calculateActionURL(pageContext,'/private/async/userprofile/avatarRules')}"/>
            <tiles:put name="notCloseWindow" value="addAvatar"/>
        </tiles:insert>

        <script type="text/javascript">
            function closeAvatarLoad()
            {
                ajaxQuery("state=cancel", "${phiz:calculateActionURL(pageContext,'/private/async/userprofile/uploadAvatar')}", function() {window.location.reload();});
            }
        </script>
    </c:if>

    <%--доступ к промо--%>
    <c:if test="${phiz:impliesOperation('UploadAvatarOperation', '') or phiz:impliesOperation('ViewPaymentsBasketOperation', 'PaymentBasketManagment') or phiz:impliesOperation('ViewAddressBookOperation', 'AddressBook')}">
        <c:if test="${not closedTutorial}">
            <%@ include file="promoProfile.jsp"%>
        </c:if>
    </c:if>
</html:form>