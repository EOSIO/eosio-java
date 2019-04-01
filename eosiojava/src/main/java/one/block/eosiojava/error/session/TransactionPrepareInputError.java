package one.block.eosiojava.error.session;

public class TransactionPrepareInputError extends TransactionPrepareError {

    public TransactionPrepareInputError() {
    }

    public TransactionPrepareInputError(String s) {
        super(s);
    }

    public TransactionPrepareInputError(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransactionPrepareInputError(Throwable throwable) {
        super(throwable);
    }

    public TransactionPrepareInputError(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
