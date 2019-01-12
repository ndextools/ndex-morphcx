package org.ndextools.morphcx.tables;

import java.io.IOException;
import org.ndexbio.model.cx.NiceCXNetwork;
import org.ndextools.morphcx.shared.CXReader;
import org.ndextools.morphcx.shared.Configuration;
import org.ndextools.morphcx.writers.TableWritable;
import org.ndextools.morphcx.writers.WriterFactory;

/**
 * The Root class acts as the controller once the configuration has been decided.
 */
public class CSVRoot {

    /**
     * Converts the JSON-format NDEx CX Network into a NiceNetworkCX object, then uses a factory
     * class to instantiate the appropriate writer.  Finally the NiceNetworkCX object is morphed to
     * the desired output format.
     *
     * @param cfg reference to a Configuration class object
     * @throws Exception base class exception when there is an IO or other processing error
     */
    public void execute(final Configuration cfg) throws Exception {

        NiceCXNetwork niceCX = LoadInputCxNetwork(cfg);
        try ( TableWritable writer = setupOutputDestination(cfg) ) {
            Webapp morph = new Webapp( cfg, niceCX, writer);
            morph.morphThisNiceCX();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private static NiceCXNetwork LoadInputCxNetwork(Configuration cfg) throws IOException {
        CXReader cxReader = new CXReader(cfg);
        return cxReader.produceNiceCX();
    }

    private static TableWritable setupOutputDestination(Configuration cfg) throws Exception {
        WriterFactory wf = new WriterFactory(cfg);
        return wf.getWriter();
    }

}
