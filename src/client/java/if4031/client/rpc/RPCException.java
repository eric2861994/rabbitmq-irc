package if4031.client.rpc;

public class RPCException extends Exception {
    public RPCException() {
    }

    public RPCException(String message) {
        super(message);
    }

    public RPCException(Throwable cause) {
        super(cause);
    }

    public RPCException(String message, Throwable cause) {
        super(message, cause);
    }
}
