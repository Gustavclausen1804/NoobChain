package NoobChain;

import java.security.PublicKey;
import java.util.ArrayList;

public class Transactions {

    public String transactionId; // Also hash of the transaction.
    public PublicKey sender; // senders address / public key
    public PublicKey reciepient; // Recipient address / public key
    public float value;
    public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.

}
