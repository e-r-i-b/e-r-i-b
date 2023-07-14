/**
 * Автор: usachev
 * Дата создания: 15.05.15
 * Описание: Класс предназначе для работы со списком заявок на карты и кредиты.
 */
function Claims()
{
    var claims = this;

    claims.MESSAGES = {
        show_all_claims: "Показать все заявки",
        rollUp: "Свернуть",
        errorOnServer: "Операция временно не доступна. Пожалуйста, повторите попытку позже."
    };

    claims.SERVICE_MARKERS = {
        class_show: "gp-show",
        class_hide: "gp-hide",
        class_lastClaims: "lastClaims",
        marker: "markerAccept"
    };

    //Статусы отображения блока с заявками
    claims.STATE = {
        hide: 0, //Блок свёрнут
        show: 1  //Блок развёрнут
    };

    /**
     * Инициализация блока с заявками
     * @param url Адрес на котором запрашивать список заявок
     * @param minSize Минимальный размер списка для отображения пользователю
     */
    claims.init = function (url, minSize)
    {
        claims.urlForGetData = url;
        claims.minListClaimsSize = minSize;
        claims.buttonHideShow = $("#buttonHideShow");
        claims.testOfButton = $("#textOfButtonHideShow");
        claims.state = claims.STATE.hide;
        claims.buttonHideShow.bind("click", claims.action);
        claims.lock = false;
    };

    /**
     * Инициализация блока с заявками в случае, если полный список заявок уже загружен на странице
     * @param minSize минимальный размер списка для отображения пользователю
     */
    claims.initWithFullData = function (minSize)
    {
        claims.urlForGetData = null;
        claims.listClaims = $(".gp-claim");
        claims.minListClaimsSize = minSize;
        claims.buttonHideShow = $("#buttonHideShow");
        claims.testOfButton = $("#textOfButtonHideShow");
        claims.state = claims.STATE.hide;
        claims.buttonHideShow.bind("click", claims.action);
        claims.lock = false;

        $(".gp-claim:last").addClass(claims.SERVICE_MARKERS.class_lastClaims);
        claims.rollUp();
    };

    /**
     * Метод диспетчер. Отображает все заявки, если они скрыты. И скрывает, если они открыты.
     */
    claims.action = function ()
    {
        if (!claims.lock)
        {
            claims.lock = true;
            if (claims.state != claims.STATE.show)
            {
                claims.showAllClaims();
            }
            else
            {
                claims.rollUp();
            }
            claims.lock = false;
        }

    };

    /**
     * Изменить видимость блока заявок
     * @param addClass Класс для добавление к кнопке
     * @param removeClass Класс для удаления с кнопки
     * @param message Текст для кнопки
     * @param state Состояние всего блока
     */
    claims.changeVisibleState = function (addClass, removeClass, message, state)
    {
        claims.testOfButton.html(message);
        claims.buttonHideShow.removeClass(removeClass);
        claims.buttonHideShow.addClass(addClass);
        claims.state = state;
    };

    /**
     * Перевести блок в состояние "Свёрнуто"
     */
    claims.hideState = function(){
        claims.changeVisibleState(claims.SERVICE_MARKERS.class_hide, claims.SERVICE_MARKERS.class_show, claims.MESSAGES.show_all_claims, claims.STATE.hide);
    };

    /**
     * Перевести блок в состояние "Развёрнуто"
     */
    claims.showState = function(){
        claims.changeVisibleState(claims.SERVICE_MARKERS.class_show, claims.SERVICE_MARKERS.class_hide, claims.MESSAGES.rollUp, claims.STATE.show);
    };

    /**
     * Обработчик ответа с сервера
     * @param res Ответ сервера
     */
    claims.callback = function (res)
    {
        var answer = $.trim(res);
        if (answer.indexOf(claims.SERVICE_MARKERS.marker) > -1)
        {
            $(".gp-claims").html(res);
            claims.listClaims = $(".gp-claim");
            $(".gp-claim:last").addClass(claims.SERVICE_MARKERS.class_lastClaims);
            claims.showState();
        }
        else
        {
            addError(claims.MESSAGES.errorOnServer);
        }
    };

    /**
     * Показать все заявки. При первом обращение метод делает запрос на сервер и кеширует ответ. Во все последующие вызовы данные берутся из кеша
     */
    claims.showAllClaims = function ()
    {
        if (claims.listClaims == null)
        {
            removeError(claims.MESSAGES.errorOnServer);
            ajaxQuery("", claims.urlForGetData, claims.callback, null, true);
        }
        else
        {
            for (var i = 0; i < claims.listClaims.size(); i++)
            {
                if (i == claims.minListClaimsSize - 1)
                {
                    claims.listClaims.eq(i).removeClass(claims.SERVICE_MARKERS.class_lastClaims);
                }
                else if (i > claims.minListClaimsSize - 1)
                {
                    claims.listClaims.eq(i).show();
                }
            }
            claims.showState();
        }

    };

    /**
     * Скрыть все заявки
     */
    claims.rollUp = function ()
    {
        for (var i = 0; i < claims.listClaims.size(); i++)
        {
            if (i == claims.minListClaimsSize - 1)
            {
                claims.listClaims.eq(i).addClass(claims.SERVICE_MARKERS.class_lastClaims);
            }
            else if (i > claims.minListClaimsSize - 1)
            {
                claims.listClaims.eq(i).hide();
            }
        }
        claims.hideState();
    };
}

var claims = new Claims();

