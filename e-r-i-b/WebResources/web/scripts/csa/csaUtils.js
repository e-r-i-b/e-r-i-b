
// Переключатель событий
// ! события не удаляются, а лишь выключаются
// @return: boolean true вслучаи удачи или false в случаи ошибки
var eventsKeyHash = {};

var isClientApp = true;

/**
 * Функция для запросов ajax
 * @param params параметры (имя=значение?имя=значение, если параметров нет передаем null(от этого зависит тип запроса)
 * @param url
 * @param callback функция для обратного вызова
 * @param type необязательный параметр для определения типа ответа (сейчас используется только для JSON)
 * @param showPreLoader параметр, отвечающий за необходимость отображения прелоудера. По умолчанию true
 * @param errorAction функция, вызываемая при ошибке
 */
function ajaxQuery(params, url, callback, type, showPreLoader, errorAction)
{
    var requestType = params == null ? "GET" : "POST";
    showPreLoader = showPreLoader == null ? true : showPreLoader;
    /*если нет параметров отправляем GET запрос*/
    if (showPreLoader)
        showOrHideAjaxPreloader();

    var isJson = type != null && type.toLowerCase() == "json";

    var defaultCallback = function (res)
    {
         if (showPreLoader)
            showOrHideAjaxPreloader(false);

        callback(res);
        if (!isJson) evalAjaxJS(res);
    };
    var newCallback = defaultCallback;

    if (isJson)
    {
        newCallback = function (res)
        {
            var resp;
            try
            {
                resp = $.parseJSON(res);
            }
            catch (e)
            {
                resp = null;
            }
            defaultCallback(resp);
        };
    }

    if (url == null) return false;
    /*jquery*/
    try
    {
        new XMLHttpRequest();
        $.ajax({
            type: requestType,
            url: url,
            data: params,
            contentType: "application/x-www-form-urlencoded; charset=utf8",
            success: newCallback
        });
        return true;
    }
    catch (err)
    {
        /*IFRAME*/
        var xmlobj = new IFrameRequest();
        xmlobj.open(requestType, url, true);
        xmlobj.onreadystatechange = function()
        {
            if (this.readyState == 4)
            {
                newCallback(this.responseText);
            }
        };
        xmlobj.send(params);
        return true;
    }
}

/**
 * Метод для выполнения всех скриптов полученых в ajax данных
 * @param msg ajax данные
 */
function evalAjaxJS(msg)
{
    // если в результате нет намека на скрипт до дальнейшее выполнение не требуется
   if (msg.toLowerCase().indexOf("script") == -1) return;
   // очишаем события при загрузки
   window.onload = null;
   if (window.evalAjaxJSCounter == undefined)
        window.evalAjaxJSCounter = 0;
   window.evalAjaxJSCounter++;
   var id = "tmpEvalAjaxJS"+window.evalAjaxJSCounter;
   var tmpDiv = document.createElement("div");
   tmpDiv.id = id;
   tmpDiv.style.display = "none";
   document.getElementsByTagName("body")[0].appendChild(tmpDiv);
   tmpDiv = document.getElementById(id);
   tmpDiv.innerHTML = msg;

   var script = tmpDiv.getElementsByTagName("script");
   for (var i = 0; i<script.length; i++ )
   {
       if (script[i].innerHTML == "")
          continue;
       try
       {
           if (!window.execScript)
               window.eval(script[i].innerHTML);
           else
               window.execScript(script[i].innerHTML);
       }
       catch (ex)
       {
           alert("При попытке инициализировать скрипты, полученные через AJAX, возникла ошибка:\n" + ex.message + "\n script = " + i + "\n" + script[i].innerHTML + "\n" + msg);
           throw(ex);
       }
   }
    if ( window.onload!= null ) window.onload();
    tmpDiv.parentNode.removeChild(tmpDiv);
}

var UrlEncodeDecode = {

	// публичная функция для кодирования URL
	encode : function (string) {
		return escape(this._utf8_encode(string));
	},

	// публичная функция для декодирования URL
	decode : function (string) {
		return this._utf8_decode(unescape(string));
	},

	// приватная функция для кодирования URL
	_utf8_encode : function (string) {
		string = string.replace(/\r\n/g,"\n");
		var utftext = "";

		for (var n = 0; n < string.length; n++) {

			var c = string.charCodeAt(n);

			if (c < 128) {
				utftext += String.fromCharCode(c);
			}
			else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			}
			else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}

		}

		return utftext;
	},

	// приватная функция для декодирования URL
	_utf8_decode : function (utftext) {
		var string = "";
		var i = 0;
        var c;
        var c1;
        var c2;
        var c3;
		c = c1 = c2 = 0;

		while ( i < utftext.length ) {

			c = utftext.charCodeAt(i);

			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			}
			else if((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i+1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			}
			else {
				c2 = utftext.charCodeAt(i+1);
				c3 = utftext.charCodeAt(i+2);
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}

		}

		return string;
	}

};
// Данное извращение актуально только для ИЕ6
function IFrameRequest()
{
    if (window.IFrameRequestCounter == undefined)
        window.IFrameRequestCounter = 0;
    this.readyState = 0;
    this.status = 0;
    this.responseText = "";
    window.IFrameRequestCounter++;
    this.req_id = window.IFrameRequestCounter;
    /**
     * метод для импортирвания данных типа param=value&param2=value2.. на форму
      * @param postData данные
     * @param frm форма
     */
    this.preparePostData = function (postData, frm)
    {
        postData = UrlEncodeDecode.decode(postData);
        var items = postData.split('&');
        for (var i=0; i<items.length; i++)
        {
            var item = items[i].split('=',2);
            frm.innerHTML += "<input type='hidden' name='"+item[0]+"' value='"+item[1]+"' />";
        }
    }
};

IFrameRequest.prototype = {
    open: function(protocol, url, async)
    {
        this.protocol = protocol;
        this.url = url;
    },

    onreadystatechange: function()
    {
    },

    send: function(postBody)
    {
        if (postBody == null) postBody = "";

        var CONTEINER_ID_NAME = "ieIFrameRequest";
        var IE_ADDRES_LENGTH_LIMIT = 2000;
        var self = this;
        var urlWithParams = this.url;
        if (postBody.length!=0)
        {
            if (this.url.indexOf("?") >= 0)
                urlWithParams += "&";
            else
               urlWithParams += "?";
            urlWithParams += postBody;
        }
        // Кривоватое ограничение на пост.
        // Гетом данный метод хорошо передает русскоязычный текст, а вот пост все ломает, однако на гет в ИЕ есть ограничение, так что используем гет а если не влезает то используем пост.
        var isPost = this.protocol.toUpperCase() == 'POST' && postBody!= null && postBody != '' && urlWithParams.length>IE_ADDRES_LENGTH_LIMIT;
        // необходимо для того чтоби избежать предупреджение о не безопастном контенте
        var  src = "javascript:;";
        if (!isPost)
            src = urlWithParams;

        try
        {
            var frameId = 'req' + this.req_id;

            var requestConteiner = document.getElementById(CONTEINER_ID_NAME);
            if (requestConteiner == null)
            {
                var conteiner = document.createElement("div");
                conteiner.id = CONTEINER_ID_NAME;
                conteiner.style.position = "absolute";
                conteiner.style.height = "1px";
                conteiner.style.weigh = "1px";
                conteiner.style.top = "-20px";
                document.body.appendChild(conteiner);
                requestConteiner = document.getElementById(CONTEINER_ID_NAME);
            }
            // если пост запрос то создаем форму и постим в iframe
            if (isPost)
            {
                requestConteiner.innerHTML += "<iframe name='" + frameId + "' id='" + frameId + "' style='width: 0px; height: 0px; border: 0px; display:none;' src='" + src + "'></iframe>";;
                // создаем форму
                var frm = document.frames[frameId].document.createElement('form');
                frm.id = 'reqPostForm' + this.req_id;
                document.frames[frameId].document.appendChild(frm);
                frm = document.frames[frameId].document.getElementById('reqPostForm' + this.req_id);
                // устанавливаем необходимые параметры в форму
                frm.method = 'post';
                frm.action = this.url;
                frm.target = frameId;
                frm.style.position = "absolute";
                // складируем элементы на форму
                this.preparePostData(postBody, frm);
                // сабмитем форму
                customPlaceholder.clearPlaceholderVal();
                frm.submit();
            }
            // GET - фрейм создаем с помощью createElement, через   innerHTML в IE6 глючит
            else
            {
                var IFrameDoc = document.createElement('iframe');
                IFrameDoc.setAttribute('id', frameId);
                IFrameDoc.setAttribute('name', frameId);
                IFrameDoc.setAttribute('src', src);
                IFrameDoc.style.width = "0";
                IFrameDoc.style.height = "0";
                IFrameDoc.style.border = "0";
                requestConteiner.appendChild(IFrameDoc);
            }
        }
        catch(e)
        {
            return false;
        }

        this.readyState = 1;
        this.onreadystatechange();

        setTimeout(function()
        {
            self.IFht(2);
        }, 2);

    },

    overrideMimeType: function()
    {
    },

    getResponseHeader: function (name)
    {
        return '';
    },

    setRequestHeader: function (name, data)
    {
    },

    IFht: function (d)
    {
        var self = this;
        var el = document.getElementById('req' + self.req_id);
        if (el.readyState == 'complete')
        {
            self.responseText = document.frames['req' + self.req_id].document.body.innerHTML.replace(/[\n\r]+/ig, "");
            el.parentNode.removeChild(el);
            self.status = 200;
            self.readyState = 4;
            self.onreadystatechange();
        }
        else
        {
            d *= 1.5;
            setTimeout(function()
            {
                self.IFht(d);
            }, d);
        }
    }
};


function getWorkSpace ()
{
  return document.getElementById("workspaceCSA");
}

/**
 * Ишет детей с указыным именем класса
 * @param obj родительский элемент относительно которого ищются дети
 * @param className имя класса
 * @param resultNum количество результатов после которого можно остановить поиск если параметр опущен (или null) ищутся все дети
 * @param result техническая информация для обеспечения рекурсии.
 * @return: массив объектов или пустой массив
 */
function findChildsByClassName(obj, className, resultNum, result)
{
    var child;
    if (result == null) result = new Array();
    if (obj == undefined) return result;

    for (var childItem in obj.childNodes)
    {
        child = obj.childNodes[childItem];
        if (child != null && child.nodeType == 1)
        {
            if (child.className != undefined && child.className.search('\\b' + className + '\\b') != -1)
            {
                result.push(child);
                if ( result.length == resultNum ) return result;
            }
            result = findChildsByClassName(child, className, resultNum, result);
            if ( result.length == resultNum ) return result;
        }
    }

    return result;
}

//добавление выключаемого события
function addSwitchableEvent(elem, eventType, fn, key)
{
    if (key == null)
    {
        addEventListenerEx(elem, eventType, function(e) { fn(e); }, false);
        return ;
    }
    if (eventsKeyHash[key] != undefined)
    {
        eventsKeyHash[key] = true;
        return ;
    }

    eventsKeyHash[key] = true;
    addEventListenerEx(elem, eventType, function(e) { if ( window.eventsKeyHash[key] ) fn(e); }, false);
}

function addEventListenerEx(elem, eventType, delegate, capture)
{
	if (typeof(elem) == 'string')
	{
		if (!(elem = document.getElementById(elem)))
		{
			var msg = elem + ' element not found';
			alert(msg);
			throw msg;
		}
	}

	if (elem.addEventListener)
		elem.addEventListener(eventType, delegate, capture);
	else if (elem.attachEvent)
		elem.attachEvent('on' + eventType, delegate);
	else
		elem['on' + eventType] = delegate;
}


// Функция устанавливающая параметры блокируюшего дива с учетом размера workspace
function setTintedDiv(show)
{
    var workspace = getWorkSpace();
    if(show == null) return ;
    TintedNet.setTinted(workspace, show);

    // дополнительные действия при активизации или диактивизации тонировочного слоя
    aditionalTintedAction (show);
}

// дополнительные действия для затемнения при ожидании
function aditionalTintedAction(show)
{
    if (!isClientApp) return; // данное действие актуально только для клиентского приложения
    var footer = document.getElementById("footer");
    // если футер отсутствует действия которые идут дальше не имеют смысла
    if (footer == null) return;

    TintedNet.setTinted(footer, show);
}

function hideOrShow(elem, hide)
{
	elem = ensureElement(elem);

	if (hide == null)
		hide = elem.style.display != "none";

    if(elem!=null){
	    elem.style.display = hide ? "none" : "";
    }
}

function ensureElement(elem)
{
	if (typeof(elem) == 'string')
	{
		elem = document.getElementById(elem);
	}
	return elem;
}

function setPositionLikeObj (obj, likeObj, centerObj)
{
    obj = ensureElement(obj);
    var objPos = absPosition(obj);
    likeObj = ensureElement(likeObj);
    var likePos = absPosition(likeObj);
    if (centerObj!=null)
    {
        centerObj = ensureElement(centerObj);
        var centerObjPos = absPosition(centerObj);
        obj.style.left = objPos.left - (centerObjPos.left - likePos.left) + "px";
        obj.style.top =  objPos.top -  (centerObjPos.top - likePos.top) +"px";
    }
    else
    {
        obj.style.left = likePos.left+"px";
        obj.style.top = likePos.top+"px";
    }
}

function screenSize()
{
    var w, h; // w - длина, h - высота
    w = (window.innerWidth ? window.innerWidth : (document.documentElement.clientWidth ? document.documentElement.clientWidth : document.body.offsetWidth));
    h = (window.innerHeight ? window.innerHeight : (document.documentElement.clientHeight ? document.documentElement.clientHeight : document.body.offsetHeight));
    return {w:w, h:h};
}

/**
 * Функция для получения абсолютной позиции объекта
 * @param obj
 */
function absPosition(obj) {
     var left = 0;
     var top = 0;
    // workspaceDiv - необходим для правильного расчета координат в АРМ сотрудника. В клиентсткой части "workspaceDiv" нет на странице.
     while(obj && obj.id != "workspaceDiv") {
            left += obj.offsetLeft;
            top += obj.offsetTop;
            obj = obj.offsetParent;
      }
      return {left:left, top:top};
}

function switchEvent(key, off)
{
    if (eventsKeyHash[key] == undefined) return false;
    eventsKeyHash[key] = !((off != null)?off:eventsKeyHash[key]);
    return true;
}

/**
 * Метод для получения координаты центра рабоченей области относительно верха окна
 * @param div
 * @return topCenter середина рабочей области относительно верха окна
 *         abs абсолютное смещение рабочей области от края окна
 */
function workCenter (div)
{
    var workSpace = getWorkSpace (); // рабочая область
    var workPosition = absPosition(workSpace); // абсолютное смещение объекта от края окна
    var winSize = screenSize(); // размер видимой части
    var scrollTop = getScrollTop(); // кол-во скроленых пикселей
    var topOffset = winSize.h/2; //смешение снизу

    var relTop = 0; // высота шапки видемой клиенту части
    if (workPosition.top - scrollTop > 0) relTop = workPosition.top - scrollTop;
    var visHeight = winSize.h; // высота рабочей облости видимая клиенту
    if ( div.offsetHeight+70 >= workSpace.offsetHeight )
    {
        var footer = document.getElementById("footer"); // подвал
        var footerPosition = absPosition(footer);
        if (winSize.h + scrollTop > footerPosition.top)
            visHeight = winSize.h - (winSize.h + scrollTop - (footerPosition.top));
        return {
            topCenter : relTop + (visHeight - relTop) / 2 + scrollTop,
            abs: workPosition
        };
    }

    if (winSize.h+scrollTop > workSpace.offsetHeight + workPosition.top) // корректировка высоты
        visHeight = winSize.h - (winSize.h+scrollTop - (workSpace.offsetHeight + workPosition.top));

    return {
        topCenter: relTop + (visHeight - relTop) / 2 + scrollTop,
        abs: workPosition
    } ;
}


/**
 * Метод для определения является ли запрос с мобильного устройства
 * Телефона/планшета
 */
function isMobileDevice()
{
    var userAgentIgnorList = ["Mobile", "iPhone", "iPad", "iPod", "Mac"];
    for (var i=0; i<userAgentIgnorList.length; i++)
        if(navigator.userAgent.indexOf(userAgentIgnorList[i]) != -1)
            return true;

    return false;
}

//Возвращает сдвиг скора от начала страницы в пикселях
function getScrollTop()
{
    var result = 0;

    if (document.documentElement.scrollTop)
        result = document.documentElement.scrollTop;

    if (document.body.scrollTop)
        result = document.body.scrollTop;

    return result;
}


// Получить z-index по объекту.
function getZindex(obj){
   var highestIndex = 0;
   var currentIndex = 0;
   var elArray = Array();
   elArray.push(obj);
   var parent = obj.parentNode;
    while ( parent != document )
    {
        elArray.push(parent);
        parent = parent.parentNode;
    }

   for(var i=0; i < elArray.length; i++){
      if (elArray[i].currentStyle){
         currentIndex = parseFloat(elArray[i].currentStyle['zIndex']);
      }else if(window.getComputedStyle){
         currentIndex = parseFloat(document.defaultView.getComputedStyle(elArray[i],null).getPropertyValue('z-index'));
      }
      if(!isNaN(currentIndex) && currentIndex > highestIndex){ highestIndex = currentIndex; }
   }
   return(highestIndex);
}
/**
 * Почти объект описывающий поведение маскировочных (затеняющих) плашек
 */
var TintedNet = {
    RESIZE_EVENT: "ResizeEvent", // постфикс для событий изменения размера
    tintedCount: 0,
    tinted : [],
    /**
     * Установить затемнение для элемента
     * @param element элемент
     * @param show флаг затемнения (показать/спрятать)
     */
    setTinted : function (element, show) {
        element = ensureElement(element);
        var tinted = this.getTintElement(element);
        // озможно что элемента show не будет это означает что произошло событие
        // такое как ресайз окна и необходимо поменять размеры затемнения
        if (show == null)
            show = !(tinted.style.display == "none");

        if (!show) {
            hideOrShow(tinted, true);
            return;
        }


        hideOrShow(tinted, false);

        tinted.style.width = element.offsetWidth+"px";
        tinted.style.height = element.offsetHeight+"px";
        tinted.style.zIndex = getZindex(element)+10;

        var divs = tinted.getElementsByTagName("div");
        for (var i =0 ; i< divs.length; i++)
        {
            divs[i].style.width = tinted.style.width;
            divs[i].style.height = tinted.style.height;
        }

        switchEvent(tinted.id+this.RESIZE_EVENT, !show); // управляем собитиями
        setPositionLikeObj (tinted, element);
    },
    /**
     * Получить ссылку на затемняющий контейнер который принадлежит element
     * @param element элемент который необходимо затемнять
     * @return Object затемняющий элемент
     */
    getTintElement: function (element) {
        element = ensureElement(element);
        if (element.tinted) return element.tinted;

        //Тонирующий элемент отсутствует, добавлем его

        var tinted = document.createElement("div"); // обертка сетки
        tinted.setAttribute("id", "tinted"+this.tintedCount);
        tinted.className = "tintedWrapper";
        document.body.appendChild(tinted); //

        tinted = document.getElementById("tinted"+this.tintedCount);
        hideOrShow(tinted, true);
        // тонировка, затемнение
        var opacity = document.createElement("div");
        opacity.className = "tinted opacity25";
        tinted.appendChild(opacity);
        // сетка
        var tintedNet = document.createElement("div");
        tintedNet.className = "tintedNet";
        tinted.appendChild(tintedNet);


        // добавляем и увеличиваем
        this.tintedCount++;
        this.tinted.push(tinted);
        // добавляем выключаемое событие на ресайз документа
        var self = this; // замыкание
        addSwitchableEvent(window, "resize", function () { self.setTinted(element);}, tinted.id+this.RESIZE_EVENT);
        switchEvent(tinted.id+this.RESIZE_EVENT, true); // по умолчанию событие не действительное

        element.tinted = tinted;
        return tinted;
    },
    /**
     * Отобразить или спрятать все затемняющие элементы
     * @param hide флаг (спраятать/показать)
     */
    hideOrShowAllTinted: function (hide) {
       for (var i = 0; i < this.tinted; i++)
            this.setTinted(this.tinted[i], !hide);
    }
};

function oneClickFilterDateChange (name, type, isSimpleTrigger)
{
    var CLASS_NAME_NO_ACTIVE = "date-filter-no-active"; //"greenSelector";
    var CLASS_NAME_ACTIVE = "date-filter-active";
    var curentType = getElement('filter(type' + name + ')');
    if (isSimpleTrigger == null) isSimpleTrigger = false;
    if (curentType.value == type) return;
    // deselect
    var oldElement = document.getElementById("type"+name+curentType.value);
    if (oldElement != undefined )
        oldElement.className = CLASS_NAME_NO_ACTIVE;
    // select
    document.getElementById("type" + name + type).className = CLASS_NAME_ACTIVE;
    var filterButton = findChildByClassName(curentType.parentNode, "filterButton"); // кнопка фильтра
    var moreLink = findChildByClassName(curentType.parentNode, "showHideMoreFilterLink"); // ссылка на рассширенный фильтр
    // moreLink.innerHTML != moreLink.rel - означает что расширенный фильтр закрыт
    var moreFilterIsClosed = moreLink == undefined || moreLink != undefined && moreLink.innerHTML != moreLink.rel;
    // кнопку показать необходимо спрятать
    if (moreLink != undefined )
    {
        if (moreLink.originData != undefined )
            moreLink.originData.filterButtonDisplay = false;
    }
    // новый параметр
    if (type == 'period')
    {
        document.getElementById("type"+name+type+"Detail").style.display = '';
        if (moreLink != undefined )
        {
            if (moreLink.originData != undefined )
                moreLink.originData.filterButtonDisplay = true;

            if (moreFilterIsClosed && filterButton != undefined )
                hideOrShow(filterButton, true);
        }
        else
            if (filterButton != undefined )
                hideOrShow(filterButton, false);
    }
    else {
       if (filterButton != undefined && !isSimpleTrigger)
            hideOrShow(filterButton, true);
    }
    // текущий, прошлый
    if (curentType.value == 'period')
        document.getElementById("type"+name+curentType.value+"Detail").style.display = 'none';

    curentType.value = type;
}

// Ишет первого ребенка с указыным именем класса
//@return: undefined или объект
function findChildByClassName(obj, className)
{
    if (obj == undefined) return undefined;

    var result = findChildsByClassName(obj, className, 1);
    if (result.length == 0 ) return undefined;
    else return result[0];
}

function getElement(name)
{
	try
	{
		var el = document.getElementsByName(name);
		return el[0];
	}
	catch (e)
	{
		return null;
	}
}

/**
 * Метод отвечающий за логику расскрытия и закрытия дополнительных параметров фильтра
 * @param obj
 * @param hiddenValue
 */
function openMoreFilter (obj, hiddenValue)
{
    var moreFilter = findChildByClassName(obj.parentNode.parentNode, "filterMore"); // контейнер расширенного фильтра
    var filterButton = findChildByClassName(obj.parentNode.parentNode, "filterButton"); // кнопка не расширенного фильтра

    var isOpenEvent = obj.innerHTML != obj.rel; // флаг отвечающий на вопрос открыть или закрыть фильтр
    // obj.originData хранилище орегинальных, истеных значений элемента
    if (obj.originData == undefined)
            obj.originData = { innerHTML : obj.innerHTML, className: obj.className};

    if (isOpenEvent) obj.originData.filterButtonDisplay = filterButton.style.display == "";

    hideOrShow(filterButton, true);

    hideOrShow(moreFilter, !isOpenEvent);

    if (isOpenEvent)
    {
        obj.innerHTML = obj.rel;
        obj.rel = obj.innerHTML;
        obj.className += " opened";
        setElement(hiddenValue, "true"); // сохраняем флаг открытие/закрытия
        return ;
    }

    setElement(hiddenValue, "false"); // сохраняем флаг открытие/закрытия

    if (obj.originData != undefined)
    {
        obj.innerHTML = obj.originData.innerHTML;
        obj.className = obj.originData.className;
    }
}

function setElement(name, val)
{
  try {
      $('*[name='+name+']').get(0).value = val;
  }
  catch (e) {alert(e.description);}
}

function ensureElement(elem)
{
	if (typeof(elem) == 'string')
	{
		elem = document.getElementById(elem);
	}
	return elem;
}

function hideOrShow(elem, hide)
{
	elem = ensureElement(elem);

	if (hide == null)
		hide = elem.style.display != "none";

    if(elem!=null){
	    elem.style.display = hide ? "none" : "";
    }
}

// Функция, следящая за правильностью использования свойства disabled полей фильтра периода
function filterDateChange (name)
{
    var type = getRadioValue(document.getElementsByName('filter(type' + name + ')'));
    var from = getElement("filter(from" + name + ")");
    var to = getElement("filter(to" + name + ")");
    if (from == null || to == null)
        return ;

    var disabled = true;
    if (type == null || type == 'period')
      disabled = false;

    to.disabled = disabled;
    from.disabled = disabled;
}

function getRadioValue(radio)
{
	if (radio.length > 0)
	{
		for (var i = 0; i < radio.length; i++)  if (radio[i].checked) return radio[i].value;
		return null;
	}
	else  return (radio.checked ? radio.value : null);
}

// блок подождите
var clientBeforeUnload = {
    // тригер который отключает "загрузчик" на ближайщий вызов
    showTrigger: true,
    /**
     * метод реализующий логику отображения блока "подождите"
     * @param timeout
     */
    show: function (timeout) {
        if (!this.showTrigger)
        {
            this.showTrigger = true;
            return;
        }
        var myself = this;
        setTintedDiv(true);
        timeout = (timeout == null) ? true : timeout;
        if (timeout)
        {
            // Смещение времени перед началм показа лоудера в мс
            var timeShift = 1000; // 1c

            setTimeout(function ()
            {
                myself.show(false)
            }, timeShift);
            return;
        }
        // Для предотвращения падения при нажатии на командную кнопку в Opera
        // на страницах где нет прелоудера необходимо отлавливать и глушить скриптовую ошибку
        try
        {
            showOrHideWaitDiv();
        }
        catch (e)
        {

        }
        addSwitchableEvent(window, "resize", function ()
        {
            setLoadingStyle();
        }, "loadindDivResizeEvent");
    },

    /**
     * метод для инициализации функционала блока "Подождите"
     */
    init: function () {
        var myself = this;
        if (window.opera)
        {
            // opera не понимает событие beforeunload. Для нее устанавливаем событие onclick для каждого тега a
            var tags = document.getElementsByTagName("a");
            for (var i = 0; i < tags.length; i++)

                if (tags[i].href.indexOf("#") < 0 && tags[i].href.indexOf("javascript:") < 0
                        && tags[i].target.toLowerCase() != '_blank' && tags[i].onclick == null)
                {
                    tags[i].onclick = function ()
                    {
                        myself.show(false);
                    };
                }
            return;
        }

        window.onbeforeunload = function () { myself.show(); };
    }

};

/* Функция для отображения и скрытия  */
function showOrHideWaitDiv(show, tintedZIndex)
{
    var FAR_FAR_AWAY = -3300; // число px на которое необходимо сместить влево для того чтобы спрятать
    show = (show == null) ? true : show;
    var needTined = true;
    if (window.win != undefined)
        needTined = win.active == null;

    var loading = ensureElement("loading");

    if (!show)
    {
        if (needTined)
            setTintedDiv(show);
        //hide
        loading.style.left = FAR_FAR_AWAY+"px";
        loading.style.display = "none";
        win.tinedWindows(show);
        switchEvent("modalWindowScrollEvent", false);
        return;
    }

    switchEvent("modalWindowScrollEvent", true);
    win.tinedWindows(show);

    loading.divFloat = new DivFloat(loading);
    loading.style.left = "";
    loading.style.display = "";
    setLoadingStyle();
    var scrollTimer; // таймер перед началом
    // если собитие скрола отсутствует добавляем его
    addSwitchableEvent(window, 'scroll', function () {
            clearTimeout(loading.divFloat.floatTimer);
            var reg = /\d*/
            var fromPx = parseInt(reg.exec(loading.style.top));
            clearTimeout(scrollTimer)

            scrollTimer = setTimeout(function(){
                loading.divFloat.floatEffect(fromPx, workCenter(loading).topCenter - loading.offsetHeight / 2);
                }, 50);

        }, "loadingScrollEvent");
    if (needTined)
        setTintedDiv(show);

    if (navigator.userAgent.indexOf("MSIE") >= 0)
    {
        var imgDiv = ensureElement("loadingImg");
        var src = imgDiv.getElementsByTagName('img')[0].src;
        imgDiv.innerHTML = '<img src="' + src + '"/>';
    }
}

/**
 * Метод для отображения и скрытия прелоудера.
 * @param isShow
 */
function showOrHideAjaxPreloader (isShow)
{
    if (!isClientApp) return; // данный метод актуален только для клиентского приложения
    if (isShow == null) isShow = true;
    var loading = ensureElement("loading"); // необходим для хранения счетчика таймоута
    //show
    if (isShow)
    {
        document.body.style.cursor = "progress";
        if (isClientApp) loading.preloaderTimer = setTimeout(function ()
        {
            showOrHideWaitDiv(true);
        }, 1000);
        return ;
    }

    // hide
    document.body.style.cursor = "auto";
    if (loading.preloaderTimer != undefined)
        clearTimeout(loading.preloaderTimer);
    showOrHideWaitDiv(false);
}

function setLoadingStyle ()
{
    var workspace = getWorkSpace();
    var loading = ensureElement("loading");
    loading.style.top = workCenter (loading).topCenter - loading.offsetHeight/2 + "px";
    var position = absPosition(workspace); // Абсолютная позиция рабочей области
    loading.style.left = (position.left+workspace.offsetWidth/2 - loading.offsetWidth/2) + "px";
}

function trim(val)
{
	var s = new String(val);
	s = s.replace(/^\s+/, "");
	return  s.replace(/\s+$/, "");
}

/* Функция для складирования функций необходимых выполнить после полной загрузки DOM */
function doOnLoad (funct, params)
{
    var onLoad = window.onload;
    window.onload = function () { if (onLoad != null) onLoad(); funct(params); };
}

// Определить, есть ли у объекта класс
//@return: true - класс найден, false - не найден
function findClassName(obj, className)
{
   return obj != undefined && obj.className != undefined && findClass(obj, className);
}

function findClass(obj, className)
{
    return obj.className.search('\\b' + className + '\\b') >= 0;
}

// Ишет первого первого родителя с указыным именем класса
//@return: undefined или объект
function findParentByClassName(obj, className)
{
    while (obj != undefined && obj.className != undefined && !findClass(obj, className))
    {
        obj = obj.parentNode;
    }

    return obj;
}

function displayError(error)
{
    var err = error;
    if (error.indexOf(":") >= 0)
    {
        var fieldName = trim(error).split(":")[0];
        err = trim(error).substring(fieldName.length + 1); //+1 --- символ :
        if (fieldName.length > 0)
        {
            $('*[name=field('+fieldName+')]').addClass("error errorDiv");
            if (fieldName == "password")
            {
                $("#passText").addClass("errorDiv");
                $("#password").addClass("error errorDiv");
            }

            $('*[name=field('+fieldName+')]').focus(function()
            {
                $('.errorDiv').removeClass("error errorDiv");
            });
        }
    }
    displayErrors(err);
}


function displayErrors(errors)
{
    $("#errorForm .error-message").html("<div class=\"error-message\">" + errors + "</div>");
    win.open("errorForm");
}

function callOperation(event, operation, confirm)
{
	var frm = document.forms.item(0);
	preventDefault(event);
    if (confirm != null && confirm != '' && ! window.confirm(confirm))
		return false;

	if (frm.onsubmit != null && !frm.onsubmit())
		return false;

	if(window.clearMasks != null)
	{
		try { clearMasks(event); } catch (e) {}
	}

	var operationField = getElementFromCollection(frm.elements,'operation');

	if (operationField == null)
	{
		addField('hidden', 'operation', operation);
	}
	else
	{
		operationField.value = operation;
	}
    customPlaceholder.clearPlaceholderVal();
    frm.submit();

	return true;
}

function preventDefault(event)
{
    if  (event != null)
    {
        try
        {
            if (event.preventDefault)
               event.preventDefault();
            // для IE<9
            else
               event.returnValue = false;
        }
        catch(e)
        {
        }
   }
}

function getElementFromCollection(object,elementName)
{
	for(var i=0;i<object.length;i++)
	{
		if(object[i].name == elementName)
			return object[i];
	}
	return null;
}

function addField(fieldType, fieldName, fieldValue)
{
	if (document.getElementById)
	{
		var input = document.createElement('INPUT');
		if (document.all)
		{ // what follows should work
			// with NN6 but doesn't in M14
			input.type = fieldType;
			input.name = fieldName;
			input.value = fieldValue;
		}
		else if (document.getElementById)
		{ // so here is the
			// NN6 workaround
			input.setAttribute('type', fieldType);
			input.setAttribute('name', fieldName);
			input.setAttribute('value', fieldValue);
		}
		document.forms[0].appendChild(input);
	}
}

/* Скриптовая пагинация */
var BACK_ACTIVE_PAGINATION_BUTTON = '<a href="#" onclick="nextValues(this, true); return false;">'+
                                        '<div class="activePaginLeftArrow"></div>'+
                                    '</a>';
var NEXT_ACTIVE_PAGINATION_BUTTON = '<a href="#" onclick="nextValues(this, false); return false;">'+
                                        '<div class="activePaginRightArrow"></div>'+
                                    '</a>';
var BACK_INACTIVE_PAGINATION_BUTTON =  '<div class="inactivePaginLeftArrow"></div>';
var NEXT_INACTIVE_PAGINATION_BUTTON =  '<div class="inactivePaginRightArrow"></div>';


function nextValues(elem, back)
{
    var cl_style = "cursor:pointer;cursor:hand";
    var hide_style="display:none"
    var pagination_size = parseInt($('[name=$$pagination_size0]').val());
    var pagination_offset = parseInt($('[name=offset]').val());
    $('[name=offset]').val(pagination_offset + (back ? -1 : 1) * pagination_size);
    pagination_offset = parseInt($('[name=offset]').val());

    var i = 0;
    $('[class^=ListLine]').each(function(){
        if($(this).css('display')=="none" && pagination_offset <= i && i < pagination_offset + pagination_size)
        {
            $(this).attr('style', cl_style);
        }
        else
        {
            $(this).attr('style', hide_style);
        }
        i++;
    });

    var buttons = "";

    if(pagination_offset == 0)
    {
        buttons += BACK_INACTIVE_PAGINATION_BUTTON;
    }

    if(pagination_offset > 0)
    {
        buttons += BACK_ACTIVE_PAGINATION_BUTTON;
    }

    if(i <= pagination_offset + pagination_size)
    {
        buttons += NEXT_INACTIVE_PAGINATION_BUTTON;
    }

    if(i > pagination_offset + pagination_size)
    {
        buttons += NEXT_ACTIVE_PAGINATION_BUTTON;
    }

    $(elem).parents('td:first').html(buttons);
}

function moreValues(elem)
{
    $('[name=$$pagination_size0]').val(parseInt($(elem).text()));
    $('[name=offset]').val(-parseInt($(elem).text()));
    $('.paginationSize').each(function(){
        var size = trim($(this).text());
        if(this == $(elem).parents('.paginationSize:first')[0])
        {
            $(this).html('<div class="greenSelector"><span>'+size+'</span></div>');
        }
        else
        {
            $(this).html('<a href="#" onclick="moreValues(this); return false;">'+size+'</a>');
        }
    });
             
    $('[class^=ListLine]:visible').hide();
    nextValues($('#pagination').find('[class$=PaginRightArrow]'), false);
}

function onCsaFilterEnterKey(e)
{
	var kk = navigator.appName == 'Netscape' ? e.which : e.keyCode;

    if(kk == 13)
    {               
        callOperation(null, 'button.filter');      
    }
}


//Методы для справочника регионов
/**
 * метод обработки результата ajax запроса справочника регионов
 * @param msg - ajax данные
 */
function regionAjaxResult(msg)
{
    if (trim(msg) == 'OK')
    {
        win.close('regionsDiv');
        document.getElementById('regionNameSpan').innerHTML = clickRegion.currentRegionName;
        if (clickRegion.currentRegionName == 'Все регионы')
            $('.regionsAlphabetList .currentRegionName span').text('регион не выбран');
        else
            $('.regionsAlphabetList .currentRegionName span').text(clickRegion.currentRegionName);
    }
    else if(trim(msg) == '')
        location.reload();
    else
        document.getElementById("regionsDiv").innerHTML = msg;
}

var REGION_DICTIONARY_URL = ''; // устанавливается позже в header

var clickRegion = {
    lock: false,
    currentRegionName: null,
    //клик по региону
    click: function (id, name)
    {
        if (this.lock) return;
        this.lock = true;
        var myself = this;
        this.currentRegionName = name;
        regionClick(myself, id, name);
    },
    //выбор региона
    choose: function(id, name)
    {
        if (this.lock) return;
        this.lock = true;
        var myself = this;
        this.currentRegionName = name;
        selectRegion(myself, id, name);
    }
};

/**
 * Функция для справочника регионов, отвечает за клик по региону
 * @param id ИД региона
 */
function regionClick(myself, id)
{
    var params = '';
    if (id > 0)
        params = 'id=' + id;

    ajaxQuery (params, REGION_DICTIONARY_URL, function(data){regionAjaxResult(data); reloadNews(); reloadBlockingMessage(); myself.lock = false;});
}

/**
 * Функция для справочника регионов, которая отвечает за выбор региона
 * @param id региона
 */
function selectRegion(myself, id)
{
    //Флаг select однозначно говорит, что узнанный id необходимо выбрать
    var params = 'select=true';
    params = params + '&id=' + id;
    document.body.style.cursor = "progress";
    ajaxQuery (params, REGION_DICTIONARY_URL, function(data){regionAjaxResult(data); reloadNews(); reloadBlockingMessage(); myself.lock = false;});
}

var buttons = new Array();

function createSubmitButton(commandId, isDefault, validationFunction, clearFormAction)
{
    var button = new buttonObject(commandId, isDefault);
    button.setValidationFunction(validationFunction);
    button.setClearFormAction(clearFormAction);
    buttons[commandId] = button;
    return button;
}

function createAjaxButton(commandId, isDefault, validationFunction, afterAjax)
{
    var button = new buttonObject(commandId, isDefault);
    button.setValidationFunction(validationFunction);
    button.setUseAjax(true);
    button.setAfterAjax(afterAjax);
    buttons[commandId] = button;
    return button;
}

function createClientButton(commandId, isDefault, onClick)
{
    var button = new buttonObject(commandId, isDefault);
    button.setOnClick(onClick);
    buttons[commandId] = button;
    return button;
}

function getButton(commandId)
{
    return buttons[commandId];
}

function getParentForm(element)
{
    return $(element).parents('form');
}

function getDefaultButton(form)
{
    return $(form).find('.commandButton.default');
}

function clickDefButtonIfEnterKeyPress(element, event)
{
    var code = navigator.appName == 'Netscape' ? event.which : event.keyCode;
    if(code != 13)
        return;
    getDefaultButton(getParentForm(element)).click();
    // для DOM-совместимых браузеров
    preventDefault(event);
}

function buttonObject(commandId, isDefault)
{
	this.id                 = commandId;
    this.enabled            = true;
    this.clearFormAction    = false;
    this.isDefault          = isDefault;
    this.validationFunction = function(){return true;};
    this.useAjax            = false;
	this.afterAjax          = function(){};
    this.onclick            = function(){
                                            // функция валидации
                                            if (!this.validationFunction())
                                                return false;

                                            // форма, на каоторой находится кнопка
                                            var form = getParentForm($('[id="commandButton_'+ commandId + '"]'));
                                            // добавляем на форму информацию об операции
                                            addOperationInfo(form, this.id);

                                            // если не аяксовая кнопка, то просо сабмитим текущую форму
                                            if (!this.useAjax)
                                            {
                                                customPlaceholder.clearPlaceholderVal();
                                                if  (this.clearFormAction)
                                                    form.attr("action", "");
                                                return form.submit();
                                            }

                                            // аякс
                                            // вытаскиваем параметры с формы
                                            var parameters = form.find(':input').filter(':not(.inputPlaceholder)').serializeToWin();
                                            // удаляем с формы информацию об операции
                                            removeOperationInfo(form);
                                            // получаем экшен формы
                                            var action = form.attr('action');
                                            // отправляем запрос
                                            return ajaxQuery(parameters, action, this.afterAjax);
                                        };

    

    function addOperationInfo(form, info)
    {
        return form.append('<input type="hidden" name="operation" value="' + info + '"/>');
    }

    function removeOperationInfo(form)
    {
        return form.find('input[name=operation]').remove();
    }

    this.setValidationFunction = function(validationFunction)
    {
        if (validationFunction != null)
            this.validationFunction = validationFunction;
    };

    this.setClearFormAction = function(clearFormAction)
    {
        this.clearFormAction = clearFormAction;
    };

    this.setUseAjax = function(useAjax)
    {
        this.useAjax = useAjax;
    };

    this.setAfterAjax = function(afterAjax)
    {
        if (afterAjax != null)
            this.afterAjax = afterAjax;
    };

    this.setOnClick = function(onclick)
    {
        if (onclick != null)
            this.onclick = onclick;
    };

    this.setEnabled = function(enabled)
    {
        $('[id="commandButton_'+ commandId + '"]').toggleClass("Disabled", !enabled);
        this.enabled = enabled;
    };

    this.click = function()
    {
        if (this.enabled)
            this.onclick();
    };
}
