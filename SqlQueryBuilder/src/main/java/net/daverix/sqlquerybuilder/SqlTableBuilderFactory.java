package net.daverix.sqlquerybuilder;

/**
 * Created by daverix on 9/20/13.
 */
public class SqlTableBuilderFactory {
    public static SqlTableBuilder getBuilder() {
        return new SqlTableBuilderImpl();
    }
}
