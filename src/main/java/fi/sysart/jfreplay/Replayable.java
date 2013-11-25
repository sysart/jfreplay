package fi.sysart.jfreplay;

public interface Replayable<TApi> {

	<TImplementation extends TApi> TImplementation replay(TImplementation implementation);

}
