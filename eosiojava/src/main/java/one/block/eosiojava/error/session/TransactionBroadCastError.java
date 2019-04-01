package one.block.eosiojava.error.session;

public class TransactionBroadCastError extends TransactionProcessorError {

    public TransactionBroadCastError() {
    }

    public TransactionBroadCastError(String s) {
        super(s);
    }

    public TransactionBroadCastError(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransactionBroadCastError(Throwable throwable) {
        super(throwable);
    }

    public TransactionBroadCastError(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
