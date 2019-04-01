package one.block.eosiojava.error.session;

public class TransactionCreateSignatureRequestError extends TransactionProcessorError {

    public TransactionCreateSignatureRequestError() {
    }

    public TransactionCreateSignatureRequestError(String s) {
        super(s);
    }

    public TransactionCreateSignatureRequestError(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransactionCreateSignatureRequestError(Throwable throwable) {
        super(throwable);
    }

    public TransactionCreateSignatureRequestError(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
