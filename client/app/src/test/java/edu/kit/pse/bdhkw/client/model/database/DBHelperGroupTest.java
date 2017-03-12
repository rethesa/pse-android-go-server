package edu.kit.pse.bdhkw.client.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.kit.pse.bdhkw.client.controller.objectStructure.AccountHandler;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for creating and upgrading database.
 * @author Theresa Heine
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(DBHelperGroup.class)
public class DBHelperGroupTest {

    @Mock
    private Context mockContext;

    @Before
    public void setUp() throws Exception {
        mockContext = mock(Context.class);
    }

    @Test
    public void onCreate() throws Exception {
        DBHelperGroup dbHelperGroupSpy = Mockito.spy(new DBHelperGroup(mockContext));
        SQLiteDatabase sqLiteDatabaseMock = mock(SQLiteDatabase.class);
        dbHelperGroupSpy.onCreate(sqLiteDatabaseMock);

        Assert.assertNotNull(dbHelperGroupSpy);
        verify(sqLiteDatabaseMock, times(2)).execSQL(anyString());
    }

    @Test
    public void onUpgrade() throws Exception {
        DBHelperGroup dbHelperGroupSpy = Mockito.spy(new DBHelperGroup(mockContext));
        SQLiteDatabase sqLiteDatabaseMock = mock(SQLiteDatabase.class);
        dbHelperGroupSpy.onUpgrade(sqLiteDatabaseMock, 1, 2);

        Assert.assertNotNull(dbHelperGroupSpy);
        verify(sqLiteDatabaseMock, times(4)).execSQL(anyString());
        verify(dbHelperGroupSpy, times(1)).onCreate(sqLiteDatabaseMock);

    }

}