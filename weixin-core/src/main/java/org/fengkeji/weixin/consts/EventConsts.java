/**
 * 
 */
package org.fengkeji.weixin.consts;

/**
 * @author ASUS
 *
 */
public class EventConsts {

	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";
	
	/**菜单click按钮*/
	/**------------------个人信息--------------*/
	/** 注册信息*/
    public static String EVENT_MY_INFO = "MY_INFO";
    
    /** 消费记录 */
    public static String EVENT_CONSUME_HISTORY = "CONSUME_HISTORY";
    
    /** 收藏夹 */
    public static String EVENT_FAVOURITE = "FAVOURITE";
    
    /**------------------商户活动--------------*/
    /** 固定活动 */
    public static String EVENT_FIXED_EVENTS = "FIXED_EVENTS";

    /** 新品上架 */
    public static String EVENT_NEW_PRODUCTS = "NEW_PRODUCTS";
    
    /** 限时折扣 */
    public static String EVENT_LIMIT_SALE = "LIMIT_SALE";
    
    /** 限时活动 */
    public static String EVENT_LIMIT_EVENTS = "LIMIT_EVENTS";
    
    /** 小游戏赢惊喜*/
    public static String EVENT_LITTLE_GAME = "LITTLE_GAME";
    
    /**------------------圈子社区--------------*/
    /** 分享赢红包 */
    public static String EVENT_SHARE_GET_PACKET = "SHARE_GET_PACKET";

    /** 月/周排行榜 */
    public static String EVENT_RANKING_LIST = "RANKING_LIST";
    
    /** 粉丝信箱 */
    public static String EVENT_FAN_MAIL = "FAN_MAIL";
    
    /** 推荐好友赢好礼 */
    public static String EVENT_RECOMMEND_WIN = "RECOMMEND_WIN";
}
