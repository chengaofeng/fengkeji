/**
 * 
 */
package org.fengkeji.weixin.game;

import javax.inject.Inject;
import javax.inject.Named;

import org.fengkeji.weixin.base.AbstractWxController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ASUS
 *
 */
@Controller
@RequestMapping(value="/weixin/game/")
public class GameController extends AbstractWxController<GameService>{
    
	@Override
	@Inject
	@Named("gameService")
	protected void setService(GameService service) {
		this.service = service;
	}
	
	
    /**
     * 处理微信服务器发来的消息
     */
	@RequestMapping(value="/bingtong/{openId}")
    public String bingtong(@ModelAttribute GameForm registForm) {
		return "/game/bingtong";

    }
    /**
     * 处理微信服务器发来的消息
     */
	@RequestMapping(value="/bingtong2/{openId}")
    public String bingtong2(@ModelAttribute GameForm registForm) {
		return "/game/bingtong2";

    }
	
}
