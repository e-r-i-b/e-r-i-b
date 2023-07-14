<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 11:55:51
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<%--
    needShowNavigation - нужно ли отображать главное меню (true - меню отображается)
    needShowLetters    - нужно ли отображать информацию о новых письмах (true - отображать)
    needShowHelpButtonInHeaderMenu - нужно ли отображать кнопку помощи в шапке (true - отображать)
--%>

<tiles:importAttribute name="headerGroup" ignore="true"/>

<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>
<c:set var="person" value="${phiz:getPersonInfo()}"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=')}${$$helpId}"/>
<c:set var="helpTitle"><bean:message bundle="mailBundle" key="list.title"/></c:set>

<script type="text/javascript">
    function cutRegionName()
    {
        var regionNameHeader = document.getElementById('regionNameHeader');
        if (regionNameHeader != undefined)
        {
            var fullRegionName = regionNameHeader.innerHTML;
            var MAX_LEN_NAME = 35;
        <%-- 35 символов отображаем + 1 затемняем --%>
            if (fullRegionName.length > MAX_LEN_NAME)
            {
                $("#regionNameSpan").text(fullRegionName.substring(0, MAX_LEN_NAME) + "...");
            }
        }
    }
</script>

<c:if test="${headerGroup == 'true'}">
    <div id="header">
        <div class="hdrContainer">
                <%--New 7.1 block--%>
            <div class="NewHeader">
                <div class="Logo">
                    <c:choose>
                        <c:when test="${indexUrl != '' && !empty indexUrl}">
                            <c:set var="href" value="${phiz:calculateActionURL(pageContext, indexUrl)}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="href" value="${phiz:calculateActionURL(pageContext, '/CSAFrontLogin')}"/>
                        </c:otherwise>
                    </c:choose>
                    <a class="logoImage logoImageText"
                       href="${href}"
                       onclick="return redirectResolved();">
                        <img src="${image}/logoHeader.png" alt="">
                    </a>
                </div>
                <div class="PhoneNambers">
                    <span>+7 (495) <span>500-55-50</span><span style="display:none;">_</span></span><br />
                    <span>8 (800) <span>555-55-50</span><span style="display:none;">_</span></span>
                </div>
            </div>

            <div class="topLineContainer">
                <c:if test="${headerMenu == 'true'}">
                    <div class="UserInfo">
                        <c:if test="${needShowHelpButtonInHeaderMenu == 'true'}">
                            <a id="helpHref" href="#" class="headHelpLink"
                               onclick="openHelp('${helpLink}'); return false;"
                               title="Открыть справку по системе" alt="Открыть справку по системе">
                                <span>Помощь</span>
                            </a>
                        </c:if>

                        <c:if test="${needShowLetters == 'true'}">
                            <c:if test="${phiz:impliesService('ClientMailManagment')}">
                                <a class="lettersForUser" onclick="return redirectResolved();"
                                   href="${phiz:calculateActionURL(pageContext,"/private/mail/list.do")}" title="${helpTitle}">
                                    <div class="mailBlock">
                                        <c:set var="newLetters" value="${phiz:getCountNewLetters()}"/>
                                        <span>
                                        <c:if test="${newLetters >0}">
                                            <c:choose>
                                                <c:when test="${newLetters <10}">
                                                    <div class="newLettersShort">${newLetters}</div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="newLettersLeft">
                                                        <div class="newLettersRight">
                                                            <div class="newLettersCenter">
                                                                ${newLetters}
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                        </span>
                                    </div>
                                </a>
                            </c:if>
                        </c:if>
                        <table cellpadding="0" cellspacing="0" border="0">
                            <tr>
                                <td>
                            <%--Информация о предыдущих входах в систему и террбанке обслуживания--%>
                                    <c:if test="${not empty person}">
                                        <ul id="previousEnter" ${isGuest ? 'class="isGuest"' : '' } >
                                            <li id="previousEnterInfo" class="float  <c:if test="${phiz:impliesServiceRigid('NewClientProfile')}">haveAvatarIcon</c:if>">
                                                <c:set var="onClickAction" value=""/>
                                                <c:if test="${!isGuest}">
                                                    <c:set var="onClickAction">
                                                        onclick=window.location='${phiz:calculateActionURL(pageContext,"/private/userprofile/userSettings.do")}';
                                                    </c:set>
                                                </c:if>
                                                <span class="word-wrap" ${onClickAction}>
                                                    <c:if test="${phiz:impliesServiceRigid('NewClientProfile')}">
                                                        <div class="float fixAvatarBlock" id="avatarIconEl">
                                                            <div>
                                                                <c:if test="${not empty ifUserProfile}">
                                                                    <c:set var="inProfile" value="avatarIconInProfile"/>
                                                                </c:if>
                                                                <div class="relative ${inProfile}">
                                                                    <tiles:insert definition="userImage" flush="false">
                                                                        <tiles:put name="selector" value="ICON"/>
                                                                        <tiles:put name="imagePath" value="${phiz:avatarPath('ICON', null)}"/>
                                                                        <tiles:put name="imgStyle" value="float avatarIcon css3 ${inProfile}"/>
                                                                    </tiles:insert>
                                                                    <div class="roundAvatar"></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:if>
                                                    <span><c:out value="${phiz:getFormattedPersonFIO(person.firstName, person.surName, person.patrName)}"/></span>
                                                </span>
                                                <div class="clear"></div>
                                                <c:if test="${!isGuest}">
                                                     <div class="clientInfo">
                                                        <div class="clientInfoItemBorder"></div>
                                                        <div class="clientInfoItem"></div>
                                                        <ul>
                                                            <li>
                                                                <c:set var="showLastUserLogonInfoOperationImpl"
                                                                       value="${phiz:impliesService('ShowLastUserLogonInfoOperation')}"/>
                                                                <c:if test="${showLastUserLogonInfoOperationImpl}">
                                                                    <c:set var="lastLogonDate"
                                                                           value="${phiz:getPersonLastLogonDate()}"/>
                                                                    <c:if test="${not empty lastLogonDate}">
                                                                        Последнее посещение:<br>
                                                                        <c:set var="lastIpAddress"
                                                                               value="${phiz:getPersonLastIpAddress()}"/>
                                                                        <c:if test="${not empty lastIpAddress}">
                                                                            IP:<b> ${lastIpAddress}</b>
                                                                        </c:if>
                                                                        <b>${phiz:formatDateDependsOnSysDate(lastLogonDate, true, false)}</b>
                                                                    </c:if>
                                                                </c:if>
                                                            </li>
                                                            <c:set var="tbName" value="${phiz:getTBName()}"/>
                                                            <c:if test="${not empty tbName}">
                                                                <li class="clientRegion">
                                                                    Подразделение банка, в котором Вы обслуживаетесь:<br>
                                                                    <b>
                                                                        <c:out value="${tbName}" default=""/>
                                                                    </b>
                                                                    <span id="regionNameHeader" style="display:none">${fullName}</span>
                                                                </li>
                                                            </c:if>
                                                        </ul>
                                                     </div>
                                                </c:if>
                                            </li>
                                        </ul>
                                    </c:if>
                                </td>
                                <td>
                                    <a class="saveExit" onclick="return redirectResolved();"
                                       href="${phiz:calculateActionURL(pageContext, "/logoff.do")}" title="Безопасный выход из системы">
                                        <span>Выход</span>
                                        <div id="exit" alt="Безопасный выход из системы"></div>
                                    </a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="clear"></div>
                </c:if>
            </div>

            <div class="clear"></div>
            <c:if test="${headerMenu == 'true' && needShowNavigation == 'true'}">
                <div id="navigation">
                    <%--mainMenuType может принимать значения "mainMenu" или "guestMainMenu"--%>
                    <tiles:insert definition="${mainMenuType}">
                        <tiles:importAttribute name="mainmenu" ignore="true"/>
                        <tiles:put name="mainmenu" value="${mainmenu}"/>
                        <tiles:importAttribute name="enabledLink" ignore="true"/>
                        <tiles:put name="enabledLink" value="${enabledLink}"/>
                    </tiles:insert>
                    <div class="clear"></div>
                </div>
            </c:if>
        </div>
        <div class="clear"></div>
    </div>
    <div class="clear"></div>
</c:if>

<c:if test="${headerMenu == 'true'}">
    <%-- Окно для выбора региона --%>
    <script type="text/javascript">
        cutRegionName();
    </script>

    <c:if test="${needRegionSelector == 'true'}">
        <script type="text/javascript">
            $(document).ready(function()
            {
                regionChoose();
            });
        </script>
        <c:set var="regionUrl" value="${phiz:calculateActionURL(pageContext,'/dictionaries/regions/list')}"/>
        <tiles:insert definition="window" flush="false">
            <tiles:put name="id" value="regionsDiv"/>
            <tiles:put name="loadAjaxUrl"   value="${regionUrl}?isOpening=true"/>
            <tiles:put name="styleClass"    value="regionsDiv"/>
        </tiles:insert>
    </c:if>
</c:if>
