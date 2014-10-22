package org.fengkeji.weixin.common.thymeleaf;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;



import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

/**
 * Springコンテナに登録されたThymeleafのタグプロセッサ、アトリビュートプロセッサを自動登録するDialectです。
 * 
 * @author Hironobu Isoda
 * @since 1.0
 * 
 */
@Component("thymeleafDialect")
public class ThymeleafDialect extends AbstractDialect {

	@Inject
	private ApplicationContext applicationContext;

	@Override
	public Set<IProcessor> getProcessors() {
		Map<String, IProcessor> beans = applicationContext.getBeansOfType(IProcessor.class);
		if (beans.size() != 0) {
			Set<IProcessor> processors = new HashSet<IProcessor>();
			processors.addAll(beans.values());
			return processors;
		}
		else {
			return super.getProcessors();
		}
	}

	@Override
	public String getPrefix() {
		return "s";
	}

	@Override
	public boolean isLenient() {
		return false;
	}

}
