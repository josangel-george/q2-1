package com.red;

public class SessionExpiredException extends Exception {

	private static final long serialVersionUID = 2229178934627658410L;

	public SessionExpiredException() {
		super();
	}

	public SessionExpiredException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public SessionExpiredException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SessionExpiredException(String arg0) {
		super(arg0);
	}

	public SessionExpiredException(Throwable arg0) {
		super(arg0);
	}
}
