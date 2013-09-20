package net.daverix.sqlquerybuilder;

/**
 * Created by daverix on 9/20/13.
 */
public class SqlTableBuilderImpl implements SqlTableBuilder {
    @Override
    public Table createTable(String tableName) {
        StringBuilder builder = new StringBuilder("CREATE TABLE ");
        builder.append(tableName);
        builder.append(" (");

        return new TableImpl(builder);
    }

    @Override
    public Table createTableIfNotExists(String tableName) {
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        builder.append(tableName);
        builder.append(" (");

        return new TableImpl(builder);
    }

    private class TableImpl implements Table {
        private final StringBuilder mBuilder;

        private TableImpl(StringBuilder builder) {
            mBuilder = builder;
        }

        @Override
        public Field withPrimaryKey(String field) {
            mBuilder.append(field).append(" ");

            Field f = new FieldImpl(mBuilder);

            mBuilder.append(" PRIMARY KEY");

            return f;
        }
    }

    private class FieldImpl implements Field {
        private final StringBuilder mBuilder;

        public FieldImpl(StringBuilder builder) {
            mBuilder = builder;
        }

        @Override
        public FieldType asNumber() {
            mBuilder.append("INTEGER");
            return new FieldTypeImpl(mBuilder);
        }

        @Override
        public FieldType asReal() {
            mBuilder.append("REAL");
            return new FieldTypeImpl(mBuilder);
        }

        @Override
        public FieldType asText() {
            mBuilder.append("TEXT");
            return new FieldTypeImpl(mBuilder);
        }
    }

    private class FieldTypeImpl implements FieldType {
        private final StringBuilder mBuilder;

        public FieldTypeImpl(StringBuilder builder) {
            mBuilder = builder;
        }

        @Override
        public FieldProperty whichCanBeNull() {
            mBuilder.append(" NOT NULL");
            return new FieldPropertyImpl(mBuilder);
        }

        @Override
        public FieldProperty whichMustNotBeNull() {
            return new FieldPropertyImpl(mBuilder);
        }
    }

    private class FieldPropertyImpl implements FieldProperty {
        private final StringBuilder mBuilder;

        public FieldPropertyImpl(StringBuilder builder) {
            mBuilder = builder;
        }

        @Override
        public Field andAddField(String field) {
            mBuilder.append(", ");
            return new FieldImpl(mBuilder);
        }

        @Override
        public String andCreateSql() {
            mBuilder.append(");");
            return mBuilder.toString();
        }
    }
}
