package fi.sysart.jfreplay;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public abstract class ReplayableProxyFactoryBase implements
		java.lang.reflect.InvocationHandler, Serializable, Replayable, Cloneable {

	private final Class apiClass;
	private List<MethodCall> methodCalls = new ArrayList();

	protected ReplayableProxyFactoryBase(Class apiClass) {
		this.apiClass = apiClass;
	}

	protected void addMethodCall(Method m, Object[] args) {
		methodCalls.add(new MethodCall(m.getName(), m.getParameterTypes(), args));
	}

	public Object invoke(Object proxy, Method m, Object[] args) throws
			Throwable {
		if (isReplay(m)) return replay(args[0]);
		if (m.getDeclaringClass().equals(Object.class)) return replay(this, m, args);
		return record(proxy, m, args);
	}

	protected abstract Object record(Object proxy, Method m, Object[] args);

	protected boolean isReplay(Method m) {
		return m.getName().equalsIgnoreCase("replay");
	}

	protected ReplayableProxyFactoryBase clone() {
		try {
			ReplayableProxyFactoryBase clone = (ReplayableProxyFactoryBase) super.clone();
			clone.methodCalls = new ArrayList(methodCalls);
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	protected List<MethodCall> getMethodCalls() {
		return methodCalls;
	}

	public Class getApiClass() {
		return apiClass;
	}

	protected Object replay(Object subject, MethodCall methodCall) {
		return replay(subject, methodCall.getName(), methodCall.getArgTypes(), methodCall.getArgs());
	}

	protected Object replay(Object subject, Method m, Object[] args) {
		return replay(subject, m.getName(), m.getParameterTypes(), args);
	}

	protected Object replay(Object subject, String methodName, Class[] argTypes, Object[] args) {
		try {
			Method m = subject.getClass().getMethod(methodName, argTypes);
			return m.invoke(subject, args);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			if (e.getCause() instanceof RuntimeException) throw (RuntimeException) e.getCause();
			throw new RuntimeException(e);
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (Proxy.isProxyClass(obj.getClass())) return equals(Proxy.getInvocationHandler(obj));
		if (!getClass().isAssignableFrom(obj.getClass())) return false;
		ReplayableProxyFactoryBase other = (ReplayableProxyFactoryBase) obj;
		return apiClass.equals(other.apiClass) && methodCalls.equals(other.methodCalls);
	}

	public int hashCode() {
		return apiClass.hashCode() + methodCalls.hashCode();
	}

}
