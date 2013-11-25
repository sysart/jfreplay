package fi.sysart.jfreplay;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;

/** Value Type for method call and it's arguments */
public class MethodCall implements Serializable {
	private final String name;
	private final Object[] args;
	private final Class[] argTypes;

	public MethodCall(String name, Class[] argTypes, Object[] args) {
		if (name == null) throw new NullPointerException();
		this.name = name;
		this.argTypes = argTypes;
		this.args = args;
	}

	public Object[] getArgs() {
		return args;
	}

	public Class[] getArgTypes() {
		return argTypes;
	}

	public String getName() {
		return name;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof MethodCall)) return false;
		MethodCall other = (MethodCall) obj;
		return name.equals(other.name) && Arrays.equals(args, other.args)
				&& Arrays.equals(argTypes, other.argTypes);
	}

	public int hashCode() {
		return name.hashCode() + Arrays.hashCode(args) + Arrays.hashCode(argTypes);
	};

	public String toString() {
		return MessageFormat.format("{0}[{1}]", getClass(), getName());
	};
}
