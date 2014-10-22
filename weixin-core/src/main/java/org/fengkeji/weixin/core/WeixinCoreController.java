/**
 * 
 */
package org.fengkeji.weixin.core;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ASUS
 *
 */
@Controller
@RequestMapping(value="/weixin/core/")
public class WeixinCoreController extends AbstractWxController<WeixinCoreService>{
    /**
     * 确认请求来自微信服务器
     */
	@RequestMapping(value="execute", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 与接口配置信息中的Token要一致
		String token = "fengyilin";
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce, token)) {
            out.print(echostr);
        }
        out.close();
        out = null;
    }
	
    /**
     * 处理微信服务器发来的消息
     */
	@RequestMapping(value="execute", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 调用核心业务类接收消息、处理消息
        String respMessage = service.processRequest(request);

        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
        out= null;

    }

	@Override
	@Inject
	@Named("CoreService")
	protected void setService(WeixinCoreService service) {
		this.service = service;
	}
	
	
    /**
     * 处理微信服务器发来的消息
     */
	@RequestMapping("init")
    public String init(HttpServletRequest request, HttpServletResponse response,Model model) {
		model.addAttribute("name","spring-mvc");  
		return "/login/login";

    }
	
}
