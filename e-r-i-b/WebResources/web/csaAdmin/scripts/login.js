
$(document).ready(function()
{
    if (isIE(6))
    {
        try
        {
            $(".logoLogin").get(0).style.filter ="progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + imagePath + "/logo_login.png,sizingMethod='scale')";
        }
        catch (e) { }
    }
    customPlaceholder.init();
});

/**
 * ��������� ������� �����������
 */
function login()
{
    var login = $('#inputLogin').val();
    var password = $('#passwordTxt').val();
    if(login.length == 0 || password.length == 0)
    {
        alert("������� ����� ������������ � ������.");
        return;
    }
    document.getElementById('passwordTxt').disabled = true;
    var clientRandom = randomHex(16);
    var serverRandom = $('#serverRandom').val();
    var challenge = serverRandom + clientRandom;
    var ph = fnStrHashLikeGOST(password);
    var ar = fnHmacLikeGOST(ph, challenge);
    $('#password').val(ar);
    $('#clientRandom').val(clientRandom);
    $('#operation').val("login");
    var form = $('#loginForm');
    form.append('<input type="hidden" name="operation" value="login"/>');
    form.submit();
}

/**
 * ������� ������
 */
function changePassword()
{
    if (!validateRequiredField("newPassword", "������� ����� ������."))
        return;
    if (!validateRequiredField("repeatedPassword", "��������� ����� ������."))
        return;

    var form = $('#changePasswordForm');
    form.append('<input type="hidden" name="operation" value="changePassword"/>');
    form.submit();
}

/**
 * �����
 */
function exit()
{
    var form = $('#changePasswordForm');
    form.append('<input type="hidden" name="operation" value="exit"/>');
    form.submit();
}

function validateRequiredField(id, message)
{
    var field = $('#'+id).val();
    if(field.length == 0)
    {
        alert(message);
        return false;
    }
    return true;
}

var HEX_DIGITS = "0123456789ABCDEF";
function randomHex(n)
{
    var res = "";

    for (var i = 0; i < n; i++)
    {
        var p = Math.floor(Math.random() * 16);
        res = res + HEX_DIGITS.charAt(p);
    }

    return res;
}

/**
 * ��������� ��������� �������
 * @param event - �������
 */
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

/*����������� Internet Explorer*/
function isIE(version){
    var browserIE = false;
    if (navigator.appName == "Microsoft Internet Explorer"){
        browserIE = true;
        if (version != null && navigator.appVersion.indexOf("MSIE "+version) < 0) return false;
    }
    return browserIE;
}

/**
 * ������������� ������� � ���� node �� ������� pos
 */
function setCaretPosition( node, posStart, posEnd){
    try{
        // mozilla
        if( node.setSelectionRange ){
            node.setSelectionRange( posStart, posEnd != null ? posEnd : posStart);
        }
        // ie
        else if ( node.createTextRange ){
            var range = node.createTextRange();
            range.collapse( true );
            range.moveEnd( 'character', posEnd != null ? posEnd : posStart);
            range.moveStart( 'character', posStart );
            range.select();
        }
    }
    catch(ignore)
    {}
}

function focusPassword()
{
    $("#passText").hide();
}

function blurPassword()
{
    if ($("#passwordTxt").val() == "")
    $("#passText").show();
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

    if(elem != null)
    {
        elem.style.display = hide ? "none" : "";
    }
}

/* ������� ��� ��������� ���� �� ������ ������� �������*/
function getWorkSpace (globalWorkScape)
{
    if (globalWorkScape)
        return document.getElementById("pageContent");

    var workSpace = document.getElementById("workspace");
    if (workSpace == null) // csa login page
        workSpace = document.getElementById("LoginDiv");
    return workSpace;
}

//������ ���� �������� � �������
function screenSize()
{
    var w, h; // w - �����, h - ������
    w = (window.innerWidth ? window.innerWidth : (document.documentElement.clientWidth ? document.documentElement.clientWidth : document.body.offsetWidth));
    h = (window.innerHeight ? window.innerHeight : (document.documentElement.clientHeight ? document.documentElement.clientHeight : document.body.offsetHeight));
    return {w:w, h:h};
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

// ������������� �������
// ! ������� �� ���������, � ���� �����������
// @return: boolean true ������� ����� ��� false � ������ ������
var eventsKeyHash = {};
function switchEvent(key, off)
{
    if (eventsKeyHash[key] == undefined) return false;
    eventsKeyHash[key] = !((off != null)?off:eventsKeyHash[key]);
    return true;
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

/**
 * ����� ��� ��������� ���������� ������ ��������� ������� ������������ ����� ����
 * @param div
 * @param globalWorkSpace ���������� ���������.
 * @return topCenter �������� ������� ������� ������������ ����� ����
 *         abs ���������� �������� ������� ������� �� ���� ����
 */
function workCenter (div, globalWorkSpace)
{
    var workSpace = getWorkSpace (globalWorkSpace); // ������� �������
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
 * ���������� ������� (obj) ��������������� �����-�� ��� � ������� likeObj
 * @param obj ������ ������� �����������
 * @param likeObj ������ ������������ �������� �����������
 * @param centerObj (�������������� ��������) ������ - ������� ������� obj, ������� ����� �������� ������������
 */
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

// ������� ��������������� ��������� ������������ ���� � ������ ������� workspace
function setTintedDiv(show, blockAllContent)
{
    var workspace = getWorkSpace(blockAllContent);
    if(show == null) return ;
    TintedNet.setTinted(workspace, show, blockAllContent);

    // �������������� �������� ��� ����������� ��� ������������� ������������� ����
    aditionalTintedAction (show);
}

// �������������� �������� ��� ���������� ��� ��������
function aditionalTintedAction(show)
{
    var footer = document.getElementById("footer");
    // ���� ����� ����������� �������� ������� ���� ������ �� ����� ������
    if (footer == null) return;

    TintedNet.setTinted(footer, show);
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
    setTinted : function (element, show, blockAllContent) {
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
        tinted.style.zIndex = getZindex(element)+(blockAllContent?11:10);

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

function monthToStringOnly(str1)
{
    var month;
    var date;
    date = "";
    month = str1.substring(3, 5) - 1;
    date = date + monthToStringByNumber(month);
    return date;
}

function monthToStringByNumber(month)
{
    var monthStr = "";

    switch (month)
    {
        case 0:monthStr = "������";break;
        case 1:monthStr = "�������";break;
        case 2:monthStr = "�����";break;
        case 3:monthStr = "������";break;
        case 4:monthStr = "���";break;
        case 5:monthStr = "����";break;
        case 6:monthStr = "����";break;
        case 7:monthStr = "�������";break;
        case 8:monthStr = "��������";break;
        case 9:monthStr = "�������";break;
        case 10:monthStr = "������";break;
        case 11:monthStr = "�������";break;
        default:monthStr = "";break;
    }
    return monthStr;
}
