package user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.UserAccount;
import user.UserManager;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by root on 11/10/14.
 */

public class UserManagerTest {

    private UserManager um, um1;
    private ArrayList<UserAccount> ual;

    @Before
    public void setUp() {
        um = new UserManager();
        ual = new ArrayList<UserAccount>();
        ual.add(new UserAccount());
        um1 = new UserManager(ual);
    }

    @Test
    public void UserManagerTest() {
        assertEquals(new ArrayList<UserAccount>(), um.getUserList());
        assertEquals(ual, um1.getUserList());
    }

    @Test
    public void LoginTest() {
        assertNull(um.login("login", "password"));
        assertNull(um1.login("Bob", "agile"));
        assertEquals(new UserAccount(), um1.login("login", "password"));
    }

    @After
    public void tearDown() {
        um = null;
        um1 = null;
        ual = null;
    }
}