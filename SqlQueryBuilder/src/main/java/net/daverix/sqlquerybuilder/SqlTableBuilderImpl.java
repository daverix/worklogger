package net.daverix.sqlquerybuilder;

/**
 * Created by daverix on 9/20/13.
 */
public class SqlTableBuilderImpl implements SqlTableBuilder {
    @Override
    public Column createTable(String tableName) {
        StringBuilder builder = new StringBuilder("CREATE TABLE ");
        builder.append(tableName);
        builder.append(" (");

        return new FirstColumn(builder);
    }

    @Override
    public Column createTableIfNotExists(String tableName) {
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        builder.append(tableName);
        builder.append(" (");

        return new FirstColumn(builder);
    }

    private class FirstColumn implements Column {
        private final StringBuilder mBuilder;

        private FirstColumn(StringBuilder builder) {
            mBuilder = builder;
        }

        @Override
        public ColumnType withField(String field) {
            mBuilder.append(field);

            return new ColumnTypeImpl(mBuilder);
        }
    }

    private class OtherColumn implements Column {
        private final StringBuilder mBuilder;

        private OtherColumn(StringBuilder builder) {
            mBuilder = builder;
        }

        @Override
        public ColumnType withField(String field) {
            mBuilder.append(",").append(field);

            return new ColumnTypeImpl(mBuilder);
        }
    }

    private class ColumnTypeImpl implements ColumnType {
        private final StringBuilder mBuilder;

        public ColumnTypeImpl(StringBuilder builder) {
            mBuilder = builder;
        }

        @Override
        public ColumnConstraint asNumber() {
            mBuilder.append(" INTEGER");
            return new ColumnConstrainImpl(mBuilder);
        }

        @Override
        public ColumnConstraint asReal() {
            mBuilder.append(" REAL");
            return new ColumnConstrainImpl(mBuilder);
        }

        @Override
        public ColumnConstraint asText() {
            mBuilder.append(" TEXT");
            return new ColumnConstrainImpl(mBuilder);
        }

        @Override
        public ColumnConstraint notNull() {
            mBuilder.append(" NOT NULL");
            return new ColumnConstrainImpl(mBuilder);
        }

        @Override
        public ColumnConstraint defaultValue(String value) {
            mBuilder.append(" DEFAULT ").append(value);
            return new ColumnConstrainImpl(mBuilder);
        }

        @Override
        public References references(String tableName) {
            return null;
        }

        @Override
        public String create() {
            mBuilder.append(");");
            return mBuilder.toString();
        }

        @Override
        public AutoIncrement primaryKey() {
            mBuilder.append(" PRIMARY KEY");
            return new AutoIncrementImpl(mBuilder);
        }

        @Override
        public ColumnType withField(String field) {
            mBuilder.append(",").append(field);
            return new ColumnTypeImpl(mBuilder);
        }
    }

    private class ColumnConstrainImpl implements ColumnConstraint {
        private final StringBuilder mBuilder;

        public ColumnConstrainImpl(StringBuilder builder) {
            mBuilder = builder;
        }

        @Override
        public ColumnConstraint notNull() {
            mBuilder.append(" NOT NULL");
            return new ColumnConstrainImpl(mBuilder);
        }

        @Override
        public ColumnConstraint defaultValue(String value) {
            mBuilder.append(" DEFAULT").append(value);
            return this;
        }

        @Override
        public References references(String tableName) {
            return null;
        }

        @Override
        public String create() {
            return mBuilder.append(");").toString();
        }

        @Override
        public AutoIncrement primaryKey() {
            mBuilder.append(" PRIMARY KEY");
            return new AutoIncrementImpl(mBuilder);
        }

        @Override
        public ColumnType withField(String field) {
            mBuilder.append(",").append(field);
            return new ColumnTypeImpl(mBuilder);
        }
    }

    private class AutoIncrementImpl implements AutoIncrement {
        private final StringBuilder mBuilder;

        public AutoIncrementImpl(StringBuilder builder) {
            mBuilder = builder;
        }

        @Override
        public Column autoIncrement() {
            mBuilder.append(" AUTOINCREMENT");
            return new OtherColumn(mBuilder);
        }

        @Override
        public ColumnType withField(String field) {
            mBuilder.append(",").append(field);
            return new ColumnTypeImpl(mBuilder);
        }
    }
}
