
// ������������� �������
// ! ������� �� ���������, � ���� �����������
// @return: boolean true ������� ����� ��� false � ������ ������
var eventsKeyHash = {};

var isClientApp = true;

/**
 * ������� ��� �������� ajax
 * @param params ��������� (���=��������?���=��������, ���� ���������� ��� �������� null(�� ����� ������� ��� �������)
 * @param url
 * @param callback ������� ��� ��������� ������
 * @param type �������������� �������� ��� ����������� ���� ������ (������ ������������ ������ ��� JSON)
 * @param showPreLoader ��������, ���������� �� ������������� ����������� ����������. �� ��������� true
 * @param errorAction �������, ���������� ��� ������
 */
function ajaxQuery(params, url, callback, type, showPreLoader, errorAction)
{
    var requestType = params == null ? "GET" : "POST";
    showPreLoader = showPreLoader == null ? true : showPreLoader;
    /*���� ��� ���������� ���������� GET ������*/
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
 * ����� ��� ���������� ���� �������� ��������� � ajax ������
 * @param msg ajax ������
 */
function evalAjaxJS(msg)
{
    // ���� � ���������� ��� ������ �� ������ �� ���������� ���������� �� ���������
   if (msg.toLowerCase().indexOf("script") == -1) return;
   // ������� ������� ��� ��������
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
           alert("��� ������� ���������������� �������, ���������� ����� AJAX, �������� ������:\n" + ex.message + "\n script = " + i + "\n" + script[i].innerHTML + "\n" + msg);
           throw(ex);
       }
   }
    if ( window.onload!= null ) window.onload();
    tmpDiv.parentNode.removeChild(tmpDiv);
}

var UrlEncodeDecode = {

	// ��������� ������� ��� ����������� URL
	encode : function (string) {
		return escape(this._utf8_encode(string));
	},

	// ��������� ������� ��� ������������� URL
	decode : function (string) {
		return this._utf8_decode(unescape(string));
	},

	// ��������� ������� ��� ����������� URL
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

	// ��������� ������� ��� ������������� URL
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
// ������ ���������� ��������� ������ ��� ��6
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
     * ����� ��� ������������� ������ ���� param=value&param2=value2.. �� �����
      * @param postData ������
     * @param frm �����
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
        // ���������� ����������� �� ����.
        // ����� ������ ����� ������ �������� ������������� �����, � ��� ���� ��� ������, ������ �� ��� � �� ���� �����������, ��� ��� ���������� ��� � ���� �� ������� �� ���������� ����.
        var isPost = this.protocol.toUpperCase() == 'POST' && postBody!= null && postBody != '' && urlWithParams.length>IE_ADDRES_LENGTH_LIMIT;
        // ���������� ��� ���� ����� �������� �������������� � �� ����������� ��������
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
            // ���� ���� ������ �� ������� ����� � ������ � iframe
            if (isPost)
            {
                requestConteiner.innerHTML += "<iframe name='" + frameId + "' id='" + frameId + "' style='width: 0px; height: 0px; border: 0px; display:none;' src='" + src + "'></iframe>";;
                // ������� �����
                var frm = document.frames[frameId].document.createElement('form');
                frm.id = 'reqPostForm' + this.req_id;
                document.frames[frameId].document.appendChild(frm);
                frm = document.frames[frameId].document.getElementById('reqPostForm' + this.req_id);
                // ������������� ����������� ��������� � �����
                frm.method = 'post';
                frm.action = this.url;
                frm.target = frameId;
                frm.style.position = "absolute";
                // ���������� �������� �� �����
                this.preparePostData(postBody, frm);
                // �������� �����
                customPlaceholder.clearPlaceholderVal();
                frm.submit();
            }
            // GET - ����� ������� � ������� createElement, �����   innerHTML � IE6 ������
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
 * ���� ����� � �������� ������ ������
 * @param obj ������������ ������� ������������ �������� ������ ����
 * @param className ��� ������
 * @param resultNum ���������� ����������� ����� �������� ����� ���������� ����� ���� �������� ������ (��� null) ������ ��� ����
 * @param result ����������� ���������� ��� ����������� ��������.
 * @return: ������ �������� ��� ������ ������
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

//���������� ������������ �������
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


// ������� ��������������� ��������� ������������ ���� � ������ ������� workspace
function setTintedDiv(show)
{
    var workspace = getWorkSpace();
    if(show == null) return ;
    TintedNet.setTinted(workspace, show);

    // �������������� �������� ��� ����������� ��� ������������� ������������� ����
    aditionalTintedAction (show);
}

// �������������� �������� ��� ���������� ��� ��������
function aditionalTintedAction(show)
{
    if (!isClientApp) return; // ������ �������� ��������� ������ ��� ����������� ����������
    var footer = document.getElementById("footer");
    // ���� ����� ����������� �������� ������� ���� ������ �� ����� ������
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
    var w, h; // w - �����, h - ������
    w = (window.innerWidth ? window.innerWidth : (document.documentElement.clientWidth ? document.documentElement.clientWidth : document.body.offsetWidth));
    h = (window.innerHeight ? window.innerHeight : (document.documentElement.clientHeight ? document.documentElement.clientHeight : document.body.offsetHeight));
    return {w:w, h:h};
}

/**
 * ������� ��� ��������� ���������� ������� �������
 * @param obj
 */
function absPosition(obj) {
     var left = 0;
     var top = 0;
    // workspaceDiv - ��������� ��� ����������� ������� ��������� � ��� ����������. � ����������� ����� "workspaceDiv" ��� �� ��������.
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
 * ����� ��� ��������� ���������� ������ ��������� ������� ������������ ����� ����
 * @param div
 * @return topCenter �������� ������� ������� ������������ ����� ����
 *         abs ���������� �������� ������� ������� �� ���� ����
 */
function workCenter (div)
{
    var workSpace = getWorkSpace (); // ������� �������
    var workPosition = absPosition(workSpace); // ���������� �������� ������� �� ���� ����
    var winSize = screenSize(); // ������ ������� �����
    var scrollTop = getScrollTop(); // ���-�� ��������� ��������
    var topOffset = winSize.h/2; //�������� �����

    var relTop = 0; // ������ ����� ������� ������� �����
    if (workPosition.top - scrollTop > 0) relTop = workPosition.top - scrollTop;
    var visHeight = winSize.h; // ������ ������� ������� ������� �������
    if ( div.offsetHeight+70 >= workSpace.offsetHeight )
    {
        var footer = document.getElementById("footer"); // ������
        var footerPosition = absPosition(footer);
        if (winSize.h + scrollTop > footerPosition.top)
            visHeight = winSize.h - (winSize.h + scrollTop - (footerPosition.top));
        return {
            topCenter : relTop + (visHeight - relTop) / 2 + scrollTop,
            abs: workPosition
        };
    }

    if (winSize.h+scrollTop > workSpace.offsetHeight + workPosition.top) // ������������� ������
        visHeight = winSize.h - (winSize.h+scrollTop - (workSpace.offsetHeight + workPosition.top));

    return {
        topCenter: relTop + (visHeight - relTop) / 2 + scrollTop,
        abs: workPosition
    } ;
}


/**
 * ����� ��� ����������� �������� �� ������ � ���������� ����������
 * ��������/��������
 */
function isMobileDevice()
{
    var userAgentIgnorList = ["Mobile", "iPhone", "iPad", "iPod", "Mac"];
    for (var i=0; i<userAgentIgnorList.length; i++)
        if(navigator.userAgent.indexOf(userAgentIgnorList[i]) != -1)
            return true;

    return false;
}

//���������� ����� ����� �� ������ �������� � ��������
function getScrollTop()
{
    var result = 0;

    if (document.documentElement.scrollTop)
        result = document.documentElement.scrollTop;

    if (document.body.scrollTop)
        result = document.body.scrollTop;

    return result;
}


// �������� z-index �� �������.
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
 * ����� ������ ����������� ��������� ������������� (����������) ������
 */
var TintedNet = {
    RESIZE_EVENT: "ResizeEvent", // �������� ��� ������� ��������� �������
    tintedCount: 0,
    tinted : [],
    /**
     * ���������� ���������� ��� ��������
     * @param element �������
     * @param show ���� ���������� (��������/��������)
     */
    setTinted : function (element, show) {
        element = ensureElement(element);
        var tinted = this.getTintElement(element);
        // ������� ��� �������� show �� ����� ��� �������� ��� ��������� �������
        // ����� ��� ������ ���� � ���������� �������� ������� ����������
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

        switchEvent(tinted.id+this.RESIZE_EVENT, !show); // ��������� ���������
        setPositionLikeObj (tinted, element);
    },
    /**
     * �������� ������ �� ����������� ��������� ������� ����������� element
     * @param element ������� ������� ���������� ���������
     * @return Object ����������� �������
     */
    getTintElement: function (element) {
        element = ensureElement(element);
        if (element.tinted) return element.tinted;

        //���������� ������� �����������, �������� ���

        var tinted = document.createElement("div"); // ������� �����
        tinted.setAttribute("id", "tinted"+this.tintedCount);
        tinted.className = "tintedWrapper";
        document.body.appendChild(tinted); //

        tinted = document.getElementById("tinted"+this.tintedCount);
        hideOrShow(tinted, true);
        // ���������, ����������
        var opacity = document.createElement("div");
        opacity.className = "tinted opacity25";
        tinted.appendChild(opacity);
        // �����
        var tintedNet = document.createElement("div");
        tintedNet.className = "tintedNet";
        tinted.appendChild(tintedNet);


        // ��������� � �����������
        this.tintedCount++;
        this.tinted.push(tinted);
        // ��������� ����������� ������� �� ������ ���������
        var self = this; // ���������
        addSwitchableEvent(window, "resize", function () { self.setTinted(element);}, tinted.id+this.RESIZE_EVENT);
        switchEvent(tinted.id+this.RESIZE_EVENT, true); // �� ��������� ������� �� ��������������

        element.tinted = tinted;
        return tinted;
    },
    /**
     * ���������� ��� �������� ��� ����������� ��������
     * @param hide ���� (���������/��������)
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
    var filterButton = findChildByClassName(curentType.parentNode, "filterButton"); // ������ �������
    var moreLink = findChildByClassName(curentType.parentNode, "showHideMoreFilterLink"); // ������ �� ������������ ������
    // moreLink.innerHTML != moreLink.rel - �������� ��� ����������� ������ ������
    var moreFilterIsClosed = moreLink == undefined || moreLink != undefined && moreLink.innerHTML != moreLink.rel;
    // ������ �������� ���������� ��������
    if (moreLink != undefined )
    {
        if (moreLink.originData != undefined )
            moreLink.originData.filterButtonDisplay = false;
    }
    // ����� ��������
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
    // �������, �������
    if (curentType.value == 'period')
        document.getElementById("type"+name+curentType.value+"Detail").style.display = 'none';

    curentType.value = type;
}

// ���� ������� ������� � �������� ������ ������
//@return: undefined ��� ������
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
 * ����� ���������� �� ������ ���������� � �������� �������������� ���������� �������
 * @param obj
 * @param hiddenValue
 */
function openMoreFilter (obj, hiddenValue)
{
    var moreFilter = findChildByClassName(obj.parentNode.parentNode, "filterMore"); // ��������� ������������ �������
    var filterButton = findChildByClassName(obj.parentNode.parentNode, "filterButton"); // ������ �� ������������ �������

    var isOpenEvent = obj.innerHTML != obj.rel; // ���� ���������� �� ������ ������� ��� ������� ������
    // obj.originData ��������� ������������, ������� �������� ��������
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
        setElement(hiddenValue, "true"); // ��������� ���� ��������/��������
        return ;
    }

    setElement(hiddenValue, "false"); // ��������� ���� ��������/��������

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

// �������, �������� �� ������������� ������������� �������� disabled ����� ������� �������
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

// ���� ���������
var clientBeforeUnload = {
    // ������ ������� ��������� "���������" �� ��������� �����
    showTrigger: true,
    /**
     * ����� ����������� ������ ����������� ����� "���������"
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
            // �������� ������� ����� ������ ������ ������� � ��
            var timeShift = 1000; // 1c

            setTimeout(function ()
            {
                myself.show(false)
            }, timeShift);
            return;
        }
        // ��� �������������� ������� ��� ������� �� ��������� ������ � Opera
        // �� ��������� ��� ��� ���������� ���������� ����������� � ������� ���������� ������
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
     * ����� ��� ������������� ����������� ����� "���������"
     */
    init: function () {
        var myself = this;
        if (window.opera)
        {
            // opera �� �������� ������� beforeunload. ��� ��� ������������� ������� onclick ��� ������� ���� a
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

/* ������� ��� ����������� � �������  */
function showOrHideWaitDiv(show, tintedZIndex)
{
    var FAR_FAR_AWAY = -3300; // ����� px �� ������� ���������� �������� ����� ��� ���� ����� ��������
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
    var scrollTimer; // ������ ����� �������
    // ���� ������� ������ ����������� ��������� ���
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
 * ����� ��� ����������� � ������� ����������.
 * @param isShow
 */
function showOrHideAjaxPreloader (isShow)
{
    if (!isClientApp) return; // ������ ����� �������� ������ ��� ����������� ����������
    if (isShow == null) isShow = true;
    var loading = ensureElement("loading"); // ��������� ��� �������� �������� ��������
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
    var position = absPosition(workspace); // ���������� ������� ������� �������
    loading.style.left = (position.left+workspace.offsetWidth/2 - loading.offsetWidth/2) + "px";
}

function trim(val)
{
	var s = new String(val);
	s = s.replace(/^\s+/, "");
	return  s.replace(/\s+$/, "");
}

/* ������� ��� ������������� ������� ����������� ��������� ����� ������ �������� DOM */
function doOnLoad (funct, params)
{
    var onLoad = window.onload;
    window.onload = function () { if (onLoad != null) onLoad(); funct(params); };
}

// ����������, ���� �� � ������� �����
//@return: true - ����� ������, false - �� ������
function findClassName(obj, className)
{
   return obj != undefined && obj.className != undefined && findClass(obj, className);
}

function findClass(obj, className)
{
    return obj.className.search('\\b' + className + '\\b') >= 0;
}

// ���� ������� ������� �������� � �������� ������ ������
//@return: undefined ��� ������
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
        err = trim(error).substring(fieldName.length + 1); //+1 --- ������ :
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
            // ��� IE<9
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

/* ���������� ��������� */
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


//������ ��� ����������� ��������
/**
 * ����� ��������� ���������� ajax ������� ����������� ��������
 * @param msg - ajax ������
 */
function regionAjaxResult(msg)
{
    if (trim(msg) == 'OK')
    {
        win.close('regionsDiv');
        document.getElementById('regionNameSpan').innerHTML = clickRegion.currentRegionName;
        if (clickRegion.currentRegionName == '��� �������')
            $('.regionsAlphabetList .currentRegionName span').text('������ �� ������');
        else
            $('.regionsAlphabetList .currentRegionName span').text(clickRegion.currentRegionName);
    }
    else if(trim(msg) == '')
        location.reload();
    else
        document.getElementById("regionsDiv").innerHTML = msg;
}

var REGION_DICTIONARY_URL = ''; // ��������������� ����� � header

var clickRegion = {
    lock: false,
    currentRegionName: null,
    //���� �� �������
    click: function (id, name)
    {
        if (this.lock) return;
        this.lock = true;
        var myself = this;
        this.currentRegionName = name;
        regionClick(myself, id, name);
    },
    //����� �������
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
 * ������� ��� ����������� ��������, �������� �� ���� �� �������
 * @param id �� �������
 */
function regionClick(myself, id)
{
    var params = '';
    if (id > 0)
        params = 'id=' + id;

    ajaxQuery (params, REGION_DICTIONARY_URL, function(data){regionAjaxResult(data); reloadNews(); reloadBlockingMessage(); myself.lock = false;});
}

/**
 * ������� ��� ����������� ��������, ������� �������� �� ����� �������
 * @param id �������
 */
function selectRegion(myself, id)
{
    //���� select ���������� �������, ��� �������� id ���������� �������
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
    // ��� DOM-����������� ���������
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
                                            // ������� ���������
                                            if (!this.validationFunction())
                                                return false;

                                            // �����, �� �������� ��������� ������
                                            var form = getParentForm($('[id="commandButton_'+ commandId + '"]'));
                                            // ��������� �� ����� ���������� �� ��������
                                            addOperationInfo(form, this.id);

                                            // ���� �� �������� ������, �� ����� �������� ������� �����
                                            if (!this.useAjax)
                                            {
                                                customPlaceholder.clearPlaceholderVal();
                                                if  (this.clearFormAction)
                                                    form.attr("action", "");
                                                return form.submit();
                                            }

                                            // ����
                                            // ����������� ��������� � �����
                                            var parameters = form.find(':input').filter(':not(.inputPlaceholder)').serializeToWin();
                                            // ������� � ����� ���������� �� ��������
                                            removeOperationInfo(form);
                                            // �������� ����� �����
                                            var action = form.attr('action');
                                            // ���������� ������
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
