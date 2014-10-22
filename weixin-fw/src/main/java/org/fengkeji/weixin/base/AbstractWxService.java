/**
 * 
 */
package org.fengkeji.weixin.base;

import javax.inject.Inject;

import org.fengkeji.weixin.jdbc.JdbcDao;

/**
 * @author ASUS
 *
 */
public abstract class AbstractWxService {

	@Inject
	protected JdbcDao jdbcDao;
}
