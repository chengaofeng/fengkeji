/**
 * 
 */
package org.fengkeji.weixin.regist;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fengkeji.weixin.common.util.SignUtil;
import org.fengkeji.weixin.base.AbstractWxController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ASUS
 *
 */
@Controller
@RequestMapping(value="/weixin/regist/")
public class RegistController extends AbstractWxController<RegistService>{

	@Override
	@Inject
	@Named("registService")
	protected void setService(RegistService service) {
		this.service = service;
	}
	
	
    /**
     * 处理微信服务器发来的消息
     */
	@RequestMapping(value="/index/{openId}")
    public String index(@ModelAttribute RegistForm registForm) {
		return "/regist/regist";

    }
	
    /**
     * 处理微信服务器发来的消息
     */
	@RequestMapping(value="regist")
    public String regist(@ModelAttribute RegistForm registForm) {
		service.regist(registForm);
		return "/regist/regist";
    }
	
}
