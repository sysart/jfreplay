package fi.sysart.jfreplay;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ReplayableProxyFactory extends ReplayableProxyFactoryBase {

	public static <T extends Replayable> T createProxy(Class<T> clazz) {
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz },
				new ReplayableProxyFactory(clazz));
	}

	private ReplayableProxyFactory(Class apiClass) {
		super(apiClass);
	}

	protected Object record(Object proxy, Method m, Object[] args) {
		addMethodCall(m, args);
		return proxy;
	}

	public Object replay(Object subject) {
		for (MethodCall m : getMethodCalls()) {
			replay(subject, m);
		}
		return subject;
	}

}
