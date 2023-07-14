// IE5.5+ PNG Alpha Fix v2.0 Alpha: Background Tiling Support
// (c) 2008-2009 Angus Turnbull http://www.twinhelix.com

// This is licensed under the GNU LGPL, version 2.1 or later.
// For details, see: http://creativecommons.org/licenses/LGPL/2.1/

var IEPNGFix = window.IEPNGFix || {};

IEPNGFix.tileBG = function(elm, pngSrc, ready) {
	// Params: A reference to a DOM element, the PNG src file pathname, and a
	// hidden "ready-to-run" passed when called back after image preloading.

	var data = this.data[elm.uniqueID],
		elmW = Math.max(elm.clientWidth, elm.scrollWidth),
		elmH = Math.max(elm.clientHeight, elm.scrollHeight),
		bgX = elm.currentStyle.backgroundPositionX,
		bgY = elm.currentStyle.backgroundPositionY,
		bgR = elm.currentStyle.backgroundRepeat;

	// Cache of DIVs created per element, and image preloader/data.
	if (!data.tiles) {
		data.tiles = {
			elm: elm,
			src: '',
			cache: [],
			img: new Image(),
			old: {}
		};
	}
	var tiles = data.tiles,
		pngW = tiles.img.width,
		pngH = tiles.img.height;

	if (pngSrc) {
		if (!ready && pngSrc != tiles.src) {
			// New image? Preload it with a callback to detect dimensions.
			tiles.img.onload = function() {
				this.onload = null;
				IEPNGFix.tileBG(elm, pngSrc, 1);
			};
			return tiles.img.src = pngSrc;
		}
	} else {
		// No image?
		if (tiles.src) ready = 1;
		pngW = pngH = 0;
	}
	tiles.src = pngSrc;

	if (!ready && elmW == tiles.old.w && elmH == tiles.old.h &&
		bgX == tiles.old.x && bgY == tiles.old.y && bgR == tiles.old.r) {
		return;
	}

	// Convert English and percentage positions to pixels.
	var pos = {
			top: '0%',
			left: '0%',
			center: '50%',
			bottom: '100%',
			right: '100%'
		},
		x,
		y,
		pc;
	x = pos[bgX] || bgX;
	y = pos[bgY] || bgY;
	if (pc = x.match(/(\d+)%/)) {
		x = Math.round((elmW - pngW) * (parseInt(pc[1]) / 100));
	}
	if (pc = y.match(/(\d+)%/)) {
		y = Math.round((elmH - pngH) * (parseInt(pc[1]) / 100));
	}
	x = parseInt(x);
	y = parseInt(y);

	// Handle backgroundRepeat.
	var repeatX = { 'repeat': 1, 'repeat-x': 1 }[bgR],
		repeatY = { 'repeat': 1, 'repeat-y': 1 }[bgR];
	if (repeatX) {
		x %= pngW;
		if (x > 0) x -= pngW;
	}
	if (repeatY) {
		y %= pngH;
		if (y > 0) y -= pngH;
	}

	// Go!
	this.hook.enabled = 0;
	if (!({ relative: 1, absolute: 1 }[elm.currentStyle.position])) {
		elm.style.position = 'relative';
	}
	var count = 0,
		xPos,
		maxX = repeatX ? elmW : x + 0.1,
		yPos,
		maxY = repeatY ? elmH : y + 0.1,
		d,
		s,
		isNew;
	if (pngW && pngH) {
		for (xPos = x; xPos < maxX; xPos += pngW) {
			for (yPos = y; yPos < maxY; yPos += pngH) {
				isNew = 0;
				if (!tiles.cache[count]) {
					tiles.cache[count] = document.createElement('div');
					isNew = 1;
				}
				var clipR = Math.max(0, xPos + pngW > elmW ? elmW - xPos : pngW),
					clipB = Math.max(0, yPos + pngH > elmH ? elmH - yPos : pngH);
				d = tiles.cache[count];
				s = d.style;
				s.behavior = 'none';
				s.left = (xPos - parseInt(elm.currentStyle.paddingLeft)) + 'px';
				s.top = yPos + 'px';
				s.width = clipR + 'px';
				s.height = clipB + 'px';
				s.clip = 'rect(' +
					(yPos < 0 ? 0 - yPos : 0) + 'px,' +
					clipR + 'px,' +
					clipB + 'px,' +
					(xPos < 0 ? 0 - xPos : 0) + 'px)';
				s.display = 'block';
				if (isNew) {
					s.position = 'absolute';
					s.zIndex = -999;
 					if (elm.firstChild) {
						elm.insertBefore(d, elm.firstChild);
					} else {
						elm.appendChild(d);
					}
                    
				}
				this.fix(d, pngSrc, 0);
				count++;
			}
		}
	}
	while (count < tiles.cache.length) {
		this.fix(tiles.cache[count], '', 0);
		tiles.cache[count++].style.display = 'none';
	}

	this.hook.enabled = 1;

	// Cache so updates are infrequent.
	tiles.old = {
		w: elmW,
		h: elmH,
		x: bgX,
		y: bgY,
		r: bgR
	};
};


IEPNGFix.update = function() {
	// Update all PNG backgrounds.
	for (var i in IEPNGFix.data) {
		var t = IEPNGFix.data[i].tiles;
		if (t && t.elm && t.src) {
			IEPNGFix.tileBG(t.elm, t.src);
		}
	}
};
IEPNGFix.update.timer = 0;

if (window.attachEvent && !window.opera) {
	window.attachEvent('onresize', function() {
		clearTimeout(IEPNGFix.update.timer);
		IEPNGFix.update.timer = setTimeout(IEPNGFix.update, 100);
	});
}


// IE5.5+ PNG Alpha Fix v2.0 Alpha
// (c) 2004-2008 Angus Turnbull http://www.twinhelix.com

// This is licensed under the GNU LGPL, version 2.1 or later.
// For details, see: http://creativecommons.org/licenses/LGPL/2.1/

var IEPNGFix = window.IEPNGFix || {};
IEPNGFix.data = IEPNGFix.data || {};


// This must be a path to a blank image, relative to the HTML document(s).
// In production use I suggest '/images/blank.gif' or similar. That's all!
IEPNGFix.blankImg = globalUrl+'/commonSkin/images/blank.gif';

IEPNGFix.fix = function(elm, src, t) {
	// Applies an image 'src' to an element 'elm' using the DirectX filter.
	// If 'src' is null, filter is disabled.
	// Disables the 'hook' to prevent infinite recursion on setting BG/src.
	// 't' = type, where background tile = 0, background = 1, IMG SRC = 2.

	var h = this.hook.enabled;
	this.hook.enabled = 0;

	var f = 'DXImageTransform.Microsoft.AlphaImageLoader';
		src = (src || '').replace(/\(/g, '%28').replace(/\)/g, '%29');

	if (
		src && !(/IMG|INPUT/.test(elm.nodeName) && (t != 2)) &&
		elm.currentStyle.width == 'auto' && elm.currentStyle.height == 'auto'
	) {
		elm.style.width = elm.offsetWidth + 'px';
		elm.style.height = elm.clientHeight + 'px';
		if (elm.currentStyle.display == 'inline') {
			elm.style.display = 'inline-block';
		}
	}

	if (t == 1) {
		elm.style.backgroundImage = 'url("' + this.blankImg + '")';
	}
	if (t == 2) {
		elm.src = this.blankImg;
	}

	if (elm.filters[f]) {
		elm.filters[f].enabled = src ? true : false;
		if (src) {
			elm.filters[f].src = src;
		}
	} else if (src) {
		elm.style.filter = 'progid:' + f + '(src="' + src +
			'",sizingMethod="' + (t == 2 ? 'scale' : 'crop') + '")';
	}

	this.hook.enabled = h;
};


IEPNGFix.process = function(elm, init) {
	// Checks the onpropertychange event (on first 'init' run, a fake event)
	// and calls the filter-applying-functions.

	if (
		!/MSIE (5\.5|6)/.test(navigator.userAgent) ||
		typeof elm.filters == 'unknown'
	) {
		return;
	}
	if (!this.data[elm.uniqueID]) {
		this.data[elm.uniqueID] = {
			className: ''
		};
	}
	var data = this.data[elm.uniqueID],
		evt = init ? { propertyName: 'src,backgroundImage' } : event,
		isSrc = /src/.test(evt.propertyName),
		isBg = /backgroundImage/.test(evt.propertyName),
		isPos = /width|height|background(Pos|Rep)/.test(evt.propertyName),
		isClass = !init && ((elm.className != data.className) &&
			(elm.className || data.className));
	if (!(isSrc || isBg || isPos || isClass)) {
		return;
	}
	data.className = elm.className;
	var blank = this.blankImg.match(/([^\/]+)$/)[1],
		eS = elm.style,
		eCS = elm.currentStyle;

	// Required for Whatever:hover - erase set BG if className changes.
	if (
		isClass && (eS.backgroundImage.indexOf('url(') == -1 ||
		eS.backgroundImage.indexOf(blank) > -1)
	) {
		return setTimeout(function() {
			eS.backgroundImage = '';
		}, 0);
	}

	// Foregrounds.
	if (isSrc && elm.src &&  { IMG: 1, INPUT: 1 }[elm.nodeName]) {
		if ((/\.png/i).test(elm.src)) {
			this.fix(elm, elm.src, 2);
		} else if (elm.src.indexOf(blank) == -1) {
			this.fix(elm, '');
		}
	}

	// Backgrounds.
	var bgSrc = eCS.backgroundImage || eS.backgroundImage;
	if ((bgSrc + elm.src).indexOf(blank) == -1) {
		var bgPNG = bgSrc.match(/url[("']+(.*\.png[^\)"']*)[\)"']/i);
		if (bgPNG) {
			if (this.tileBG && !{ IMG: 1, INPUT: 1 }[elm.nodeName]) {
				this.tileBG(elm, bgPNG[1]);
				this.fix(elm, '', 1);
			} else {
				if (data.tiles && data.tiles.src) {
					this.tileBG(elm, '');
				}
				this.fix(elm, bgPNG[1], 1);
				this.childFix(elm);
			}
		} else {
			if (data.tiles && data.tiles.src) {
				this.tileBG(elm, '');
			}
			this.fix(elm, '');
		}
	} else if ((isPos || isClass) && data.tiles && data.tiles.src) {
		this.tileBG(elm, data.tiles.src);
	}

	if (init) {
		this.hook.enabled = 1;
		elm.attachEvent('onpropertychange', this.hook);
	}
};


IEPNGFix.childFix = function(elm) {
	// "hasLayout" fix for unclickable children inside PNG backgrounds.
	var tags = [
			'a',
			'input',
			'select',
			'textarea',
			'button',
			'iframe',
			'object'
		],
		t = tags.length,
		tFix = [];
	while (t--) {
		var pFix = elm.all.tags(tags[t]),
			e = pFix.length;
		while (e--) {
			tFix.push(pFix[e]);
		}
	}
	t = tFix.length;
	if (t && (/relative|absolute/i).test(elm.currentStyle.position)) {
		alert('IEPNGFix: Unclickable children of element:' +
			'\n\n<' + elm.nodeName + (elm.id && ' id=' + elm.id) + '>');
	}
	while (t--) {
		if (!(/relative|absolute/i).test(tFix[t].currentStyle.position)) {
			tFix[t].style.position = 'relative';
		}
	}
};

IEPNGFix.init = function(element)
{

    IEPNGFix.hook = function() {
        if (IEPNGFix.hook.enabled) {
            IEPNGFix.process(element, 0);
        }
    };
    IEPNGFix.process(element, 1);
};

IEPNGFix.fixTransparentBackground = function(imageURL, event)
    {
        var bGrad = "filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + imageURL + "', sizingMethod='scale')";
        document.styleSheets[0].addRule(event, bGrad);
    };

// fixImg
doOnLoad( function () {
     var imgs = document.getElementsByTagName("img");
     for (var i=0; i<imgs.length; i++)
        {
        IEPNGFix.init(imgs[i]);
        }
});



