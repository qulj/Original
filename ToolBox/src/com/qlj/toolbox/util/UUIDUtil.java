package com.qlj.toolbox.util;

import java.util.UUID;

public class UUIDUtil {
	/**
	 * 
	 * Description:用于生成数据库的主键. <br/>
	 * 
	 * @author ibm
	 * @return
	 * @since JDK 1.6
	 */
	public synchronized static String getUuid()
	{
		return String.valueOf(UUID.randomUUID()).trim().replaceAll("-", "");
	}

	public synchronized static String getUuidTimeStamp()
	{
		return String.valueOf(UUID.randomUUID().randomUUID()).trim()
				.replaceAll("-", "");
	}

	/**
	 * 
	 * Description:用于生成数据库的主键. <br/>
	 * 
	 * @author ibm
	 * @return
	 * @since JDK 1.6
	 */
	public synchronized static String getUuidClockSequence()
	{
		return String.valueOf(UUID.randomUUID().randomUUID().randomUUID())
				.trim().replaceAll("-", "");
	}

	/**
	 * 
	 * Description:用于生成数据库的主键. <br/>
	 * 
	 * @author ibm
	 * @return
	 * @since JDK 1.6
	 */
//	public static void main(String[] arg)
//	{
//		System.out.println(UUIDUtil.getUuid());
////		System.out.println(UUIDUtil.getUuidTimeStamp());
////		System.out.println(UUIDUtil.getUuidClockSequence());
//	}
}
