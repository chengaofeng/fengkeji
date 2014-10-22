// Stringクラスの拡張
/**
 * トリム処理を行う.
 */
String.prototype.trim = function() {
    return $.trim(this);
};

/**
 * 文字のバイト数を取得する.
 */
String.prototype.getByte = function() {
    var count = 0;
    var n;
    for (var i = 0; i< this.length; i++) { 
        n = escape(this.charAt(i));
        if (n.length < 4) count++; else count+=2; 
    } 
    return count;
};

/**
 * String拡張【接尾語判定】
 */
String.prototype.endsWith = function(suffix) {
    var sub = this.length - suffix.length;
    return (sub >= 0) && (this.lastIndexOf(suffix) === sub);
};

/**
 * String拡張【接頭語判定】
 */
String.prototype.startsWith = function(prefix, toffset) {
    var i = 0;
    if(toffset && (typeof toffset === 'number')) {
        i = toffset;
    }
    return this.slice(i).indexOf(prefix) === 0;
};

/**
 * String拡張【バイト数（漢字を２倍とした）】
 */
String.prototype.bLen = function() { 
    var r = 0;
    for (var i = 0; i < this.length; i++) { 
        var c = this.charCodeAt(i);
        // Shift_JIS: 0x0 ～ 0x80, 0xa0 , 0xa1 ～ 0xdf , 0xfd ～ 0xff 
        // Unicode : 0x0 ～ 0x80, 0xf8f0, 0xff61 ～ 0xff9f, 0xf8f1 ～ 0xf8f3 
        if ( (c >= 0x0 && c < 0x81) || (c == 0xf8f0) || (c >= 0xff61 && c < 0xffa0) || (c >= 0xf8f1 && c < 0xf8f4)) { 
            r += 1; 
        } else { 
            r += 2; 
        } 
    } 
    return r; 
};
/**
 * String拡張【数値判定】
 */
String.prototype.isNumeric = function() { 
    if (this.match(/^-{0,1}\d+\.*\d*$/)) {
        return true;
    } else {
        return false;
    }
};
/**
 * String拡張【数字判定】
 */
String.prototype.isNumber = function() { 
    if (this.match(/[^0-9]/)) {
        return false;
    } else {
        return true;
    }
};
/**
 * String拡張【指定文字数になるように文字列を左部に補間します。】
 */
String.prototype.fillLeft = function(len,chr) { 
    var pre= "";
    for (var i=0;i<len;i++){
        pre += chr;
    }
    return (pre+this).slice(-1*len);
};

/**
 * String拡張【指定文字数になるように文字列を右部に補間します。】
 */
String.prototype.fillRight = function(len,chr) { 
    var suf= "";
    for (var i=0;i<len;i++){
        suf += chr;
    }
    return (this+suf).slice(0,len);
};