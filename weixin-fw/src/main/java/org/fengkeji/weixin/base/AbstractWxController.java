/**
 * 
 */
package org.fengkeji.weixin.base;

/**
 * @author ASUS
 *
 */
public abstract class AbstractWxController < S extends AbstractWxService>{

	protected S service;

	protected abstract void setService(S service);
	
	
}
