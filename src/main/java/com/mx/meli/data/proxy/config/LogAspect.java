package com.mx.meli.data.proxy.config;

import java.util.Date;

/** David Garcia Isario
 * Esta clase se encarga del aspecto de logueo  en modo debug para todos los metodos del componente
 * 
 * 
 */

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogAspect {
	
	@Before("execution(* com.mx.meli.data.*.controller.*.*(..))")
	public void logControllerMethodsBefore(JoinPoint  joinPoint) {
		
		Date date = new Date();
		String nameMethod = joinPoint.getSignature().getName();
		log.info("-");
		log.info("-");
		log.info("===================================== START {} " + date + " =====================================",nameMethod);

		

	}
	
	@After("execution(* com.mx.meli.data.*.controller.*.*(..) )")
	public void logControllerMethodsAfter(JoinPoint joinPoint){
		Date date = new Date();
		String nameMethod = joinPoint.getSignature().getName();
		log.info("===================================== END   {} " + date + " =====================================",nameMethod);
		
	}
	
	@Before("execution(* com.mx.meli.data.*.service.impl.*.*(..))")
	public void logAroundAllMethodsIn(JoinPoint  joinPoint) {
		String parameters = "";
		 String nameMethod = joinPoint.getSignature().getName();
		 
		for (Object parameter : joinPoint.getArgs()) {
			parameters += " [" + parameter + "]";
		}
		
		 log.info("{} <=== in: {}" ,nameMethod ,parameters);
	}
	
	@AfterReturning(pointcut="execution(* com.mx.meli.data.*.service.impl.*.*(..))", returning="retVal")
	public void logAroundAllMethodsOut(JoinPoint joinPoint, Object retVal) {
		
		 String nameMethod = joinPoint.getSignature().getName();
		 if( retVal instanceof java.util.List<?>) {
			 log.info("{} ===> out.size: {}" ,nameMethod, ((java.util.List<?>) retVal).size());
		 }else {
			 log.info("{} ===> out: {}" ,nameMethod, retVal);
		 }
		 
	}
	 
}
