package com.example.aop;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ProfilingAspect {

	
	@Around("@annotation(profiler) && packageConstrained()")
	public Object methodProfiled(ProceedingJoinPoint pjp, Profiling profiler) throws Throwable {
		long start = System.nanoTime();
		Object result = pjp.proceed();
		long stop = System.nanoTime();
		String methodName = pjp.getSignature().getName();
		TimeUnit tu = profiler.value();
		System.err.println(
				String.format("%s:%s runs %d %s", pjp.getTarget().getClass().getName(), methodName, tu.convert(stop - start, TimeUnit.NANOSECONDS), tu.name()));
		return result;
	}

	@Around("@target(profiler) &&  packageConstrained()")
	public Object classProfiled(ProceedingJoinPoint pjp, Profiling profiler) throws Throwable {
		return methodProfiled(pjp, profiler);
	}
	
	@Pointcut("within(com.example..*)")
	public void packageConstrained() {}
}
