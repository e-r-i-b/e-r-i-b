if(typeof deconcept=="undefined"){
    var deconcept=new Object();
}
if(typeof deconcept.util=="undefined"){
    deconcept.util=new Object();
}
if(typeof deconcept.InfObjectUtil=="undefined"){
    deconcept.InfObjectUtil=new Object();
}
deconcept.InfObject=function(baseUrl, size, position){
    if(!document.getElementById){
        return;
    }
    this.DETECT_KEY="detectflash";
    this.skipDetect=deconcept.util.getRequestParameter(this.DETECT_KEY);
    this.params=new Object();
    this.variables=new Object();
    this.attributes=new Array();

    this._bu = baseUrl;

    this.setAttribute("swf",this._bu + "/static/Vishnu.swf?"+Math.random());
    this.setAttribute("id","vishnu");
    var min=0, max=0;
    this.addVariable("position", position ? position : "right");
    switch (size.toUpperCase()) {
        case "S":min=200;max=400;break;
        case "L":min=300;max=550;break;
        default: // "M"
            min=250;max=450;size="m";
    }
    this.addVariable("size", size);

    switch (position.toUpperCase()) {
        case "TOP":
        case "BOTTOM":
            this.setAttribute("width", min);
            this.setAttribute("height",max);
            break;
        default: // LEFT & RIGHT
            this.setAttribute("width", max);
            this.setAttribute("height",min);
    }
    this.installedVer=deconcept.InfObjectUtil.getPlayerVersion();
    if(!window.opera&&document.all&&this.installedVer.major>7){
        deconcept.InfObject.doPrepUnload=true;
    }
    this.setAttribute("version",new deconcept.PlayerVersion(9));
    this.addParam("quality","high");
    this.setAttribute("useExpressInstall",false);
    this.setAttribute("doExpressInstall",false);
    this.setAttribute("xiRedirectUrl",window.location);
    this.setAttribute("redirectUrl","");

    // defaults
    this.addParam('wmode', 'window');
    this.addParam('allowScriptAccess', 'always');
    this.addParam('allowDomain', 'always');
};

deconcept.InfObject.prototype={
    _ph:null,
    _sk:null,
    _bu:null,
    xmlhttp:null,
    useExpressInstall:function(_d){
        this.xiSWFPath=!_d?"expressinstall.swf":_d;this.setAttribute("useExpressInstall",true);
    },
    setAttribute:function(_e,_f){
        this.attributes[_e]=_f;
    },
    getAttribute:function(_10){
        return this.attributes[_10];
    },
    addParam:function(_11,_12){
        this.params[_11]=_12;
    },
    getParams:function(){
        return this.params;
    },
    addVariable:function(_13,_14){
        this.variables[_13]=_14;
    },
    getVariable:function(_15){
        return this.variables[_15];
    },
    getVariables:function(){
        return this.variables;
    },
    getVariablePairs:function(){
        var _16=new Array();var key;var _18=this.getVariables();for(key in _18){
            _16[_16.length]=key+"="+_18[key];
        }return _16;
    },
    getSWFHTML:function(){
        var _19="";if(navigator.plugins&&navigator.mimeTypes&&navigator.mimeTypes.length){
            if(this.getAttribute("doExpressInstall")){
                this.addVariable("MMplayerType","PlugIn");this.setAttribute("swf",this.xiSWFPath);
            }_19="<embed type=\"application/x-shockwave-flash\" src=\""+this.getAttribute("swf")+"\" width=\""+this.getAttribute("width")+"\" height=\""+this.getAttribute("height")+"\" style=\""+this.getAttribute("style")+"\"";_19+=" id=\""+this.getAttribute("id")+"\" name=\""+this.getAttribute("id")+"\" ";var _1a=this.getParams();for(var key in _1a){
                _19+=[key]+"=\""+_1a[key]+"\" ";
            }var _1c=this.getVariablePairs().join("&");if(_1c.length>0){
                _19+="flashvars=\""+_1c+"\"";
            }_19+="/>";
        }else{
            if(this.getAttribute("doExpressInstall")){
                this.addVariable("MMplayerType","ActiveX");this.setAttribute("swf",this.xiSWFPath);
            }_19="<object id=\""+this.getAttribute("id")+"\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" width=\""+this.getAttribute("width")+"\" height=\""+this.getAttribute("height")+"\" style=\""+this.getAttribute("style")+"\">";_19+="<param name=\"movie\" value=\""+this.getAttribute("swf")+"\" />";var _1d=this.getParams();for(var key in _1d){
                _19+="<param name=\""+key+"\" value=\""+_1d[key]+"\" />";
            }var _1f=this.getVariablePairs().join("&");if(_1f.length>0){
                _19+="<param name=\"flashvars\" value=\""+_1f+"\" />";
            }_19+="</object>";
        }return _19;
    },
    write:function(_20){
        this._sk = Math.random().toString().substr(2);
        this._ph = _20;
        this.addVariable("startHash", this._sk);
        var n=(typeof _20=="string")?document.getElementById(_20):_20;
        var img = document.createElement('img');
        var inst = this;
        img.onload = function () {
            inst._write(inst._ph);
        };
        img.src = this._bu + "/1.gif?" + this._sk;
        if(n)n.appendChild(img);
    },
    ajaxDone:function(a){
        if (4 == this.readyState) {
            this.so._write(this.so._ph);
        }
    },
    _write:function(_20){
        if(this.getAttribute("useExpressInstall")){
            var _21=new deconcept.PlayerVersion([6,0,65]);if(this.installedVer.versionIsValid(_21)&&!this.installedVer.versionIsValid(this.getAttribute("version"))){
                this.setAttribute("doExpressInstall",true);this.addVariable("MMredirectURL",escape(this.getAttribute("xiRedirectUrl")));document.title=document.title.slice(0,47)+" - Flash Player Installation";this.addVariable("MMdoctitle",document.title);
            }
            }if(this.skipDetect||this.getAttribute("doExpressInstall")||this.installedVer.versionIsValid(this.getAttribute("version"))){
            var n=(typeof _20=="string")?document.getElementById(_20):_20;
            if(n)n.innerHTML=this.getSWFHTML();
            return true;
        }else{
            if(this.getAttribute("redirectUrl")!=""){
                document.location.replace(this.getAttribute("redirectUrl"));
            }
            }return false;
    }
    };
    deconcept.InfObjectUtil.getPlayerVersion=function(){
    var _23=new deconcept.PlayerVersion([0,0,0]);if(navigator.plugins&&navigator.mimeTypes.length){
        var x=navigator.plugins["Shockwave Flash"];if(x&&x.description){
            _23=new deconcept.PlayerVersion(x.description.replace(/([a-zA-Z]|\s)+/,"").replace(/(\s+r|\s+b[0-9]+)/,".").split("."));
        }
        }else{
        if(navigator.userAgent&&navigator.userAgent.indexOf("Windows CE")>=0){
            var axo=1;var _26=3;while(axo){
                try{
                    _26++;axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash."+_26);_23=new deconcept.PlayerVersion([_26,0,0]);
                }catch(e){
                    axo=null;
                }
                }
            }else{
            try{
                var axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");
            }catch(e){
                try{
                    var axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");_23=new deconcept.PlayerVersion([6,0,21]);axo.AllowScriptAccess="always";
                }catch(e){
                    if(_23.major==6){
                        return _23;
                    }
                    }try{
                    axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
                }catch(e){}
                }if(axo!=null){
                _23=new deconcept.PlayerVersion(axo.GetVariable("$version").split(" ")[1].split(","));
            }
            }
        }return _23;
};
deconcept.PlayerVersion=function(_29){
    this.major=_29[0]!=null?parseInt(_29[0]):0;this.minor=_29[1]!=null?parseInt(_29[1]):0;this.rev=_29[2]!=null?parseInt(_29[2]):0;
};
deconcept.PlayerVersion.prototype.versionIsValid=function(fv){
    if(this.major<fv.major){
        return false;
    }if(this.major>fv.major){
        return true;
    }if(this.minor<fv.minor){
        return false;
    }if(this.minor>fv.minor){
        return true;
    }if(this.rev<fv.rev){
        return false;
    }return true;
};
deconcept.util={
    getRequestParameter:function(_2b){
        var q=document.location.search||document.location.hash;if(_2b==null){
            return q;
        }if(q){
            var _2d=q.substring(1).split("&");for(var i=0;i<_2d.length;i++){
                if(_2d[i].substring(0,_2d[i].indexOf("="))==_2b){
                    return _2d[i].substring((_2d[i].indexOf("=")+1));
                }
                }
            }return "";
    }
    };deconcept.InfObjectUtil.cleanupSWFs=function(){
    var _2f=document.getElementsByTagName("OBJECT");for(var i=_2f.length-1;i>=0;i--){
        _2f[i].style.display="none";for(var x in _2f[i]){
            if(typeof _2f[i][x]=="function"){
                _2f[i][x]=function(){};
            }
            }
        }
    };if(deconcept.InfObject.doPrepUnload){
    if(!deconcept.unloadSet){
        deconcept.InfObjectUtil.prepUnload=function(){
            __flash_unloadHandler=function(){};__flash_savedUnloadHandler=function(){};window.attachEvent("onunload",deconcept.InfObjectUtil.cleanupSWFs);
        };window.attachEvent("onbeforeunload",deconcept.InfObjectUtil.prepUnload);deconcept.unloadSet=true;
    }
    }if(!document.getElementById&&document.all){
    document.getElementById=function(id){
        return document.all[id];
    };
}
var getQueryParamValue=deconcept.util.getRequestParameter;
var FlashObject=deconcept.InfObject;
var InfObject=deconcept.InfObject;

var NanoInf = {
    baseUrl: 'http://biz.nanosemantics.ru',
    source: '',
    closeButton: 'http://biz.nanosemantics.ru/static/custom/close.png',
    swfWidth: 600,
    swfHeight: 600,
    swfPlaceName: "nanoinf-flash",
    showWindow: true,
    isFixedSupports: false,
    init: function(initdata) {
        if (typeof window.addEventListener != "undefined") {
            window.addEventListener("load", this.onready, false);
        } else if (typeof document.addEventListener != "undefined") {
            document.addEventListener("load", this.onready, false);
        } else if (typeof window.attachEvent != "undefined") {
            window.attachEvent('onload', this.onready);
        } else if (typeof window.onload == "function") {
            var fnOld = window.onload;
            var self = this;
            window.onload = function() {
                fnOld();
                self.onready();
            };
        } else {
            window.onload = this.onready;
        }

        for (var i in initdata) {
            this[i] = initdata[i];
        }
    },
    onready: function() {
        var self = NanoInf, html = '', css;
        var userAgent = navigator.userAgent.toLowerCase();
        var msie = /msie/.test(userAgent) && !/opera/.test(userAgent);
        var msie6 = msie && /msie\s6/.test(userAgent);
        var boxModel = !msie || document.compatMode == 'CSS1Compat';
        var visible = !(document.cookie.indexOf('nanoinf[visible]=0') >= 0);

        self.buildModal();

        var io = new InfObject(self.baseUrl, '', '');
        io.setAttribute('swf', self.source);
        io.setAttribute('width', self.swfWidth);
        io.setAttribute('height', self.swfHeight);
        io.write(self.swfPlaceName);
    },
    buildModal: function() {
        var userAgent = navigator.userAgent.toLowerCase();
        var msie = /msie/.test(userAgent) && !/opera/.test(userAgent);
        this.isFixedSupports = !($.browser.msie && $.browser.version.substring(0,1) < 7);

        var w = parseInt(NanoInf.swfWidth);
        var h = parseInt(NanoInf.swfHeight);

        var html = '<div id="inf_blocker_layer" style="width:100%; height:100px; display:none; position:absolute; left:0; top:0; z-index:1000; background:#fff; -moz-opacity:0.5; -webkit-opacity:0.5; opacity:0.5; filter:alpha(opacity=50);"></div>';

        if (this.showWindow) {
            html += '<div id="vishnu_modal_window" style="display:none; clear:both; position:absolute; background:transparent; left:50%; top:50%; z-index:6000; width:' + (w + 10) + 'px; height:' + (h + 10) + 'px; margin-left:-' + Math.round(w/2) + 'px; margin-top:-' + Math.round(h/2) + 'px; border-radius:3px; -moz-border-radius:3px; border:2px solid #CCC; text-align:center;"> \
              <div style="padding:0px; width:' + w + 'px; height:' + h + 'px; margin:auto; z-index:6000;"> \
                  <div align="right" style="padding-right:0px;"><a href="javascript:void(0);" onclick="NanoInf.hideModal(); return false;" title="а�аАаКб�б�б�б�"><img src="' + this.closeButton + '" alt="У�" border="0"/></a></div> \
                  <div align="left" style="padding:0px;"><div id="' + this.swfPlaceName + '"></div></div> \
              </div> \
            </div>';
        } else {
            html += '<div id="vishnu_modal_window" style="display:none; clear:both; position:absolute; background:transparent; left:50%; top:50%; z-index:6000; width:' + w + 'px; height:' + h + 'px; margin-left:-' + Math.round(w/2) + 'px; margin-top:-' + Math.round(h/2) + 'px; text-align:center;"> \
              <div style="padding:0px; width:' + w + 'px; height:' + h + 'px; margin:auto; z-index:6000;"> \
                  <div id="' + this.swfPlaceName + '"></div> \
              </div> \
            </div>';
        }


        var body = document.getElementsByTagName('body')[0];
        var div = document.createElement('div');
        div.innerHTML = html;
        body.appendChild(div);

        $('.inf_button').click(function(){
            NanoInf.showModal();
            return false;
        });

        if (this.isFixedSupports) {
            $('#vishnu_modal_window').css('position','fixed');
        } else {
            $('#vishnu_modal_window').css('margin-top','0');
            $(window).scroll(function(){
                NanoInf.updateModalPosition();
            });
            $(window).resize(function(){
                NanoInf.updateModalPosition();
            });
            this.updateModalPosition();
        }
    },
    updateModalPosition: function()
    {
        $('#vishnu_modal_window').css({
            top:Math.round((parseInt($(window).height()) - parseInt($('#vishnu_modal_window').height()))/2) + $(window).scrollTop()
        });
    },
    showModal: function()
    {
        $('#inf_blocker_layer').show();
        $('#inf_blocker_layer').height(parseInt($(document).height()));
        $('#vishnu_modal_window').show();
        $('#vishnu_modal_window').css('visibility', 'visible');
        if (!this.isFixedSupports) {
            this.updatePosition();
        }

        $('#vishnu_modal_window').height($('#'+this.swfPlaceName).position().top + $('#'+this.swfPlaceName).outerHeight() + 10);
        $('#vishnu_modal_window').width($('#'+this.swfPlaceName).position().left + $('#'+this.swfPlaceName).outerWidth() + 10);

        document.cookie = 'nanoinf[visible]=1; path=/';
    },
    hideModal: function()
    {
        $('#vishnu_modal_window').css('visibility', 'hidden');
        $('#inf_blocker_layer').hide();
        document.cookie = 'nanoinf[visible]=0; path=/';
    }
};

