package one.block.eosiojava.error.session;

public class TransactionPrepareError extends TransactionProcessorError{

    public TransactionPrepareError() {
    }

    public TransactionPrepareError(String s) {
        super(s);
    }

    public TransactionPrepareError(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransactionPrepareError(Throwable throwable) {
        super(throwable);
    }

    public TransactionPrepareError(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
