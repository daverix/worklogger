package net.daverix.sqlquerybuilder;

/**
 * Created by daverix on 9/20/13.
 */
public interface SqlTableBuilder {
    public Table createTable(String tableName);
    public Table createTableIfNotExists(String tableName);

    public interface Table {
        ColumnType withField(String field);
    }

    public interface Creator {
        String create();
    }

    public interface ColumnType extends ColumnConstraint {
        ColumnConstraint asNumber();
        ColumnConstraint asReal();
        ColumnConstraint asText();
    }

    public interface AutoIncrement extends Table {
        Table autoIncrement();
    }

    public interface PrimaryKeyColumn extends Table {
        AutoIncrement primaryKey();
    }

    public interface ColumnConstraint extends PrimaryKeyColumn, Table, Creator {
        ColumnConstraint notNull();
        ColumnConstraint defaultValue(String value);
        References references(String tableName);
    }

    public interface References {
        ReferenceAction onDelete();
        ReferenceAction onUpdate();
        ColumnConstraint withField(String name);
    }

    public interface ReferenceAction {
        public ColumnConstraint setNUll();
        public ColumnConstraint setDefault();
        public ColumnConstraint cascade();
        public ColumnConstraint restrict();
        public ColumnConstraint noAction();
    }
}
