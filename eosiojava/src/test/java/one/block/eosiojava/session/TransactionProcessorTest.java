package one.block.eosiojava.session;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import one.block.eosiojava.error.rpcProvider.GetInfoError;
import one.block.eosiojava.error.session.TransactionPrepareError;
import one.block.eosiojava.interfaces.IABIProvider;
import one.block.eosiojava.interfaces.IRPCProvider;
import one.block.eosiojava.interfaces.ISerializationProvider;
import one.block.eosiojava.interfaces.ISignatureProvider;
import one.block.eosiojava.models.rpcProvider.Action;
import one.block.eosiojava.models.rpcProvider.Authorization;
import one.block.eosiojava.models.rpcProvider.response.GetInfoResponse;
import one.block.eosiojava.utilities.Utils;
import org.junit.Before;
import org.junit.Test;

public class TransactionProcessorTest {

    private IRPCProvider mockedRpcProvider = mock(IRPCProvider.class);
    private ISignatureProvider mockedSignatureProvider = mock(ISignatureProvider.class);
    private IABIProvider mockedABIProvider = mock(IABIProvider.class);
    private ISerializationProvider mockedSerializationProvider = mock(ISerializationProvider.class);

    private TransactionSession session;

    @Before
    public void setUp() {
        this.session = new TransactionSession(this.mockedSerializationProvider, this.mockedRpcProvider, this.mockedABIProvider,
                this.mockedSignatureProvider);
    }

    @Test
    public void prepare() {
        TransactionProcessor processor = session.getTransactionProcessor();
        assertNotNull(processor);

        // Mock data
        String getInfoResponseJSON = "{\n"
                + "    \"server_version\": \"0f6695cb\",\n"
                + "    \"chain_id\": \"687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17\",\n"
                + "    \"head_block_num\": 31984402,\n"
                + "    \"last_irreversible_block_num\": 31984357,\n"
                + "    \"last_irreversible_block_id\": \"01e80ae52e0de696dd72abac81217fd4e89d19f16b1d48ddd9a0345c5859daeb\",\n"
                + "    \"head_block_id\": \"01e80b12d7bbc471edb47e384f078393fbd24520d8cb192e834f35e66849d307\",\n"
                + "    \"head_block_time\": \"2019-04-01T22:08:40.000\",\n"
                + "    \"head_block_producer\": \"blkproducer3\",\n"
                + "    \"virtual_block_cpu_limit\": 200000000,\n"
                + "    \"virtual_block_net_limit\": 1048576000,\n"
                + "    \"block_cpu_limit\": 199900,\n"
                + "    \"block_net_limit\": 1048576,\n"
                + "    \"server_version_string\": \"v1.3.0\"\n"
                + "}";

        try {
            when(mockedRpcProvider.getInfo()).thenReturn(Utils.getDefaultGson().fromJson(getInfoResponseJSON, GetInfoResponse.class));
        } catch (GetInfoError getInfoError) {
            getInfoError.printStackTrace();
        }

        // Apply
        List<Action> actions = this.defaultActions();
        try {
            processor.prepare(actions);
        } catch (TransactionPrepareError transactionPrepareError) {
            transactionPrepareError.printStackTrace();
        }
    }

    @Test
    public void sign() {
    }

    @Test
    public void broadcast() {
    }

    @Test
    public void signAndBroadcast() {
    }

    @Test
    public void toJSON() {
    }

    @Test
    public void serialize() {
    }

    @Test
    public void getTransaction() {
    }

    @Test
    public void getOriginalTransaction() {
    }

    @Test
    public void getSignatures() {
    }

    @Test
    public void getSerializedTransaction() {
    }

    @Test
    public void getTransactionConfig() {
    }

    @Test
    public void setTransactionConfig() {
    }

    @Test
    public void setChainId() {
    }

    @Test
    public void setAvailableKeys() {
    }

    @Test
    public void setRequiredKeys() {
    }

    private List<Action> defaultActions() {
        String jsonData = "{\n" +
                "\"from\": \"an\",\n" +
                "\"to\": \"ken\",\n" +
                "\"quantity\": \"10.0000 EOS\",\n" +
                "\"memo\" : \"Something\"\n" +
                "}";

        List<Authorization> authorizations = new ArrayList<>();
        authorizations.add(new Authorization("cryptkeeper", "active"));
        List<Action> actions = new ArrayList<>();
        actions.add(new Action("eosio.token", "transfer", authorizations, jsonData));

        return actions;
    }
}