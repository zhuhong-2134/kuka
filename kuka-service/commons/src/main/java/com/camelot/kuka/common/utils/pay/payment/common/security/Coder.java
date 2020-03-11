package com.camelot.kuka.common.utils.pay.payment.common.security;

/**
 *  
 * @description 
 * 		
 * @author  
 *
 * @modified_by 
 */
public interface Coder {

	public String encode(byte[] data);

	public byte[] decode(String string);

}