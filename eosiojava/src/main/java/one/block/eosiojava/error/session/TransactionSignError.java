package one.block.eosiojava.error.session;

public class TransactionSignError extends TransactionProcessorError {

    public TransactionSignError() {
    }

    public TransactionSignError(String s) {
        super(s);
    }

    public TransactionSignError(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransactionSignError(Throwable throwable) {
        super(throwable);
    }

    public TransactionSignError(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
