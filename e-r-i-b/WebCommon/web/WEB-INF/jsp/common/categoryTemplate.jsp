<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute/>

<%--
    Блок с всплывающей подсказкой (страница Платежи и переводы, Просмотр категории и шаги выбора поставщика услуг)
    title   - заголовок блока
    hint    - текст подсказки
    imagePath - путь к изображению
    url  -  ссылка, по которой переходим кликнув по заголовку
    serviceId  - соответсвующий сервис
    availableAutopayment  - признак доступности автоплатежа
--%>

<c:if test="${empty categoryTemplateCounter}">
    <c:set var="categoryTemplateCounter" value="0" scope="request"/>
</c:if>

<c:set var="categoryTemplateCounter" value="${categoryTemplateCounter+1}" scope="request"/>

<c:if test="${categoryTemplateCounter == 1}">

    <script type="text/javascript">

        doOnLoad(
                function ()
                {
                    var userAgentIgnorList = ["Mobile", "iPhone", "iPad", "iPod"];
                    for (var i=0; i<userAgentIgnorList.length; i++)
                        if(navigator.userAgent.indexOf(userAgentIgnorList[i]) != -1)
                      return; // если в игнор листе то не инициализируем
                    var parent;
                    if (isClientApp)
                        parent = document.forms[0];
                    else
                        parent = document.getElementById('workspaceDiv');

                    var payDivs = findChildsByClassName(document, "payment");

                    for (var payDiv=0; payDiv < payDivs.length; payDiv++)
                    {
                        var id = payDivs[payDiv].id;
                        var hint = document.getElementById('categoryTemplateFloatHint' + id);
                        var hintData = document.getElementById('categoryTemplateFloatHintData' + id); // блок с данными

                        if (hint.currentParent == undefined)
                            hint.currentParent = null;

                        // пропускаем задизейбленные
                        if (payDivs[payDiv].className.indexOf("disabled") != -1) continue;
                        // устанавливаем события
                        (function(payDivId){
                            $(payDivs[payDiv]).bind("mouseenter", function () {categoryTemplate (this, false, payDivId)});
                            $(payDivs[payDiv]).bind("mouseleave", function () {categoryTemplate (this, true, payDivId)});
                            $(payDivs[payDiv]).bind("mousemove", function () {categoryTemplate (this, false, payDivId)});
                        })(id);
                        // для IE фиксируем размер картинок (вот такой вот гад IE)
                        if (payDivs[payDiv].getElementsByTagName == undefined) continue;
                        var imgs = payDivs[payDiv].getElementsByTagName('img');
                        for ( var key in imgs )
                        {
                          if (imgs[key].style == undefined) continue;
                          imgs[key].style.width = imgs[key].offsetWidth+"px";
                          imgs[key].style.height = imgs[key].offsetHeight+"px";
                        }

                        // копируем данные в окно с подсказкой
                        hintData.innerHTML = payDivs[payDiv].innerHTML;
                        payDivs[payDiv].appendChild(hint);

                        $(hintData).find('.hintLinks').each(function(){
                            this.onmouseover = function () {$(this).addClass('hover')};
                            this.onmouseout = function () {$(this).removeClass('hover')};
                        });
                    }
                });

        function categoryTemplate (obj, hide, id)
        {
            var MAGIC_NUMBER = 50; // сумма паддингов рамки
            var MAGIC_HEIGHT = 18;
            var DELTA_H = 15;       // погрешность высоты подсказки

            var hint = document.getElementById('categoryTemplateFloatHint' + id);
            var hintData = document.getElementById('categoryTemplateFloatHintData' + id); // блок с данными
            var payHint = findChildByClassName(hint, "paymentHint", 1);
            var hintText = $('#categoryTemplateFloatHintData' + id + ' .paymentTitle');

            if (hint.currentParent === undefined ) return; // зашита от запуска до полной инициализации документа
            if (obj.fadeIn) return;

            hideOrShow(hint, hide); // прячем или отображаем подсказку
            if (hide)
            {
                // удаляем relative, чтобы в ие через подсказку низлежащие категории не просвечивали
                $(obj).removeClass("relative");
                hint.currentParent = null;
                hideOrShow(payHint, true);
                return ; // если подсказку спрятали
            }
            $(obj).addClass("relative");
            setCategoryHintPosition(obj, hint);

            if (hint.currentParent == obj) return;

            if (hint.currentParent == null || hint.currentParent != obj)
                hint.currentParent = obj; // запоминаем объекта

            //устанавливаем размеры линку с названием
            var titleLink = findChildByClassName(obj, "paymentLinkTitle");
            if (titleLink.style.width == "") titleLink.style.width = titleLink.clientWidth + "px";

            $(hintData).find(".hidable").show();
            hint.style.width = obj.clientWidth+MAGIC_NUMBER+"px";
            // устанавливаем размер данных всплываюшей таблички
            hintData.style.width = obj.offsetWidth+"px";
            hintData.style.height = obj.offsetHeight+"px";

            var links = obj.getElementsByTagName("A");
            if (links != undefined && links.length != 0)
            {
                var clickDiv = document.getElementById("categoryTemplateFloatOnclick" + id);
                clickDiv.style.width = hint.style.width;
                clickDiv.style.height = obj.offsetHeight + MAGIC_HEIGHT + "px";

                clickDiv.onclick = function(){window.location = links[0].href;};
            }

            // очищаем таймеры
            if (hint.categoryTimer!= undefined)
                clearTimeout(hint.categoryTimer);

            if (hintData.fadeTimer!= undefined)
                clearTimeout(hintData.fadeTimer);

           //устанавливаем событие для открытие дополнительной информации категории
            var bottomDiv = findChildByClassName(hint, "r-bottom-left");
            var bottomCenterDiv = findChildByClassName(hint, "r-bottom-center");

            if (payHint != undefined && trim(payHint.innerHTML) != '' || hintText.height() > $(hintData).height() + DELTA_H)
            {
                $(hint).addClass("hasHint");
                bottomDiv.style.cursor = "pointer";
                bottomDiv.onclick = function () { showCategoryTemplateHint (obj, hint, hintData, payHint, bottomDiv, bottomCenterDiv, id) };
                bottomCenterDiv.innerHTML = "<img src='"+globalUrl+"/commonSkin/images/greenArrowDown.gif' />";
            }
            else // если доп инфо отсутствует
            {
                $(hint).removeClass("hasHint");
                bottomDiv.style.cursor = "auto";
                bottomDiv.onclick = null;
                bottomCenterDiv.innerHTML = "";
            }
        }

        /**
         * отображаем скрытый по умолчанию текст подсказки
         * @param obj иконка категории видемая изначально
         * @param hint видимый при наведении див
         * @param hintData блок данных (без зеленнйо рамки)
         * @param payHint блок с дополнительной подсказкой
         * @param bottomDiv нижний блок (с градиентом)
         * @param bottomCenterDiv центральная чать нижнего блока (с градиентом)
         */
        function showCategoryTemplateHint (obj, hint, hintData, payHint, bottomDiv, bottomCenterDiv, id)
        {
            // div  с подсказкой
            if (payHint == undefined || payHint.style.display != "none") return; // див с подсказкой отсутствует

            payHint.style.display = "";
            // очищаем таймер
            if (hintData.fadeTimer!= undefined)
                clearTimeout(hintData.fadeTimer);

            //hintData.offsetHeight не дает реальной высоты, т.к она обрезается при наведении. Поэтому используем $(hintData).find(".paymentTitle").height()
            var hideHeight = $(hintData).height();
            fadeOut(hintData, hintData.offsetHeight, ($(hintData).find(".paymentTitle").height() + payHint.offsetHeight));
            bottomCenterDiv.innerHTML = "<img src='"+globalUrl+"/commonSkin/images/greenArrowUp.gif' />";
            bottomDiv.onclick = function () { hideCategoryTemplateHint (obj, hint, hintData, payHint, bottomDiv, bottomCenterDiv, hideHeight, id) };
        }

        /**
         * скрываем скрытый по умолчанию текст подсказки
         * @param obj иконка категории видемая изначально
         * @param hint видимый при наведении див
         * @param hintData блок данных (без рамки)
         * @param payHint блок с дополнительной подсказкой
         * @param bottomDiv нижний блок
         * @param bottomCenterDiv центральная чать нижнего блока
         * @param hideHeight высота до которой необходимо свернуть блок
         * @param id - id блока
         */
        function hideCategoryTemplateHint (obj, hint, hintData, payHint, bottomDiv, bottomCenterDiv, hideHeight, id)
        {
            // div  с подсказкой
            if (payHint == undefined || payHint.style.display == "none") return; // див с подсказкой отсутствует

            obj.fadeIn = true;
            var fromHeight = $(hintData).find(".paymentTitle").height() + payHint.offsetHeight;
            fadeIn(hintData, fromHeight, hideHeight,
                function(){
                    obj.fadeIn = false;
                    categoryTemplate (obj, true, id);
                    $(payHint).hide();
                }
            );
            bottomCenterDiv.innerHTML = "<img src='"+globalUrl+"/commonSkin/images/greenArrowDown.gif' />";
            bottomDiv.onclick = function () { showCategoryTemplateHint (obj, hint, hintData, payHint, bottomDiv, bottomCenterDiv, id) };
        }

        /**
         * Устанавливаем позицию подсказки относительно категории
         * @param obj - категория
         * @param hint - подсказка
         */
        function setCategoryHintPosition(obj, hint)
        {
            $(hint).css("top", $(obj).find("img").position().top - $(hint).find("img").position().top + "px");
            $(hint).css("left", $(obj).find("img").position().left - $(hint).find("img").position().left + "px");
        }
    </script>

</c:if>

<div id="categoryTemplateFloatHintPayment${categoryTemplateCounter}" style="display: none" class="categoryTemplateFloatHint">
    <tiles:insert definition="roundBorder" flush="false">
        <tiles:put name="color" value="lightGray"/>
        <tiles:put name="data">
            <div id="categoryTemplateFloatOnclickPayment${categoryTemplateCounter}" class="categoryTemplateFloatOnclick"></div>
            <div id="categoryTemplateFloatHintDataPayment${categoryTemplateCounter}" class="categoryTemplateFloatHintData">
            </div>
        </tiles:put>
    </tiles:insert>
</div>

<div class="payment ${disabled?'disabled':''}" id="Payment${categoryTemplateCounter}">
    <table cellpadding="0" cellspacing="0">
        <tr>
            <td class="paymentIcon">
                <c:choose>
                    <c:when test="${disabled}">
                        <img src="${imagePath}" alt="" border="0"/>
                    </c:when>
                    <c:when test="${empty serviceId}">
                        <phiz:link url="${fn:trim(url)}">
                            <img src="${imagePath}" alt="" border="0"/>
                        </phiz:link>
                    </c:when>
                    <c:otherwise>
                        <phiz:link url="${fn:trim(url)}" serviceId="${serviceId}">
                            <img src="${imagePath}" alt="" border="0"/>
                        </phiz:link>
                    </c:otherwise>
                </c:choose>

            </td>
            <td class="paymentTitle">
                <c:choose>
                    <c:when test="${disabled}">
                        ${title}
                    </c:when>
                    <c:otherwise>
                        <phiz:link url="${fn:trim(url)}" serviceId="${serviceId}" styleClass="paymentLinkTitle">${title}</phiz:link>
                        <c:if test="${!empty hint}"><span class="hidable" style="display:none;">${hint}</span></c:if>
                    </c:otherwise>
                </c:choose>
                ${availableAutopayment}
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <div class="paymentHint" style="display: none">${hintLink}</div>
            </td>
        </tr>

    </table>
</div>
