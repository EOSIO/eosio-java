

package one.block.eosiojava.enums;

public enum AlgorithmEmployed {
    SECP256R1("secp256r1"),
    SECP256K1("secp256k1"),
    PRIME256V1("prime256v1");

    private String str;

    AlgorithmEmployed(String str){
        this.str = str;
    }

    public String getString(){
        return str;
    }
}
