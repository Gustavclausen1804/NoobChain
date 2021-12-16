package NoobChain;

import java.util.Date;
import java.util.ArrayList;

public class Block {

    public String hash;
    public String previousHash;
    private String data; // data is simple message
    private long timeStamp; // number of miliseconds since 1/1/1970
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); // our data will be a simple message.
    public String merkleRoot;
    private int nonce;

    // Block contructor

    public Block(String previousHash) {
        // this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash(); // Calculates the hash, with the encrypted string.}
    }

    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        data
                        + Integer.toString(nonce) + data +
                        merkleRoot);
        return calculatedhash;
    }

    // Miners will try different variable values in the block until its hash starts
    // with a certain number of 0’s.

    // The mineBlock() method takes in an int called difficulty, this is the number
    // of 0’s they must solve for. Low difficulty like 1 or 2 can be solved nearly
    // instantly on most computers, i’d suggest something around 4–6 for testing.
    public void mineBlock(int difficulity) {
        String target = new String(new char[difficulity]).replace('\0', '0'); // Create a string with difficulty * "0"
        while (!hash.substring(0, difficulity).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Hash block mined!!!" + hash);
    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;

        // loop through blockchain to check hashes:
        for (int i = 1; i < NoobChain.blockchain.size(); i++) {
            currentBlock = NoobChain.blockchain.get(i);
            previousBlock = NoobChain.blockchain.get(i - 1);
            // compare registered hash and calculated hash:
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            // compare previous hash and registered previous hash
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
        }
        return true;
    }

    public boolean addTransaction(Transaction transaction) {
        // process transaction and check if valid, unless block is genesis block then
        // ignore.
        if (transaction == null)
            return false;
        if ((previousHash != "0")) {
            if ((transaction.processTransaction() != true)) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }

}
