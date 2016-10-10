package kandroid.utils;

public class CatException extends RuntimeException {
	public CatException() {
		super();
	}

    public CatException(String detailMessage) {
		super(detailMessage);
	}

    public CatException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

    public CatException(Throwable throwable) {
		super(throwable);
	}
}
