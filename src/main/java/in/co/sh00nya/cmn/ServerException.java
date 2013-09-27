package in.co.sh00nya.cmn;

public class ServerException extends Exception {
	
	private int errorCode;

	public ServerException(int errorCode) {
		super();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public ServerException(int errorCode, Throwable cause) {
		super(cause);
	}

}
