package org.ndextools.morphcx.writers;

import org.ndextools.morphcx.shared.Configuration;
import org.ndextools.morphcx.shared.Utilities;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * Implementation class that formats and outputs a single row of a table using the PrintStream class.  It acts as a
 * substitute to using the Apache Commons CSV library functions.  Can be used for debugging this application.
 */
public final class TableToStream implements TableWritable, AutoCloseable {
    private final PrintStream out;
    private final char delimiter;
    private final String newline;

    /**
     * Class constructor
     *
     * @param delimiter character used to separate column data (typically comma or tab character)
     * @param newline string of characters used when overriding the JVM line separator system property.
     */
    public TableToStream(final PrintStream out, final char delimiter, final String newline) {
        Utilities.nullReferenceCheck(out, Configuration.class.getSimpleName());
        Utilities.nullReferenceCheck(newline, Configuration.class.getSimpleName());
        this.out = out;
        this.delimiter = delimiter;
        this.newline = newline;
    }

    /**
     * Refer to the corresponding method in the TableWritable interface for more details.
     *
     * @param rowOfColumns is a list of ordered columns/cells that are output as a single and complete row.
     * @throws Exception likely caused by an IOException when when writing to the underlying output stream.
     */
    @Override
    public void outputRow(final List<String> rowOfColumns) throws Exception {
        String[] columns = new String[rowOfColumns.size()];
        rowOfColumns.toArray(columns);

        StringBuilder row = new StringBuilder();
        for (String column : columns) {
            if (row.length() > 0) {
                row.append(delimiter);
            }
            row.append(column);
        }

        out.println(row.toString());
        if ( out.checkError() ) {
            String msg = String.format("%s: Error when writing to underlying output stream", this.getClass().getSimpleName());
            throw new IOException(msg);
        }
    }

    /**
     * Releases resources associated with AutoClosable interface
     */
    @Override
    public void close() {
        if (out != null) {
            out.flush();
        }
        if (out != null) {
            out.close();
        }
    }

    /**
     * Override of Object.toString()
     *
     * @return contents of class variables
     */
    @Override
    public String toString() {
        String stringDelimiter = Utilities.delimiterToStringConvert(this.delimiter);
        String stringNewline = Utilities.newlineToStringConvert(this.newline);

        return String.format("thisWriter=%s, delimiter='%s', newline='%s'",
                this.getClass().getSimpleName(),
                stringDelimiter,
                stringNewline);
    }

}
