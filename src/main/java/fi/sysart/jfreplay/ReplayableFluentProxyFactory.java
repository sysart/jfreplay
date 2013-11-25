package fi.sysart.jfreplay;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ReplayableFluentProxyFactory extends ReplayableProxyFactoryBase {

	public static <T extends Replayable> T createProxy(Class<T> clazz) {
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz },
				new ReplayableFluentProxyFactory(clazz));
	}

	private ReplayableFluentProxyFactory(Class apiClass) {
		super(apiClass);
	}

	protected Object record(Object proxy, Method m, Object[] args) {
		ReplayableFluentProxyFactory clone = (ReplayableFluentProxyFactory) clone();
		clone.addMethodCall(m, args);
		return Proxy.newProxyInstance(getApiClass().getClassLoader(),
				new Class[] { getApiClass() }, clone);
	}

	public Object replay(Object subject) {
		for (MethodCall m : getMethodCalls()) {
			subject = replay(subject, m);
		}
		return subject;
	}
}
