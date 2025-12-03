package infrastructure.sql.connection;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void testConnectionToDockerMySQL() throws Exception {
        Connection conn = DBConnection.getConnection();
        assertNotNull(conn);
        assertTrue(conn.isValid(2)); // timeout 2 segundos
    }

}