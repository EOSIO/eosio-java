package one.block.eosiojava.error.session;

public class TransactionGetSignatureError extends TransactionProcessorError {

    public TransactionGetSignatureError() {
    }

    public TransactionGetSignatureError(String s) {
        super(s);
    }

    public TransactionGetSignatureError(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransactionGetSignatureError(Throwable throwable) {
        super(throwable);
    }

    public TransactionGetSignatureError(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
