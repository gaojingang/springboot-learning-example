package com.kk.nettytest.util;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Who;

public class OSUtils {
	
	
	
	
	public static void main(String[] args) {
		Sigar sigar = new Sigar();
		try {
			Who[] whos = sigar.getWhoList();
			
			for (Who who : whos) {
				System.out.println(who);
			}
			
			
			CpuInfo[] infos = sigar.getCpuInfoList();
			for (CpuInfo info : infos) {
				System.out.println(info);
			}
			
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
