/**
 * 
 */
package com.camelot.kuka.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * @Description
 * @author    chenguibing 
 * @Date      2019年11月27日
 */
public class SpringConfigUtil {
	/**
	  * 添加外部配置文件参数
	 * @author chenguibing
	 * @param args
	 * @return
	 */

	public static String[] addSpringConfigLocation(String[] args) {
		/*
		 * 外部配置文件路径读取顺序：启动参数->操作系统环境变量->项目中的默认配置
		 * 环境变量配置示例：spring.config.location=/xxx/xxx.yml
		 * 
		 */
		
		String springConfigLocation = "spring.config.location";
		//默认配置，windows环境该目录为项目所在的盘加该路径，如在D盘，那么完整路径为D:/usr/local/configs/common.yml
		String dirConf = "/usr/local/configs/common.yml";

		// 读取启动参数,如果启动参数存在就不添加
		if (isExits(args, springConfigLocation)) {
			return args;
		}
		
		//读取系统环境变量
		String envVal=readEnv(springConfigLocation);
		if (envVal!=null) {
			dirConf=envVal;
		}
		System.out.println("启用外部配置文件"+dirConf);
		String arg="--"+springConfigLocation+"="+dirConf;
		args= addArg(args,arg);
		return args;
	}
	/**
	  * 设置spring.application.name,可通过启动参数覆盖
	 * @author chenguibing
	 * @param args
	 * @param appName
	 * @return
	 */
	public static String[] setAppName(String[] args,String appName) {
		String key="spring.application.name";
		if (isExits(args,key)) {
			return args;
		}
		String arg="--"+key+"="+appName;
		return addArg(args,arg);
	}
	
	/**
	  * 设置server.port,可通过启动参数覆盖
	 * @author chenguibing
	 * @param args
	 * @param port
	 * @return
	 */
	public static String[] setServerPort(String[] args,String port) {
		String key="server.port";
		if (isExits(args,key)) {
			return args;
		}
		if (StringUtils.isEmpty(port)) {
			return args;
		}
		String arg="--"+key+"="+port;
		return addArg(args,arg);
	}
	/**
	  * 生成8000-8999随机端口
	 * @return
	 */
	private static int genPort() {
		
		Random r=new Random();
		double ram=r.nextDouble();
		ram=(ram+8)*1000;
		int port=(int)ram;
		if(!isPortAvailable(port)) {
			System.out.println(port+"端口被占用，自动更换端口");
			port=genPort();
		}
		return port;
	}
	private static void bindPort(String host, int port) throws Exception {
	    Socket s = new Socket();
	    s.bind(new InetSocketAddress(host, port));
	    s.close();
	}
	/**
	 * 检查端口是否被占用
	 * @param port
	 * @return
	 */
	private static boolean isPortAvailable(int port) {
	    try {
	        bindPort("0.0.0.0", port);
	        bindPort("127.0.0.1", port);
	        bindPort(InetAddress.getLocalHost().getHostAddress(), port);
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	private static String[] addArg(String[] args,String arg) {
		if (args == null) {
			args = new String[] { arg};
			return args;
		}

		String[] argsNew = new String[args.length + 1];
		for (int i = 0; i < args.length; i++) {
			argsNew[i] = args[i];
		}
		argsNew[argsNew.length - 1] = arg;
		return argsNew;
	}
	private static boolean isExits(String[] args,String arg) {
		if (args==null) {
			return false;
		}
		
		for (int i = 0; i < args.length; i++) {
			String argName=args[i].split("=")[0];
			if (argName.contains(arg)) {
				System.out.println("存在启动参数"+args[i]);
				return true;
			}
		}
		return false;
	}
	
	private static String readEnv(String key) {
		Map<String,String> map = System.getenv();  
		Iterator<String> it = map.keySet().iterator();  
		while(it.hasNext()) {  
		   String key2=it.next();
		   if (key2.equals(key)) {
			   String val=map.get(key2);
			   String conf= key+"="+val;
			   System.out.println("读取到环境变量"+conf);
			   return conf;
		   }
		}
		return null;
	}
	
}
