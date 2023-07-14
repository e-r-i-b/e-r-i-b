<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>
<%--
Как работает кнопка.
- При нажатии на кнопку вызывается validationFunction (если установлена)
- Если валидация прошла успешно показывается confirmText (если установлен)
- Если пользователь подтвердил выполнение команды на сервер отправляется запрос

	bundle    - bundle в котором ищутся текст для textKey и helpKey
	helpKey   - ключ для описания команды
	textKey   - ключ для заголовка команды
	image	  - название файла с картинкой, картинка должна находится в папке картинок текущей конфигурации
	imageUrl  - полный путь к картинке
	imageHover- инструмент для замены иконки у кнопок при наведении
	textIconClass  - иконка к тексту
	onclick   - javascript функция
	action    - struts action куда осуществляется переход
	width	  - ширина кнопки, если не указана определяется размером кнорки
	saveData  - признак который говорит пользователю что необходимо сохранять POST данные (данные постятся на указанный экшен) по умолчанию false
	typeBtn   - тип кнопки, может быть defaultBtn, disableBtn, dictBtn
	enabled   - активна ли кнопка (true - кнопка активна, false - не активна, при нажатии ничего не происходит)
    viewType  - тип отображения кнопки. ПО УМОЛЧАНИЮ buttonGreen. Возможные значения:
    buttonGrey, buttonRed, simpleLink, boldLink, LoginButton, icon, buttonGreyWithImg, buttonWhite,buttonLightGray,buttonOrange, linkWithImg, buttonRoundGray, buttonRoundGrayLight, blueGrayLink, blueGrayLinkDotted, blueBorder, darkGrayButton, delBtnLight, buttonFillRed, orangePromo, lightGrayPromo. По умолчанию buttonGreen.
    isDefault   - true/false кнопка по умолчанию (ее действие выполняется по нажатию Enter)
    imagePosition - положение картинки (по умолчанию справа)
    fromBanner    - если == <число>, то при нажатии на кнопку необходимо отправить запрос на сохранение записи в журнал статистики по баннерам о выполнении операции для баннера
    redirectURL    - абсолютный URL для редиректа
--%>

<c:set var="commandText"><bean:message key="${commandTextKey}" bundle="${bundle}"/></c:set>
<c:set var="commandHelp"><bean:message key="${commandHelpKey}" bundle="${bundle}"/></c:set>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:if test="${!empty onclick && fn:startsWith(onclick,'javascript:')}">
	<c:set var="onclick" value="${fn:substringAfter(onclick,'javascript:')}"/>
</c:if>

<c:if test="${not empty imageHover}">
    <c:choose>
        <c:when test="${phiz:isIE()}">
            <script type="text/javascript">
                <c:set var="htmlImageOver">
                    onmouseenter="$(this).find('img').attr('src','${commonImagePath}/${imageHover}');"
                    onmouseleave="$(this).find('img').attr('src','${commonImagePath}/${image}');"
                </c:set>
            </script>
        </c:when>
        <c:otherwise>
            <script type="text/javascript">
                <c:set var="htmlImageOver">
                    onmouseover="$(this).find('img').attr('src','${commonImagePath}/${imageHover}');"
                    onmouseout="$(this).find('img').attr('src','${commonImagePath}/${image}');"
                </c:set>
            </script>
        </c:otherwise>
    </c:choose>
</c:if>


<c:choose>
    <c:when test="${not empty fromBanner}">
        <c:set var="beforeOnclick">bannerRequest(${fromBanner}, 'operation');</c:set>
    </c:when>
    <c:otherwise>
        <c:set var="beforeOnclick" value=""/>
    </c:otherwise>
</c:choose>
<script type="text/javascript">
    if(window.createClientButton != undefined)
    {
        var button = createClientButton('${commandTextKey}', '${commandText}', function(){${onclick}});
        <c:if test="${isDefault}">setDefaultClientButon(button);</c:if>
    }
</script>

<c:set var="contextName" value="${phiz:loginContextName()}"/>

<c:set var="htmlOnclick">
    <c:set var="url">
        <c:choose>
            <c:when test="${empty redirectURL}">
                ${phiz:calculateActionURL(pageContext,action)}
            </c:when>
            <c:otherwise>
                ${redirectURL}
            </c:otherwise>
        </c:choose>
    </c:set>

    <c:choose>
        <c:when test="${empty onclick and empty action}">
            onclick="alert('Не задан ни onclick ни action')"
        </c:when>
        <c:when test="${empty onclick}">
            <c:choose>
                <c:when test="${saveData}">onclick="${beforeOnclick}changeFormAction ('${url}'); document.forms[0].submit();"</c:when>
                <c:otherwise>onclick="loadNewAction('','');window.location='${url}'"</c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${empty action}">
            onclick="${beforeOnclick}${onclick}"
        </c:when>
        <c:otherwise>
            <c:set var="onclick">(function(){var r=${onclick};return r;})()</c:set>
            <c:choose>
                <c:when test="${saveData}">onclick="${beforeOnclick}if(${onclick}){ changeFormAction ('${url}'); document.forms[0].submit(); } "</c:when>
                <c:otherwise>onclick="${beforeOnclick}loadNewAction('');if(${onclick}) window.location='${url}'"</c:otherwise>
            </c:choose>

        </c:otherwise>
    </c:choose>
</c:set>

<c:set var="imageHtml">
    <c:choose>
        <c:when test="${not empty image}">
            <img src="${commonImagePath}/${image}" alt="${commandHelp}">
        </c:when>
        <c:when test="${not empty imageUrl}">
            <img src="${imageUrl}/${image}" alt="${commandHelp}" title="${commandHelp}">
        </c:when>
    </c:choose>
</c:set>

<c:choose>
    <c:when test="${contextName == 'PhizIA'}">
        <c:if test="${isDefault}"><c:set var="typeBtn" value="defaultBtn"/></c:if>
        <c:choose>
            <c:when test="${viewType == 'icon'}">
                <img src="${imagePath}/${image}" alt="${commandHelp}" title="${commandText}" class="iconButton" ${htmlOnclick}/>
            </c:when>
            <c:otherwise>
                <div class="clientButton<c:if test="${not empty typeBtn and typeBtn!=''}"> ${typeBtn}</c:if>">
                    <div

                        <c:if test="${not empty btnId}">id="${btnId}"</c:if>

                        <c:if test="${enabled}">${htmlOnclick}</c:if>
                            class="${viewType} <c:if test="${not enabled}">disabled</c:if>"
                            >
                        <div class="left-corner"></div>

                        <div class="text">
                            <c:if test="${imagePosition == 'center'}">${imageHtml}</c:if>

                            <c:if test="${viewType != 'linkWithImg' && not empty image && imagePosition=='right'}">
                                <img src="${imagePath}/${image}" alt="${commandHelp}" width="12px" height="12px">&nbsp;
                            </c:if>
                            <span>
                                <c:if test="${viewType == 'linkWithImg'}">
                                    <img src="${imageUrl}" alt="${commandHelp}" title="${commandText}" class="iconButton" ${htmlOnclick} ${htmlOnMouseOver}/>
                                </c:if>
                                ${commandText}
                            </span>
                        </div>

                        <div class="right-corner"></div>
                    </div>
                    <div class="clear"></div>
                </div>
            </c:otherwise>
        </c:choose>
    </c:when>


    <c:when test="${contextName == 'PhizIC' or contextName == 'WebPFP'}">
        <c:choose>
            <c:when test="${viewType == 'icon'}">
                <img src="${commonImagePath}/${image}" alt="${commandHelp}" title="${commandText}" class="iconButton" ${htmlOnclick}/>
            </c:when>
            <c:otherwise>
                <div ${htmlImageOver} class="clientButton<c:if test="${not empty typeBtn and typeBtn!=''}"> ${typeBtn}</c:if>
                     <c:if test="${not empty imageHover}"> imageAndButton</c:if>">
                    <div

                        <c:if test="${not empty btnId}">id="${btnId}"</c:if>

                        <c:if test="${enabled}">${htmlOnclick}</c:if>
                            class="${viewType} <c:if test="${not enabled}">disabled</c:if>"
                            >
                        <a>
                            <div class="left-corner">
                                <c:if test="${imagePosition == 'left'}">${imageHtml}</c:if>
                            </div>

                            <div class="text">
                                <c:if test="${imagePosition == 'center'}">${imageHtml}</c:if>
                                <c:if test="${!empty textIconClass}"><span class="${textIconClass} textIcon float"></span></c:if>
                                <span <c:if test="${!empty textIconClass}">class="float"</c:if>>
                                <c:if test="${viewType == 'linkWithImg'}">
                                    <img src="${imageUrl}" alt="${commandHelp}" title="${commandText}" class="iconButton" ${htmlOnclick} ${htmlOnMouseOver}/>
                                </c:if>${commandText}</span>
                            </div>

                            <div class="right-corner">
                                <c:if test="${imagePosition == 'right' && viewType != 'linkWithImg'}">${imageHtml}</c:if>
                            </div>
                        </a>
                    </div>
                    <div class="clear"></div>
                </div>
            </c:otherwise>
        </c:choose>
    </c:when>
</c:choose>




