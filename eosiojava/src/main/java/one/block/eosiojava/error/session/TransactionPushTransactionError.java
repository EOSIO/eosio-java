package one.block.eosiojava.error.session;

public class TransactionPushTransactionError extends TransactionProcessorError {

    public TransactionPushTransactionError() {
    }

    public TransactionPushTransactionError(String s) {
        super(s);
    }

    public TransactionPushTransactionError(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransactionPushTransactionError(Throwable throwable) {
        super(throwable);
    }

    public TransactionPushTransactionError(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
