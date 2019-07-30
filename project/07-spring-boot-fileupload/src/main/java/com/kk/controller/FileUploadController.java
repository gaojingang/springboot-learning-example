package com.kk.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/***
 *  
 * @author gaoji
 *
 * SpringBoot 文件上传
 *
 */


@RestController  //表示改类下的所有方法都会做 Json格式的转化 ， =  Controller +  Body
public class FileUploadController {

	@RequestMapping("/fileuploadcontroller")
	public Map<String,Object> fileuplaod(MultipartFile filename) throws  Exception {
		System.out.println("FileUploadController.fileupad() "+filename.getOriginalFilename());
		
		filename.transferTo(new File("e:/"+filename.getOriginalFilename()));
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("msg", "ok");
		
		return map;
		
	}
	
	/*


org.apache.tomcat.util.http.fileupload.FileUploadBase$SizeLimitExceededException: the request was rejected because its size (127446960) exceeds the configured maximum (10485760)
	at org.apache.tomcat.util.http.fileupload.FileUploadBase$FileItemIteratorImpl.<init>(FileUploadBase.java:808) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.tomcat.util.http.fileupload.FileUploadBase.getItemIterator(FileUploadBase.java:256) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.tomcat.util.http.fileupload.FileUploadBase.parseRequest(FileUploadBase.java:280) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.catalina.connector.Request.parseParts(Request.java:2855) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.catalina.connector.Request.parseParameters(Request.java:3194) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.catalina.connector.Request.getParameter(Request.java:1116) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.catalina.connector.RequestFacade.getParameter(RequestFacade.java:381) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:84) ~[spring-web-5.1.7.RELEASE.jar:5.1.7.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) ~[spring-web-5.1.7.RELEASE.jar:5.1.7.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200) ~[spring-web-5.1.7.RELEASE.jar:5.1.7.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) ~[spring-web-5.1.7.RELEASE.jar:5.1.7.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:200) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96) [tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:490) [tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139) [tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92) [tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) [tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343) [tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:408) [tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66) [tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:836) [tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1747) [tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) [tomcat-embed-core-9.0.19.jar:9.0.19]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) [na:1.8.0_181]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) [na:1.8.0_181]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) [tomcat-embed-core-9.0.19.jar:9.0.19]
	at java.lang.Thread.run(Thread.java:748) [na:1.8.0_181]

	 */
	
}
