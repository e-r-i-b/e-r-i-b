<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
Кнопка, добавляющая текущую страницу, в избранные ссылки под названием ${formName}, с шаблоном ${patternName}.
formName -  имя ссылки
patternName - шаблон имени
typeFormat - фактически url. есть несколько типов каждому соответсвует свой url
productId - id продукта на какой продукт указывает ссылка
dopParam - дополнительные параметры для идентификации ссылки
--%>

<c:if test="${phiz:impliesOperation('AddFavouriteLinksOperation', 'FavouriteManagment')}">
    <tiles:importAttribute/>

    <script type="text/javascript">
        var oldFavouriteLinkMessage = null;

        function addFavouriteLinkToPersonalMenu(data)
        {
            if (trim(data) == '')
            {
                return false;
            }
            if (oldFavouriteLinkMessage != null)
                removeMessage(oldFavouriteLinkMessage);
            var htmlData = trim(data).replace(/^&nbsp;+/, "");
            $("#favouriteLinks").html(htmlData);
            var favouriteLinkMessage = $("#favouriteLinkMessage").val();
            addMessage(favouriteLinkMessage);
            $("#favouriteLinkMessage").remove();
            oldFavouriteLinkMessage = favouriteLinkMessage;
            $(".favouriteButton").find(".blueGrayLinkDotted").find(".text").html("<span>В избранном</span>");
            $(".favouriteButton").wrapInner('<div class="inFavourite"></div>').find(".blueGrayLinkDotted").find('img').attr('src','${globalUrl}/commonSkin/images/favouriteHover.gif');
            $(".favouriteButton").find(".blueGrayLinkDotted")[0].onclick = null;
            $(".favouriteButton").find(".imageAndButton")[0].onmouseout = null;
            $(".favouriteButton").find(".imageAndButton")[0].onmouseleave = null;
            return true;
        }

        function parametersStringFromQueryString()
        {
            var additionalParameters = "${phiz:getFavouriteLinkUrl(pageContext, formName, patternName, typeFormat)}";
            var query = window.location.search.replace(/^\?/, "");
            var queryParams =  query.split("&");
            var paramsLength = queryParams.length;

            for (var j=0; j < paramsLength; j++)
            {
                var parameter = queryParams[j].split("=");
                var parameterName = "parameter(" + parameter[0] + ")";
                var parameterValue = parameter[1];
                var newParameter = parameterName + "=" + parameterValue;
                if (additionalParameters.length > 0)
                    newParameter = "&" + newParameter;
                var parameterPosition = additionalParameters.indexOf(parameterName);

                if (parameterPosition < 0)
                {
                    additionalParameters = additionalParameters + newParameter;
                }
            }
            var url = "url=" + window.location.pathname + "&";
            return url + additionalParameters;
        }

        function afterConfirmFunction()
        {
            var actionURL = "${phiz:calculateActionURL(pageContext, '/private/async/favourite/add')}";
            var ajaxQueryParameters = parametersStringFromQueryString();

            ajaxQuery(ajaxQueryParameters, actionURL, addFavouriteLinkToPersonalMenu, null, true);
        }
    </script>

    <div class="add-to-favourite favouriteButton">
        <c:choose>
            <c:when test="${phiz:isLinkInFavourite(pageContext, typeFormat, productId, dopParam)}">
                <div class="inFavourite">
                    <tiles:insert definition="confirmationButton" flush="false" operation="AddFavouriteLinksOperation">
                        <tiles:put name="winId" value="favouriteLinkConfirmation"/>
                        <tiles:put name="title"><bean:message key="label.links" bundle="favouriteBundle"/></tiles:put>
                        <tiles:put name="currentBundle"  value="favouriteBundle"/>
                        <tiles:put name="confirmCommandKey" value="button.inFavourite"/>
                        <tiles:put name="afterConfirmFunction">afterConfirmFunction();</tiles:put>
                        <tiles:put name="buttonViewType" value="blueGrayLinkDotted"/>
                        <tiles:put name="buttonImage" value="favouriteHover.gif"/>
                        <tiles:put name="urlImageHover" value="favouriteHover.gif"/>
                        <tiles:put name="iconPosition" value="left"/>
                        <tiles:put name="validationFunction" value="function() {return false;};"/>
                        <tiles:put name="message"><bean:message key="label.saveFavourite" bundle="favouriteBundle"/></tiles:put>
                    </tiles:insert>
                </div>
            </c:when>
            <c:otherwise>
                <tiles:insert definition="confirmationButton" flush="false" operation="AddFavouriteLinksOperation">
                    <tiles:put name="winId" value="favouriteLinkConfirmation"/>
                    <tiles:put name="title"><bean:message key="label.links" bundle="favouriteBundle"/></tiles:put>
                    <tiles:put name="currentBundle"  value="favouriteBundle"/>
                    <tiles:put name="confirmCommandKey" value="button.saveFavourite"/>
                    <tiles:put name="afterConfirmFunction">afterConfirmFunction();</tiles:put>
                    <tiles:put name="buttonViewType" value="blueGrayLinkDotted"/>
                    <tiles:put name="buttonImage" value="favourite.gif"/>
                    <tiles:put name="urlImageHover" value="favouriteHover.gif"/>
                    <tiles:put name="iconPosition" value="left"/>
                    <tiles:put name="message"><bean:message key="label.saveFavourite" bundle="favouriteBundle"/></tiles:put>
                </tiles:insert>
            </c:otherwise>
        </c:choose>

    </div>
</c:if>
