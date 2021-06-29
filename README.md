# How to apply aop to web filters


@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Profiling {
	TimeUnit value() default TimeUnit.MICROSECONDS;
}


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

