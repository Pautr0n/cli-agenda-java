package infrastructure.sql.dao;

import infrastructure.sql.connection.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.enums.DoneType;
import task.enums.PriorityType;
import task.model.Task;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class MySQLTaskDAOAdapterTest {
    private MySQLTaskDAOAdapter dao;

    @BeforeEach
    void setUp() {
        Connection conn = DBConnection.getConnection(); // usa db.properties
        dao = new MySQLTaskDAOAdapter(conn);
    }

    @Test
    void testFindByIdExisting() {
        Task task = dao.findById(1);
        assertNotNull(task);
        assertEquals("Comprar pan", task.getTitle());
        assertEquals(PriorityType.MEDIUM, task.getPriority());
        assertEquals(DoneType.NOTDONE, task.getDoneStatus());
    }







}