package net.daverix.sqlquerybuilder;

/**
 * Created by daverix on 9/20/13.
 */
public interface SqlTableBuilder {
    public Column createTable(String tableName);
    public Column createTableIfNotExists(String tableName);

    public interface Column {
        ColumnType withField(String field);
    }

    public interface Creator extends Column {
        String create();
    }

    public interface ColumnType extends ColumnConstraint {
        ColumnConstraint asNumber();
        ColumnConstraint asReal();
        ColumnConstraint asText();
    }

    public interface AutoIncrement extends Column {
        Column autoIncrement();
    }

    public interface PrimaryKeyColumn extends Column {
        AutoIncrement primaryKey();
    }

    public interface ColumnConstraint extends PrimaryKeyColumn, Column, Creator {
        ColumnConstraint notNull();
        ColumnConstraint defaultValue(String value);
        References references(String tableName);
    }

    public interface References extends Column {
        ReferenceAction onDelete();
        ReferenceAction onUpdate();
    }

    public interface ReferenceAction extends Creator {
        public ColumnConstraint setNull();
        public ColumnConstraint setDefault();
        public ColumnConstraint cascade();
        public ColumnConstraint restrict();
        public ColumnConstraint noAction();
    }
}
