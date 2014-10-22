package org.fengkeji.weixin.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fengkeji.weixin.common.message.resp.RespArticle;
import org.fengkeji.weixin.common.message.resp.RespNewsMessage;
import org.fengkeji.weixin.common.message.resp.RespTextMessage;
import org.fengkeji.weixin.common.util.DateUtil;
import org.fengkeji.weixin.common.util.MessageUtil;
import org.fengkeji.weixin.common.util.RobotUtil;
import org.fengkeji.weixin.common.util.StringUtil;
import org.fengkeji.weixin.consts.EventConsts;
import org.fengkeji.weixin.consts.StringConsts;
import org.fengkeji.weixin.base.AbstractWxService;
import org.fengkeji.weixin.entity.RedPacket;
import org.fengkeji.weixin.entity.ScratchCard;
import org.fengkeji.weixin.entity.Fkj001tDto;
import org.springframework.stereotype.Service;

/**
 * 核心服务类
 * 
 * @author Song
 */
@Service("CoreService")
public class WeixinCoreService extends AbstractWxService {

	/**
	 * 处理微信发来的请求
	 *
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "皮皮听不懂您说的。汪汪汪！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String openId = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 内容
			String msgContent = requestMap.get("Content");

			RespTextMessage textMessage = getTextMessage(openId, toUserName,
					msgType);

			RespNewsMessage newsMessage = getNewsMessage(openId, toUserName,
					msgType);
			// 文本消息
			if (msgType.equals(EventConsts.REQ_MESSAGE_TYPE_TEXT)) {

				if (updateMessageCnt(openId)) {
					respContent = "哇，红包来了！";
				} else {
					respContent = RobotUtil.callTuling(msgContent);
				}

				// 事件推送
			} else if (msgType.equals(EventConsts.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 事件key
				String eventKey = requestMap.get("EventKey");
				// 订阅
				if (eventType.equals(EventConsts.EVENT_TYPE_SUBSCRIBE)) {
					// 根据OpenId获取用户信息
					int count = jdbcDao.executeForObject(
							"Fkj001t.selectFkj001tCnt001", openId, Integer.class);
					if (count <= 0) {
						// 订阅的时候
						Fkj001tDto userN = new Fkj001tDto();
						userN.createDate = DateUtil.toString(new Date(), DateUtil.DATE_PATTERN_1);
						userN.setOpenId(openId);
						// 该用户首次关注，登记用户
						jdbcDao.execute("Fkj001t.insertFkj001t001", userN);

						respContent = StringConsts.MESSAGE_EVENT_SUBSCRIBE;
					} else {
						// 该用户之前已登记过再次关注
						StringBuilder sb = new StringBuilder(
								StringConsts.MESSAGE_EVENT_SUBSCRIBE_RE);
						respContent = sb.toString();
					}

				}
				// 自定义菜单点击事件
				else if (eventType.equals(EventConsts.EVENT_TYPE_CLICK)) {
					// 注册信息
					if (EventConsts.EVENT_MY_INFO.equals(eventKey)) {
						Fkj001tDto user = jdbcDao.executeForObject(
								"Fkj001t.selectFkj001t001", openId, Fkj001tDto.class);

						if (user.getUserName() != null) {
							respContent = StringUtil.concat("会员姓名：", user.userName, "\r\n");
							respContent = StringUtil.concat(respContent, "关注时间：", user.createDate, "\r\n");
							respContent = StringUtil.concat(respContent, "注册时间：", user.registDate, "\r\n");
							respContent = StringUtil.concat(respContent, "互动次数：", StringUtil.convertIntegerToString(user.messageCnt));
						} else {
							respContent = "你还没有注册，立即注册成为注册会员，享受更多优惠!";
							RespArticle article = new RespArticle();
							article.setTitle("会员注册");
							article.setDescription("注册");
							article.setUrl("http://fengyilin1010.xicp.net/weixin-core/weixin/regist/index/"
									.concat(openId));
							List<RespArticle> articles = new ArrayList<RespArticle>();
							articles.add(article);
		                    articles.addAll(articles);
		                    newsMessage.setArticles(articles);
		                    newsMessage.setArticleCount(articles.size());
		                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
		                    return respMessage;
						}

					} else if (EventConsts.EVENT_LITTLE_GAME.equals(eventKey)) {
						List<RespArticle> articles = new ArrayList<RespArticle>();
						RespArticle article = new RespArticle();
						article.setTitle("冰桶");
						article.setDescription("加量版冰桶挑战");
						article.setPicUrl("http://fengyilin1010.xicp.net/weixin-core/assets/image/game_bingtong_01.jpg");
						article.setUrl("http://fengyilin1010.xicp.net/weixin-core/weixin/game/bingtong/"
								.concat(openId));
						articles.add(article);
						article = new RespArticle();
						article.setTitle("冰桶2");
						article.setDescription("冰桶挑战");
						article.setPicUrl("http://fengyilin1010.xicp.net/weixin-core/assets/image/game_bingtong_12.jpg");
						article.setUrl("http://fengyilin1010.xicp.net/weixin-core/weixin/game/bingtong2/"
								.concat(openId));
						articles.add(article);

	                    newsMessage.setArticles(articles);
	                    newsMessage.setArticleCount(articles.size());
	                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
	                    return respMessage;
					}
				}
			}

			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}

	private List<RespArticle> toArticlesCard(List<ScratchCard> scratchCardList,
			String openId) {
		List<RespArticle> articles = new ArrayList<RespArticle>();
		RespArticle article = null;
		for (ScratchCard card : scratchCardList) {
			article = new RespArticle();
			article.setTitle("刮刮卡");
			article.setDescription(card.getScratchCardContent());
			article.setPicUrl("http://fengyilin1010.xicp.net/struts23/images/gua.jpg");
			article.setUrl("http://fengyilin1010.xicp.net/struts23/ScratchCard_init"
					.concat("?openId=").concat(openId).concat("&")
					.concat("scratchCardId=").concat(card.getScratchCardId()));
			articles.add(article);
		}
		return articles;
	}

	private List<RespArticle> toArticles(List<RedPacket> redPackageList,
			String openId) {
		// TODO
		List<RespArticle> articles = new ArrayList<RespArticle>();
		RespArticle article = null;
		for (RedPacket red : redPackageList) {
			article = new RespArticle();
			article.setTitle("红包");
			article.setDescription(red.getRedPacketContent());
			article.setPicUrl("http://fengyilin1010.xicp.net/struts23/images/hb.png");
			article.setUrl("http://fengyilin1010.xicp.net/struts23/RedPacket_init"
					.concat("?openId=").concat(openId).concat("&")
					.concat("redPacketId=").concat(red.getRedPacketId()));
			articles.add(article);
		}
		return articles;
	}

	/**
	 * 用户聊天次数增加
	 * @param openId
	 * @return
	 */
	private boolean updateMessageCnt( String openId) {
		boolean messageOk = false;
		int messageCnt = 0;
		Integer cnt = jdbcDao.executeForObject("Fkj001t.selectFkj001t004", openId, Integer.class);
		if (cnt != null) {
			messageCnt = cnt;
		}
		Fkj001tDto user = new Fkj001tDto();
		if (messageCnt == 50) {
			user.setMessageCnt(0);
			messageOk = true;
		} else {
			user.setMessageCnt(messageCnt + 1);
		}
		user.setOpenId(openId);
		jdbcDao.execute("Fkj001t.updateFkj001t004", user);
		return messageOk;
	}

	private RespNewsMessage getNewsMessage(String fromUserName,
			String toUserName, String msgType) {

		// 回复文本消息
		RespNewsMessage newsMessage = new RespNewsMessage();
		// 调换from/toUserName
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(EventConsts.RESP_MESSAGE_TYPE_NEWS);

		return newsMessage;
	}

	private RespTextMessage getTextMessage(String fromUserName,
			String toUserName, String msgType) {

		// 回复文本消息
		RespTextMessage textMessage = new RespTextMessage();
		// 调换from/toUserName
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(EventConsts.RESP_MESSAGE_TYPE_TEXT);

		return textMessage;
	}

}
