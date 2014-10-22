package org.fengkeji.weixin.regist;

import org.fengkeji.weixin.base.AbstractWxService;
import org.fengkeji.weixin.common.util.DateUtil;
import org.fengkeji.weixin.entity.Fkj001tDto;
import org.springframework.stereotype.Service;

/**
 * 核心服务类
 * 
 * @author Song
 */
@Service
public class RegistService extends AbstractWxService {

	

	public void regist(RegistForm form) {
		Fkj001tDto dto = new Fkj001tDto();
		dto.openId = form.openId;
		dto.userName = form.userName;
		dto.sex = form.sex;
		dto.registDate = DateUtil.getSystemDate(DateUtil.DATE_PATTERN_1);
		
		jdbcDao.execute("Fkj001t.updateFkj001t002", dto);
	}
}
