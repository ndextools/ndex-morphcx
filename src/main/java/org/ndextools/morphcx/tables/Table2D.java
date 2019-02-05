package org.ndextools.morphcx.tables;

import java.io.IOException;
import java.util.List;

/**
 * Interface for Table classes
 */
public interface Table2D {

    void morphThisCX() throws IOException;

    List<String> buildColumnHeadings();

}
