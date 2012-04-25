package levelFactory;

public interface Buffer <T>{
	// place int value into Buffer
	public void set (Class<T> c) throws InterruptedException;
	// obtain int value from Buffer
	public Class<T> get () throws InterruptedException;
}