package one.block.eosiojava.error.session;

public class TransactionPrepareRpcError extends TransactionPrepareError {

    public TransactionPrepareRpcError() {
    }

    public TransactionPrepareRpcError(String s) {
        super(s);
    }

    public TransactionPrepareRpcError(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransactionPrepareRpcError(Throwable throwable) {
        super(throwable);
    }

    public TransactionPrepareRpcError(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
