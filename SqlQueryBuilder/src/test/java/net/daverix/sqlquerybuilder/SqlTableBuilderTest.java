package net.daverix.sqlquerybuilder;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

/**
 * Created by daverix on 9/20/13.
 */
public class SqlTableBuilderTest {
    @Test
    public void testShouldVerifyBuilderProvideCorrectOutput() {
        //Arrange
        final String expected = "CREATE TABLE IF NOT EXISTS APA (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "message TEXT);";

        //Act
        final String actual = SqlTableBuilderFactory.getBuilder().createTableIfNotExists("APA")
                .withField("_id").asNumber().notNull().primaryKey().autoIncrement()
                .withField("name").asText().notNull()
                .withField("message").asText().notNull()
                .create();

        //Assert
        Assert.assertThat(actual, is(equalTo(expected)));
    }
}
